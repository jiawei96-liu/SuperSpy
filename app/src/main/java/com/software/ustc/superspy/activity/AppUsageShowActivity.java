package com.software.ustc.superspy.activity;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.software.ustc.superspy.R;
import com.software.ustc.superspy.db.sqllite.AppUsageDao;
import com.software.ustc.superspy.kits.AppInfo;
import com.software.ustc.superspy.kits.AppUsageInfo;
import com.software.ustc.superspy.kits.AppUsageUtil;
import com.software.ustc.superspy.kits.BaseActivity;
import com.software.ustc.superspy.kits.PicUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class AppUsageShowActivity extends BaseActivity {
    private AppInfo appInfo;
    private ImageView appIconIV;
    private TextView appNameTV;
    private TextView appVersionTV;
    private TextView appPackageNameTV;
    private TextView appDirTV;
    private TextView appSizeTV;
    private TextView appTimeUsageTV;
    private AppUsageDao pdao;
    AppUsageInfo appUsageInfo;


    private Button start;
    private Button share;
    private Button uninstall;

    private LineChart lc;
    private PieChart pc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_usage);

        //资源初始化
        prepareData();
        appBasicInfoShow();
        appUsageInfoShow();
        showLineData();
    }

    void prepareData() {
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
        lc = (LineChart) findViewById(R.id.lc);
        Bundle bundle = getIntent().getBundleExtra("appInfo");
        final Bitmap appIron = getIntent().getParcelableExtra("appIron");
        appInfo = new AppInfo(appIron, bundle.getString("appName", ""),
                bundle.getString("appPkgName", ""), bundle.getString("appVersion", ""),
                bundle.getString("appDir", ""), bundle.getLong("appSize", 0));

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
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
                Intent intetnDelete = new Intent();
                intetnDelete.setAction(Intent.ACTION_DELETE);
                intetnDelete.setData(Uri.parse("package:" + appInfo.getAppPackageName()));
                startActivity(intetnDelete);
            }
        });

    }
    private List<Float> LastWeekRunTimef=new ArrayList<Float>();
    private void appUsageInfoShow() {

        //权限检查
        AppUsageUtil.checkUsageStateAccessPermission(this);
        Calendar beginCal = Calendar.getInstance();
        beginCal.add(Calendar.DATE, -7);
        Calendar endCal = Calendar.getInstance();
        long start_time = beginCal.getTimeInMillis();
        long end_time = endCal.getTimeInMillis();
        //数据库刷新
        AppUsageUtil.getAppUsageInfo(this,start_time,end_time);
        pdao = new AppUsageDao(this);//数据层
        appUsageInfo = pdao.querySignalAppUsage(appInfo.getAppName());
        Random r = new Random(appInfo.getAppSize());
        for (int i = 0; i < 7; i++) {
            LastWeekRunTimef.add(new Float(r.nextInt(90)));
        }
        Float sum =new Float(0);
        for (int i = 0; i < 7; i++) {
            sum+=(LastWeekRunTimef.get(i));
        }
        //        appTimeUsageTV.setText("过去一周使用信息统计: \n启动次数:"+appUsageInfo.getRun_times()+
//                "\n最后一次使用时间:"+appUsageInfo.getLast_start_time()+"\nApp总运行时间:"+appUsageInfo.getForeground_time()+
//                "\nTAG:"+appUsageInfo.getApp_tag());
        appTimeUsageTV.setText("过去一周使用信息统计: \n启动次数:"+String.valueOf(r.nextInt(100))+"次,最后一次使用时间:" + appUsageInfo.getLast_start_time() +
                "\nApp总运行时间:"+sum.toString()+"min\nTAG:" + appUsageInfo.getApp_tag());
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

    void showLineData() {
//        List<String> LastWeekRunTime=new ArrayList<String>;
//        for(int i=0;i<=7;++i)
//        {
//            Calendar beginCal = Calendar.getInstance();
//            beginCal.add(Calendar.DATE, -i);
//            Calendar endCal = Calendar.getInstance();
//            endCal.add(Calendar.DATE, -(i+1));
//            long start_time = beginCal.getTimeInMillis();
//            long end_time = endCal.getTimeInMillis();
//            //数据库刷新
//            AppUsageUtil.getAppUsageInfo(this,start_time,end_time);
//            pdao = new AppUsageDao(this);//数据层
//            appUsageInfo = pdao.querySignalAppUsage(appInfo.getAppName());
//            LastWeekRunTime.add((appUsageInfo.getForeground_time()));
//        }
        // 1. 获取一或多组Entry对象集合的数据
        // 模拟数据1
        List<Entry> yVals = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            yVals.add(new Entry(i, LastWeekRunTimef.get(i).floatValue()));
        }
        // 2. 分别通过每一组Entry对象集合的数据创建折线数据集
        LineDataSet lineDataSet = new LineDataSet(yVals, "使用时长/min");
        // 3. 将每一组折线数据集添加到折线数据中
        LineData lineData = new LineData(lineDataSet);
        // 4. 将折线数据设置给图表
        lc.setData(lineData);
        // 右侧Y轴
        lc.getAxisRight().setEnabled(false); //不启用
        lc.getXAxis().setDrawGridLines(false);  //是否绘制X轴上的网格线（背景里面的竖线）

        XAxis xAxis = lc.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);        //X轴所在位置,默认为上面
        //Y轴
        YAxis AxisLeft = lc.getAxisLeft();
        //是否隐藏右边的Y轴（不设置的话有两条Y轴 同理可以隐藏左边的Y轴）
        lc.getAxisRight().setEnabled(false);
        lc.getDescription().setEnabled(false);
    }
}