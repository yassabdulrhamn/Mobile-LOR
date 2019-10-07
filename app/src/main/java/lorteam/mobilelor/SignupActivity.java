package lorteam.mobilelor;

import android.content.Intent;
import android.graphics.Paint;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private EditText email, password , confirmPass , FName , LName,address,phone  ;
    private Button signup;
    private TextView lable ;
    ImageView imageFAQ;
    String NamePattren;


    //Firebase Variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference databaseReference;

    private FirebaseUser firebaseuser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        final Toolbar toolbar=(Toolbar)findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbar);
        imageFAQ=(ImageView)findViewById(R.id.imageFAQ);
        imageFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imgFAQ=new Intent(SignupActivity.this,listFAQ.class);
                startActivity(imgFAQ);
            }
        });
        email = (EditText) findViewById(R.id.verEmailET);
        password = (EditText) findViewById(R.id.verPassET);
        confirmPass = (EditText) findViewById(R.id.verPassET1);
        FName = (EditText) findViewById(R.id.fn1);
        LName = (EditText) findViewById(R.id.ln1);
        address=(EditText)findViewById(R.id.address);
        phone=(EditText)findViewById(R.id.phone);
        lable = (TextView) findViewById(R.id.lable);

        signup = (Button) findViewById(R.id.btn_create);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String e = email.getText().toString();
                final String pass = password.getText().toString();
                final String pass1 = confirmPass.getText().toString();

                final String fName= FName.getText().toString();
                final String lName = LName.getText().toString();
                final String add = address.getText().toString();
                final String phoneNo = phone.getText().toString();
                NamePattren="[a-zA-Z ]+";

                    int index = 0;
                char ch=   'a';

                int size = e.indexOf('@');
                boolean flag = true;


                if (!e.isEmpty()) {
                    index = e.indexOf('@');

                    ch = e.charAt(0);


                    for (int i = 0; i < index; i++) {

                        ch = e.charAt(i);
                        if (ch >= '0' && ch <= '9') {
                            ch = e.charAt(i);
                        } else {
                            i = e.indexOf('@');
                            flag = false;
                        }
                    }


                }













                if (e.isEmpty()) {
                    toastMessage("Enter E-Mail");

                }



                else  if ((!e.contains("@")) ||(!e.contains(".sa")) ){
                    toastMessage("invalid email");

                }

                else   if ((e.substring(e.indexOf('@')).equals("@student.ksu.edu.sa")) && (!flag)) {
                    toastMessage("not ksu student email   there is letter");
                }

                else    if ((e.substring(e.indexOf('@')).equals("@student.ksu.edu.sa")) && (size!=9)) {
                    toastMessage("not ksu student email  not 9 numbers");
                }
                else if ((!e.substring(e.indexOf('@')).equalsIgnoreCase("@student.ksu.edu.sa")) && (!e.substring(e.indexOf('@')).equalsIgnoreCase("@ksu.edu.sa")))  {
                    toastMessage("Enter ksu");

                }

                else if ((e.substring(e.indexOf('@')).equalsIgnoreCase("@ksu.edu.sa")) && (flag==true))  {
                    toastMessage("not a ksu staff");

                }
                else if (pass.isEmpty()) {
                    toastMessage("Enter Password");
                } else if (pass.length()<9) {
                    toastMessage("the password has to be more than 9 char");
                }

                else   if (pass1.isEmpty()) {
                    toastMessage("Enter Password");
                }






                else if (!pass.equals(pass1)){
                    toastMessage("different password ");}

                else   if (fName.isEmpty()) {
                    toastMessage("Enter your first name");
                }

                else   if (lName.isEmpty()) {
                    toastMessage("Enter your last name");
                }
                else if(!lName.matches(NamePattren)){

                    toastMessage("Please enter correct name");}

                else if(!fName.matches(NamePattren)){

                    toastMessage("Please enter correct name");}






                else

                {
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(e, pass)
                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    databaseReference= FirebaseDatabase.getInstance().getReference();
                                    if (add.length() == 0)
                                        address.setText("");
                                    if (phoneNo.length() == 0)
                                        phone.setText("");
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                        if(e.substring(e.indexOf('@')).equals("@student.ksu.edu.sa")){
                                            databaseReference.child("Requester").child(uid).child("email").setValue(e);
                                            databaseReference.child("Requester").child(uid).child("password").setValue(pass);
                                            databaseReference.child("Requester").child(uid).child("firstName").setValue(fName);
                                            databaseReference.child("Requester").child(uid).child("lastName").setValue(lName);
                                            databaseReference.child("Requester").child(uid).child("address").setValue(add);
                                            databaseReference.child("Requester").child(uid).child("phoneNo").setValue(phoneNo);}
                                        if(e.substring(e.indexOf('@')).equals("@ksu.edu.sa")){
                                        databaseReference.child("Issuer").child(uid).child("email").setValue(e);
                                        databaseReference.child("Issuer").child(uid).child("password").setValue(pass);
                                        databaseReference.child("Issuer").child(uid).child("firstName").setValue(fName);
                                        databaseReference.child("Issuer").child(uid).child("lastName").setValue(lName);
                                        databaseReference.child("Issuer").child(uid).child("address").setValue(add);
                                        databaseReference.child("Issuer").child(uid).child("phoneNo").setValue(phoneNo);}
                                        user.sendEmailVerification();
                                        toastMessage("create account successfully ! Please check your E-mail to verified your account  ");

                                    } else {
                                        mAuth.signInWithEmailAndPassword(e, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                                    if (user.isEmailVerified()){
                                                        toastMessage("THIS EMAIL ALREADY REGISTER AND VARIED");
                                                    }

                                                    else
                                                    if (!user.isEmailVerified()){
                                                        toastMessage("confirm your email");

                                                        user.sendEmailVerification();
                                                    }

                                                }
                                                if (!task.isSuccessful()) {



                                                }
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                if (e instanceof FirebaseAuthInvalidUserException) {
                                                    Toast.makeText(SignupActivity.this, "This User Not Found , Create A New Account", Toast.LENGTH_SHORT).show();
                                                }
                                                if (e instanceof FirebaseAuthInvalidCredentialsException) {

                                                    Toast.makeText(SignupActivity.this, "The Password Is Invalid, Please Try Valid Password", Toast.LENGTH_SHORT).show();
                                                    lable.setVisibility(View.VISIBLE);

                                                    lable.setPaintFlags(lable.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                                                    lable.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                                        }
                                                    });
                                                }
                                                if (e instanceof FirebaseNetworkException) {
                                                    Toast.makeText(SignupActivity.this, "Please Check Your Connection", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });



                                    }//Else

                                }
                            });
                }





            }
        });

    }
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
