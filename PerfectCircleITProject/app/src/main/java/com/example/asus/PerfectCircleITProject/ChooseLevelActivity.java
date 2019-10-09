package com.example.asus.PerfectCircleITProject;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.*;

public class ChooseLevelActivity extends AppCompatActivity {
    Intent chooseWayActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_choose_level);
        chooseWayActivity = new Intent(ChooseLevelActivity.this, ChooseWayActivity.class);
    }


    public void easyPlay(View view) {
        chooseWayActivity.putExtra("LevelHardness", "Easy");
        startActivity(chooseWayActivity);
    }

    public void hardPlay(View view) {
        chooseWayActivity.putExtra("LevelHardness", "Hard");
        startActivity(chooseWayActivity);
    }

    public void mediumPlay(View view) {
        chooseWayActivity.putExtra("LevelHardness", "Medium");
        startActivity(chooseWayActivity);
    }
}
