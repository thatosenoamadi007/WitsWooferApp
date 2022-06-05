package com.example.witswooferapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SearchUserActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);


        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.search);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.profile:
                    startActivity(new Intent(SearchUserActivity.this, profile.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.chat:
                    startActivity(new Intent(SearchUserActivity.this, chat.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.home:
                    startActivity(new Intent(SearchUserActivity.this, HomePage.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.search:
                    return true;
            }
            return false;
        });




    }


}