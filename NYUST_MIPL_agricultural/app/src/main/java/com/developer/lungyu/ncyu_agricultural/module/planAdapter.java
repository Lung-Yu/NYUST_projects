package com.developer.lungyu.ncyu_agricultural.module;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.developer.lungyu.ncyu_agricultural.R;
import com.developer.lungyu.ncyu_agricultural.datamodel.DataModelPlan;

import java.util.List;

/**
 * Created by lungyu on 11/3/17.
 */

public class planAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<DataModelPlan> items = null;

    public planAdapter(Context context){
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public planAdapter(Context context, List<DataModelPlan> datas){
        this(context);
        items = datas;
    }

    public void setdataSouce(List<DataModelPlan> newData){
        this.items = newData;
    }

    @Override
    public int getCount() {
        if(items == null)
            return 0;
        return items.size();
    }

    @Override
    public DataModelPlan getItem(int i) {
        if(items == null)
            return null;
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        if(items == null)
            return 0;
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = layoutInflater.inflate(R.layout.item_plan_layout, viewGroup, false);
//
        TextView textView1 = (TextView) v.findViewById(R.id.textView1);
        TextView textView2 = (TextView) v.findViewById(R.id.textView2);
        TextView textView3 = (TextView) v.findViewById(R.id.textView3);
        DataModelPlan datamode = getItem(i);
        textView1.setText(datamode.getId());
        textView2.setText(datamode.getName());
        textView3.setText(datamode.getLandCode());
//        ImageView imgView = (ImageView) v.findViewById(R.id.imgView);
//        TextView txtView = (TextView) v.findViewById(R.id.txtView);
//
//        imgView.setImageResource(Integer.valueOf(mItemList.get(position).get("Item icon").toString()));
//        txtView.setText(mItemList.get(position).get("Item title").toString());

        return v;
    }
}

