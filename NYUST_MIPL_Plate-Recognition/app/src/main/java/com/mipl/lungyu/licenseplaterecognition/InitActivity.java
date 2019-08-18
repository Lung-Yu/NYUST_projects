package com.mipl.lungyu.licenseplaterecognition;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mipl.lungyu.licenseplaterecognition.utils.IDFactory;
import com.mipl.lungyu.licenseplaterecognition.utils.LogTracker;
import com.mipl.lungyu.licenseplaterecognition.utils.UniqueID;

public class InitActivity extends Activity {

    LogTracker logTracker;

    public static String APP_ID="non-id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cemra);

        logTracker = new LogTracker(getApplicationContext());

        APP_ID = new UniqueID().getID(getApplicationContext());
    }
}
