package com.software.ustc.superspy.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class AppUsageService extends Service {
    public AppUsageService() {
    }

    //创建时调用
    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "AppUsageService Create", Toast.LENGTH_SHORT).show();
    }

    //服务销毁时调用
    @Override
    public void onDestroy() {
        Toast.makeText(this, "AppUsageService Destroy", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    //每次服务启动时调用
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "AppUsageService Start", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
//        appRunTime temp = new appRunTime();
//        temp.getAppInfo(getApplicationContext());
//        KLog.d("end");
//
//
//        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}