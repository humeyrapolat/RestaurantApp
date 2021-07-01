package com.example.restaurantapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import java.util.List;

public class ReservationRecyclerAdapter extends RecyclerView.Adapter<ReservationRecyclerAdapter.ReservationHolder>{

    private List<ReservationRecycler> reservationListt;
    private Context context;

    public ReservationRecyclerAdapter(List<ReservationRecycler> reservationRecyclers, Context context) {
        this.reservationListt = reservationRecyclers;
        this.context = context;
    }

    @NonNull
    @Override
    public ReservationRecyclerAdapter.ReservationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_reservation_recycler, parent, false);
        return new ReservationRecyclerAdapter.ReservationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationRecyclerAdapter.ReservationHolder holder, int position) {

        if (reservationListt.size()>0){
            holder.resDate.setText(reservationListt.get(position).getDate());
            holder.resTime.setText(reservationListt.get(position).getTime());
            holder.resNumPeople.setText(reservationListt.get(position).getNumOfPerson());
        }
    }
    @Override
    public int getItemCount() {
        return reservationListt.size();
    }

    class ReservationHolder extends RecyclerView.ViewHolder{

        TextView resDate,resTime,resNumPeople;

        public ReservationHolder(@NonNull View itemView) {
            super(itemView);

            resDate = itemView.findViewById(R.id.resDate);
            resTime = itemView.findViewById(R.id.resTime);
            resNumPeople = itemView.findViewById(R.id.res_number);
        }
    }
}