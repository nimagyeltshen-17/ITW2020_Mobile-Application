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

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    //All Variable Declaration
    private EditText mFullName, mEmail, mLicense, mAddress, mContactNumber, mShopName, mPassword, mConfirmPassword;
    private Button signup_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Instantiate the variables with XML file
        mFullName = (EditText) findViewById(R.id.FullName);
        mEmail = (EditText) findViewById(R.id.Email);
        mLicense = (EditText) findViewById(R.id.LicenseNo);
        mAddress = (EditText) findViewById(R.id.Address);
        mShopName = (EditText) findViewById(R.id.ShopName);
        mContactNumber = (EditText) findViewById(R.id.ContactNumber);
        mPassword = (EditText) findViewById(R.id.Password);
        mConfirmPassword = (EditText) findViewById(R.id.ConfirmPassword);
        signup_btn = (Button) findViewById(R.id.signup_btn);

        //Create account
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateFullName() | !validateEmail() | !validateLicense() | !validateAddress() | !validateShopName() | !validateContact() | !validatePassword()){
                    return;
                }
                else{
                    String fullName = mFullName.getText().toString().trim();
                    String email = mEmail.getText().toString().trim();
                    String license = mLicense.getText().toString().trim();
                    String address = mAddress.getText().toString().trim();
                    String shopName = mShopName.getText().toString().trim();
                    String number = mContactNumber.getText().toString().trim();
                    String password = mPassword.getText().toString().trim();
                    Intent intent = new Intent(getApplicationContext(),RegisterImageUploadActivity.class);
                    intent.putExtra("FullName",fullName);
                    intent.putExtra("Email",email);
                    intent.putExtra("License",license);
                    intent.putExtra("Address",address);
                    intent.putExtra("ShopName",shopName);
                    intent.putExtra("Number",number);
                    intent.putExtra("Password",password);
                    startActivity(intent);
                    finish();
                    mFullName.setText("");
                    mEmail.setText("");
                    mLicense.setText("");
                    mAddress.setText("");
                    mShopName.setText("");
                    mContactNumber.setText("");
                    mPassword.setText("");
                    mConfirmPassword.setText("");

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

    //Validate Shop Name
    private boolean validateShopName() {
        String val = mShopName.getText().toString().trim();
        if(val.isEmpty()){
            mShopName.setError("Shop Name is Required!");
            mShopName.requestFocus();
            return false;
        }
        return true;
    }

    //Validate phone number
    private boolean validateContact() {
        String val = mContactNumber.getText().toString().trim();
        final Pattern BPHONE_NUMBER = Pattern.compile("[1][7][0-9]{6}",Pattern.CASE_INSENSITIVE);
        if(val.isEmpty()){
            mContactNumber.setError("Contact number is Required!");
            mContactNumber.requestFocus();
            return false;
        }
        else if(!BPHONE_NUMBER.matcher(val).matches()){
            mContactNumber.setError("Invalid Contact Number");
            mContactNumber.requestFocus();
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