package com.example.asus.PerfectCircleITProject;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.SharedLibraryInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.*;

public class MainActivity extends AppCompatActivity {
    AlertDialog.Builder welcomeWindow;
    AlertDialog.Builder newsWindow;
    AlertDialog.Builder askCameraPermissionWindow;
    AlertDialog.Builder explanationWindow;
    SharedPreferences preferences = null;
    int denied=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences("com.example.asus.PerfectCircleITProject", MODE_PRIVATE);
    }

    protected void onResume() {
        super.onResume();
        if (preferences.getBoolean("isfirsttime1", true)) {
            showNewsMessage();
            askCameraPermission();
            showWelcomeMessage();
            preferences.edit().putBoolean("isfirsttime1", false).commit();
        }
    }

    public void showWelcomeMessage (){
        welcomeWindow = new AlertDialog.Builder(MainActivity.this);
        welcomeWindow.setTitle("Несколько советов по работе с приложением:");
        welcomeWindow.setMessage("1. Приложение, прежде чем сравнивать изображения, переводит их в чёрно-белый " +
                "вид, причём каждый пиксель становится либо белым, либо чёрным. Поэтому рисунок " +
                "стоит делать на белом листе бумаги, а фотографировать его стоит на чёрном фоне.\n" +
                "2. Пропорции даваемого вам изображения соответстуют отношению сторон " +
                "листа А4, поэтому рисовать нужно на листе с таким же отношением сторон " +
                "(Тот же А4, А5 и т.д.), желательно на А4.\n" +
                "3. Учтите, что оценка изображения с камеры занимает больше времени, " +
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
        newsWindow.setTitle("Хотите получать уведомления об обновлениях приложения? ");
        newsWindow.setNegativeButton("Да",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //NothingHappens...
                        //ForNow...
                    }
                });
        newsWindow.setPositiveButton("Нет",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //NothingHappens...
                    }
                });
        newsWindow.show();
    }

    public void askCameraPermission (){
        askCameraPermissionWindow = new AlertDialog.Builder(MainActivity.this);
        askCameraPermissionWindow.setTitle("Разрешить приложению доступ к камере?");
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
        explanationWindow.setTitle("Разрешить приложению доступ к камере?");
        explanationWindow.setMessage("Если у приложения не будет доступа к камере, вы потеряете возможность"+
                "оценивать рисунки, нарисованные на бумаге. Разрешить доступ к камере?");
        explanationWindow.setNegativeButton("Да",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //LolNothingHappensAgain
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
        finish();
    }

    public void statsMethod(View view) {
        Intent statsActivity = new Intent (MainActivity.this, StatsActivity.class);
        startActivity(statsActivity);
    }
}
