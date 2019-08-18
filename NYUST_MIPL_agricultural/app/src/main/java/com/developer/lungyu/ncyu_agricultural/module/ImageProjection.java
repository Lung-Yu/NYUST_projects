package com.developer.lungyu.ncyu_agricultural.module;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lungyu on 11/28/17.
 */

public class ImageProjection {
    public static List<Integer> vertical(Bitmap bitmap,int thold){
        List<Integer> list = new ArrayList<>(bitmap.getHeight());

        for(int y=0;y<bitmap.getHeight();y++){
            int count = 0;
            for(int x=0;x<bitmap.getWidth();x++){
                int val = new PixelCastor(bitmap.getPixel(x,y)).get_Red();
                if(val>thold)
                    count++;
            }
            list.add(count);
        }

        return list;
    }

    public static List<Integer> horizontal(Bitmap bitmap,int thold){
        List<Integer> list = new ArrayList<>(bitmap.getWidth());

        for(int x=0;x<bitmap.getWidth();x++){
            int count = 0;
            for(int y=0;y<bitmap.getHeight();y++){
                int val = new PixelCastor(bitmap.getPixel(x,y)).get_Red();
                if(val>thold)
                    count++;
            }
            list.add(count);
        }

        return list;
    }
}
