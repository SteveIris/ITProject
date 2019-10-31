package com.example.asus.PerfectCircleITProject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {

    private RecyclerView listOfGames;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        ArrayList <OneGameCard> oneGameCards = new ArrayList<>();
        oneGameCards.add(new OneGameCard("Test", "Test", "Testing"));
        oneGameCards.add(new OneGameCard("Difficulty", "Date", "Mark"));
        oneGameCards.add(new OneGameCard("Wow", "ItReallyWorks", "NotBad"));
        listOfGames=findViewById(R.id.listofgames);
        adapter = new RecyclerViewAdapter(oneGameCards);
        layoutManager=new LinearLayoutManager(this);
        listOfGames.setAdapter(adapter);
        listOfGames.setLayoutManager(layoutManager);
    }
}
