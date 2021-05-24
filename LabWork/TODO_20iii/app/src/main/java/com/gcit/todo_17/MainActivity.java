package com.gcit.todo_17;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int mScore_1;
    private int mScore_2;

    private TextView mScoreText1, mScoreText2;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== R.id.night_mode){
            int nightMode = AppCompatDelegate.getDefaultNightMode();
            if(nightMode == AppCompatDelegate.MODE_NIGHT_YES){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScoreText1 = findViewById(R.id.score_1);
        mScoreText2 = findViewById(R.id.score_2);

    }

    public void decreaseScore(View view){
        int viewID = view.getId();
        switch (viewID){
            case R.id.decreaseTeam1:
                mScore_1--;
                mScoreText1.setText(String.valueOf(mScore_1));
                break;

            case R.id.decreaseTeam2:
                mScore_2--;
                mScoreText2.setText(String.valueOf(mScore_2));
                break;
        }

    }

    public void increaseScore(View view){
        int viewID = view.getId();
        switch (viewID){
            case R.id.increaseTeam1:
                mScore_1++;
                mScoreText1.setText(String.valueOf(mScore_1));
                break;

            case R.id.increaseTeam2:
                mScore_2++;
                mScoreText2.setText(String.valueOf(mScore_2));
                break;
        }

    }
}