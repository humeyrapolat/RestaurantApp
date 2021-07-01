package com.example.restaurantapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllMemoriesAdapter extends RecyclerView.Adapter<AllMemoriesAdapter.AllMemoriesHolder> {


    private ArrayList<AllMemories> allList;
    private Context context;

    public AllMemoriesAdapter(ArrayList<AllMemories> allList, Context context) {
        this.allList = allList;
        this.context = context;
    }

    @NonNull
    @Override
    public AllMemoriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.activity_all_memories, parent, false);
        return new AllMemoriesAdapter.AllMemoriesHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AllMemoriesAdapter.AllMemoriesHolder holder, int position) {


        if (allList.size() > 0) {
            Picasso.get().load(allList.get(position).getAlllink()).into(holder.memoriesImage);
            holder.title.setText(allList.get(position).getAllName());
            holder.desc.setText(allList.get(position).getAllDesc());
//
        }

    }

    @Override
    public int getItemCount() {
        return allList.size();
    }


    class AllMemoriesHolder extends RecyclerView.ViewHolder {
        ImageView memoriesImage;
        TextView title, desc;
        public AllMemoriesHolder(@NonNull View itemView) {
            super(itemView);
            memoriesImage = itemView.findViewById(R.id.allMenuImage);
            title = itemView.findViewById(R.id.allMenuTitle);
            desc = itemView.findViewById(R.id.allMenuDesc);
        }
    }
}

