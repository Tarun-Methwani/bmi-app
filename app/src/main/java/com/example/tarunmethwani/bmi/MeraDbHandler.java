package com.example.tarunmethwani.bmi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Tarun Methwani on 09-07-2018.
 */

public class MeraDbHandler extends SQLiteOpenHelper {
    Context context;
    SQLiteDatabase db;
    MeraDbHandler(Context context)
    {
        super(context,"bmidb",null,1);
        this.context=context;
        db=this.getWritableDatabase();

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table bmi(value text,"+" condition text,"+" date text)";
        db.execSQL(sql);
    }

    @Override

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }
    public void addBmi(String value,String condition,String date)
    {
        ContentValues cv=new ContentValues();
        cv.put("value",value);
        cv.put("condition",condition);
        cv.put("date",date);
        long rid=db.insert("bmi",null,cv);
        if(rid<0)
            Toast.makeText(context, "Can't INSERT Values due to some issue", Toast.LENGTH_SHORT).show();

    }
    public String viewBmi()
    {
        Cursor cursor=db.query("bmi",null,null,null,null,null,null);
        StringBuffer sb=new StringBuffer();
        cursor.moveToFirst();
        if(cursor.getCount()>0)
        {
            do {
                sb.append(cursor.getString(2)+"\n"+"Your BMI value is "+cursor.getString(0)+"\n"+" Condition is "+ cursor.getString(1)+"\n"+"---------------------------------------------------------------------"+
                        "\n");
            }while (cursor.moveToNext());
        }
        return  sb.toString();
    }
}


