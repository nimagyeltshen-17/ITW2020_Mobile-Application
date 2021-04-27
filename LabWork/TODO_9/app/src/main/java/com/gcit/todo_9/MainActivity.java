package com.gcit.todo_9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText editText1, editText2;
    private TextView textView;
    private Calculator cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = findViewById(R.id.editTextText1);
        editText2 = findViewById(R.id.editTextText2);
        cal = new Calculator();
        textView = findViewById(R.id.txtView);
    }
    public void Add(View view){
        String value1 = editText1.getText().toString();
        String value2 = editText2.getText().toString();
        double resultAdd = cal.add(Double.valueOf(value1), Double.valueOf(value2));
        textView.setText(String.valueOf(resultAdd));

        Log.d("LOG_TAG; ", "DEBUG MODE");
    }
    public void Sub(View view){
        String value1 = editText1.getText().toString();
        String value2 = editText2.getText().toString();
        double resultSub = cal.sub(Double.valueOf(value1), Double.valueOf(value2));
        textView.setText(String.valueOf(resultSub));
        Log.d("LOG_TAG; ", "DEBUG MODE");
    }

    public void Mul(View view){
        String value1 = editText1.getText().toString();
        String value2 = editText2.getText().toString();
        double resultMul = cal.mul(Double.valueOf(value1), Double.valueOf(value2));
        textView.setText(String.valueOf(resultMul));
        Log.d("LOG_TAG; ", "DEBUG MODE");
    }

    public void Div(View view){
        String value1 = editText1.getText().toString();
        String value2 = editText2.getText().toString();
        double resultDiv = cal.div(Double.valueOf(value1), Double.valueOf(value2));
        textView.setText(String.valueOf(resultDiv));
       Log.d("LOG_TAG; ", "DEBUG MODE");
    }

}
