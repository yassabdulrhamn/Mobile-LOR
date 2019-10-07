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

public class EditConActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase ;
    Button update;
    ImageView imageLogout , home;
    EditText title7,s7op1,s7op2,s7op3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_con);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbar);
        imageLogout=(ImageView)findViewById(R.id.imageLogOut);
        home=(ImageView)findViewById(R.id.imageHome);
        update=(Button)findViewById(R.id.btn_update3);
        s7op1=(EditText)findViewById(R.id.s7op1);
        s7op2=(EditText)findViewById(R.id.s7op2);
        s7op3=(EditText)findViewById(R.id.s7op3);
        mDatabase= FirebaseDatabase.getInstance().getReference("Template");
        firebaseAuth= FirebaseAuth.getInstance();
        title7=(EditText)findViewById(R.id.S7);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent h= new Intent(EditConActivity.this, HomeAdminActivity.class);
                startActivity(h);
            }
        });
        imageLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Alert
                AlertDialog.Builder builder1 = new AlertDialog.Builder(EditConActivity.this);
                builder1.setMessage("Are you sure to logout?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Alert
                                firebaseAuth.signOut();
                                Intent main = new Intent(EditConActivity.this,MainActivity.class);
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
        mDatabase.child("conclusion").child("op1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s= dataSnapshot.getValue(String.class);
                s7op1.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("conclusion").child("op2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s= dataSnapshot.getValue(String.class);
                s7op2.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("conclusion").child("op3").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s= dataSnapshot.getValue(String.class);
                s7op3.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(EditConActivity.this);
                builder1.setMessage("Are you sure to logout?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String t7,op17,op18,op19;

                                t7=title7.getText().toString().trim();
                                op17=s7op1.getText().toString().trim();
                                op18=s7op2.getText().toString().trim();
                                op19=s7op3.getText().toString().trim();



                                mDatabase.child("conclusion").child("op1").setValue(op17);
                                mDatabase.child("conclusion").child("op2").setValue(op18);
                                mDatabase.child("conclusion").child("op3").setValue(op19);

                                Toast.makeText(getApplicationContext(),"Update ",Toast.LENGTH_LONG).show();
                                Intent i = new Intent(EditConActivity.this,EditTemplateActivity.class);
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


    }
}
