package com.mipl.lungyu.licenseplaterecognition.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by lungyu on 2016/11/11.
 */

public class LogTracker {

    private Context context;

    public LogTracker(Context context){
        this.context = context;
    }

    public void d(String message) {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

}
