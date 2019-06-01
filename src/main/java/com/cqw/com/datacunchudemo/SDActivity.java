package com.cqw.com.datacunchudemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * 测试手机外部存储
 * 保存方式一：路径：/storage/sdcard/Android/data/packageName/files，-------------应用卸载时会删除（指定路径）
 *
 * 保存方式二：路径：/storage/sdcard/xxx-------------应用卸载时不会删除（自定义路径）
 */
public class SDActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private EditText et_sd_name;
    private EditText et_sd_data;
    private Button btn_sd_save1;
    private Button btn_sd_read1;
    private Button btn_sd_save2;
    private Button btn_sd_read2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sd);
        initView();
    }

    private void initView() {
        et_sd_name = (EditText) findViewById(R.id.et_sd_name);
        et_sd_data = (EditText) findViewById(R.id.et_sd_data);
        btn_sd_save1 = (Button) findViewById(R.id.btn_sd_save1);
        btn_sd_read1 = (Button) findViewById(R.id.btn_sd_read1);
        btn_sd_save2 = (Button) findViewById(R.id.btn_sd_save2);
        btn_sd_read2 = (Button) findViewById(R.id.btn_sd_read2);

        btn_sd_save1.setOnClickListener(this);
        btn_sd_read1.setOnClickListener(this);
        btn_sd_save2.setOnClickListener(this);
        btn_sd_read2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sd_save1:
                // 1. 判断SD卡的状态，如果是挂载的状态才继续，否则提示
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) == true) {
                    // 2. 读取输入的文件名/内容
                    String fileName = et_sd_name.getText().toString();
                    String fileData = et_sd_data.getText().toString();

                    // 3. 得到指定文件的OutputStream
                    // 1). 得到SD卡下的files路径
                    String filesPath = getExternalFilesDir(null).getAbsolutePath();
                    // Log.e("cqw", "onClick: " + filesPath);

                    // 2). 组成完整路径
                    String filePath = filesPath + "/" + fileName;// todo 可以在fileName之后加上文件后缀名，这里默认是没加

                    try {
                        // 3). 创建FileOutputStream
                        FileOutputStream fos = new FileOutputStream(filePath);

                        // 4. 写数据
                        fos.write(fileData.getBytes("utf-8"));
                        fos.close();

                        // 5. 提示
                        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "SD卡为挂载或没有SD卡", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btn_sd_read1:
                // 1. 判断SD卡的状态，如果是挂载的状态才继续，否则提示
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) == true) {
                    // 2. 读取输入的文件名
                    String fileName = et_sd_name.getText().toString();

                    // 3. 得到指定文件的InputStream
                    // 1). 得到SD卡下的files路径
                    String filesPath = getExternalFilesDir(null).getAbsolutePath();
                    // Log.e("cqw", "onClick: filesPath:" + filesPath);

                    // 2). 组成完整路径
                    String filePath = filesPath + "/" + fileName;// todo 可以在fileName之后加上文件后缀名，这里默认是没加

                    try {
                        // 3). 创建FileInputStream
                        FileInputStream fis = new FileInputStream(filePath);

                        // 4. 读取数据，成String
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int len = -1;
                        while ((len = fis.read(buffer)) != -1) {
                            baos.write(buffer, 0, len);
                        }
                        String content = baos.toString();
//                        Log.e("cqw", "onClick: content:"+content);

                        fis.close();

                        // 5. 显示
                        et_sd_data.setText(content + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "SD卡为挂载或没有SD卡", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_sd_save2:
                checkPermission();
                // 1. 判断SD卡的状态，如果是挂载的状态才继续，否则提示
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) == true) {
                    // 2. 读取输入的文件名/内容
                    String fileName = et_sd_name.getText().toString();
                    String fileData = et_sd_data.getText().toString();

                    // 3. 得到指定文件的OutputStream
                        // 1). 得到/storage/sdcard/
                        String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
//                        Log.e("cqw", "onClick: sdPath:"+sdPath);

                        // 2). 得到/storage/sdcard/cqwIODemo（创建文件夹）
                        File file = new File(sdPath + "/cqwIODemo");
//                        Log.e("cqw", "onClick: file:"+file);
                        if (!file.exists()){
                            file.mkdirs();// 创建文件夹
                        }

                        // 3). 得到/storage/sdcard/cqwIODemo/xxx.txt
                        String filePath = sdPath + "/cqwIODemo/"+fileName;// todo 可以在fileName之后加上文件后缀名，这里默认是没加

                    try {
                        // 4). 创建输出流
                        FileOutputStream fos = new FileOutputStream(filePath);

                        // 4. 写数据
                        fos.write(fileData.getBytes("utf-8"));
                        fos.close();

                        // 5. 提示
                        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("cqw", "onClick: Exception:"+e.getMessage());
                    }
                } else {
                    Toast.makeText(this, "SD卡为挂载或没有SD卡", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_sd_read2:
                // 1. 判断SD卡的状态，如果是挂载的状态才继续，否则提示
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) == true) {
                    // 2. 读取输入的文件名
                    String fileName = et_sd_name.getText().toString();

                    // 3. 得到指定文件的InputStream

                        // 1). 得到文件路径
                        String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                        String filePath = sdPath+"/cqwIODemo/"+fileName;
                    try {
                        // 3). 创建FileInputStream
                        FileInputStream fis = new FileInputStream(filePath);

                        // 4. 读取数据，成String
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int len = -1;
                        while ((len = fis.read(buffer)) != -1) {
                            baos.write(buffer, 0, len);
                        }
                        String content = baos.toString();
//                        Log.e("cqw", "onClick: content:"+content);

                        fis.close();

                        // 5. 显示
                        et_sd_data.setText(content + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "SD卡为挂载或没有SD卡", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    /**
     * android6.0以上设置动态权限
     * 读写权限
     */    private void checkPermission() {
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
            }
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);

        } else {
//            Toast.makeText(this, "授权成功！", Toast.LENGTH_SHORT).show();
            Log.e("cqw", "checkPermission: 已经授权！");
        }
    }

}
