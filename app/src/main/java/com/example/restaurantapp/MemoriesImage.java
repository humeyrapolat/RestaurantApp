package com.example.restaurantapp;

import android.media.Rating;
import android.widget.TextView;

import java.util.ArrayList;

public class MemoriesImage {

      String title,memoriesImage;

    public MemoriesImage(String memoriesImage, String  title) {
        this.memoriesImage = memoriesImage;
        this.title = title;
    }


    public String getMemoriesImage() {
        return memoriesImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMemoriesImage(String memoriesImage) {
        this.memoriesImage = memoriesImage;
    }

}
