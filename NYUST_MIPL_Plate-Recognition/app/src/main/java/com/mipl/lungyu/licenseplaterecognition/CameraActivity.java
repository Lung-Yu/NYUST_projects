package com.mipl.lungyu.licenseplaterecognition;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.mipl.lungyu.licenseplaterecognition.camera.TackPictureCallBack;
import com.mipl.lungyu.licenseplaterecognition.camera.CameraPreview;
import com.mipl.lungyu.licenseplaterecognition.utils.PermissionRequests;
import com.mipl.lungyu.licenseplaterecognition.views.UIPosition;

import java.io.File;
import java.io.FileOutputStream;

public class CameraActivity extends GPSActivity {
    private Camera mCamera;
    protected FrameLayout previewLayout;
    protected CameraPreview mPreview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();

        releaseCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkCameraPermission();
        checkStoragePermission();
    }

    private void initCamera() {
        // Create an instance of Camera
        mCamera = getCameraInstance();

        if (mCamera == null) {
            Toast.makeText(this, "請開啟相機存取權限", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create our Preview view and set it as the content of our activity.
        mPreview = CameraPreview.Initialize(this, mCamera);
        UIPosition uiPosition = new UIPosition(this,null);

        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        preview.addView(uiPosition);
    }

    /**
     * Check if this device has a camera
     */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }

    // check Android 6 permission
    public void checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PermissionRequests.WRITE_EXTERNAL_STORAGE);//1 can be another integer
        }
    }

    // check Android 6 permission
    public void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Log.i("TEST", "Granted");
            initCamera();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PermissionRequests.CAMERA);//1 can be another integer
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionRequests.CAMERA:
                initCamera();
                break;
        }
    }

    /**
     * A safe way to get an instance of the Camera object.
     */

    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }



}
