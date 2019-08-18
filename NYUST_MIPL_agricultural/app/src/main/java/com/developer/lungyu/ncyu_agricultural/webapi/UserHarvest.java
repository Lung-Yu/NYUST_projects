package com.developer.lungyu.ncyu_agricultural.webapi;

import android.content.Context;

import com.developer.lungyu.ncyu_agricultural.R;

import org.json.JSONObject;

/**
 * Created by lungyu on 11/1/17.
 */

public class UserHarvest extends WebAPIBase{
    private String planid;
    private String actdate;
    private String qty;
    private String vurl;

    private int response_code;
    public UserHarvest(Context context){
        super(context);
    }

    @Override
    protected JSONObject getParams() {
        JSONObject jsonObject = new JSONObject();
        return jsonObject;
    }

    @Override
    protected String getUrl() {
        String url = String.format("%s?%s",
                context.getResources().getString(R.string.web_api_harvest),
                getParameters());
        return url;
    }
    private String getParameters(){
        String param = String.format("planid=%s&actdate=%s&qty=%s&vurl=%s",
                this.planid,
                this.actdate,
                this.qty,
                this.vurl);
        return param;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            this.response_code = HttpURLConnection.send_post_noresponse(getUrl(),getParameters(),token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public int getStatusCode(){
        return this.response_code;
    }

    public void setPlanid(String planid) {
        this.planid = planid;
    }

    public void setActdate(String actdate) {
        this.actdate = actdate;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public void setVurl(String vurl) {
        this.vurl = vurl;
    }
}
