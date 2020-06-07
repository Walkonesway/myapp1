package com.example.testone;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Regist extends AppCompatActivity {
    private EditText xingming;
    private EditText username;
    private EditText password;
    private Spinner spinner;
    private String Regist_permission;
    private Button Registok;
    private String infoString;
    private String acc;
    private String pass;
    private String XM;
    private String baseUrl = " http://10.4.221.66:8080/Storehouse/servlet/AddOperator?";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        xingming=(EditText) findViewById(R.id.XingMing);
        username=(EditText) findViewById(R.id.Regist_username);
        password=(EditText) findViewById(R.id.Regist_password);
        spinner=(Spinner) findViewById(R.id.Regist_permission);
        Registok=(Button) findViewById(R.id.Button_Register);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                String[] languages = getResources().getStringArray(R.array.permission);
                Regist_permission=languages[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });//spin 获取值
        Registok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acc=username.getText().toString();
                pass=password.getText().toString();
                XM=xingming.getText().toString();
                new Thread(new MyThread()).start();
            }
        });
    }
    public class MyThread implements Runnable{
        @Override
        public void run() {
           networkValidation(acc,pass,Regist_permission,XM);
            //更新UI，使用runOnUiThread()方法
        }
    }
    private void networkValidation(String acc,String pass,String per,String name)
    {
        String param = "account="+acc+"&password="+pass+"&permission="+per+"&name="+name;
        Log.d("URL--->", baseUrl+param);
        //第一步获取okHttpClient对象
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        //第二步构建Request对象
        Request request = new Request.Builder()
                .url(baseUrl+param)
                .get()
                .build();
        //第三步构建Call对象
        final Call call = client.newCall(request);
        //第四步:同步get请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();//必须子线程执行
                    String result = response.body().string();
                    Log.d("status", result);
                    if ("ok".equals(result.toLowerCase())) {        //确认返回值
                        Looper.prepare();
                        Toast.makeText(Regist.this, "注册成功", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    } else {
                        Toast.makeText(Regist.this, "注册失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
