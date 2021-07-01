package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class notification extends AppCompatActivity {

    TextView txt_not;
    SwitchCompat switch_notf;
    ImageView bactToSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        txt_not = findViewById(R.id.txt_notification);
        switch_notf = findViewById(R.id.switch_notf);
        bactToSetting = findViewById(R.id.bactToSetting);

        bactToSetting.setOnClickListener(view -> finish());
    }



}