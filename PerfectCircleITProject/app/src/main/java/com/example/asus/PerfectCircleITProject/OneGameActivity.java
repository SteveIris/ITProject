package com.example.asus.PerfectCircleITProject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class OneGameActivity extends AppCompatActivity {

    private FirebaseFirestore database;
    private DocumentSnapshot userDoc;
    private String userEmailAdress;
    private FirebaseAuth auth;
    private int position;
    private boolean isDataReady=false;
    private ImageView secondImage;
    private Map<String, Object> data;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private String pointsScored;
    private String difficulty;
    private String date;
    private AlertDialog.Builder areYouSureWindow;
    private String userLogin="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_one_game);
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        userEmailAdress = user.getEmail().substring(0, user.getEmail().indexOf("."));
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference().child(userEmailAdress);
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
        data = new HashMap<>();
        data = userDoc.getData();
        userLogin = data.get("name").toString();
        while(data.get(""+position+"Date").equals("DELETED")){
          position++;
        };
        TextView difficultyText = findViewById(R.id.hardness);
        TextView dateText = findViewById(R.id.date);
        TextView markText = findViewById(R.id.mark);
        final ImageView firstImage = findViewById(R.id.createdImage);
        secondImage = findViewById(R.id.drawnImage);
        int width = secondImage.getWidth();
        pointsScored=""+data.get(""+position+"Mark");
        difficulty=""+data.get(""+position+"Difficulty");
        date=(""+data.get(""+position+"Date"));
        date=date.substring(0, 4)+"."+date.substring(4, 6)+"."+date.substring(6, 8);
        difficultyText.setText("Сложность: "+difficulty);
        dateText.setText("Дата: "+date);
        markText.setText("Оценка: "+pointsScored);
        isDataReady=true;
        Glide.with(firstImage.getContext()).load(data.get(""+position+"Created")).into(firstImage);
        Glide.with(secondImage.getContext()).load(data.get(""+position+"Drawn")).into(secondImage);
    }

    public void deleteGameMethod() {
            Map<String, String> data = new HashMap<>();
            data.put(""+position+"Date", "DELETED");
            database.collection("Users").document(userEmailAdress).set(data, SetOptions.merge());
            //DELETE
            Intent historyActivity = new Intent(OneGameActivity.this, StatsActivity.class);
            startActivity(historyActivity);
    }

    public void askBeforeDeleteMethod (View view){
        if(isDataReady) {
            areYouSureWindow = new AlertDialog.Builder(OneGameActivity.this);
            areYouSureWindow.setTitle(userLogin + ", Вы уверены, что хотите удалить этот раунд?");
            areYouSureWindow.setMessage("Удалённые раунды нельзя будет восстановить! \n" +
                    "Примечание: все данные хранятся на сервере, а не на устройстве, поэтому удалив раунд, вы не освободите память на устройстве");
            areYouSureWindow.setNegativeButton("Да",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            deleteGameMethod();
                        }
                    });
            areYouSureWindow.setPositiveButton("Нет",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            areYouSureWindow.show();
        };
    }

    public void shareGameMethod(View view) {
        if(isDataReady) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            if (difficulty.equals("Hard")) {
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Я набрал " + pointsScored + " баллов на сложном уровне в Perfect Circle!");
            }
            ;
            if (difficulty.equals("Easy")) {
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Я набрал " + pointsScored + " баллов на лёгком уровне в Perfect Circle!");
            }
            ;
            if (difficulty.equals("Medium")) {
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Я набрал " + pointsScored + " баллов на среднем уровне в Perfect Circle!");
            }
            ;
            startActivity(Intent.createChooser(shareIntent, "Выберите приложение:"));
        };
    }
}
