package com.example.restaurantapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.ButterKnife;


public class SecondFragment extends Fragment {

    private FirebaseDatabase database;
    private DatabaseReference categoryRef,menuRef;
    private FirebaseFirestore firestore;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerCategories;
    private RecyclerView recyclerItems;
    private FoodAdapter fAdapter;
    private List<Category> dataa;
    private TextView btncart;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_second, container, false);
        firestore = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        dataa = new ArrayList<>();

        recyclerCategories = view.findViewById(R.id.recycler_categories);
        recyclerItems = view.findViewById(R.id.recycler_food);
        btncart = view.findViewById(R.id.btnCart);
        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CartDetail.class));
            }
        });


        FoodCategoryAdapter cAdapter = new FoodCategoryAdapter(dataa, getContext(), new FoodCategoryAdapter.OnCategoryClick() {
            @Override
            public void onClick(int pos) {
                setFoodItem(pos);
            }
        });
        recyclerCategories.setHasFixedSize(true);

        categoryRef= FirebaseDatabase.getInstance().getReference();
        categoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                HashMap<String,String > map=new HashMap<>();
                for(DataSnapshot ds : snapshot.child("catgoriesName").getChildren()) {
                    for (DataSnapshot child : ds.getChildren()) {
                        map.put(child.getKey(),(String)child.getValue());
                    }
                    dataa.add(new Category((String) map.get("categoriesTitle"),(String) map.get("ImageLink")));
                    cAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayoutManager managerCategories = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerCategories.setLayoutManager(managerCategories);
        recyclerCategories.setAdapter(cAdapter);
        cAdapter.notifyDataSetChanged();


        setFoodItem(0);

        return view;
    }

    private void setFoodItem(int pos) {

        ArrayList<RestaurantMenu> foodItem = new ArrayList<>();
        CollectionReference collectionReference2;

        switch (pos) {

            case 6:

                menuRef= FirebaseDatabase.getInstance().getReference();
                menuRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        HashMap<String,String > map=new HashMap<>();
                        for(DataSnapshot ds : snapshot.child("Cake").getChildren()) {
                            for (DataSnapshot child : ds.getChildren()) {
                                map.put(child.getKey(),(String)child.getValue());
                            }
                            foodItem.add(new RestaurantMenu((String) map.get("Imagetitle"),(String)
                                    map.get("ImagePrice"),(String) map.get("ImageLink"),(String)map.get("ProductId")));
                            fAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            case 5:

                menuRef= FirebaseDatabase.getInstance().getReference();
                menuRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        HashMap<String,String > map=new HashMap<>();
                        for(DataSnapshot ds : snapshot.child("Pastry").getChildren()) {
                            for (DataSnapshot child : ds.getChildren()) {
                                map.put(child.getKey(),(String)child.getValue());
                            }
                            foodItem.add(new RestaurantMenu((String) map.get("Imagetitle"),(String)
                                    map.get("ImagePrice"),(String) map.get("ImageLink"),(String)map.get("ProductId")));
                            fAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

                break;

            case 4:
                menuRef= FirebaseDatabase.getInstance().getReference();
                menuRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        HashMap<String,String > map=new HashMap<>();
                        for(DataSnapshot ds : snapshot.child("Icecream").getChildren()) {
                            for (DataSnapshot child : ds.getChildren()) {
                                map.put(child.getKey(),(String)child.getValue());
                            }
                            foodItem.add(new RestaurantMenu((String) map.get("Imagetitle"),(String)
                                    map.get("ImagePrice"),(String) map.get("ImageLink"),(String)map
                                    .get("ProductId")));
                            fAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

                break;

            case 3:

                menuRef= FirebaseDatabase.getInstance().getReference();
                menuRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        HashMap<String,String > map=new HashMap<>();
                        for(DataSnapshot ds : snapshot.child("Drinks").getChildren()) {
                            for (DataSnapshot child : ds.getChildren()) {
                                map.put(child.getKey(),(String)child.getValue());
                            }
                            foodItem.add(new RestaurantMenu((String) map.get("Imagetitle"),(String) map.get("ImagePrice"),(String) map.get("ImageLink"),(String)map.get("ProductId")));
                            fAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            case 2:

                menuRef= FirebaseDatabase.getInstance().getReference();
                menuRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        HashMap<String,String > map=new HashMap<>();
                        for(DataSnapshot ds : snapshot.child("DesertSyrup").getChildren()) {
                            for (DataSnapshot child : ds.getChildren()) {
                                map.put(child.getKey(),(String)child.getValue());
                            }
                            foodItem.add(new RestaurantMenu((String) map.get("Imagetitle"),(String) map.get("ImagePrice"),(String) map.get("ImageLink"),(String)map.get("ProductId")));
                            fAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            case 1:

                menuRef= FirebaseDatabase.getInstance().getReference();
                menuRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        HashMap<String,String > map=new HashMap<>();
                        for(DataSnapshot ds : snapshot.child("DesertMilk").getChildren()) {
                            for (DataSnapshot child : ds.getChildren()) {
                                map.put(child.getKey(),(String)child.getValue());
                            }
                            foodItem.add(new RestaurantMenu((String) map.get("Imagetitle"),(String) map.get("ImagePrice"),(String) map.get("ImageLink"),(String)map.get("ProductId")));
                            fAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

//
                break;
            case 0:

                menuRef= FirebaseDatabase.getInstance().getReference();
                menuRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        HashMap<String,String > map=new HashMap<>();
                        for(DataSnapshot ds : snapshot.child("New").getChildren()) {
                            for (DataSnapshot child : ds.getChildren()) {
                                map.put(child.getKey(),(String)child.getValue());
                            }
                            foodItem.add(new RestaurantMenu((String) map.get("Imagetitle"),(String) map.get("ImagePrice"),(String) map.get("ImageLink"),(String)map.get("ProductId")));
                            fAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

                break;
        }

        fAdapter = new FoodAdapter(foodItem, getContext());
        LinearLayoutManager managerItems = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerItems.setLayoutManager(managerItems);
        recyclerItems.setAdapter(fAdapter);
    }


}