package com.example.witswooferapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText registerEmail, passwordEmail;
    private Button signUp;
    private TextView tvMessage;
    private FirebaseAuth insFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.white));
// A commit8
        insFirebase = FirebaseAuth.getInstance();

        registerEmail = findViewById(R.id.registerEmail);
        passwordEmail = findViewById(R.id.registerPassword);
        signUp=findViewById(R.id.registerBtn);
        tvMessage= findViewById(R.id.tvMessage);

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

    private void createUser() {
        String email = registerEmail.getText().toString();
        String password = passwordEmail.getText().toString();

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

    }
}