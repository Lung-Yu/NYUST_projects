package com.developer.lungyu.ncyu_agricultural.module;

/**
 * Created by lungyu on 2016/11/13.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.developer.lungyu.ncyu_agricultural.module.TackPictureCallBack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A basic Camera preview class
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "CameraPreview";
    private SurfaceHolder mHolder;
    private Camera mCamera;

    private Context mContext;

    private static CameraPreview ourInstance = null;
    public static boolean safeToTakePicture = true;

    private static final int DisplayOrientation = 90;


    /**
     * frame message
     */
    private Point ptScreenResolution;
    private Rect framingRect;
    private static final int MIN_FRAME_WIDTH = 240;
    private static final int MIN_FRAME_HEIGHT = 180;
    private static final int MAX_FRAME_WIDTH = 480;
    private static final int MAX_FRAME_HEIGHT = 360;

    private boolean IsPreview = false;

    private static final TackPictureCallBack takePictureCallBack = new TackPictureCallBack();

    private CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        mContext = context;
        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public static CameraPreview Initialize(Context context, Camera camera) {
        if (ourInstance == null || ourInstance.mCamera == null)
            ourInstance = new CameraPreview(context, camera);
        return ourInstance;
    }

    public static CameraPreview getInstance() {
        return ourInstance;
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            IsPreview = true;

            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
            mCamera.setDisplayOrientation(DisplayOrientation);
            setCameraParameters();

            Log.d(TAG, "surfaceCreated");

        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }

    }

    private void setCameraParameters() {
        Camera.Parameters parameters = mCamera.getParameters();
        int zoom = parameters.getMaxZoom();
        Log.d(TAG, "Zoom " + zoom);

        List<String> focusModes = parameters.getSupportedFocusModes();
        if(focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)){ parameters.setFocusMode( Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO); }
        else if(focusModes.contains(Camera.Parameters.FLASH_MODE_AUTO)){ parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO); }

        //parameters.setZoom(99);
        mCamera.setParameters(parameters);
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
        releaseCamera();
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here
        Log.d(TAG, "surfaceChanged");

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
            mCamera.autoFocus(autoFocusCallback);

        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    private Camera.AutoFocusCallback autoFocusCallback =  new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {

        }
    };

    /**
     * @return the screen resolution
     */
    private Point GetScreenResolution() {
        if (ptScreenResolution == null) {
            WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            ptScreenResolution = new Point(display.getWidth(), display.getHeight());
        }
        return ptScreenResolution;
    }

    public Rect GetFramingRectline() {
        Point screenResolution = GetScreenResolution();
        if (framingRect == null) {

            int width = screenResolution.x * 3 / 4;
            if (width < MIN_FRAME_WIDTH) {
                width = MIN_FRAME_WIDTH;
            } else if (width > MAX_FRAME_WIDTH) {
                width = MAX_FRAME_WIDTH;
            }
            int height = screenResolution.y * 3 / 4;
            if (height < MIN_FRAME_HEIGHT) {
                height = MIN_FRAME_HEIGHT;
            } else if (height > MAX_FRAME_HEIGHT) {
                height = MAX_FRAME_HEIGHT;
            }
            int leftOffset = (screenResolution.x - width) / 2;
            int topOffset = (screenResolution.y - height) / 2;
            framingRect = new Rect(leftOffset, topOffset, leftOffset + width, topOffset + height);
            Log.d(TAG, "Calculated framing rect: " + framingRect);
        }
        return framingRect;

    }

    public void takePicture() {
        Log.d(TAG, "tackPicture");
        try{
            if (safeToTakePicture) {
                safeToTakePicture = false;
                mCamera.takePicture(null, null, takePictureCallBack);
                safeToTakePicture = true;
            }
        }catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public Bitmap getImage(){
        return takePictureCallBack.getPicture();
    }

}