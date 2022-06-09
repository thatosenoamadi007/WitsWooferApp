package com.example.witswooferapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.StorageTask;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class profile extends AppCompatActivity {
    private CircleImageView circleImageView;
    private TextView username, degree;
    private EditText postCaption;
    private Button saveBtn, postBtn, deleteBtn, listFriends;
    private ImageView addPost;
    private FirebaseAuth auth;
    BottomNavigationView bottomNavigationView;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private RecyclerView recyclerView;
    private myPostAdapter mainAdapter;
    String email;
    String degreeS;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setStatusBarColor(ContextCompat.getColor(profile.this, R.color.white));

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.profile:
                    return true;
                case R.id.home:
                    startActivity(new Intent(profile.this, HomePage.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.search:
                    startActivity(new Intent(profile.this, SearchUserActivity.class));
                    overridePendingTransition(0, 0);
                    return true;

            }
            return false;
        });

        circleImageView = findViewById(R.id.circleImageView);
        username = findViewById(R.id.username);
        saveBtn = findViewById(R.id.save_button);
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef=storage.getReference();
        addPost = findViewById(R.id.addPost);
        postBtn = findViewById(R.id.postBtn);
        recyclerView = (RecyclerView)findViewById(R.id.myPostRecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postCaption = findViewById(R.id.postCaption);
        listFriends = findViewById(R.id.listFriends);

        recyclerView.setItemAnimator(null);

        setProfile();

        listFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profile.this, my_Friends_Activity.class);
                startActivity(intent);
            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPicture();
            }

        });

        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPost();
            }
        });

        FirebaseRecyclerOptions<Post> options =
                new FirebaseRecyclerOptions.Builder<Post>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Posts").child(FirebaseAuth.getInstance().getCurrentUser().getUid()), Post.class)//.orderByChild("modName").equalTo("APHY8010")
                        .build();
        recyclerView.getRecycledViewPool().clear();
        mainAdapter = new myPostAdapter(options);
        recyclerView.setAdapter(mainAdapter);

       getProfile();

    }



    private void getProfile(){FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            email = user.getEmail();
            username.setText(email);
            degreeS= user.getDisplayName();

        }
        /*FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        friendModel friend=snapshot.getValue(friendModel.class);

                        email=friend.getEmail();
                        degreeS=friend.getDegree();

                        username.setText(email);
                        degree.setText(degreeS);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(profile.this,"Couldnt retrieve user information.",Toast.LENGTH_SHORT).show();
                    }
                });*/
    }



    private void setProfile() {
        //final String userKey =FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        FirebaseDatabase.getInstance().getReference("ProfilePic")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue(String.class);
                        Glide.with(circleImageView.getContext())
                                .load(name)
                                .placeholder(R.drawable.ic_baseline_account_circle_24)
                                .circleCrop()
                                .error(R.drawable.ic_baseline_home_24)
                                .into(circleImageView);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        //circleImageView.setImageURI(Uri.parse("https://firebasestorage.googleapis.com/v0/b/witswooferapp-dfe72.appspot.com/o/images%2FM8NSuAv9tHaCNWLrYw2ASCYa0r32?alt=media&token=4fb9306a-f15d-4e31-acd7-1e506d246547"));
    }

    @Override
    protected void onStart(){
        super.onStart();
        mainAdapter.startListening();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            startActivity(new Intent(profile.this,Login.class));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }



    private void selectPicture() {
        Intent intent=new Intent();
        intent.setType("*/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"SELECT PROFILE"),12);
    }
    private void selectPost() {
        Intent intent=new Intent();
        intent.setType("*/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"SELECT POST"),10);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==12 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            circleImageView.setImageURI(imageUri);


            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    uploadPicture();
                }
            });
        }
        else if(requestCode==10 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            addPost.setImageURI(imageUri);
            postBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uploadPost();
                }
            });
        }
        else{
            Toast.makeText(profile.this, "resultcode:"+resultCode+"--- requetcde:"+requestCode, Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadPost() {
            final ProgressDialog pd=new ProgressDialog(this);
            pd.setTitle("File is loading.....");
            pd.show();
            final String randomKey = UUID.randomUUID().toString();
            final String userKey =FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
            StorageReference galleryPictures = storageRef.child("Posts/"+userKey)
                            .child(randomKey);
            galleryPictures.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isComplete()) ;
                            Uri uri = uriTask.getResult();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            Post post=new Post(uri.toString(),postCaption.getText().toString(),user.getEmail());
                            FirebaseDatabase.getInstance().getReference("Posts")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
                                    .child(randomKey)
                                    .setValue(post);
                            FirebaseDatabase.getInstance().getReference("AllPosts")
                                    .child(randomKey)
                                    .setValue(post);
                            Toast.makeText(profile.this, "Picture Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    });
        FirebaseRecyclerOptions<Post> options =
                new FirebaseRecyclerOptions.Builder<Post>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Posts").child(FirebaseAuth.getInstance().getCurrentUser().getUid()), Post.class)//.orderByChild("modName").equalTo("APHY8010")
                        .build();
        mainAdapter = new myPostAdapter(options);
        recyclerView.setAdapter(mainAdapter);
    }

    private void uploadPicture() {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("File is loading.....");
        progressDialog.show();
        final String userKey =FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        StorageReference galleryPictures = storageRef.child("images/"+userKey);
        galleryPictures.putFile(imageUri)
        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                  @Override
                                  public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                      Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                      while (!uriTask.isComplete()) ;
                                      Uri uri = uriTask.getResult();
                                      FirebaseDatabase.getInstance().getReference("ProfilePic")
                                              .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                              .setValue(uri.toString());
                                      Toast.makeText(profile.this, "Picture Uploaded", Toast.LENGTH_SHORT).show();
                                  }
                              });
    }






}





