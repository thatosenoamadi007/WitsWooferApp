package com.example.witswooferapp;

import android.widget.ImageView;
import android.widget.TextView;

public class Post {
    private String post;
    private String caption;
    private String username;

    public Post(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Post(String post, String caption, String username){
        this.post=post;
        this.caption=caption;
        this.username=username;

    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
