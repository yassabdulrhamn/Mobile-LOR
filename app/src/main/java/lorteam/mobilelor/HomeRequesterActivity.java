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

public class HomeRequesterActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference ;
    Button profile , req, listR, contactus ;
    ImageView imageLogout , home;
    //ترتيب البوتونز
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_requester);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbar);
         imageLogout=(ImageView)findViewById(R.id.imageLogOut);
         home=(ImageView)findViewById(R.id.imageHome);
        profile=(Button)findViewById(R.id.btn_profile_r);
        req=(Button)findViewById(R.id.btn_req);
        listR=(Button)findViewById(R.id.btn_list_r);
        contactus=(Button)findViewById(R.id.appCompatButton);
        firebaseAuth=FirebaseAuth.getInstance();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent h= new Intent(HomeRequesterActivity.this, HomeRequesterActivity.class);
                startActivity(h);
            }
        });
        imageLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(HomeRequesterActivity.this);
                builder1.setMessage("Are you sure to logout ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                //Alert
                firebaseAuth.signOut();
                Intent main = new Intent(HomeRequesterActivity.this,MainActivity.class);
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
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p= new Intent(HomeRequesterActivity.this,profileRequesterActivity.class);
                startActivity(p);
            }
        });

        req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent r= new Intent(HomeRequesterActivity.this,requestLORActivity.class);
                startActivity(r);
            }
        });

        listR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent l= new Intent(HomeRequesterActivity.this,listReqActivity.class);
                startActivity(l);
            }
        });

        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c= new Intent(HomeRequesterActivity.this,contactusActivity.class);
                startActivity(c);
            }
        });
    }
}
