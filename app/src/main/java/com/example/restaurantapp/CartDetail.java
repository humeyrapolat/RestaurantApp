package com.example.restaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CartDetail extends AppCompatActivity {

    public static final String TAG = "TAG";

    FirebaseAuth mAuth;
    List<Cart> cartList;
    CartAdapter adapter;
    TextView buyTotal;
    ImageView btnBack;
    RecyclerView cartRecycler;
    DatabaseReference databaseReference;
    Button completeOrder;
    HashMap<String, String> map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_detail);
        mAuth = FirebaseAuth.getInstance();
        completeOrder = findViewById(R.id.btn_order);
        btnBack = findViewById(R.id.btnBack);
        buyTotal = findViewById(R.id.total_price);
        String user = mAuth.getCurrentUser().getUid();
        btnBack.setOnClickListener(view -> finish());
        map = new HashMap<>();


        cartRecycler = findViewById(R.id.recycler_cart);
        cartRecycler.setHasFixedSize(true);
        cartList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        cartRecycler.setLayoutManager(manager);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartList.clear();

                for (DataSnapshot ds : snapshot.child(user).child("Cart List").getChildren()) {
                    for (DataSnapshot child : ds.getChildren()) {
                        map.put(child.getKey(), (String) child.getValue());
                    }
                    String cartTitle = (String) map.get("productTitle");
                    String cartQuantity = (String) map.get("productQuantity");
                    String cartPrice = (String) map.get("productPrice");
                    String cartURL = (String) map.get("productURL");

                    cartList.add(new Cart(cartTitle, cartQuantity, cartPrice,cartURL));
                }
                double sum = 0;
                for (Cart cart : cartList) {
                    sum += (Double.parseDouble(cart.getCartPrice())) * (Integer.parseInt(cart.getCartQuantity()));
                }
                buyTotal.setText(sum + "$");
                adapter = new CartAdapter(CartDetail.this, cartList);
                cartRecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    public void click(View view) {
        completeOrders();
        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance()
                        .getCurrentUser().getUid()).child("Cart List").removeValue();
    }

    private void completeOrders() {
        String saveCurrentTime, saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());


        final HashMap<String, Object> cartMap = new HashMap<>();

        cartMap.put("productPrice", buyTotal.getText().toString().replace("$", ""));
        cartMap.put("Time", saveCurrentTime);
        cartMap.put("Date", saveCurrentDate);

        String user = mAuth.getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(user).child("Orders").child(saveCurrentTime);
        databaseReference.setValue(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(CartDetail.this, " Order is successful", Toast.LENGTH_SHORT).show();
            }
        });

    }

}