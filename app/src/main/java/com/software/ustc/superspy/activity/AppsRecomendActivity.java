package com.software.ustc.superspy.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.software.ustc.superspy.R;
import com.software.ustc.superspy.db.sqllite.AppUsageDao;
import com.software.ustc.superspy.kits.AppTagsMap;
import com.software.ustc.superspy.kits.AppUsageUtil;
import com.software.ustc.superspy.kits.BaseActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AppsRecomendActivity extends BaseActivity {

    private RadarChart chart;
    private AppUsageDao pdao;
    private AppTagsMap appTagsMap;
    private HashMap<String, String> chartData = new HashMap<String, String>();
    private final List<String>chartX=new ArrayList<String>();
    private final List<String>chartY=new ArrayList<String>();
    private List<String> timeBeginEndList = new ArrayList<String>();
    private TextView timeBeginEndShowTextView;
    private Spinner timeBeginEndShowChoseSpinner;
    private ArrayAdapter<String> timeBeginEndShowAdapter;
    private int timeBeginEnd=30;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps_recomend);
        initBeginEndList();
        repareData();
        initChart();
        setChartData();
    }
    void initBeginEndList()
    {
        //第一步：定义下拉列表内容
        timeBeginEndList.add("一周");
        timeBeginEndList.add("半月");
        timeBeginEndList.add("一月");
        timeBeginEndList.add("三月");
        timeBeginEndList.add("半年");
        timeBeginEndList.add("一年");
        timeBeginEndShowTextView = (TextView)findViewById(R.id.tv_time_beginend);
        timeBeginEndShowChoseSpinner = (Spinner)findViewById(R.id.span_time_beginend);
        //第二步：为下拉列表定义一个适配器
        timeBeginEndShowAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timeBeginEndList);
        //第三步：设置下拉列表下拉时的菜单样式
        timeBeginEndShowAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        timeBeginEndShowChoseSpinner.setAdapter(timeBeginEndShowAdapter);
        //第五步：添加监听器，为下拉列表设置事件的响应
        timeBeginEndShowChoseSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onltemSelected(AdapterView<?> argO, View argl, int arg2, long arg3) {
                // TODO Auto-generated method stub
                /* 将所选timeBeginEndShowChoseSpinner的值带入myTextView中*/
                timeBeginEndShowTextView.setText("当前时间粒度:" + timeBeginEndShowAdapter.getItem(arg2));
                /* 将 timeBeginEndShowChoseSpinner 显示^*/
                argO.setVisibility(View.VISIBLE);
            }

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            public void onNothingSelected(AdapterView<?> argO) {
                // TODO Auto-generated method stub
                timeBeginEndShowTextView.setText(String.valueOf(timeBeginEnd)+"天");
                argO.setVisibility(View.VISIBLE);
            }
        });
        //将timeBeginEndShowChoseSpinner添加到OnTouchListener对内容选项触屏事件处理
        timeBeginEndShowChoseSpinner.setOnTouchListener(new Spinner.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                // 将mySpinner隐藏
                v.setVisibility(View.INVISIBLE);
                Log.i("spinner", "Spinner Touch事件被触发!");
                return false;
            }
        });
        //焦点改变事件处理
        timeBeginEndShowChoseSpinner.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                v.setVisibility(View.VISIBLE);
                Log.i("spinner", "Spinner FocusChange事件被触发！");
            }
        });
    }

    void repareData()
    {
        appTagsMap=new AppTagsMap();
        //权限检查
        AppUsageUtil.checkUsageStateAccessPermission(this);
        Calendar beginCal = Calendar.getInstance();
        beginCal.add(Calendar.HOUR_OF_DAY, -timeBeginEnd);
        Calendar endCal = Calendar.getInstance();
        long start_time = beginCal.getTimeInMillis();
        long end_time = endCal.getTimeInMillis();
        //数据库刷新
        AppUsageUtil.getAppUsageInfo(getApplicationContext(),start_time,end_time);
        pdao = new AppUsageDao(this);//数据层
        long[] tagUsage=new long[10];
        for(int i=0;i<appTagsMap.getAppTag().length-1;++i)
        {
            tagUsage[i]=pdao.queryAppTagUsage(appTagsMap.getAppTag()[i]);
        }
        //寻找使用最多的五种类型的app,构建图标数据
        long[] temp=tagUsage;
        for(int i=0;i<5;++i)
        {
            int max=0;
            //不统计其他类
            for(int j=0;j<appTagsMap.getAppTag().length-1;++j)
            {
                if(temp[j]>temp[max])
                {
                    max=j;
                }
            }
            java.text.DecimalFormat myformat = new java.text.DecimalFormat("0.00");
            String hour = myformat.format(temp[max]/60.0/60.0);
            chartData.put(appTagsMap.getPeopleTag()[max],hour);
            temp[max]=-1;
        }
        Iterator<Map.Entry<String, String>> iterator = chartData.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            chartX.add(entry.getKey());
            chartY.add(entry.getValue());
        }
    }



    /**
     * 一些轴上的设置等等
     */
    private void initChart() {
        chart = findViewById(R.id.chart);
        //设置web线的颜色(即就是外面包着的那个颜色)
        chart.setWebColorInner(Color.BLACK);
        //设置中心线颜色(也就是竖着的线条)
        chart.setWebColor(Color.BLACK);
        chart.setWebAlpha(50);
        XAxis xAxis = chart.getXAxis();
        //设置x轴标签字体颜色
        xAxis.setLabelCount(9, false);
        //自定义X轴坐标描述（也就是五个顶点上的文字,默认是0、1、2、3、4）
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float v, AxisBase axisBase) {
                if (v==0){
                    return chartX.get(0);
                }
                if (v==1){
                    return chartX.get(1);
                }
                if (v==2){
                    return chartX.get(2);
                }
                if (v==3){
                    return chartX.get(3);
                }
                if (v==4){
                    return chartX.get(4);
                }
                return "";
            }
        });
        xAxis.setAxisMaximum(4f);
        xAxis.setAxisMinimum(0f);
        xAxis.setTextSize(10f);
        YAxis yAxis = chart.getYAxis();
        //设置y轴的标签个数
        yAxis.setLabelCount(5, true);
        //设置y轴从0f开始
        yAxis.setAxisMinimum(0f);
        /*启用绘制Y轴顶点标签，这个是最新添加的功能
         * */
        yAxis.setDrawTopYLabelEntry(false);
        //设置字体大小
        yAxis.setTextSize(15f);
        //设置字体颜色
        yAxis.setTextColor(Color.RED);

        //启用线条，如果禁用，则无任何线条
        chart.setDrawWeb(true);

        //禁用图例和图表描述
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
    }

    /**
     * 设置数据
     */
    private void setChartData() {
        List<RadarEntry> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new RadarEntry(Float.parseFloat(chartY.get(i))));
        }
        RadarDataSet set = new RadarDataSet(list, "Petterp");

        //禁用标签
        set.setDrawValues(false);
        //设置填充颜色
        set.setFillColor(Color.BLUE);
        //设置填充透明度
        set.setFillAlpha(40);
        //设置启用填充
        set.setDrawFilled(true);
        //设置点击之后标签是否显示圆形外围
        set.setDrawHighlightCircleEnabled(true);
        //设置点击之后标签圆形外围的颜色
        set.setHighlightCircleFillColor(Color.RED);
        //设置点击之后标签圆形外围的透明度
        set.setHighlightCircleStrokeAlpha(40);
        //设置点击之后标签圆形外围的半径
        set.setHighlightCircleInnerRadius(20f);
        //设置点击之后标签圆形外围内圆的半径
        set.setHighlightCircleOuterRadius(10f);


        RadarData data = new RadarData(set);
        chart.setData(data);
        chart.invalidate();
    }
}