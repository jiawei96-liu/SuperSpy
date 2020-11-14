package com.software.ustc.superspy.db.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.software.ustc.superspy.kits.AppTagsMap;
import com.software.ustc.superspy.kits.AppUsageInfo;
import com.software.ustc.superspy.kits.AppUsageUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class AppUsageDao {
    //定义AppUsageDbHelper帮助类
    private AppUsageDbHelper helper;
    public AppUsageDao(Context context) {
        helper = new AppUsageDbHelper(context, "AppUsage.db", null, 1);
    }

//    public AppUsageDao(Context context,String dbName) {
//        helper = new AppUsageDbHelper(context, dbName, null, 1);
//    }

    public List<AppUsageInfo> queryAppUsageList() {
        //返回值
        List<AppUsageInfo> plist = new ArrayList();
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
                String app_tag = cursor.getString(cursor.getColumnIndex("app_tag"));
                //封装到个人对象中
                AppUsageInfo appUsageInfo = new AppUsageInfo(apk_name,app_name,first_timestamp,last_timestamp,foreground_time,last_start_time,run_times,app_tag);
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

    public long queryAppTagUsage(String tag) {
        //创建数据库操作对象
        SQLiteDatabase db = null;
        long sum=0;
        try {
            //创建数据库操作对象
            db = helper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from usageInfoTable where app_tag = \""+ tag+ "\"",null );
            while (cursor.moveToNext()) {
                String foreground_time = cursor.getString(cursor.getColumnIndex("foreground_time"));
                sum += Long.parseLong(foreground_time);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();//关闭数据库
            }
        }
       return sum;
    }

    public AppUsageInfo querySignalAppUsage(String apkName) {
        //创建数据库操作对象
        SQLiteDatabase db = null;
        AppUsageInfo appUsageInfo = null;

        try {

            //创建数据库操作对象
            db = helper.getReadableDatabase();

            Cursor cursor = db.rawQuery("select * from usageInfoTable where apk_name = \""+ apkName+ "\"",null );
            while (cursor.moveToNext()) {
                String apk_name = cursor.getString(cursor.getColumnIndex("apk_name"));
                String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                String first_timestamp = cursor.getString(cursor.getColumnIndex("first_timestamp"));
                String last_timestamp = cursor.getString(cursor.getColumnIndex("last_timestamp"));
                String foreground_time = cursor.getString(cursor.getColumnIndex("foreground_time"));
                String last_start_time = cursor.getString(cursor.getColumnIndex("last_start_time"));
                String run_times = cursor.getString(cursor.getColumnIndex("run_times"));
                String app_tag = cursor.getString(cursor.getColumnIndex("app_tag"));
                appUsageInfo = new AppUsageInfo(apk_name,app_name,first_timestamp,last_timestamp,foreground_time,last_start_time,run_times,app_tag);
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
    public void insertAppUsage(AppUsageInfo appUsageInfo) {

        //创建数据库操作对象
        SQLiteDatabase db = null;
        try {
            //创建数据库操作对象
            db = helper.getWritableDatabase();//可写
            //封装数据（Map(key,value)）
            ContentValues values = new ContentValues();
            values.put("apk_name", appUsageInfo.getApk_name());
            values.put("app_name", appUsageInfo.getApp_name());
            values.put("first_timestamp", appUsageInfo.getFirst_timestamp());
            values.put("last_timestamp", appUsageInfo.getLast_timestamp());
            values.put("foreground_time", appUsageInfo.getForeground_time());
            values.put("last_start_time", appUsageInfo.getLast_start_time());
            values.put("run_times", appUsageInfo.getRun_times());
            values.put("app_tag",appUsageInfo.getApp_tag());
            db.insert("usageInfoTable", null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(db!=null){
                db.close();//关闭数据库
            }
        }
    }

    //删
    public void deleteAppUsage(String name) {

        //创建数据库操作对象
        SQLiteDatabase db = null;

        db = helper.getWritableDatabase();
        //执行删除操作
        String v_sql = "delete from " + name;
        db.execSQL(v_sql);

    }
}
