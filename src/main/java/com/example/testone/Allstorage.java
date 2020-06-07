package com.example.testone;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Allstorage extends AppCompatActivity {
    private String baseUrl = " http://10.4.221.66:8080/Storehouse/servlet/Storage?";
    public ListView lv;
    private int x;
    String sid;
    public List<stroge> stroges=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allstrogelist);
        init();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {  //点击响应
                final int tempt=position;
                final AlertDialog.Builder builder=new AlertDialog.Builder(Allstorage.this);
                builder.setTitle("提示");
                builder.setMessage("是否修改该Stroge");
                builder.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                a=new Intent(Allsupplier.this,updatesupplier.class);
//                                startActivity(a);
//                                int id= suppliers.get(position).getId();
//                                String name=suppliers.get(position).getName();
//                                String web=suppliers.get(position).getWebsite();
//                                String addres=suppliers.get(position).getAddress();
//                                String phone=suppliers.get(position).getPhone();
//                                Intent intent=new Intent(Allsupplier.this,updatesupplier.class);
//                                intent.putExtra("id",id);
//                                intent.putExtra("name",name);
//                                intent.putExtra("address",addres);
//                                intent.putExtra("phone",phone);
//                                intent.putExtra("web",web);
//                                startActivity(intent);
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
             //   final int temp=position;
               // x=stroges.get(position).getId()
               // sid=String.valueOf(x);
               // new Thread(new MyThread()).start();
                return true;
            }
        });
    }
    private Mybaseadapter list_item;
    private void init() {
        stroges.clear();
        lv = (ListView) findViewById(R.id.lv_storage);
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
                            .url("http://10.4.221.66:8080/Storehouse/servlet/Storage").build();
                    Response response = client.newCall(request).execute();
                    //得到服务器返回的数据后，调用parseJSONWithJSONObject进行解析
                    String responseData = response.body().string();
                    stroges=jsonToList(responseData,stroge.class);
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
            return stroges.size();
        }
        @Override
        public Object getItem(int position) {
            return stroges.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
          ViewHolder viewHolder = new ViewHolder();
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.activity_allstorage, null);
                viewHolder.Scoding = (TextView) convertView.findViewById(R.id.AstrogeCoding);
                viewHolder.SstrogeNo = (TextView) convertView.findViewById(R.id.AstrogeStorageNo);
                viewHolder.SgooodsNo = (TextView) convertView.findViewById(R.id.AstrogeGoodsNo);
                viewHolder.SunitPrice = (TextView) convertView.findViewById(R.id.AstrogeUnitPrice);
                viewHolder.Snumber = (TextView) convertView.findViewById(R.id.AstrogeNumber);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.Scoding.setText("Coding：" + stroges.get(position).getCoding());
            viewHolder.SstrogeNo.setText("StorageNo：" + stroges.get(position).getStorageNo());
            viewHolder.SgooodsNo.setText("GoodsNo：" + stroges.get(position).getGoodsNo());
            viewHolder.SunitPrice.setText("UnitPrice：" + stroges.get(position).getUnitPrice());
            viewHolder.Snumber.setText("Number：" + stroges.get(position).getNumber());
            return convertView;
        }
    }
    final static class ViewHolder {
        TextView Scoding;
        TextView SstrogeNo;
        TextView SgooodsNo;
        TextView SunitPrice;
        TextView Snumber;
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
//    public class MyThread implements Runnable{
//        @Override
//        public void run() {
//            networkValidation(sid);
//            //更新UI，使用runOnUiThread()方法
//        }
//    }
//    private void networkValidation(String id)
//    {
//        String param = "id="+id;
//        Log.d("URL--->", baseUrl+param);
//        //第一步获取okHttpClient对象
//        OkHttpClient client = new OkHttpClient.Builder()
//                .build();
//        //第二步构建Request对象
//        Request request = new Request.Builder()
//                .url(baseUrl+param)
//                .get()
//                .build();
//        //第三步构建Call对象
//        final Call call = client.newCall(request);
//        //第四步:同步get请求
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Response response = call.execute();//必须子线程执行
//                    String result = response.body().string();
//                    Log.d("status", result);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
}
