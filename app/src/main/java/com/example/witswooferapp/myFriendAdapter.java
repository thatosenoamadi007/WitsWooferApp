package com.example.witswooferapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;
import java.util.UUID;

public class myFriendAdapter extends FirebaseRecyclerAdapter<friendModel, com.example.witswooferapp.myFriendAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    public myFriendAdapter(@NonNull FirebaseRecyclerOptions<friendModel> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull com.example.witswooferapp.myFriendAdapter.myViewHolder holder, int position, @NonNull friendModel friend) {

        holder.email.setText(friend.getEmail());
        holder.Degree.setText(friend.getDegree());

        //implement buttons
        Random rand = new Random();
        int randomNum = rand.nextInt((5 - 0) + 1) + 0;

        switch (randomNum){
            case 0:
                holder.imageView.setImageResource(R.drawable.profile1);
                break;
            case 1:
                holder.imageView.setImageResource(R.drawable.profile2);
                break;
            case 2:
                holder.imageView.setImageResource(R.drawable.profile3);
                break;
            case 3:
                holder.imageView.setImageResource(R.drawable.profile4);
                break;
            case 4:
                holder.imageView.setImageResource(R.drawable.profile5);
                break;
        }
        holder.commonFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final  String randomKey= UUID.randomUUID().toString();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("MyFriends").child(FirebaseAuth.getInstance().getCurrentUser().getEmail()).child(randomKey);
                friendModel friend1 = new friendModel(friend.getEmail(),friend.getDegree());//Adding the quiz question to the database
                databaseReference.setValue(friend1);
            }
        });


        holder.viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, profile.class);
            }
        });

    }

    @NonNull
    @Override
    public com.example.witswooferapp.myFriendAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_friend_view,parent,false);
        return new com.example.witswooferapp.myFriendAdapter.myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView email, Degree ;
        ImageView commonFriend, imageView, goToChat, viewProfile;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            email = (TextView)itemView.findViewById(R.id.myFriendEmail);
            Degree = (TextView)itemView.findViewById(R.id.myFriendDegree);
            commonFriend=(ImageView)itemView.findViewById(R.id.commonFriend);
            imageView=(ImageView)itemView.findViewById(R.id.myFriendProfilePic);
            viewProfile=(ImageView)itemView.findViewById(R.id.myFriendProfile);
        }
    }
}

