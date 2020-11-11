package com.software.ustc.superspy.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.software.ustc.superspy.activity.LoginActivity;
import com.software.ustc.superspy.kits.AppUsageUtil;

public class AppUsageService extends Service {
    private final int DELAY_LENGHT = 10000; // 10s刷新一次数据库
    private boolean stopflag=false;
    private static final String TAG = "AppUsageService";
    public AppUsageService() {
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
//        Log.d(TAG, "start Refresh database!");
//        AppUsageUtil.getAppUsageInfo(getApplicationContext());
//        Thread myThread=new Thread(){//创建子线程
//            @Override
//            public void run() {
//                try{
//                    while(!stopflag)
//                    {
//                        Log.d(TAG, "111");
//                        AppUsageUtil.getAppUsageInfo(getApplicationContext());
//                        sleep(DELAY_LENGHT);
//                    }
//                    Log.d(TAG, "stop Refresh database!");
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        };
//        myThread.start();//启动线程
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}