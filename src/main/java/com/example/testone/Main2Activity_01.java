package com.example.testone;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
public class Main2Activity_01 extends AppCompatActivity{
    Intent a,b,c,d,e,f;
    Button button_user;
    Button button_come;
    Button button_out;
    Button button_supplier;
    Button button_CangKuSerach;
    Button button_allsupplier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_come=(Button) findViewById(R.id.button_come);
        button_come.setOnClickListener(new Buttonlistener());
        button_user=(Button) findViewById(R.id.button_user);
        button_user.setOnClickListener(new Buttonlistener());
        button_out=(Button) findViewById(R.id.button_out);
        button_out.setOnClickListener(new Buttonlistener());
        button_supplier=(Button) findViewById(R.id.button_supplier);
        button_supplier.setOnClickListener(new Buttonlistener());
        button_CangKuSerach=(Button) findViewById(R.id.button_CangKuSearch);
        button_CangKuSerach.setOnClickListener(new Buttonlistener());
        button_allsupplier=(Button)findViewById(R.id.Allsupplier);
        button_allsupplier.setOnClickListener(new Buttonlistener());
    }
    private class Buttonlistener implements View.OnClickListener{
        public void onClick(View v){
        switch (v.getId()) {
            case R.id.button_user:
                a = new Intent(Main2Activity_01.this, userupdate.class);
                startActivity(a);
                break;
            case R.id.button_come:
                b = new Intent(Main2Activity_01.this, goodcome.class);
                startActivity(b);
                break;
            case R.id.button_out:
                c=new Intent(Main2Activity_01.this,GoodOut.class);
              startActivity(c);
                break;
            case R.id.button_supplier:
                d=new Intent(Main2Activity_01.this, Addsupplier.class);
                startActivity(d);
                break;
            case R.id.button_CangKuSearch:
                e=new Intent(Main2Activity_01.this,Allstorage.class);
                startActivity(e);
                break;
            case R.id.Allsupplier:
                f=new Intent(Main2Activity_01.this,Allsupplier.class);
                startActivity(f);
                break;
            default:
                break;
        }
        }
    }
}