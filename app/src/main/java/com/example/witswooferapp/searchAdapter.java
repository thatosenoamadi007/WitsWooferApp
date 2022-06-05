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

import java.util.Random;

public class searchAdapter extends FirebaseRecyclerAdapter<friendModel, com.example.witswooferapp.searchAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    public searchAdapter(@NonNull FirebaseRecyclerOptions<friendModel> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull com.example.witswooferapp.searchAdapter.myViewHolder holder, int position, @NonNull friendModel friend) {

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
        holder.addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.goToChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, chat.class);
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
    public com.example.witswooferapp.searchAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userlayout,parent,false);
        return new com.example.witswooferapp.searchAdapter.myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView email, Degree ;
        ImageView imageView, addFriend, goToChat, viewProfile;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            email = (TextView)itemView.findViewById(R.id.usernameView);
            Degree = (TextView)itemView.findViewById(R.id.degreeView);
            imageView=(ImageView)itemView.findViewById(R.id.userProfileView);
            addFriend=(ImageView)itemView.findViewById(R.id.addFriendView);
            goToChat=(ImageView)itemView.findViewById(R.id.chatView);
            viewProfile=(ImageView)itemView.findViewById(R.id.profileView);
        }
    }
}

