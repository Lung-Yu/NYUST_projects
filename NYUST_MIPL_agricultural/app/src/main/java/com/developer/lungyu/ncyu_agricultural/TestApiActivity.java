package com.developer.lungyu.ncyu_agricultural;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.developer.lungyu.ncyu_agricultural.module.MyProcessDialog;
import com.developer.lungyu.ncyu_agricultural.webapi.UserCrops;
import com.developer.lungyu.ncyu_agricultural.webapi.UserFramers;
import com.developer.lungyu.ncyu_agricultural.webapi.UserHarvest;
import com.developer.lungyu.ncyu_agricultural.webapi.UserLands;
import com.developer.lungyu.ncyu_agricultural.webapi.UserManures;
import com.developer.lungyu.ncyu_agricultural.webapi.UserPesticides;
import com.developer.lungyu.ncyu_agricultural.webapi.UserPlans;
import com.developer.lungyu.ncyu_agricultural.webapi.UserTasklogs;
import com.developer.lungyu.ncyu_agricultural.webapi.UserTasks;
import com.developer.lungyu.ncyu_agricultural.webapi.WebApiCallBack;

public class TestApiActivity extends AppCompatActivity {

    private static final String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.YWRtaW4sMTIz.RnTHB5w_AFrbYraz8lcd56PsMhY3VqbwCuCe67xCCrc";


    private Context context = this;
    private UserFramers framers;
    private UserLands lands;
    private UserCrops crops;
    private UserPesticides pesticides;
    private UserManures manures;
    private UserPlans plans;
    private UserTasks tasks;
    private UserTasklogs tasklogs;
    private UserHarvest harvest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_api);

        test_tasks();
        //process_dialog();

        //MyProcessDialog myProcessDialog = new MyProcessDialog(context);
        //myProcessDialog.show();

    }
    private void process_dialog(){
        Button waitButton = (Button)findViewById(R.id.waitButton);
        waitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                final ProgressDialog dialog = ProgressDialog.show(context,
                        context.getResources().getString(R.string.dialog_process_title),
                        context.getResources().getString(R.string.dialog_process_detail)
                        ,true);
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(3000);
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                        finally{
                            dialog.dismiss();
                        }
                    }
                }).start();
            }
        });
    }

    private void test_harvest(){
        harvest = new UserHarvest(context);
        harvest.setCallBackFunc(harvest_call_back);
        harvest.setToken(token);

        harvest.setPlanid("1");
        harvest.setActdate("1");
        harvest.setQty("12");
        harvest.setVurl("we");

        harvest.execute();
    }

    WebApiCallBack harvest_call_back = new WebApiCallBack() {
        @Override
        public void onProcessCallBack() {
            Log.d("harvest",harvest.getStatusCode()+"");
        }
    };

    private void test_tasklogs(){
        tasklogs = new UserTasklogs(context);
        tasklogs.setCallBackFunc(task_logs_call_back);
        tasklogs.setToken(token);

        tasklogs.setPlanid("1");
        tasklogs.setTaskid("1");
        tasklogs.setActdate("1");
        tasklogs.setQty("12");
        tasklogs.setVurl("we");

        tasklogs.execute();
    }
    WebApiCallBack task_logs_call_back = new WebApiCallBack() {
        @Override
        public void onProcessCallBack() {
            Log.d("tasks_logs",tasklogs.getStatusCode()+"");
        }
    };

    private void test_tasks(){
        tasks = new UserTasks(context);
        tasks.setCallBackFunc(tasks_call_back);
        tasks.setToken(token);
        tasks.setPlanId("hello");
        tasks.execute();
    }

    WebApiCallBack tasks_call_back = new WebApiCallBack() {
        @Override
        public void onProcessCallBack() {
            Log.d("tasks",tasks.getTaskTypes().size()+"");
        }
    };


    private void test_plans(){
        plans = new UserPlans(context);
        plans.setCallBackFunc(plans_call_back);
        plans.setToken(token);
        plans.execute();
    }

    WebApiCallBack plans_call_back = new WebApiCallBack() {
        @Override
        public void onProcessCallBack() {
            Log.d("plans",plans.getPlans().size()+"");
        }
    };


    private void test_manures(){
        manures = new UserManures(context);
        manures.setCallBackFunc(manures_call_back);
        manures.setToken(token);
        manures.execute();
    }
    WebApiCallBack manures_call_back = new WebApiCallBack() {
        @Override
        public void onProcessCallBack() {
            Log.d("manures",manures.getManures().size()+"");
        }
    };

    private void test_pesticides(){
        pesticides = new UserPesticides(context);
        pesticides.setCallBackFunc(pesticides_call_back);
        pesticides.setToken(token);
        pesticides.execute();
    }
    WebApiCallBack pesticides_call_back = new WebApiCallBack() {
        @Override
        public void onProcessCallBack() {
            Log.d("pesticides",pesticides.getPesticides().size()+"");
        }
    };

    private void test_crops(){
        crops = new UserCrops(context);
        crops.setCallBackFunc(crop_call_back);
        crops.setToken(token);
        crops.execute();
    }
    WebApiCallBack crop_call_back = new WebApiCallBack() {
        @Override
        public void onProcessCallBack() {
            Log.d("crop",crops.getCrops().size()+"");
        }
    };


    private void test_lands(){
        lands = new UserLands(context);
        lands.setCallBackFunc(lands_call_back);
        lands.setToken(token);
        lands.execute();
    }
    WebApiCallBack lands_call_back = new WebApiCallBack() {
        @Override
        public void onProcessCallBack() {
            Log.d("lands",lands.getFarmerCode());

        }
    };

    private void test_framers(){
        framers = new UserFramers(context);
        framers.setCallBackFunc(frames_call_back);
        framers.setToken(token);
        framers.execute();
    }

    WebApiCallBack frames_call_back = new WebApiCallBack() {
        @Override
        public void onProcessCallBack() {
            Log.d("framers",framers.getCode());
            Log.d("framers",framers.getName());
        }
    };
}
