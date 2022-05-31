package com.example.witswooferapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class profile extends AppCompatActivity {
    private CircleImageView circleImageView;
    private EditText username;
    private Button saveBtn;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        circleImageView = findViewById(R.id.circleImageView);
        username = findViewById(R.id.username);
        saveBtn = findViewById(R.id.save_button);
        auth = FirebaseAuth.getInstance();

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                    if(ContextCompat.checkSelfPermission(profile.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(profile.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);

                    }else{
                        /*CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .start(this);*/
                    }
              }
            }
        });
    }

}