package com.example.testone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class Addsupplier extends AppCompatActivity {
    private EditText suppliername;
    private EditText suppliernumber;
    private EditText supplieraddress;
    private EditText supplierweb;
    private Button SupplierAdd;
    private Button Crear;
    private String infoString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);
        suppliername = (EditText) findViewById(R.id.button_SupplierName);
        suppliernumber = (EditText) findViewById(R.id.button_SupplierNumber);
        supplieraddress = (EditText) findViewById(R.id.button_SuppilerAddress);
        supplierweb = (EditText) findViewById(R.id.button_SupplierWeb);
        Crear=(Button) findViewById(R.id.Clear);
        SupplierAdd=(Button) findViewById(R.id.SuppilerAdd);
        SupplierAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new MyThread()).start();
            }
        });
        Crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                suppliername.setText("");
                suppliernumber.setText("");
                supplierweb.setText("");
                supplieraddress.setText("");
            }
        });
    }

    public class MyThread implements Runnable {
        @Override
        public void run() {
            infoString = supplierGet.executeHttpGet(suppliername.getText().toString(), suppliernumber.getText().toString(), supplieraddress.getText().toString(), supplierweb.getText().toString());//获取服务器返回的数据
            //更新UI，使用runOnUiThread()方法
            showResponse(infoString);
        }
    }
    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            //更新UI
            @Override
            public void run() {
                if(response.equals("ok")){
                    Toast.makeText(Addsupplier.this,"增加成功！",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Addsupplier.this,"增加失败！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}