package com.mipl.lungyu.licenseplaterecognition;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.webkit.PermissionRequest;
import android.widget.TextView;

import com.mipl.lungyu.licenseplaterecognition.jni.LPDModule;
import com.mipl.lungyu.licenseplaterecognition.utils.FileOption;
import com.mipl.lungyu.licenseplaterecognition.utils.InitModule;
import com.mipl.lungyu.licenseplaterecognition.utils.PermissionRequests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SplashActivity extends Activity {

    private String cascadeFile1 = InitModule.BASEIC_PATH + "/LPD/models/output+670-500-wg.xml";
    private String cascadeFile2 = InitModule.BASEIC_PATH + "/LPD/models//output_char+1250-1038-wg.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        LPDModule lpd = new LPDModule();

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
//        tv.setText(lpd.LPD(cascadeFile1, null, 0, 0, 0));
//        tv.setText(FileOption.readFromSDcard(cascadeFile2));
        tv.setText(lpd.LPD(cascadeFile1,null,10,10,3));

        //InitModule.copyFile();

        initFinish();
    }

    private void initFinish() {
        Intent intent = new Intent(this, MainActivity.class);
        //Intent intent = new Intent(this, TestingActivity.class);
        startActivity(intent);
        finish();
    }



}
