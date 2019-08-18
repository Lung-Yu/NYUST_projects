package com.developer.lungyu.ncyu_agricultural.module;

import android.app.ProgressDialog;
import android.content.Context;

import com.developer.lungyu.ncyu_agricultural.R;

/**
 * Created by lungyu on 11/3/17.
 */

public class MyProcessDialog {

    private Context context;
    private ProgressDialog dialog;

    public MyProcessDialog(Context context){
        this.context = context;

    }

    public void show(){
        this.dialog = ProgressDialog.show(context,
                context.getResources().getString(R.string.dialog_process_title),
                context.getResources().getString(R.string.dialog_process_detail)
                ,true);
    }

    public void dismiss(){
        this.dialog.dismiss();
    }

}
