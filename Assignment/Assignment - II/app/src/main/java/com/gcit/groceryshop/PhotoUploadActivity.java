package com.gcit.groceryshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class PhotoUploadActivity extends AppCompatActivity {

    //Declare all required variable
    private FirebaseAuth firebaseAuth;
    private EditText mTitle, mShopName, mContact;
    private Button mChooseBtn, mUploadBtn;
    private ImageView uploadPhoto;
    private Context context = PhotoUploadActivity.this;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri uploadPhotoUri;
    private DatabaseReference reference, reference1;
    String licenseNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_upload);

        //Get value from Login activity
        String email = getIntent().getStringExtra("Email");

        //Instantiate all the variable with XML
        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference1 = FirebaseDatabase.getInstance().getReference("Uploads");
        mTitle = (EditText) findViewById(R.id.phototitle);
        mShopName = (EditText)findViewById(R.id.shopname);
        mContact = (EditText)findViewById(R.id.contactnumber);
        uploadPhoto = (ImageView) findViewById(R.id.uploadImage);
        mChooseBtn = (Button) findViewById(R.id.choosefile);
        mUploadBtn = (Button)findViewById(R.id.uploadfile);

        //Get data from realtime database
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot db : snapshot.getChildren()){
                    if(db.child("email").getValue().equals(email)){
                        licenseNo = db.child("license").getValue(String.class);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mChooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });

        mUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPhoto();
            }
        });
    }
    //Creates file name
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    //Upload photo btn
    private void uploadPhoto() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.show();
        if(uploadPhotoUri != null){
            final StorageReference fileReference = FirebaseStorage.getInstance().getReference("Users/" + System.currentTimeMillis() + "." + getFileExtension(uploadPhotoUri));
            fileReference.putFile(uploadPhotoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.setProgress(0);
                        }
                    },500);
                    Toast.makeText(getApplicationContext(),"Uploaded Successfully", Toast.LENGTH_LONG).show();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(licenseNo);
                    String title = mTitle.getText().toString().trim();
                    String shopName = mShopName.getText().toString().trim();
                    String contact = mContact.getText().toString().trim();
                    PhotoUploadHelperClass photo = new PhotoUploadHelperClass(title, shopName, contact,taskSnapshot.getUploadSessionUri().toString());
                    String photoId = databaseReference.push().getKey();
                    databaseReference.child(title).setValue(photo);
                    reference1.push().setValue(photo);

                    //Clear editText
                    mTitle.setText("");
                    mShopName.setText("");
                    mContact.setText("");
                    progressDialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploading..." + (int)progress +"%");
                }
            });
        }
        else{
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(),"No file selected",Toast.LENGTH_SHORT).show();
        }
    }

    //Choose photo
    private void choosePhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    //Confirm file selected or not && uploaded or not && click on btn upload or not
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            uploadPhotoUri = data.getData();
            Picasso.get().load(uploadPhotoUri).into(uploadPhoto);
        }
    }
}