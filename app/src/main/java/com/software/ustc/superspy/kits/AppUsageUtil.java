package com.software.ustc.superspy.kits;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.software.ustc.superspy.db.sqllite.AppUsageDao;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class AppUsageUtil {
    private final static String TAG = "AppUsageUtil";
    private final static String PACKAGE_NAME_UNKNOWN = "unknown";

    public static void checkUsageStateAccessPermission(Context context) {
        if(!AppUsageUtil.checkAppUsagePermission(context)) {
            AppUsageUtil.requestAppUsagePermission(context);
        }
    }

    public static boolean checkAppUsagePermission(Context context) {
        UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        if(usageStatsManager == null) {
            return false;
        }
        long currentTime = System.currentTimeMillis();
        // try to get app usage state in last 1 min
        List<UsageStats> stats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, currentTime - 60 * 1000, currentTime);
        if (stats.size() == 0) {
            return false;
        }

        return true;
    }

    public static void requestAppUsagePermission(Context context) {
        Intent intent = new Intent(android.provider.Settings.ACTION_USAGE_ACCESS_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.i(TAG,"Start usage access settings activity fail!");
        }
    }

    public static String getTopActivityPackageName(@NonNull Context context) {
        final UsageStatsManager usageStatsManager = (UsageStatsManager)context.getSystemService(Context.USAGE_STATS_SERVICE);
        if(usageStatsManager == null) {
            return PACKAGE_NAME_UNKNOWN;
        }

        String topActivityPackageName = PACKAGE_NAME_UNKNOWN;
        long time = System.currentTimeMillis();
        // 查询最后十秒钟使用应用统计数据
        List<UsageStats> usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000*10, time);
        // 以最后使用时间为标准进行排序
        if(usageStatsList != null) {
            SortedMap<Long,UsageStats> sortedMap = new TreeMap<Long,UsageStats>();
            for (UsageStats usageStats : usageStatsList) {
                sortedMap.put(usageStats.getLastTimeUsed(),usageStats);
            }
            if(sortedMap.size() != 0) {
                topActivityPackageName =  sortedMap.get(sortedMap.lastKey()).getPackageName();
                Log.d(TAG,"Top activity package name = " + topActivityPackageName);
            }
        }

        return topActivityPackageName;
    }

/*
    UsageStats
    UsageStats是在指定时间区间内某个应用使用统计数据的封装类。包含的公开方法及对应的作用如下：

    方法	用途
    getFirstTimeStamp()	获取指定时间区间内应用第一次使用时间戳
    getLastTimeStamp()	获取指定时间区间内应用最后一次使用时间戳
    getLastTimeUsed()	获取应用最后一次使用时间戳
    getPackageName()	获取应用包名
    getTotalTimeInForeground()	获取应用在前台的时间
    https://www.jianshu.com/p/3b6bcf9cec67
*/

    public static void getAppUsageInfo(Context context){
        Calendar beginCal = Calendar.getInstance();
        beginCal.add(Calendar.HOUR_OF_DAY, -1);
        Calendar endCal = Calendar.getInstance();

        long start_time = beginCal.getTimeInMillis();
        long end_time = endCal.getTimeInMillis();

        UsageStatsManager usageStatsManager = (UsageStatsManager)context.getSystemService(Context.USAGE_STATS_SERVICE);
/*
        INTERVAL_DAILY：天存储级别的数据；
        INTERVAL_WEEKLY：星期存储级别的数据；
        INTERVAL_MONTHLY：月存储级别的数据；
        INTERVAL_YEARLY：年存储级别的数据；
        INTERVAL_BEST：根据给定时间范围选取最佳时间间隔类型

        queryUsageStats 返回的数据，它是一个 UsageStats 类型的数据集合，其中有几个关键字段
        mBeginTimeStamp：查询范围的起始时间；
        mEndTimeStamp：查询范围的起结束时间；
        mLastTimeUsed：应用最后一次使用结束时的时间；
        mTotalTimeInForeground：查询范围内应用在前台的累积时长；
        mLaunchCount：查询范围内应用的打开次数。
        https://blog.csdn.net/liuwan1992/article/details/83625520
*/
        List<UsageStats> list = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST,start_time, end_time);

        String run_times ="none";
        AppUsageDao appUsageDao = new AppUsageDao(context);
        appUsageDao.deleteAppInfo("runlog");

        for(UsageStats tt : list){

            try{
                run_times = String.valueOf(tt.getClass().getDeclaredField("mLaunchCount").getInt(tt));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
            String PackageName = tt.getPackageName();
            String first_start_time = df.format(tt.getFirstTimeStamp());
            String last_time = df.format(tt.getLastTimeUsed());

            String Foreground_time = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(tt.getTotalTimeInForeground())) ;

            PackageInfo info = null;
            String app_name = "None";
            PackageManager pm =  context.getPackageManager();
            try {
                info = pm.getPackageInfo(PackageName,PackageManager.GET_ACTIVITIES);
                app_name = info.applicationInfo.loadLabel(pm).toString();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            if(app_name.equals("None"))
                continue;

            AppUsageInfo appUsageinfo = new AppUsageInfo(1,app_name,first_start_time,last_time,Foreground_time,run_times);

            appUsageDao.insertAppInfo(appUsageinfo);
        }

    }


    public static HashMap<String, Integer> getAppUsageTimeSpent( Context context,String packageName, long beginTime, long endTime) {
        UsageEvents.Event currentEvent;
        List<UsageEvents.Event> allEvents = new ArrayList<>();
        HashMap<String, Integer> appUsageMap = new HashMap<>();

        UsageStatsManager usageStatsManager = (UsageStatsManager)context.getSystemService(Context.USAGE_STATS_SERVICE);
        UsageEvents usageEvents = usageStatsManager.queryEvents(beginTime, endTime);

        List<UsageStats> list = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_YEARLY,beginTime, endTime);

        for(UsageStats tt : list){

            try{
                Field field = tt.getClass().getDeclaredField("mLaunchCount");
                String app_name = field.getName();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
            String first_start_time = df.format(tt.getFirstTimeStamp());
            String last_time = df.format(tt.getLastTimeStamp());
            String Foreground_time =  df.format(tt.getTotalTimeInForeground());
            String PackageName = df.format(tt.getPackageName());


        }

        while (usageEvents.hasNextEvent()) {
            currentEvent = new UsageEvents.Event();
            usageEvents.getNextEvent(currentEvent);
            if(currentEvent.getPackageName().equals(packageName) || packageName == null) {
                if (currentEvent.getEventType() == UsageEvents.Event.ACTIVITY_RESUMED
                        || currentEvent.getEventType() == UsageEvents.Event.ACTIVITY_PAUSED) {
                    allEvents.add(currentEvent);
                    String key = currentEvent.getPackageName();
                    if (appUsageMap.get(key) == null)
                        appUsageMap.put(key, 0);
                }
            }
        }


        for (int i = 0; i < allEvents.size() - 1; i++) {
            UsageEvents.Event lastEvent = allEvents.get(allEvents.size() - 1);
            if(lastEvent.getEventType() == UsageEvents.Event.ACTIVITY_RESUMED) {
                int diff = (int)System.currentTimeMillis() - (int)lastEvent.getTimeStamp();
                diff /= 1000;
                Integer prev = appUsageMap.get(lastEvent.getPackageName());
                if(prev == null) prev = 0;
                appUsageMap.put(lastEvent.getPackageName(), prev + diff);
            }
        }
        return appUsageMap;
    }
}
