package com.example.restaurantapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavRecyclerAdapter extends RecyclerView.Adapter<FavRecyclerAdapter.MyFavoriteHolder> {

    List<FavRecycler> favList;
    Context context;

    public FavRecyclerAdapter(List<FavRecycler> favList, Context context) {
        this.favList = favList;
        this.context = context;
    }

    @NonNull
    @Override
    public FavRecyclerAdapter.MyFavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_fav_recycler, parent, false);
        return new MyFavoriteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavRecyclerAdapter.MyFavoriteHolder holder, int position) {

        holder.favName.setText(favList.get(position).getFavName());
        holder.favPrice.setText(favList.get(position).getFavPrice());
        Picasso.get().load(favList.get(position).getFavImage()).into(holder.favImage);
    }
    @Override
    public int getItemCount() {
        return favList.size();
    }

    class MyFavoriteHolder extends RecyclerView.ViewHolder {

        ImageView deleteFromFav;
        TextView favName, favPrice;
        ImageView favImage;

        public MyFavoriteHolder(@NonNull View itemView) {
            super(itemView);

            favName = itemView.findViewById(R.id.fav_r_name);
            favPrice = itemView.findViewById(R.id.fav_r_price);
            favImage = itemView.findViewById(R.id.fav_r_img);
            deleteFromFav = itemView.findViewById(R.id.removeFavorites);

            deleteFromFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users")
                            .child(mAuth.getCurrentUser().getUid()).child("FavoritesList").child(favName.getText().toString());
                            db.removeValue();

                    DatabaseReference db2 = FirebaseDatabase.getInstance().getReference("Users")
                            .child(mAuth.getCurrentUser().getUid()).child("Favorites").child(favName.getText().toString());
                    db2.removeValue();
                }

            });
        }
    }
}
