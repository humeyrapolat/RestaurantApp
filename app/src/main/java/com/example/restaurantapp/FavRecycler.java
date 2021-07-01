package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

public class FavRecycler extends AppCompatActivity {
    String favName, favPrice, favImage;


    public FavRecycler(String favName, String favPrice, String favImage) {
        this.favImage = favImage;
        this.favName = favName;
        this.favPrice = favPrice;

    }
    public FavRecycler() {
    }


    public String getFavImage() {
        return favImage;
    }


    public String getFavName() {
        return favName;
    }


    public String getFavPrice() {
        return favPrice;
    }

}