package com.developer.lungyu.ncyu_agricultural;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.developer.lungyu.ncyu_agricultural.datamodel.DataModelTask;
import com.developer.lungyu.ncyu_agricultural.module.ITaskTypeSeletedListener;
import com.developer.lungyu.ncyu_agricultural.module.SharedPreferencesUntil;
import com.developer.lungyu.ncyu_agricultural.module.WorkAdapter;
import com.developer.lungyu.ncyu_agricultural.module.planAdapter;
import com.developer.lungyu.ncyu_agricultural.webapi.UserPlans;
import com.developer.lungyu.ncyu_agricultural.webapi.UserTasks;
import com.developer.lungyu.ncyu_agricultural.webapi.WebApiCallBack;

import java.util.List;

public class WorksActivity extends AppCompatActivity {

    String planId;
    private Context context = this;
    private ListView listView;
    private SharedPreferencesUntil until;
    private WorkAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_works);

        planId = getIntent().getStringExtra(getResources().getString(R.string.intent_plan_id));
        until = new SharedPreferencesUntil(context);
        listView = (ListView) findViewById(R.id.listview);

        adapter = new WorkAdapter(context);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        adapter.setTaskTypeSelectedListener(new ITaskTypeSeletedListener() {
            @Override
            public void onItemSelected(List<DataModelTask> taskItems) {
                //Toast.makeText(context,taskItems.size(),Toast.LENGTH_SHORT).show();
                //Toast.makeText(context,"Hello",Toast.LENGTH_SHORT).show();
                showDialog(taskItems);
            }
        });

        load_task_types(planId);
    }


    private UserTasks taskModel;
    private void load_task_types(String planId){
        taskModel = new UserTasks(context);
        taskModel.setCallBackFunc(task_call_back);
        taskModel.setPlanId(planId);
        taskModel.setToken(until.getToken());
        taskModel.execute();
    }

    WebApiCallBack task_call_back = new WebApiCallBack() {
        @Override
        public void onProcessCallBack() {
            Log.d("tasks",taskModel.getTaskTypes().size()+"");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.setdataSouce(taskModel.getTaskTypes());
                    adapter.notifyDataSetChanged();
                }
            });
        }
    };


    private void showDialog(final List<DataModelTask> taskItems){
        //final String[] dinner = {"腿庫","雞蛋糕","沙威瑪","澳美客","麵線","麵疙瘩"};
        final String[] items = new String[taskItems.size()];
        for(int i=0;i<items.length;i++)
            items[i] = taskItems.get(i).getTaskName();

        AlertDialog.Builder dialog_list = new AlertDialog.Builder(context);
        dialog_list.setTitle(getResources().getString(R.string.dialog_task_item_title));
        dialog_list.setItems(items, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                //Toast.makeText(context, "you selected" + taskItems.get(which).getTaskName(), Toast.LENGTH_SHORT).show();
                goToWork(taskItems.get(which));
            }
        });
        dialog_list.show();
    }

    private void goToWork(DataModelTask model){
        Intent intent = new Intent(context,DoWorkActivity.class);
        intent.putExtra(getResources().getString(R.string.intent_task_name),model.getTaskName());
        intent.putExtra(getResources().getString(R.string.intent_task_id),model.getTaskId());
        startActivity(intent);
    }
}
