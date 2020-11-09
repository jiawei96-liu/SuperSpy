package com.software.ustc.superspy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.software.ustc.superspy.R;
import com.software.ustc.superspy.db.sqllite.AppUsageDao;
import com.software.ustc.superspy.kits.AppUsageInfo;
import com.software.ustc.superspy.kits.AppUsageUtil;
import com.software.ustc.superspy.kits.BaseActivity;
import com.software.ustc.superspy.service.AppUsageService;

import java.util.List;

public class AppUsageAnalysisActivity extends BaseActivity implements View.OnClickListener{

    private ListView lvapp;

    //数据集合
    private List plist;
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
        //service switch
        Button startAppUsageServiceBtn=(Button)findViewById(R.id.btn_app_usage_collect_service_start);
        Button stopAppUsageServiceBtn=(Button)findViewById(R.id.btn_app_usage_collect_service_stop);
        startAppUsageServiceBtn.setOnClickListener(this);
        stopAppUsageServiceBtn.setOnClickListener(this);
        //数据展示
        lvapp = (ListView) findViewById(R.id.lvapp);
        pdao = new AppUsageDao(this);//数据层
        plist = pdao.queryAppUsageInfoList();
        //适配器
        adapter = new MyAdapter();
        //设置适配器
        lvapp.setAdapter(adapter);
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
        plist = pdao.queryAppUsageInfoList();
        //适配器
        adapter = new MyAdapter();
        //设置适配器
        lvapp.setAdapter(adapter);
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_app_usage_collect_service_start:
                startService(new Intent(this, AppUsageService.class));
                break;
            case R.id.btn_app_usage_collect_service_stop:
                stopService(new Intent(this, AppUsageService.class));
                break;
            default:
                break;
        }
    }

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            //数据项大小
            return plist.size();
        }

        @Override
        public Object getItem(int position) {
            //返回数据
            return plist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //初始化数据项
            final View app_itemView = View.inflate(AppUsageAnalysisActivity.this, R.layout.item_app_usage_info, null);

            //获取数据项视图
            TextView txt_app_name1 = (TextView) app_itemView.findViewById(R.id.txt_app_name1);
            TextView txt_first_runtime = (TextView) app_itemView.findViewById(R.id.txt_first_runtime1);
            TextView txt_last_runtime = (TextView) app_itemView.findViewById(R.id.txt_last_runtime1);
            TextView txt_total_runtime = (TextView) app_itemView.findViewById(R.id.txt_total_runtime1);

            //获取数据项
            final AppUsageInfo appUsageInfo = (AppUsageInfo) plist.get(position);
            txt_app_name1.setText(appUsageInfo.getApk_name());
//            txt_first_runtime.setText(appUsageInfo.getFirst_start_time());
            txt_last_runtime.setText(appUsageInfo.getLast_start_time());
            txt_total_runtime.setText(appUsageInfo.getForeground_time() + "秒");
            return app_itemView;
        }
    }

}
