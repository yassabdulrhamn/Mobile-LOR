package lorteam.mobilelor;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WriteActivity extends AppCompatActivity {
    ImageView imageLogout , home;


    FirebaseAuth firebaseAuth;
    Button template ;
    Button custom ;
    String emailRequester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbar);
        imageLogout=(ImageView)findViewById(R.id.imageLogOut);
        home=(ImageView)findViewById(R.id.imageHome);
        template=(Button)findViewById(R.id.btn_template);
        custom=(Button)findViewById(R.id.btn_out);
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            emailRequester= null;
        } else {
            emailRequester= extras.getString("EmailRequester");
        }
        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
           String uid= user.getUid();

        }

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent h= new Intent(WriteActivity.this, HomeIssuerActivity.class);
                startActivity(h);
            }
        });

        imageLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(WriteActivity.this);
                builder1.setMessage("Are you sure to logout?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Alert
                                firebaseAuth.signOut();
                                Intent main = new Intent(WriteActivity.this,MainActivity.class);
                                startActivity(main);
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });

        template.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tem= new Intent(WriteActivity.this,Step1Activity.class);
                tem.putExtra("EmailRequester",emailRequester);
                startActivity(tem);
            }
        });
        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c= new Intent(WriteActivity.this,customActivity.class);
                c.putExtra("EmailRequester",emailRequester);
                startActivity(c);
            }
        });
    }
}
