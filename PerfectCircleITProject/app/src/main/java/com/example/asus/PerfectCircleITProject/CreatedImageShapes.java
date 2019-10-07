package com.example.asus.PerfectCircleITProject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.io.Serializable;

public class CreatedImageShapes implements Serializable {
    boolean isCircle=false;
    boolean isRectangle=false;
    boolean isTriangle=false;
    boolean isQuadrangle=false;
    int rectangleL, rectangleR, rectangleB, rectangleT;
    int circleRadius, circleX, circleY;
    int triangleX1, triangleY1, triangleX2, triangleY2, triangleX3, triangleY3;
    int quadrangleX1, quadrangleY1, quadrangleX2, quadrangleY2, quadrangleX3, quadrangleY3, quadrangleX4, quadrangleY4;

    public Canvas createCanvas (int strokeWidth){
        Canvas canvas = new Canvas();
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint ();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(Color.BLACK);
        if(isCircle){
            canvas.drawCircle(circleX, circleY, circleRadius, paint);
        }
        if(isTriangle){
            Path trianglePath = new Path();
            trianglePath.moveTo(triangleX1, triangleY1);
            trianglePath.moveTo(triangleX2, triangleY2);
            trianglePath.moveTo(triangleX3, triangleY3);
            trianglePath.moveTo(triangleX1, triangleY1);
            canvas.drawPath(trianglePath, paint);
        }
        if(isRectangle){
            canvas.drawRect(rectangleL, rectangleT, rectangleR, rectangleB,  paint);
        }
        if(isQuadrangle){
            Path quadranglePath = new Path();
            quadranglePath.moveTo(quadrangleX1, quadrangleY1);
            quadranglePath.moveTo(quadrangleX2, quadrangleY2);
            quadranglePath.moveTo(quadrangleX3, quadrangleY3);
            quadranglePath.moveTo(quadrangleX4, quadrangleY4);
            quadranglePath.moveTo(quadrangleX1, quadrangleY1);
            canvas.drawPath(quadranglePath, paint);
        }
        return canvas;
    }

}
