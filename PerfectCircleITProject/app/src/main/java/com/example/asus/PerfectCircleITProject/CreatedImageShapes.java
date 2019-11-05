package com.example.asus.PerfectCircleITProject;

import android.graphics.Bitmap;
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
    boolean isPentagon=false;
    int rectangleL, rectangleR, rectangleB, rectangleT;
    int circleRadius, circleX, circleY;
    int triangleX1, triangleY1, triangleX2, triangleY2, triangleX3, triangleY3;
    int quadrangleX1, quadrangleY1, quadrangleX2, quadrangleY2, quadrangleX3, quadrangleY3, quadrangleX4, quadrangleY4;
    int pentagonRadius, pentagonCenterX, pentagonCenterY;

    public Canvas createCanvas (int strokeWidth, Bitmap bitmap){
        Canvas canvas = new Canvas(bitmap);
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
            trianglePath.lineTo(triangleX2, triangleY2);
            trianglePath.lineTo(triangleX3, triangleY3);
            trianglePath.lineTo(triangleX1, triangleY1);
            canvas.drawPath(trianglePath, paint);
        }
        if(isRectangle){
            canvas.drawRect(rectangleL, rectangleT, rectangleR, rectangleB,  paint);
        }
        if(isQuadrangle){
            Path quadranglePath = new Path();
            quadranglePath.moveTo(quadrangleX1, quadrangleY1);
            quadranglePath.lineTo(quadrangleX2, quadrangleY2);
            quadranglePath.lineTo(quadrangleX3, quadrangleY3);
            quadranglePath.lineTo(quadrangleX4, quadrangleY4);
            quadranglePath.lineTo(quadrangleX1, quadrangleY1);
            canvas.drawPath(quadranglePath, paint);
        }
        if(isPentagon){
            Path pentagonPath = new Path();
            pentagonPath.moveTo(pentagonCenterX+pentagonRadius, pentagonCenterY);
            pentagonPath.lineTo(Math.round(pentagonCenterX+Math.cos(2*Math.PI/5)*pentagonRadius), Math.round(pentagonCenterY+Math.sin(2*Math.PI/5)*pentagonRadius));
            pentagonPath.lineTo(Math.round(pentagonCenterX+Math.cos(4*Math.PI/5)*pentagonRadius), Math.round(pentagonCenterY+Math.sin(4*Math.PI/5)*pentagonRadius));
            pentagonPath.lineTo(Math.round(pentagonCenterX+Math.cos(6*Math.PI/5)*pentagonRadius), Math.round(pentagonCenterY+Math.sin(6*Math.PI/5)*pentagonRadius));
            pentagonPath.lineTo(Math.round(pentagonCenterX+Math.cos(8*Math.PI/5)*pentagonRadius), Math.round(pentagonCenterY+Math.sin(8*Math.PI/5)*pentagonRadius));
            pentagonPath.lineTo(pentagonCenterX+pentagonRadius, pentagonCenterY);
            canvas.drawPath(pentagonPath, paint);
        }
        return canvas;
    }

}
