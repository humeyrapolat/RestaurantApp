package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity  {

    private FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    private ImageView btnCart;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
       // btnCart = findViewById(R.id.btnCart);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.fragment);


        Set<Integer> topLevelDestination = new HashSet<>();
        topLevelDestination.add(R.id.firstFragment);
        topLevelDestination.add(R.id.secondFragment);
        topLevelDestination.add(R.id.fourthFragment);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(topLevelDestination).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

    }




    public void logout(View view) {
        mAuth.signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void favorites(View view) {
        Intent intent = new Intent(this, MyFavorites.class);
        startActivity(intent);
    }

    public void goOrders(View view) {
        Intent intent = new Intent(this, SeeOrders.class);
        startActivity(intent);
    }

    public void notif(View view){
        Intent intent = new Intent(this, notification.class);
        startActivity(intent);
    }

    public void goCart(View view){

        Intent goCart = new Intent(this,CartDetail.class);
        startActivity(goCart);
    }


    public void goProfile(View view){

        Intent goProfile = new Intent(this,Profile.class);
        startActivity(goProfile);
    }


    public void goPolicy(View view){

        Intent policy = new Intent(this,Policy.class);
        startActivity(policy);
    }

    public void goReservation(View view){

        Intent reservation = new Intent(this,SeeMyReservation.class);
        startActivity(reservation);
    }




}