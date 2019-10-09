package com.example.asus.PerfectCircleITProject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ChooseWayActivity extends AppCompatActivity {
    String difficulty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_way);
        difficulty=new String();
        Intent receivedFromChooseLevel = getIntent();
        difficulty = receivedFromChooseLevel.getStringExtra("LevelHardness");
    }

    public void cameraMethod(View view) {
        Intent createImageActivity = new Intent(ChooseWayActivity.this, CreateImageActivity.class);
        createImageActivity.putExtra("TheWayYouDraw", "Camera");
        createImageActivity.putExtra("LevelHardness", difficulty);
        startActivity(createImageActivity);
    }

    public void screenMethod(View view) {
        Intent createImageActivity = new Intent(ChooseWayActivity.this, CreateImageActivity.class);
        createImageActivity.putExtra("TheWayYouDraw", "Screen");
        createImageActivity.putExtra("LevelHardness", difficulty);
        startActivity(createImageActivity);
    }
}
