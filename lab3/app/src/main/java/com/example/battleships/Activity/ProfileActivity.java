package com.example.battleships.Activity;


import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.battleships.Model.User;
import com.example.battleships.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    FirebaseDatabase db = FirebaseDatabase.getInstance("https://battleships-f50de-default-rtdb.firebaseio.com/");
    DatabaseReference reference = db.getReference("Users");
    Button imageChangeButton, nameChangeButton, saveAllButton;
    EditText newNameEdit;
    TextView emailText, winsText, gamesText;
    ImageView imageView;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedUri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private StorageTask storageTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imageChangeButton = findViewById(R.id.imageChangeButton);
        nameChangeButton = findViewById(R.id.nameChangeButton);
        saveAllButton = findViewById(R.id.saveAllButton);
        newNameEdit = findViewById(R.id.newNameEdit);
        emailText = findViewById(R.id.emailText);
        winsText = findViewById(R.id.winsText);
        gamesText = findViewById(R.id.gamesText);
        imageView = findViewById(R.id.imageView);

        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");

        emailText.setText(User.Email);
        newNameEdit.setText(User.Name);
        winsText.setText(User.Wins);
        gamesText.setText(User.Games);


        Picasso.get().load(User.Image).into(imageView);

        nameChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!User.Name.equals(newNameEdit.getText().toString()))
                {
                    reference.child(User.Name).setValue(null);
                    User.Name=newNameEdit.getText().toString();
                    reference.child(newNameEdit.getText().toString()).child("name").setValue(User.Name);
                    reference.child(newNameEdit.getText().toString()).child("email").setValue(User.Email);
                    reference.child(newNameEdit.getText().toString()).child("image").setValue(User.Image);
                    reference.child(newNameEdit.getText().toString()).child("password").setValue(User.Password);
                    reference.child(newNameEdit.getText().toString()).child("statistics").child("games").setValue(User.Games);
                    reference.child(newNameEdit.getText().toString()).child("statistics").child("wins").setValue(User.Wins);
                    Toast.makeText(ProfileActivity.this, "Updated!", Toast.LENGTH_LONG).show();
                }
            }
        });

        imageChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openFileFolder();
            }
        });

        saveAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void openFileFolder() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            selectedUri = data.getData();
            Picasso.get().load(selectedUri).into(imageView);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (selectedUri != null) {
            StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(selectedUri));
            storageTask = fileReference.putFile(selectedUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(ProfileActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                            Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                            while(!uri.isComplete());
                            Uri url = uri.getResult();

                            User.Image = url.toString();
                            reference.child(User.Name).child("image").setValue(User.Image );
                            Picasso.get().load(selectedUri).into(imageView);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Nothing selected", Toast.LENGTH_SHORT).show();
        }
    }
}