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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Step2Activity extends AppCompatActivity {
    DatabaseReference dbTemplate ,db ,mDatabase;
    FirebaseAuth firebaseAuth;
    Button next;
    RadioGroup rg1, rg2 ,rg3, rg4;
    ImageView imageLogout , home;
    TextView q1,q2, q3,q4;
    String emailRequester, uid,emailIss ,emRe , emIs;
    String s1,s2,s3,s4,s5,s6,s,s7,s8,s9,s10,s11,s12;
    RadioButton radioButton1, radioButton2, radioButton3, radioButton4 , radioButton5,radioButton6,radioButton7,radioButton8,
    radioButton9,radioButton10,radioButton11,radioButton12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step2);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbarHeader);
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            emailRequester= null;
        } else {
            emailRequester= extras.getString("EmailRequester");
        }
        setSupportActionBar(toolbar);
        imageLogout=(ImageView)findViewById(R.id.imageLogOut);
        home=(ImageView)findViewById(R.id.imageHome);
        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            uid= user.getUid();
            emailIss=user.getEmail();
        }

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent h= new Intent(Step2Activity.this, HomeIssuerActivity.class);
                startActivity(h);
            }
        });

        imageLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(Step2Activity.this);
                builder1.setMessage("Are you sure to logout?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Alert
                                firebaseAuth.signOut();
                                Intent main = new Intent(Step2Activity.this,MainActivity.class);
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

        rg1=(RadioGroup)findViewById(R.id.RGQ1);
        rg2=(RadioGroup)findViewById(R.id.RGQ2);
        rg3=(RadioGroup)findViewById(R.id.RGQ3);
        rg4=(RadioGroup)findViewById(R.id.RGQ4);
        radioButton1=(RadioButton)findViewById(R.id.radio1_1);
        radioButton2=(RadioButton)findViewById(R.id.radio1_2);
        radioButton3=(RadioButton)findViewById(R.id.radio1_3);
        radioButton4=(RadioButton)findViewById(R.id.radio2_1);
        radioButton5=(RadioButton)findViewById(R.id.radio2_2);
        radioButton6=(RadioButton)findViewById(R.id.radio2_3);
        radioButton7=(RadioButton)findViewById(R.id.radio3_1);
        radioButton8=(RadioButton)findViewById(R.id.radio3_2);
        radioButton9=(RadioButton)findViewById(R.id.radio3_3);
        radioButton10=(RadioButton)findViewById(R.id.radio4_1);
        radioButton11=(RadioButton)findViewById(R.id.radio4_2);
        radioButton12=(RadioButton)findViewById(R.id.radio4_3);


        q1=(TextView)findViewById(R.id.q1);
        q2=(TextView)findViewById(R.id.q2);
        q3=(TextView)findViewById(R.id.q3);
        q4=(TextView)findViewById(R.id.q4);

        next=(Button)findViewById(R.id.btn_next2);
        db= FirebaseDatabase.getInstance().getReference("Template");

        db.child("body1").child("q").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String s = dataSnapshot.getValue(String.class);

                q1.setText(s);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        db.child("body1").child("op1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                s1 = dataSnapshot.getValue(String.class);


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        db.child("body1").child("op2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                s2 = dataSnapshot.getValue(String.class);



            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        db.child("body1").child("op3").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                s3 = dataSnapshot.getValue(String.class);



            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        db.child("body2").child("q").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String s = dataSnapshot.getValue(String.class);

                q2.setText(s);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        db.child("body2").child("op1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                s4 = dataSnapshot.getValue(String.class);



            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        db.child("body2").child("op2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                s5 = dataSnapshot.getValue(String.class);



            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        db.child("body2").child("op3").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                s6 = dataSnapshot.getValue(String.class);



            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        db.child("body3").child("q").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String s = dataSnapshot.getValue(String.class);

                q3.setText(s);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        db.child("body3").child("op1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                s7 = dataSnapshot.getValue(String.class);



            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        db.child("body3").child("op2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                s8 = dataSnapshot.getValue(String.class);



            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        db.child("body3").child("op3").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                s9 = dataSnapshot.getValue(String.class);


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        db.child("body4").child("q").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String s = dataSnapshot.getValue(String.class);

                q4.setText(s);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        db.child("body4").child("op1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                s10 = dataSnapshot.getValue(String.class);



            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        db.child("body4").child("op2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                s11 = dataSnapshot.getValue(String.class);



            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        db.child("body4").child("op3").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                s12 = dataSnapshot.getValue(String.class);



            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase=FirebaseDatabase.getInstance().getReference();
                emRe=encodeUserEmail(emailRequester);
                emIs=encodeUserEmail(emailIss);
                RadioButton selectRadio = (RadioButton) findViewById(rg1.getCheckedRadioButtonId());
                String option4_1 = selectRadio.getText().toString();

                if(option4_1.equals("Outstanding")){

                    mDatabase.child("LORs").child(emRe).child(emIs).child("body1").setValue(s1);
                }

                else if(option4_1.equals("Average")){
                    mDatabase.child("LORs").child(emRe).child(emIs).child("body1").setValue(s2);

                }
                else {
                    mDatabase.child("LORs").child(emRe).child(emIs).child("body1").setValue(s3);

                }

                RadioButton selectRadio2 = (RadioButton) findViewById(rg2.getCheckedRadioButtonId());
                String option4_2 = selectRadio2.getText().toString();

                if(option4_2.equals("Outstanding")){
                    mDatabase.child("LORs").child(emRe).child(emIs).child("body2").setValue(s4);}
                else if(option4_2.equals("Average")){
                    mDatabase.child("LORs").child(emRe).child(emIs).child("body2").setValue(s5);}
                else {mDatabase.child("LORs").child(emRe).child(emIs).child("body2").setValue(s6);}
                RadioButton selectRadio3 = (RadioButton) findViewById(rg3.getCheckedRadioButtonId());
                String option4_3 = selectRadio3.getText().toString();
                if(option4_3.equals("Outstanding")){
                    mDatabase.child("LORs").child(emRe).child(emIs).child("body3").setValue(s7);}
                else if(option4_3.equals("Average")){
                    mDatabase.child("LORs").child(emRe).child(emIs).child("body3").setValue(s8);}
                else {
                    mDatabase.child("LORs").child(emRe).child(emIs).child("body3").setValue(s9);}
                RadioButton selectRadio4 = (RadioButton) findViewById(rg4.getCheckedRadioButtonId());
                String option4_4= selectRadio4.getText().toString();
                if(option4_4.equals("Outstanding")){
                    mDatabase.child("LORs").child(emRe).child(emIs).child("body4").setValue(s10);}
                else if(option4_4.equals("Average")){
                    mDatabase.child("LORs").child(emRe).child(emIs).child("body4").setValue(s11);}
                else {
                    mDatabase.child("LORs").child(emRe).child(emIs).child("body4").setValue(s12);}
                Intent st3= new Intent(Step2Activity.this, Step3Activity.class);
                st3.putExtra("EmailRequester",emailRequester);
                startActivity(st3);

            }
        });


    }
    private static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }
}
