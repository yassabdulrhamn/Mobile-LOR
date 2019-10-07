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
import android.widget.ImageView;
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

public class DetailsRequestActivity extends AppCompatActivity {
    DatabaseReference dbRequest;
    FirebaseAuth firebaseAuth;
    ImageView imageLogout , home;
    TextView email_issuer , status , lor , reject , location, date , lT, dT;
    StorageReference mStorageReference;

    String uid , emailReq, stat ,s ,rej , emailIssuer , emRe, emIs ,t ,l ,d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_request);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbar);
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            emailIssuer= null;
        } else {
            emailIssuer= extras.getString("EmailIssuer");
        }

        imageLogout=(ImageView)findViewById(R.id.imageLogOut);
        home=(ImageView)findViewById(R.id.imageHome);
        email_issuer=(TextView) findViewById(R.id.emailI);
        status=(TextView)findViewById(R.id.status);
        lor=(TextView)findViewById(R.id.doc);
        location=(TextView)findViewById(R.id.location) ;
        date=(TextView)findViewById(R.id.date) ;
        lT=(TextView)findViewById(R.id.l);
        dT=(TextView)findViewById(R.id.d);
        reject=(TextView)findViewById(R.id.rejreason);
        dbRequest = FirebaseDatabase.getInstance().getReference("listReq");
        mStorageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            uid= user.getUid();
            emailReq=user.getEmail();
        }

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent h= new Intent(DetailsRequestActivity.this, HomeRequesterActivity.class);
                startActivity(h);
            }
        });

        imageLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(DetailsRequestActivity.this);
                builder1.setMessage("Are you sure to logout ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Alert
                                firebaseAuth.signOut();
                                Intent main = new Intent(DetailsRequestActivity.this,MainActivity.class);
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
        emRe=encodeUserEmail(emailReq);
        emIs=encodeUserEmail(emailIssuer);
        email_issuer.setText(emailIssuer);

        dbRequest.child(emRe).child(emIs).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    if((dataSnapshot.hasChild("location"))&&(dataSnapshot.hasChild("date"))){
                    dbRequest.child(emRe).child(emIs).child("location").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            l=dataSnapshot.getValue(String.class);
                            location.setVisibility(View.VISIBLE);
                            location.setText(l);
                            lT.setVisibility(View.VISIBLE);}
                        @Override
                        public void onCancelled(DatabaseError databaseError) {} });
                    dbRequest.child(emRe).child(emIs).child("date").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            d=dataSnapshot.getValue(String.class);
                            date.setVisibility(View.VISIBLE);
                            date.setText(d);
                            dT.setVisibility(View.VISIBLE);}
                        @Override
                        public void onCancelled(DatabaseError databaseError) {}});}
                else if(dataSnapshot.hasChild("LOR")){
                    dbRequest.child(emRe).child(emIs).child("LOR").child("url").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            s = dataSnapshot.getValue(String.class);
                            lor.setVisibility(View.VISIBLE);
                            lor.setMovementMethod(LinkMovementMethod.getInstance());
                            lor.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                                        browserIntent.setData(Uri.parse(s));
                                        startActivity(browserIntent);}});}
                        @Override
                        public void onCancelled(DatabaseError databaseError) {}});}
                else if(dataSnapshot.hasChild("rej")){
                    dbRequest.child(emRe).child(emIs).child("rej").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            rej = dataSnapshot.getValue(String.class);
                            reject.setText(rej);
                            reject.setVisibility(View.VISIBLE);}
                        @Override
                        public void onCancelled(DatabaseError databaseError) {}});}}
            @Override
            public void onCancelled(DatabaseError databaseError) {}});

        dbRequest.child(emRe).child(emIs).child("status").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                stat= dataSnapshot.getValue(String.class);
                if(stat.equals("0"))
                    status.setText("Sending");
                if(stat.equals("1"))
                    status.setText("Processing");
                if(stat.equals("2")){
                    status.setText("Done");}
                if(stat.equals("3")){
                    status.setText("Reject");}}


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







    }
    private static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }
}
