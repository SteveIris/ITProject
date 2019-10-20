package com.example.asus.PerfectCircleITProject;

import android.util.Log;

import java.util.Random;

public class ImageProcessor {
    static int w=0;
    static int h=0;
    static boolean[][] picture1;
    static boolean[][] picture2;

    public void compare() {
                int i, j, r, p, p1;
                double s1, s2, m;
                for (r = 0; r < w; r++) {
                    m = 0;
                    for (i = r; i < w; i += (r + 1)) {
                        for (j = r; j < h; j += (r + 1)) {
                            s1 = 0;
                            s2 = 0;
                            for (p = i - r; p <= i; p++) {
                                for (p1 = j - r; p1 <= j; p1++) {
                                    s1 += picture1[p][p1] ? 1 : 0;
                                    s2 += picture2[p][p1] ? 1 : 0;
                                }
                            }
                            m += Math.abs(((s1 * 1.000000000) / ((r + 1) * (r + 1))) - ((s2 * 1.000000000) / ((r + 1) * (r + 1))));
                    }
                    }
                }
    }

    public void compare1(){
        int i, j, r, p, p1;
        double m;
        double[][] srednee1= new double[w][h];
        double[][] srednee2= new double[w][h];
        for (r = 0; r < w; r+=3) {
            m = 0;
            for (i = w-1; i >= r; i--) {
                for (j = h-1; j >=r; j--) {
                    if(r==0){
                        srednee1[i][j]=picture1[i][j] ? 1 : 0;
                        srednee2[i][j]=picture2[i][j] ? 1 : 0;
                    } else {
                        srednee1[i][j]=srednee1[i-1][j-1]*r*r;
                        for(p=i-r;p<i;p++){
                            srednee1[i][j]+=picture1[p][j] ? 1 : 0;
                            srednee1[i][j]+=picture1[i][j+p-i] ? 1 : 0;
                            srednee2[i][j]+=picture2[p][j] ? 1 : 0;
                            srednee2[i][j]+=picture2[i][j+p-i] ? 1 : 0;
                        };
                        srednee1[i][j]+=picture1[i][j] ? 1 : 0;
                        srednee2[i][j]+=picture2[i][j] ? 1 : 0;
                        srednee1[i][j]=srednee1[i][j]/((r+1)*(r+1));
                        srednee2[i][j]=srednee2[i][j]/((r+1)*(r+1));
                    }
                    if(Math.pow(Math.abs(srednee1[i][j]-srednee2[i][j]), 2)>=0.01){
                        m+=Math.pow(Math.abs(srednee1[i][j]-srednee2[i][j]), 2);
                    }
                }
            }
            m=m/((w-r)*(h-r));
        }
    }

    public void compare2(){
        int i, j, r, p, p1, s, s1;
        double m;
        int [][] kaskad1 = new int[w+1][h+1];
        int [][] kaskad2 = new int[w+1][h+1];
        for(i=1;i<w+1;i++){
            for(j=1;j<h+1;j++){
                kaskad1[i][j]=kaskad1[i-1][j]+kaskad1[i][j-1]-kaskad1[i-1][j-1]+(picture1[i-1][j-1] ? 1 : 0);
                kaskad2[i][j]=kaskad2[i-1][j]+kaskad2[i][j-1]-kaskad2[i-1][j-1]+(picture2[i-1][j-1] ? 1 : 0);
                //if(kaskad2[i][j]!=0){
                //   Log.d("oopss", "ahh");
                //}
            }
        }
        for (r = 0; r < w; r++) {
            m = 0;
            for (i = 0; i < w+r; i++) {
                for (j = 0; j < h+r; j++) {
                    //p = kaskad1[i+1][j+1] - kaskad1[i - r][j+1] - kaskad1[i+1][j - r] + kaskad1[i-r][j-r];
                    //p1 = kaskad2[i+1][j+1] - kaskad2[i - r][j+1] - kaskad2[i+1][j - r] + kaskad2[i-r][j-r];
                    if(i>=r&&j>=r&&i<w&&j<h){
                        m+=Math.abs((1.00000000*(kaskad1[i+1][j+1] - kaskad1[i - r][j+1] - kaskad1[i+1][j - r] + kaskad1[i-r][j-r])/((r+1)*(r+1)))-(1.00000000*(kaskad2[i+1][j+1] - kaskad2[i - r][j+1] - kaskad2[i+1][j - r] + kaskad2[i-r][j-r])/((r+1)*(r+1))));
                    } else {
                        if(i<r){
                            s=0;
                        } else {
                            s=i-r;
                        };
                        if(j<r){
                            s1=0;
                        } else {
                            s1=j-r;
                        };
                        if(i>=w){
                            p=w;
                        } else {
                            p=i+1;
                        };
                        if(j>=h){
                            p1=h;
                        } else {
                            p1=j+1;
                        };
                        m+=Math.abs((1.00000000*(kaskad1[p][p1] - kaskad1[s][p1] - kaskad1[p][s1] + kaskad1[s][s1])/((r+1)*(r+1)))-(1.00000000*(kaskad2[p][p1] - kaskad2[s][p1] - kaskad2[p][s1] + kaskad2[s][s1])/((r+1)*(r+1))));
                    }
                }
            }
            m=100*(1-(m/(w*h)));
        }
    }

