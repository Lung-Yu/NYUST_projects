package com.dev.lungyu.cvgip_2017_pass;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DialogTitle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOError;
import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Context context = this;
    private LodgingDAO itemDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemDAO = new LodgingDAO(context);
    }

    public void onSync(){
        if(isConnected()){
            renewDB();
            SyncDB();
        }else {
            Log.d("NetworkConnection", "No network connection available.");
            Toast.makeText(context,"請確認網路連線正常",Toast.LENGTH_SHORT).show();
        }
    }

    private void renewDB(){
        SQLiteDatabase db = DBHelper.getDatabase(this);
        DBHelper.renew(db);
    }

    private void SyncDB(){
        final LodgingDAO DAO = new LodgingDAO(context);

        new Thread(new Runnable() {
            @Override
            public void run() {

                Looper.prepare();

                final OkHttpClient client = new OkHttpClient();
                String search_url = "http://cvgip2017.yuntech.edu.tw/admin/service.aspx?cvgip=all";
                Request request = new Request.Builder().url(search_url).build();

                try{
                    Response response = client.newCall(request).execute();
                    String res = response.body().string();

                    String[] lines = res.split("\n");
                    int count = 0;
                    for(String item : lines){
                        String[] col = item.split(",");
                        DAO.insert(new LodgingOAI(col[1],col[2],col[3]));
                        count++;
                    }


                    final String toast_message = "同步"+ count +"筆";
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context,toast_message ,Toast.LENGTH_SHORT).show();
                        }
                    });

                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void onClickScanQR(View v){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result !=null){
            if(result.getContents() == null){
                Toast.makeText(this,"You can't celled the scanning",Toast.LENGTH_SHORT).show();;
            }else {
                takeContentFromQRcode(result.getContents());
                //Toast.makeText(this,result.getContents(),Toast.LENGTH_SHORT).show();
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void takeContentFromQRcode(String qr_content){
        if(!qr_content.startsWith("{CVGIP}-")){
            DialogHelper.getPassDialog(this,"非本大會提供之 QR Code").show();
            return;
        }

        String id = qr_content.replace("{CVGIP}-","").trim();

        if (isConnected()) {
            Log.d("NetworkConnection", "Network Connected.");
            checkInByNetwork(id);
        }else{
            Log.d("NetworkConnection", "No network connection available.");
            checkInByLocal(id);
        }
    }

    private void checkInByNetwork(final String id){
        new Thread(new Runnable() {
            @Override
            public void run() {

                Looper.prepare();

                final OkHttpClient client = new OkHttpClient();
                String search_url = "http://cvgip2017.yuntech.edu.tw/admin/service.aspx?cvgip=" + id;
                Request request = new Request.Builder().url(search_url).build();

                try{
                    Response response = client.newCall(request).execute();
                    String res = response.body().string();

                    String[] contents = res.split(",");
                    if(contents.length < 3)
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogHelper.getPassDialog(context,"查無此人 " + id).show();
                            }
                        });
                    else {
                        final String message = "住宿登記名稱 ： " + contents[0] + "\n"
                                + "房型 ： " + contents[1] + "\n"
                                + "房間人數 ： " + contents[2];
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogHelper.getPassDialog(context,message).show();
                            }
                        });

                    }

                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void checkInByLocal(String id){

        LodgingOAI item = itemDAO.get(Long.parseLong(id));

        final String message = "住宿登記名稱 ： " + item.getName() + "\n"
                + "房型 ： " + item.getRoomType() + "\n"
                + "房間人數 ： " + item.getPeopleSize();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogHelper.getPassDialog(context,message).show();
            }
        });
    }

    private boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.menu_sync:
                onSync();
                break;
        }

        return true;
    }
}
