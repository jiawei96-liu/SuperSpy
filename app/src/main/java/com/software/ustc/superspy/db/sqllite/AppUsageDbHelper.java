package com.software.ustc.superspy.db.sqllite;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class AppUsageDbHelper extends SQLiteOpenHelper {
    public AppUsageDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //数据库第一次被创建时调用，创建表e
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql =  "create table usageInfoTable("+
                "apk_name varchar(100) primary key,"+
                "app_name  varchar(50),"+
                "first_timestamp varchar(50)," +
                "last_timestamp varchar(50)," +
                "foreground_time varchar(50)," +
                "last_start_time varchar(50)," +
                "run_times varchar(50)," +
                "app_tag varchar(50))";
        db.execSQL(sql);//创建数据库
    }

    //当数据库更新调用，例如：数据版本更新，增加表，修改表字段
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("数据库更新","no operation");
    }
}
