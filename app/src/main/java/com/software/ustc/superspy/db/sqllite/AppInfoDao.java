package com.software.ustc.superspy.db.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.software.ustc.superspy.kits.AppInfo;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AppInfoDao {
    //定义DbHelper帮助类
    private DbHelper helper;

    public AppInfoDao(Context context) {
        //初始化DbHelper帮助类
        helper = new DbHelper(context, "SuperSpy.db", null, 1);
    }

    public List queryAppInfoList() {
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
                String app_version = cursor.getString(cursor.getColumnIndex("app_version"));
                String app_dir = cursor.getString(cursor.getColumnIndex("app_dir"));
                long app_size = cursor.getLong(cursor.getColumnIndex("app_size"));
                byte[] temp=cursor.getBlob(cursor.getColumnIndex("app_icon"));
                Bitmap app_icon= BitmapFactory.decodeByteArray(temp,0,temp.length);
                AppInfo AppInfo = new AppInfo(app_icon,app_name,apk_name,app_version,app_dir,app_size);
                plist.add(AppInfo);
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

    public AppInfo querySignalAppInfo(String apkName) {
        //创建数据库操作对象
        SQLiteDatabase db = null;
        AppInfo AppInfo = null;

        try {

            //创建数据库操作对象
            db = helper.getReadableDatabase();

            Cursor cursor = db.rawQuery("select * from usageInfoTable where app_name = \""+ apkName+ "\"",null );
            while (cursor.moveToNext()) {
                String apk_name = cursor.getString(cursor.getColumnIndex("apk_name"));
                String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                String app_version = cursor.getString(cursor.getColumnIndex("app_version"));
                String app_dir = cursor.getString(cursor.getColumnIndex("app_dir"));
                long app_size = cursor.getLong(cursor.getColumnIndex("app_size"));
                byte[] temp=cursor.getBlob(cursor.getColumnIndex("app_icon"));
                Bitmap app_icon= BitmapFactory.decodeByteArray(temp,0,temp.length);
                AppInfo = new AppInfo(app_icon,app_name,apk_name,app_version,app_dir,app_size);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();//关闭数据库
            }
        }
        return AppInfo;
    }

    //插入数据库
    public void insertAppInfo(AppInfo appInfo) {

        //创建数据库操作对象
        SQLiteDatabase db = null;
        try {
            //创建数据库操作对象
            db = helper.getWritableDatabase();//可写
            //封装数据（Map(key,value)）
            ContentValues values = new ContentValues();
            values.put("apk_name", appInfo.getAppPackageName());
            values.put("app_name", appInfo.getAppName());
            values.put("app_version", appInfo.getAppVersion());
            values.put("app_dir", appInfo.getAppDir());
            values.put("fapp_size", appInfo.getAppSize());

            Bitmap bmp=appInfo.getAppIcon();
            final ByteArrayOutputStream os = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
            values.put("app_icon", os.toByteArray());
            //增加一条个人信息记录
            db.insert("appInfoTable", null, values);
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
    public void deleteAppInfo(String name) {

        //创建数据库操作对象
        SQLiteDatabase db = null;

        db = helper.getWritableDatabase();
        //执行删除操作
        String v_sql = "delete from " + name;
        db.execSQL(v_sql);

    }
}
