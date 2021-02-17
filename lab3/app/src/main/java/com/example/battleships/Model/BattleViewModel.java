package com.example.battleships.Model;

import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.timgroup.jgravatar.Gravatar;
import com.timgroup.jgravatar.GravatarDefaultImage;
import com.timgroup.jgravatar.GravatarRating;

import java.util.ArrayList;

public class BattleViewModel extends ViewModel {
    FirebaseDatabase db = FirebaseDatabase.getInstance("https://battleships-f50de-default-rtdb.firebaseio.com/");;
    DatabaseReference reference = db.getReference("games");

    private final MutableLiveData<int[][]> shots = new MutableLiveData<new int[10][10]>();
    private final MutableLiveData<int[][]> shotsEnemy = new MutableLiveData<new int[10][10]>();
    ArrayList<String> ships = new ArrayList<>();
    private final MutableLiveData<Boolean[][]> shipsMy = new MutableLiveData<new Boolean[10][10]>();
    private final MutableLiveData<Boolean[][]> shipsEnemy = new MutableLiveData<new Boolean[10][10]>();
    EnumShips selectShip;
    int sizeShip;
    boolean iGo = false;
    boolean isBattle = false;

    public void setCell(Button cell, int i, int j, String buttonID){
        cell.setBackgroundColor(Color.BLUE);
        cell.setText(buttonID);
        cell.setTextColor(Color.BLUE);
        shipsMy[i][j] = false;
        shipsEnemy[i][j] = false;
        shots[i][j] = 0;
        shotsEnemy[i][j] = 0;
    }

    public  void checkUser(){
        if(User.Rank.equals("creator")){
            iGo = true;
        }
    }

    public void setShip(EnumShips ship){
        selectShip = ship;
    }

    public  void clickField(View v, Button[][] field, Button mainButton, Button largeShipButton, Button mediumShipButton, Button smallShipButton){
        if(!isBattle)
        {
            if (selectShip == EnumShips.NULL) {
                return;
            } else if(selectShip == EnumShips.SMALL) {
                sizeShip = EnumShips.SMALL.getSize();
            } else if(selectShip == EnumShips.MEDIUM) {
                sizeShip = EnumShips.MEDIUM.getSize();
            } else {
                sizeShip = EnumShips.LARGE.getSize();
            }
            fillField(((Button) v).getText().toString(), field);
            checkShips(largeShipButton, mediumShipButton, smallShipButton, mainButton);
        }else if(iGo){
            checkShot(((Button) v).getText().toString(), field,  mainButton);
        }
    }

    public  void changeTurn(Button mainButton, Button[][] field){
        String info = "Wait enemy";
        mainButton.setText(info);
        User.Status = "ready";
        reference.child(User.Id).child(User.Rank).child("action").setValue(User.Status);
        if(User.EnemyStatus.equals("ready")){
            isBattle = true;
            drawShips(mainButton, field);
        }
    }

    public void checkShot(String shot, Button[][] field, Button mainButton){
        String subStr = shot.substring(7, 9);
        char c = subStr.charAt(0);
        int Y = Character.getNumericValue(c);
        c = subStr.charAt(1);
        int X = Character.getNumericValue(c);
        if(shots[Y][X] != 0) {
            return;
        }
        if(shipsEnemy[Y][X])
        {
            field[Y][X].setBackgroundColor(Color.RED);
            field[Y][X].setTextColor(Color.RED);
            shipsEnemy[Y][X] = false;
            ships.remove(subStr);
            shots[Y][X] = 2;
        }else {
            field[Y][X].setBackgroundColor(Color.WHITE);
            field[Y][X].setTextColor(Color.WHITE);
            shots[Y][X] = 1;
        }
        reference.child(User.Id).child(User.Rank).child("action").setValue(shot);
        iGo = false;
        drawShips(mainButton, field);
        Victoty(mainButton);
    }

