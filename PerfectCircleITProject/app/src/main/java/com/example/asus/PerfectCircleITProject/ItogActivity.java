package com.example.asus.PerfectCircleITProject;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ItogActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private String difficulty;
    private CreatedImageShapes shapesList;
    private String mark;
    private FirebaseFirestore database;
    private int numberOfGames=0;
    private String userEmailAdress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_itog);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        userEmailAdress = user.getEmail().substring(0, user.getEmail().indexOf("."));
        database = FirebaseFirestore.getInstance();
        //userData = database.getReference().child("Users").child(user.getEmail().substring(0, user.getEmail().indexOf(".")));
        DocumentReference docRef = database.collection("Users").document(userEmailAdress);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Map<String, Object> data1 = new HashMap<>();
                    data1=document.getData();
                    numberOfGames=Integer.parseInt((String)data1.get("numberOfGames"));
                    Log.d("DatabaseNews", ""+numberOfGames);
                    if (document.exists()) {
                        Log.d("DatabaseNews", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("DatabaseNews", "No such document");
                    }
                    updateDatabase();
                } else {
                    Log.d("DatabaseNews", "get failed with ", task.getException());
                }
            }
        });
        int res, i, j;
        ImageProcessor getpicture1 = new ImageProcessor();
        Intent receivedFromDraw = getIntent();
        difficulty = receivedFromDraw.getStringExtra("LevelHardness");
        shapesList = (CreatedImageShapes) receivedFromDraw.getSerializableExtra("ShapesList");
        TextView hardnessInfo = findViewById(R.id.hardnessinfo);
        hardnessInfo.setText("Сложность: "+ difficulty);
        Bitmap created = Bitmap.createBitmap(getpicture1.w, getpicture1.h, Bitmap.Config.RGB_565);
        Bitmap drawn = Bitmap.createBitmap((getpicture1.w/2)+1, (getpicture1.h/2)+1, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(created);
        Canvas canvaz = new Canvas();
        canvas.drawColor(Color.WHITE);
        canvas = shapesList.createCanvas(20, created);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(created, (getpicture1.w/2)+1, (getpicture1.h/2)+1, false);
        boolean[][] pic = new boolean[getpicture1.w][getpicture1.h];
        for(i=0;i<getpicture1.w;i++){
            for(j=0;j<getpicture1.h;j++){
                if(created.getPixel(i, j)==Color.BLACK){
                    pic[i][j]=true;
                } else {
                    pic[i][j]=false;
                }
            }
        }
        getpicture1.picture1=pic;
        new Thread(new Runnable() {
            public void run() {
                int result;
                TextView ocenka = findViewById(R.id.ozenka);
                ImageProcessor getpicture = new ImageProcessor();
                result=getpicture.compare4();
                ocenka.setText("ОЦЕНКА: " + result);
                mark = new String();
                ocenka = findViewById(R.id.ozenka);
                mark = (ocenka.getText()).toString();
            }}).start();
        /*Paint paint = new Paint ();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(Color.BLACK);
        canvas.drawRect(shapesList.rectangleL, shapesList.rectangleT, shapesList.rectangleR, shapesList.rectangleB,  paint);*/
        for(i=0;i<getpicture1.w;i++){
            for(j=0;j<getpicture1.h;j++){
                /*if(getpicture1.picture1[i][j]){
                    created.setPixel(i/2, j/2, Color.BLACK);
                } else {
                    created.setPixel(i/2, j/2, Color.WHITE);
                }*/
                if(getpicture1.picture2[i][j]){
                    drawn.setPixel(i/2, j/2, Color.BLACK);
                } else {
                    drawn.setPixel(i/2, j/2, Color.WHITE);
                }
            }
        }
        ImageView createdimage = findViewById(R.id.created);
        ImageView drawnimage = findViewById(R.id.drawn);
        createdimage.setImageBitmap(scaledBitmap);
        drawnimage.setImageBitmap(drawn);
        //mark = new String();
        //TextView ocenka = findViewById(R.id.ozenka);
        //mark = (ocenka.getText()).toString();
    }

    public void updateDatabase(){
        Log.d("DatabaseNews", "BEFOOORE"+numberOfGames);
        numberOfGames=numberOfGames+1;
        Log.d("DatabaseNews", "aaand"+numberOfGames);
        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
        Map<String, String> data = new HashMap<>();
        data.put("numberOfGames", ""+numberOfGames);
        data.put(""+numberOfGames+"Mark", mark.substring(8, mark.length()));
        data.put(""+numberOfGames+"Date", timeStamp);
        data.put(""+numberOfGames+"Difficulty", difficulty);
        database.collection("Users").document(userEmailAdress).set(data, SetOptions.merge());
    }

    public void onBackPressed() {
        /*
        super.onBackPressed();
        nothingHappens!
        */
    }

    public void backToMenuMethod(View view) {
        Intent MainActivity = new Intent(ItogActivity.this, MainActivity.class);
        startActivity(MainActivity);
    }
}
