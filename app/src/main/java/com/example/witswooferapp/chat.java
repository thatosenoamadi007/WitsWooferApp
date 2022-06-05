package com.example.witswooferapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class chat extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getWindow().setStatusBarColor(ContextCompat.getColor(chat.this, R.color.white));

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.chat);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.profile:
                    startActivity(new Intent(chat.this, profile.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.chat:
                    return true;
                case R.id.home:
                    startActivity(new Intent(chat.this, HomePage.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.search:
                    startActivity(new Intent(chat.this, SearchUserActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });
    }
}