package com.developer.lungyu.ncyu_agricultural;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.developer.lungyu.ncyu_agricultural.datamodel.DataModelPlan;
import com.developer.lungyu.ncyu_agricultural.module.SharedPreferencesUntil;
import com.developer.lungyu.ncyu_agricultural.module.WorkAdapter;
import com.developer.lungyu.ncyu_agricultural.module.planAdapter;
import com.developer.lungyu.ncyu_agricultural.webapi.UserPlans;
import com.developer.lungyu.ncyu_agricultural.webapi.WebApiCallBack;

import java.util.List;

public class PlanActivity extends AppCompatActivity {

    //private static final String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.YWRtaW4sMTIz.RnTHB5w_AFrbYraz8lcd56PsMhY3VqbwCuCe67xCCrc";

    private ListView listView;
    private Context context = this;
    private List<DataModelPlan> dataItems;
    private planAdapter adapter;

    private SharedPreferencesUntil until;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        listView = (ListView)findViewById(R.id.listview);

        until = new SharedPreferencesUntil(context);

        adapter = new planAdapter(context);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClickListener);

        load_plan_data();
    }

    private UserPlans planModel;
    private void load_plan_data(){
        planModel = new UserPlans(context);
        planModel.setCallBackFunc(plans_call_back);
        planModel.setToken(until.getToken());
        planModel.execute();
    }

    WebApiCallBack plans_call_back = new WebApiCallBack() {
        @Override
        public void onProcessCallBack() {
            Log.d("plans",planModel.getPlans().size()+"");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.setdataSouce(planModel.getPlans());
                    adapter.notifyDataSetChanged();
                }
            });
        }
    };



    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //go to work-list
            DataModelPlan item =  adapter.getItem(i);
            Log.d("Plan_Selected",item.getId());
            Log.d("Plan_Selected",item.getName());
            Log.d("Plan_Selected",item.getLandCode());

            //Toast.makeText(context,item.getId(),Toast.LENGTH_SHORT).show();
            goToWorkPage(item.getId());
        }
    };

    private void goToWorkPage(String plan_id){
        Intent intent = new Intent(context,WorksActivity.class);
        intent.putExtra(getResources().getString(R.string.intent_plan_id),plan_id);
        startActivity(intent);
    }
}
