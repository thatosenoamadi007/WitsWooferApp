package com.example.witswooferapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private EditText registerEmail, passwordEmail, registerDegree;
    private Button signUp;
    private TextView tvMessage;
    private FirebaseAuth insFirebase;
    private ImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.white));
// A commit8
        insFirebase = FirebaseAuth.getInstance();

        registerEmail = findViewById(R.id.registerEmail);
        passwordEmail = findViewById(R.id.registerPassword);
        registerDegree = findViewById(R.id.registerDegree);
        signUp=findViewById(R.id.registerBtn);
        tvMessage= findViewById(R.id.tvMessage);
        profilePic = findViewById(R.id.registerProfilePic);

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPicture();
            }
        });

        tvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createUser();
            }

        });



    }



    private void selectPicture() {
        Intent intent=new Intent();
        intent.setType("*/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"SELECT PROFILE"),12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri imageUri;
        if(requestCode==12 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            profilePic.setImageURI(imageUri);
        }
        else{
            Toast.makeText(MainActivity.this, "resultcode:"+resultCode+"--- requetcde:"+requestCode, Toast.LENGTH_SHORT).show();
        }
    }

    private void createUser() {
        String email = registerEmail.getText().toString();
        String password = passwordEmail.getText().toString();
        String degree = registerDegree.getText().toString();

        if(TextUtils.isEmpty(email)){
            registerEmail.setError("Email cannot be empty");
            registerEmail.requestFocus();
        }else if(TextUtils.isEmpty(password)){
            passwordEmail.setError("Password cannot be empty");
            passwordEmail.requestFocus();
        }else{
            insFirebase.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(MainActivity.this,"User registered successfully",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,Login.class));
                    }else{
                        Toast.makeText(MainActivity.this,"Registration Error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                    }
                }
            });


        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        friendModel friend = new friendModel(email, degree);
        databaseReference.setValue(friend);


    }


}