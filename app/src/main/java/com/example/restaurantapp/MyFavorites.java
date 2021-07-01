package com.example.restaurantapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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
import java.util.LinkedList;
import java.util.List;

public class MyFavorites extends AppCompatActivity {

    FirebaseAuth mAuth;
    List<FavRecycler> favoritesList;
    FavRecyclerAdapter favoriteAdapter;
    RecyclerView favRecycler;
    ImageView goSetting;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorites);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        String user = mAuth.getUid();
        goSetting = findViewById(R.id.goSetting);
        goSetting.setOnClickListener(view -> finish());


        favRecycler = findViewById(R.id.recycler_fav);
        favRecycler.setHasFixedSize(true);
        favoritesList = new ArrayList<>();
        LinearLayoutManager manager = new GridLayoutManager(this, 2);
        favRecycler.setLayoutManager(manager);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                favoritesList.clear();
                for (DataSnapshot dsp : snapshot.child("Users").child(user).child("FavoritesList").getChildren()) {
                    HashMap<String, Object> map = new HashMap<>();
                    for (DataSnapshot child : dsp.getChildren()) {
                        map.put(child.getKey(), (String) child.getValue());
                    }
                    String favName = (String) map.get("productTitle");
                    String favImage = (String) map.get("productImage");
                    String favPrice = (String) map.get("productPrice");

                    favoritesList.add(new FavRecycler(favName, "Price : "+favPrice + "$", favImage));
                }
                favoriteAdapter = new FavRecyclerAdapter(favoritesList, MyFavorites.this);
                favRecycler.setAdapter(favoriteAdapter);
                favoriteAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}