    public void Victoty(Button mainButton){
        if(ships.isEmpty()){
            mainButton.setText("You won!");
            mainButton.setEnabled(true);
            reference.child(User.Id).child(User.Rank).child("action").setValue("won");
            reference = db.getReference("users");
            Query checkName = reference.orderByChild("name").equalTo(User.Name);
            checkName.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String winsDB = dataSnapshot.child(User.Name).child("statistics").child("wins").getValue(String.class);
                        String gamesDB = dataSnapshot.child(User.Name).child("statistics").child("games").getValue(String.class);
                        int wins = Integer.parseInt(winsDB);
                        int games = Integer.parseInt(gamesDB);
                        wins++;
                        games++;

                        reference.child(User.Name).child("statistics").child("wins").setValue(String.valueOf(wins));
                        reference.child(User.Name).child("statistics").child("games").setValue(String.valueOf(games));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void checkShips(Button mainButton, Button largeShipButton, Button mediumShipButton, Button smallShipButton){
        largeShipButton.setEnabled(EnumShips.LARGE.getCount() != 0);
        mediumShipButton.setEnabled(EnumShips.MEDIUM.getCount() != 0);
        smallShipButton.setEnabled(EnumShips.SMALL.getCount() != 0);
        mainButton.setEnabled(EnumShips.LARGE.getCount() == 0 && EnumShips.MEDIUM.getCount() == 0 && EnumShips.SMALL.getCount() == 0);
        sizeShip = 0;
    }

    public void fillField(String nameButton, Button[][] field){
        String subStr = nameButton.substring(7, 9);
        char c = subStr.charAt(0);
        int Y = Character.getNumericValue(c);
        c = subStr.charAt(1);
        int X = Character.getNumericValue(c);
        for (int i=0; i<sizeShip; i++){
            if (shipsMy[Y][X] != shipsMy[Y][X + i]  || X+sizeShip>10) {
                return;
            }
        }
        if(sizeShip == 4)
        {
            EnumShips.LARGE.countMinus();
        }else if (sizeShip == 3){
            EnumShips.MEDIUM.countMinus();
        }else {
            EnumShips.SMALL.countMinus();
        }
        for (int i=0; i<sizeShip; i++){
            shipsMy[Y][X+i] = true;
            field[Y][X+i].setBackgroundColor(Color.BLACK);
            field[Y][X+i].setTextColor(Color.BLACK);
            String yx = String.valueOf(Y) + String.valueOf(X+i);
            reference.child(User.Id).child(User.Rank).child("ships").child(yx).setValue("a");
        }
        sizeShip=0;
    }

    public void fillOpponent(TextView opponentName, ImageView enemyImage){
        if(!User.EnemyName.equals("")){
            opponentName.setText(User.EnemyName);

            Gravatar gravatar = new Gravatar();
            gravatar.setSize(50);
            gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
            gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
            String url = gravatar.getUrl(User.EnemyEmail);
            url = new StringBuffer(url).insert(4, "s").toString();
            if(User.ImageType.equals("gravatar"))
                Picasso.get().load(url).into(enemyImage);
            else
                Picasso.get().load(User.Image).into(enemyImage);
        }
    }

    public void EnemyEventListener(Button mainButton, Button[][] field, TextView enemyName, ImageView enemyImage) {
        DatabaseReference checkRef = db.getReference("games/" + User.Id + "/" + User.EnemyRank );
        checkRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                opponentData();
                User.EnemyStatus = snapshot.child("action").getValue(String.class);
                User.EnemyName = snapshot.child("name").getValue(String.class);
                User.EnemyEmail = snapshot.child("email").getValue(String.class);
                if(User.EnemyStatus.equals("notReady")){
                    fillOpponent(enemyName, enemyImage);
                }else if(User.EnemyStatus.equals("won")){
                    mainButton.setEnabled(true);
                    mainButton.setText("You lost...");
                }else if(User.EnemyStatus.equals("ready")){
                    if(User.Status.equals("ready")){
                        drawShips(mainButton, field);
                        isBattle = true;
                    }
                }else if(User.EnemyStatus.contains("button")){
                    String substr = User.EnemyStatus.substring(7, 9);
                    char c = substr.charAt(0);
                    int Y = Character.getNumericValue(c);
                    c = substr.charAt(1);
                    int X = Character.getNumericValue(c);

                    if(shipsMy[Y][X])
                    {
                        shotsEnemy[Y][X] = 2;
                    }else {
                        shotsEnemy[Y][X] = 1;
                    }
                    iGo = true;
                    drawShips(mainButton, field);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void opponentData() {
        DatabaseReference ref = db.getReference("games").child(User.Id).child(User.EnemyRank).child("ships");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    fillData(postSnapshot.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void fillData(String data){
        char c = data.charAt(0);
        int Y = Character.getNumericValue(c);
        c = data.charAt(1);
        int X = Character.getNumericValue(c);
        shipsEnemy[Y][X] = true;
        ships.add(data);
    }

    public void drawShips(Button btnMain, Button[][] field){
        if(!iGo){
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if(shipsMy[i][j])
                    {
                        field[i][j].setBackgroundColor(Color.BLACK);
                        field[i][j].setTextColor(Color.BLACK);
                    }else {
                        if(shotsEnemy[i][j] == 0) {
                            field[i][j].setBackgroundColor(Color.BLUE);
                            field[i][j].setTextColor(Color.BLUE);
                        }
                    }
                    if(shotsEnemy[i][j] == 1) {
                        field[i][j].setBackgroundColor(Color.WHITE);
                        field[i][j].setTextColor(Color.WHITE);
                    }else  if (shotsEnemy[i][j] == 2) {
                        field[i][j].setBackgroundColor(Color.RED);
                        field[i][j].setTextColor(Color.RED);
                    }
                }
            }
            btnMain.setText("Enemy's turn");
        }else {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if(shots[i][j] == 0) {
                        field[i][j].setBackgroundColor(Color.BLUE);
                        field[i][j].setTextColor(Color.BLUE);
                    }     else    if(shots[i][j] == 1) {
                        field[i][j].setBackgroundColor(Color.WHITE);
                        field[i][j].setTextColor(Color.WHITE);
                    }else if (shots[i][j] == 2) {
                        field[i][j].setBackgroundColor(Color.RED);
                        field[i][j].setTextColor(Color.RED);
                    }
                }
            }
            btnMain.setText("Your turn");
        }
    }
}