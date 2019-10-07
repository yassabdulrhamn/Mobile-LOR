package lorteam.mobilelor;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IdRes;
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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RejectActivity extends AppCompatActivity {
    ImageView imageLogout , home;
    FirebaseAuth firebaseAuth;
    String uid,emailIss,emailRequester ,emRe, emIs , op;
    RadioGroup r;
    RadioButton r1,r2,r3 ,r4;
    Button save;
    EditText t;
    private DatabaseReference mDatabase;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reject);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbarHeader);

        setSupportActionBar(toolbar);
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            emailRequester= null;
        } else {
            emailRequester= extras.getString("EmailRequester");
        }
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
                Intent h= new Intent(RejectActivity.this, HomeIssuerActivity.class);
                startActivity(h);
            }
        });

        imageLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(RejectActivity.this);
                builder1.setMessage("Are you sure to logout?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Alert
                                firebaseAuth.signOut();
                                Intent main = new Intent(RejectActivity.this,MainActivity.class);
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
        r = (RadioGroup)findViewById(R.id.RGRej);
        r1 = (RadioButton)findViewById(R.id.rej1);
        r2 = (RadioButton)findViewById(R.id.rej2);
        r3 = (RadioButton)findViewById(R.id.rej3);
        r4=(RadioButton)findViewById(R.id.rej4);
        t=(EditText)findViewById(R.id.rejText);

        save = (Button)findViewById(R.id.save);
        mDatabase = FirebaseDatabase.getInstance().getReference();
       emRe= encodeUserEmail(emailRequester);
        emIs= encodeUserEmail(emailIss);

        mDatabase.child("Reject").child("op1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                r1.setText(s);}
            @Override
            public void onCancelled(DatabaseError databaseError) {}});
        mDatabase.child("Reject").child("op2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                r2.setText(s);}
            @Override
            public void onCancelled(DatabaseError databaseError) {}});
        mDatabase.child("Reject").child("op3").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                r3.setText(s);}
            @Override
            public void onCancelled(DatabaseError databaseError) {}});
        r.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                RadioButton selectRadio = (RadioButton) findViewById(r.getCheckedRadioButtonId());
                op = selectRadio.getText().toString();
                if(op.equals("Other")){
                    t.setVisibility(View.VISIBLE);}
                else{
                    op=selectRadio.getText().toString();}}
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(RejectActivity.this);
                builder1.setMessage("Are you sure ?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                               if(op.equals("Other")){
                                   String option=t.getText().toString();
                                   mDatabase.child("listReq").child(emRe).child(emIs).child("status").setValue("3");
                                   mDatabase.child("listReq").child(emRe).child(emIs).child("rej").setValue(option);
                                   Query q = mDatabase.child("listIssuer").child(emIs).orderByKey().equalTo(emRe);
                                   q.addListenerForSingleValueEvent(new ValueEventListener() {
                                       @Override
                                       public void onDataChange(DataSnapshot dataSnapshot) {
                                           if(dataSnapshot.exists()) {
                                               for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                   snapshot.getRef().removeValue();}}}
                                       @Override
                                       public void onCancelled(DatabaseError databaseError) {}});}
                                else{
                                   mDatabase.child("listReq").child(emRe).child(emIs).child("status").setValue("3");
                                   mDatabase.child("listReq").child(emRe).child(emIs).child("rej").setValue(op);
                                   Query q = mDatabase.child("listIssuer").child(emIs).orderByKey().equalTo(emRe);
                                   q.addListenerForSingleValueEvent(new ValueEventListener() {
                                       @Override
                                       public void onDataChange(DataSnapshot dataSnapshot) {
                                           if(dataSnapshot.exists()) {
                                               for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                   snapshot.getRef().removeValue();}}}
                                       @Override
                                       public void onCancelled(DatabaseError databaseError) {}});}
                                Toast.makeText(getApplicationContext(),"Done!",Toast.LENGTH_LONG).show();
                                Intent h1= new Intent(RejectActivity.this,HomeIssuerActivity.class);
                                startActivity(h1);
                                dialog.cancel();}
                        });
                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();}
        });}
    private static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }
}
