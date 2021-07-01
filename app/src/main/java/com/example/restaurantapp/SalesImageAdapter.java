package com.example.restaurantapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SalesImageAdapter extends RecyclerView.Adapter<SalesImageAdapter.SalesImageHolder> {

    private ArrayList<SalesImage> salesImagesList ;
    private Context context;

    public SalesImageAdapter(ArrayList<SalesImage> salesImagesList, Context context) {
        this.salesImagesList = salesImagesList;
        this.context = context;
    }

    @NonNull
    @Override
    public SalesImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.sales_images,parent,false);

        return new SalesImageHolder(v) ;
    }

    @Override
    public void onBindViewHolder(@NonNull SalesImageHolder holder, int position) {

//        holder.salesImage.setImageResource(salesImagesList.get(position).getSalesImage());
//        holder.salesName.setText(salesImagesList.get(position).getSalesName());
//        holder.salesPrice.setText(salesImagesList.get(position).getSalesPrice());


        if (salesImagesList.size() > 0) {
            Picasso.get().load(salesImagesList.get(position).getImageLink()).into(holder.salesImage);
            holder.salesName.setText(salesImagesList.get(position).getSalesName());

        }
    }

    @Override
    public int getItemCount() {
        return salesImagesList.size();
    }

    class SalesImageHolder extends RecyclerView.ViewHolder{
        ImageView salesImage;
        TextView salesName;
        LinearLayout salesLayout;

        public SalesImageHolder(@NonNull View itemView) {
            super(itemView);
            salesImage =(ImageView)itemView.findViewById(R.id.sales_images);
            salesName = (TextView)itemView.findViewById(R.id.sales_name);
            salesLayout = itemView.findViewById(R.id.sales_layout);

        }
    }
}
