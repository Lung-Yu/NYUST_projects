package com.developer.lungyu.ncyu_agricultural.webapi;

import android.content.Context;

import com.developer.lungyu.ncyu_agricultural.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lungyu on 11/1/17.
 */

public class UserFramers extends WebAPIBase{

    private String code ="";
    private String name = "";
    private String addr = "";
    private String tel="";
    private String mobile="";

    private String token="";




    public UserFramers(Context context){
        super(context);
    }

    @Override
    protected JSONObject getParams() {
        JSONObject jsonParam = new JSONObject();
        return jsonParam;
    }

    @Override
    protected String getUrl() {
        return this.context.getString(R.string.web_api_farmes);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        service_connect();
        parser_response();

        return null;
    }

    private void service_connect(){
        ServiceConnector connector = new ServiceConnector(getUrl());
        connector.connectGetByToken(token);
        response_message = connector.getResponse();
    }

    private void parser_response(){
        try {
            JSONObject json_response = new JSONObject(response_message);
            this.code = json_response.getString("Code");
            this.name = json_response.getString("Name");
            this.addr = json_response.getString("Addr");
            this.tel = json_response.getString("Tel");
            this.mobile = json_response.getString("Mobile");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return addr;
    }

    public void setAddress(String addr) {
        this.addr = addr;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
