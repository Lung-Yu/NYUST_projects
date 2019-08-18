package com.mipl.lungyu.licenseplaterecognition.sender.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.mipl.lungyu.licenseplaterecognition.sender.IMessageSender;
import com.mipl.lungyu.licenseplaterecognition.utils.Utils;

import java.io.BufferedOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by lungyu on 2016/11/11.
 */

public class SocketSender implements IMessageSender {

    private static final String TAG = "SocketSender";

    private final static String address = "140.125.46.79";// 連線的ip
    private final static int port = 9999;// 連線的port
    private final static int CONNECT_TIME_OUT = 100000;

    @Override
    public boolean Send(String payload) {

        boolean isSend = true  ;
        Socket client = new Socket();
        InetSocketAddress isa = new InetSocketAddress(this.address, this.port);
        try {
            client.connect(isa, CONNECT_TIME_OUT);
            BufferedOutputStream out = new BufferedOutputStream(client.getOutputStream());

            // send message payload to server side
            out.write(payload.getBytes());
            out.flush();

            //close connect and realse resource
            out.close();
            out = null;

            client.close();
            client = null;

        } catch (java.io.IOException e) {
            Log.d(TAG,"Socket連線有問題 !");
            System.out.println("IOException :" + e.toString());
            isSend =  false;
        }

        return isSend;
    }
}
