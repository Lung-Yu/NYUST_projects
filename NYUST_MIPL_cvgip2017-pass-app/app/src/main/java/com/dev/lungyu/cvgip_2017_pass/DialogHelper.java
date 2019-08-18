package com.dev.lungyu.cvgip_2017_pass;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

/**
 * Created by lungyu on 8/19/17.
 */

public class DialogHelper {

    private static String TITLE = "CVGIP-2017 住宿查詢結果";

    public static AlertDialog getPassDialog(final Context context, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(TITLE);
        builder.setMessage(message);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context,"確認通關 ！！！",Toast.LENGTH_SHORT).show();
                    }
                });

//        builder.setPositiveButton("取消",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });

        return builder.create();
    }

}
