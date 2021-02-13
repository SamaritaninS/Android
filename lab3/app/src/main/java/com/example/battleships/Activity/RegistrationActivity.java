package com.example.battleships.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.battleships.Model.User;
import com.example.battleships.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    FirebaseDatabase db = FirebaseDatabase.getInstance("https://battleships-f50de-default-rtdb.firebaseio.com/");
    DatabaseReference reference = db.getReference("Users");
    Button registrationButton, signInButton;
    EditText userNameEdit, emailEdit, passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        signInButton = findViewById(R.id.signInButton);
        registrationButton = findViewById(R.id.registrationButton);
        emailEdit = findViewById(R.id.emailEdit);
        passwordEdit = findViewById(R.id.passwordEdit);
        userNameEdit = findViewById(R.id.userNameEdit);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
            }
        });

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputName = userNameEdit.getText().toString();
                String inputEmail = emailEdit.getText().toString();
                String inputPassword = passwordEdit.getText().toString();
                reference.child(inputEmail).child("name").setValue(inputName);
                reference.child(inputEmail).child("email").setValue(inputEmail);
                reference.child(inputEmail).child("image").setValue("");
                reference.child(inputEmail).child("password").setValue(inputPassword);
                reference.child(inputEmail).child("statistics").child("wins").setValue("0");
                reference.child(inputEmail).child("statistics").child("games").setValue("0");
                User user = new User(inputName, inputEmail, inputPassword, "", "0", "0");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}