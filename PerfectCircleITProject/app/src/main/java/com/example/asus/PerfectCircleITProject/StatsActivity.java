package com.example.asus.PerfectCircleITProject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
        oneGameCards.add(new OneGameCard("HELOO", "YOLO", "WHADDAWEHAVERE"));
        oneGameCards.add(new OneGameCard("WEHAVEAWORK", "DONEHERE", "WEREALLYHAVE"));
        oneGameCards.add(new OneGameCard("OMG", "ITWORKS", "prbbly"));
        listOfGames=findViewById(R.id.listofgames);
        adapter = new RecyclerViewAdapter(oneGameCards);
        layoutManager=new LinearLayoutManager(this);
        listOfGames.setAdapter(adapter);
        listOfGames.setLayoutManager(layoutManager);
    }
}
