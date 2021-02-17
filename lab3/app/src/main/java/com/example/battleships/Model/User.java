package com.example.battleships.Model;

import java.util.UUID;

public class User {
    public static String Name;
    public static String Email;
    public static String Password;
    public static String Image;
    public static String ImageType;
    public static String Games;
    public static String Wins;
    public static String Rank;
    public static String Id;
    public static String Status;
    public static String EnemyName;
    public static String EnemyEmail;
    public static String EnemyImage;
    public static String EnemyRank;
    public static String EnemyStatus;

    public User(String name, String email, String password, String imageType, String image, String games, String wins) {
        Name = name;
        Email = email;
        Password = password;
        ImageType = imageType;
        Image = image;
        Wins = wins;
        Games = games;
    }

    public static void newId(){

    }
}
