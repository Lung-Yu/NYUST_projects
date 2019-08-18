package com.developer.lungyu.ncyu_agricultural.module;

/**
 * Created by lungyu on 11/28/17.
 */

public class PixelCastor {

    private int _Red;
    private int _Green;
    private int _Blue;
    private int _Alpha;

    public PixelCastor(int col){
        this._Alpha = col & 0xFF000000;
        //得到图像的像素RGB的值
        this._Red =  (col & 0x00FF0000) >> 16;
        this._Green = (col & 0x0000FF00) >> 8;
        this._Blue =  (col & 0x000000FF) ;
    }


    public int get_Red() {
        return _Red;
    }

    public int get_Green() {
        return _Green;
    }

    public int get_Blue() {
        return _Blue;
    }

    public int get_Alpha() {
        return _Alpha;
    }
}
