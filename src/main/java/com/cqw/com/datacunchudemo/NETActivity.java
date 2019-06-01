package com.cqw.com.datacunchudemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NETActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "NETActivity";
    private EditText et_net_address;
    private Button btn_net_hucGet;
    private Button btn_net_hucPost;
    private Button btn_net_hcGet;
    private Button btn_net_hcPost;
    private Button btn_net_VolleyGet;
    private Button btn_net_VolleyPost;
    private TextView tv_net_content;

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);
        initView();

        // 1. 创建请求队列对象(一次)
        queue = Volley.newRequestQueue(this);

        et_net_address.setText("http://api.juheapi.com/japi/toh");
    }

    private void initView() {
        et_net_address = (EditText) findViewById(R.id.et_net_address);
        btn_net_hucGet = (Button) findViewById(R.id.btn_net_hucGet);
        btn_net_hucPost = (Button) findViewById(R.id.btn_net_hucPost);
        btn_net_hcGet = (Button) findViewById(R.id.btn_net_hcGet);
        btn_net_hcPost = (Button) findViewById(R.id.btn_net_hcPost);
        btn_net_VolleyGet = (Button) findViewById(R.id.btn_net_VolleyGet);
        btn_net_VolleyPost = (Button) findViewById(R.id.btn_net_VolleyPost);
        tv_net_content = (TextView) findViewById(R.id.tv_net_content);

        btn_net_hucGet.setOnClickListener(this);
        btn_net_hucPost.setOnClickListener(this);
        btn_net_hcGet.setOnClickListener(this);
        btn_net_hcPost.setOnClickListener(this);
        btn_net_VolleyGet.setOnClickListener(this);
        btn_net_VolleyPost.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*
             * 使用HttpUrlConnection提交get请求
             * */

            /**
             1. 显示ProgressDialog
             2. 启动分线程
             3. 在分线程中，发送请求，得到数据
             1). 得到path，并带上参数*********
             2). 创建URL对象
             3). 打开连接，得到HttpUrlConnection
             4). 设置请求方式，连接超时，读取数据超时
             5). 连接服务器
             6). 发请求，得到响应数据
             得到响应码，必须是200才读取
             得到InputStream，并读取成String
             7). 断开连接
             4. 在主线程，显示得到的结果，移除dialog
             */
            case R.id.btn_net_hucGet:
                HttpUrlConnectionGet();

                break;

            /*
             * 使用HttpUrlConnection提交post请求
             * */

            /**
             1. 显示ProgressDialog
             2. 启动分线程
             3. 在分线程中，发送请求，得到数据
             1). 得到path
             2). 创建URL对象
             3). 打开连接，得到HttpUrlConnection
             4). 设置请求方式，连接超时，读取数据超时
             5). 连接服务器
             6). 发请求，得到响应数据
             得到输出流，写请求体*****
             得到响应码，必须是200才读取
             得到InputStream，并读取成String
             7). 断开连接
             4. 在主线程，显示得到的结果，移除dialog
             */
            case R.id.btn_net_hucPost:
                HttpUrlConnectionPost();
                break;

            /*
             * 使用HttpClient提交get请求
             * Android6.0以上版本谷歌移除了对HttpClient===>DefaultHttpClient()相关API的支持
             * */
            case R.id.btn_net_hcGet:
                HttpClientGet();
                break;

            /*
             * 使用HttpClient提交post请求
             * */
            case R.id.btn_net_hcPost:
                HttpClientPost();
                break;

            /*
             * 使用Volley提交get请求
             * */
            /**
             * 1. 创建请求队列对象(一次)
             * 2. 创建请求对象StringRequest
             * 3. 将请求添加到队列中
             * */
            case R.id.btn_net_VolleyGet:
                VolleyGet();
                break;

            /*
             * 使用Volley提交post请求
             * */
            case R.id.btn_net_VolleyPost:
                VolleyPost();
                break;
        }
    }

    private void VolleyPost() {
        final ProgressDialog dialog = ProgressDialog.show(this, null, "正在请求中...");

        // 2. 创建请求对象StringRequest
        String path = et_net_address.getText().toString();
        StringRequest request = new StringRequest(Request.Method.POST, path, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                tv_net_content.setText(s);
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                dialog.dismiss();
            }
        }){
            // 添加请求体需重写此方法，返回map作为请求体
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("key","a34c0f334ed1cc591cd58517ea822109");
                map.put("v","1.0");
                map.put("month","7");
                map.put("day","1");
                return map;
            }
        };

        // 3. 将请求添加到队列中
        queue.add(request);
    }

    private void VolleyGet() {
        final ProgressDialog dialog = ProgressDialog.show(this, null, "正在请求中...");

        // 2. 创建请求对象StringRequest
        String path = et_net_address.getText().toString() + "?key=a34c0f334ed1cc591cd58517ea822109&v=1.0&month=2&day=19";
        StringRequest request = new StringRequest(path, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {  // 在主线程执行
                tv_net_content.setText(s);
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "onErrorResponse: "+volleyError.toString());
                dialog.dismiss();
            }
        });

        // 3. 将请求添加到队列中
        queue.add(request);
    }

    /**
     * Android6.0以上版本谷歌移除了对HttpClientAPI的支持
     */
    private void HttpClientGet() {
        // 1. 显示ProgressDialog
        final ProgressDialog dialog = ProgressDialog.show(this, null, "正在请求中...");

        // 2. 启动分线程
        new Thread() {
            // 3. 在分线程中，发送请求，得到数据
            @Override
            public void run() {
                try {
                    // 1). 得到path，并带上参数*********
                    String path = et_net_address.getText().toString() + "?key=a34c0f334ed1cc591cd58517ea822109&v=1.0&month=10&day=1";

                    // 2). 创建Client对象
                    HttpClient httpClient = new DefaultHttpClient();


                    // 3). 设置超时
                    HttpParams params = httpClient.getParams();
                    HttpConnectionParams.setConnectionTimeout(params, 10000);
                    HttpConnectionParams.setSoTimeout(params, 10000);

                    // 4). 创建请求对象
                    HttpGet request = new HttpGet(path);

                    // 5). 执行请求对象，得到响应对象
                    HttpResponse response = httpClient.execute(request);

                    // 得到并判断状态码
                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == 200) {
                        // 6). 得到响应体文本
                        HttpEntity entity = response.getEntity();
                        final String result = EntityUtils.toString(entity);

                        // 4. 在主线程显示数据，移除dialog
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_net_content.setText(result);
                                dialog.dismiss();
                            }
                        });

                        // 7). 断开连接;
                        httpClient.getConnectionManager().shutdown();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "run: 加载网络地址异常" + e.toString());
                    dialog.dismiss();
                }
            }
        }.start();
    }

    private void HttpClientPost() {
        // 1. 显示ProgressDialog
        final ProgressDialog dialog = ProgressDialog.show(this, null, "正在请求中...");

        // 2. 启动分线程
        new Thread() {
            // 3. 在分线程中，发送请求，得到数据
            @Override
            public void run() {
                try {
                    // 1). 得到path
                    String path = et_net_address.getText().toString();

                    // 2). 创建Client对象
                    HttpClient httpClient = new DefaultHttpClient();


                    // 3). 设置超时
                    HttpParams params = httpClient.getParams();
                    HttpConnectionParams.setConnectionTimeout(params, 10000);
                    HttpConnectionParams.setSoTimeout(params, 10000);

                    // 4). 创建请求对象
                    HttpPost request = new HttpPost(path);
                    List<BasicNameValuePair> parameters = new ArrayList<>();
                    parameters.add(new BasicNameValuePair("key", "a34c0f334ed1cc591cd58517ea822109"));
                    parameters.add(new BasicNameValuePair("v","1.0"));
                    parameters.add(new BasicNameValuePair("month","1"));
                    parameters.add(new BasicNameValuePair("day","23"));
                    HttpEntity entity = new UrlEncodedFormEntity(parameters);
                    request.setEntity(entity);

                    // 5). 执行请求对象，得到响应对象
                    HttpResponse response = httpClient.execute(request);

                    // 得到并判断状态码
                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == 200) {
                        // 6). 得到响应体文本
                        entity = response.getEntity();
                        final String result = EntityUtils.toString(entity);

                        // 4. 在主线程显示数据，移除dialog
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_net_content.setText(result);
                                dialog.dismiss();
                            }
                        });

                        // 7). 断开连接;
                        httpClient.getConnectionManager().shutdown();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "run: 加载网络地址异常" + e.toString());
                    dialog.dismiss();
                }
            }
        }.start();
    }

    private void HttpUrlConnectionPost() {
        // 1. 显示ProgressDialog
        final ProgressDialog dialog = ProgressDialog.show(this, null, "正在请求中...");

        // 2. 启动分线程
        new Thread() {
            @Override
            public void run() {
                // 3. 在分线程中，发送请求，得到数据
                try {
                    // 1). 得到path
                    String path = et_net_address.getText().toString();
                    // 2). 创建URL对象
                    URL url = new URL(path);
                    // 3). 打开连接，得到HttpUrlConnection
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    // 4). 设置请求方式，连接超时，读取数据超时
                    connection.setRequestMethod("POST");
                    connection.setReadTimeout(10000);
                    connection.setConnectTimeout(10000);
                    // 5). 连接服务器
                    connection.connect();
                    // 6). 发请求，得到响应数据
                    // 得到输出流，写请求体*****
                    OutputStream os = connection.getOutputStream();
                    final String data = "key=a34c0f334ed1cc591cd58517ea822109&v=1.0&month=6&day=1";
                    os.write(data.getBytes("utf-8"));
                    // 得到响应码，必须是200才读取
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        // 得到InputStream，并读取成String
                        InputStream is = connection.getInputStream();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int len = -1;
                        while ((len = is.read(buffer)) != -1) {
                            baos.write(buffer, 0, len);
                        }
                        final String result = baos.toString();

                        // 断开连接
                        baos.close();       // 正规写法是要在finally里面关闭
                        is.close();         // 正规写法是要在finally里面关闭

                        // 4. 在主线程，显示得到的结果，移除dialog
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_net_content.setText(result);
                                dialog.dismiss();
                            }
                        });
                    }
                    // 关闭输入流
                    os.close();     // 防止响应码不是200时程序出错      正规写法是要在finally里面关闭

                    // 7). 断开连接;
                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                } finally {

                }
            }
        }.start();
    }

    private void HttpUrlConnectionGet() {
        // 1. 显示ProgressDialog
        final ProgressDialog dialog = ProgressDialog.show(this, null, "正在请求中...");

        // 2. 启动分线程
        new Thread() {
            // 3. 在分线程中，发送请求，得到数据
            @Override
            public void run() {
                try {
                    // 1). 得到path，并带上参数*********
                    String path = et_net_address.getText().toString() + "?key=a34c0f334ed1cc591cd58517ea822109&v=1.0&month=1&day=1";
                    // 2). 创建URL对象
                    URL url = new URL(path);
                    // 3). 打开连接，得到HttpUrlConnection
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    // 4). 设置请求方式，连接超时，读取数据超时
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(10000);
                    // 5). 连接服务器
                    connection.connect();
                    // 6). 发请求，得到响应数据
                    //     得到响应码，必须是200才读取
                    int responseCode = connection.getResponseCode();
                    Log.e(TAG, "run-->responseCode: " + responseCode);
                    if (responseCode == 200) {
                        //     得到InputStream，并读取成String
                        InputStream is = connection.getInputStream();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int len = -1;
                        while ((len = is.read(buffer)) != -1) {
                            baos.write(buffer, 0, len);
                        }
                        final String result = baos.toString();

                        baos.close();
                        is.close();

                        // 4. 在主线程，显示得到的结果，移除dialog
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_net_content.setText(result);
                                dialog.dismiss();
                            }
                        });
                    }
                    // 7). 断开连接;
                    connection.disconnect();

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "run: 加载网络地址异常" + e.toString());
                    dialog.dismiss();
                }
            }
        }.start();
    }

}
