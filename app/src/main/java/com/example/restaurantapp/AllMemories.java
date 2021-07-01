package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AllMemories extends AppCompatActivity {

    String allName,allDesc,alllink;

    public AllMemories(String allName, String allDesc, String alllink) {
        this.allName = allName;
        this.allDesc = allDesc;
        this.alllink = alllink;
    }

    public String getAllName() {
        return allName;
    }

    public void setAllName(String allName) {
        this.allName = allName;
    }

    public String getAllDesc() {
        return allDesc;
    }

    public void setAllDesc(String allDesc) {
        this.allDesc = allDesc;
    }

    public String getAlllink() {
        return alllink;
    }

    public void setAlllink(String alllink) {
        this.alllink = alllink;
    }
}