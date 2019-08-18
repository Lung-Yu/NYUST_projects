package com.developer.lungyu.ncyu_agricultural;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DoWorkActivity extends AppCompatActivity {

    private String task_name;
    private String task_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_work);

        task_name = getResources().getString(R.string.intent_task_name);
        task_id = getResources().getString(R.string.intent_task_id);

        setTitle(task_name);
    }
}
