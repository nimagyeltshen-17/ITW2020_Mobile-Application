package com.gcit.groceryshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserDashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mRecycleView;
    private ImageAdapter imageAdapter;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    String email, license;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        //Get data from dashboard
        email = getIntent().getStringExtra("Email");
        license = getIntent().getStringExtra("License");
        databaseReference = FirebaseDatabase.getInstance().getReference(license);

        //Instantiate
        firebaseAuth = FirebaseAuth.getInstance();
        drawerLayout = (DrawerLayout) findViewById(R.id.user_drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.user_navigation);
        toolbar = (Toolbar)findViewById(R.id.user_toolbar);
        mRecycleView = (RecyclerView) findViewById(R.id.user_recycle_view);

        //Set toolbar in user dashboard
        setSupportActionBar(toolbar);

        //Set Navigation bar
        navigationView.bringToFront();

        //Set open and close navigation
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.user_open,R.string.user_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //User Click button checking
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.user_navigation);

        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        Query check = databaseReference.orderByChild("title");
        FirebaseRecyclerOptions<PhotoUploadHelperClass> options =
                new FirebaseRecyclerOptions.Builder<PhotoUploadHelperClass>()
                        .setQuery(check,PhotoUploadHelperClass.class)
                        .build();
        imageAdapter = new ImageAdapter(options);
        mRecycleView.setAdapter(imageAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        imageAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        imageAdapter.stopListening();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.upload:
            Intent intent = new Intent(this,PhotoUploadActivity.class);
            intent.putExtra("Email",email);
            startActivity(intent);
            break;

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intentHome = new Intent(this,MainActivity.class);
                startActivity(intentHome);
                finish();
                break;
        }
        return true;
    }

//    //Checks user is login or not
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(firebaseAuth.getCurrentUser() == null){
//            finish();
//            startActivity(new Intent(this,PhotoUploadActivity.class));
//        }
//    }
}