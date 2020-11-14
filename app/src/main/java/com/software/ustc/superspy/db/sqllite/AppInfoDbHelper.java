package com.software.ustc.superspy.db.sqllite;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class AppInfoDbHelper extends SQLiteOpenHelper {
    public AppInfoDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //数据库第一次被创建时调用，创建表
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql =  "create table appInfoTable( id integer primary key autoincrement," +
                "apk_name varchar(100)," +
                "app_name varchar(50)," +
                "app_version varchar(50)," +
                "app_dir varchar(50)," +
                "app_size INTEGER," +
                "app_icon BLOB)";
        db.execSQL(sql);//创建数据库表
    }

    //当数据库更新调用，例如：数据版本更新，增加表，修改表字段
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("数据库更新","no operation");
    }
}
