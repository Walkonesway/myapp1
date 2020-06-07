package com.example.testone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
public class Allsupplier extends AppCompatActivity  {
    public ListView lv;
    private Intent a;
    private int x;
    String sid;
    public List<supplier> suppliers=new ArrayList<>();
    private Context context;
    private String baseUrl = " http://10.4.221.66:8080/Storehouse/servlet/DeleteSupplier?";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supplier_list);
        init();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {  //点击响应
                final int tempt=position;
                final AlertDialog.Builder builder=new AlertDialog.Builder(Allsupplier.this);
                builder.setTitle("提示");
                builder.setMessage("是否修改该供应商");
                builder.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                a=new Intent(Allsupplier.this,updatesupplier.class);
//                                startActivity(a);
                               int id= suppliers.get(position).getId();
                               String name=suppliers.get(position).getName();
                               String web=suppliers.get(position).getWebsite();
                               String addres=suppliers.get(position).getAddress();
                               String phone=suppliers.get(position).getPhone();
                               Intent intent=new Intent(Allsupplier.this,updatesupplier.class);
                                intent.putExtra("id",id);
                                intent.putExtra("name",name);
                                intent.putExtra("address",addres);
                                intent.putExtra("phone",phone);
                                intent.putExtra("web",web);
                               startActivity(intent);
                            }
                        });
                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {   //长按响应
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                final int temp=position;
                x=suppliers.get(position).getId();
                sid=String.valueOf(x);
                new Thread(new MyThread()).start();
                return true;
            }
        });
    }
    private Mybaseadapter list_item;
    private void init() {
        suppliers.clear();
        lv = (ListView) findViewById(R.id.lv_contact);
        list_item = new Mybaseadapter();
        lv.setAdapter(list_item);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    //服务端访问地址
                    Request request = new Request
                            .Builder()
                            .url("http://10.4.221.66:8080/Storehouse/servlet/Supplier").build();
                    Response response = client.newCall(request).execute();
                    //得到服务器返回的数据后，调用parseJSONWithJSONObject进行解析
                    String responseData = response.body().string();
                  suppliers=jsonToList(responseData,supplier.class);
                  handler.sendEmptyMessageDelayed(1, 100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    list_item.notifyDataSetChanged();
                    break;
            }
        }
    };
    //listview适配器
    public class Mybaseadapter extends BaseAdapter {
        @Override
        public int getCount() {
            return suppliers.size();
        }
        @Override
        public Object getItem(int position) {
            return suppliers.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = new ViewHolder();
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.activity_allsupplier, null);
                viewHolder.Sid = (TextView) convertView.findViewById(R.id.AsupplierID);
                viewHolder.Sname = (TextView) convertView.findViewById(R.id.AsupplierName);
                viewHolder.Saddress = (TextView) convertView.findViewById(R.id.AsupplierAddress);
                viewHolder.Sweb = (TextView) convertView.findViewById(R.id.AsuppllierWeb);
                viewHolder.Sphone = (TextView) convertView.findViewById(R.id.AsupplierPhone);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.Sid.setText("id：" + suppliers.get(position).getId());
            viewHolder.Sname.setText("供应商名字：" + suppliers.get(position).getName());
            viewHolder.Saddress.setText("地址：" + suppliers.get(position).getAddress());
            viewHolder.Sweb.setText("网址：" + suppliers.get(position).getWebsite());
            viewHolder.Sphone.setText("电话：" + suppliers.get(position).getPhone());
            return convertView;
        }
    }
    final static class ViewHolder {
        TextView Sid;
        TextView Sname;
        TextView Saddress;
        TextView Sweb;
        TextView Sphone;
    }
    private static <T> ArrayList<T> jsonToList(String json, Class<T> classOfT) {
        Type type = new TypeToken<ArrayList<JsonObject>>(){}.getType();
        ArrayList<JsonObject> jsonObjs = new Gson().fromJson(json, type);
        ArrayList<T> listOfT = new ArrayList<>();
        for (JsonObject jsonObj : jsonObjs) {
            listOfT.add(new Gson().fromJson(jsonObj, classOfT));
        }
        return listOfT;
    }
    public class MyThread implements Runnable{
        @Override
        public void run() {
            networkValidation(sid);
            //更新UI，使用runOnUiThread()方法
        }
    }
    private void networkValidation(String id)
    {
        String param = "id="+id;
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