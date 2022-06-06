package com.example.witswooferapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class commonFriends extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_friends);


        String email=getIntent().getStringExtra("compare_email");
        //String friend="";
        final String email1=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        final String myemail=email1.replace("@gmail.com","");
        //List<friendModel> myfriend = new ArrayList<>();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference friendRef = rootRef.child("MyFriends").child(myemail);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<friendModel> myfriend = new ArrayList<>();

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    friendModel friendModel = ds.getValue(friendModel.class);
                    myfriend.add(friendModel);

                }
                //for the second student
                DatabaseReference rootRef1 = FirebaseDatabase.getInstance().getReference();
                DatabaseReference friendRef1 = rootRef1.child("MyFriends").child(email);
                ValueEventListener valueEventListener1 = new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String friend="nothing";
                        List<friendModel> myfriend1 = new ArrayList<>();
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            friendModel friendModel = ds.getValue(friendModel.class);
                            myfriend1.add(friendModel);
                            //friend+=friendModel.getEmail();
                        }
                        if(!myfriend1.isEmpty()){


                            List<friendModel> commonfriends = new ArrayList<>();
                            for (friendModel first : myfriend){
                                for(friendModel second:myfriend1){
                                    if(first.getEmail().equals(second.getEmail())){
                                        commonfriends.add(first);
                                    }
                                }
                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                };

                friendRef1.addListenerForSingleValueEvent(valueEventListener1);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };
        friendRef.addListenerForSingleValueEvent(valueEventListener);


    }
}