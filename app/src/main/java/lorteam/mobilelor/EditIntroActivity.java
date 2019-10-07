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

public class EditIntroActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase ;

    ImageView imageLogout , home;
    Button update;
    EditText title1,title2;
    EditText s1op1,s1op2,s2op1,s2op2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_intro);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbar);
        imageLogout=(ImageView)findViewById(R.id.imageLogOut);
        home=(ImageView)findViewById(R.id.imageHome);
        update=(Button)findViewById(R.id.btn_update1);
        s1op1=(EditText)findViewById(R.id.s1op1);
        s1op2=(EditText)findViewById(R.id.s1op2);
        s2op1=(EditText)findViewById(R.id.s2op1);
        s2op2=(EditText)findViewById(R.id.s2op2);
        title1=(EditText)findViewById(R.id.S1);
        title2=(EditText)findViewById(R.id.S2);
        firebaseAuth= FirebaseAuth.getInstance();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent h= new Intent(EditIntroActivity.this, HomeAdminActivity.class);
                startActivity(h);
            }
        });
        imageLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Alert
                AlertDialog.Builder builder1 = new AlertDialog.Builder(EditIntroActivity.this);
                builder1.setMessage("Are you sure to logout?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Alert
                                firebaseAuth.signOut();
                                Intent main = new Intent(EditIntroActivity.this,MainActivity.class);
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

        mDatabase= FirebaseDatabase.getInstance().getReference("Template");

        mDatabase.child("Introduction1").child("q").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s= dataSnapshot.getValue(String.class);
                title1.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mDatabase.child("Introduction1").child("op1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s= dataSnapshot.getValue(String.class);
                s1op1.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("Introduction1").child("op2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s= dataSnapshot.getValue(String.class);
                s1op2.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mDatabase.child("Introduction2").child("q").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s= dataSnapshot.getValue(String.class);
                title2.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mDatabase.child("Introduction2").child("op1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s= dataSnapshot.getValue(String.class);
                s2op1.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("Introduction2").child("op2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s= dataSnapshot.getValue(String.class);
                s2op2.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(EditIntroActivity.this);
                builder1.setMessage("Are you sure to logout?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String t1, t2, op1, op2, op3, op4;
                                t1 = title1.getText().toString().trim();
                                t2 = title2.getText().toString().trim();
                                op1 = s1op1.getText().toString().trim();
                                op2 = s1op2.getText().toString().trim();
                                op3 = s2op1.getText().toString().trim();
                                op4 = s2op2.getText().toString().trim();
                                mDatabase.child("Introduction1").child("q").setValue(t1);
                                mDatabase.child("Introduction1").child("op1").setValue(op1);
                                mDatabase.child("Introduction1").child("op2").setValue(op2);
                                mDatabase.child("Introduction2").child("q").setValue(t2);
                                mDatabase.child("Introduction2").child("op1").setValue(op3);
                                mDatabase.child("Introduction2").child("op2").setValue(op4);
                                Toast.makeText(getApplicationContext(),"Update ",Toast.LENGTH_LONG).show();
                                Intent i = new Intent(EditIntroActivity.this,EditTemplateActivity.class);
                                startActivity(i);
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
                alert11.show();
            }
        });

    }
}
