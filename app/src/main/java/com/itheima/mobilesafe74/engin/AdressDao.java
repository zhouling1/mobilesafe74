package com.itheima.mobilesafe74.engin;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.sql.SQLOutput;
import java.util.logging.LogRecord;

/**
 * Created by zhouling on 2017/6/10.
 */

public class AdressDao {
    public static String path = "data/data/com.itheima.mobilesafe74/files/address.db";
    public static String tag = "12345";
    private static String location;
    private static SQLiteDatabase db;

    public static String getAddress(String phone) {
        System.out.println("查询");
        //正则验证"^1[3-8]\d{9}"
        String regularExpression = "^1[3-8]\\d{9}";

        if (!phone.matches(regularExpression)) {
            switch (phone.length()) {
                //各种区分
                case 3:

                    break;
                case 11:
                    String area = phone.substring(1, 3);
                    Cursor indexcursor11 = db.query("data2", new String[]{"location"}, "area = ?", new String[]{area}, null, null, null);
                    if (indexcursor11.moveToNext()) {
                        location = indexcursor11.getString(0);
                    } else {
                        location = "未知号码";
                    }
                    break;
            }
        } else {
            phone = phone.substring(0, 7);

            db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);

            Cursor cursor = db.query("data1", new String[]{"outkey"}, "id = ?", new String[]{phone}, null, null, null);


            if (cursor.moveToNext()) {
                String outkey = cursor.getString(0);
                System.out.println("outkey:" + cursor.getString(0));
                Cursor indexcursor = db.query("data2", new String[]{"location"}, "id = ?", new String[]{outkey}, null, null, null);
                if (indexcursor.moveToNext()) {

                    location = indexcursor.getString(0);
                    Log.i(tag, "location:" + indexcursor.getString(0));
                }
                Log.i(tag, "outkey:" + cursor.getString(0));
            } else {
                location = "未知电话";
            }
        }
        return location;
    }
}
