package com.example.asus.PerfectCircleITProject;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ItogActivity extends AppCompatActivity {
    private String difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_itog);
        int res, i, j;
        ImageProcessor getpicture1 = new ImageProcessor();
        new Thread(new Runnable() {
            public void run() {
                ImageProcessor getpicture = new ImageProcessor();
                //getpicture.obrabotka3();
            }}).start();
        Intent receivedFromDraw = getIntent();
        difficulty = receivedFromDraw.getStringExtra("levelHardness");
        TextView hardnessInfo = findViewById(R.id.hardnessinfo);
        hardnessInfo.setText("Сложность: "+ difficulty);
        Bitmap created = Bitmap.createBitmap((getpicture1.w/2)+1, (getpicture1.h/2)+1, Bitmap.Config.RGB_565);
        Bitmap drawn = Bitmap.createBitmap((getpicture1.w/2)+1, (getpicture1.h/2)+1, Bitmap.Config.RGB_565);
        for(i=0;i<getpicture1.w;i++){
            for(j=0;j<getpicture1.h;j++){
                if(getpicture1.picture1[i][j]){
                    created.setPixel(i/2, j/2, Color.BLACK);
                } else {
                    created.setPixel(i/2, j/2, Color.WHITE);
                }
                if(getpicture1.picture2[i][j]){
                    drawn.setPixel(i/2, j/2, Color.BLACK);
                } else {
                    drawn.setPixel(i/2, j/2, Color.WHITE);
                }
            }
        }
        ImageView createdimage = findViewById(R.id.created);
        ImageView drawnimage = findViewById(R.id.drawn);
        createdimage.setImageBitmap(created);
        drawnimage.setImageBitmap(drawn);
    }


    public void onBackPressed() {
        /*
        super.onBackPressed();
        nothingHappens!
        */
    }

}
