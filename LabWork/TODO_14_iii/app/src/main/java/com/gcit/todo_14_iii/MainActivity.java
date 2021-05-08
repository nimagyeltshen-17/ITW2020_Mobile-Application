package com.gcit.todo_14_iii;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showDatePicker(View view){
        DialogFragment newFragment = new com.example.todo_14_ii.DatePickerFragment();
        String tag;
        newFragment.show(getSupportFragmentManager(),  "datePicker");
    }

    public void processDatePickerResult(int year, int month, int day){
        int i;
        String month_string = Integer.toString( month + 1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);

        String date_message = (month_string + "/" + day_string +"/"+year_string);


        Toast.makeText( this, "Date:"+date_message, Toast.LENGTH_SHORT).show();
    }



}