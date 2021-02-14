package com.example.battleships.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.battleships.Model.BattleViewModel;
import com.example.battleships.Model.EnumShips;
import com.example.battleships.Model.User;
import com.example.battleships.R;

public class BattleActivity extends AppCompatActivity {

    Button mainButton, largeShipButton, mediumShipButton, smallShipButton;
    TextView enemyName;
    ImageView enemyImage;
    TextView id;

    private final Button[][] field = new Button[10][10];

    private BattleViewModel battleViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        battleViewModel = ViewModelProvider.of(this).get(BattleViewModel.class);
        setContentView(R.layout.activity_battle);

        enemyName = findViewById(R.id.enemyName);
        id = findViewById(R.id.idText);
        id.setText(User.Id);
        enemyImage = findViewById(R.id.imageEnemy);
        battleViewModel.fillOpponent(enemyName, enemyImage);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                field[i][j] = findViewById(resID);
                field[i][j].setOnClickListener(this);
                battleViewModel.setCell(field[i][j], i, j, buttonID);
            }
        }

        battleViewModel.checkUser();

        smallShipButton = findViewById(R.id.smallShipButton);
        mediumShipButton = findViewById(R.id.mediumShipButton);
        largeShipButton = findViewById(R.id.largeShipButton);

        smallShipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                battleViewModel.setShip(EnumShips.SMALL);
            }
        });
        mediumShipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                battleViewModel.setShip(EnumShips.MEDIUM);
            }
        });

        largeShipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                battleViewModel.setShip(EnumShips.LARGE);
            }
        });

        mainButton = findViewById(R.id.mainButton);
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (User.Status.equals("notReady")) {
                    battleViewModel.changeTurn(mainButton, field);
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
        battleViewModel.checkShips(largeShipButton, smallShipButton, mediumShipButton, mainButton);
        battleViewModel.EnemyEventListener(mainButton, field, enemyName, enemyImage);
    }

    public void onClick(View v) {
        battleViewModel.clickField(v, field, mainButton, largeShipButton, smallShipButton, mediumShipButton);
    }
}