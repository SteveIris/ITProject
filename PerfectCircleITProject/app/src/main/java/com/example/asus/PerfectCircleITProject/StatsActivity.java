package com.example.asus.PerfectCircleITProject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.opencensus.stats.Stats;

public class StatsActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private RecyclerView listOfGames;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseFirestore database;
    private DocumentSnapshot userDoc;
    private int numberOfGames;
    private ArrayList <OneGameCard> oneGameCards;
    private String userEmailAdress;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        auth=FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseFirestore.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        userEmailAdress =user.getEmail().substring(0, user.getEmail().indexOf("."));
        storageRef = storage.getReference().child(userEmailAdress);
        oneGameCards = new ArrayList<>();
        Log.d("DatabaseNews", "Hell");
        DocumentReference docRef = database.collection("Users").document(userEmailAdress);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Log.d("DatabaseNews", "Hellooooooo");
                    if (document.exists()) {
                        Log.d("DatabaseNews", "DocumentSnapshot data: " + document.getData());
                        userDoc=document;
                        updateRecyclerView();
                    } else {
                        Log.d("DatabaseNews", "No such document");
                    }
                } else {
                    Log.d("DatabaseNews", "get failed with ", task.getException());
                }
            }
        });
        //oneGameCards.add(new OneGameCard("Test", "Test", "Testing"));
    }

    private void updateRecyclerView() {
        int i;
        Map<String, Object> data = new HashMap<>();
        data=userDoc.getData();
        numberOfGames=Integer.parseInt((String)data.get("numberOfGames"));
        Log.d("DatabaseNews", ""+numberOfGames);
        for(i=1;i<=numberOfGames;i++){
            if(!data.get(""+i+"Date").equals("DELETED")){
                oneGameCards.add(new OneGameCard(""+data.get(""+i+"Created"),""+data.get(""+i+"Drawn"),"Сложность: "+data.get(""+i+"Difficulty"), "Дата: " +data.get(""+i+"Date"), "Оценка: " +data.get(""+i+"Mark")));
            };
        };
        listOfGames=findViewById(R.id.listofgames);
        adapter = new RecyclerViewAdapter(oneGameCards);
        layoutManager=new LinearLayoutManager(this);
        listOfGames.setAdapter(adapter);
        listOfGames.setLayoutManager(layoutManager);
        listOfGames.addOnItemTouchListener(
                new RecyclerItemClickListener(this, listOfGames ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent oneGameActivity = new Intent(StatsActivity.this, OneGameActivity.class);
                        oneGameActivity.putExtra("position", position+1);
                        startActivity(oneGameActivity);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        //Nothing
                    }
                })
        );
    }
}
