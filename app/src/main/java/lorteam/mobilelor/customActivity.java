package lorteam.mobilelor;

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
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class customActivity extends AppCompatActivity {
    DatabaseReference dbTemplate ,db ,mDatabase , dbIssuer;
    FirebaseAuth firebaseAuth;
    Button save;
    ImageView imageLogout , home;
    EditText doc;
    String emailRequester, uid,emailIss ,emRe, emIs ,t ,lor;
    final static int PICK_PDF_CODE = 2342;
    StorageReference mStorageReference;
    ProgressBar progressBar;
    String outpath ,Rname ,Iname, InameL;
    int num;
    String m="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
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
                Intent h= new Intent(customActivity.this, HomeIssuerActivity.class);
                startActivity(h);
            }
        });

        imageLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(customActivity.this);
                builder1.setMessage("Are you sure to logout?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                firebaseAuth.signOut();
                                Intent main = new Intent(customActivity.this,MainActivity.class);
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

        db = FirebaseDatabase.getInstance().getReference();
        outpath="";

        db.child("listReq").child(emRe).child(emIs).child("type").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                m=dataSnapshot.getValue(String.class);
                if(m.equals("Soft copy")){

                    doc.setText(""+ "\n"+ "please visit our website: localhost/mobilelor/index.html then enter this verification number" +" " +num +" " + "to check this LOR is authenticated or not." +"\n\n\n\n\n\n\n");}
                else{

                    doc.setText("");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        db.child("Issuer").child(uid).child("firstName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iname = dataSnapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}});
        db.child("Issuer").child(uid).child("lastName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                InameL = dataSnapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}});



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(m.equals("Hard copy")){
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(customActivity.this);
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
                            try {PdfWriter.getInstance(d, new FileOutputStream(outpath));
                                d.open();
                                d.add(new Paragraph(doc.getText().toString()));
                                d.close();
                                Uri file = Uri.fromFile(new File(outpath));
                                uploadFile(file, emRe, emIs);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (DocumentException e) {
                                e.printStackTrace();}
                            db.child("listReq").child(emRe).child(emIs).child("location").setValue(l);
                        db.child("listReq").child(emRe).child(emIs).child("date").setValue(date);
                       }
                        else{
                           editTextL.setHint("Please enter location");}
                            b.dismiss();

                    }
                });
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        b.dismiss();}
                });}
                else{
                AlertDialog.Builder builder1 = new AlertDialog.Builder(customActivity.this);
                builder1.setMessage("Are you sure ?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Document d = new Document();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                                    startActivity(intent);
                                    return;}
                                outpath = Environment.getExternalStorageDirectory() + "/mypdf.pdf";
                                try {PdfWriter.getInstance(d, new FileOutputStream(outpath));
                                    d.open();
                                    d.add(new Paragraph(doc.getText().toString()));
                                    d.close();
                                    Uri file = Uri.fromFile(new File(outpath));
                                    uploadFile(file, emRe, emIs);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (DocumentException e) {
                                    e.printStackTrace();}

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
                alert11.show();}}});}


    private void uploadFile(Uri data, final String em1 , final String em2) {
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
                                        snapshot.getRef().removeValue();}}}
                            @Override
                            public void onCancelled(DatabaseError databaseError) {}});
                        if(m.equals("Soft copy")){
                            String uid=firebaseAuth.getCurrentUser().getUid();
                        db.child("verification").child(String.valueOf(num)).child("code").setValue(String.valueOf(num));

                            db.child("verification").child(String.valueOf(num)).child("issuerName").setValue(Iname+" "+ InameL);
                        db.child("listReq").child(em1).child(em2).child("NameRequester").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Rname = dataSnapshot.getValue(String.class);
                                db.child("verification").child(String.valueOf(num)).child("requesterName").setValue(Rname);}
                            @Override
                            public void onCancelled(DatabaseError databaseError) {}});}

                        else{
                            Toast.makeText(getApplicationContext(),"We will send LOR as pdf file to your E-mail",Toast.LENGTH_LONG).show();
                            sendMessage(em1,em2);
                        }

                        Toast.makeText(getApplicationContext(),"Save!",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(customActivity.this,listIssActivity.class);
                        startActivity(i);}})
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

    private void sendMessage(final String e1, final String e2) {


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
                    sender.sendMail("LOR to  "+" " +eR,
                           "This is LOR to" +" "+ eR+" " + "You can download from this link" +" "+lor,
                            "mobilelor20172018@gmail.com",
                            eI);

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


    private static String encodeUserEmail1(String userEmail) {
        return userEmail.replace(",", ".");
    }
}


