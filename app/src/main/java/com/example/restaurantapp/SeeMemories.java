package com.example.restaurantapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class SeeMemories extends AppCompatActivity {

    View view;
    private AllMemoriesAdapter allMemoriesAdapter;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFireStore;
    private ArrayList<AllMemories> allMemoriesArrayList;
    private ImageView goHome;
    RecyclerView allMemoriesRecyclerview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_memories);
        mAuth = FirebaseAuth.getInstance();
        mFireStore= FirebaseFirestore.getInstance();
        allMemoriesArrayList = new ArrayList<>();
        RecyclerView allRecycler;
        goHome=findViewById(R.id.goBack4);
        goHome.setOnClickListener(view1 -> finish());


        allMemoriesRecyclerview = findViewById(R.id.allMemRecycler);
        allMemoriesAdapter = new AllMemoriesAdapter(allMemoriesArrayList,this);
        allMemoriesRecyclerview.setHasFixedSize(true);


        CollectionReference collectionReference2 = mFireStore.collection("memories");
        collectionReference2.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null)
                    Toast.makeText(SeeMemories.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                else if (value != null) {
                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        Map<String, Object> map2 = snapshot.getData();
                        String descMemories = (String) map2.get("ImageDescMemories");
                        String URLMemories = (String) map2.get("ImageLinkMemories");
                        String titleMemories = (String) map2.get("ImageTitleMemories");

                        allMemoriesArrayList.add(new AllMemories(titleMemories,descMemories,URLMemories));
                        allMemoriesAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        LinearLayoutManager managerMemories = new LinearLayoutManager(SeeMemories.this, LinearLayoutManager.HORIZONTAL, false);
        allMemoriesRecyclerview.setLayoutManager(managerMemories);
        allMemoriesRecyclerview.setAdapter(allMemoriesAdapter);


    }
}