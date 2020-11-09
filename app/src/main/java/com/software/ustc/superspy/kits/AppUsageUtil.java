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

    public static void getAppUsageInfo(Context context){
        Calendar beginCal = Calendar.getInstance();
        beginCal.add(Calendar.HOUR_OF_DAY, -1);
        Calendar endCal = Calendar.getInstance();

        long start_time = beginCal.getTimeInMillis();
        long end_time = endCal.getTimeInMillis();

        UsageStatsManager usageStatsManager = (UsageStatsManager)context.getSystemService(Context.USAGE_STATS_SERVICE);
        List<UsageStats> list = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_YEARLY,start_time, end_time);

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
            String last_time = df.format(tt.getLastTimeStamp());

            String Foreground_time = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(tt.getTotalTimeInForeground())) ;

//            String str = "com.android.calculator2";

            PackageInfo info = null;
            String app_name = "test";
            PackageManager pm =  context.getPackageManager();
            try {
                info = pm.getPackageInfo(PackageName,PackageManager.GET_ACTIVITIES);
                app_name = info.applicationInfo.loadLabel(pm).toString();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

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
//            UsageEvents.Event E0 = allEvents.get(i);
//            UsageEvents.Event E1 = allEvents.get(i + 1);

            UsageEvents.Event lastEvent = allEvents.get(allEvents.size() - 1);
            if(lastEvent.getEventType() == UsageEvents.Event.ACTIVITY_RESUMED) {
                int diff = (int)System.currentTimeMillis() - (int)lastEvent.getTimeStamp();
                diff /= 1000;
                Integer prev = appUsageMap.get(lastEvent.getPackageName());
                if(prev == null) prev = 0;
                appUsageMap.put(lastEvent.getPackageName(), prev + diff);
            }

//            if (E0.getEventType() == UsageEvents.Event.ACTIVITY_RESUMED
//                    && E1.getEventType() == UsageEvents.Event.ACTIVITY_PAUSED
//                    && E0.getClassName().equals(E1.getClassName())) {
//                int diff = (int)(E1.getTimeStamp() - E0.getTimeStamp());
//                diff /= 1000;
//                Integer prev = appUsageMap.get(E0.getPackageName());
//                if(prev == null) prev = 0;
//                appUsageMap.put(E0.getPackageName(), prev + diff);
//            }


        }
        return appUsageMap;
    }
}