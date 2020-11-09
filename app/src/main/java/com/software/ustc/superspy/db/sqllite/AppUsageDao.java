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
        helper = new DbHelper(context, "AppUsage.db", null, 1);
    }

    public List queryAppUsageInfoList() {
        //返回值
        List plist = new ArrayList();
        //创建数据库操作对象
        SQLiteDatabase db = null;

        try {

            //创建数据库操作对象
            db = helper.getReadableDatabase();

            Cursor cursor = db.rawQuery("select * from runlog order by cast(foreground_time as int ) desc",null );
            while (cursor.moveToNext()) {
                //取数据
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                String first_start_time = cursor.getString(cursor.getColumnIndex("first_start_time"));//推荐的方式
                String last_start_time = cursor.getString(cursor.getColumnIndex("last_start_time"));
                String foreground_time = cursor.getString(cursor.getColumnIndex("foreground_time"));
                //封装到个人对象中
                AppUsageInfo appUsageInfo = new AppUsageInfo(id,app_name,first_start_time,last_start_time,foreground_time,null);
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

    public AppUsageInfo querySignalAppUsageInfo(String appName) {
        //创建数据库操作对象
        SQLiteDatabase db = null;
        AppUsageInfo appUsageInfo = null;

        try {

            //创建数据库操作对象
            db = helper.getReadableDatabase();

            Cursor cursor = db.rawQuery("select * from runlog where app_name = \""+ appName+ "\"",null );
            while (cursor.moveToNext()) {
                //取数据
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                String first_start_time = cursor.getString(cursor.getColumnIndex("first_start_time"));//推荐的方式
                String last_start_time = cursor.getString(cursor.getColumnIndex("last_start_time"));
                String foreground_time = cursor.getString(cursor.getColumnIndex("foreground_time"));
                //封装到个人对象中
                appUsageInfo = new AppUsageInfo(id,app_name,first_start_time,last_start_time,foreground_time,null);
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

            values.put("app_name", appUsageInfo.getApp_name());
            values.put("first_start_time", appUsageInfo.getFirst_start_time());
            values.put("last_start_time", appUsageInfo.getLast_start_time());
            values.put("foreground_time", appUsageInfo.getForeground_time());
            values.put("run_times", appUsageInfo.getRun_times());
            //增加一条个人信息记录
            db.insert("runlog", null, values);
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
