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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class EditTemplateActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference ;
    Button editI , editB , editC , editR;
    ImageView imageLogout , home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_template);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbar);
        imageLogout=(ImageView)findViewById(R.id.imageLogOut);
        home=(ImageView)findViewById(R.id.imageHome);
        editI=(Button)findViewById(R.id.btn_edit_intro);
        editB=(Button)findViewById(R.id.btn_edit_body);
        editC=(Button)findViewById(R.id.btn_edit_con);
        editR=(Button)findViewById(R.id.btn_edit_rej);
        firebaseAuth=FirebaseAuth.getInstance();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent h= new Intent(EditTemplateActivity.this, HomeAdminActivity.class);
                startActivity(h);
            }
        });
        imageLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(EditTemplateActivity.this);
                builder1.setMessage("Are you sure ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Alert
                                firebaseAuth.signOut();
                                Intent main = new Intent(EditTemplateActivity.this,MainActivity.class);
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

        editI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent t = new Intent(EditTemplateActivity.this,EditIntroActivity.class);
                startActivity(t);
            }
        });


        editB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(EditTemplateActivity.this,EditBodyActivity.class);
                startActivity(a);
            }
        });

        editC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent t = new Intent(EditTemplateActivity.this,EditConActivity.class);
                startActivity(t);
            }
        });


        editR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(EditTemplateActivity.this,EditRejActivity.class);
                startActivity(a);
            }
        });

    }
}
