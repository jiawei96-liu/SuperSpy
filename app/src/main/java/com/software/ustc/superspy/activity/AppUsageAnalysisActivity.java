package com.software.ustc.superspy.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.software.ustc.superspy.R;
import com.software.ustc.superspy.db.sqllite.AppInfoDao;
import com.software.ustc.superspy.db.sqllite.AppUsageDao;
import com.software.ustc.superspy.kits.AppInfo;
import com.software.ustc.superspy.kits.AppTagsMap;
import com.software.ustc.superspy.kits.AppUsageInfo;
import com.software.ustc.superspy.kits.AppUsageUtil;
import com.software.ustc.superspy.kits.BaseActivity;
import com.software.ustc.superspy.kits.PicUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AppUsageAnalysisActivity extends BaseActivity implements View.OnClickListener {

    private ListView lvapp;

    //数据集合
    private List<AppUsageInfo> plist;
    private AppUsageDao pdao;
    private Button refresh;
    private Button gotoRecommend;
    //适配器
    private MyAdapter adapter;
    private String tagChosen;
    private long startTime;
    private long endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_usage_analysis);
        //权限检查
        AppUsageUtil.checkUsageStateAccessPermission(this);
        Calendar beginCal = Calendar.getInstance();
        beginCal.add(Calendar.YEAR, -1);
        Calendar endCal = Calendar.getInstance();
        startTime = beginCal.getTimeInMillis();
        endTime = endCal.getTimeInMillis();
        //数据库刷新
        AppUsageUtil.getAppUsageInfo(getApplicationContext(), startTime, endTime);
        pdao = new AppUsageDao(this);//数据层
        lvapp = (ListView) findViewById(R.id.lvapp);
        plist = pdao.queryAppUsageList();
        //适配器
        adapter = new MyAdapter(AppUsageAnalysisActivity.this, R.layout.item_app_usage_info, plist);
        //设置适配器
        lvapp.setAdapter(adapter);

        refresh = findViewById(R.id.refresh);
        refresh.setOnClickListener(this);
        gotoRecommend = findViewById(R.id.goto_recomend);
        gotoRecommend.setOnClickListener(this);
        showSpanTag();
        showSpanTime();
    }

    private void showSpanTag() {
        List<String> list = new ArrayList<String>();
        list.add("ALL");
        AppTagsMap tagsMap = new AppTagsMap();
        String[] appTag = tagsMap.getAppTag();
        for (int i = 0; i < appTag.length; ++i) {
            list.add(appTag[i]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        Spinner sp = (Spinner) findViewById(R.id.span_app_tag);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();
                tagChosen = adapter.getItem(position);
            }

            //没有选中时的处理
            public void onNothingSelected(AdapterView<?> parent) {
                tagChosen="ALL";
            }
        });
    }

    private void showSpanTime() {
        List<String> list = new ArrayList<String>();
        final String[] timeAll = new String[]{"一年", "半年", "三月", "一月", "半月", "一周", "一天"};
        for (int i = 0; i < timeAll.length; ++i) {
            list.add(timeAll[i]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        Spinner sp = (Spinner) findViewById(R.id.span_app_time);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();
                int days=0;
                Calendar beginCal = Calendar.getInstance();
                switch (adapter.getItem(position)) {
                    case "一年":
                        beginCal.add(Calendar.YEAR, -1);
                        break;
                    case "半年":
                        beginCal.add(Calendar.MONTH, -6);
                        break;
                    case "三月":
                        beginCal.add(Calendar.MONTH, -3);
                        break;
                    case "一月":
                        beginCal.add(Calendar.MONTH, -1);
                        break;
                    case "半月":
                        beginCal.add(Calendar.DATE, -15);
                        break;
                    case "一周":
                        beginCal.add(Calendar.DATE, -7);
                        break;
                    case "一天":
                        beginCal.add(Calendar.DATE, -1);
                        break;
                }
                Calendar endCal = Calendar.getInstance();
                startTime = beginCal.getTimeInMillis();
                endTime = endCal.getTimeInMillis();
            }

            //没有选中时的处理
            public void onNothingSelected(AdapterView<?> parent) {
                Calendar beginCal = Calendar.getInstance();
                beginCal.add(Calendar.YEAR, -1);
                Calendar endCal = Calendar.getInstance();
                startTime = beginCal.getTimeInMillis();
                endTime = endCal.getTimeInMillis();
            }
        });
    }

    @Override
    protected void onResume() {
        //刷洗listview
        plist = pdao.queryAppUsageList();
        //适配器
        adapter = new MyAdapter(AppUsageAnalysisActivity.this, R.layout.item_app_usage_info, plist);
        //设置适配器
        lvapp.setAdapter(adapter);
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refresh:
                //数据库刷新
                AppUsageUtil.getAppUsageInfo(getApplicationContext(), startTime, endTime);
                pdao = new AppUsageDao(this);//数据层
                lvapp = (ListView) findViewById(R.id.lvapp);
                if(tagChosen.equals("ALL"))
                {
                    plist = pdao.queryAppUsageList();
                }
                else
                {
                    plist = pdao.queryAppUsageListByTag(tagChosen);
                }
                //适配器
                adapter = new MyAdapter(AppUsageAnalysisActivity.this, R.layout.item_app_usage_info, plist);
                //设置适配器
                lvapp.setAdapter(adapter);
                break;
            case R.id.goto_recomend:
                Intent intent=new Intent(AppUsageAnalysisActivity.this,AppsRecomendActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    class MyAdapter extends ArrayAdapter<AppUsageInfo> {
        private int resId;

        public MyAdapter(@NonNull Context context, int resource, @NonNull List<AppUsageInfo> objects) {
            super(context, resource, objects);
            resId = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //可以将之前加载好的布局进行缓存,之后进行重用,提高加载速度
            View app_itemView;
            if (convertView == null) {
                app_itemView = LayoutInflater.from(AppUsageAnalysisActivity.this).inflate(resId, parent, false);
            } else {
                app_itemView = convertView;
            }

            //获取数据项视图
            ImageView icon_usage_item = (ImageView)app_itemView.findViewById(R.id.iv_icon_usage_item);
            TextView txt_app_name = (TextView) app_itemView.findViewById(R.id.txt_app_name_usage_item);
            TextView txt_last_start_time = (TextView) app_itemView.findViewById(R.id.txt_last_start_time_usage_item);
//            TextView txt_run_times = (TextView) app_itemView.findViewById(R.id.txt_run_times_usage_item);
            TextView txt_foreground_time = (TextView) app_itemView.findViewById(R.id.txt_foreground_time_usage_item);

            //获取数据项
            final AppUsageInfo appUsageInfo = (AppUsageInfo) plist.get(position);
            txt_app_name.setText(appUsageInfo.getApp_name());
            txt_last_start_time.setText(appUsageInfo.getLast_start_time());
//            txt_run_times.setText(appUsageInfo.getRun_times());
            AppInfoDao pdao = new AppInfoDao(getApplicationContext());
            AppInfo appInfo=pdao.querySignalAppInfo(appUsageInfo.getApp_name());
            if(appInfo!=null)
                icon_usage_item.setImageDrawable(PicUtil.BitmapToDrawable(appInfo.getAppIcon()));
            else
                icon_usage_item.setImageDrawable(getResources().getDrawable((R.mipmap.app_icon_none)));

            java.text.DecimalFormat myformat = new java.text.DecimalFormat("0.00");
            Float minuts=Float.parseFloat(appUsageInfo.getForeground_time());
            String time = myformat.format(minuts/60.0);
            txt_foreground_time.setText(time);
            return app_itemView;
        }
    }

}
