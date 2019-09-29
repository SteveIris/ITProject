package com.example.asus.PerfectCircleITProject;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.*;

public class ChooseLevelActivity extends AppCompatActivity {
    Intent createImageActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_choose_level);
        createImageActivity = new Intent(ChooseLevelActivity.this, CreateImageActivity.class);
    }


    public void easyPlay(View view) {
        createImageActivity.putExtra("LevelHardness", "Easy");
        startActivity(createImageActivity);
    }

    public void hardPlay(View view) {
        createImageActivity.putExtra("LevelHardness", "Hard");
        startActivity(createImageActivity);
    }

    public void mediumPlay(View view) {
        createImageActivity.putExtra("LevelHardness", "Medium");
        startActivity(createImageActivity);
    }
}
