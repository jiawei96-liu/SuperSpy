package com.software.ustc.superspy.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.software.ustc.superspy.R;
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


    private Button start;
    private Button share;
    private Button uninstall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_usage);
        //资源初始化
        appIconIV = (ImageView) findViewById(R.id.iv_icon_single);
        appNameTV = (TextView) findViewById(R.id.txt_app_name_single);
        appVersionTV = (TextView) findViewById(R.id.txt_app_version_single);
        appPackageNameTV = (TextView) findViewById(R.id.txt_app_package_name_single);
        appDirTV = (TextView) findViewById(R.id.txt_app_dir_single);
        appSizeTV = (TextView) findViewById(R.id.txt_app_size_single);
        appTimeUsageTV = (TextView) findViewById(R.id.txt_app_time_usage_single);
        start = findViewById(R.id.ll_app_start);
        share = findViewById(R.id.ll_app_share);
        uninstall = findViewById(R.id.ll_app_uninstall);
        appBasicInfoShow();
//        appUsageInfoShow();

        Bundle bundle = getIntent().getBundleExtra("appInfo");
        final Bitmap appIron = getIntent().getParcelableExtra("appIron");
        appInfo = new AppInfo(appIron, bundle.getString("appName", ""),
                bundle.getString("appPkgName", ""), bundle.getString("appVersion", ""),
                bundle.getString("appDir", ""), bundle.getLong("appSize", 0));

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    /**
                     * 拿到这个包对应的PackageInfo对象，这里我们指定了两个flag，
                     * 一个就是之前讲过的，所有的安装过的应用程序都找出来，包括卸载了但没清除数据的
                     * 一个就是指定它去扫描这个应用的AndroidMainfest文件时候的activity节点，
                     * 这样我们才能拿到具有启动意义的ActivityInfo，如果不指定，是无法扫描出来的
                     *
                     * */
                    PackageInfo packageInfo = getPackageManager().getPackageInfo(appInfo.getAppPackageName(), PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_ACTIVITIES);
                    //扫描出来的所有activity节点的信息
                    ActivityInfo[] activityInfos = packageInfo.activities;
                    //有些应用是无法启动的，所以我们就要判断一下
                    if (activityInfos != null && activityInfos.length > 0) {
                        //在扫描出来的应用里面，第一个是具有启动意义的
                        ActivityInfo startActivity = activityInfos[0];
                        //设置Intent，启动activity
                        try {
                            Intent intent = new Intent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setClassName(appInfo.getAppPackageName(), startActivity.name);
                            startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(AppUsageShowActivity.this, "很抱歉，启动失败！", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AppUsageShowActivity.this, "这个应用程序无法启动", Toast.LENGTH_SHORT).show();
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareintent = new Intent();
                //设置Intent的action
                shareintent.setAction(Intent.ACTION_SEND);
                //设定分享的类型是纯文本的
                shareintent.setType("text/plain");
                //设置分享主题
                shareintent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                //设置分享的文本
                shareintent.putExtra(Intent.EXTRA_TEXT, "有一个很好的应用程序哦！给你推荐一下：" + appInfo.getAppPackageName() + "/n本消息来自系统测试");
                startActivity(shareintent);
            }
        });
        uninstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                if(item.isSystemApp()){
//                    Toast.makeText(MainActivity.this, "不能卸载系统的应用程序", Toast.LENGTH_SHORT).show();
//                }else{

//                String strUri = "package:"+appInfo.getAppPackageName();
//                Uri uri = Uri.parse(strUri);//通过uri去访问你要卸载的包名
//                Intent delectIntent = new Intent();
//                delectIntent.setAction(Intent.ACTION_DELETE);
//                delectIntent.setData(uri);
//                startActivityForResult(delectIntent, 0);

                Intent intetnDelete = new Intent();
                intetnDelete.setAction(Intent.ACTION_DELETE);
                intetnDelete.setData(Uri.parse("package:" + appInfo.getAppPackageName()));
                startActivity(intetnDelete);

//                Uri uri = Uri.fromParts("package", appInfo.getAppPackageName(), null);
//                Intent intent = new Intent(Intent.ACTION_DELETE, uri);
//                startActivity(intent);
//                }
            }
        });
    }

    private void appUsageInfoShow() {
        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_WEEK, -1);
        long startTime = calendar.getTimeInMillis();
        HashMap<String, Integer> timeSpentMap = AppUsageUtil.getAppUsageTimeSpent(this, appInfo.getAppPackageName(), startTime, endTime);
        Integer value = (Integer) timeSpentMap.get(appInfo.getAppPackageName());
        appTimeUsageTV.setText("过去一周使用时间: " + Integer.toString(value) + " s");
    }

    private void appBasicInfoShow() {
        //获取传递过来的数据
        Bundle bundle = getIntent().getBundleExtra("appInfo");
        Bitmap appIron = getIntent().getParcelableExtra("appIron");
        appInfo = new AppInfo(appIron, bundle.getString("appName", ""),
                bundle.getString("appPkgName", ""), bundle.getString("appVersion", ""),
                bundle.getString("appDir", ""), bundle.getLong("appSize", 0));
        appIconIV.setImageDrawable(PicUtil.BitmapToDrawable(PicUtil.CreateReflectionImageWithOrigin(appInfo.getAppIcon())));
        appNameTV.setText(appInfo.getAppName());
        appVersionTV.setText("版本号: " + appInfo.getAppVersion());
        appPackageNameTV.setText("包名: " + appInfo.getAppPackageName());
        appDirTV.setText("路径: " + appInfo.getAppDir());
        java.text.DecimalFormat myformat = new java.text.DecimalFormat("0.00");
        String appSiseMB = myformat.format(appInfo.getAppSize() / 1024.0 / 1024.0);
        appSizeTV.setText("大小: " + appSiseMB + "M / " + Long.toString(appInfo.getAppSize()) + "B");
    }

}