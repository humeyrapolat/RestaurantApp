package com.example.restaurantapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MemoriesImageAdapter extends RecyclerView.Adapter<MemoriesImageAdapter.MemoriesHolder> {

    private  ArrayList<MemoriesImage> memoriesImagesList ;
    private  Context context;

    public MemoriesImageAdapter(ArrayList<MemoriesImage> memoriesImagesList, Context context) {
        this.memoriesImagesList = memoriesImagesList;
        this.context = context;
    }

    @NonNull
    @Override
    public MemoriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.memories_images,parent,false);
        return new MemoriesHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoriesHolder holder, int position) {

        if (memoriesImagesList.size() > 0) {
            Picasso.get().load(memoriesImagesList.get(position).getMemoriesImage()).into(holder.memoriesImage);
            holder.title.setText(memoriesImagesList.get(position).getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return memoriesImagesList.size();
    }

    class MemoriesHolder extends RecyclerView.ViewHolder{
            ImageView memoriesImage;
            TextView title;
            RelativeLayout memoriesLayout;


            public MemoriesHolder(@NonNull View itemView) {
                super(itemView);
                memoriesImage = itemView.findViewById(R.id.menuImage);
                title = itemView.findViewById(R.id.menuTitle);
                memoriesLayout = itemView.findViewById(R.id.relativeLayoutMemories);
            }
        }
}
