package com.software.ustc.superspy.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.software.ustc.superspy.activity.LoginActivity;
import com.software.ustc.superspy.db.sqllite.AppInfoDao;
import com.software.ustc.superspy.kits.AppInfo;
import com.software.ustc.superspy.kits.AppUsageUtil;

import java.util.Calendar;
import java.util.List;

public class AppDbPrepareService extends Service {
    private final int DELAY_LENGHT = 10000; // 10s刷新一次数据库
    private boolean stopflag=false;
    private static final String TAG = "AppUsageService";
    public AppDbPrepareService() {
    }

    //创建时调用
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    //服务销毁时调用
    @Override
    public void onDestroy() {
        stopflag=true;
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    //每次服务启动时调用
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStart");
        Log.d(TAG, "start Refresh database!");
        //权限检查
        AppUsageUtil.checkUsageStateAccessPermission(this);

//        pdao = new AppInfoDao(this);
//        pdao.deleteAppInfo("appInfoTable");
//        try {
//            getAppInfos();
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        List<AppInfo> list=pdao.queryAppInfoList();

        Calendar beginCal = Calendar.getInstance();
        beginCal.add(Calendar.HOUR_OF_DAY, -30);
        Calendar endCal = Calendar.getInstance();
        long start_time = beginCal.getTimeInMillis();
        long end_time = endCal.getTimeInMillis();
        AppUsageUtil.getAppUsageInfo(getApplicationContext(),start_time,end_time);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}