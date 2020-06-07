package com.example.testone;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
public  class  MainActivity extends AppCompatActivity {
    private Intent a,b;
    private Spinner spinner;
    private Button login;
    private EditText username;
    private EditText password;
    private String infoString;
    private Mybatabase dbHelper;
    private String userpermission;
    private String baseUrl = " http://10.4.221.66:8080/Storehouse/servlet/Login_servlet?";
    private String user;
    private String pass;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        infoString=null;
        TextView regist=findViewById(R.id.tv_name);
        spinner=(Spinner) findViewById(R.id.permission);
        username=(EditText) findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        dbHelper=new Mybatabase(this,"userBase.db",null,1);
        dbHelper.getWritableDatabase ();
        login = (Button) findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user=username.getText().toString();
                pass=password.getText().toString();
                final String userName = username.getText().toString().trim();
                final String passWord = password.getText().toString().trim();
                if (!checkNetwork()) {
                    Toast toast = Toast.makeText(MainActivity.this, "网络未连接", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (userName.equals("")) {
                    Toast.makeText(MainActivity.this, "账号不能为空", Toast.LENGTH_SHORT).show();

                } else if (passWord.equals("")) {
                    Toast.makeText(MainActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();

                } else {
                    String username = "";
                    String password = "";
                    if (!TextUtils.isEmpty(userName))
                        username = userName;
                    if (!TextUtils.isEmpty(passWord))
                        password = passWord;

                    new Thread(new MyThread()).start();
                }
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                String[] languages = getResources().getStringArray(R.array.permission);
                userpermission=languages[pos];
                System.out.println(userpermission);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b=new Intent(MainActivity.this,Regist.class);
                startActivity(b);
            }
        });
}
    public class MyThread implements Runnable{
        @Override
        public void run() {
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            ContentValues values=new ContentValues();
            values.put("username",user);
            values.put("password",pass);
            values.put("permission",userpermission);
            db.insert("userxinxi",null,values);
            networkValidation(user,pass,userpermission);
        }
    }
    private void networkValidation(String user,String pass,String per)
    {
        String param = "account="+user+"&password="+pass+"&permission="+per;
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
                        Toast.makeText(MainActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                        a = new Intent(MainActivity.this, Main2Activity_01.class);
                        startActivity(a);
                        Looper.loop();
                    }
                    else {
                        errorEvent();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private boolean checkNetwork() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }
    private void errorEvent(){
        Looper.prepare();
        Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
        Looper.loop();
    }
}

