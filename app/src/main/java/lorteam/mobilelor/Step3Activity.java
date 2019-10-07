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

public class Step3Activity extends AppCompatActivity {
    DatabaseReference dbTemplate ,db ,mDatabase , dbIssuer;
    FirebaseAuth firebaseAuth;
    Button next;
    RadioGroup rg1;
    ImageView imageLogout , home;
    EditText nameIssuer, emailIssuer, phoneIssuer, addressIssuer;
    String emailRequester, uid,emailIss ,emRe, emIs ,s;
    RadioButton rb1,rb2,rb3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step3);
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
                Intent h= new Intent(Step3Activity.this, HomeIssuerActivity.class);
                startActivity(h);
            }
        });

        imageLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(Step3Activity.this);
                builder1.setMessage("Are you sure to logout?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Alert
                                firebaseAuth.signOut();
                                Intent main = new Intent(Step3Activity.this,MainActivity.class);
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

        rg1=(RadioGroup)findViewById(R.id.rgcon);
        rb1=(RadioButton)findViewById(R.id.radio_1);
        rb2=(RadioButton)findViewById(R.id.radio_2);
        rb3=(RadioButton)findViewById(R.id.radio_3);
        nameIssuer=(EditText)findViewById(R.id.name);
        emailIssuer=(EditText)findViewById(R.id.email);
        phoneIssuer=(EditText)findViewById(R.id.phoneNo);
        addressIssuer=(EditText)findViewById(R.id.Address);
        mDatabase= FirebaseDatabase.getInstance().getReference("Template");
        db=FirebaseDatabase.getInstance().getReference("listReq");
        dbTemplate=FirebaseDatabase.getInstance().getReference();
        dbIssuer=FirebaseDatabase.getInstance().getReference("Issuer");
        next=(Button)findViewById(R.id.btn_next3);
        emRe=encodeUserEmail(emailRequester);
        emIs=encodeUserEmail(emailIss);
        db.child(emRe).child(emIs).child("purpose").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String d = dataSnapshot.getValue(String.class);
                if (d.equals("Business")) {

                    mDatabase.child("conclusion").child("op1").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String s = dataSnapshot.getValue(String.class);


                            rb1.setText(s);
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    mDatabase.child("conclusion").child("op3").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String s = dataSnapshot.getValue(String.class);


                          rb2.setText(s);
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

               else if ( d.equals("Academic"))  {

                    mDatabase.child("conclusion").child("op1").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String s = dataSnapshot.getValue(String.class);


                            rb1.setText(s);
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    mDatabase.child("conclusion").child("op2").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String s = dataSnapshot.getValue(String.class);


                            rb2.setText(s);
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
                else{
                    mDatabase.child("conclusion").child("op1").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String s = dataSnapshot.getValue(String.class);


                            rb1.setText(s);
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    mDatabase.child("conclusion").child("op2").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String s = dataSnapshot.getValue(String.class);


                            rb2.setText(s);
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    mDatabase.child("conclusion").child("op3").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String s = dataSnapshot.getValue(String.class);


                            rb3.setText(s);
                            rb3.setVisibility(View.VISIBLE);
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dbIssuer.child(uid).child("firstName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                 s = dataSnapshot.getValue(String.class);

                nameIssuer.setText(s);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dbIssuer.child(uid).child("lastName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String s1 = dataSnapshot.getValue(String.class);

                nameIssuer.setText(s+ " "+ s1);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dbIssuer.child(uid).child("email").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String s = dataSnapshot.getValue(String.class);

                emailIssuer.setText(s);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dbIssuer.child(uid).child("address").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String s = dataSnapshot.getValue(String.class);

                addressIssuer.setText(s);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton selectRadio5 = (RadioButton) findViewById(rg1.getCheckedRadioButtonId());
                String option5= selectRadio5.getText().toString();
                dbTemplate.child("LORs").child(emRe).child(emIs).child("conclusion").setValue(option5);
                String n , e, a, p;
                n=nameIssuer.getText().toString();
                e=emailIssuer.getText().toString();
                a=addressIssuer.getText().toString();
                p=phoneIssuer.getText().toString();
                dbTemplate.child("LORs").child(emRe).child(emIs).child("issuerName").setValue(n);
                dbTemplate.child("LORs").child(emRe).child(emIs).child("issuerEmail").setValue(e);
                dbTemplate.child("LORs").child(emRe).child(emIs).child("issuerAddress").setValue(a);
                dbTemplate.child("LORs").child(emRe).child(emIs).child("issuerPhone").setValue(p);
                Intent i4= new Intent(Step3Activity.this , Step4Activity.class);
                i4.putExtra("EmailRequester",emailRequester);
                startActivity(i4);


            }
        });


    }
    private static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }
}
