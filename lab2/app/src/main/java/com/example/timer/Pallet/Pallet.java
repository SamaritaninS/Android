package com.example.timer.Pallet;

import java.util.ArrayList;

public class Pallet {

    private String color;

    public Pallet(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public static ArrayList<Pallet> getPallet(){
        ArrayList<Pallet> pallets = new ArrayList<>();
        pallets.add(new Pallet("#FFD800"));
        pallets.add(new Pallet("#00FF21"));
        pallets.add(new Pallet("#00FFFF"));
        pallets.add(new Pallet("#0026FF"));
        pallets.add(new Pallet("#FF00DC"));
        pallets.add(new Pallet("#FFFFFF"));
        pallets.add(new Pallet("#7F0037"));
        return pallets;
    }
}