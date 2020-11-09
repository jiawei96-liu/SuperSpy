package com.software.ustc.superspy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.software.ustc.superspy.R;
import com.software.ustc.superspy.kits.BaseActivity;

public class WelcomeActivity extends BaseActivity {

    private final int WELCONE_DISPLAY_LENGHT = 3000; // 3秒后进入系统
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        setContentView(R.layout.activity_welcome);
        Thread myThread=new Thread(){//创建子线程
            @Override
            public void run() {
                try{
                    sleep(WELCONE_DISPLAY_LENGHT);//使程序休眠五秒
                    Intent it=new Intent(getApplicationContext(), LoginActivity.class);//启动MainActivity
                    startActivity(it);
                    finish();//关闭当前活动
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        myThread.start();//启动线程
    }
}