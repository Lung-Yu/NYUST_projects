package com.mipl.lungyu.licenseplaterecognition;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import esoc.lprRobot.jni.OpenCV;

public class TestingActivity extends Activity {

    OpenCV opencv = new OpenCV();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        LPD();
    }

    private void LPD(){

        Bitmap b = BitmapFactory.decodeFile("/sdcard/test.jpg");
        Log.i(null, " start find plate !");

        long T_start = System.currentTimeMillis();

        // -----Convert to Gray--------------------------------------------
        int Width = b.getWidth()/2;
        int Height = b.getHeight()/2;
//		int Width = 512;
//		int Height = 500;// 526

        int Width2 = Width/2;
        int Height2 = Height/2;

        System.out.println("Max picture size"
                + String.valueOf(Width));
        System.out.println("Max picture size"
                + String.valueOf(Height));

        int[] pix = new int[Width * Height];
        int[] pixRGB = new int[Width * Height];
        int[] Gray = new int[Width * Height]; // byte matrix for grayscale

        b.getPixels(pixRGB, 0, Width, Width2, Height2, Width, Height); // x=256
        // y=192為起始位置
        long T_DROBegin = System.currentTimeMillis();
        opencv.DRO(pixRGB, Width, Height, 31);
        long T_DROEnd = System.currentTimeMillis();

        System.out.println("1. DRO total time:"
                + String.valueOf(T_DROEnd - T_DROBegin));

        for (int y = Height - 1; y >= 0; y--) {
            for (int x = Width - 1; x >= 0; x--) {
                int index = y * Width + x; // pix is an 1D Matrix
                int R = (pix[index] >> 16) & 0xff;
                int G = (pix[index] >> 8) & 0xff;
                int B = pix[index] & 0xff;
                int temp = (int) (0.299 * R + 0.587 * G + 0.114 * B);
                Gray[index] = temp;
            }
        }
        Log.i(null, "FistGray end !");

        //adaboost
        String cascadeFile = "/sdcard/DRV/output+670-500-wg.xml";
        String cascadeFile1 = "/sdcard/DRV/output_char+1250-1038-wg.xml";

        long T_AdaBegin = System.currentTimeMillis();

        int LPD_fin[] = AdaBoost(cascadeFile, cascadeFile1, pix, Width, Height,
                b);// b is bitmap(RGB565)
        Log.i("LPD_fin",LPD_fin[0]+"");
        Log.i("LPD_fin", LPD_fin[1]+"");
        Log.i("LPD_fin", LPD_fin[2]+"");
        Log.i("LPD_fin", LPD_fin[3]+"");
    }


    public int[] AdaBoost(String cascadeFile, String cascadeFile1, int[] pix,
                          int Width, int Height, Bitmap b) {

        int Width2 = Width/2;
        int Height2 = Height/2;

        int[] LPD_fin = new int[4];
        LPD_fin[0] = 0;
        LPD_fin[1] = 0;
        LPD_fin[2] = 0;
        LPD_fin[3] = 0;
        int[] LPD = opencv.LPD(cascadeFile, pix, Width, Height, 3);
        int case1 = LPD.length;
        if (case1 != 0) {
            // 找出所有車牌定位出來的影像區塊並將此區塊合併成一個區塊
            int mini_x_left = 2000;
            int max_x_right = 0;
            int mini_y_up = 2000;
            int max_y_down = 0;
            for (int i = 0; i < LPD.length; i += 4) {
                if (LPD[i + 0] < mini_x_left) {
                    mini_x_left = LPD[i + 0];
                }
                if (LPD[i + 1] > max_x_right) {
                    max_x_right = LPD[i + 1];
                }
                if (LPD[i + 2] < mini_y_up) {
                    mini_y_up = LPD[i + 2];
                }
                if (LPD[i + 3] > max_y_down) {
                    max_y_down = LPD[i + 3];
                }
            }
            Log.i(null, "adaboost 3698");
            // 合併完擴大一點 0.2為擴大5分之1 plate based 做DRO, character based
            // 做gray+區域放大0.2倍
            int mini_pix_w = max_x_right - mini_x_left;

            mini_x_left = mini_x_left - 0; // 35
            max_x_right = max_x_right + 0; // 35

            int mini_pix_h = max_y_down - mini_y_up;
            // mini_y_up = (int)(mini_y_up - (mini_pix_h * 0.2));
            // max_y_down= (int)(max_y_down+ (mini_pix_h * 0.2));
            mini_y_up = mini_y_up - 45; // 35
            max_y_down = max_y_down + 45; // 35

            mini_pix_w = max_x_right - mini_x_left;
            mini_pix_h = max_y_down - mini_y_up;
            // int[] mini_pix = new int[mini_pix_w * mini_pix_h];

            mini_pix_w = Width;
            mini_x_left = 0;// 2013.04.16 不切割區域
            int[] mini_pix = new int[mini_pix_w * mini_pix_h];

            // 將找出來的區塊從原始影像切割出來
            // Bitmap b = BitmapFactory.decodeFile("/sdcard/img.jpg");

            b.getPixels(mini_pix, 0, mini_pix_w, Width2 + mini_x_left,
                    Height2 + mini_y_up, mini_pix_w, mini_pix_h);// x=256 y=192為起始位置

            int[] LPD_char = opencv.LPD(cascadeFile1, mini_pix, mini_pix_w,
                    mini_pix_h, 1);

            // 擴展為原來座標
            for (int i = 0; i < LPD_char.length; i += 4) {
                LPD_char[i + 0] = LPD_char[i + 0] + mini_x_left;
                LPD_char[i + 1] = LPD_char[i + 1] + mini_x_left;
                LPD_char[i + 2] = LPD_char[i + 2] + mini_y_up;
                LPD_char[i + 3] = LPD_char[i + 3] + mini_y_up;
            }

            for (int i = 0; i < LPD.length; i += 4) {
                LPD[i + 0] = LPD[i + 0] - 20;
                LPD[i + 1] = LPD[i + 1] + 20;

            }

            // ----------搜尋車牌內字元多寡 留最大字元區域------------------
            int count1 = 0;
            int count2 = -4;
            int index = 0;

            for (int i = 0; i < LPD.length; i += 4) {
                for (int j = 0; j < LPD_char.length; j += 4) {
                    if (LPD[i + 2] - 30 < LPD_char[j + 2]
                            && LPD[i + 3] + 30 > LPD_char[j + 3]) {
                        count1 = count1 + 1;
                    }
                }
                if (count1 != 0 && count1 > index) // count1 >= index
                {
                    count2 = count2 + 4;

                    LPD_fin[0] = LPD[i + 0];
                    LPD_fin[1] = LPD[i + 1];
                    LPD_fin[2] = LPD[i + 2];
                    LPD_fin[3] = LPD[i + 3];

                    index = count1;
                }

                count1 = 0;

            }
            Log.i(null, "adaboost 3818");
            // ---------判斷LPD的y_up車牌座標上下30個像素內是否有包含LPD_char字元座標，
            // ---------有的話向ｘ_left or x_right往外擴展並取代
            int OT = 45; // 30

            if (LPD_fin[1] - LPD_fin[0] > 235 && LPD_fin[1] - LPD_fin[0] < 300) {
                OT = 40;
            }

            // %%%%%%%%
            int xleft = 1000;
            int xright = 0;
            int h = LPD_fin[3] - LPD_fin[2];
            for (int i = 0; i < LPD_fin.length; i += 4) {
                for (int j = 0; j < LPD_char.length; j += 4) {
                    if (LPD_fin[i + 2] - h <= LPD_char[j + 2]
                            && LPD_fin[i + 3] + 10 >= LPD_char[j + 3]) // LPD_fin[i+2]-h
                    // 提高y_up
                    // 將低y_down
                    {
                        if (LPD_char[j + 0] < xleft)
                            xleft = LPD_char[j + 0];

                        if (LPD_char[j + 1] > xright)
                            xright = LPD_char[j + 1];
                    }

                }
            }
            int T_add = 2 * (LPD_fin[1] - LPD_fin[0]) / 3;// 拓寬比率
            if (LPD_fin[0] - xleft > 0 && LPD_fin[0] - xleft < T_add)// 最終定位結果往左拓展
                LPD_fin[0] = xleft;

            if (xright - LPD_fin[1] > 0 && xright - LPD_fin[1] < T_add)
                LPD_fin[1] = xright;

            Log.i(null, "adaboost 3862");

            // %%%%%%%%
            for (int i = 0; i < LPD_fin.length; i += 4) {
                for (int j = 0; j < LPD_char.length; j += 4) {
                    // if(LPD_fin[i + 2] + OT > LPD_char[j + 2] && LPD_fin[i +
                    // 2] - OT < LPD_char[j + 2])
                    if (LPD_fin[i + 2] + 30 > LPD_char[j + 2]
                            && LPD_fin[i + 2] - 30 < LPD_char[j + 2])

                    {

                        if (LPD_char[j + 0] < LPD_fin[i + 0]) {
                            if (LPD_fin[i + 0] - LPD_char[j + 0] < OT) {
                                LPD_fin[i + 0] = LPD_char[j + 0];
                            }
                        }

                        if (LPD_char[j + 1] > LPD_fin[i + 1]) {
                            if (LPD_char[j + 1] - LPD_fin[i + 1] < OT) {
                                LPD_fin[i + 1] = LPD_char[j + 1];
                            }
                        }

                    }

                }
            }
            return LPD_fin;

        }

        else {
            LPD_fin[0] = 0;
            LPD_fin[1] = 0;
            LPD_fin[2] = 0;
            LPD_fin[3] = 0;
            return LPD_fin;
        }

    }
}
