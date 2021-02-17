package com.example.battleships.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.battleships.Model.User;
import com.example.battleships.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    Button signInButton, profileButton, gameCreateButton, gameFindButton;
    EditText gameIdEdit;
    FirebaseDatabase db = FirebaseDatabase.getInstance("https://battleships-f50de-default-rtdb.firebaseio.com/");
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signInButton = findViewById(R.id.signInButton);
        profileButton = findViewById(R.id.profileButton);
        gameCreateButton = findViewById(R.id.gameCreateButton);
        gameFindButton = findViewById(R.id.gameFindButton);
        gameIdEdit = findViewById(R.id.gameIdEdit);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        gameCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = db.getReference("games");
                User.Id = UUID.randomUUID().toString().substring(0, 5);
                User.Rank = "creator";
                User.EnemyRank = "guest";
                User.Status = "notReady";
                User.EnemyName = "";
                reference.child(User.Id).child("id").setValue(User.Id);
                reference.child(User.Id).child(User.Rank).child("name").setValue(User.Name);
                reference.child(User.Id).child(User.Rank).child("email").setValue(User.Email);
                reference.child(User.Id).child(User.Rank).child("action").setValue(User.Status);
                reference.child(User.Id).child(User.Rank).child("image").setValue(User.Image);
                reference.child(User.Id).child(User.EnemyRank).child("action").setValue("a");
                Intent intent = new Intent(getApplicationContext(), BattleActivity.class);
                startActivity(intent);
            }
        });

        gameFindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = gameIdEdit.getText().toString().trim();
                reference = db.getReference("games");
                Query checkUser = reference.orderByChild("id").equalTo(input);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            gameIdEdit.setError(null);
                            User.Rank = "guest";
                            User.EnemyRank = "creator";
                            User.Status = "notReady";
                            User.EnemyName = dataSnapshot.child(input).child("creator").child("name").getValue(String.class);
                            User.EnemyEmail = dataSnapshot.child(input).child("creator").child("email").getValue(String.class);
                            User.EnemyImage = dataSnapshot.child(input).child("creator").child("image").getValue(String.class);
                            User.Id = dataSnapshot.child(input).child("id").getValue(String.class);
                            reference.child(input).child(User.Rank).child("image").setValue(User.Image);
                            reference.child(input).child(User.Rank).child("name").setValue(User.Name);
                            reference.child(input).child(User.Rank).child("email").setValue(User.Email);
                            reference.child(input).child(User.Rank).child("action").setValue(User.Status);
                            Intent intent = new Intent(getApplicationContext(), BattleActivity.class);
                            startActivity(intent);
                        } else {
                            gameIdEdit.setError("No such room exist");
                            gameIdEdit.requestFocus();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}