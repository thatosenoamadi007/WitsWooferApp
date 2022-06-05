package com.example.witswooferapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class HomePage extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private myPostAdapter mainAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        getWindow().setStatusBarColor(ContextCompat.getColor(HomePage.this, R.color.white));

        firebaseAuth = FirebaseAuth.getInstance();


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.profile:
                    startActivity(new Intent(HomePage.this, profile.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.home:
                    return true;
                case R.id.chat:
                    startActivity(new Intent(HomePage.this, chat.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.search:
                    startActivity(new Intent(HomePage.this, SearchUserActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });

        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewAllPosts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Post> options =
                new FirebaseRecyclerOptions.Builder<Post>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("AllPosts"), Post.class)//.orderByChild("modName").equalTo("APHY8010")
                        .build();
        recyclerView.getRecycledViewPool().clear();
        mainAdapter = new myPostAdapter(options);
        recyclerView.setAdapter(mainAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.profileMenu){

        }else if(item.getItemId()==R.id.signOutMenu){
            firebaseAuth.signOut();
            startActivity(new Intent(HomePage.this, Login.class));
            finish();
        }
        return true;
    }
}