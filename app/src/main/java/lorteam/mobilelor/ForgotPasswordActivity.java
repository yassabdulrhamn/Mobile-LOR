package lorteam.mobilelor;

import android.content.Intent;
import android.provider.ContactsContract;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText emailForgot;
    Button save;
    FirebaseAuth firebaseAuth;
    TextView signup , login;
    ImageView imageFAQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbar);
        imageFAQ=(ImageView)findViewById(R.id.imageFAQ);
        emailForgot=(EditText)findViewById(R.id.input_email_Forgot);
        save=(Button)findViewById(R.id.btn_forgot);
        firebaseAuth=FirebaseAuth.getInstance();
        signup=(TextView)findViewById(R.id.link_signup);
        login=(TextView)findViewById(R.id.link_login);
        imageFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imgFAQ=new Intent(ForgotPasswordActivity.this,listFAQ.class);
                startActivity(imgFAQ);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sign= new Intent(ForgotPasswordActivity.this, SignupActivity.class);
                startActivity(sign);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent log= new Intent(ForgotPasswordActivity.this, MainActivity.class);
                startActivity(log);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailForgot.getText().toString().trim();
                int index = 0;
                char ch=   'a';

                int size = email.indexOf('@');
                boolean flag = true;

                if (!email.isEmpty()) {
                    index = email.indexOf('@');

                    ch = email.charAt(0);


                    for (int i =0; i<index;i++) {

                        ch = email.charAt(i);
                        if (ch>='0' && ch<='9') {
                            ch = email.charAt(i);
                        }
                        else{
                            i= email.indexOf('@');
                            flag = false;}
                    }


                }

                if (email.isEmpty()) {
                    toastMessage("Enter E-Mail");





                }



                else  if ((!email.contains("@")) ||(!email.contains(".sa")) ){
                    toastMessage("invalid email");

                }

                else   if ((email.substring(email.indexOf('@')).equals("@student.ksu.edu.sa")) && (!flag)) {
                    toastMessage("not ksu student email   there is letter");
                }

                else    if ((email.substring(email.indexOf('@')).equals("@student.ksu.edu.sa")) && (size!=9)) {
                    toastMessage("not ksu student email  not 9 numbers");
                }
                else if ((!email.substring(email.indexOf('@')).equalsIgnoreCase("@student.ksu.edu.sa")) && (!email.substring(email.indexOf('@')).equalsIgnoreCase("@ksu.edu.sa")))  {
                    toastMessage("Enter ksuuu");

                }

                else if ((email.substring(email.indexOf('@')).equalsIgnoreCase("@ksu.edu.sa")) && (flag==true))  {
                    toastMessage("not a ksu staff");

                }


                else {




                    firebaseAuth.sendPasswordResetEmail(email)

                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                    if (task.isSuccessful()) {
                          Toast.makeText(ForgotPasswordActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        if(task.getException().getMessage().equals("There is no user record corresponding to this identifier. The user may have been deleted."))
                      toastMessage("This email is not registered");
                                        else

                                            if(task.getException().getMessage().equals("A network error (such as timeout, interrupted connection or unreachable host) has occurred."))
                           toastMessage("there is no internet connection");

                                    }


                                }
                            });}

            }
        });
    }
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
