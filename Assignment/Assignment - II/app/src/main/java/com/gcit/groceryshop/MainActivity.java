package com.gcit.groceryshop;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton addNewUser;
    private RecyclerView mRecycleView;
    private MainImageAdapter mainImageAdapter;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecycleView = (RecyclerView)findViewById(R.id.main_recycle_view);
        addNewUser = (FloatingActionButton)findViewById(R.id.addNewUser);

        addNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<RegisterHelperClass> options =
                new FirebaseRecyclerOptions.Builder<RegisterHelperClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users"),RegisterHelperClass.class)
                        .build();
        mainImageAdapter = new MainImageAdapter(options);
        mRecycleView.setAdapter(mainImageAdapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        mainImageAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainImageAdapter.stopListening();
    }
}