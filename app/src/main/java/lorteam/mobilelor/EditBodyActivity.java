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

public class EditBodyActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase ;
    Button update;
    ImageView imageLogout , home;
    EditText title3,title4,title5,title6,s3op1,s3op2,s3op3,s4op1,s4op2,s4op3,s5op1,s5op2,s5op3,s6op1,s6op2,s6op3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_body);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbar);
        imageLogout=(ImageView)findViewById(R.id.imageLogOut);
        home=(ImageView)findViewById(R.id.imageHome);
        update=(Button)findViewById(R.id.btn_update2);
        title3=(EditText)findViewById(R.id.S3);
        title4=(EditText)findViewById(R.id.S4);
        title5=(EditText)findViewById(R.id.S5);
        title6=(EditText)findViewById(R.id.S6);
        s3op1=(EditText)findViewById(R.id.s3op1);
        s3op2=(EditText)findViewById(R.id.s3op2);
        s3op3=(EditText)findViewById(R.id.s3op3);
        s4op1=(EditText)findViewById(R.id.s4op1);
        s4op2=(EditText)findViewById(R.id.s4op2);
        s4op3=(EditText)findViewById(R.id.s4op3);
        s5op1=(EditText)findViewById(R.id.s5op1);
        s5op2=(EditText)findViewById(R.id.s5op2);
        s5op3=(EditText)findViewById(R.id.s5op3);
        s6op1=(EditText)findViewById(R.id.s6op1);
        s6op2=(EditText)findViewById(R.id.s6op2);
        s6op3=(EditText)findViewById(R.id.s6op3);
        mDatabase= FirebaseDatabase.getInstance().getReference("Template");

        firebaseAuth= FirebaseAuth.getInstance();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent h= new Intent(EditBodyActivity.this, HomeAdminActivity.class);
                startActivity(h);
            }
        });
        imageLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(EditBodyActivity.this);
                builder1.setMessage("Are you sure to logout?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Alert
                                firebaseAuth.signOut();
                                Intent main = new Intent(EditBodyActivity.this,MainActivity.class);
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

        mDatabase.child("body1").child("q").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s= dataSnapshot.getValue(String.class);
                title3.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mDatabase.child("body1").child("op1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s= dataSnapshot.getValue(String.class);
                s3op1.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("body1").child("op2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s= dataSnapshot.getValue(String.class);
                s3op2.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("body1").child("op3").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s= dataSnapshot.getValue(String.class);
                s3op3.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mDatabase.child("body2").child("q").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s= dataSnapshot.getValue(String.class);
                title4.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mDatabase.child("body2").child("op1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s= dataSnapshot.getValue(String.class);
                s4op1.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("body2").child("op2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s= dataSnapshot.getValue(String.class);
                s4op2.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("body2").child("op3").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s= dataSnapshot.getValue(String.class);
                s4op3.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("body3").child("q").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s= dataSnapshot.getValue(String.class);
                title5.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mDatabase.child("body3").child("op1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s= dataSnapshot.getValue(String.class);
                s5op1.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("body3").child("op2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s= dataSnapshot.getValue(String.class);
                s5op2.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("body3").child("op3").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s= dataSnapshot.getValue(String.class);
                s5op3.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mDatabase.child("body4").child("q").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s= dataSnapshot.getValue(String.class);
                title6.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mDatabase.child("body4").child("op1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s= dataSnapshot.getValue(String.class);
                s6op1.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("body4").child("op2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s= dataSnapshot.getValue(String.class);
                s6op2.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("body4").child("op3").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s= dataSnapshot.getValue(String.class);
                s6op3.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(EditBodyActivity.this);
                builder1.setMessage("Are you sure to logout?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                String t3,t4,t5,t6,op5,op6,op7,op8,op9,op10,op11,op12,op13,op14,op15,op16;
                                t3=title3.getText().toString().trim();
                                t4=title4.getText().toString().trim();
                                t5=title5.getText().toString().trim();
                                t6=title6.getText().toString().trim();
                                op5=s3op1.getText().toString().trim();
                                op6=s3op2.getText().toString().trim();
                                op7=s3op3.getText().toString().trim();
                                op8=s4op1.getText().toString().trim();
                                op9=s4op2.getText().toString().trim();
                                op10=s4op3.getText().toString().trim();
                                op11=s5op1.getText().toString().trim();
                                op12=s5op2.getText().toString().trim();
                                op13=s5op3.getText().toString().trim();
                                op14=s6op1.getText().toString().trim();
                                op15=s6op2.getText().toString().trim();
                                op16=s6op3.getText().toString().trim();
                                mDatabase.child("body1").child("q").setValue(t3);
                                mDatabase.child("body1").child("op1").setValue(op5);
                                mDatabase.child("body1").child("op2").setValue(op6);
                                mDatabase.child("body1").child("op3").setValue(op7);
                                mDatabase.child("body2").child("q").setValue(t4);
                                mDatabase.child("body2").child("op1").setValue(op8);
                                mDatabase.child("body2").child("op2").setValue(op9);
                                mDatabase.child("body2").child("op3").setValue(op10);
                                mDatabase.child("body3").child("q").setValue(t5);
                                mDatabase.child("body3").child("op1").setValue(op11);
                                mDatabase.child("body3").child("op2").setValue(op12);
                                mDatabase.child("body3").child("op3").setValue(op13);
                                mDatabase.child("body4").child("q").setValue(t6);
                                mDatabase.child("body4").child("op1").setValue(op14);
                                mDatabase.child("body4").child("op2").setValue(op15);
                                mDatabase.child("body4").child("op3").setValue(op16);


                                Toast.makeText(getApplicationContext(),"Update ",Toast.LENGTH_LONG).show();
                                Intent i = new Intent(EditBodyActivity.this,EditTemplateActivity.class);
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
