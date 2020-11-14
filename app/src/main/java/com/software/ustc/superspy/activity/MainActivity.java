package com.software.ustc.superspy.activity;

import android.app.usage.ConfigurationStats;
import android.app.usage.EventStats;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.software.ustc.superspy.R;
import com.software.ustc.superspy.db.sqllite.AppInfoDao;
import com.software.ustc.superspy.kits.ActivityCollector;
import com.software.ustc.superspy.kits.AppInfo;
import com.software.ustc.superspy.kits.AppUsageUtil;
import com.software.ustc.superspy.kits.BaseActivity;
import com.software.ustc.superspy.kits.PicUtil;
import com.software.ustc.superspy.service.AppDbPrepareService;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MainActivity extends BaseActivity {

    private final static String TAG = "MainActivity";
    private AppInfoDao pdao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        getSupportActionBar().show();//隐藏标题栏
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        initDbData();
    }
    void initDbData()
    {
        //权限检查
        AppUsageUtil.checkUsageStateAccessPermission(this);
        Calendar beginCal = Calendar.getInstance();
        beginCal.add(Calendar.HOUR_OF_DAY, -1);
        Calendar endCal = Calendar.getInstance();
        long start_time = beginCal.getTimeInMillis();
        long end_time = endCal.getTimeInMillis();
        //数据库刷新
        AppUsageUtil.getAppUsageInfo(getApplicationContext(),start_time,end_time);

        pdao = new AppInfoDao(this);
        pdao.deleteAppInfo("appInfoTable");
        try {
            getAppInfos();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        AppInfo appInfo=pdao.querySignalAppInfo("SuperSpy");
    }


    public void getAppInfos() throws PackageManager.NameNotFoundException {
        //数据库相关


        List<ApplicationInfo> apps = queryFilterAppInfo();
        for (ApplicationInfo applicationInfo : apps) {
            //packageManager是应用管理者对象
            PackageManager packageManager = getPackageManager();

            //获取应用图标
            Drawable appIconDrawale = applicationInfo.loadIcon(packageManager);
            Bitmap app_icon = PicUtil.DrawableToBitmap(appIconDrawale);
            //获取应用名
            String app_name = applicationInfo.loadLabel(packageManager).toString();
            //获取应用程序的包名
            String app_pkg = applicationInfo.packageName;
            //获取应用程序的版本
            PackageInfo packageInfo =packageManager.getPackageInfo(app_pkg,0);
            String app_version=packageInfo.versionName;
            //获取应用存放数据目录
            String app_dir = applicationInfo.sourceDir;
            //获取应用数据大小
            long app_size = new File(app_dir).length();

            AppInfo appInfo = new AppInfo(app_icon,app_name,app_pkg,app_version,app_dir,app_size);

            pdao.insertAppInfo(appInfo);

        }
    }

    private List<ApplicationInfo> queryFilterAppInfo() {
        PackageManager pm = this.getPackageManager();
        // 查询所有已经安装的应用程序
        List<ApplicationInfo> appInfos = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);// GET_UNINSTALLED_PACKAGES代表已删除，但还有安装目录的
        List<ApplicationInfo> applicationInfos = new ArrayList<>();

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        // 通过getPackageManager()的queryIntentActivities方法遍历,得到所有能打开的app的packageName
        List<ResolveInfo> resolveinfoList = getPackageManager()
                .queryIntentActivities(resolveIntent, 0);
        Set<String> allowPackages = new HashSet();
        for (ResolveInfo resolveInfo : resolveinfoList) {
            allowPackages.add(resolveInfo.activityInfo.packageName);
        }

        for (ApplicationInfo app : appInfos) {
            //只列出有packageName的app
            if (allowPackages.contains(app.packageName)) {
                applicationInfos.add(app);
            }
        }
        return applicationInfos;
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, AppDbPrepareService.class));
        super.onDestroy();
        ActivityCollector.finishAll();
    }
}