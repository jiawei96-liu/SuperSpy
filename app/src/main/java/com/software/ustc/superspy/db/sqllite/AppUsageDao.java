package com.software.ustc.superspy.db.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.software.ustc.superspy.kits.AppUsageInfo;
import com.software.ustc.superspy.kits.AppUsageUtil;

import java.util.ArrayList;
import java.util.List;

public class AppUsageDao {
    //定义DbHelper帮助类
    private DbHelper helper;

    public AppUsageDao(Context context) {
        //初始化DbHelper帮助类
        helper = new DbHelper(context, "SuperSpy.db", null, 1);
    }

    public List queryAppUsageInfoList() {
        //返回值
        List plist = new ArrayList();
        //创建数据库操作对象
        SQLiteDatabase db = null;

        try {

            //创建数据库操作对象
            db = helper.getReadableDatabase();

            Cursor cursor = db.rawQuery("select * from usageInfoTable order by cast(foreground_time as int ) desc",null );
            while (cursor.moveToNext()) {
                String apk_name = cursor.getString(cursor.getColumnIndex("apk_name"));
                String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                String first_timestamp = cursor.getString(cursor.getColumnIndex("first_timestamp"));
                String last_timestamp = cursor.getString(cursor.getColumnIndex("last_timestamp"));
                String foreground_time = cursor.getString(cursor.getColumnIndex("foreground_time"));
                String last_start_time = cursor.getString(cursor.getColumnIndex("last_start_time"));
                String run_times = cursor.getString(cursor.getColumnIndex("run_times"));
                //封装到个人对象中
                AppUsageInfo appUsageInfo = new AppUsageInfo(apk_name,app_name,first_timestamp,last_timestamp,foreground_time,last_start_time,run_times);
                plist.add(appUsageInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();//关闭数据库
            }
        }
        return plist;
    }

    public AppUsageInfo querySignalAppUsageInfo(String apkName) {
        //创建数据库操作对象
        SQLiteDatabase db = null;
        AppUsageInfo appUsageInfo = null;

        try {

            //创建数据库操作对象
            db = helper.getReadableDatabase();

            Cursor cursor = db.rawQuery("select * from usageInfoTable where app_name = \""+ apkName+ "\"",null );
            while (cursor.moveToNext()) {
                String apk_name = cursor.getString(cursor.getColumnIndex("apk_name"));
                String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                String first_timestamp = cursor.getString(cursor.getColumnIndex("first_timestamp"));
                String last_timestamp = cursor.getString(cursor.getColumnIndex("last_timestamp"));
                String foreground_time = cursor.getString(cursor.getColumnIndex("foreground_time"));
                String last_start_time = cursor.getString(cursor.getColumnIndex("last_start_time"));
                String run_times = cursor.getString(cursor.getColumnIndex("run_times"));
                //封装到个人对象中
                appUsageInfo = new AppUsageInfo(apk_name,app_name,first_timestamp,last_timestamp,foreground_time,last_start_time,run_times);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();//关闭数据库
            }
        }
        return appUsageInfo;
    }

    //插入数据库
    public void insertAppInfo(AppUsageInfo appUsageInfo) {

        //创建数据库操作对象
        SQLiteDatabase db = null;
        try {
            //创建数据库操作对象
            db = helper.getWritableDatabase();//可写
            //封装数据（Map(key,value)）
            ContentValues values = new ContentValues();
            values.put("apk_name", appUsageInfo.getApk_name());
            values.put("first_timestamp", appUsageInfo.getFirst_timestamp());
            values.put("last_timestamp", appUsageInfo.getLast_timestamp());
            values.put("foreground_time", appUsageInfo.getForeground_time());
            values.put("last_start_time", appUsageInfo.getLast_start_time());
            values.put("run_times", appUsageInfo.getRun_times());
            //增加一条个人信息记录
            db.insert("usageInfoTable", null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        finally {
//            if(db!=null){
//                db.close();//关闭数据库
//            }
//        }

    }

    //删
    public void deleteAppInfo(String name) {

        //创建数据库操作对象
        SQLiteDatabase db = null;

        db = helper.getWritableDatabase();
        //执行删除操作
        String v_sql = "delete from " + name;
        db.execSQL(v_sql);

    }
}
