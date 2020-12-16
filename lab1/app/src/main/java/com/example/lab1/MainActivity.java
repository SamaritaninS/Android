package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;




public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, ConvertActivity.class);
        switch(view.getId())
        {
            case R.id.distance_button:
                intent.putExtra("token", "distance");
                startActivity(intent);
                break;

            case  R.id.currency_button:
                intent.putExtra("token", "currency");
                startActivity(intent);
                break;

            case R.id.weight_button:
                intent.putExtra("token", "weight");
                startActivity(intent);
                break;
        }
    }

}