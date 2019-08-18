package com.mipl.lungyu.licenseplaterecognition.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.mipl.lungyu.licenseplaterecognition.sender.IMessageSender;
import com.mipl.lungyu.licenseplaterecognition.sender.impl.SocketSender;

/**
 * Created by lungyu on 2016/11/24.
 */

public class SendTask extends AsyncTask {

    private String payload;
    private  boolean isSend = false;
    public SendTask( String payload) {

        this.payload = payload;
    }

    @Override
    protected Object doInBackground(Object[] params) {

        IMessageSender sender = new SocketSender();

        isSend = sender.Send(payload);
        return isSend;
    }
    public boolean IsSend(){
        return isSend;
    }
}
