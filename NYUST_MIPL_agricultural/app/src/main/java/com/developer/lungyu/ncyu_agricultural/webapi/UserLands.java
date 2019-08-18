package com.developer.lungyu.ncyu_agricultural.webapi;

import android.content.Context;

import com.developer.lungyu.ncyu_agricultural.R;
import com.developer.lungyu.ncyu_agricultural.datamodel.DataModelLand;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lungyu on 11/1/17.
 */

public class UserLands extends WebAPIBase{

    private String farmerCode;
    private List<DataModelLand> lands;

    public UserLands(Context context){
        super(context);
        lands = new ArrayList<>();
    }

    @Override
    protected JSONObject getParams() {
        JSONObject jsonParam = new JSONObject();
        return jsonParam;
    }

    @Override
    protected String getUrl() {
        return this.context.getString(R.string.web_api_lands);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        service_connect();
        parser_response();
        return null;
    }

    private void parser_response(){
        try {

            JSONObject json_response = new JSONObject(response_message);
            this.farmerCode = json_response.getString("FarmerCode");
            JSONArray lands_json = json_response.getJSONArray("Lands");
            for(int i=0;i<lands_json.length();i++){
                JSONObject jsonObj = lands_json.getJSONObject(i);

                DataModelLand item = new DataModelLand();
                item.setCode(jsonObj.getString("Code"));
                item.setArea(jsonObj.getString("Area"));
                item.setCity(jsonObj.getString("City"));
                item.setSignNo(jsonObj.getString("SignNo"));
                item.setLatitude(jsonObj.getString("Latitude"));
                item.setLongitude(jsonObj.getString("Longitude"));

                this.lands.add(item);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void service_connect(){
        ServiceConnector connector = new ServiceConnector(getUrl());
        connector.connectGetByToken(token);
        response_message = connector.getResponse();
    }

    public void setToken(String token){
        this.token = token;
    }

    public String getFarmerCode() {
        return farmerCode;
    }
}
