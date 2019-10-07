package lorteam.mobilelor;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditAccountActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase ;
    Button update;
    ImageView imageLogout , home;
    EditText e,p;
    String emailPattern;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbar);
        imageLogout=(ImageView)findViewById(R.id.imageLogOut);
        home=(ImageView)findViewById(R.id.imageHome);
        update=(Button)findViewById(R.id.btn_saveAcc);
        e=(EditText)findViewById(R.id.email);
        p=(EditText)findViewById(R.id.pass);
         emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        firebaseAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent h= new Intent(EditAccountActivity.this, HomeAdminActivity.class);
                startActivity(h);
            }
        });
        imageLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(EditAccountActivity.this);
                builder1.setMessage("Are you sure to logout ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Alert
                                firebaseAuth.signOut();
                                Intent main = new Intent(EditAccountActivity.this,MainActivity.class);
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

        mDatabase.child("Admin").child("email").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String s = dataSnapshot.getValue(String.class);

                e.setText(s);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("Admin").child("password").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String s = dataSnapshot.getValue(String.class);

                p.setText(s);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(EditAccountActivity.this);
                builder1.setMessage("Are you sure  ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String email, pass;
                                email=e.getText().toString();
                                pass=p.getText().toString();
                                if (email.matches(emailPattern))
                                {mDatabase.child("Admin").child("email").setValue(email);

                                    mDatabase.child("Admin").child("password").setValue(pass);
                                    Intent i4= new Intent(EditAccountActivity.this , HomeAdminActivity.class);
                                    startActivity(i4);}
                                else
                                {Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();}
                                dialog.cancel();}
                        });
                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();}});

    }
}
