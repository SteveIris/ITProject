package com.example.asus.PerfectCircleITProject;

import android.util.Log;

import java.util.Random;

public class ImageProcessor {
    static int w=0;
    static int h=0;
    static boolean[][] picture1;
    static boolean[][] picture2;

    public int compare4(){
        int q, j, x, y, i, c1y=0, c1x=0, c2y=0, c2x=0, s1=0, s2=0, res;
        double res1;
        long r=0;
        for(q=0;q<1000;q++){
            c1y=0; c1x=0; c2y=0; c2x=0; s1=0; s2=0;
            y = new Random().nextInt(h-100)+50;
            x = new Random().nextInt(w-100)+50;
            for(i=x-50;i<=x+50;i++){
                for(j=y-50;j<=y+50;j++){
                    if((i-x)*(i-x)+(j-y)*(j-y)<=2500){
                        if(picture1[i][j]){
                            s1++;
                            c1y+=j;
                            c1x+=i;
                        };
                        if(picture2[i][j]){
                            s2++;
                            c2y+=j;
                            c2x+=i;
                        };
                    }
                }
            }
            if(s1!=0&&s2!=0) {
                c1y /= s1;
                c1x /= s1;
                c2x /= s2;
                c2y /= s2;
                r += ((c1y - c2y) * (c1y - c2y)) + ((c1x - c2x) * (c1x - c2x));
            } else {
                if(s1!=0&&s2==0){
                    c1y /= s1;
                    c1x /= s1;
                    r += Math.pow((50-Math.sqrt(((c1y - y) * (c1y - y)) + ((c1x - x) * (c1x - x)))), 2);
                };
                if(s1==0&&s2!=0){
                    c2x /= s2;
                    c2y /= s2;
                    r += Math.pow((50-Math.sqrt(((c2y - y) * (c2y - y)) + ((c2x - x) * (c2x - x)))), 2);
                };
            };
        };
        r=r/1000;
        res1=((40-(Math.round(Math.sqrt(r))))*10)/4;
        res=(int)Math.round(res1);
        if(res>0){
            return res;
        } else {
            return 0;
        }
    }
}
