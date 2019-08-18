package com.developer.lungyu.ncyu_agricultural.module;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.lungyu.ncyu_agricultural.R;
import com.developer.lungyu.ncyu_agricultural.datamodel.DataModelPlan;
import com.developer.lungyu.ncyu_agricultural.datamodel.DataModelTask;
import com.developer.lungyu.ncyu_agricultural.datamodel.DataModelTaskTypes;

import java.util.List;

/**
 * Created by lungyu on 11/6/17.
 */

public class WorkAdapter extends BaseAdapter {

    private List<DataModelTaskTypes> items = null;
    private LayoutInflater layoutInflater;
    private Context context;
    private ITaskTypeSeletedListener listener = null;

    public WorkAdapter(Context context){
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setTaskTypeSelectedListener(ITaskTypeSeletedListener listener){
        this.listener = listener;
    }

    @Override
    public int getCount() {
        if(items == null)
            return 0;
        else
            return items.size();
    }

    @Override
    public DataModelTaskTypes getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        //this.items.get(position).getTaskId();
        return position;
    }

    public void setdataSouce(List<DataModelTaskTypes> dataSouce){
        this.items = dataSouce;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {

        View v = layoutInflater.inflate(R.layout.item_task_type_layout, parent, false);
//
        TextView textView1 = (TextView) v.findViewById(R.id.textView1);
        TextView textView2 = (TextView) v.findViewById(R.id.textView2);
        Button btnMore = (Button) v.findViewById(R.id.btn_more);
        final DataModelTaskTypes datamode = getItem(position);
        textView1.setText(datamode.getTaskTypeId());
        textView2.setText(datamode.getTaskTypeName());

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,datamode.getTaskItems().size(),Toast.LENGTH_SHORT).show();
                Log.d("work_adapter","size " + datamode.getTaskItems().size());
                if(listener!=null)
                    listener.onItemSelected(datamode.getTaskItems());

            }
        });

        return v;
    }

}
