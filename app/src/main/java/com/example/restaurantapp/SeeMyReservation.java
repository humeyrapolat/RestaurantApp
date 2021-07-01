package com.example.restaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SeeMyReservation extends AppCompatActivity {

    FirebaseAuth mAuth;
    List<ReservationRecycler> reservationList;
    ReservationRecyclerAdapter reservationRecyclerAdapter;
    RecyclerView reservartionRecycler;
    DatabaseReference databaseReference;
    ImageView goBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_my_reservation);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        String user = mAuth.getCurrentUser().getUid();
        goBack = findViewById(R.id.goBackRes);
        goBack.setOnClickListener(view -> finish());


        reservartionRecycler = findViewById(R.id.recycler_reservation);
        reservartionRecycler.setHasFixedSize(true);
        reservationList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL,
                false);
        reservartionRecycler.setLayoutManager(manager);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reservationList.clear();
                for (DataSnapshot dsp : snapshot.child("Users").child(user).child("Reservation")
                        .getChildren()) {
                    HashMap<String, Object> map = new HashMap<>();
                    for (DataSnapshot child : dsp.getChildren()) {
                        map.put(child.getKey(), (String) child.getValue());
                    }
                    String date = (String) map.get("Date");
                    String time = (String) map.get("Time");
                    String people = (String) map.get("People");

                    reservationList.add(new ReservationRecycler("Reservation date: " + date ,
                            "Reservation Time: " + time ,
                            "Number of the people: " +people));
                }
                reservationRecyclerAdapter = new ReservationRecyclerAdapter(reservationList,
                        SeeMyReservation.this);
                reservationRecyclerAdapter.notifyDataSetChanged();
                reservartionRecycler.setAdapter(reservationRecyclerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}