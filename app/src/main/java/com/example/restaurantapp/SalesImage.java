package com.example.restaurantapp;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class SalesImage {


    private String salesName, imageLink;


    public SalesImage(String salesName, String imageLink) {
        this.salesName = salesName;
        this.imageLink = imageLink;
    }

    public SalesImage() {
    }

    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

}
