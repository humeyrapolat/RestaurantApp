package com.example.restaurantapp;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FourthFragment extends Fragment {

    private Button orders,favorites,support,notification,reservation,logout;
    private View view;
    private FirebaseAuth mAuth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fourth, container, false);
        mAuth = FirebaseAuth.getInstance();

        orders = view.findViewById(R.id.orders_button);
        favorites = view.findViewById(R.id.favorites_button);
        support = view.findViewById(R.id.support_button);
        notification = view.findViewById(R.id.notification_button);
        reservation = view.findViewById(R.id.reservation_button);
        logout = view.findViewById(R.id.logout_button);

        return view;
    }



}