    public void compare3(){
        int i, j;
        long res1=0, res2=0, res3=0, res4=0;
        double it1, it2, it3, it4;
        long [][][] kaskad = new long[w+2][h+2][8];
        for(i=1;i<w+1;i++){
            for(j=1;j<h+1;j++){
                kaskad[i][j][0]=kaskad[i-1][j][0]+kaskad[i][j-1][0]-kaskad[i-1][j-1][0]+(picture1[i-1][j-1] ? 1 : 0);
                kaskad[i][j][1]=kaskad[i-1][j][1]+kaskad[i][j-1][1]-kaskad[i-1][j-1][1]+(picture2[i-1][j-1] ? 1 : 0);
                res1+=Math.abs(kaskad[i][j][0]-kaskad[i][j][1]);

            }
        }
        //if(res1>(w*(w+1)*h*(h+1))/4){
        //    Log.d("Kaskadnik", "dada");
        //};
        it1=100-((4.0000000*res1)/(w*(w+1)*h*(h+1)));
        for(i=w;i>=1;i--){
            for(j=1;j<h+1;j++){
                kaskad[i][j][2]=kaskad[i+1][j][2]+kaskad[i][j-1][2]-kaskad[i+1][j-1][2]+(picture1[i-1][j-1] ? 1 : 0);
                kaskad[i][j][3]=kaskad[i+1][j][3]+kaskad[i][j-1][3]-kaskad[i+1][j-1][3]+(picture2[i-1][j-1] ? 1 : 0);
                res2+=Math.abs(kaskad[i][j][2]-kaskad[i][j][3]);
            }
        }
        it2=100-((4.0000000*res2)/(w*(w+1)*h*(h+1)));
        for(i=1;i<w+1;i++){
            for(j=h;j>=1;j--){
                kaskad[i][j][4]=kaskad[i-1][j][4]+kaskad[i][j+1][4]-kaskad[i-1][j+1][4]+(picture1[i-1][j-1] ? 1 : 0);
                kaskad[i][j][5]=kaskad[i-1][j][5]+kaskad[i][j+1][5]-kaskad[i-1][j+1][5]+(picture2[i-1][j-1] ? 1 : 0);
                res3+=Math.abs(kaskad[i][j][4]-kaskad[i][j][5]);
            }
        }
        it3=100-((4.0000000*res3)/(w*(w+1)*h*(h+1)));
        for(i=w;i>=1;i--){
            for(j=h;j>=1;j--){
                kaskad[i][j][6]=kaskad[i+1][j][6]+kaskad[i][j+1][6]-kaskad[i+1][j+1][6]+(picture1[i-1][j-1] ? 1 : 0);
                kaskad[i][j][7]=kaskad[i+1][j][7]+kaskad[i][j+1][7]-kaskad[i+1][j+1][7]+(picture2[i-1][j-1] ? 1 : 0);
                res4+=Math.abs(kaskad[i][j][6]-kaskad[i][j][7]);
            }
        }
        it4=100-((4.0000000*res4)/(w*(w+1)*h*(h+1)));
    }

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
            //Log.d("Result", "ы2:"+c1x+" "+c1y+" "+c2x+" "+c2y);
            if(s1!=0&&s2!=0) {
                c1y /= s1;
                c1x /= s1;
                c2x /= s2;
                c2y /= s2;
                //Log.d("Result", "Kolvo:"+(c1x-c2x)+" "+(c1y-c2y));
                r += ((c1y - c2y) * (c1y - c2y)) + ((c1x - c2x) * (c1x - c2x));
                //Log.d("Result", "Kolvo:"+(int)(((c1y - c2y) * (c1y - c2y))+((c1x - c2x) * (c1x - c2x))));
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
            //Log.d("Result", "Dannye:"+s1+" "+s2);
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
