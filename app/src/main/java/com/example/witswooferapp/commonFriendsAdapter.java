package com.example.witswooferapp;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class commonFriendsAdapter extends RecyclerView.Adapter<commonFriendsAdapter.MyViewHolder>{
    Context context;
    ArrayList<friendModel> commonfriends;
    public commonFriendsAdapter (Context context, ArrayList<friendModel>commonfriends){
        this.context = context;
        this.commonfriends=commonfriends;

    }
    @NonNull
    @Override
    public commonFriendsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //assign a view or a layout to our row
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.common_friend_layout, parent, false);
        return new commonFriendsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull commonFriendsAdapter.MyViewHolder holder, int position) {
        //assign values to view
        holder.Email.setText(commonfriends.get(position).getEmail());
        holder.Degree.setText(commonfriends.get(position).getEmail());
        Random rand = new Random();
        int randomNum = rand.nextInt((5 - 0) + 1) + 0;
        switch (randomNum){
            case 0:
                holder.profilePic.setImageResource(R.drawable.profile1);
                break;
            case 1:
                holder.profilePic.setImageResource(R.drawable.profile2);
                break;
            case 2:
                holder.profilePic.setImageResource(R.drawable.profile3);
                break;
            case 3:
                holder.profilePic.setImageResource(R.drawable.profile4);
                break;
            case 4:
                holder.profilePic.setImageResource(R.drawable.profile5);
                break;
        }

    }

    @Override
    public int getItemCount() {
        //number of items
        return commonfriends.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //get the view or layout for our row
        ImageView profilePic, addFriend, goToProfile;
        TextView Email, Degree;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.commonFriendProfilePicView);
            Email = itemView.findViewById(R.id.commonFriendUsernameView);
            Degree = itemView.findViewById(R.id.commonFrienddegreeView);

        }
    }
}
