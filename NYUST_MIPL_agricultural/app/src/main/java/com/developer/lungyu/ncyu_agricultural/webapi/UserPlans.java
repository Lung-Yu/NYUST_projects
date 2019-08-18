package com.developer.lungyu.ncyu_agricultural.webapi;

import android.content.Context;

import com.developer.lungyu.ncyu_agricultural.R;
import com.developer.lungyu.ncyu_agricultural.datamodel.DataModelManure;
import com.developer.lungyu.ncyu_agricultural.datamodel.DataModelPlan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lungyu on 11/1/17.
 */

public class UserPlans extends WebAPIBase{

    private List<DataModelPlan> items;

    public UserPlans(Context context){
        super(context);
        this.items = new ArrayList<>();
    }
    @Override
    protected JSONObject getParams() {
        JSONObject jsonObject = new JSONObject();
        return jsonObject;
    }

    @Override
    protected String getUrl() {
        return  context.getResources().getString(R.string.web_api_plans);
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
                DataModelPlan item = new DataModelPlan();

                item.setId(jsonObject.getString("PlanId"));
                item.setLandCode(jsonObject.getString("LandCode"));
                item.setName(jsonObject.getString("Name"));

                this.items.add(item);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public List<DataModelPlan> getPlans(){
        return this.items;
    }
}
