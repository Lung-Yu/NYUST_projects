package com.developer.lungyu.ncyu_agricultural.module;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lungyu on 11/3/17.
 */

public class SharedPreferencesUntil {

    private static final String TAG_DATESET = "lung_data";
    private static final String TAG_TOKEN = "TAG_TOKEN";
    private final SharedPreferences settings;
    private final Context context;
    public SharedPreferencesUntil(Context context){
        this.context = context;
        this.settings = context.getSharedPreferences(TAG_DATESET,0);
    }

    public String getToken(){
        return this.settings.getString(TAG_TOKEN,"");
    }

    public void setToken(String token){
        settings.edit().putString(TAG_TOKEN, token).commit();
    }
}
