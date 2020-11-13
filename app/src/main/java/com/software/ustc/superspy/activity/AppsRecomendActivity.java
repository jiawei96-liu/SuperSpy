package com.software.ustc.superspy.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.software.ustc.superspy.R;

import java.util.ArrayList;
import java.util.List;

public class AppsRecomendActivity extends AppCompatActivity {

    private RadarChart chart;
    private int[] colors = {Color.RED, Color.BLACK, Color.GREEN, Color.BLUE, Color.GRAY};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps_recomend);
        initChart();
        setData();
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
        xAxis.setLabelCount(4, true);
        xAxis.setAxisMaximum(4f);
        xAxis.setAxisMinimum(0f);
        xAxis.setTextSize(20f);

//        //自定义y轴标签，x轴同理
//        xAxis.setValueFormatter(new ValueFormatter() {
//            @Override
//            public String getFormattedValue(float value) {
//                //这里只是自定义了标签的颜色，如果要使用自定义标签颜色，请更改布局文件为自定为RoadarCharts类
//                chart.getXAxis().setTextColor(colors[(int) Math.abs(value % 5)]);
//                return super.getFormattedValue(value);
//            }
//        });

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
    private void setData() {
        List<RadarEntry> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new RadarEntry((float) (Math.random() * 100)));
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