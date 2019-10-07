package com.example.asus.PerfectCircleITProject;

import android.content.pm.ActivityInfo;
import android.graphics.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
    }

    public void playMethod(View view) {
        Intent ChooseLevelActivity = new Intent(MainActivity.this, ChooseLevelActivity.class);
        startActivity(ChooseLevelActivity);
    }

    public void exitMethod(View view) {
        finish();
    }

    public void statsMethod(View view) {
        Intent statsActivity = new Intent (MainActivity.this, StatsActivity.class);
        startActivity(statsActivity);
    }
}
