package com.software.ustc.superspy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.software.ustc.superspy.kits.AppInfo;
import com.software.ustc.superspy.kits.AppInfoAdapter;
import com.software.ustc.superspy.kits.BaseActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AppInfoShowActivity extends BaseActivity {
    private List<AppInfo> appInfoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info_show);
        getApps();
        AppInfoAdapter adapter = new AppInfoAdapter(AppInfoShowActivity.this, R.layout.item_app_info, appInfoList);
        final ListView listView = (ListView) findViewById(R.id.lv_apps);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppInfo appInfo = appInfoList.get(position);
                Toast toast = Toast.makeText(AppInfoShowActivity.this,null,Toast.LENGTH_SHORT);
                toast.setText(appInfo.getAppName()+" : "+appInfo.getAppName());
                toast.show();
                Intent intent = new Intent();

//                //传递Bundle(集装箱),将我们需要传递的数据全部放入集装箱，然后直接将集装箱传递到目标页面
//                Bundle appInfoBundle=new Bundle();
//                appInfoBundle.putString("appName",appInfo.getAppName());
//                appInfoBundle.putString("appPkgName",appInfo.getAppPackageName());
//                appInfoBundle.putInt("appSize",appInfo.getAppSize());
//                appInfoBundle.putString("appDir",appInfo.getAppDir());
//                appInfoBundle.putByteArray("appIcon",appInfo.getAppIcon().);
//                intent.putExtra("appInfo",appInfoBundle);
//                intent.setClass(AppInfoShowActivity.this,AppUsageShowActivity.class);
//                startActivity(intent);
            }
        });
    }

    public void getApps() {
        List<ApplicationInfo> apps = queryFilterAppInfo();
        for (ApplicationInfo applicationInfo : apps) {
            //packageManager是应用管理者对象
            PackageManager packageManager = getPackageManager();
            //获取应用名
            String appName = applicationInfo.loadLabel(packageManager).toString();
            //获取应用程序的 包名
            String appPackageName = applicationInfo.packageName;
            //获取应用图标
            Drawable appIcon = applicationInfo.loadIcon(packageManager);
            //获取应用存放数据目录
            String appDir = applicationInfo.sourceDir;
            //获取应用数据大小
            long length = new File(appDir).length();
            //转换为 M
            int appSize = (int)(length*1f/1024/1024);
            appInfoList.add(new AppInfo(appIcon, appName, appPackageName, appDir, appSize));
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
}