package com.gcit.todo_8_ii;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView tview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tview = findViewById(R.id.hey);
        Intent intent = getIntent();
        Uri message = intent.getData();
        if(message != null){
            String msg = message.toString();
            tview.setText(msg);
        }
    }
}