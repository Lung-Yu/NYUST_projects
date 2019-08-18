package com.developer.lungyu.ncyu_agricultural.webapi;

import android.content.Context;

import com.developer.lungyu.ncyu_agricultural.R;
import com.developer.lungyu.ncyu_agricultural.datamodel.DataModelManure;
import com.developer.lungyu.ncyu_agricultural.datamodel.DataModelPesticides;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by lungyu on 11/1/17.
 */

public class UserManures extends  WebAPIBase{

    private List<DataModelManure> items;
    public UserManures(Context context){
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
        return  context.getResources().getString(R.string.web_api_manures);
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
                DataModelManure item = new DataModelManure();

                item.setCode(jsonObject.getString("Code"));
                item.setName(jsonObject.getString("Name"));
                item.setType(jsonObject.getString("Type"));
                item.setInterval(jsonObject.getString("Interval"));
                item.setUnitQty(jsonObject.getString("UnitQty"));
                item.setDiluteMultiple(jsonObject.getString("DiluteMiultiple"));
                item.setUsage(jsonObject.getString("Usage"));

                this.items.add(item);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<DataModelManure> getManures(){
        return this.items;
    }
}
