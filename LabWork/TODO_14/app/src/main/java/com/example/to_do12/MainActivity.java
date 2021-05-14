package com.example.to_do12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    public static final String LOG = MainActivity.class.getName();
    FloatingActionButton floatingActionButton;
    String list;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.Order:
                Intent intent = new Intent(getApplicationContext(),OrderdActivity.class);
                intent.putExtra("list", list);
                startActivity(intent);
                return true;
            case R.id.Status:
                displayToast("You Selected Status");
                return true;
            case R.id.Favourite:
                displayToast("You Selected Favourites");
                return true;
            case R.id.Contact:
                displayToast("You Selected Contact");
                return true;

        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.tooblar);
        setSupportActionBar(toolbar);


        floatingActionButton = (FloatingActionButton) findViewById(R.id.callFloatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),OrderdActivity.class);
                intent.putExtra("list", list);
                startActivity(intent);
            }
        });
    }

    public void donut(View view) {
        String donut = "You ordered a donut";
        list = donut;
        Toast.makeText(this,"You ordered a donut",Toast.LENGTH_SHORT).show();
    }

    public void ice_cream(View view) {
        String ice = "You ordered a ice cream";
        list = ice;
        Toast.makeText(this,"You ordered a ice cream",Toast.LENGTH_SHORT).show();
    }

    public void froyo(View view) {
        String froyo = "You ordered a froyo";
        list = froyo;
        Toast.makeText(this,"You ordered a froyo",Toast.LENGTH_SHORT).show();
    }
    private void displayToast(String pick_up) {
        Toast.makeText(getApplicationContext(),pick_up,Toast.LENGTH_LONG).show();
    }
}
