package com.example.asus.PerfectCircleITProject;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
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
    private ByteArrayOutputStream baos1;
    private ByteArrayOutputStream baos2;
    private Bitmap scaledBitmap;
    private Bitmap drawn;
    private int isReady=0;
    private int isUploaded=0;
    private byte[] image1data;
    private byte[] image2data;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private Map<String, String> imagesData;
    private ImageView drawnimage;
    private ImageView createdimage;
    private int widthOfImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_itog);
        storage = FirebaseStorage.getInstance();
        imagesData = new HashMap<>();
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        userEmailAdress = user.getEmail().substring(0, user.getEmail().indexOf("."));
        database = FirebaseFirestore.getInstance();
        storageRef = storage.getReference().child(userEmailAdress);
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
        drawn = Bitmap.createBitmap((getpicture1.w/2)+1, (getpicture1.h/2)+1, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(created);
        Canvas canvaz = new Canvas();
        canvas.drawColor(Color.WHITE);
        canvas = shapesList.createCanvas(20, created);
        scaledBitmap = Bitmap.createScaledBitmap(created, (getpicture1.w/2)+1, (getpicture1.h/2)+1, false);
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
        for(i=0;i<getpicture1.w;i++){
            for(j=0;j<getpicture1.h;j++){
                if(getpicture1.picture2[i][j]){
                    drawn.setPixel(i/2, j/2, Color.BLACK);
                } else {
                    drawn.setPixel(i/2, j/2, Color.WHITE);
                }
            }
        }
        createdimage = findViewById(R.id.created);
        drawnimage = findViewById(R.id.drawn);
        createdimage.setImageBitmap(scaledBitmap);
        drawnimage.setImageBitmap(drawn);
        widthOfImage=scaledBitmap.getWidth();
        new Thread(new Runnable() {
            public void run() {
                baos1=new ByteArrayOutputStream();
                baos2=new ByteArrayOutputStream();
                ((BitmapDrawable)createdimage.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, baos1);
                ((BitmapDrawable)drawnimage.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, baos2);
                image1data = baos1.toByteArray();
                image2data = baos2.toByteArray();
                isReady++;
            }}).start();
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
        isReady++;
    }

    public void onBackPressed() {
        /*
        super.onBackPressed();
        nothingHappens!
        */
    }

    public void backToMenuMethod(View view) {
        Log.d("isReady", ""+isReady);
        if(isReady>=2){
            UploadTask uploadTask1 = storageRef.child(""+numberOfGames+"Drawn").putBytes(image2data);
            uploadTask1.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(ItogActivity.this, "Ошибка подключения!",
                            Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                }
            });
            Task<Uri> urlTask1 = uploadTask1.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return storageRef.child(""+numberOfGames+"Drawn").getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        imagesData.put(""+numberOfGames+"Drawn", downloadUri.toString());
                    } else {
                    }
                    isUploaded++;
                    if(isUploaded==2){
                        intentMethod();
                    };
                }
            });
            UploadTask uploadTask2 = storageRef.child(""+numberOfGames+"Created").putBytes(image1data);
            uploadTask2.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(ItogActivity.this, "Ошибка подключения!",
                            Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                }
            });
            Task<Uri> urlTask2 = uploadTask2.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return storageRef.child(""+numberOfGames+"Created").getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        imagesData.put(""+numberOfGames+"Created", downloadUri.toString());
                    } else {
                    }
                    isUploaded++;
                    if(isUploaded==2){
                        intentMethod();
                    };
                }
            });
        };
    }

    public void intentMethod(){
        database.collection("Users").document(userEmailAdress).set(imagesData, SetOptions.merge());
        Intent MainActivity = new Intent(ItogActivity.this, MainActivity.class);
        startActivity(MainActivity);
    }
}
