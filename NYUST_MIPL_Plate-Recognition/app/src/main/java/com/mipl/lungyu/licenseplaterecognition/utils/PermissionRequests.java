package com.mipl.lungyu.licenseplaterecognition.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * Created by lungyu on 2016/11/13.
 */

public class PermissionRequests {

    public static final int CAMERA = 10;
    public static final int WRITE_EXTERNAL_STORAGE = 20;

    // check Android 6 permission
    public void checkCameraPermission(Activity context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Log.i("TEST", "Granted");
        } else {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CAMERA}, 1);//1 can be another integer
        }
    }


}
