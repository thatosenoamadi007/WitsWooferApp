package com.example.witswooferapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Login extends AppCompatActivity {
    private TextView Email, Password;
    private Button signIn;
    private FirebaseAuth authInst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = findViewById(R.id.signInEmail);
        Password = findViewById(R.id.signInPassword);
        signIn = findViewById(R.id.signInBtn);
        authInst = FirebaseAuth.getInstance();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logInUser();
            }
        });


    }

    private void logInUser() {
        String email = Email.getText().toString();
        String password = Password.getText().toString();

        if(TextUtils.isEmpty(email)){
            Email.setError("Email cannot be empty");
            Email.requestFocus();
        }else if(TextUtils.isEmpty(password)){
            Password.setError("Password cannot be empty");
            Password.requestFocus();
        }else{
            authInst.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Login.this,"User registered successfully",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this,HomePage.class));
                    }else{
                        Toast.makeText(Login.this,"Registration Error: "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

    }

    }
