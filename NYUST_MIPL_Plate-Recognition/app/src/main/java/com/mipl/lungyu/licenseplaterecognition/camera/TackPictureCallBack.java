package com.mipl.lungyu.licenseplaterecognition.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.util.Log;

import com.mipl.lungyu.licenseplaterecognition.CameraActivity;
import com.mipl.lungyu.licenseplaterecognition.GPSActivity;
import com.mipl.lungyu.licenseplaterecognition.sender.MessageFormater;
import com.mipl.lungyu.licenseplaterecognition.utils.PlateDetection;
import com.mipl.lungyu.licenseplaterecognition.utils.SendTask;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by lungyu on 2016/11/14.
 */

public class TackPictureCallBack implements Camera.PictureCallback {

    public static final String TAG = "TackPictureCallBack";
    public static final String PHOTO_PATH = "/sdcard/";

    private Context context;



    private static int count = 0;


    public TackPictureCallBack() {

    }

    /**
     * called when jpeg-image ready, just send to the parent handler the whole  image to be processed
     */
    @Override
    public void onPictureTaken(byte[] data, Camera camera) {

        Log.d(TAG,"onPictureTaken" + count++);

        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        // 创建并保存图片文件
        File mFile = new File(PHOTO_PATH);
        if (!mFile.exists()) {
            mFile.mkdirs();
        }

        File pictureFile = new File(PHOTO_PATH, String.format("%s.jpg", "test"));
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            bitmap.recycle();
            fos.close();
            Log.i(TAG, "拍摄成功！");
        } catch (Exception error) {
            Log.e(TAG, "拍摄失败");
            error.printStackTrace();
        }
        //pate dectect
        PlateDetection.LPD(context);

        //finished saving picture
        CameraPreview.safeToTakePicture = true;
    }

    public void setContext(Context context){
        this.context = context;
    }

    public Context getContext(){
        return context;
    }


}
