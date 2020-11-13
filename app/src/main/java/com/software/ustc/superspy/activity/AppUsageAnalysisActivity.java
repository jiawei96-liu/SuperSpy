package com.software.ustc.superspy.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.software.ustc.superspy.R;
import com.software.ustc.superspy.db.sqllite.AppUsageDao;
import com.software.ustc.superspy.kits.AppInfo;
import com.software.ustc.superspy.kits.AppUsageInfo;
import com.software.ustc.superspy.kits.AppUsageUtil;
import com.software.ustc.superspy.kits.BaseActivity;
import com.software.ustc.superspy.service.AppUsageService;

import java.util.List;

public class AppUsageAnalysisActivity extends BaseActivity implements View.OnClickListener{

    private ListView lvapp;

    //数据集合
    private List<AppUsageInfo> plist;
    private AppUsageDao pdao;
    //适配器
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_usage_analysis);
        //权限检查
        AppUsageUtil.checkUsageStateAccessPermission(this);
        //数据库刷新
        AppUsageUtil.getAppUsageInfo(getApplicationContext());
        //数据展示
        lvapp = (ListView) findViewById(R.id.lvapp);
        pdao = new AppUsageDao(this);//数据层
        plist = pdao.queryAppUsageList();
        //适配器
        adapter = new MyAdapter(AppUsageAnalysisActivity.this, R.layout.item_app_usage_info, plist);
        //设置适配器
        lvapp.setAdapter(adapter);

//        startService(new Intent(this, AppUsageService.class));
    }

    @Override
    protected void onResume() {
        //权限检查
        AppUsageUtil.checkUsageStateAccessPermission(this);
        //数据库刷新
        AppUsageUtil.getAppUsageInfo(getApplicationContext());
        //数据展示
        lvapp = (ListView) findViewById(R.id.lvapp);
        pdao = new AppUsageDao(this);//数据层
        plist = pdao.queryAppUsageList();
        //适配器
        adapter = new MyAdapter(AppUsageAnalysisActivity.this, R.layout.item_app_usage_info, plist);
        //设置适配器
        lvapp.setAdapter(adapter);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, AppUsageService.class));
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    class MyAdapter extends ArrayAdapter<AppUsageInfo> {
        private int resId;
        public MyAdapter(@NonNull Context context, int resource, @NonNull List<AppUsageInfo> objects) {
            super(context, resource, objects);
            resId=resource;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //可以将之前加载好的布局进行缓存,之后进行重用,提高加载速度
            View app_itemView;
            if(convertView == null)
            {
                app_itemView = LayoutInflater.from(AppUsageAnalysisActivity.this).inflate(resId,parent,false);
            }
            else
            {
                app_itemView=convertView;
            }

            //获取数据项视图
            TextView txt_app_name = (TextView) app_itemView.findViewById(R.id.txt_app_name_usage_item);
            TextView txt_last_start_time = (TextView) app_itemView.findViewById(R.id.txt_last_start_time_usage_item);
            TextView txt_run_times = (TextView) app_itemView.findViewById(R.id.txt_run_times_usage_item);
            TextView txt_foreground_time = (TextView) app_itemView.findViewById(R.id.txt_foreground_time_usage_item);

            //获取数据项
            final AppUsageInfo appUsageInfo = (AppUsageInfo) plist.get(position);
            txt_app_name.setText(appUsageInfo.getApp_name());
            txt_last_start_time.setText(appUsageInfo.getLast_start_time());
            txt_run_times.setText(appUsageInfo.getRun_times());
            txt_foreground_time.setText(appUsageInfo.getForeground_time());
            return app_itemView;
        }
    }

}
