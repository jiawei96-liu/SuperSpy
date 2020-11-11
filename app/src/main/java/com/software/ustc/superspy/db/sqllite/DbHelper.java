package com.software.ustc.superspy.db.sqllite;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //数据库第一次被创建时调用，创建表
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlusage =  "create table usageInfoTable( id integer primary key autoincrement," +
                "apk_name varchar(100),"+
                "app_name  varchar(50),"+
                "first_timestamp varchar(50)," +
                "last_timestamp varchar(50)," +
                "foreground_time varchar(50)," +
                "last_start_time varchar(50)," +
                "run_times varchar(50))";

        String sqlbasic =  "create table appInfoTable(apk_name varchar(100) primary key," +
                "app_name varchar(50)," +
                "app_version varchar(50)," +
                "app_dir varchar(50)," +
                "app_size INTEGER," +
                "app_icon BLOB)";
        db.execSQL(sqlusage);//创建数据库表
        db.execSQL(sqlbasic);//创建数据库表
    }

    //当数据库更新调用，例如：数据版本更新，增加表，修改表字段
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("数据库更新","no operation");
    }
}
