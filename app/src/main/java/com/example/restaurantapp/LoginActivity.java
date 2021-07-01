package com.example.restaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFireStore;
    private TextView forgetPass;
//    private TabLayout tabLayout;
//    ViewPager viewPager;
//    float v=0;
//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mFireStore = FirebaseFirestore.getInstance();

        forgetPass = findViewById(R.id.forger_password_txtView);
    }

    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser()!= null){
            //Kullanıcı daha önceden griş yaptıysa yeniden giriş yapmasına gerek yok.
            Intent intentLoginToMain = new Intent(LoginActivity.this,
                    MainActivity.class);
            startActivity(intentLoginToMain);

        }
    }

    public void navigateUser(View view){
        Intent intentLoginToSignUp = new Intent(LoginActivity.this,SignUpActivity.class);
        startActivity(intentLoginToSignUp);
    }

    public  void navigateUser2(View view){
        Intent intentForgetPassword = new Intent(LoginActivity.this,MakeSelection.class);
        startActivity(intentForgetPassword);
    }



//
//    public void forgetPassword (){
//
//        forgetPass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                EditText resetMail = new EditText(view.getContext());
//                AlertDialog.Builder passwordReset = new AlertDialog.Builder(view.getContext());
//                passwordReset.setTitle("Reset Password");
//                passwordReset.setMessage("Enter your email to  received reset link");
//                passwordReset.setView(resetMail);
//
//                passwordReset.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int i) {
//
//                        String mail = resetMail.getText().toString();
//                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(LoginActivity.this,"Reset link sent to your email",Toast.LENGTH_SHORT).show();
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(LoginActivity.this,"Reset link is not sent"+ e.getMessage(),Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//                    }
//                });
//
//                passwordReset.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//
//                passwordReset.create().show();
//
//            }
//        });
//    }


    public void login(View view){

        EditText Login_editTxtEmail= findViewById(R.id.Login_editTxtEmail);
        String login_email = Login_editTxtEmail.getText().toString();

        EditText login_editTxtPassword = findViewById(R.id.Login_editTxtPassword);
        String login_password= login_editTxtPassword.getText().toString();

        Intent intentLoginToMain= new Intent(LoginActivity.this,
                MainActivity.class);



        if (TextUtils.isEmpty(login_email) && TextUtils.isEmpty(login_password)){
            Toast.makeText(LoginActivity.this, "Please fill in the blanks",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        else {

            if(TextUtils.isEmpty(login_email)){
                Login_editTxtEmail.setError("Email is required");
                return;
            }

            if(TextUtils.isEmpty(login_email)){
                login_editTxtPassword.setError("Password is required");
                return;
            }
        }

        mAuth.signInWithEmailAndPassword(login_email,login_password)
                .addOnCompleteListener(LoginActivity.this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){

                                    startActivity(intentLoginToMain);
                                } else {
                                    Toast.makeText(LoginActivity.this,"Error !"+
                                                    task.getException().getMessage()
                                            , Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
    }
}