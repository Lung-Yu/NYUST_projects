package com.developer.lungyu.ncyu_agricultural.module;

import android.graphics.Bitmap;
import android.util.Log;

import com.developer.lungyu.ncyu_agricultural.datamodel.ImageSubDataModel;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by lungyu on 11/29/17.
 */

public class SegmentImage {
    private Bitmap img;
    private List<ImageSubDataModel> result = new LinkedList<>();
    public SegmentImage(Bitmap bmp){
        this.img = bmp;

        segment();
    }

    private void segment(){
        List<Integer> vals = ImageProjection.horizontal(this.img,127);
        boolean isStart = false;
        int start_index = 0;

        int count_max = this.img.getHeight();

        for(int i=0;i<vals.size();i++) {

            if(isStart && count_max == vals.get(i)){
                ImageSubDataModel model = new ImageSubDataModel();
                model.setStart(start_index);
                model.setEnd(i);
                result.add(model);

                isStart = false;
            }

            //find start
            while(i<vals.size() && vals.get(i) == count_max){
                i++;
                start_index = i;
                isStart = true;
            }

        }
    }

    public List<ImageSubDataModel> getAllSubImageIndexs(){
        return this.result;
    }
}
