
package com.gcit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //private static final String LOG_TAG = MainActivity.class.getSimpleName();
    //Extra(exit in pairs) helps to pass information from one activity to another activity
    public static final String EXTRA_MESSAGE = "com.gcit";

    public static final int TEXT_REQUEST = 1;

    //Declaring variable of EditText
    private EditText mMessageEditText;
    private TextView mReplyHeadTextView;
    private TextView mReplyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Identifies the editText in the xml file
        mMessageEditText = (EditText) findViewById(R.id.editTextTextPersonName);
        mReplyHeadTextView = (TextView) findViewById(R.id.text_header_reply);
        mReplyTextView = (TextView) findViewById(R.id.text_message_reply);

    }

    public void launchedSecondActivity(View view) {
        //Log.d(LOG_TAG, "Button clicked!");
        //Creating obj for intent class
        Intent intent = new Intent(this,SecondActivity2.class);

        //Getting message from user as string
        String message = mMessageEditText.getText().toString();


        //helps in sending message to another activity
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivityForResult(intent, TEXT_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == TEXT_REQUEST){
            if(requestCode == RESULT_OK){
                String reply = data.getStringExtra(SecondActivity2.EXTRA_REPLY);
                mReplyHeadTextView.setVisibility(View.VISIBLE);
                mReplyTextView.setText(reply);
                mReplyTextView.setVisibility(View.VISIBLE);

            }
        }
    }
}