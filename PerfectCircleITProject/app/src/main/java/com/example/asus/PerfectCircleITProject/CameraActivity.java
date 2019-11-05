package com.example.asus.PerfectCircleITProject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {
    static final int reqCode = 1;
    public ImageView image;
    public String currentPhotoPath;
    Bitmap bitmap;
    BitmapFactory.Options bmOptions;
    String difficulty;
    CreatedImageShapes shapesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        difficulty = new String();
        shapesList = new CreatedImageShapes();
        Intent receivedFromCreateImage = getIntent();
        difficulty=receivedFromCreateImage.getStringExtra("LevelHardness");
        shapesList=(CreatedImageShapes) receivedFromCreateImage.getSerializableExtra("ShapesList");
        image = findViewById(R.id.pikcha);
        currentPhotoPath = new String();
        dispatchTakePictureIntent();
    }

    public void onBackPressed() {
        /*
        super.onBackPressed();
        nothingHappens!
        */
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.d("Error", "OOPS");
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, reqCode);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == reqCode && resultCode == RESULT_OK) {
            bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
            Log.d("Sotherazmeris", ""+bitmap.getHeight()+" "+bitmap.getWidth());
            ImageProcessor receiver = new ImageProcessor();
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, receiver.w, receiver.h, false);
            boolean[][] pic = new boolean[scaledBitmap.getWidth()][scaledBitmap.getHeight()];
            int i, j;
            for (i = 0; i < scaledBitmap.getWidth(); i++) {
                for (j = 0; j < scaledBitmap.getHeight(); j++) {
                    if (Math.abs(scaledBitmap.getPixel(i, j)-Color.BLACK)<Math.abs(scaledBitmap.getPixel(i, j)-Color.WHITE)) {
                        pic[i][j] = true;
                    } else {
                        pic[i][j] = false;
                    }
                }
            }
            receiver.picture2 = pic;
            Intent itogActivity = new Intent(CameraActivity.this, ItogActivity.class);
            itogActivity.putExtra("LevelHardness", difficulty);
            itogActivity.putExtra("ShapesList", shapesList);
            startActivity(itogActivity);
        }
    }

}
