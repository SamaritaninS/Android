package com.example.battleships.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.battleships.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    }
}