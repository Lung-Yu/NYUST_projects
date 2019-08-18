package com.mipl.lungyu.licenseplaterecognition.jni;

/**
 * Created by lungyu on 2016/11/14.
 */

public class LPDModule {
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI(String cascade);


    public native String LPD(String cascade,int[] pixles,int w,int h,int t);

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
}
