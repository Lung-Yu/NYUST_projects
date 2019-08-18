package com.developer.lungyu.ncyu_agricultural.module;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class CameraView extends SurfaceView{
    private static String TAG = "CameraView";

    SurfaceHolder holder;
    Camera _Camera;

    private boolean _HasCachePicture = false;
    private Bitmap cachePicture;

    public CameraView(Context context) {
        super(context);
        init();
    };
    public CameraView(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }

    public CameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        init();
    };
    public CameraView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context,attrs,defStyleAttr,defStyleRes);
        init();
    }

    private void init(){
        holder = getHolder();//獲得surfaceHolder引用
        holder.addCallback(shCallback);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//設置類型
    }

    private SurfaceHolder.Callback2 shCallback = new SurfaceHolder.Callback2(){
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if(_Camera == null)
            {
                _Camera = Camera.open();//開启相機,不能放在構造函數中，不然不會顯示畫面
                CameraSetting();
                try {
                    _Camera.setPreviewDisplay(holder);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        private void CameraSetting(){
            _Camera.setDisplayOrientation(90);

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            _Camera.startPreview();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            _Camera.stopPreview();
            _Camera.release();
            _Camera = null;
            Log.d(TAG, "surfaceDestroyed");
        }

        @Override
        public void surfaceRedrawNeeded(SurfaceHolder holder) {

        }
    };


    public void takePicture() {
        _Camera.takePicture(null, null, _jpeg);
    }

    private Camera.PictureCallback _jpeg = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera Camera) {
            BufferedOutputStream bos=null;
            Bitmap bm0 = null;
            try {
                // get  Picture
                bm0 = BitmapFactory.decodeByteArray(data, 0, data.length,getBitmapFactoryOptions());

                Matrix m = new Matrix();
                m.setRotate(90,(float) bm0.getWidth() / 2, (float) bm0.getHeight() / 2);
                Bitmap bm = Bitmap.createBitmap(bm0, 0, 0, bm0.getWidth(), bm0.getHeight(), m, true);
                updateCache(bm);

                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    savePicture(bos,bm);
                }else{
                    Toast.makeText(getContext(),"SD Card Not Found", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if (bm0 != null){
                    bm0.recycle();
                }
            }
        }
    };



    private BitmapFactory.Options getBitmapFactoryOptions(){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize=16;
        //options.inTempStorage = new byte[5*1024]; //设置16MB的临时存储空间（不过作用还没看出来，待验证）
        return  options;
    }

    public boolean HasCachePicture(){
        return _HasCachePicture;
    }
    public Bitmap getCachePicture(){
        return cachePicture;
    }

    private void updateCache(Bitmap bitmap){
        if(_HasCachePicture){
            if(cachePicture != null){
                cachePicture.recycle();
                cachePicture = null;
            }
        }
        _HasCachePicture = true;
        cachePicture = bitmap;
    }

    private void savePicture(BufferedOutputStream bos,Bitmap bm) throws FileNotFoundException {
        Log.i(TAG, "Environment.getExternalStorageDirectory()="+Environment.getExternalStorageDirectory());
        File file = new File(Environment.getExternalStorageDirectory()+"/test.jpg");//拍摄照片的保存地址
        bos = new BufferedOutputStream(new FileOutputStream(file));
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);//将图片压缩到流中
    }


}
