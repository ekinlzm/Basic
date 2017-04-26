package com.tinnotech.basic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LZM on 2017/4/26.
 */

public class DbOpenHelper extends SQLiteOpenHelper {
    private static final int bdVersion = 1;
    public DbOpenHelper(Context context, String dbName) {
        super(context, dbName, null, bdVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
