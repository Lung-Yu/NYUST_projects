package com.developer.lungyu.ncyu_agricultural;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    private EditText txtName;           //名稱
    private EditText txtProvider;        //生產者
    private EditText txtSpaceOrigin;     //產地
    private TextView txtGPSPosition;     //GPS 座標
    private EditText txtProductName;    //產品名稱
    private TextView txtCaptureTime;    //拍攝時間

    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;

    private SettingPreferences setting;
    /***  GPS ***/
    protected static double currentLatitude = 0;
    protected static double currentLongitude = 0;
    private LocationManager mLocationManager;
    private static final int LOCATION_UPDATE_MIN_DISTANCE = 1;
    private static final int LOCATION_UPDATE_MIN_TIME = 50;

    /***Current  Capture Time ***/
    private String _dateStr="";

    private static final int REQUEST_VIDEO_CAPTURE = 100;
    private static final int REQUEST_OCR_NAME = 5010;
    private static final int REQUEST_OCR_PROVIDER = 5020;
    private static final int REQUEST_OCR_SPACE_ORIGIN = 5030;
    private static final int REQUEST_OCR_PRODUCT = 5040;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindView();
        loadInitial();

        initGPS();
        UpdateCurrenTime();
    }

    private void loadInitial() {
        setting = new SettingPreferences(this);
        setting.load();

        txtName.setText(setting.getName());
        txtProvider.setText(setting.getProvider());
        txtSpaceOrigin.setText(setting.getSpaceOrigin());
        txtGPSPosition.setText(setting.getGPSPosition());
        txtProductName.setText(setting.getProductName());
        txtCaptureTime.setText(setting.getCaptureTime());
    }

    private void updatePreferences(){
        if(setting == null )
            setting = new SettingPreferences(this);

        setting.setName(txtName.getText().toString());
        setting.setProvider(txtProvider.getText().toString());
        setting.setSpaceOrigin(txtSpaceOrigin.getText().toString());
        setting.setGPSPosition(txtGPSPosition.getText().toString());
        setting.setProductName(txtProductName.getText().toString());
        setting.setCaptureTime(txtCaptureTime.getText().toString());

        setting.save();
    }

    private void bindView(){
        txtName = (EditText) findViewById(R.id.textView1);
        txtProvider = (EditText) findViewById(R.id.textView2);
        txtSpaceOrigin = (EditText) findViewById(R.id.textView3);
        txtGPSPosition = (TextView) findViewById(R.id.textView4);
        txtProductName = (EditText) findViewById(R.id.textView5);
        txtCaptureTime = (TextView) findViewById(R.id.textView6);

        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
    }


    private void sendTaskToOcr(int requestCode){
        Intent intent=new Intent();
        intent.setClass(this, TackPictureActivity.class);
        startActivityForResult(intent, requestCode);
    }

    public void recongnizeNameOnClick(View v){
        sendTaskToOcr(REQUEST_OCR_NAME);
    }
    public void recongnizeProviderOnClick(View v){
        sendTaskToOcr(REQUEST_OCR_PROVIDER);
    }
    public void recongnizeSpaceOnClick(View v){
        sendTaskToOcr(REQUEST_OCR_SPACE_ORIGIN);
    }
    public void recongnizeProdctNameOnClick(View v){
        sendTaskToOcr(REQUEST_OCR_PRODUCT);
    }
    private void initGPS(){
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getCurrentLocation();
    }
    private void getCurrentLocation() {
        boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location location = null;
        if (!(isGPSEnabled || isNetworkEnabled)) {
            // Snackbar.make(mMapView, R.string.error_location_provider,
            // Snackbar.LENGTH_INDEFINITE).show();
        } else {
            if (isNetworkEnabled) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    return;
                }
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_UPDATE_MIN_TIME,
                        LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            } else if (isGPSEnabled) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_UPDATE_MIN_TIME,
                        LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
        if (location != null) {
            Log.d("location", String.format("getCurrentLocation(%f, %f)", location.getLatitude(), location.getLongitude()));
        }
    }

    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {

                // drawMarker(location);
                // mLocationManager.removeUpdates(mLocationListener);
                currentLatitude = location.getLatitude();
                currentLongitude = location.getLongitude();
                UpdateGPS(currentLatitude,currentLongitude);
            } else {
                // Logger.d("Location is null");
                Toast.makeText(getApplicationContext(), "Location is null", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private void scanQRCode(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    private void goToMakeQrCodeActivity(){
        //String qrStr = txtName.getText().toString();
        String qrStr = "";

        qrStr += txtName.getText().toString() + " ";
        qrStr += txtProvider.getText().toString() + " ";
        qrStr += txtSpaceOrigin.getText().toString() + " ";
        qrStr += txtProductName.getText().toString() + " ";

        if(radioButton1.isChecked())
            qrStr += "澆水";
        else if (radioButton2.isChecked())
            qrStr += "施肥";
        else if(radioButton3.isChecked())
            qrStr += "噴農藥";
        else
            qrStr += "蔬果";


        Intent intent = new Intent(this,QRMakerActivity.class);
        intent.putExtra("QrStr",qrStr);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode != RESULT_OK)
            return;

        switch (requestCode){
            case REQUEST_VIDEO_CAPTURE:
                Uri videoUri = data.getData();
                Toast.makeText(this,videoUri.toString(),Toast.LENGTH_SHORT).show();
                //mVideoView.setVideoURI(videoUri);
                break;

            case REQUEST_OCR_NAME:
                txtName.setText(data.getExtras().getString(getResources().getString(R.string.tag_ocr_result)));
                break;
            case REQUEST_OCR_PRODUCT:
                txtProductName.setText(data.getExtras().getString(getResources().getString(R.string.tag_ocr_result)));
                break;
            case REQUEST_OCR_PROVIDER:
                txtProvider.setText(data.getExtras().getString(getResources().getString(R.string.tag_ocr_result)));
                break;
            case REQUEST_OCR_SPACE_ORIGIN:
                txtSpaceOrigin.setText(data.getExtras().getString(getResources().getString(R.string.tag_ocr_result)));
                break;

            default:
                if(!getQrCodeScanResult(requestCode,resultCode,data)){
                    super.onActivityResult(requestCode, resultCode, data);
                }
        }

    }

    private boolean getQrCodeScanResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result ==null)
            return false;

        if(result.getContents() == null){
            Toast.makeText(this,"You can't celled the scanning",Toast.LENGTH_SHORT).show();;
        }else {
            //Toast.makeText(this,result.getContents(),Toast.LENGTH_SHORT).show();
            UpdateScanResult(result.getContents());
        }

        return true;
    }

    protected void onStop(){
        updatePreferences();
        super.onStop();
    }

    private void UpdateScanResult(String resultStr){
        String[] items = resultStr.split("@");
        if(items.length < 4)
            return;
        txtName.setText(items[0]);
        txtProvider.setText(items[1]);
        txtSpaceOrigin.setText(items[2]);
        txtProductName.setText(items[3]);
    }

    private void UpdateGPS(double currentLatitude, double currentLongitude){
        DecimalFormat df = new DecimalFormat("#.##");

        //final String msg = String.format("%s, %.2f",df.format(currentLatitude).toString(), currentLongitude);
        final String msg = String.format("%.2f, %.2f",currentLatitude, currentLongitude);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txtGPSPosition.setText(msg);
            }
        });
    }

    private void UpdateCurrenTime(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm");
        final String dateStr = sdf.format(new java.util.Date());
        _dateStr = dateStr;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txtCaptureTime.setText(dateStr);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int item_id = item.getItemId();

        switch (item_id){
            case R.id.Qr_Code_Maker:
                goToMakeQrCodeActivity();
                break;
            case R.id.QR_Code_Scan:
                scanQRCode();
                break;
            case R.id.record_video:
                dispatchTakeVideoIntent();
                break;
            default:
                return false;
        }
        return true;
    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }
}
