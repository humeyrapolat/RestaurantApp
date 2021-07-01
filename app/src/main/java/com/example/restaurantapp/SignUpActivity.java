package com.example.restaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

public class SignUpActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    String userId;
    private FirebaseFirestore firestore;
    private DatabaseReference mReference;
    private HashMap<String, Object> mData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        mReference = FirebaseDatabase.getInstance().getReference();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(SignUpActivity.this
                    , MainActivity.class));
            finish();
        }
    }


    public void navigateUserToLogin(View view) {
        Intent intentToSignUpToLogin = new Intent(SignUpActivity.this,
                LoginActivity.class);
        startActivity(intentToSignUpToLogin);
    }

    //We can create user
    public void createUser(View view) {


        EditText signUp_editTxt_username = findViewById(R.id.SignUp_editTxt_username);
        String signUp_username = signUp_editTxt_username.getText().toString();

        EditText SignUp_editTxtEmail = findViewById(R.id.SignUp_editTxtEmail);
        String signUp_email = SignUp_editTxtEmail.getText().toString();

        EditText SignUp_editTxtpassword = findViewById(R.id.SignUp_editTxtPassword);
        String signUp_password = SignUp_editTxtpassword.getText().toString();

        EditText SignUp_editTxtConfirmPassword = findViewById(R.id.SignUp_editTxtConfirmPassword);
        String signUp_confirmPassword = SignUp_editTxtConfirmPassword.getText().toString();

        EditText SignUp_editTxtPhone = findViewById(R.id.SignUp_editTxtPhone);
        String signUp_phone =  SignUp_editTxtPhone.getText().toString();

        EditText SignUp_editTxtDOB = findViewById(R.id.SignUp_editTxtDOB);
        String signUp_dob =  SignUp_editTxtDOB.getText().toString();

        String img_url = "";

        GifImageView logo = findViewById(R.id.gifImageView);


        if (!signUp_password.equals(signUp_confirmPassword) && !TextUtils.isEmpty(signUp_confirmPassword) && !TextUtils.isEmpty(signUp_password)) {
            Toast.makeText(SignUpActivity.this, "Your password doesn't match...",
                    Toast.LENGTH_SHORT).show();
        } else {

            if (TextUtils.isEmpty(signUp_email)) {
                SignUp_editTxtEmail.setError("Email is required");
                return;
            }

            if (TextUtils.isEmpty(signUp_password)) {
                SignUp_editTxtpassword.setError("Password is required");
                return;
            }
            if (TextUtils.isEmpty(signUp_confirmPassword)) {
                SignUp_editTxtConfirmPassword.setError("Confirm password is required");
                return;
            }
            if (TextUtils.isEmpty(signUp_username)) {
                signUp_editTxt_username.setError("Username is required");
                return;
            }
            if(TextUtils.isEmpty(signUp_phone)){
                SignUp_editTxtPhone.setError("Phone Number is required");
                return;
            }
            if(TextUtils.isEmpty(signUp_dob)){
                SignUp_editTxtPhone.setError("Address is required");
                return;
            }




            // sign up the user firebase
            mAuth.createUserWithEmailAndPassword(signUp_email, signUp_password)
                    .addOnCompleteListener(SignUpActivity.this,
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        mUser = mAuth.getCurrentUser();
                                        Toast.makeText(SignUpActivity.this,
                                                "User created", Toast.LENGTH_SHORT).show();
                                        userId = mAuth.getCurrentUser().getUid();
                                        mData = new HashMap<>();
                                        mData.put("username", signUp_username);
                                        mData.put("email", signUp_email);
                                        mData.put("password", signUp_password);
                                        mData.put("phone", signUp_phone);
                                        mData.put("address", signUp_dob);
                                        //mData.put("URL", img_url);
                                        mData.put("userID", mUser.getUid());

                                        mReference.child("Users").child(mUser.getUid())
                                                .child("Profile")
                                                .setValue(mData).addOnCompleteListener
                                                (SignUpActivity.this, new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    //Toast.makeText(SignUpActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                        startActivity(new Intent(SignUpActivity.this
                                                , MainActivity.class));
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Error !" +
                                                        task.getException().getMessage()
                                                , Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
        }
    }
}