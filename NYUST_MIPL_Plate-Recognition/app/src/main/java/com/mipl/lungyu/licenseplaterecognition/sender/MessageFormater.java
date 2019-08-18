package com.mipl.lungyu.licenseplaterecognition.sender;

import android.graphics.Bitmap;

import com.mipl.lungyu.licenseplaterecognition.utils.Utils;

/**
 * Created by lungyu on 2016/11/24.
 */

public class MessageFormater {
    protected String APP_ID;

    protected Utils utils;
    public MessageFormater(String appId){
        APP_ID = appId;
        utils = Utils.getInstance();
    }

    public String getPayload(double lat, double lng, Bitmap bitmap,Bitmap bitmapSource) {
        //String imageBase64 = utils.ImageBase64(bitmap);
        String imageBase64 = "";
        String imageSourceBase64 = utils.ImageBase64(bitmapSource);
        return payloadFromat(APP_ID, lat, lng, imageBase64,imageSourceBase64, utils.currentTime());
    }

    protected static String payloadFromat(String tag, double latitude, double longitude, String imageBase64, String sourceBase64,String timeStamp) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append(String.format("\"tag\":\"%s\",", tag));
        sb.append(String.format("\"latitude\":\"%f\",", latitude));
        sb.append(String.format("\"longitude\":\"%f\",", longitude));
        sb.append(String.format("\"image\":\"%s\",", imageBase64));
        sb.append(String.format("\"src\":\"%s\",", sourceBase64));
        sb.append(String.format("\"timeStamp\":\"%s\"", timeStamp));
        sb.append("}");

        return sb.toString();
    }
}
