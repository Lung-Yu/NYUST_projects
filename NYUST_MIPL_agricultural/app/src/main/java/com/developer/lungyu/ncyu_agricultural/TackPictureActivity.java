package com.developer.lungyu.ncyu_agricultural;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lungyu.ncyu_agricultural.module.CameraPreview;
import com.developer.lungyu.ncyu_agricultural.module.CameraView;
import com.developer.lungyu.ncyu_agricultural.module.OCRFactory;
import com.developer.lungyu.ncyu_agricultural.module.PreOCRFactory;
import com.developer.lungyu.ncyu_agricultural.module.UIPosition;
import com.googlecode.tesseract.android.TessBaseAPI;



public class TackPictureActivity extends AppCompatActivity {


    private boolean hasPicture = false;

    private ImageView imageView;
    private TextView txtResult;

    private Camera mCamera;
    protected CameraPreview mPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tack_picture);

        imageView = (ImageView) findViewById(R.id.imageView1);
        txtResult = (TextView)findViewById(R.id.textView1);
    }
    @Override
    protected void onResume() {
        super.onResume();
        checkCameraPermission();
        //checkStoragePermission();
    }

    // check Android 6 permission
    public void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Log.i("TEST", "check Camera Permission");
            initCamera();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);//1 can be another integer
        }
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

        if(mPreview.getParent()!=null){
            ((FrameLayout)mPreview.getParent()).removeView(mPreview);
        }

        preview.addView(mPreview);
        preview.addView(uiPosition);

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

    public void onTakePicture(View view){
        //cameraView.takePicture();
        mPreview.takePicture();

        Bitmap cache = mPreview.getImage();

        if(cache == null)
            return;

        PreOCRFactory factory = new PreOCRFactory(cache);
        imageView.setImageBitmap(factory.process());
        hasPicture = true;
    }

    public void onRecongnize(View view){
        if(hasPicture){

            imageView.setDrawingCacheEnabled(true);
            final Bitmap bitmap = imageView.getDrawingCache();
            final String ocrStr = OCRFactory.ocr(bitmap);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txtResult.setText(ocrStr);
                }
            });
        }
    }

    public void onEnter(View v){
        Intent intent = new Intent();
        final String tag = getResources().getString(R.string.tag_ocr_result);
        final String msg = txtResult.getText().toString();
        intent.putExtra(tag, msg);
        setResult(RESULT_OK, intent);
        finish();
    }
    public void onChancel(View v){
        setResult(RESULT_CANCELED);
        finish();
    }

    public Bitmap gray2Binary(Bitmap graymap) {
        //创建二值化图像
        Bitmap binarymap = null;

        try{
            //得到图形的宽度和长度
            int width = graymap.getWidth();
            int height = graymap.getHeight();

            binarymap = graymap.copy(Bitmap.Config.ARGB_8888, true);

            //依次循环，对图像的像素进行处理
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    //得到当前像素的值
                    int col = binarymap.getPixel(i, j);
                    //得到alpha通道的值
                    int alpha = col & 0xFF000000;
                    //得到图像的像素RGB的值
                    int red =   (col & 0x00FF0000) >> 16;
                    int green = (col & 0x0000FF00) >> 8;
                    int blue =  (col & 0x000000FF) ;
                    // 用公式X = 0.3×R+0.59×G+0.11×B计算出X代替原来的RGB
                    int gray = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                    //对图像进行二值化处理
                    if (gray <= 150) {
                        gray = 0;
                    } else {
                        gray = 255;
                    }
                    // 新的ARGB
                    int newColor = alpha | (gray << 16) | (gray << 8) | gray;
                    //设置新图像的当前像素值
                    binarymap.setPixel(i, j, newColor);
                }
            }
        }catch (Exception e){

        }

        return binarymap;
    }

    @Override
    protected void onDestroy() {
        releaseCamera();
        super.onDestroy();
    }

    private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }

}
