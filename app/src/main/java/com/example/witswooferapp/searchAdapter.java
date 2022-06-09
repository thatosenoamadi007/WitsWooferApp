package com.example.witswooferapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

public class searchAdapter extends FirebaseRecyclerAdapter<friendModel, com.example.witswooferapp.searchAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    public searchAdapter(@NonNull FirebaseRecyclerOptions<friendModel> options,Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull com.example.witswooferapp.searchAdapter.myViewHolder holder, int position, @NonNull friendModel friend) {

        holder.email.setText(friend.getEmail());
        holder.Degree.setText(friend.getDegree());

        //implement buttons
        Random rand = new Random();
        int randomNum = rand.nextInt((4 - 0) + 1) + 0;

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
        final String email1=FirebaseAuth.getInstance().getCurrentUser().getEmail();
         final String email=email1.replace("@gmail.com","");
        final String friendsEmail=friend.getEmail();
        final String addFriend=friendsEmail.replace("@gmail.com","");
        holder.addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("MyFriends").child(email).child(addFriend);
                friendModel friend1 = new friendModel(friend.getEmail(),friend.getDegree());//Adding the quiz question to the database
                databaseReference.setValue(friend1);
            }
        });
        final String email21=friend.getEmail();
        final String email2=email21.replace("@gmail.com","");
        holder.commonFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, commonFriends.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("compare_email",email2);
                context.startActivity(intent);
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
        ImageView imageView, addFriend, commonFriend, viewProfile;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            email = (TextView)itemView.findViewById(R.id.usernameView);
            Degree = (TextView)itemView.findViewById(R.id.degreeView);
            imageView=(ImageView)itemView.findViewById(R.id.userProfileView);
            addFriend=(ImageView)itemView.findViewById(R.id.addFriendView);
            commonFriend=(ImageView)itemView.findViewById(R.id.commonFriend);

        }
    }
}

