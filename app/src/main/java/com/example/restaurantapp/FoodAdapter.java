package com.example.restaurantapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodHolder> {

    List<RestaurantMenu> data;
    Context context;
    int selectedItem = 0;
    Boolean favoriteChecker = false;
    FirebaseFirestore firestore;
    DatabaseReference databaseReference, fvrtRef, fvrt_listRef;
    FirebaseAuth mAuth;
    DatabaseReference cartListRef;


    public FoodAdapter(List<RestaurantMenu> data, Context context) {
        this.data = data;
        this.context = context;
    }


    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_restaurant_menu, parent, false);
        firestore = FirebaseFirestore.getInstance();
        cartListRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        fvrtRef = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Favorites");
        fvrt_listRef = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid()).child("FavoritesList");

        return new FoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodHolder holder, int position) {
        holder.price.setText(data.get(position).getPrice() + "$");
        holder.title.setText(data.get(position).getName());
        holder.setListener((view, adapterPosition) -> {
        });
        holder.favListener((view, position1) -> {
        });
        Picasso.get().load(data.get(position).getImage()).into(holder.image);

        if (selectedItem == position) {
            holder.cardView.animate().scaleX(1f);
            holder.cardView.animate().scaleY(1f);
            holder.title.setTextColor(context.getColor(R.color.darkred));
            holder.price.setTextColor(context.getColor(R.color.darkred));
            holder.rllBackground.setBackgroundResource(R.drawable.selected_category);
        } else {
            holder.cardView.animate().scaleX(0.9f);
            holder.cardView.animate().scaleY(0.9f);
            holder.title.setTextColor(Color.BLACK);
            holder.price.setTextColor(Color.BLACK);
            holder.rllBackground.setBackgroundResource(R.drawable.categories_bg);
        }

        holder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String saveCurrentTime, saveCurrentDate;
                Calendar calForDate = Calendar.getInstance();

                SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
                saveCurrentDate = currentDate.format(calForDate.getTime());

                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                saveCurrentTime = currentTime.format(calForDate.getTime());


                final HashMap<String, Object> cartMap = new HashMap<>();

                cartMap.put("productTitle", holder.title.getText().toString());
                cartMap.put("productPrice", holder.price.getText().toString().replace("$", ""));
                cartMap.put("productQuantity", holder.quantity.getText().toString());
                cartMap.put("productURL",data.get(position).getImage());
                cartMap.put("Time", saveCurrentTime);
                cartMap.put("Date", saveCurrentDate);

                String user = mAuth.getCurrentUser().getUid();

                databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(user).child("Cart List").child(holder.title.getText().toString());
                databaseReference.setValue(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, holder.title.getText().toString() + " Added", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



        String userid = mAuth.getCurrentUser().getUid();
        holder.favoriteChecker(userid);
        holder.btn_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String saveCurrentTime;
                Calendar calForDate = Calendar.getInstance();
                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                saveCurrentTime = currentTime.format(calForDate.getTime());

                final HashMap<String, Object> cartMap = new HashMap<>();

                favoriteChecker = true;
                fvrtRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (favoriteChecker.equals(true)) {
                            if (snapshot.hasChild(holder.title.getText().toString())) {
                                fvrtRef.child(holder.title.getText().toString()).removeValue();
                                delete();
                                favoriteChecker = false;
                            } else {
                                cartMap.put("productTitle", holder.title.getText().toString());
                                cartMap.put("productPrice", holder.price.getText().toString().replace("$", ""));
                                cartMap.put("productQuantity", holder.quantity.getText().toString());
                                cartMap.put("productImage", data.get(position).getImage());
                                cartMap.put("Time", saveCurrentTime);

                                fvrtRef.child(holder.title.getText().toString()).child(holder.title.getText().toString()).setValue(true);
                                fvrt_listRef.child(holder.title.getText().toString()).setValue(cartMap);
                                favoriteChecker = false;

                            }
                        }
                    }

                    private void delete() {

                        FirebaseDatabase.getInstance().getReference("Users").child(userid).child("FavoritesList")
                               .child(holder.title.getText().toString())
                                .removeValue();
                        holder.btn_favorites.setImageResource(R.drawable.fav);
                        fvrt_listRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    System.out.println(dataSnapshot);
                                    dataSnapshot.child(holder.title.getText().toString()).getRef().removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class FoodHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // RatingBar ratingBar;
        ImageView image, addItem, removeItem, btn_favorites;
        TextView title, price, quantity;
        RelativeLayout rllBackground;
        // RatingBar ratingBar;
        CardView cardView;
        int totalQuantity;
        MaterialCardView cart;
        IMaterialViewClickListener listener;
        FavClickListener favClickListener;
        private Unbinder unbinder;
        LinearLayout addMoreLayout;
        DatabaseReference favouriteref;

        public void setListener(IMaterialViewClickListener listener) {
            this.listener = listener;
        }

        public void favListener(FavClickListener favClickListener) {
            this.favClickListener = favClickListener;
        }

        public FoodHolder(View holder) {
            super(holder);

            // ratingBar = holder.findViewById(R.id.rating);
            title = holder.findViewById(R.id.food_title);
            image = holder.findViewById(R.id.food_img);
            price = holder.findViewById(R.id.txt_price);
            cardView = holder.findViewById(R.id.food_background);
            rllBackground = holder.findViewById(R.id.rll_background);
            cart = holder.findViewById(R.id.materialCardView);
            addItem = holder.findViewById(R.id.addItem);
            removeItem = holder.findViewById(R.id.removeItem);
            quantity = holder.findViewById(R.id.quantity);
            addMoreLayout = holder.findViewById(R.id.addMoreLayout);
            btn_favorites = holder.findViewById(R.id.favorites_button);
            //   ratingBar = holder.findViewById(R.id.rating);


            addItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (totalQuantity < 10) {
                        totalQuantity++;
                        quantity.setText(String.valueOf(totalQuantity));

                    }
                }
            });

            removeItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (totalQuantity > 1) {
                        totalQuantity--;
                        quantity.setText(String.valueOf(totalQuantity));
                    }
                }
            });

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedItem = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });



            unbinder = ButterKnife.bind(this, holder);
            holder.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            listener.onMaterialViewClick(view, getAdapterPosition());
        }


        public void favoriteChecker(String a) {
            btn_favorites = itemView.findViewById(R.id.favorites_button);
            favouriteref = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Favorites");

            favouriteref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(title.getText().toString())) {
                        btn_favorites.setImageResource(R.drawable.selectedfav);
                        //  Toast.makeText(context, "Favoried", Toast.LENGTH_SHORT).show();
                    } else {
                        btn_favorites.setImageResource(R.drawable.fav);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


        private void addToCart() {

        }
    }
}
