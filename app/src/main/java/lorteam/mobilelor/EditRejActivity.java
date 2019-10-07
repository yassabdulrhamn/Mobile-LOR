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

public class EditRejActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    ImageView imageLogout , home;
    EditText rej1,rej2,rej3;
    Button update;
    DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_rej);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbar);
        imageLogout=(ImageView)findViewById(R.id.imageLogOut);
        home=(ImageView)findViewById(R.id.imageHome);
        update=(Button)findViewById(R.id.btn_update4);
        rej1=(EditText)findViewById(R.id.r1);
        rej2=(EditText)findViewById(R.id.r2);
        rej3=(EditText)findViewById(R.id.r3);
        mDatabase= FirebaseDatabase.getInstance().getReference("Reject");

        mDatabase.child("op1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s= dataSnapshot.getValue(String.class);
                rej1.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("op2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s= dataSnapshot.getValue(String.class);
                rej2.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("op3").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s= dataSnapshot.getValue(String.class);
                rej3.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(EditRejActivity.this);
                builder1.setMessage("Are you sure ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String r1=rej1.getText().toString().trim();
                                String r2=rej2.getText().toString().trim();
                                String r3=rej3.getText().toString().trim();

                                mDatabase.child("op1").setValue(r1);
                                mDatabase.child("op2").setValue(r2);
                                mDatabase.child("op3").setValue(r3);

                                Toast.makeText(getApplicationContext(),"Update ",Toast.LENGTH_LONG).show();
                                Intent i = new Intent(EditRejActivity.this,EditTemplateActivity.class);
                                startActivity(i);
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

        firebaseAuth= FirebaseAuth.getInstance();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent h= new Intent(EditRejActivity.this, HomeAdminActivity.class);
                startActivity(h);
            }
        });
        imageLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(EditRejActivity.this);
                builder1.setMessage("Are you sure to logout ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                firebaseAuth.signOut();
                                Intent main = new Intent(EditRejActivity.this,MainActivity.class);
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
    }
}
