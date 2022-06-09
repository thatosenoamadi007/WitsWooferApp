package com.example.witswooferapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class my_Friends_Activity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    private myFriendAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);

        firebaseAuth = FirebaseAuth.getInstance();

        recyclerView = (RecyclerView)findViewById(R.id.myFriendsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final String email1=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        final String myemail=email1.replace("@gmail.com","");
        FirebaseRecyclerOptions<friendModel> options =
                new FirebaseRecyclerOptions.Builder<friendModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("MyFriends").child(myemail), friendModel.class)//.orderByChild("modName").equalTo("APHY8010")
                        .build();
        recyclerView.getRecycledViewPool().clear();
        mainAdapter = new myFriendAdapter(options);
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