package com.cqw.com.datacunchudemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btn_main_sp;
    private Button btn_main_if;
    private Button btn_main_sd;
    private Button btn_main_db;
    private Button btn_main_net;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_main_sp = (Button) findViewById(R.id.btn_main_sp);
        btn_main_if = (Button) findViewById(R.id.btn_main_if);
        btn_main_sd = (Button) findViewById(R.id.btn_main_sd);
        btn_main_db = (Button) findViewById(R.id.btn_main_db);
        btn_main_net = (Button) findViewById(R.id.btn_main_net);

        btn_main_sp.setOnClickListener(this);
        btn_main_if.setOnClickListener(this);
        btn_main_sd.setOnClickListener(this);
        btn_main_db.setOnClickListener(this);
        btn_main_net.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main_sp:
                startActivity(new Intent(this, SPActivity.class));
                break;
            case R.id.btn_main_if:
                startActivity(new Intent(this, IFActivity.class));
                break;
            case R.id.btn_main_sd:
                startActivity(new Intent(this, SDActivity.class));
                break;
            case R.id.btn_main_db:
                startActivity(new Intent(this, DBActivity.class));
                break;
            case R.id.btn_main_net:
                startActivity(new Intent(this, NETActivity.class));
                break;
        }
    }
}
