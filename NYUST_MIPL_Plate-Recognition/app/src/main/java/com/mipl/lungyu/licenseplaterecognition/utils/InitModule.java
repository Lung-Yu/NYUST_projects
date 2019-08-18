package com.mipl.lungyu.licenseplaterecognition.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by lungyu on 2016/11/14.
 */

public class InitModule {
    private static final String TAG = "InitModule";

    public static final String BASEIC_PATH = Environment.getExternalStorageDirectory().getPath();


    public static void copyFile() {
        File adaboostModels = new File(BASEIC_PATH + "/LPD/models/");
        if (!adaboostModels.exists()) adaboostModels.mkdir();

    }
}
