package com.developer.lungyu.ncyu_agricultural.module;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * Created by lungyu on 11/28/17.
 */

public class GrayImageProcess {
    public static Bitmap process(Bitmap bitmap){
        int width, height;
        height = bitmap.getHeight();
        width = bitmap.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bitmap, 0, 0, paint);
        return bmpGrayscale;
    }

    public static void grayscale(Bitmap bitmap,int thold){
        for (int i = 0; i < bitmap.getWidth(); i++) {
            for (int j = 0; j < bitmap.getHeight(); j++) {
                int col = bitmap.getPixel(i, j);
                //得到alpha通道的值
                int alpha = col & 0xFF000000;
                //得到图像的像素RGB的值
                int red =   (col & 0x00FF0000) >> 16;
                int green = (col & 0x0000FF00) >> 8;
                int blue =  (col & 0x000000FF) ;
                // 用公式X = 0.3×R+0.59×G+0.11×B计算出X代替原来的RGB
                int gray = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                gray = (gray > thold)?255:0;

                int newColor = alpha | (gray << 16) | (gray << 8) | gray;
                //bitmap[i][j] = gray;

                bitmap.setPixel(i,j,newColor);
            }
        }
    }

}
