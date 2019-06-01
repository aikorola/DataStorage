package com.cqw.com.datacunchudemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 测试SharedPreferences存储的界面
 */
public class SPActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_sp_key;
    private EditText et_sp_value;
    private Button btn_sp_save;
    private Button btn_sp_read;

    private SharedPreferences sp;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp);
        initView();

        // 得到sp对象
        sp = getSharedPreferences("cqw",Context.MODE_PRIVATE);

        // 得到editor对象
        edit = sp.edit();
    }

    private void initView() {
        et_sp_key = (EditText) findViewById(R.id.et_sp_key);
        et_sp_value = (EditText) findViewById(R.id.et_sp_value);
        btn_sp_save = (Button) findViewById(R.id.btn_sp_save);
        btn_sp_read = (Button) findViewById(R.id.btn_sp_read);

        btn_sp_save.setOnClickListener(this);
        btn_sp_read.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sp_save:
                // 得到key和value
                String key = et_sp_key.getText().toString();
                String value = et_sp_value.getText().toString();

                // 使用editor保存key和value
                edit.putString(key,value);
                edit.commit();

                // 提示
                Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();

                break;
            case R.id.btn_sp_read:
                // 得到输入的key
                String key1 = et_sp_key.getText().toString();

                // 根据输入的key得到value
                String value1 = sp.getString(key1, null);

                if (value1==null){
                    Toast.makeText(this,"没有找到对应的value",Toast.LENGTH_SHORT).show();
                }

                // 显示
                et_sp_value.setText(value1);

                break;
        }
    }

}
