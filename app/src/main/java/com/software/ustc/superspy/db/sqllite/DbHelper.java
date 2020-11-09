package com.software.ustc.superspy.db.sqllite;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DbHelper extends SQLiteOpenHelper {


    /**
     * 构造方法，一般用于创建数据库
     * @param context  上下文
     * @param name 数据库名称
     * @param factory
     * @param version  版本
     */
    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //数据库第一次被创建时调用，创建表
    @Override
    public void onCreate(SQLiteDatabase db) {

        //初始化表结构
        String sql = "create table person(id integer primary key autoincrement," +
                "name varchar(100) not null," +
                "sex varchar(50)," +
                "age integer," +
                "phone varchar(100))";
        Log.d("数据表创建",sql);

        String sql1 =  "create table runlog(id integer primary key autoincrement," +
                "app_name varchar(100) ," +
                "first_start_time varchar(50)," +
                "last_start_time varchar(50)," +
                "foreground_time varchar(50)," +
                "run_times varchar(50))";

        db.execSQL(sql);//创建数据库表
        db.execSQL(sql1);
    }

    //当数据库更新调用，例如：数据版本更新，增加表，修改表字段
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("数据库更新","no operation");
    }
}
