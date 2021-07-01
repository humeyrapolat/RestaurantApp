package com.example.restaurantapp;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends MakeSelection {

    FirebaseAuth mAuth;
    TextView resetMailTitle;
    EditText resetEmail;
    Button buttonresetMail;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        mAuth = FirebaseAuth.getInstance();

        resetMailTitle = findViewById(R.id.resetmail_edittxt);
        resetEmail = findViewById(R.id.reset_email);
        login = findViewById(R.id.login_button_two);
        buttonresetMail = findViewById(R.id.reset_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogin();
            }
        });

        buttonresetMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetpassword();
            }
        });

    }
    private void goToLogin(){
        Intent intentResetToLogin =  new Intent(ForgetPassword.this,
                LoginActivity.class);
        startActivity(intentResetToLogin);

    }

    private void resetpassword() {
        String mail = resetEmail.getText().toString().trim();

        if (mail.isEmpty()) {
            resetEmail.setError("Email is required");
            resetEmail.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgetPassword.this, "Check your email to reset your password", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ForgetPassword.this, "Try again, Something wrong happened", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}



