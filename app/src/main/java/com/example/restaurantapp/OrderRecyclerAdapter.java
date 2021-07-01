package com.example.restaurantapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderRecyclerAdapter extends RecyclerView.Adapter<OrderRecyclerAdapter.OrderHolder> {


    List<OrderRecycler> orderRecyclerList;
    Context context;

    public OrderRecyclerAdapter(List<OrderRecycler> orderRecyclerList, Context context) {
        this.orderRecyclerList = orderRecyclerList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderRecyclerAdapter.OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_order_recycler, parent, false);
        return new OrderRecyclerAdapter.OrderHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull OrderRecyclerAdapter.OrderHolder holder, int position) {
        holder.orderDate.setText(orderRecyclerList.get(position).getOrderDate());
        holder.orderTime.setText(orderRecyclerList.get(position).getOrderTime());
        holder.orderTotal.setText(orderRecyclerList.get(position).getOrderTotal());

    }

    @Override
    public int getItemCount() {
        return orderRecyclerList.size();
    }

    class OrderHolder extends RecyclerView.ViewHolder{

        TextView orderTime,orderDate,orderTotal;

        public OrderHolder(@NonNull View itemView) {
            super(itemView);

            orderDate =itemView.findViewById(R.id.orderDate);
            orderTime =itemView.findViewById(R.id.orderTime);
            orderTotal =itemView.findViewById(R.id.orderTotal);

        }
    }
}
