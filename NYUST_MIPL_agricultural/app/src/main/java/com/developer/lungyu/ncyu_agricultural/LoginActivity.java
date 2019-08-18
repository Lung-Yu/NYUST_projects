package com.developer.lungyu.ncyu_agricultural;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.developer.lungyu.ncyu_agricultural.module.SharedPreferencesUntil;
import com.developer.lungyu.ncyu_agricultural.webapi.UserFramers;
import com.developer.lungyu.ncyu_agricultural.webapi.UserLogin;
import com.developer.lungyu.ncyu_agricultural.webapi.UserManures;
import com.developer.lungyu.ncyu_agricultural.webapi.WebApiCallBack;

public class LoginActivity extends AppCompatActivity {

    private Context context = this;

    private EditText editUserName;
    private EditText editUserPassword;
    private UserLogin userLogin;
    private UserFramers framers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUserName = (EditText) findViewById(R.id.editText);
        editUserPassword = (EditText) findViewById(R.id.editText2);
    }

    public void onLoginClick(View v){
        vaildLogin(editUserName.getText().toString(),editUserPassword.getText().toString());
    }

    private void vaildLogin(String account,String password){
        userLogin = new UserLogin(context);
        userLogin.setCallBackFunc(login_call_back);
        userLogin.setAccount(account);
        userLogin.setPassword(password);
        userLogin.execute();
    }

    WebApiCallBack login_call_back = new WebApiCallBack() {
        @Override
        public void onProcessCallBack() {
            if(userLogin.getToken().isEmpty()){
                Toast.makeText(context,"login fail",Toast.LENGTH_SHORT).show();
            }else{
                //AysncInfomaction(userLogin.getToken());
                SharedPreferencesUntil until = new SharedPreferencesUntil(context);
                until.setToken(userLogin.getToken());

                Intent intent = new Intent(context,PlanActivity.class);
                startActivity(intent);
            }
        }
    };

    private void AysncInfomaction(String token){
        framers = new UserFramers(context);
        framers.setCallBackFunc(manures_call_back);
        framers.setToken(token);
        framers.execute();
    }

    WebApiCallBack manures_call_back = new WebApiCallBack() {
        @Override
        public void onProcessCallBack() {
            Log.d("manures",framers.getCode());
            Log.d("manures",framers.getName());
        }
    };
}
