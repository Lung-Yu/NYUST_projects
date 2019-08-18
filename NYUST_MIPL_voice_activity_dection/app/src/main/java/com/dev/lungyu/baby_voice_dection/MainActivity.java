package com.dev.lungyu.baby_voice_dection;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.lungyu.lib.StorageUtils;
import com.dev.lungyu.lib.VoiceActivityDection;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private VoiceActivityDection voiceActivityDection = new VoiceActivityDection();

    private Context context = this;
    private List<String> datalist;
    private Spinner spinner;
    private TextView textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        textView = (TextView) findViewById(R.id.sample_text);
        textView.setText(voiceActivityDection.stringFromJNI());

        // build Spinner list ,Selected Cry Voice to Testing
        spinner = (Spinner) findViewById(R.id.spinner);

        makeFileList();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, datalist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                onCryVoiceSeleted(adapterView,view,i,l);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(context,"onNothingSelected",Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void makeFileList(){
        datalist  = new ArrayList<String>();
        datalist.add("cry.wav");
        datalist.add("ccheer.wav");
    }

    private void onCryVoiceSeleted(AdapterView<?> adapterView, View view, int i, long l){
        int res = voiceActivityDection.hasVoice(datalist.get(i));
        Toast.makeText(this,i + " voice activity dection : " + res ,Toast.LENGTH_SHORT).show();
    }
}

