package com.mipl.lungyu.licenseplaterecognition;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mipl.lungyu.licenseplaterecognition.sender.UIUpdateListener;
import com.mipl.lungyu.licenseplaterecognition.sender.impl.LoadCarsText;
import com.mipl.lungyu.licenseplaterecognition.utils.UniqueID;

public class CatchImageActivity extends CameraActivity {
    public static final String TAG = "CatchImageActivity";
    public static final int TACKE_PICTURE = 1000;

    private static final int DELAY_TIME = 200;
    private boolean IsStop = false;


    final Handler handler = new Handler();
    private FrameLayout txtResult;

    private static UniqueID uniqueID = new UniqueID();
    private LoadCarsText task;
    private Thread execute;

    private String ServerResultUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        txtResult = (FrameLayout) findViewById(R.id.resultLayout);
        ServerResultUrl = "http://140.125.46.79:8888/webservice?t=" + uniqueID.getID(getApplicationContext());
        Log.d(TAG,ServerResultUrl);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IsStop = false;
        handler.postDelayed(runTackPicture, DELAY_TIME);

    }

//    private void initLoadMessageTread() {
//        task = new LoadCarsText(getApplicationContext(), ServerResultUrl, new UIUpdateListener() {
//
//            @Override
//            public void update(final View v) {
//                // TODO Auto-generated method stub
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        // TODO Auto-generated method stub
//                        Toast.makeText(getApplicationContext(), previewLayout.getChildCount(), Toast.LENGTH_LONG).show();
//
//
//                        txtResult.removeAllViews();
//                        txtResult.addView(v);
//                        Log.d(TAG, "load message");
//                    }
//                });
//            }
//
//            @Override
//            public void update(String msg) {
//                // TODO Auto-generated method stub
//                Log.d(TAG, msg);
//            }
//        });
//        execute = new Thread(task);
//        if (!execute.isAlive())
//            execute.start();
//    }

    private final Runnable runTackPicture = new Runnable() {
        @Override
        public void run() {
            if (!IsStop) {
                getCurrentLatitude();
                mPreview.tackPicture();
                handler.postDelayed(runTackPicture, DELAY_TIME);
            }
        }
    };



    @Override
    protected void onStop() {
        IsStop = true;
        //task.setStop(true);
        super.onStop();
    }
}
