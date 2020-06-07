package com.example.testone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class updatesupplier extends AppCompatActivity {
    private EditText upsuppid;
    private EditText upsuppname;
    private EditText upsuppphone;
    private EditText upsuppaddress;
    private EditText upsuppweb;
    private Button upok;
    private int id;
    private String name;
    private String addres;
    private String phone;
    private String web;
    private String sid;
    private String baseUrl = " http://10.4.221.66:8080/Storehouse/servlet/Update_Sup?";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatesupplier);
        upsuppaddress=(EditText)findViewById(R.id.UpSupplierAddress);
        upsuppid=(EditText)findViewById(R.id.UpSupplierid);
        upsuppname=(EditText)findViewById(R.id.UpSupplierName);
        upsuppphone=(EditText)findViewById(R.id.UpSupplierNumber);
        upsuppweb=(EditText) findViewById(R.id.UpSupplierWeb);
        upok=(Button)findViewById(R.id.UpSuppiler);
        Intent intent=getIntent();
        id=intent.getIntExtra("id",0);
         sid=String.valueOf(id);
        name=intent.getStringExtra("name");
        addres=intent.getStringExtra("address");
        web=intent.getStringExtra("web");
        phone=intent.getStringExtra("phone");
        upsuppweb.setText(web);
        upsuppphone.setText(phone);
        upsuppname.setText(name);
        upsuppid.setText(sid);
        upsuppaddress.setText(addres);
       System.out.print(addres+phone+name+web);
       upok.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               new Thread(new MyThread()).start();
               name=upsuppname.getText().toString();
               addres=upsuppaddress.getText().toString();
               phone=upsuppphone.getText().toString();
               web=upsuppweb.getText().toString();
               sid=upsuppid.getText().toString();

           }
       });
    }
    public class MyThread implements Runnable{
        @Override
        public void run() {
            networkValidation(sid,name,phone,addres,web);
            //更新UI，使用runOnUiThread()方法
        }
    }
    private void networkValidation(String id,String name,String phone,String address,String website)
    {
        String param ="id="+id+"&name="+name+"&phone="+phone+"&address="+address+"&website="+website;
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
