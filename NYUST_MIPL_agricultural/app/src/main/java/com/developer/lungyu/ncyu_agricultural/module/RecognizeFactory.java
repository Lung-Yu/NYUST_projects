package com.developer.lungyu.ncyu_agricultural.module;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.icu.text.LocaleDisplayNames;
import android.nfc.Tag;
import android.util.Log;

import com.developer.lungyu.ncyu_agricultural.datamodel.ImageSubDataModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lungyu on 11/28/17.
 */

public class RecognizeFactory {

    private static final String TAG = "RecognizeFactory";
    private static final int scale_size = 500;
    private int min_segment_width = scale_size / 50;

    private Bitmap _imgSrc;
    private Bitmap _imgResult;
    private int _imgWidth;
    private int _imgHeight;
    private List<ImageSubDataModel> subDataModels = new ArrayList<>();


    public RecognizeFactory(Bitmap bitmap){
        this._imgSrc = bitmap;
        _imgWidth = _imgSrc.getWidth();
        _imgHeight = _imgSrc.getHeight();

        scale_image();
        //_imgResult = GrayImageProcess.process(_imgResult);
        GrayImageProcess.grayscale(_imgResult,127);
        List<Integer> vals = ImageProjection.vertical(_imgResult,127);

        BoundaryLineCalculator calculator = new BoundaryLineCalculator(vals);
        List<Integer> indexs = calculator.getAllBoundaryIndex();
        crop_image(indexs);
        SegmentImage segmentImage = new SegmentImage(_imgResult);
        List<ImageSubDataModel> items =segmentImage.getAllSubImageIndexs();
        removeNoise(items);
    }

    private void removeNoise(List<ImageSubDataModel> items){
        List<Integer> removeIndex = new LinkedList<>();
        for(int i=0;i<items.size();i++){
            if(items.get(i).getDifference() > min_segment_width)
                subDataModels.add(items.get(i));
        }
    }

    private void scale_image(){
        float scaleWidth = scale_size / (float)_imgWidth;
        float scaleHeight = scale_size / (float)_imgHeight;
        Log.i(TAG,"SettingManager====> set scale value : "+scaleWidth + ":" + scaleHeight);
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // create the new Bitmap object
        _imgResult = Bitmap.createBitmap(_imgSrc, 0, 0, _imgWidth,_imgHeight, matrix, true);

        Log.d(TAG,"Width : " + _imgResult.getWidth());
        Log.d(TAG,"Height : " + _imgResult.getHeight());
    }

    private void crop_image(List<Integer> indexs){
        int w = _imgResult.getWidth(); // 得到图片的宽，高
        int h = _imgResult.getHeight();
        int new_hight = indexs.get(2) - indexs.get(1);
        int cropWidth = w >= h ? h : w;// 裁切后所取的正方形区域边长
        cropWidth /= 2;
        int cropHeight = (int) (cropWidth / 1.2);
        _imgResult = Bitmap.createBitmap(_imgResult,0,indexs.get(1) , _imgResult.getWidth(), new_hight, null, false);
    }

    public Bitmap getResultImage(){
        return _imgResult;
    }

    public int getSubImageSize(){
        return subDataModels.size();
    }

    public Bitmap getSubImage(int index){
        ImageSubDataModel item = subDataModels.get(index);

        int w = _imgResult.getWidth(); // 得到图片的宽，高
        int h = _imgResult.getHeight();
        int new_width = item.getEnd() - item.getStart();
        int cropWidth = w >= h ? h : w;// 裁切后所取的正方形区域边长
        cropWidth /= 2;
        int cropHeight = (int) (cropWidth / 1.2);
        Bitmap bitmap = Bitmap.createBitmap(_imgResult,item.getStart(),0 ,new_width, _imgResult.getHeight(), null, false);

        Log.d(TAG,"image width ---> " +new_width);

        return bitmap;
    }


    public boolean IsRecognize(){
        if(subDataModels.size() < 11)
            return false;
        return true;
    }

    private int getReconginzeIndex(){
        int bound_line = scale_size / 15;
        int target_index = 0;

        for(int i=0;i<subDataModels.size();i++)
            if(subDataModels.get(i).getDifference() > bound_line)
                target_index = i;
        return target_index;
    }


    public String getRecognizeString(){
        String[] values = {"分級","預冷","包裝","冷藏"};
        int target_index = getReconginzeIndex();

        int index = target_index / 2;
        Log.d(TAG,"index : "+index);
        return values[index];
    }

    public Bitmap getReconginzeImage(){

        int target_index = getReconginzeIndex();
        return getSubImage(target_index);
    }


}
