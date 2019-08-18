package com.developer.lungyu.ncyu_agricultural;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.lungyu.ncyu_agricultural.module.RecognizeFactory;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class RecognizeActivity extends AppCompatActivity {

    private int PICK_IMAGE_REQUEST = 1;
    private final static String TAG = "RecognizeActivity";
    private Bitmap imgSelected = null;
    private ImageView imageView;
    private ImageView imageViewSub;
    private ImageView imageViewSouce;

    private TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognize);


        imageView = (ImageView) findViewById(R.id.imageView);
        imageViewSouce = (ImageView) findViewById(R.id.imageView1);
        imageViewSub = (ImageView) findViewById(R.id.imageView2);

        txtResult = (TextView) findViewById(R.id.textView7);
    }

    public void onSelected(View view){
        pickImage();

    }

    private void recoginzeProcess(){
        RecognizeFactory factory = new RecognizeFactory(imgSelected);
        imageView.setImageBitmap(factory.getResultImage());

        //saveImage(factory.getResultImage());

        if(factory.IsRecognize()){
            imageViewSub.setImageBitmap(factory.getReconginzeImage());
            Log.d(TAG,factory.getRecognizeString());
            txtResult.setText(factory.getRecognizeString());
        }

    }

    private void pickImage(){
        Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void saveImage(Bitmap bmp){
        String path = Environment.getExternalStorageDirectory().toString();
        File file = new File(path, "FitnessGirl.png"); // the File to save , append increasing numeric counter to prevent files from getting overwritten.


        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            tackImage(uri);
            showImageOnUI();
            recoginzeProcess();
        }
    }

    private void showImageOnUI(){
        if(imgSelected == null)
            return;

        imageViewSouce.setImageBitmap(imgSelected);
        imageView.setImageBitmap(imgSelected);
    }

    private void tackImage(Uri uri){
        try {
            imgSelected = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            // Log.d(TAG, String.valueOf(bitmap));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
