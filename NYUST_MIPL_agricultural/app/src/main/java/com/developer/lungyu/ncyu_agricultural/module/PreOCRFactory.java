package com.developer.lungyu.ncyu_agricultural.module;

import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by lungyu on 7/10/2017.
 */

public class PreOCRFactory {

    int _width;
    int _height;
    int _imgMap[][];

    int _bound_Start;
    int _bound_End;

    Bitmap _src;

    public PreOCRFactory(Bitmap bitmap){
        ImgageProcessUtils proc = new ImgageProcessUtils(bitmap);
        proc.gray();
        proc.verProjection(127);


        _src = proc.getBitmap();
        _imgMap = proc.getMartix();
        _width = proc.getWidth();
        _height = proc.getHeight();

    }

    private void findBound(){

        int T = 127;
        int T_low = (_width / 10) * 0;
        int T_high = (_width / 10) * 10;

        int start = 0;
        int end = _height;

        for (int j = _height/2; j >= 0 ; j--) {
            int count = 0;
            for (int i = 0 ; i < _width; i++) {
                int val = _imgMap[i][j];
                if(val > T)
                    count++;
            }
            //if(count >T_low && count < T_high){
            if(count < 5){
                start = j;
                break;
            }
        }

        for (int j = _height/2; j < _height; j++) {
            int count = 0;
            for (int i = 0 ; i < _width; i++) {
                if(_imgMap[i][j] > T)
                    count++;
            }
            //if(count >T_low && count < T_high){
            if(count < 5){
                end = j;
                break;
            }
        }



        Log.d("GGG","_width : " + _width);
        Log.d("GGG","_height : " + _height);

        _bound_Start = start;
        _bound_End = end;

        Log.d("GGG","_bound_Start : " + _bound_Start);
        Log.d("GGG","_bound_End : " + _bound_End);
    }

    public Bitmap process(){
        findBound();
        Bitmap newImage = Bitmap.createBitmap(_src,0,_bound_Start,_width,_bound_End - _bound_Start);


        return newImage;
    }

}
