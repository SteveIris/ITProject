package com.example.asus.PerfectCircleITProject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class OneGameActivity extends AppCompatActivity {

    private FirebaseFirestore database;
    private DocumentSnapshot userDoc;
    private String userEmailAdress;
    private FirebaseAuth auth;
    private int position;
    private boolean isDataReady=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_game);
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        userEmailAdress = user.getEmail().substring(0, user.getEmail().indexOf("."));
        Intent receivedFromHistory = getIntent();
        position = receivedFromHistory.getIntExtra("position", 1);
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
                        isDataReady=true;
                        fillLayout();
                    } else {
                        Log.d("DatabaseNews", "No such document");
                    }
                } else {
                    Log.d("DatabaseNews", "get failed with ", task.getException());
                }
            }
        });
    }

    private void fillLayout() {
        Map<String, Object> data = new HashMap<>();
        data = userDoc.getData();
        while(data.get(""+position+"Date").equals("DELETED")){
          position++;
        };
        TextView difficultyText = findViewById(R.id.hardness);
        TextView dateText = findViewById(R.id.date);
        TextView markText = findViewById(R.id.mark);
        final ImageView firstImage = findViewById(R.id.createdImage);
        ImageView secondImage = findViewById(R.id.drawnImage);
        int width = secondImage.getWidth();
        difficultyText.setText("Сложность: "+data.get(""+position+"Difficulty"));
        dateText.setText("Дата: "+data.get(""+position+"Date"));
        markText.setText("Оценка: "+data.get(""+position+"Mark"));
        Bitmap firstImageBitmap;
        Bitmap secondImageBitmap;
        Glide.with(firstImage.getContext()).load(data.get(""+position+"Created")).into(firstImage);
        Glide.with(secondImage.getContext()).load(data.get(""+position+"Drawn")).into(secondImage);
    }

    public void deleteGameMethod(View view) {
        if(isDataReady) {
            Map<String, String> data = new HashMap<>();
            data.put(""+position+"Date", "DELETED");
            database.collection("Users").document(userEmailAdress).set(data, SetOptions.merge());
            Intent historyActivity = new Intent(OneGameActivity.this, StatsActivity.class);
            startActivity(historyActivity);
        };
    }
}
