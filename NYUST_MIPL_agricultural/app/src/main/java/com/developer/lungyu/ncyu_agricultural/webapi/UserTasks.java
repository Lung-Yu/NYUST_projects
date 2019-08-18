package com.developer.lungyu.ncyu_agricultural.webapi;

import android.content.Context;
import android.util.Log;

import com.developer.lungyu.ncyu_agricultural.R;
import com.developer.lungyu.ncyu_agricultural.datamodel.DataModelPlan;
import com.developer.lungyu.ncyu_agricultural.datamodel.DataModelTask;
import com.developer.lungyu.ncyu_agricultural.datamodel.DataModelTaskTypes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lungyu on 11/1/17.
 */

public class UserTasks extends WebAPIBase{
    private String planId;
    private List<DataModelTaskTypes> items;

    public UserTasks(Context context){
        super(context);
        this.items = new ArrayList<>();
    }
    public void setPlanId(String planId){
        this.planId = planId;
    }
    public String getPlanId(){
        return this.planId;
    }
    public List<DataModelTaskTypes> getTaskTypes(){
        return this.items;
    }

    @Override
    protected JSONObject getParams() {
        JSONObject jsonObject= new JSONObject();
        return jsonObject;
    }

    @Override
    protected String getUrl() {
        String url = String.format("%s?planid=%s",
                context.getResources().getString(R.string.web_api_tasks),
                this.planId);
        return url;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        service_get_connect();
        parser_response();
        return null;
    }

    private void parser_response(){
        try {
            Log.d("User_Task",response_message);

            JSONObject response_json = new JSONObject(response_message);
            this.planId = response_json.getString("PlanIdId");

            JSONArray task_types_json_arrays = response_json.getJSONArray("TaskTypes");
            for(int i=0;i<task_types_json_arrays.length();i++){
                DataModelTaskTypes taskTypes = new DataModelTaskTypes();
                JSONObject task_types_obj = task_types_json_arrays.getJSONObject(i);

                taskTypes.setTaskTypeId(task_types_obj.getString("TaskTypeId"));
                taskTypes.setTaskTypeName(task_types_obj.getString("TaskTypeName"));

                List<DataModelTask> task_items = new ArrayList<>();
                JSONArray task_items_json_arrays = task_types_obj.getJSONArray("TaskItems");
                for(int j=0;j<task_items_json_arrays.length();j++){
                    DataModelTask modelTask = new DataModelTask();
                    JSONObject task_item_obj = task_items_json_arrays.getJSONObject(j);
                    modelTask.setTaskId(task_item_obj.getString("TaskId"));
                    modelTask.setTaskName(task_item_obj.getString("TaskName"));
                    task_items.add(modelTask);
                }
                taskTypes.setTaskItems(task_items);

                this.items.add(taskTypes);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
