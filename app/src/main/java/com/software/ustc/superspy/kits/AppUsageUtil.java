package com.software.ustc.superspy.kits;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

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

    public static HashMap<String, Integer> getTimeSpent( Context context,String packageName, long beginTime, long endTime) {
        UsageEvents.Event currentEvent;
        List<UsageEvents.Event> allEvents = new ArrayList<>();
        HashMap<String, Integer> appUsageMap = new HashMap<>();

        UsageStatsManager usageStatsManager = (UsageStatsManager)context.getSystemService(Context.USAGE_STATS_SERVICE);
        UsageEvents usageEvents = usageStatsManager.queryEvents(beginTime, endTime);

        List<UsageStats> list = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_YEARLY,beginTime, endTime);

        for(UsageStats tt : list){
//            KLog.d("value:"+tt.getPackageName() + tt.getFirstTimeStamp()+tt.getLastTimeStamp()+tt.getTotalTimeInForeground());
            try{
                Field field = tt.getClass().getDeclaredField("mLaunchCount");
//                KLog.d(field);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        while (usageEvents.hasNextEvent()) {
            currentEvent = new UsageEvents.Event();
            usageEvents.getNextEvent(currentEvent);

//            KLog.d(currentEvent.getPackageName()+"");
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
