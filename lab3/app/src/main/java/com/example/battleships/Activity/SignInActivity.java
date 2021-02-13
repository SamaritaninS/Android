package com.example.battleships.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.battleships.Model.User;
import com.example.battleships.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {

    FirebaseDatabase db = FirebaseDatabase.getInstance("https://battleships-f50de-default-rtdb.firebaseio.com/");
    DatabaseReference reference = db.getReference("Users");
    Button signInButton, registrationButton;
    EditText emailEdit, passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        signInButton = findViewById(R.id.signInButton);
        registrationButton = findViewById(R.id.registrationButton);
        emailEdit = findViewById(R.id.emailEdit);
        passwordEdit = findViewById(R.id.passwordEdit);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!emailEdit.getText().toString().isEmpty() || !passwordEdit.getText().toString().isEmpty()) {
                    SignIn();
                }
            }
        });

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void SignIn() {
        final String inputEmail = emailEdit.getText().toString().trim();
        final String inputPassword = passwordEdit.getText().toString().trim();
        Query checkEmail = reference.orderByChild("email").equalTo(inputEmail);
        checkEmail.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    emailEdit.setError(null);
                    String passwordDB = dataSnapshot.child(inputEmail).child("password").getValue(String.class);
                    if (passwordDB.equals(inputPassword)) {
                        emailEdit.setError(null);
                        String nameDB = dataSnapshot.child(inputEmail).child("name").getValue(String.class);
                        String imageDB = dataSnapshot.child(inputEmail).child("image").getValue(String.class);
                        String winsDB = dataSnapshot.child(inputEmail).child("statistics").child("wins").getValue(String.class);
                        String gamesDB = dataSnapshot.child(inputEmail).child("statistics").child("games").getValue(String.class);
                        User user = new User(nameDB, inputEmail, inputPassword, imageDB, winsDB, gamesDB);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        passwordEdit.setError("Wrong Password");
                        passwordEdit.requestFocus();
                    }
                }
                else {
                    emailEdit.setError("Wrong email");
                    emailEdit.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}