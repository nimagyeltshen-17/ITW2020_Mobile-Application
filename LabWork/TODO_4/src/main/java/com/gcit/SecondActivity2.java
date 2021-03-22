package com.gcit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity2 extends AppCompatActivity {
    public static final String EXTRA_REPLY = "com.gcit";

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second2);
        editText = (EditText) findViewById(R.id.editTextTextPersonName2);

        Intent intent = getIntent();

        //Receiving message from MainActivity
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setText(message);
    }

    public void replyMessage(View view) {

        String reply = editText.getText().toString();

        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLY, reply);

        //Result_OK indicates response was successful
        setResult(RESULT_OK, replyIntent);

        //It close the activity and return to main activity
        finish();
    }
}