package com.tinnotech.basic;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TestActivity extends AppCompatActivity {
    private HandlerThread hd;
    private Looper lp;
    private Handler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Button secButton = (Button)findViewById(R.id.start_second);
        secButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestActivity.this, SecondActivity.class));
            }
        });
        if(Build.VERSION.SDK_INT < 23) {
            DatabaseContext dbContext = new DatabaseContext(this);
            DbOpenHelper dbHelper = new DbOpenHelper(dbContext, "TestActivity.db");
            dbHelper.getWritableDatabase();
        }
        else {
            if((ActivityCompat.checkSelfPermission(TestActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                || (ActivityCompat.checkSelfPermission(TestActivity.this, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS) != PackageManager.PERMISSION_GRANTED))
            {
                ActivityCompat.requestPermissions(TestActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS}, 1);
            }
            else{
                DatabaseContext dbContext = new DatabaseContext(this);
                DbOpenHelper dbHelper = new DbOpenHelper(dbContext, "TestActivity.db");
                dbHelper.getWritableDatabase();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean writeAccepted = false;
        switch(requestCode){
            case 1:
                writeAccepted = (grantResults[0] == PackageManager.PERMISSION_GRANTED);
                break;
            default:
                break;
        }

        if(writeAccepted){
            DatabaseContext dbContext = new DatabaseContext(this);
            DbOpenHelper dbHelper = new DbOpenHelper(dbContext, "TestActivity.db");
            dbHelper.getWritableDatabase();
        }
    }
}
