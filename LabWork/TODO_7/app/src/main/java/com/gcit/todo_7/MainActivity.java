 package com.gcit.todo_7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int count;
    private TextView countView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countView = (TextView) findViewById(R.id.count_display);
        if(savedInstanceState != null) {
            count = savedInstanceState.getInt("mCount");
            countView.setText(String.valueOf(count));
        }
    }

    public void count(View view) {
        count++;
        countView.setText(String.valueOf(count));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mCount",count);
    }
}