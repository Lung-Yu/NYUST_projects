package com.developer.lungyu.ncyu_agricultural.webapi;

import android.content.Context;

import com.developer.lungyu.ncyu_agricultural.R;

import org.json.JSONObject;

/**
 * Created by lungyu on 11/1/17.
 */

public class UserTasklogs extends WebAPIBase{
    private String planid;
    private String taskid;
    private String actdate;
    private String qty;
    private String vurl;

    private int response_code;


    public UserTasklogs(Context context){
        super(context);
    }

    public String getPlanid() {
        return planid;
    }

    public void setPlanid(String planid) {
        this.planid = planid;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getActdate() {
        return actdate;
    }

    public void setActdate(String actdate) {
        this.actdate = actdate;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getVurl() {
        return vurl;
    }

    public void setVurl(String vurl) {
        this.vurl = vurl;
    }

    @Override
    protected JSONObject getParams() {
        JSONObject jsonObject = new JSONObject();
        return jsonObject;
    }

    @Override
    protected String getUrl() {
        String url = String.format("%s?%s",
                context.getResources().getString(R.string.web_api_tasklogs),
                getParameters());
        return url;
    }

    private String getParameters(){
        String param = String.format("planid=%s&taskid=%s&actdate=%s&qty=%s&vurl=%s",
                this.planid,
                this.taskid,
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
}
