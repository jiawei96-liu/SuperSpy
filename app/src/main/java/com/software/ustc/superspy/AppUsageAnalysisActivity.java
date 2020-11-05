package com.software.ustc.superspy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.software.ustc.superspy.kits.BaseActivity;

public class AppUsageAnalysisActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_usage_analysis);
    }
}