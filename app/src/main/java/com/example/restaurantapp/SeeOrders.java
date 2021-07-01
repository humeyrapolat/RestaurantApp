package com.example.restaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
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

public class SeeOrders extends AppCompatActivity {

    FirebaseAuth mAuth;
    List<OrderRecycler> orderRecyclerList;
    OrderRecyclerAdapter orderRecyclerAdapter;
    RecyclerView orderRecycler;
    DatabaseReference databaseReference;
    ImageView goBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_orders);

        mAuth = FirebaseAuth.getInstance();
        String user = mAuth.getUid();


        goBack = findViewById(R.id.goBack2);
        goBack.setOnClickListener(view -> finish());
        orderRecycler = findViewById(R.id.recycler_orders);
        orderRecycler.setHasFixedSize(true);
        orderRecyclerList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        orderRecycler.setLayoutManager(manager);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderRecyclerList.clear();
                for (DataSnapshot dsp : snapshot.child( user).child("Orders").getChildren()) {
                    HashMap<String, Object> map = new HashMap<>();
                    for (DataSnapshot child : dsp.getChildren()) {
                        map.put(child.getKey(), (String) child.getValue());
                    }
                    String date = (String) map.get("Date");
                    String time = (String) map.get("Time");
                    String price = (String) map.get("productPrice");

                    orderRecyclerList.add(new OrderRecycler("The time you created the order: " + time
                            , "The date you created the order: " + date, "Amount Paid: " + price + "$"));
                }
                orderRecyclerAdapter = new OrderRecyclerAdapter(orderRecyclerList, SeeOrders.this);
                orderRecyclerAdapter.notifyDataSetChanged();
                orderRecycler.setAdapter(orderRecyclerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}