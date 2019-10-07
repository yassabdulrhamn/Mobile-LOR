package lorteam.mobilelor;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference ;
    FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference databaseReferenceAdmin;

    EditText email;
    EditText password;

    Button login ;
    TextView signup;
    TextView forgotPassword ;
    String emailAdmin, passwordAdmin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = (EditText)findViewById(R.id.input_email);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbarHeader);

        ImageView imageFAQ=(ImageView)findViewById(R.id.imageFAQ);
        setSupportActionBar(toolbar);
        password = (EditText)findViewById(R.id.input_password);
        imageFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imgFAQ=new Intent(MainActivity.this,listFAQ.class);
                startActivity(imgFAQ);
            }
        });
        login = (Button)findViewById(R.id.btn_login);

        signup = (TextView)findViewById(R.id.link_signup);
        forgotPassword = (TextView)findViewById(R.id.link_forgotPassword);


        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();

         databaseReferenceAdmin = FirebaseDatabase.getInstance().getReference("Admin");
         databaseReferenceAdmin.child("email").addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
        emailAdmin= dataSnapshot.getValue(String.class);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
        });
         databaseReferenceAdmin.child("password").addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
        passwordAdmin= dataSnapshot.getValue(String.class);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
        });


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                //   mAuth.createUserWithEmailAndPassword("1223456788@student.ksu.edu.sa", "123456");
                if (user != null) {
                    // User is signed in


                } else {
                    // User is signed out


                }

            }
        };



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String e = email.getText().toString();
                String p = password.getText().toString();



                if (e.isEmpty()) {
                    toastMessage("Please Enter E-Mail");

                } else if (p.isEmpty()) {
                    toastMessage("Please Enter Password");
                } else {

                    if((e.equals(emailAdmin)) && (p.equals(passwordAdmin))){
                        firebaseAuth.signInWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent i = new Intent(MainActivity.this, HomeAdminActivity.class);
                                    startActivity(i);

                                }

                            }
                        });

                    }


                    else {




                        firebaseAuth.signInWithEmailAndPassword(e, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    if(e.substring(e.indexOf('@')).equals("@student.ksu.edu.sa")){
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                     //  if (user.isEmailVerified()) {
                                            Intent i = new Intent(MainActivity.this,HomeRequesterActivity.class);
                                        startActivity(i);
                                    //  }

                                    //  else if (!user.isEmailVerified()) {
                                     //    toastMessage("confirm your email");
                                    //       user.sendEmailVerification();}
                                    }

                                    if(e.substring(e.indexOf('@')).equals("@ksu.edu.sa")){
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                      // if (user.isEmailVerified()) {
                                            Intent i = new Intent(MainActivity.this, HomeIssuerActivity.class);
                                            startActivity(i);
                                      //  }

                                    // else if (!user.isEmailVerified()) {
                                        //  toastMessage("confirm your email");
                                        //  user.sendEmailVerification();
                                     // }

                                    }}
                                if (!task.isSuccessful()) {

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (e instanceof FirebaseAuthInvalidUserException) {
                             Toast.makeText(MainActivity.this, "This User Not Found , Create A New Account", Toast.LENGTH_SHORT).show();
                                }
                                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                     Toast.makeText(MainActivity.this, "The Password Is Invalid, Please Try Valid Password", Toast.LENGTH_SHORT).show();
                                }
                                if (e instanceof FirebaseNetworkException) {
                            Toast.makeText(MainActivity.this, "Please Check Your Connection", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(i);


            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i2 = new Intent(MainActivity.this, ForgotPasswordActivity.class);
                startActivity(i2);


            }
        });







    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
