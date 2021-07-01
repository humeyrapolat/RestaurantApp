package com.example.restaurantapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FirstFragment extends Fragment {

    private View view;
    private SalesImageAdapter adapterSales;
    private MemoriesImageAdapter adapterMemories;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFireStore;
    private  ImageView onSales,memories__explain;
    private TextView restaurantApp;
    private ArrayList<SalesImage> salesList;
    private ArrayList<MemoriesImage> memoriesList;
    private TextView btnMem;
    private Button makeReservation;
    HashMap<String, String> map;
    private DatabaseReference newRef;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_first, container, false);
        mAuth = FirebaseAuth.getInstance();
        mFireStore= FirebaseFirestore.getInstance();
        onSales = view.findViewById(R.id.sales_image_explain);
        onSales = view.findViewById(R.id.sales_image_explain);
        map = new HashMap<>();
        makeReservation =view.findViewById(R.id.makeReservation);
        makeReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SeeMemories.class));
            }
        });

        btnMem=view.findViewById(R.id.btnMem);
        btnMem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SeeMemories.class));
            }
        });

        makeReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Reservation.class));
            }
        });


        salesList = new ArrayList<>();
        memoriesList= new ArrayList<>();



        RecyclerView mRecyclerView_sales = view.findViewById(R.id.recyclerView_salesImages);
        adapterSales = new SalesImageAdapter(salesList, getContext());
        mRecyclerView_sales.setHasFixedSize(true);

        newRef= FirebaseDatabase.getInstance().getReference();
        newRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //salesList.clear();

                HashMap<String,String > map=new HashMap<>();
                for(DataSnapshot ds : snapshot.child("New").getChildren()) {
                    for (DataSnapshot child : ds.getChildren()) {
                        map.put(child.getKey(),(String)child.getValue());
                    }
                    salesList.add(new SalesImage((String) map.get("Imagetitle"),(String) map.get("ImageLink")));
                    adapterSales.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        LinearLayoutManager managerSales = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView_sales.setLayoutManager(managerSales);
        mRecyclerView_sales.setAdapter(adapterSales);
        adapterSales.notifyDataSetChanged();

        //****************************************MEMORIES**************************************

        RecyclerView mRecyclerView_memories = view.findViewById(R.id.recyclerView_memoriesImages);
        adapterMemories = new MemoriesImageAdapter(memoriesList, getContext());
        mRecyclerView_memories.setHasFixedSize(true);

        CollectionReference collectionReference2 = mFireStore.collection("memories");
        collectionReference2.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null)
                    Toast.makeText(getActivity(),error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                else if (value != null) {
                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        Map<String, Object> map2 = snapshot.getData();
                        String URLMemories = (String) map2.get("ImageLinkMemories");
                        String titleMemories = (String) map2.get("ImageTitleMemories");

                        memoriesList.add(new MemoriesImage(URLMemories,titleMemories));
                        adapterMemories.notifyDataSetChanged();
                    }
                }
            }
        });

        LinearLayoutManager managerMemories = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        mRecyclerView_memories.setLayoutManager(managerMemories);
        mRecyclerView_memories.setAdapter(adapterMemories);

        return view;
    }
}