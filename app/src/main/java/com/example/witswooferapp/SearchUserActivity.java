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
    private FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    private searchAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        firebaseAuth = FirebaseAuth.getInstance();

        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.search);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.profile:
                    startActivity(new Intent(SearchUserActivity.this, profile.class));
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

        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewAllUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<friendModel> options =
                new FirebaseRecyclerOptions.Builder<friendModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users"), friendModel.class)//.orderByChild("modName").equalTo("APHY8010")
                        .build();
        recyclerView.getRecycledViewPool().clear();
        mainAdapter = new searchAdapter(options,getApplicationContext());
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


}