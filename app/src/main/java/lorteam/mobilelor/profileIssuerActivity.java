package lorteam.mobilelor;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profileIssuerActivity extends AppCompatActivity {
    DatabaseReference dbProfile;
    FirebaseAuth firebaseAuth;
    TextView name, email , address, phoneNo;
    ImageView imageLogout , home ;
    ImageButton editP;
    String n;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_issuer);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbar);
        imageLogout=(ImageView)findViewById(R.id.imageLogOut);
        home=(ImageView)findViewById(R.id.imageHome);
        editP=(ImageButton) findViewById(R.id.goeditprofileI);
        editP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent edit= new Intent(profileIssuerActivity.this, EditProfileIActivity.class);
                startActivity(edit);
            }
        });
        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            uid= user.getUid();
        }

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent h= new Intent(profileIssuerActivity.this, HomeIssuerActivity.class);
                startActivity(h);
            }
        });

        imageLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(profileIssuerActivity.this);
                builder1.setMessage("Are you sure to logout ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Alert
                                firebaseAuth.signOut();
                                Intent main = new Intent(profileIssuerActivity.this,MainActivity.class);
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

        name=(TextView)findViewById(R.id.textNameI);
        email=(TextView)findViewById(R.id.texEmailI);
        address=(TextView)findViewById(R.id.textviewaddressI);
        phoneNo=(TextView)findViewById(R.id.phoneNoI);
        dbProfile= FirebaseDatabase.getInstance().getReference("Issuer");
        dbProfile.child(uid).child("firstName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 n= dataSnapshot.getValue(String.class);
                name.setText(n);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}});
        dbProfile.child(uid).child("lastName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s= dataSnapshot.getValue(String.class);
                name.setText(n + " " +s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}});
        dbProfile.child(uid).child("email").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String e= dataSnapshot.getValue(String.class);
                email.setText(e);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}});

        dbProfile.child(uid).child("address").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String a= dataSnapshot.getValue(String.class);
                address.setText(a);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}});

        dbProfile.child(uid).child("phoneNo").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ph= dataSnapshot.getValue(String.class);
                phoneNo.setText(ph);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}});

    }
}
