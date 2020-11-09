package com.software.ustc.superspy;

import android.app.usage.ConfigurationStats;
import android.app.usage.EventStats;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.software.ustc.superspy.kits.ActivityCollector;
import com.software.ustc.superspy.kits.AppUsageUtil;
import com.software.ustc.superspy.kits.BaseActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.List;

public class MainActivity extends BaseActivity {

    private final static String TAG = "MainActivity";

    private Context mContext;
    private UsageStatsManager mUsageStatsManager;
    private long mCurrentTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        //权限检查
        mContext = this;
        AppUsageUtil.checkUsageStateAccessPermission(mContext);
        mUsageStatsManager = (UsageStatsManager) getSystemService(USAGE_STATS_SERVICE);
    }
/*
    @Override
    protected void onResume() {
        super.onResume();
        mCurrentTime = System.currentTimeMillis();
        if(mUsageStatsManager != null) {
            queryUsageStats();
            queryConfigurations();
            queryEventStats();
            AppUsageUtil.getTopActivityPackageName(mContext);
        }

    }

    private void queryUsageStats() {
        List<UsageStats> usageStatsList = mUsageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY, mCurrentTime - 60 * 1000, mCurrentTime);
        for(UsageStats usageStats: usageStatsList) {
            Log.d(TAG,"usageStats PackageName = " + usageStats.getPackageName() + " , FirstTimeStamp = "
                    + usageStats.getFirstTimeStamp() + " , LastTimeStamp = " + usageStats.getLastTimeStamp()
                    + ", LastTimeUsed = " + usageStats.getLastTimeUsed()
                    + " , TotalTimeInForeground = " + usageStats.getTotalTimeInForeground());
        }
    }

    private void queryConfigurations() {
        List<ConfigurationStats> configurationStatsList = mUsageStatsManager.queryConfigurations(
                UsageStatsManager.INTERVAL_DAILY,mCurrentTime - 60 * 1000, mCurrentTime);
        for (ConfigurationStats configurationStats:configurationStatsList) {
            Log.d(TAG,"configurationStats Configuration = " + configurationStats.getConfiguration() + " , ActivationCount = " + configurationStats.getActivationCount()
                    + " , FirstTimeStamp = " + configurationStats.getFirstTimeStamp() + " , LastTimeStamp = " + configurationStats.getLastTimeStamp()
                    + " , LastTimeActive = " + configurationStats.getLastTimeActive() + " , TotalTimeActive = " + configurationStats.getTotalTimeActive());
        }
    }

    private void queryEventStats() {
        if (android.os.Build.VERSION.SDK_INT >= 28) {
            List<EventStats> eventStatsList = mUsageStatsManager.queryEventStats(
                    UsageStatsManager.INTERVAL_DAILY,mCurrentTime - 60 * 1000,mCurrentTime);
            for(EventStats eventStats:eventStatsList) {
                Log.d(TAG,"eventStats EventType" + eventStats.getEventType() + " , Count = " + eventStats.getCount()
                        + " , FirstTime = " + eventStats.getFirstTimeStamp() + " , LastTime = " + eventStats.getLastTimeStamp()
                        + " , LastEventTime = " + eventStats.getLastEventTime() + " , TotalTime = " + eventStats.getTotalTime());
            }
        }
    }*/


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.finishAll();
    }
}