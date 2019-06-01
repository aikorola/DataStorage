package com.cqw.com.datacunchudemo;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * 测试SQLite数据库存储
 */
public class DBActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "DBActivity";
    private Button btn_db_createDB;
    private Button btn_db_updateDB;
    private Button btn_db_insert;
    private Button btn_db_update;
    private Button btn_db_delete;
    private Button btn_db_query;
    private Button btn_db_testTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
        initView();
    }

    private void initView() {
        btn_db_createDB = (Button) findViewById(R.id.btn_db_createDB);
        btn_db_updateDB = (Button) findViewById(R.id.btn_db_updateDB);
        btn_db_insert = (Button) findViewById(R.id.btn_db_insert);
        btn_db_update = (Button) findViewById(R.id.btn_db_update);
        btn_db_delete = (Button) findViewById(R.id.btn_db_delete);
        btn_db_query = (Button) findViewById(R.id.btn_db_query);
        btn_db_testTransaction = (Button) findViewById(R.id.btn_db_testTransaction);

        btn_db_createDB.setOnClickListener(this);
        btn_db_updateDB.setOnClickListener(this);
        btn_db_insert.setOnClickListener(this);
        btn_db_update.setOnClickListener(this);
        btn_db_delete.setOnClickListener(this);
        btn_db_query.setOnClickListener(this);
        btn_db_testTransaction.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_db_createDB:
                // 创建库
                DBHelper dbHelper = new DBHelper(this, 1);
                // 获取连接
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                Toast.makeText(this, "创建数据库", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_db_updateDB:
                // 更新库
//                DBHelper dbHelper1 = new DBHelper(this, 2);
//                SQLiteDatabase database1 = dbHelper1.getWritableDatabase();
                break;
            case R.id.btn_db_insert:
                // 添加
                insertDB();
                break;
            case R.id.btn_db_update:
                // 修改
                updateDB();
                break;
            case R.id.btn_db_delete:
                // 删除
                deleteDB();
                break;
            case R.id.btn_db_query:
                // 查询
                queryDB();
                break;
            case R.id.btn_db_testTransaction:
                // 测试事务
                testTransaction();
                break;
        }
    }

    /**
     * 测试事务处理
     * <p>
     * 一个功能中对数据库进行的多个操作：要么都成功、要么都失败
     * 事务处理的三步：
     * 一. 开启事务(获取连接后)
     * 二. 设置事务成功(在全部正常执行完后)
     * 三. 结束事务(finally中)
     */
    private void testTransaction() {
        SQLiteDatabase database = null;
        try {
            // 1. 得到连接
            DBHelper dbHelper = new DBHelper(this, 1);
            database = dbHelper.getWritableDatabase();

            // 一. 开启事务(获取连接后)
            database.beginTransaction();

            // 执行第一个update     update person set age = 1000 where _id = 2
            ContentValues values = new ContentValues();
            values.put("name", "Li");
            values.put("age", 1000);
            int updateCount = database.update("person", values, "_id=?", new String[]{"2"});
            Log.e(TAG, "testTransaction: " + updateCount);

            // todo 模拟异常
            boolean flag = true;
            if (flag) {
                throw new RuntimeException("出异常啦！！！");
            }

            // 执行第二个update     update person set age = 1111 where _id = 4
            ContentValues values2 = new ContentValues();
            values2.put("name", "Li");
            values2.put("age", 1111);
            int updateCount2 = database.update("person", values2, "_id=?", new String[]{"4"});
            Log.e(TAG, "testTransaction: " + updateCount2);

            // 二. 设置事务成功(在全部正常执行完后)
            database.setTransactionSuccessful();

        } catch (Exception e) {
            // catch 异常提示
            e.printStackTrace();
            Toast.makeText(DBActivity.this,"出异常啦！！！\t"+e.toString(),Toast.LENGTH_LONG).show();
        } finally {
            if (database != null) {
                // 三. 结束事务(finally中)
                database.endTransaction();
                // 3. 关闭
                database.close();
            }
        }
    }

    private void queryDB() {
        // 1. 得到连接
        DBHelper dbHelper = new DBHelper(this, 1);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        // 2. 执行query     select * from person;
        // Cursor cursor = database.query("person", null, null, null, null, null, null);                                                                     // 无条件默认查询所有数据
        Cursor cursor = database.query("person", null, "_id=?", new String[]{"4"}, null, null, null);      // 根据条件来查询(id)

        // 得到匹配的总记录数
        int count = cursor.getCount();

        // 去除cursor中所有的数据
        while (cursor.moveToNext()) {
            // _id
            int id = cursor.getInt(cursor.getColumnIndex("_id"));           // 方式一：取死数据
            // name
            String name = cursor.getString(cursor.getColumnIndex("name"));
            // age
            int age = cursor.getInt(cursor.getColumnIndex("age"));          // 方式二：动态取数据

            Log.e(TAG, "queryDB: " + id + "\t" + name + "\t" + age);
        }

        // 3. 关闭
        cursor.close();
        database.close();
        // 4. 提示
        Toast.makeText(this, "count=" + count, Toast.LENGTH_SHORT).show();
    }

    private void deleteDB() {
        // 1. 得到连接
        DBHelper dbHelper = new DBHelper(this, 1);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        // 执行delete     delete from person where _id = ?
        int deleteCount = database.delete("person", "_id=?", new String[]{"1"});

        // 3. 关闭
        database.close();
        // 4. 提示
        Toast.makeText(this, "删除成功：id=" + deleteCount, Toast.LENGTH_SHORT).show();
    }

    private void updateDB() {
        // 1. 得到连接
        DBHelper dbHelper = new DBHelper(this, 1);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        // 执行update     update person set name = ?,age = ? where _id = ?
        ContentValues values = new ContentValues();
        values.put("name", "Li");
        values.put("age", 101);
        int updateCount = database.update("person", values, "_id=?", new String[]{"4"});

        // 3. 关闭
        database.close();
        // 4. 提示
        Toast.makeText(this, "更新成功：id=" + updateCount, Toast.LENGTH_SHORT).show();
    }

    private void insertDB() {
        // 1. 得到连接
        DBHelper dbHelper = new DBHelper(this, 1);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        // 2. 执行insert
        ContentValues values = new ContentValues();
        values.put("name", "Chan");
        values.put("age", 20);
        long id = database.insert("person", null, values);
        // 3. 关闭
        database.close();
        // 4. 提示
        Toast.makeText(this, "插入成功：id=" + id, Toast.LENGTH_SHORT).show();
    }
}
