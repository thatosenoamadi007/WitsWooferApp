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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;
import java.util.UUID;

import javax.sql.StatementEvent;

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

        holder.deleteFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email1=FirebaseAuth.getInstance().getCurrentUser().getEmail();
                final String email=email1.replace("@gmail.com","");


                final String friendEmail = friend.getEmail();
                final String delFriend = friendEmail.replace("@gmail.com", "");
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query applesQuery = ref.child("MyFriends").child(email).child(delFriend);

                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
        /*holder.deleteFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email1= FirebaseAuth.getInstance().getCurrentUser().getEmail();
                final String email=email1.replace("@gmail.com","");
                final String randomKey = UUID.randomUUID().toString();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference dbreference = database.getReference("MyFriends");
                dbreference.child("email").removeValue();
            }
        });*/




    @NonNull
    @Override
    public com.example.witswooferapp.myFriendAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_friend_view,parent,false);
        return new com.example.witswooferapp.myFriendAdapter.myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView email, Degree ;
        ImageView imageView, deleteFriend;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            email = (TextView)itemView.findViewById(R.id.myFriendEmail);
            Degree = (TextView)itemView.findViewById(R.id.myFriendDegree);
            imageView=(ImageView)itemView.findViewById(R.id.myFriendProfilePic);
            deleteFriend=(ImageView)itemView.findViewById(R.id.deleteMyFriend);
        }
    }
}

