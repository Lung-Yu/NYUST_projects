package com.developer.lungyu.ncyu_agricultural.webapi;

import android.content.Context;

import com.developer.lungyu.ncyu_agricultural.R;
import com.developer.lungyu.ncyu_agricultural.datamodel.DataModelLand;
import com.developer.lungyu.ncyu_agricultural.datamodel.DataModelPesticides;
import com.developer.lungyu.ncyu_agricultural.datamodel.DatatModelCrop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by lungyu on 11/1/17.
 */

public class UserPesticides extends WebAPIBase{


    private List<DataModelPesticides> items;
    public UserPesticides(Context context){
        super(context);
        this.items = new LinkedList<>();
    }


    @Override
    protected JSONObject getParams() {
        JSONObject jsonObject = new JSONObject();
        return jsonObject;
    }

    @Override
    protected String getUrl() {
        return context.getResources().getString(R.string.web_api_pesticides);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        service_get_connect();
        parser_response();
        return null;
    }

    private void parser_response(){
        try {
            JSONArray response_json = new JSONArray(response_message);
            for(int i=0;i<response_json.length();i++){
                JSONObject jsonObject = response_json.getJSONObject(i);
                DataModelPesticides item = new DataModelPesticides();

                item.setCode(jsonObject.getString("Code"));
                item.setName(jsonObject.getString("Name"));
                item.setType(jsonObject.getString("Type"));
                item.setInterval(jsonObject.getString("Interval"));
                item.setSaftyDays(jsonObject.getString("SaftyDays"));
                item.setUnitQty(jsonObject.getString("UnitQty"));
                item.setDiluteMultiple(jsonObject.getString("DiluteMiultiple"));
                item.setUsage(jsonObject.getString("Usage"));

                this.items.add(item);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<DataModelPesticides> getPesticides(){
        return this.items;
    }
}
