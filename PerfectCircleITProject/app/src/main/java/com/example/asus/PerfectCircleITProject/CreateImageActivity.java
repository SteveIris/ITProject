package com.example.asus.PerfectCircleITProject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.lang.*;

import java.util.Random;

public class CreateImageActivity extends AppCompatActivity {
    TextView timeLeftText;
    MyCountDownTimer timer;
    static String difficulty;
    static String dawae;
    static public Bitmap imageBitmap;
    static public Canvas imageCanvas;
    static public CreatedImageShapes shapesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_create_image);
        shapesList = new CreatedImageShapes();
        Intent receivedFromChooseWay = getIntent();
        dawae = receivedFromChooseWay.getStringExtra("TheWayYouDraw");
        difficulty = receivedFromChooseWay.getStringExtra("LevelHardness");
        timeLeftText =findViewById(R.id.sekunda);
        timer = new MyCountDownTimer(10000, 1000);
        timer.start();
    }

    public void onBackPressed() {
        /*
        super.onBackPressed();
        nothingHappens!
        */
    }

    static public class GraphicsView extends View {
        Paint paint;
        @Override
        protected void onSizeChanged(int width, int height, int idont, int needit) {
            super.onSizeChanged(width, height, idont, needit);
            imageBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            imageCanvas = new Canvas(imageBitmap);
            imageCanvas.drawColor(Color.WHITE);
            if(difficulty.equals("Easy")){
                createTriangle();
                createRectangle();
            };
            if(difficulty.equals("Medium")){
                createCircle();
                createQuadrangle();
                createTriangle();
            };
            if(difficulty.equals("Hard")){
                createCircle();
                createQuadrangle();
                createTriangle();
            };

        }

        public GraphicsView(Context context, AttributeSet attrs) {
            super(context);
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStrokeWidth(20);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setColor(Color.BLACK);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawBitmap(imageBitmap, new Matrix(), paint);
        }

        public void createRectangle(){
            shapesList.isRectangle=true;
            int t, r, l, b, s;
            t = new Random().nextInt(imageBitmap.getHeight()-300)+150;
            r = new Random().nextInt(imageBitmap.getWidth()-300)+150;
            b = new Random().nextInt(imageBitmap.getHeight()-300)+150;
            l = new Random().nextInt(imageBitmap.getWidth()-300)+150;
            if(r<l){
                s=l;
                l=r;
                r=s;
            };
            if(b<t){
                s=b;
                b=t;
                t=s;
            };
            imageCanvas.drawRect(l, t, r, b, paint);
            shapesList.rectangleL=l;
            shapesList.rectangleB=b;
            shapesList.rectangleT=t;
            shapesList.rectangleR=r;
        }

        public void createTriangle(){
            shapesList.isTriangle=true;
            int x1, x2, x3, y1, y2, y3;
            Path treugolnik = new Path();
            y1 = new Random().nextInt(imageBitmap.getHeight()-300)+150;
            x1 = new Random().nextInt(imageBitmap.getWidth()-300)+150;
            y2 = new Random().nextInt(imageBitmap.getHeight()-300)+150;
            x2 = new Random().nextInt(imageBitmap.getWidth()-300)+150;
            y3 = new Random().nextInt(imageBitmap.getHeight()-300)+150;
            x3 = new Random().nextInt(imageBitmap.getWidth()-300)+150;
            treugolnik.moveTo(x1, y1);
            treugolnik.lineTo(x2, y2);
            treugolnik.lineTo(x3, y3);
            treugolnik.lineTo(x1, y1);
            imageCanvas.drawPath(treugolnik, paint);
            shapesList.triangleX1=x1;
            shapesList.triangleX2=x2;
            shapesList.triangleX3=x3;
            shapesList.triangleY1=y1;
            shapesList.triangleY2=y2;
            shapesList.triangleY3=y3;
        }

        public void createCircle(){
            shapesList.isCircle=true;
            int x, y, r;
            y = new Random().nextInt(imageBitmap.getHeight()-300)+150;
            x = new Random().nextInt(imageBitmap.getWidth()-300)+150;
            r = new Random().nextInt(Math.min(Math.abs(y- imageBitmap.getHeight()), Math.min(Math.abs(x- imageBitmap.getWidth()), Math.min(y, x)))-100)+50;
            imageCanvas.drawCircle(x, y, r, paint);
            shapesList.circleX=x;
            shapesList.circleY=y;
            shapesList.circleRadius=r;
        }

        public void createQuadrangle(){
            shapesList.isQuadrangle=true;
            int x1, y1, x2, y2, x3, y3, x4, y4;
            Path put = new Path();
            y1 = new Random().nextInt(imageBitmap.getHeight()-300)+150;
            x1 = new Random().nextInt(imageBitmap.getWidth()-300)+150;
            y2 = new Random().nextInt(imageBitmap.getHeight()-300)+150;
            x2 = new Random().nextInt(imageBitmap.getWidth()-300)+150;
            y3 = new Random().nextInt(imageBitmap.getHeight()-300)+150;
            x3 = new Random().nextInt(imageBitmap.getWidth()-300)+150;
            y4 = new Random().nextInt(imageBitmap.getHeight()-300)+150;
            x4 = new Random().nextInt(imageBitmap.getWidth()-300)+150;
            put.moveTo(x1, y1);
            put.lineTo(x2, y2);
            put.lineTo(x3, y3);
            put.lineTo(x4, y4);
            put.lineTo(x1, y1);
            imageCanvas.drawPath(put, paint);
            shapesList.quadrangleX1=x1;
            shapesList.quadrangleX2=x2;
            shapesList.quadrangleX3=x3;
            shapesList.quadrangleX4=x4;
            shapesList.quadrangleY1=y1;
            shapesList.quadrangleY2=y2;
            shapesList.quadrangleY3=y3;
            shapesList.quadrangleY4=y4;
        }

    }

    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long startTime, long interval){
            super(startTime, interval);
        }

        public void onFinish (){
            /*boolean[][] pic = new boolean[imageBitmap.getWidth()][imageBitmap.getHeight()];
            int i, j;
            for(i=0; i< imageBitmap.getWidth(); i++){
                for(j=0; j< imageBitmap.getHeight(); j++){
                    if(imageBitmap.getPixel(i, j)==Color.BLACK){
                        pic[i][j]=true;
                    } else {
                        pic[i][j]=false;
                    }
                }
            }
            ImageProcessor holder = new ImageProcessor();
            holder.w= imageBitmap.getWidth();
            holder.h= imageBitmap.getHeight();
            holder.picture1=pic;
            //DrawActivity.putExtra("width", imageBitmap.getWidth());
            //DrawActivity.putExtra("height", imageBitmap.getHeight());
            //DrawActivity.putExtra("boo", pic); */
            ImageProcessor holder = new ImageProcessor();
            holder.w = imageBitmap.getWidth();
            holder.h = imageBitmap.getHeight();
            Log.d("AHA", imageBitmap.getWidth()+ " "+imageBitmap.getHeight());
            if(dawae.equals("Camera")){
                Intent cameraActivity = new Intent(CreateImageActivity.this, CameraActivity.class);
                cameraActivity.putExtra("LevelHardness", difficulty);
                cameraActivity.putExtra("ShapesList", shapesList);
                startActivity(cameraActivity);
            } else {
                Intent DrawActivity = new Intent(CreateImageActivity.this, DrawActivity.class);
                DrawActivity.putExtra("LevelHardness", difficulty);
                DrawActivity.putExtra("ShapesList", shapesList);
                startActivity(DrawActivity);
            };
        }

        public void onTick (long millisUntilFinished){
            timeLeftText.setText("" + millisUntilFinished/1000);
        };
    }
}
