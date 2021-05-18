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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    //All Variable Declaration
    private EditText mFullName, mEmail, mLicense, mAddress, mPassword, mConfirmPassword;
    private Button signup_btn;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private Context context = RegisterActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Instantiate the variables with Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Users");

        //Instantiate the variables with XML file
        mFullName = (EditText) findViewById(R.id.FullName);
        mEmail = (EditText) findViewById(R.id.Email);
        mLicense = (EditText) findViewById(R.id.LicenseNo);
        mAddress = (EditText) findViewById(R.id.Address);
        mPassword = (EditText) findViewById(R.id.Password);
        mConfirmPassword = (EditText) findViewById(R.id.ConfirmPassword);
        signup_btn = (Button) findViewById(R.id.signup_btn);

        //Create account
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setTitle("Please wait");
                progressDialog.setMessage("Registering...");
                progressDialog.show();
                if(!validateFullName() | !validateEmail() | !validateLicense() | !validateAddress() | !validatePassword()){
                    progressDialog.dismiss();
                    return;
                }
                else{
                    String fullName = mFullName.getText().toString().trim();
                    String email = mEmail.getText().toString().trim();
                    String license = mLicense.getText().toString().trim();
                    String address = mAddress.getText().toString().trim();
                    String password = mPassword.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                user.sendEmailVerification().addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        progressDialog.dismiss();
                                        RegisterHelperClass newUser = new RegisterHelperClass(fullName, email, license, address, password);
                                        reference.child(license).setValue(newUser);
                                        Toast.makeText(getApplicationContext(),"The verification link has been successfully sent to your Email" + email, Toast.LENGTH_LONG).show();
                                        mFullName.setText("");
                                        mEmail.setText("");
                                        mLicense.setText("");
                                        mAddress.setText("");
                                        mPassword.setText("");
                                        mConfirmPassword.setText("");
                                    }
                                });
                            }
                            else{
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }

    //Full Name validation
    private boolean validateFullName() {
        String val = mFullName.getText().toString().trim();
        if(val.isEmpty()){
            mFullName.setError("Name is Required!");
            mFullName.requestFocus();
            return false;
        }
        return true;
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
    //License validation
    private boolean validateLicense() {
        String val = mLicense.getText().toString().trim();
        if(val.isEmpty()){
            mLicense.setError("License number is Required!");
            mLicense.requestFocus();
            return false;
        }
        else if(val.length() != 6){
            mLicense.setError("Your license number should exactly six!");
            mLicense.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validateAddress() {
        String val = mAddress.getText().toString().trim();
        if(val.isEmpty()){
            mAddress.setError("Address is Required!");
            mAddress.requestFocus();
            return false;
        }
        return true;
    }

    //Password validation
    private boolean validatePassword() {
        String val = mPassword.getText().toString().trim();
        String val1 = mConfirmPassword.getText().toString().trim();
        if(val.isEmpty()){
            mPassword.setError("Password is Required!");
            mPassword.requestFocus();
            return false;
        }
        else if(val1.isEmpty()){
            mConfirmPassword.setError("Confirm Password is Required!");
            mConfirmPassword.requestFocus();
            return false;
        }
        else if(val.length() < 8) {
            mPassword.setError("Password is too short, it should be at least 8!");
            mPassword.requestFocus();
            return false;
        }
        else if(!val.equals(val1)){
            mConfirmPassword.setError("Confirm Password is didn't match");
            mConfirmPassword.requestFocus();
            return false;
        }
        return true;
    }

    public void GoToLoginPage(View view) {
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }
}