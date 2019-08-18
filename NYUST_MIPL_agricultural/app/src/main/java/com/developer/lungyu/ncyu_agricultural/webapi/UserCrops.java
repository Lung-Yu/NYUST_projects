package com.developer.lungyu.ncyu_agricultural.webapi;

import android.content.Context;

import com.developer.lungyu.ncyu_agricultural.R;
import com.developer.lungyu.ncyu_agricultural.datamodel.DataModelLand;
import com.developer.lungyu.ncyu_agricultural.datamodel.DatatModelCrop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by lungyu on 11/1/17.
 */

public class UserCrops extends WebAPIBase{

    private List<DatatModelCrop> items ;

    private Context context;
    public UserCrops(Context context)
    {
        super(context);
        items=  new LinkedList<>();
    }
    @Override
    protected JSONObject getParams() {
        JSONObject jsonParam = new JSONObject();
        return jsonParam;
    }

    @Override
    protected String getUrl() {
            return this.context.getString(R.string.web_api_crops);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        service_connect();
        parser_response();

        return null;
    }

    private void parser_response(){
        try {
            JSONArray crops_json = new JSONArray(response_message);
            for(int i=0;i<crops_json.length();i++){
                JSONObject jsonObject = crops_json.getJSONObject(i);
                DatatModelCrop item = new DatatModelCrop();

                item.setCode(jsonObject.getString("Code"));
                item.setName(jsonObject.getString("Name"));
                item.setType(jsonObject.getString("Type"));

                this.items.add(item);
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

    public List<DatatModelCrop> getCrops(){
        return this.items;
    }
}
