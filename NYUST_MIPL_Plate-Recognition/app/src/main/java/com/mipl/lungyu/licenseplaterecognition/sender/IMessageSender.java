package com.mipl.lungyu.licenseplaterecognition.sender;

import android.graphics.Bitmap;

/**
 * Created by lungyu on 2016/11/11.
 */

public interface IMessageSender {

    String TAG_APP_ID = "TAG";
    String TAG_LATITUDE = "latitude";
    String TAG_LONGITUDE = "longitude";
    String TAG_TIMESTAMP = "timeStamp";
    String TAG_IMAGE = "image";

    boolean Send(String payload);
}
