package lorteam.mobilelor;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EditProfileIActivity extends AppCompatActivity {
    DatabaseReference dbProfile;
    FirebaseAuth firebaseAuth;
    EditText fname, lname , address, phoneNo , pass , rePass;
    ImageView imageLogout , home;
    Button save,delete;

    String uid , emailUser ,e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_i);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbar);
        imageLogout=(ImageView)findViewById(R.id.imageLogOut);
        home=(ImageView)findViewById(R.id.imageHome);
        firebaseAuth= FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            uid= user.getUid();
            emailUser= user.getEmail();

        }

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent h= new Intent(EditProfileIActivity.this, HomeIssuerActivity.class);
                startActivity(h);
            }
        });

        imageLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(EditProfileIActivity.this);
                builder1.setMessage("Are you sure ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Alert
                                firebaseAuth.signOut();
                                Intent main = new Intent(EditProfileIActivity.this,MainActivity.class);
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


        fname=(EditText)findViewById(R.id.texFirstNameI);
        lname=(EditText) findViewById(R.id.texLastNameI);
        address=(EditText) findViewById(R.id.texAddressI);
        phoneNo=(EditText) findViewById(R.id.texPhoneI);
        pass=(EditText) findViewById(R.id.texPasswordI);
        rePass=(EditText) findViewById(R.id.texRePasswordI);
        save=(Button)findViewById(R.id.butSubmitI);
        delete=(Button)findViewById(R.id.butDeleteI);
        dbProfile= FirebaseDatabase.getInstance().getReference("Issuer");
        dbProfile.child(uid).child("firstName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String n= dataSnapshot.getValue(String.class);
                fname.setText(n);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






        dbProfile.child(uid).child("lastName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s= dataSnapshot.getValue(String.class);
                lname.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        dbProfile.child(uid).child("password").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String e= dataSnapshot.getValue(String.class);
                pass.setText(e);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dbProfile.child(uid).child("password").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                e= dataSnapshot.getValue(String.class);
                rePass.setText(e);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dbProfile.child(uid).child("address").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String a= dataSnapshot.getValue(String.class);
                address.setText(a);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dbProfile.child(uid).child("phoneNo").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ph= dataSnapshot.getValue(String.class);
                phoneNo.setText(ph);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        save.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                int m=6;

                String p2 = fname.getText().toString();
                if (!nAmeCheck(p2) || p2.length()<2) {
                    if(!nAmeCheck(p2) && p2.length()<2)
                    {
                        fname.setError("Only letters and two charat or more");
                    }
                    else if(!nAmeCheck(p2))
                    {
                        fname.setError("Only letters");
                    }
                    else
                    {
                        fname.setError("two charat or more");
                    }
                    m=-1;
                }
                String p3 = lname.getText().toString();
                if (!nAmeCheck(p3) || p3.length()<2) {
                    if(!nAmeCheck(p3) && p3.length()<2)
                    {
                        lname.setError("Only letters and two charat or more");
                    }
                    else if(!nAmeCheck(p3))
                    {
                        lname.setError("Only letters");
                    }
                    else
                    {
                        lname.setError("two charat or more");
                    }
                    m=-1;
                }
                String p5 = rePass.getText().toString();
                if(p5.isEmpty() || p5.length()<6)
                {
                    if(p5.isEmpty() && p5.length()<6)
                    {
                        rePass.setError("Enter a password from more than 5 charat");
                    }
                    else if (p5.length()<6)
                    {
                        rePass.setError("Must be more than 5 charat");
                    }
                    m=-1;
                }

                String p6 = pass.getText().toString();
                if(!p6.matches(p5))
                {
                    pass.setError("Dose not match");
                    m=-1;
                }
                String phoneCheck = phoneNo.getText().toString();
                if (!NumbCheck(phoneNo.getText().toString()) || phoneCheck.toString().length()!=10) {
                    //toastMessage(Integer.toString(phoneCheck.toString().length()));
                    if (phoneCheck.length() != 10 && !NumbCheck(phoneNo.getText().toString()))
                        phoneNo.setError("10 digits And no letters");
                    else if (phoneCheck.length() == 10)
                        phoneNo.setError("Only digits");
                    else
                        phoneNo.setError("10 digits Only");

                    m=-1;
                }

                if(m==6)
                {


                    AlertDialog.Builder builder1 = new AlertDialog.Builder(EditProfileIActivity.this);
                    builder1.setMessage("Are you sure ?");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //Alert
                                    String Fname, Lname, password, rePassword, add, ph;
                                    Fname = fname.getText().toString();
                                    Lname = lname.getText().toString();
                                    password = pass.getText().toString();
                                    rePassword = rePass.getText().toString();
                                    add = address.getText().toString();
                                    ph = phoneNo.getText().toString();

                                    dbProfile.child(uid).child("firstName").setValue(Fname);
                                    dbProfile.child(uid).child("lastName").setValue(Lname);
                                    dbProfile.child(uid).child("password").setValue(password);
                                    dbProfile.child(uid).child("address").setValue(add);
                                    dbProfile.child(uid).child("phoneNo").setValue(ph);
                                    firebaseAuth.getCurrentUser().updatePassword(password);

                                    if (!password.equals(rePassword))
                                        toastMessage("Password and confirm password is not match!");
                                    dialog.cancel();

                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            }

        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(EditProfileIActivity.this);
                builder1.setMessage("Are you sure ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                AuthCredential credential = EmailAuthProvider
                                        .getCredential(emailUser, e);

                                // Prompt the user to re-provide their sign-in credentials
                                user.reauthenticate(credential)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                user.delete()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {

                                                                    toastMessage("deleted");
                                                                    delete.setEnabled(false);
                                                                    delete.setVisibility(View.INVISIBLE);
                                                                    Intent i = new Intent(EditProfileIActivity.this, MainActivity.class);
                                                                    startActivity(i);
                                                                }
                                                            }
                                                        });

                                            }
                                        });


                                //Alert

                                dialog.cancel();

                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();


            }});



        phoneNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String phoneCheck = phoneNo.getText().toString();
                if (!NumbCheck(phoneNo.getText().toString()) || phoneCheck.toString().length()!=10) {
                    //toastMessage(Integer.toString(phoneCheck.toString().length()));
                    if (phoneCheck.length() != 10 && !NumbCheck(phoneNo.getText().toString()))
                        phoneNo.setError("10 digits And no letters");
                    else if (phoneCheck.length() == 10)
                        phoneNo.setError("Only digits");
                    else
                        phoneNo.setError("10 digits Only");


                }

            }
        });
        rePass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String p5 = rePass.getText().toString();
                if(p5.isEmpty() || p5.length()<6)
                {
                    if(p5.isEmpty() && p5.length()<6)
                    {
                        rePass.setError("Enter a password from more than 5 charat");
                    }
                    else if (p5.length()<6)
                    {
                        rePass.setError("Must be more than 5 charat");
                    }
                }

            }
        });
        fname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String p3 = fname.getText().toString();
                if (!nAmeCheck(p3) || p3.length()<2) {
                    if(!nAmeCheck(p3) && p3.length()<2)
                    {
                        fname.setError("Only letters and two charat or more");
                    }
                    else if(!nAmeCheck(p3))
                    {
                        fname.setError("Only letters");
                    }
                    else
                    {
                        fname.setError("two charat or more");
                    }
                }

            }
        });
        lname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String p3 = lname.getText().toString();
                if (!nAmeCheck(p3) || p3.length()<2) {
                    if(!nAmeCheck(p3) && p3.length()<2)
                    {
                        lname.setError("Only letters and two charat or more");
                    }
                    else if(!nAmeCheck(p3))
                    {
                        lname.setError("Only letters");
                    }
                    else
                    {
                        lname.setError("two charat or more");
                    }
                }

            }
        });
    }

    // validating phone id
    private boolean NumbCheck(String email) {
        String EMAIL_PATTERN = "^[0-9]*$";
        //toastMessage(Integer.toString(email.toString().length()));
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        //toastMessage(Boolean.toString(matcher.matches()));

        return matcher.matches();
    }
    // validating name id
    private boolean nAmeCheck(String email) {
        String EMAIL_PATTERN = "^[A-Za-z]*$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        //toastMessage(matcher.toString());
        return matcher.matches();
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
