package com.tinnotech.basic;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.File;

/**
 * Created by LZM on 2017/4/26.
 */

public class DatabaseContext extends ContextWrapper {

    public DatabaseContext(Context base) {
        super(base);
    }

    @Override
    public File getDatabasePath(String name) {
        if(Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState())){
            Log.e("DatabaseContext", "sd卡不存在");
            return null;
        }
        String dbDir = android.os.Environment.getExternalStorageDirectory().toString();
        dbDir += "/lzm/";
        String dbPath = dbDir + name;
        File dirFile = new File(dbDir);
        boolean dirCreated = false;
        boolean fileCreated = false;
        if(!dirFile.exists())
            dirCreated = dirFile.mkdirs();

        File dbFile = new File(dbPath);
        if(!dbFile.exists()){
            try {
                fileCreated = dbFile.createNewFile();
            }catch(Exception e){

            }
        }else{
            fileCreated = true;
        }

        if(fileCreated)
            return dbFile;

        return null;
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
    }
}
