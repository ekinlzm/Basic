package com.tinnotech.basic;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MemberCheckActivity extends AppCompatActivity {
    private EditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_check);
        findViewById(R.id.button_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etPhone = (EditText)findViewById(R.id.edit_text_phone);
                Button checkBtn = (Button)findViewById(R.id.button_start);
                checkBtn.setEnabled(false);
                if(!TntUtil.isMobile(etPhone.getText().toString())){
                    Toast.makeText(MemberCheckActivity.this, R.string.input_valid_phone, Toast.LENGTH_SHORT).show();
                }
                else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int ret = TntHttpUtil.MemberCheck(etPhone.getText().toString());
                            memberCheckHandler.sendEmptyMessage(ret);
                        }
                    }).start();
                }
            }
        });
    }

    private final Handler memberCheckHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1) { //已注册

            }else if(msg.what == 0){ //未注册
                finish();
                Intent itReg = new Intent("com.tinnotech.action.REGISTER");
                itReg.putExtra("phone", etPhone.getText().toString());
                startActivity(itReg);
            }else { //查询失败
                Button  checkBtn = (Button)findViewById(R.id.button_start);
                checkBtn.setEnabled(true);
            }
        }
    };
}
