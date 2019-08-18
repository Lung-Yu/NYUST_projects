package com.developer.lungyu.ncyu_agricultural.webapi;

import android.content.Context;

import com.developer.lungyu.ncyu_agricultural.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lungyu on 11/1/17.
 */

public class UserLogin extends WebAPIBase{

    private String account;
    private String password;

    private String videoAccount;
    private String videoPassword;

    public UserLogin(Context context){
        super(context);
        this.token = "";
    }

    @Override
    protected JSONObject getParams(){
        JSONObject jsonParam = new JSONObject();

        try {
            jsonParam.put("user", this.account);
            jsonParam.put("pwd", this.password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonParam;
    }

    @Override
    protected String getUrl() {

        //return "https://algdb.csie.ncyu.edu.tw:8080/api/login?user=admin&pwd=123";
        String url = this.context.getString(R.string.web_api_login)+"?user="+account + "&pwd="+password;
        return url;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        service_connect();
        parser_response();

        return null;
    }

    private void service_connect(){
        ServiceConnector connector = new ServiceConnector(getUrl());
        connector.connect();
        response_message = connector.getResponse();
    }

    private void parser_response(){
        try {
            JSONObject json_response = new JSONObject(response_message);
            this.token = json_response.getString("Token");
            this.videoAccount = json_response.getString("Vuser");
            this.videoPassword = json_response.getString("Vpwd");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setAccount(String account){
        this.account = account;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getAccount(){
        return this.account;
    }

    public String getPassword(){
        return this.password;
    }

}

