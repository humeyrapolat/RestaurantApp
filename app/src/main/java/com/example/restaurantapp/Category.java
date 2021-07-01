package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Category extends AppCompatActivity {

    private String name,image;


    public Category(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public Category() {

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}