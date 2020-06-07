package com.example.testone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class goodcome extends AppCompatActivity {
    private EditText goodcoding;
    private EditText goodstorageNo;
    private EditText goodNo;
    private EditText goodprice;
    private EditText goodnumb;
    private Button buttonok;
    private String infoString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodcome);
        goodcoding=(EditText)findViewById(R.id.button_Goodxuhao) ;
        goodstorageNo = (EditText) findViewById(R.id.button_Goodkuhao);
        goodNo = (EditText) findViewById(R.id.button_GoodNo);
        goodprice = (EditText) findViewById(R.id.button_Goodprice);
        goodnumb = (EditText) findViewById(R.id.button_Goodnumber);
        buttonok = (Button) findViewById(R.id.GoodComeOk);
        buttonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new MyThread()).start();
            }
        });
    }
    public class MyThread implements Runnable {
        @Override
        public void run() {
            infoString = goodcomeget.executeHttpGet(goodcoding.getText().toString(), goodstorageNo.getText().toString(), goodNo.getText().toString(), goodprice.getText().toString(),goodnumb.getText().toString());//获取服务器返回的数据
            //更新UI，使用runOnUiThread()方法
            showResponse(infoString);
        }
    }
    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            //更新UI
            @Override
            public void run() {

            }
        });
    }

}