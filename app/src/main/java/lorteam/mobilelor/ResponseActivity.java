package lorteam.mobilelor;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ResponseActivity extends AppCompatActivity {
    DatabaseReference dblor , dbIssuer;
    FirebaseAuth firebaseAuth;

    ImageView imageLogout , home;

    TextView name, emailReq;
    TextView comment;
    TextView purpose;
    TextView cv;
    TextView transcript,type;

    Button accept ;
    Button reject ;
    StorageReference mStorageReference;
    String uid , emailIss , nameReq , s1 ,ema, emailRequester , emRe, emIs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);
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
                Intent h= new Intent(ResponseActivity.this, HomeIssuerActivity.class);
                startActivity(h);
            }
        });

        imageLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ResponseActivity.this);
                builder1.setMessage("Are you sure to logout?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Alert
                                firebaseAuth.signOut();
                                Intent main = new Intent(ResponseActivity.this,MainActivity.class);
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
        mStorageReference = FirebaseStorage.getInstance().getReference();
        emailReq=(TextView)findViewById(R.id.emailr);
        name=(TextView)findViewById(R.id.reqName);
        comment=(TextView)findViewById(R.id.comm);
        type=(TextView)findViewById(R.id.dele);
        purpose=(TextView)findViewById(R.id.pur);
        cv=(TextView)findViewById(R.id.cv);
        transcript=(TextView)findViewById(R.id.transcript);
        accept = (Button) findViewById(R.id.btn_acc);
        reject = (Button) findViewById(R.id.btn_rej);

        dblor = FirebaseDatabase.getInstance().getReference("listReq");
        emRe=encodeUserEmail(emailRequester);
        emIs=encodeUserEmail(emailIss);

        dblor.child(emRe).child(emIs).child("emailRequester").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                emailReq.setText(s);}
            @Override
            public void onCancelled(DatabaseError databaseError) {}});
        dblor.child(emRe).child(emIs).child("NameRequester").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                name.setText(s);}
            @Override
            public void onCancelled(DatabaseError databaseError) {}});
        dblor.child(emRe).child(emIs).child("comment").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                if(s.isEmpty()){comment.setText("There is no comment");}
                else
                comment.setText(s);}
            @Override
            public void onCancelled(DatabaseError databaseError) {}});
        dblor.child(emRe).child(emIs).child("purpose").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                purpose.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}});
        dblor.child(emRe).child(emIs).child("type").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                type.setText(s);}
            @Override
            public void onCancelled(DatabaseError databaseError) {}});
        dbIssuer=FirebaseDatabase.getInstance().getReference("listReq");
        dbIssuer.child(emRe).child(emIs).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("cv")){
                    dbIssuer.child(emRe).child(emIs).child("cv").child("url").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            final String s = dataSnapshot.getValue(String.class);
                                cv.setVisibility(View.VISIBLE);
                                cv.setMovementMethod(LinkMovementMethod.getInstance());
                                cv.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                                        browserIntent.setData(Uri.parse(s));
                                        startActivity(browserIntent);}
                                });}
                        @Override
                        public void onCancelled(DatabaseError databaseError) {}});}
                else{
                    cv.setVisibility(View.INVISIBLE);}}
            @Override
            public void onCancelled(DatabaseError databaseError) {}});
        dblor.child(emRe).child(emIs).child("transcript").child("url").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String s1 = dataSnapshot.getValue(String.class);
                transcript.setMovementMethod(LinkMovementMethod.getInstance());
                transcript.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                        browserIntent.setData(Uri.parse(s1));
                        startActivity(browserIntent);}
                });}
            @Override
            public void onCancelled(DatabaseError databaseError) {}});
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ResponseActivity.this);
                builder1.setMessage("Are you sure ?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dblor.child(emRe).child(emIs).child("status").setValue("1");
                                Intent i = new Intent(ResponseActivity.this,WriteActivity.class);
                                i.putExtra("EmailRequester",emailRequester);
                                startActivity(i);
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
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ResponseActivity.this);
                builder1.setMessage("Are you sure ?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i2 = new Intent(ResponseActivity.this,RejectActivity.class);
                                i2.putExtra("EmailRequester",emailRequester);
                                startActivity(i2);
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
