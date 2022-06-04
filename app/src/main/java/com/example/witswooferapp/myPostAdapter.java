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

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

public class myPostAdapter extends FirebaseRecyclerAdapter<Post, myPostAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
    */
    public myPostAdapter(@NonNull FirebaseRecyclerOptions<Post> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Post post) {

            Glide.with(holder.post.getContext())
                    .load(post.getPost())
                    .placeholder(R.drawable.ic_baseline_account_circle_24)
                    .error(R.drawable.ic_baseline_home_24)
                    .into(holder.post);

            holder.caption.setText(post.getCaption());

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_rc,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView caption;
        ImageView post;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            caption = (TextView)itemView.findViewById(R.id.caption);
            post=(ImageView)itemView.findViewById(R.id.post);
        }
    }
}
