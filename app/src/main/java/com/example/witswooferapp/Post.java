package com.example.witswooferapp;

import android.widget.ImageView;
import android.widget.TextView;

public class Post {
    private String post;
    private String caption;

    public Post(){}

    public Post(String post, String caption){
        this.post=post;
        this.caption=caption;

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
