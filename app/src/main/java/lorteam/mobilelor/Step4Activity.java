package lorteam.mobilelor;

import android.*;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;

public class Step4Activity extends AppCompatActivity {
    DatabaseReference dbTemplate ,db ,mDatabase , dbIssuer;
    FirebaseAuth firebaseAuth;
    Button save;
    ImageView imageLogout , home;
    EditText doc;
    String emailRequester, uid,emailIss ,emRe, emIs ,s9;
    final static int PICK_PDF_CODE = 2342;
    StorageReference mStorageReference;
    ProgressBar progressBar;
    String outpath ,em1,em2, Rname , t ,lor;
    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step4);
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
        List<Integer> NumberList = new ArrayList<Integer>();
        for (int i = 1000; i <= 1200; i++)
        {
            NumberList.add(i);
        }
        Collections.shuffle(NumberList);
        num = NumberList.get(0);
        NumberList.remove(0);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent h= new Intent(Step4Activity.this, HomeIssuerActivity.class);
                startActivity(h);
            }
        });

        imageLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(Step4Activity.this);
                builder1.setMessage("Are you sure to logout?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Alert
                                firebaseAuth.signOut();
                                Intent main = new Intent(Step4Activity.this,MainActivity.class);
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

        doc = (EditText) findViewById(R.id.editText);
        save = (Button) findViewById(R.id.button2);
       progressBar = (ProgressBar) findViewById(R.id.progressbar);
        mStorageReference = FirebaseStorage.getInstance().getReference();
        emRe=encodeUserEmail(emailRequester);
        emIs=encodeUserEmail(emailIss);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("LORs").child(emRe).child(emIs);
        db = FirebaseDatabase.getInstance().getReference();
        outpath="";
        db.child("listReq").child(emRe).child(emIs).child("type").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                t=dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s1 = (String) dataSnapshot.child("Introduction1").getValue();
                String s2 = (String) dataSnapshot.child("Introduction2").getValue();
                String s4 = (String) dataSnapshot.child("body1").getValue();
                String s5 = (String) dataSnapshot.child("body2").getValue();
                String s6 = (String) dataSnapshot.child("body3").getValue();
                String s7 = (String) dataSnapshot.child("body4").getValue();
                String s8 = (String) dataSnapshot.child("conclusion").getValue();
                 s9 = (String) dataSnapshot.child("issuerName").getValue();
                String s10 = (String) dataSnapshot.child("issuerEmail").getValue();
                String s11 = (String) dataSnapshot.child("issuerAddress").getValue();
                String s12 = (String) dataSnapshot.child("issuerPhone").getValue();
                if(t.equals("Soft copy")){
                doc.setText("\n\n\n\n\n\n\n" + s1 +"\n\n\n\n" + s2 +"\n\n" + s4+" " + s5+" " + s6+" " + s7+" " +"\n\n"+ s8 + "\n\n" + s9 + "\n" +s10 + "\n" + s11 + "\n" + s12 + "\n "+
                        "please visit our website: localhost/mobilelor/index.html then enter this verification number" +" " +num +" "+
                        "to check this LOR is authenticated or not." +"\n\n\n\n\n\n\n" );}
                else{
                    doc.setText("\n\n\n\n\n\n\n" + s1 +"\n\n\n\n" + s2 +"\n\n" + s4+" " + s5+" "+ s6+" " + s7+" " +"\n\n"+ s8 + "\n\n" + s9 + "\n" +s10 + "\n" + s11 + "\n" + s12 +
                            "\n"+ "\n\n\n\n\n\n\n" );
                }}

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addListenerForSingleValueEvent(eventListener);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(t.equals("Hard copy")){
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Step4Activity.this);
                    LayoutInflater inflater = getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.dialog, null);
                    dialogBuilder.setView(dialogView);
                    final DatePicker dp = (DatePicker) dialogView.findViewById(R.id.dp);

                    final EditText editTextL = (EditText) dialogView.findViewById(R.id.editTextLoc);
                    final Button buttonSave = (Button) dialogView.findViewById(R.id.buttonSave);
                    final Button buttonCancel = (Button) dialogView.findViewById(R.id.buttonCancel);

                    dialogBuilder.setTitle("Enter the information of pickup LOR");
                    final AlertDialog b = dialogBuilder.create();
                    b.show();


                    buttonSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int y= dp.getYear();
                            int mon= dp.getMonth()+1;
                            int day= dp.getDayOfMonth();
                            String date= y+"-"+mon+"-"+day;


                            String l = editTextL.getText().toString().trim();
                            if (!TextUtils.isEmpty(l)) {
                                Document d = new Document();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                                    startActivity(intent);
                                    return;}
                                outpath = Environment.getExternalStorageDirectory() + "/mypdf.pdf";
                                try {
//create pdf writer instance
                                    emRe=encodeUserEmail(emailRequester);
                                    emIs=encodeUserEmail(emailIss);
                                    PdfWriter.getInstance(d, new FileOutputStream(outpath));
//open the document for writing
                                    d.open();
//add paragraph to the document
                                    d.add(new Paragraph(doc.getText().toString()));
//close the document
                                    d.close();
                                    Uri file = Uri.fromFile(new File(outpath));

                                    uploadFile(file, emRe ,emIs);

                                } catch (FileNotFoundException e) {
// TODO Auto-generated catch block
                                    e.printStackTrace();
                                } catch (DocumentException e) {
// TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                db.child("listReq").child(emRe).child(emIs).child("location").setValue(l);
                                db.child("listReq").child(emRe).child(emIs).child("date").setValue(date);
                           }
                            else{
                                editTextL.setHint("Please Enter location");
                            }



                            b.dismiss();

                        }
                    });


                    buttonCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            b.dismiss();
                        }
                    });}
                else{

                AlertDialog.Builder builder1 = new AlertDialog.Builder(Step4Activity.this);
                builder1.setMessage("Are you sure ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Document d = new Document();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                                    startActivity(intent);
                                    return;}
                                outpath = Environment.getExternalStorageDirectory() + "/mypdf.pdf";
                                try {
//create pdf writer instance
                                    emRe=encodeUserEmail(emailRequester);
                                    emIs=encodeUserEmail(emailIss);
                                    PdfWriter.getInstance(d, new FileOutputStream(outpath));
//open the document for writing
                                    d.open();
//add paragraph to the document
                                    d.add(new Paragraph(doc.getText().toString()));
//close the document
                                    d.close();

                                    Uri file = Uri.fromFile(new File(outpath));

                                    uploadFile(file, emRe ,emIs);

                                } catch (FileNotFoundException e) {
// TODO Auto-generated catch block
                                    e.printStackTrace();
                                } catch (DocumentException e) {
// TODO Auto-generated catch block
                                    e.printStackTrace();
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









               // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, Manifest.permission.) != PackageManager.PERMISSION_GRANTED) {
                //Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                //startActivity(intent);
                //return;}
                /**
                 Intent intent1 = new Intent();
                 intent1.setType("application/pdf");
                 intent1.setAction(Intent.ACTION_GET_CONTENT);
                 startActivityForResult(Intent.createChooser(intent1, "Select Picture"), PICK_PDF_CODE);

                 // }
                 **/
                }
            }
        });

    }


    private void uploadFile(Uri data, final String em1, final String em2) {
        progressBar.setVisibility(View.VISIBLE);
        StorageReference sRef = mStorageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + ".pdf");
        sRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressBar.setVisibility(View.GONE);
                        db.child("listReq").child(em1).child(em2).child("status").setValue("2");
                        Upload upload = new Upload(taskSnapshot.getDownloadUrl().toString());
                        db.child("listReq").child(em1).child(em2).child("LOR").setValue(upload);

                        Query q = db.child("listIssuer").child(em2).orderByKey().equalTo(em1);
                        q.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()) {


                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                        snapshot.getRef().removeValue();


                                    }

                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                        db.child("listReq").child(em1).child(em2).child("type").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                t=dataSnapshot.getValue(String.class);

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        if(t.equals("Soft copy")) {
                            db.child("verification").child(String.valueOf(num)).child("code").setValue(String.valueOf(num));

                            db.child("verification").child(String.valueOf(num)).child("issuerName").setValue(s9);

                            db.child("listReq").child(em1).child(em2).child("NameRequester").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Rname = dataSnapshot.getValue(String.class);
                                    db.child("verification").child(String.valueOf(num)).child("requesterName").setValue(Rname);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }
                        else{ Toast.makeText(getApplicationContext(),"We will send LOR as pdf file to your E-mail",Toast.LENGTH_LONG).show();
                            sendMessage(em1,em2);}

                        // mDatabase.child(mDatabase.push().getKey()).setValue(upload);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {


                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @SuppressWarnings("VisibleForTests")
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

            }
        });
    }
    private static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }
    private void sendMessage(final String e1, final  String e2) {

       db.child("listReq").child(e1).child(e2).child("LOR").child("url").addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               lor=dataSnapshot.getValue(String.class);
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String eR= encodeUserEmail1(e1);
                    String eI= encodeUserEmail1(e2);
                    GMailSender sender = new GMailSender("mobilelor20172018@gmail.com", "123456789lor");
                    sender.sendMail("LOR to  "+" "+ eR,
                            "This is LOR to" +" "+ eR+ " " + "You can download from this link" +" "+lor,
                            "mobilelor20172018@gmail.com",
                            eI);

                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();
    }

    private static String encodeUserEmail1(String userEmail) {
        return userEmail.replace(",", ".");
    }
}

