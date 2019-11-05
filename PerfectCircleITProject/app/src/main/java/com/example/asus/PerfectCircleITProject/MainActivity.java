package com.example.asus.PerfectCircleITProject;

import android.content.pm.ActivityInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.*;
import android.widget.Toast;

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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    AlertDialog.Builder welcomeWindow;
    AlertDialog.Builder newsWindow;
    AlertDialog.Builder askCameraPermissionWindow;
    AlertDialog.Builder explanationWindow;
    SharedPreferences preferences = null;
    private FirebaseAuth auth;
    private boolean isFirstTime=false;
    private boolean isDataReady=false;
    private FirebaseFirestore database;
    private String userLogin="";
    private DocumentSnapshot userDoc;
    private String userEmailAdress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        auth =FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        userEmailAdress =user.getEmail().substring(0, user.getEmail().indexOf("."));
        database = FirebaseFirestore.getInstance();
        DocumentReference docRef = database.collection("Users").document(userEmailAdress);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        userDoc=document;
                        updateUsername();
                    } else {
                        Toast.makeText(MainActivity.this, "Ошибка загрузки данных",
                                Toast.LENGTH_SHORT).show();
                    }
                };
            }
        });
        preferences = getSharedPreferences("com.example.asus.PerfectCircleITProject", MODE_PRIVATE);
    }

    private void updateUsername() {
        Map<String, Object> data = new HashMap<>();
        data = userDoc.getData();
        userLogin = data.get("name").toString();
        if(isFirstTime){
            showNewsMessage();
            askCameraPermission();
            showWelcomeMessage();
        }
        isDataReady=true;
    }

    protected void onResume() {
        super.onResume();
        if (preferences.getBoolean("IsItFirstRun", true)) {
            if(isDataReady) {
                showNewsMessage();
                askCameraPermission();
                showWelcomeMessage();
            };
            isFirstTime=true;
            preferences.edit().putBoolean("IsItFirstRun", false).commit();
        }
    }

    public void showWelcomeMessage (){
        welcomeWindow = new AlertDialog.Builder(MainActivity.this);
        welcomeWindow.setTitle(userLogin+", пожалуйста, прочитайте советы по работе с приложением:");
        welcomeWindow.setMessage("1. Подразумевается, что при работе с приложением у вас есть доступ в интернет, " +
                "если это не так, ваши данные не будут сохранены, а в работе приложения возможны сбои. \n" +
                "2. Приложение, прежде чем сравнивать изображения, переводит их в чёрно-белый " +
                "вид, причём каждый пиксель становится либо белым, либо чёрным. Поэтому рисунок стоит " +
                "делать на белом листе бумаги, и фотографировать его стоит также на белом (светлом) фоне.\n" +
                "3. Пропорции даваемого вам изображения соответстуют отношению сторон " +
                "листа А4, поэтому рисовать нужно на листе с таким же отношением сторон " +
                "(Тот же А4, А5 и т.д.), желательно на А4.\n" +
                "4. Учтите, что оценка изображения с камеры занимает больше времени, " +
                "чем оценка изображения, нарисованного на экране. Также она может быть менее " +
                "точной, так как неправильно могут определены границы листа или толщина " +
                "вашего инструмента.");
        welcomeWindow.setNegativeButton("ОК",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //NothingHappens
                    }
                });
        welcomeWindow.show();
    }

    public void showNewsMessage (){
        newsWindow = new AlertDialog.Builder(MainActivity.this);
        newsWindow.setTitle("Обновления");
        newsWindow.setMessage(userLogin + ", хотите получать новости об обновлениях приложения?");
        newsWindow.setNegativeButton("Да",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Map<String, Object> data1 = new HashMap<>();
                        data1.put("news", "true");
                        database.collection("Users").document(userEmailAdress).set(data1, SetOptions.merge());
                    }
                });
        newsWindow.setPositiveButton("Нет",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Map<String, Object> data1 = new HashMap<>();
                        data1.put("news", "false");
                        database.collection("Users").document(userEmailAdress).set(data1, SetOptions.merge());
                    }
                });
        newsWindow.show();
    }

    public void askCameraPermission (){
        askCameraPermissionWindow = new AlertDialog.Builder(MainActivity.this);
        askCameraPermissionWindow.setTitle(userLogin+", Разрешить приложению доступ к камере?");
        askCameraPermissionWindow.setNegativeButton("Да",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //NothingHappens
                    }
                });
        askCameraPermissionWindow.setPositiveButton("Нет",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        explanation();
                    }
                });
        askCameraPermissionWindow.show();
    }

    public void explanation (){
        explanationWindow = new AlertDialog.Builder(MainActivity.this);
        explanationWindow.setTitle(userLogin+", Разрешить приложению доступ к камере?");
        explanationWindow.setMessage("Если у приложения не будет доступа к камере, вы потеряете возможность"+
                "оценивать рисунки, нарисованные на бумаге. Разрешить доступ к камере?");
        explanationWindow.setNegativeButton("Да",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //NothingHappens
                    }
                });
        explanationWindow.setPositiveButton("Нет",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        preferences.edit().putBoolean("CamPermission2", false).commit();
                    }
                });
        explanationWindow.show();
    }



    public void playMethod(View view) {
        Intent ChooseLevelActivity = new Intent(MainActivity.this, ChooseLevelActivity.class);
        startActivity(ChooseLevelActivity);
    }

    public void exitMethod(View view) {
        auth.getInstance().signOut();
        Intent loginActivity = new Intent (MainActivity.this, LoginActivity.class);
        startActivity(loginActivity);
    }

    public void statsMethod(View view) {
        Intent statsActivity = new Intent (MainActivity.this, StatsActivity.class);
        startActivity(statsActivity);
    }
}
