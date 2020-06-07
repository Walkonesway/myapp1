package com.example.testone;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
public class userupdate extends AppCompatActivity {
    private EditText username;
    private EditText xingming;
   private EditText password;
   private Button updateok;
   private String XM;
   private EditText updatepermission;
    private String permission;
   private String a;
   private String result;
   private  String b;
   private String c;
   private String account;
   private SQLiteDatabase db;
   private String newpass;
   private EditText NEWpass;
    private Mybatabase dbHelper;
   private Cursor cursor;
   private List<User> userList=new ArrayList<>();
   private String baseUrl= " http://10.4.221.66:8080/Storehouse/servlet/SearchName?";
   private String Url= " http://10.4.221.66:8080/Storehouse/servlet/Update_OP?";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userupdate);
        NEWpass=(EditText) findViewById(R.id.updat_newpass);
        updatepermission=(EditText)findViewById(R.id.update_permission);
        xingming=(EditText) findViewById(R.id.update_xingming);
        dbHelper=new Mybatabase(this,"userBase.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        username=(EditText)findViewById(R.id.update_username);
        password=(EditText)findViewById(R.id.update_password);
        updateok=(Button)findViewById(R.id.button_updatepass);
        cursor=db.query("userxinxi",null,null,null,null,null,null);
        if(cursor.moveToFirst()) {
            a = cursor.getString(cursor.getColumnIndex("username"));
            b = cursor.getString(cursor.getColumnIndex("password"));
            permission = cursor.getString(cursor.getColumnIndex("permission"));
        }
        cursor.close();

        updatepermission.setText(permission);
        updateok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c=password.getText().toString();
                account=username.getText().toString();
                    newpass=NEWpass.getText().toString();
                    permission=updatepermission.getText().toString();
                    XM=xingming.getText().toString();
                    new Thread(new MyThread()).start();
                }
        });
    }
    public class MyThread implements Runnable{
        @Override
        public void run() {
            networkValidation(account,newpass,permission,XM);
            //更新UI，使用runOnUiThread()方法
        }
    }
    private void networkValidation(String acc,String pass,String per,String name)
    {
        String param = "account="+acc+"&password="+pass+"&permission="+per+"&name="+name;
        Log.d("URL---++++", Url+param);
        //第一步获取okHttpClient对象
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        //第二步构建Request对象
        Request request = new Request.Builder()
                .url(Url+param)
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
