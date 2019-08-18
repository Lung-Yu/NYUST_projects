package com.developer.lungyu.ncyu_agricultural.module;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * Created by lungyu on 10/11/17.
 */

public class ImgageProcessUtils {
    private static final String TAG = "Img Proc";

    private Bitmap _src;
    private int _width;
    private int _height;

    private int[][] _gray;

    public ImgageProcessUtils(Bitmap bitmap){
        _src = bitmap;
        _width = bitmap.getWidth();
        _height = bitmap.getHeight();
    }


    public void gray(){
        _gray = new int[_width][_height];
        for (int i = 0; i < _width; i++) {
            for (int j = 0; j < _height; j++) {
                int col = _src.getPixel(i, j);
                //得到alpha通道的值
                int alpha = col & 0xFF000000;
                //得到图像的像素RGB的值
                int red =   (col & 0x00FF0000) >> 16;
                int green = (col & 0x0000FF00) >> 8;
                int blue =  (col & 0x000000FF) ;
                // 用公式X = 0.3×R+0.59×G+0.11×B计算出X代替原来的RGB
                int gray = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                gray = (gray > 127)?255:0;

                int newColor = alpha | (gray << 16) | (gray << 8) | gray;
                _gray[i][j] = gray;

                _src.setPixel(i,j,newColor);
            }
        }
    }

    public void verProjection(int T){
        for(int i=0;i<_height;i++){
            int count = 0;
            for(int j=0;j<_width;j++){
                if(_gray[j][i] > T)
                    count++;
            }

            Log.d(TAG,count+"");
        }
    }

    public Bitmap getBitmap(){
        return _src;
    }

    public int[][] getMartix(){
        return _gray;
    }

    public int getWidth(){
        return _width;
    }
    public int getHeight(){
        return  _height;
    }
}
