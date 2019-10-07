package lorteam.mobilelor;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class createAdmin extends AppCompatActivity {
    EditText e, p;
    Button c;
    FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_admin);
        e=(EditText)findViewById(R.id.email);
        p=(EditText)findViewById(R.id.password);
        c=(Button)findViewById(R.id.s);
        mDatabase= FirebaseDatabase.getInstance().getReference("Admin");

        firebaseAuth= FirebaseAuth.getInstance();
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em=e.getText().toString();
                String pass= p.getText().toString();

                firebaseAuth.createUserWithEmailAndPassword(em,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String em=e.getText().toString();
                        String pass= p.getText().toString();
                        if(task.isSuccessful()){
                            mDatabase.child("email").setValue(em);
                            mDatabase.child("password").setValue(pass);

                        }

                    }
                });
            }
        });
    }
}
