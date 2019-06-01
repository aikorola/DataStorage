package com.cqw.com.datacunchudemo;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 测试手机内部文件存储
 */
public class IFActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_if_save;
    private Button btn_if_read;
    private ImageView iv_if_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_if);
        initView();
    }

    private void initView() {
        btn_if_save = (Button) findViewById(R.id.btn_if_save);
        btn_if_read = (Button) findViewById(R.id.btn_if_read);

        btn_if_save.setOnClickListener(this);
        btn_if_read.setOnClickListener(this);
        iv_if_pic = (ImageView) findViewById(R.id.iv_if_pic);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_if_save:
                // 判断文件是否存在
                String path = getFilesDir().getAbsolutePath();
                String filePath = path + "/logo.png";
                boolean isExists = fileIsExists(filePath);// 返回true说明文件存在
                if (!isExists) {

                    // 1. 得到InputStream-->读取assets下的logo.png
                    // 得到AssetManager对象
                    AssetManager manager = getAssets();
                    try {
                        // 读取文件
                        InputStream is = manager.open("logo.png");

                        // 2. 得到OutputStream-->/data/data/packageName/files/logo.png
                        FileOutputStream fos = openFileOutput("logo.png", Context.MODE_PRIVATE);

                        // 3. 边读边写
                        byte[] buffer = new byte[1024];
                        int len = -1;
                        while ((len = is.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
                        }
                        fos.close();
                        is.close();

                        // 4. 提示
                        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // 文件存在的提示
                    Toast.makeText(this, "文件已存在", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btn_if_read:
                // 1. 得到图片路径
                String filePath1 = getFilesDir().getAbsolutePath();
                String imagePath = filePath1 + "/logo.png";

                // 2. 读取加载图片文件得到bitmap对象
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

                // 3. 将其设置到imageView中显示
                iv_if_pic.setImageBitmap(bitmap);
                break;
        }
    }


    // 判断文件是否存在
    private boolean fileIsExists(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
