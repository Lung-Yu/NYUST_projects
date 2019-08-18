package com.developer.lungyu.ncyu_agricultural;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

/**
 * Created by lungyu on 2017/5/21.
 */

public class SettingPreferences {

    private static final String PREFS_NAME = "MyPrefsFile";

    private Context mContext;

    private static final String TAG_NAME = "TAG_NAME";
    private static final String TAG_Provider = "TAG_Provider";
    private static final String TAG_SpaceOrigin = "TAG_SpaceOrigin";
    private static final String TAG_GPSPosition = "TAG_GPSPosition";
    private static final String TAG_ProductName = "TAG_ProductName";
    private static final String TAG_CaptureTime = "TAG_CaptureTime";
    private static final String TAG_Watering = "TAG_Watering";
    private static final String TAG_Manure = "TAG_Manure";
    private static final String TAG_SprayPesticide = "TAG_SprayPesticide";
    private static final String TAG_FruitsVegetables = "TAG_FruitsVegetables";

    private static final String TAG_DEFAULT_VALUE = "";

    private String Name;           //名稱
    private String Provider;        //生產者
    private String SpaceOrigin;     //產地
    private String GPSPosition;     //GPS 座標
    private String ProductName;    //產品名稱
    private String CaptureTime;    //拍攝時間
    private String Watering;        //澆水
    private String Manure;         //施肥
    private String SprayPesticide;   //噴農藥
    private String FruitsVegetables; //蔬果

    public SettingPreferences(Context mContext) {
        this.mContext = mContext;
    }


    public void load(){
        SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);

        setName(settings.getString(TAG_NAME, TAG_DEFAULT_VALUE));
        setProvider(settings.getString(TAG_Provider, TAG_DEFAULT_VALUE));
        setSpaceOrigin(settings.getString(TAG_SpaceOrigin, TAG_DEFAULT_VALUE));
        setGPSPosition(settings.getString(TAG_GPSPosition, TAG_DEFAULT_VALUE));
        setProductName(settings.getString(TAG_ProductName, TAG_DEFAULT_VALUE));
        setCaptureTime(settings.getString(TAG_CaptureTime, TAG_DEFAULT_VALUE));
        setWatering(settings.getString(TAG_Watering, TAG_DEFAULT_VALUE));
        setManure(settings.getString(TAG_Manure, TAG_DEFAULT_VALUE));
        setSprayPesticide(settings.getString(TAG_SprayPesticide, TAG_DEFAULT_VALUE));
        setFruitsVegetables(settings.getString(TAG_FruitsVegetables, TAG_DEFAULT_VALUE));

    }

    public void save(){
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString(TAG_NAME, getName());
        editor.putString(TAG_Provider, getProvider());
        editor.putString(TAG_SpaceOrigin, getSpaceOrigin());
        editor.putString(TAG_GPSPosition, getGPSPosition());
        editor.putString(TAG_ProductName, getProductName());
        editor.putString(TAG_CaptureTime, getCaptureTime());
        editor.putString(TAG_Watering, getWatering());
        editor.putString(TAG_Manure, getManure());
        editor.putString(TAG_SprayPesticide, getSprayPesticide());
        editor.putString(TAG_FruitsVegetables, getFruitsVegetables());

        // Commit the edits!
        editor.commit();
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getProvider() {
        return Provider;
    }

    public void setProvider(String provider) {
        Provider = provider;
    }

    public String getSpaceOrigin() {
        return SpaceOrigin;
    }

    public void setSpaceOrigin(String spaceOrigin) {
        SpaceOrigin = spaceOrigin;
    }

    public String getGPSPosition() {
        return GPSPosition;
    }

    public void setGPSPosition(String GPSPosition) {
        this.GPSPosition = GPSPosition;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getCaptureTime() {
        return CaptureTime;
    }

    public void setCaptureTime(String captureTime) {
        CaptureTime = captureTime;
    }

    public String getWatering() {
        return Watering;
    }

    public void setWatering(String watering) {
        Watering = watering;
    }

    public String getManure() {
        return Manure;
    }

    public void setManure(String manure) {
        Manure = manure;
    }

    public String getSprayPesticide() {
        return SprayPesticide;
    }

    public void setSprayPesticide(String sprayPesticide) {
        SprayPesticide = sprayPesticide;
    }

    public String getFruitsVegetables() {
        return FruitsVegetables;
    }

    public void setFruitsVegetables(String fruitsVegetables) {
        FruitsVegetables = fruitsVegetables;
    }
}
