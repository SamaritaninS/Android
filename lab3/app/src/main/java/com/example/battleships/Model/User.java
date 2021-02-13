package com.example.battleships.Model;

import java.util.UUID;

public class User {
    public static String Name;
    public static String Email;
    public static String Password;
    public static String Image;
    public static String Games;
    public static String Wins;
    public static String Role;
    public static String Id;
    public static String Action;
    public static String EnemyName;
    public static String EnemyImage;
    public static String EnemyRole;
    public static String EnemyAction;

    public User(String name, String email, String password, String image, String games, String wins) {
        Name = name;
        Email = email;
        Password = password;
        Image = image;
        Wins = wins;
        Games = games;
    }

    public static void newId(){

    }
}
