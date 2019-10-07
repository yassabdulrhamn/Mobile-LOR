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

public class Step1Activity extends AppCompatActivity {
    DatabaseReference dbTemplate ,db ,mDatabase;
    FirebaseAuth firebaseAuth;
    Button next;
    RadioGroup rg1, rg2;
    ImageView imageLogout , home;
    TextView q1,q2;
    String emailRequester, uid,emailIss ,emRe , emIs;
    RadioButton radioButton1, radioButton2, radioButton3, radioButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step1);
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
                Intent h= new Intent(Step1Activity.this, HomeIssuerActivity.class);
                startActivity(h);
            }
        });

        imageLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(Step1Activity.this);
                builder1.setMessage("Are you sure to logout?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Alert
                                firebaseAuth.signOut();
                                Intent main = new Intent(Step1Activity.this,MainActivity.class);
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
        rg1 = (RadioGroup) findViewById(R.id.RG1);
        rg2 = (RadioGroup) findViewById(R.id.RG2);
        q1=(TextView)findViewById(R.id.s1q1);
        q2=(TextView)findViewById(R.id.st1q2);




        radioButton1 = (RadioButton) findViewById(R.id.radio_st1op1);
        radioButton2 = (RadioButton) findViewById(R.id.radio_st1op2);
        radioButton3 = (RadioButton) findViewById(R.id.radio_st2op1);
        radioButton4 = (RadioButton) findViewById(R.id.radio_st2op2);
        dbTemplate= FirebaseDatabase.getInstance().getReference("Template");
        db= FirebaseDatabase.getInstance().getReference();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        dbTemplate.child("Introduction1").child("q").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                q1.setText(s);}
            @Override
            public void onCancelled(DatabaseError databaseError) {}});
        dbTemplate.child("Introduction1").child("op1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                radioButton1.setText(s);}
            @Override
            public void onCancelled(DatabaseError databaseError) {}});
        dbTemplate.child("Introduction1").child("op2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                radioButton2.setText(s);}
            @Override
            public void onCancelled(DatabaseError databaseError) {}});
        dbTemplate.child("Introduction2").child("q").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                q2.setText(s);}
            @Override
            public void onCancelled(DatabaseError databaseError) {}});
        emRe=encodeUserEmail(emailRequester);
        emIs=encodeUserEmail(emailIss);
        db.child("listReq").child(emRe).child(emIs).child("NameRequester").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String n = dataSnapshot.getValue(String.class);
                dbTemplate.child("Introduction2").child("op1").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String s = dataSnapshot.getValue(String.class);
                        radioButton3.setText(n + " "+ s);}
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}});
                dbTemplate.child("Introduction2").child("op2").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String s = dataSnapshot.getValue(String.class);
                        radioButton4.setText(n+ " "+ s);}
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}});}
            @Override
            public void onCancelled(DatabaseError databaseError) {}});
        next=(Button)findViewById(R.id.btn_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton selectRadio1 = (RadioButton) findViewById(rg1.getCheckedRadioButtonId());
                String option1 = selectRadio1.getText().toString();
                RadioButton selectRadio2 = (RadioButton) findViewById(rg2.getCheckedRadioButtonId());
                String option2 = selectRadio2.getText().toString();
                mDatabase.child("LORs").child(emRe).child(emIs).child("Introduction1").setValue(option1);
                mDatabase.child("LORs").child(emRe).child(emIs).child("Introduction2").setValue(option2);
                Intent i= new Intent(Step1Activity.this, Step2Activity.class);
                i.putExtra("EmailRequester",emailRequester);
                startActivity(i);
            }
        });
    }
    private static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }
    }

