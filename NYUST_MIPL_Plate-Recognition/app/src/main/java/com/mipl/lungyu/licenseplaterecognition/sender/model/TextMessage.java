package com.mipl.lungyu.licenseplaterecognition.sender.model;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mipl.lungyu.licenseplaterecognition.sender.IConvertMessage;

/**
 * Created by lungyu on 2016/11/28.
 */

public class TextMessage implements IConvertMessage {

    private String txt;
    private String time;

    public TextMessage(String text, String time) {
        this.txt = text;
        this.time = time;
    }

    @Override
    public View getView(Context context) {
        // TODO Auto-generated method stub
        LinearLayout panel = new LinearLayout(context);
        panel.setOrientation(LinearLayout.HORIZONTAL);
        panel.setBackgroundColor(Color.BLACK);

        TextView txtUserName = new TextView(context);
        txtUserName.setText(txt);
        txtUserName.setPadding(20, 20, 20, 20);
        txtUserName.setTextColor(Color.WHITE);
        txtUserName.setTextSize(20);

        TextView txtMessage = new TextView(context);
        txtMessage.setText(time);
        txtMessage.setPadding(20, 20, 20, 20);
        txtUserName.setTextSize(20);

        panel.addView(txtUserName);
        panel.addView(txtMessage);

        return panel;
    }

}
