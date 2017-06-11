package com.itheima.mobilesafe74.engin;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLOutput;

/**
 * Created by zhouling on 2017/6/10.
 */

public class AdressDao {
public static String path="data/data/com.itheima.mobilesafe74/files/address.db";
    public static String tag="12345";
    public static void getAddress(String phone){
        System.out.println("查询");
        phone=phone.substring(0,7);
        SQLiteDatabase db=SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READONLY);

        Cursor cursor=db.query("data1",new String[]{"outkey"},"id = ?",new String[]{phone},null,null,null);




        if(cursor.moveToNext()){
            System.out.println("outkey:"+cursor.getString(0));

            Log.i(tag,"outkey:"+cursor.getString(0));
        }
    }


}
