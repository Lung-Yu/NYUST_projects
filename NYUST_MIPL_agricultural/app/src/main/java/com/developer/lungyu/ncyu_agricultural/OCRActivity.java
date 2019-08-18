package com.developer.lungyu.ncyu_agricultural;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lungyu.ncyu_agricultural.module.OCRFactory;
import com.developer.lungyu.ncyu_agricultural.module.PreOCRFactory;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.FileNotFoundException;


public class OCRActivity extends AppCompatActivity {

    static final String TESSBASE_PATH = "/storage/emulated/0/download/tesseract/";
    static final String DEFAULT_LANGUAGE = "eng";
    static final String CHINESE_LANGUAGE = "chi_tra";
    static final String LUNG_WRITE_LANGUAGE = "lung";

    private TextView txtResult;
    private ImageView imgSrc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);

        imgSrc = (ImageView)findViewById(R.id.imageView1);
        txtResult = (TextView) findViewById(R.id.textView);
    }
    public void btnProcess(View v){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = drawableToBitmap(getResources().getDrawable(R.drawable.test_writer_ocr_1));
                imgSrc.setImageBitmap(bitmap);

                PreOCRFactory factory = new PreOCRFactory(bitmap);
                Bitmap gray = factory.process();
                //imgSrc.setImageBitmap(gray);

//             txtResult.setText(res);
            }
        });
    }
    public void btnExecute(View v){
        //Toast.makeText(this,res,Toast.LENGTH_SHORT).show();
        imgSrc.setDrawingCacheEnabled(true);
        final Bitmap bitmap = imgSrc.getDrawingCache();
        String res = OCRFactory.ocr(bitmap);
        //String res = ocrWithEnglish();
        Log.d("OCR_RES",res);
    }



    public static Bitmap drawableToBitmap(Drawable drawable) {
                // 取 drawable 的長寬
            int w = drawable.getIntrinsicWidth();
            int h = drawable.getIntrinsicHeight();

            // 取 drawable 的顏色格式
            Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                    : Bitmap.Config.RGB_565;
            // 建立對應 bitmap
            Bitmap bitmap = Bitmap.createBitmap(w, h, config);
            // 建立對應 bitmap 的畫布
            Canvas canvas = new Canvas(bitmap);
           drawable.setBounds(0, 0, w, h);
            // 把 drawable 內容畫到畫布中
            drawable.draw(canvas);
            return bitmap;
    }




    public String ocrWithEnglish() {
        String resString = "";

        imgSrc.setDrawingCacheEnabled(true);
        final Bitmap bitmap = imgSrc.getDrawingCache();
        final TessBaseAPI ocrApi = new TessBaseAPI();

        ocrApi.init(TESSBASE_PATH, CHINESE_LANGUAGE);
        ocrApi.setPageSegMode(TessBaseAPI.PageSegMode.PSM_SINGLE_LINE);

        ocrApi.setImage(bitmap);
        resString = ocrApi.getUTF8Text();

        ocrApi.clear();
        ocrApi.end();
        return  resString;
    }
}
