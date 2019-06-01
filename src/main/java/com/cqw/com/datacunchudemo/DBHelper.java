package com.cqw.com.datacunchudemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 数据库操作的帮助类
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String CREATE_TABLE = "create table person(_id integer primary key autoincrement, name varchar,age int)";
    private String TAG = "DBHelper";

    public DBHelper(Context context, int version) {
        super(context, "cqwTest.db", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "onCreate: 数据库创建了");
        // 建表
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e(TAG, "onCreate: 数据库更新了");
    }
}
