package com.example.testone;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Mybatabase extends SQLiteOpenHelper {
    public static final String CREATE_user="create table userxinxi("
            + "username integer primary key autoincrement, "
            + "password, "
            + "permission )";
    private Context mContsxt;
    public Mybatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
        mContsxt=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_user);
    }
    @Override
    public void onUpgrade (SQLiteDatabase db,int oldVersion,int newVersion){

    }
}