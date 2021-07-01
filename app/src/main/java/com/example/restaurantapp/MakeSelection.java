package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MakeSelection extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseFirestore mFireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_selection);

        mAuth = FirebaseAuth.getInstance();
        mFireStore= FirebaseFirestore.getInstance();

        Button resetmail = findViewById(R.id.makeSelection_email);

        resetmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetWithMail(view);
            }
        });

    }


    private void resetWithMail(View view){
        Intent makeSelectiontoResetMail = new Intent(MakeSelection.this,ForgetPassword.class);
        startActivity(makeSelectiontoResetMail);

    }

}