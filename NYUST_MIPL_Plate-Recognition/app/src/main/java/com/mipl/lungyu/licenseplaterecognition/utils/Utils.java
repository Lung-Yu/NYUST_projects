package com.mipl.lungyu.licenseplaterecognition.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lungyu on 2016/11/11.
 */
public class Utils {
    private static Utils ourInstance = new Utils();

    public static Utils getInstance() {
        return ourInstance;
    }

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm");

    private Utils() {

    }
    public static String ImageBase64(String filePath) {
        Bitmap bm = BitmapFactory.decodeFile(filePath);

        return ImageBase64(bm);
    }

    public static String ImageBase64(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap
        // object
        byte[] byteArrayImage = baos.toByteArray();
        return Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
    }
    public String getFileName(String ext) {
        Date date = new Date();
        String name = dateFormat.format(date);
        return name + "." + ext;
    }
    public String currentTime() {
        Date curDate = new Date(System.currentTimeMillis()); // 獲取當前時間
        return dateFormat.format(curDate);
    }

}
