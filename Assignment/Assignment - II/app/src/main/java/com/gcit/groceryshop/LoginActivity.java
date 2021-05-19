package com.gcit.groceryshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText mEmail, mPassword;
    private Button login_btn;
    private FirebaseAuth firebaseAuth;
    private Context context = LoginActivity.this;
    private DatabaseReference reference;
    String licenseNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Users");

        mEmail = (EditText)findViewById(R.id.Email);
        mPassword = (EditText)findViewById(R.id.Password);
        login_btn = (Button) findViewById(R.id.login);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                if(!validateEmail() | !validatePassword()){
                    progressDialog.dismiss();
                    return;
                }
                else{
                    String email = mEmail.getText().toString().trim();
                    String password = mPassword.getText().toString().trim();
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
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                            else{
                                if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                    progressDialog.dismiss();
                                    finish();
                                    Intent intent = new Intent(LoginActivity.this,UserDashboardActivity.class);
                                    intent.putExtra("Email",email);
                                    intent.putExtra("License",licenseNo);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                                else{
                                    progressDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "Login Error, Email Verification required",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    //Email validation
    private boolean validateEmail() {
        String val = mEmail.getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(val.isEmpty()){
            mEmail.setError("Email Address is Required!");
            mEmail.requestFocus();
            return false;
        }
        else if(!val.matches(checkEmail)){
            mEmail.setError("Invalid Email Address!");
            mEmail.requestFocus();
            return false;
        }
        return true;
    }

    //Password validation
    private boolean validatePassword() {
        String val = mPassword.getText().toString().trim();
        if(val.isEmpty()){
            mPassword.setError("Password is Required!");
            mPassword.requestFocus();
            return false;
        }
        return true;
    }

//    //Checks user is login or not
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(firebaseAuth.getCurrentUser() != null){
//            finish();
//            startActivity(new Intent(this,UserDashboardActivity.class));
//        }
//    }

    //Go to Register page
    public void GoToRegisterPage(View view) {
        startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
    }
}