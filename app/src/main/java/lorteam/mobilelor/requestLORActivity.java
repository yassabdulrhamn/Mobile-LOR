package lorteam.mobilelor;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class requestLORActivity extends AppCompatActivity {
    DatabaseReference dbRequest , dbIssuer;
    FirebaseAuth firebaseAuth;
    EditText emailIssuer, comment ;
    RadioGroup purpose , type;
    RadioButton a, b ,c , d, e;
    ImageView imageLogout , home;
    Button cv,transcript, req;
    final static int PICK_PDF_CODE = 2342;
    StorageReference mStorageReference;
    String uid , emailReq , nameReq , s1 ,ema , emRe, emIs ,email, LastnameReq;
    String em1,em2,em_2, key;
    TextView t;
    boolean flag1= true;
    boolean flag2=true;
    //لازم اشيك ع الايميل انو يكون جامعي
    // البيانات الاساسية : ايميل + بيربوس + نوع الرسالة + السي في
    //البيربوس يتغير بخليه جينرال
    // مفروض الايميل ما يتكرر مرتين بنفس الليست
    //اظن اني كل مره ارسل ايميل للاشيور واناديه يسجل !! + لازم اكتب رسالة كويسة للدكتور
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_lor);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbar);
        imageLogout=(ImageView)findViewById(R.id.imageLogOut);
        home=(ImageView)findViewById(R.id.imageHome);
        t=(TextView)findViewById(R.id.t1);
        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            uid= user.getUid();
            emailReq=user.getEmail();
        }



        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent h= new Intent(requestLORActivity.this, HomeRequesterActivity.class);
                startActivity(h);
            }
        });

        imageLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(requestLORActivity.this);
                builder1.setMessage("Are you sure to logout?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Alert
                                firebaseAuth.signOut();
                                Intent main = new Intent(requestLORActivity.this,MainActivity.class);
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
        final String emailPatternIssuer = "[a-zA-Z]+@[ksu]+\\.+[edu]+\\.+[sa]+";
        mStorageReference = FirebaseStorage.getInstance().getReference();
        emailIssuer=(EditText)findViewById(R.id.emailissuer);
        comment=(EditText)findViewById(R.id.comment_edittext);
        purpose=(RadioGroup) findViewById(R.id.rg1);
        type=(RadioGroup)findViewById(R.id.rg2);
        a=(RadioButton) findViewById(R.id.ac);
        b=(RadioButton) findViewById(R.id.bu);
        c=(RadioButton) findViewById(R.id.all);
        d=(RadioButton)findViewById(R.id.pick);
        e=(RadioButton)findViewById(R.id.soft);
        req=(Button) findViewById(R.id.btn_req);
        cv=(Button) findViewById(R.id.cv);
        transcript=(Button) findViewById(R.id.tran);
        ema= emailIssuer.getText().toString();
        emRe= encodeUserEmail(emailReq);
        emIs= encodeUserEmail(ema);
        t.setText(emRe);

        dbRequest = FirebaseDatabase.getInstance().getReference();
        dbIssuer=FirebaseDatabase.getInstance().getReference("Issuer");
        dbRequest.child("Requester").child(uid).child("firstName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nameReq= dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dbRequest.child("Requester").child(uid).child("lastName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LastnameReq= dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dbRequest.child("Requester").child(uid).child("email").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                email= dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag2=false;
                Intent intentPDF = new Intent();
                intentPDF.setType("application/pdf");
                intentPDF.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intentPDF, "Select Picture"), PICK_PDF_CODE);

            }
        });


        transcript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag1=false;
                Intent intentPDF = new Intent();
                intentPDF.setType("application/pdf");
                intentPDF.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intentPDF, "Select Picture"), PICK_PDF_CODE);

            }
        });

        req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ema= emailIssuer.getText().toString();
                emRe = encodeUserEmail(emailReq);
                emIs = encodeUserEmail(ema);
                dbRequest.child("listRequest").child(emRe).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(emIs)){
                            key=dataSnapshot.child(emIs).getValue(String.class);
                       }
                        else{
                            key="";
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                AlertDialog.Builder builder1 = new AlertDialog.Builder(requestLORActivity.this);
                builder1.setMessage("Are you sure ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ema= emailIssuer.getText().toString();
                                emRe = encodeUserEmail(emailReq);
                                emIs = encodeUserEmail(ema);



                                  if(ema.isEmpty()){
                                    Toast.makeText(getApplicationContext(),"Please enter email's issuer",Toast.LENGTH_LONG).show();}
                                else if(!ema.matches(emailPatternIssuer)){
                                    Toast.makeText(getApplicationContext(),"Please enter ksu email",Toast.LENGTH_LONG).show();}
                                else if (!key.equals("")){
                                    Toast.makeText(getApplicationContext(),"You already request  LOR from this issuer",Toast.LENGTH_LONG).show();
                                }

                                else if(flag1==true){
                                    Toast.makeText(getApplicationContext(),"Please upload your transcript",Toast.LENGTH_LONG).show();
                                }

                                    else {

                                    ema= emailIssuer.getText().toString();
                                    emRe = encodeUserEmail(emailReq);
                                    emIs = encodeUserEmail(ema);
                                        Query q= dbIssuer.orderByChild("email").equalTo(ema);
                                        q.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.exists()) {

                                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                     s1 = snapshot.child("email").getValue(String.class);


                                                    }

                                                }
                                                else{

 Toast.makeText(getApplicationContext(), "This issuer is not register in MobileLOR application . We will send to her email to invent to register ", Toast.LENGTH_SHORT).show();
                                                    sendMessage();

                                                }

                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });

                                        String com=comment.getText().toString();
                                        if(comment.length()==0){
                                            com="";
                                        }
                                        RadioButton selectedRadio= (RadioButton)findViewById(purpose.getCheckedRadioButtonId());
                                        String pur= selectedRadio.getText().toString();
                                        RadioButton selectedRadio1= (RadioButton)findViewById(type.getCheckedRadioButtonId());
                                        String t1= selectedRadio1.getText().toString();
                                        emRe= encodeUserEmail(emailReq);
                                        emIs= encodeUserEmail(ema);

                                        dbRequest.child("listReq").child(emRe).child(emIs).child("NameRequester").setValue(nameReq+" "+ LastnameReq);
                                        dbRequest.child("listReq").child(emRe).child(emIs).child("emailRequester").setValue(email);
                                        dbRequest.child("listReq").child(emRe).child(emIs).child("comment").setValue(com);
                                        dbRequest.child("listReq").child(emRe).child(emIs).child("purpose").setValue(pur);
                                        dbRequest.child("listReq").child(emRe).child(emIs).child("type").setValue(t1);
                                        dbRequest.child("listReq").child(emRe).child(emIs).child("status").setValue("0");
                                        dbRequest.child("listRequest").child(emRe).child(emIs).setValue(ema);
                                        dbRequest.child("listIssuer").child(emIs).child(emRe).setValue(emailReq);


                                        Toast.makeText(getApplicationContext(),"Send !",Toast.LENGTH_LONG).show();
                                    Intent h1= new Intent(requestLORActivity.this, HomeRequesterActivity.class);
                                    startActivity(h1);


                                }


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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         em1=t.getText().toString();
        em_2=emailIssuer.getText().toString();
        em2=encodeUserEmail(em_2);

        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            if (data.getData() != null) {

                String ema= emailIssuer.getText().toString();
                String emailPatternIssuer = "[a-zA-Z]+@[ksu]+\\.+[edu]+\\.+[sa]+";
                if(ema.matches(emailPatternIssuer)){
                if((cv.getId()==R.id.cv)&&(flag2==false)){

                    uploadFile(data.getData(),em1,em2);}
                 if((transcript.getId()==R.id.tran)&&(flag1==false)) {
                    uploadFile1(data.getData(),em1,em2);}}
            } else {
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void uploadFile(Uri data, final String em1 ,final String em2) {

        StorageReference sRef = mStorageReference.child(Constants.STORAGE_PATH_UPLOADS+ System.currentTimeMillis() + ".pdf");
        sRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Upload upload = new Upload(taskSnapshot.getDownloadUrl().toString());
                        dbRequest.child("listReq").child(em1).child(em2).child("cv").setValue(upload);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void uploadFile1(Uri data, final String em1 ,final String em2) {

        StorageReference sRef = mStorageReference.child(Constants.STORAGE_PATH_UPLOADS+ System.currentTimeMillis() + ".pdf");
        sRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        Upload upload = new Upload(taskSnapshot.getDownloadUrl().toString());
                        dbRequest.child("listReq").child(em1).child(em2).child("transcript").setValue(upload);


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

    }

    private void sendMessage() {
        ema= emailIssuer.getText().toString();



        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("mobilelor20172018@gmail.com", "123456789lor");
                    sender.sendMail("MobileLOR App",
                            "Your Student "+ " "+emailReq + "request Letter of recommendation from you on MobileLOR Application on Android " +
                                    "\n"+" Yon can write LOR with easy way download now!"+"\n"+"MobileLOR Team",
                            "mobilelor20172018@gmail.com",
                            ema);

                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();
    }
    private static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

}
