package com.example.asus.PerfectCircleITProject;

import android.content.*;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.*;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Button;

public class DrawActivity extends AppCompatActivity {
    static private Bitmap imageBitmap;
    static float x, y, i=0;
    static boolean isEraser =false, isFinished =false;
    LinearLayout buttonsLayout;
    private Button eraser;
    private String difficulty;
    private CreatedImageShapes shapesList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        GraphicsView grr;
        AttributeSet atrs = new AttributeSet() {
            @Override
            public int getAttributeCount() {
                return 0;
            }

            @Override
            public String getAttributeName(int index) {
                return null;
            }

            @Override
            public String getAttributeValue(int index) {
                return null;
            }

            @Override
            public String getAttributeValue(String namespace, String name) {
                return null;
            }

            @Override
            public String getPositionDescription() {
                return null;
            }

            @Override
            public int getAttributeNameResource(int index) {
                return 0;
            }

            @Override
            public int getAttributeListValue(String namespace, String attribute, String[] options, int defaultValue) {
                return 0;
            }

            @Override
            public boolean getAttributeBooleanValue(String namespace, String attribute, boolean defaultValue) {
                return false;
            }

            @Override
            public int getAttributeResourceValue(String namespace, String attribute, int defaultValue) {
                return 0;
            }

            @Override
            public int getAttributeIntValue(String namespace, String attribute, int defaultValue) {
                return 0;
            }

            @Override
            public int getAttributeUnsignedIntValue(String namespace, String attribute, int defaultValue) {
                return 0;
            }

            @Override
            public float getAttributeFloatValue(String namespace, String attribute, float defaultValue) {
                return 0;
            }

            @Override
            public int getAttributeListValue(int index, String[] options, int defaultValue) {
                return 0;
            }

            @Override
            public boolean getAttributeBooleanValue(int index, boolean defaultValue) {
                return false;
            }

            @Override
            public int getAttributeResourceValue(int index, int defaultValue) {
                return 0;
            }

            @Override
            public int getAttributeIntValue(int index, int defaultValue) {
                return 0;
            }

            @Override
            public int getAttributeUnsignedIntValue(int index, int defaultValue) {
                return 0;
            }

            @Override
            public float getAttributeFloatValue(int index, float defaultValue) {
                return 0;
            }

            @Override
            public String getIdAttribute() {
                return null;
            }

            @Override
            public String getClassAttribute() {
                return null;
            }

            @Override
            public int getIdAttributeResourceValue(int defaultValue) {
                return 0;
            }

            @Override
            public int getStyleAttribute() {
                return 0;
            }
        };
        grr = new GraphicsView(this, atrs);
        grr = (GraphicsView) findViewById(R.id.drawSpace);
        setContentView(R.layout.activity_draw);
        buttonsLayout = (LinearLayout)findViewById(R.id.knopochki);
        eraser = (Button)findViewById(R.id.lastic);
        Intent receivedFromCreateImage = getIntent();
        difficulty = receivedFromCreateImage.getStringExtra("levelHardness");
        shapesList = (CreatedImageShapes) receivedFromCreateImage.getSerializableExtra("ShapesList");
    }

    public void onBackPressed() {
        /*
        super.onBackPressed();
        nothingHappens!
        */
    }

    public void eraserMethod(View view) {
        if(isEraser){
            isEraser =false;
            eraser.setText("Ластик");
        } else {
            isEraser = true;
            eraser.setText("Карандаш");
        }
    }

    public void finishMethod(View view) {
        if(!isFinished) {
            isFinished = true;
            boolean[][] pic = new boolean[imageBitmap.getWidth()][imageBitmap.getHeight()];
            int i, j;
            for (i = 0; i < imageBitmap.getWidth(); i++) {
                for (j = 0; j < imageBitmap.getHeight(); j++) {
                    if (imageBitmap.getPixel(i, j) == Color.BLACK) {
                        pic[i][j] = true;

                    } else {
                        pic[i][j] = false;
                    }
                }
            }
            ImageProcessor receiver = new ImageProcessor();
            if(imageBitmap.getWidth()!=receiver.w|| imageBitmap.getHeight()!=receiver.h){
                Log.d("oopss", "Heyhey");
            }
            receiver.picture2 = pic;
            Intent ItogActivity = new Intent(DrawActivity.this, ItogActivity.class);
            ItogActivity.putExtra("levelHardness", difficulty);
            ItogActivity.putExtra("ShapesList", shapesList);
            startActivity(ItogActivity);
        };
    }

    static public class GraphicsView extends View{
        private Canvas imageCanvas;
        private Path drawPath;
        Paint paint;

        @Override
        protected void onSizeChanged(int width, int height, int idont, int needit ) {
            super.onSizeChanged(width, height, idont, needit);
            imageBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            imageCanvas = new Canvas(imageBitmap);
            imageCanvas.drawColor(Color.WHITE);
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
            drawPath = new Path();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawBitmap(imageBitmap, 0, 0, paint);
            if(isEraser){
                paint.setColor(Color.WHITE);
                paint.setStrokeWidth(40);
            } else {
                paint.setColor(Color.BLACK);
                paint.setStrokeWidth(20);
            };
            canvas.drawPath (drawPath, paint);
        }


        public boolean onTouchEvent (MotionEvent event) {
            x = event.getX();
            y = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    drawPath.moveTo(x, y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    drawPath.lineTo(x, y);
                    break;
                case MotionEvent.ACTION_UP:
                    imageCanvas.drawPath(drawPath, paint);
                    drawPath.reset();
                    break;
                case MotionEvent.ACTION_CANCEL:
                    break;
            }
            invalidate();
            return true;
        }

    }



}