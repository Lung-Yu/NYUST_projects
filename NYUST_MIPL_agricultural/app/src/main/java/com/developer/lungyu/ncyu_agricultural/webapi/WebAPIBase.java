package com.developer.lungyu.ncyu_agricultural.webapi;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.developer.lungyu.ncyu_agricultural.module.MyProcessDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by lungyu on 11/1/17.
 */

public abstract class WebAPIBase extends AsyncTask<Void,Void,Void>{
    protected int http_code;
    protected String response_message;
    protected String token;

    protected Context context;

    protected WebAPIBase(Context context){
        this.context = context;
        myProcessDialog = new MyProcessDialog(context);
    }

    private WebApiCallBack callBackFunc;

    protected WebAPIBase(){
        callBackFunc = null;
    }
    protected WebAPIBase(WebApiCallBack callBackFunc){
        this.callBackFunc = callBackFunc;
    }

    protected abstract JSONObject getParams() ;
    protected abstract String  getUrl();


    private MyProcessDialog myProcessDialog;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        myProcessDialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        myProcessDialog.dismiss();

        if(callBackFunc != null){
            callBackFunc.onProcessCallBack();
        }
    }

    @Override
    protected void onCancelled(Void aVoid) {
        super.onCancelled(aVoid);
        myProcessDialog.dismiss();
    }

    protected void service_get_connect(){
        ServiceConnector connector = new ServiceConnector(getUrl());
        connector.connectGetByToken(token);
        response_message = connector.getResponse();
    }

    public void setCallBackFunc(WebApiCallBack func){
        this.callBackFunc = func;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

