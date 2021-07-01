

package com.example.restaurantapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {


    private FirebaseAuth mAuth;
    Context context;
    List<Cart> cartList;


    FirebaseFirestore firestore;
    DocumentReference docRef;
    CollectionReference colRef;
    DatabaseReference databaseReference;
    SharedPreferences sharedPreferences;

    public static final String TAG = "TAG";

    public CartAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    public CartAdapter(List<Cart> cartList) {
        this.cartList = cartList;
    }


    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        return new CartHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {

        holder.cPrice.setText("Price : " + cartList.get(position).getCartPrice() + "$");
        holder.cTitle.setText(cartList.get(position).getCartTitle());
        holder.quantity.setText("Piece : " + cartList.get(position).getCartQuantity());
        Picasso.get().load(cartList.get(position).getCartImage()).into(holder.cURL);
        holder.deleteFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productName= cartList.get(position).getCartTitle();

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance()
                                        .getCurrentUser().getUid()).child("Cart List").child(productName).removeValue().
                                addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(context,productName+" removed successfully",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    class CartHolder extends RecyclerView.ViewHolder {

        ImageView deleteFromCart;
        TextView cTitle, cPrice, quantity;
        ImageView cURL;


        public CartHolder(@NonNull View itemView) {
            super(itemView);
            cTitle = itemView.findViewById(R.id.cart_title);
            cPrice = itemView.findViewById(R.id.favRecycler_price);
            deleteFromCart = itemView.findViewById(R.id.cancel);
            cURL = itemView.findViewById(R.id.cart_img);
            quantity = itemView.findViewById(R.id.favRecycler_quantity);


        }
    }



}