package com.software.ustc.superspy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.software.ustc.superspy.kits.AppInfo;
import com.software.ustc.superspy.kits.AppUsageUtil;
import com.software.ustc.superspy.kits.BaseActivity;
import com.software.ustc.superspy.kits.PicUtil;

import java.util.Calendar;
import java.util.HashMap;

public class AppUsageShowActivity extends BaseActivity {
    private AppInfo appInfo;
    private ImageView appIconIV;
    private TextView appNameTV;
    private TextView appVersionTV;
    private TextView appPackageNameTV;
    private TextView appDirTV;
    private TextView appSizeTV;
    private TextView appTimeUsageTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_usage);
        //资源初始化
        appIconIV = (ImageView)findViewById(R.id.iv_icon_single);
        appNameTV = (TextView)findViewById(R.id.txt_app_name_single);
        appVersionTV = (TextView)findViewById(R.id.txt_app_version_single);
        appPackageNameTV = (TextView)findViewById(R.id.txt_app_package_name_single);
        appDirTV = (TextView)findViewById(R.id.txt_app_dir_single);
        appSizeTV = (TextView)findViewById(R.id.txt_app_size_single);
        appTimeUsageTV=(TextView)findViewById(R.id.txt_app_time_usage_single);

        appBasicInfoShow();
//        appUsageInfoShow();
    }

    private void appUsageInfoShow()
    {
        Calendar calendar=Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_WEEK,-1);
        long startTime=calendar.getTimeInMillis();
        HashMap<String, Integer> timeSpentMap= AppUsageUtil.getTimeSpent(this,appInfo.getAppPackageName(), startTime, endTime);
        Integer value=(Integer)timeSpentMap.get(appInfo.getAppPackageName());
        appTimeUsageTV.setText("过去一周使用时间: "+ Integer.toString(value) +" s");
    }

    private void appBasicInfoShow()
    {
        //获取传递过来的数据
        Bundle bundle=getIntent().getBundleExtra("appInfo");
        Bitmap appIron=getIntent().getParcelableExtra("appIron");
        appInfo = new AppInfo(appIron,bundle.getString("appName",""),
                bundle.getString("appPkgName",""),bundle.getString("appVersion",""),
                bundle.getString("appDir",""),bundle.getLong("appSize",0));
        appIconIV.setImageDrawable(PicUtil.BitmapToDrawable(PicUtil.CreateReflectionImageWithOrigin(appInfo.getAppIcon())));
        appNameTV.setText(appInfo.getAppName());
        appVersionTV.setText("版本号: "+appInfo.getAppVersion());
        appPackageNameTV.setText("包名: "+appInfo.getAppPackageName());
        appDirTV.setText("路径: "+appInfo.getAppDir());
        java.text.DecimalFormat myformat=new java.text.DecimalFormat("0.00");
        String appSiseMB = myformat.format(appInfo.getAppSize()/1024.0/1024.0);
        appSizeTV.setText("大小: "+appSiseMB+"M / "+Long.toString(appInfo.getAppSize())+"B");
    }

}