
package com.example.lab1;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;




public class ConvertActivity extends AppCompatActivity {

    Spinner spinner1;
    Spinner spinner2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert);
        Intent intent = getIntent();
        int layoutId = 0;
        switch (intent.getStringExtra("token"))
        {
            case "distance": layoutId = R.array.spinnerItemDistance; break;
            case "weight": layoutId = R.array.spinnerItemWeight; break;
            case "currency": layoutId = R.array.spinnerItemCurrency; break;
        }

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, layoutId, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1 = findViewById(R.id.input_spinner);
        spinner2 = findViewById(R.id.output_spinner);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);

    }
}
