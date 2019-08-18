package com.mipl.lungyu.licenseplaterecognition.sender.impl;

import android.content.Context;
import android.util.Log;

import com.mipl.lungyu.licenseplaterecognition.sender.IConvertMessage;
import com.mipl.lungyu.licenseplaterecognition.sender.UIUpdateListener;
import com.mipl.lungyu.licenseplaterecognition.sender.model.TextMessage;
import com.mipl.lungyu.licenseplaterecognition.utils.LoadHTMLModel;

/**
 * Created by lungyu on 2016/11/28.
 */

public class LoadCarsText implements Runnable {

    private final String url;
    private final UIUpdateListener uiUpdate;
    private final Context context;
    private boolean Stop = false;
    private int count = 0;

    public LoadCarsText(Context context, String url, UIUpdateListener uiUpdate) {
        this.url = url;
        this.uiUpdate = uiUpdate;
        this.context = context;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (!Stop) {
            String html = LoadHTMLModel.getHtmlByGet(url);
            Log.d("html", html);
            String[] lines = html.split("\n");
            for (int i = count; i < lines.length; i++) {
                String[] cols = lines[i].split(",");
                if (cols.length < 2)
                    continue;
                IConvertMessage convert = new TextMessage(cols[0], cols[1]);
                // uiUpdate.update(convert.getView(context));
                uiUpdate.update(cols[0]);
            }
            count = lines.length;
        }
    }

    public boolean isStop() {
        return Stop;
    }

    public void setStop(boolean stop) {
        Stop = stop;
    }

}