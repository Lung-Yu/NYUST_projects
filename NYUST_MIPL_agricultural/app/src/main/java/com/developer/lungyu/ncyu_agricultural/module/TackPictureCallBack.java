package com.developer.lungyu.ncyu_agricultural.module;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by lungyu on 2016/11/14.
 */

public class TackPictureCallBack implements Camera.PictureCallback {

    public static final String TAG = "TackPictureCallBack";
    public static final String PHOTO_PATH = "/sdcard/";

    private static int count = 0;
    private Bitmap cache;

    public TackPictureCallBack() {

    }

    public Bitmap getPicture(){
        return  cache;
    }

    /**
     * called when jpeg-image ready, just send to the parent handler the whole  image to be processed
     */
    @Override
    public void onPictureTaken(byte[] data, Camera camera) {

        Log.d(TAG,"onPictureTaken" + count++);

        if(cache != null){
            cache.recycle();
            cache = null;
        }

        cache = rotate(BitmapFactory.decodeByteArray(data, 0, data.length,getBitmapFactoryOptions()));

        // 创建并保存图片文件
        File mFile = new File(PHOTO_PATH);
        if (!mFile.exists()) {
            mFile.mkdirs();
        }

        File pictureFile = new File(PHOTO_PATH, String.format("%s.jpg", "test"));
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            cache.compress(Bitmap.CompressFormat.JPEG, 100, fos);

            fos.close();
            Log.i(TAG, "拍摄成功！");
        } catch (Exception error) {
            Log.e(TAG, "拍摄失败");
            error.printStackTrace();
        }
    }

    private Bitmap rotate(Bitmap bm0){
        Matrix m = new Matrix();
        m.setRotate(90,(float) bm0.getWidth() / 2, (float) bm0.getHeight() / 2);
        Bitmap bm = Bitmap.createBitmap(bm0, 0, 0, bm0.getWidth(), bm0.getHeight(), m, true);

        bm0.recycle();
        bm0 = null;

        return bm;
    }

    private BitmapFactory.Options getBitmapFactoryOptions(){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize=10;
        //options.inTempStorage = new byte[5*1024]; //设置16MB的临时存储空间（不过作用还没看出来，待验证）
        return  options;
    }

}
