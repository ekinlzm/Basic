package com.tinnotech.basic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    private EditText etName;
    private EditText etPwd;
    private Button btnRegister;
    private String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        btnRegister = (Button)findViewById(R.id.button_register);
        etName = (EditText)findViewById(R.id.edit_text_name);
        etPwd = (EditText)findViewById(R.id.edit_text_pwd);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((etName.getText().length() == 0) || (etPwd.getText().length() == 0)) {
                    Toast.makeText(RegisterActivity.this, "请输入完整的信息", Toast.LENGTH_SHORT).show();
                }else {
                    btnRegister.setEnabled(false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int ret = TntHttpUtil.Register(phone, etName.getText().toString(), etPwd.getText().toString());
                            Message msg = new Message();
                            msg.what = 1;
                            Bundle bundle = new Bundle();
                            bundle.putInt("ret", ret);
                            msg.setData(bundle);
                            registerHandler.sendMessage(msg);
                        }
                    }).start();
                }
            }
        });
    }

    private Handler registerHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1) {  //register
                if(msg.getData().getInt("ret") == 0){
                    saveMemberInfo();
                    //开始登陆
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            handleLoginResult(TntHttpUtil.Login(phone, etPwd.getText().toString()));
                        }
                    }).start();

                }else{
                    btnRegister.setEnabled(true);
                    Toast.makeText(RegisterActivity.this, "失败", Toast.LENGTH_SHORT).show();
                }
            }else if(msg.what == 2){  //login
                if(msg.getData().getInt("ret") == 0) {
                    btnRegister.setEnabled(true);
                    Toast.makeText(RegisterActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                }else{
                    finish();
                }
            }

        }
    };

    private void saveMemberInfo() {
        SharedPreferences sp = getSharedPreferences(TntConstants.TNT_SP_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("phone", phone);
        editor.putString("name", etName.getText().toString());
        editor.putString("password", etPwd.getText().toString());
        editor.commit();
    }

    private void handleLoginResult(JSONObject loginJson){
        int ret = 0;
        try{
            if(loginJson.getInteger("errcode") != 0){
                return;
            }
            JSONObject data = loginJson.getJSONObject("data");
            String token = data.getString("token");
            String openid = data.getString("openid");
            int expire = data.getInteger("expire");
            SharedPreferences sp = getSharedPreferences(TntConstants.TNT_SP_FILE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("token", token);
            editor.putString("openid", openid);
            editor.putInt("expire", expire);
            editor.commit();
            ret = 1;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            Message msg = new Message();
            msg.what = 2;
            Bundle bundle = new Bundle();
            bundle.putInt("ret", ret);
            msg.setData(bundle);
            registerHandler.sendMessage(msg);
        }

    }
}
