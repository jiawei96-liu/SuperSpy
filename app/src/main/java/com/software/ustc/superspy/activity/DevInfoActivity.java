package com.software.ustc.superspy.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.software.ustc.superspy.R;
import com.software.ustc.superspy.kits.BaseActivity;
import com.software.ustc.superspy.kits.SystemUtils;


import android.os.Bundle;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class DevInfoActivity extends BaseActivity {

    private TelephonyManager phone;
    private WifiManager wifi;
    private Display display;
    private DisplayMetrics metrics;
    private LinearLayout ima;
    private static Context mParam1;
    ImageView im;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_info);

        phone = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        display = getWindowManager().getDefaultDisplay();

        metrics = getResources().getDisplayMetrics();


        ima =findViewById(R.id.la);
        ima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });
        ;

        init();

        DecimalFormat decimalFormat=new DecimalFormat("#.#");
        DecimalFormat decimalFormat2=new DecimalFormat("#");
        memInfo mem=new memInfo();
        double usd=((double)mem.getmem_UNUSED(DevInfoActivity.this))/(1024*1024);
        double tol=((double)mem.getmem_TOLAL())/(1024*1024);
        double l=usd/tol;
        im=findViewById(R.id.im);
        ViewGroup.LayoutParams lp1;
        lp1=  im.getLayoutParams();
        lp1.width=(int)(lp1.width*l);
        im.setLayoutParams(lp1);


    }

    private void init() {
        DisplayMetrics book = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(book);
        DecimalFormat decimalFormat=new DecimalFormat("#.#");
        DecimalFormat decimalFormat2=new DecimalFormat("#");
        memInfo mem=new memInfo();
        ArrayList<String> temp_storge = new ArrayList<String>();
        temp_storge = SystemUtils.queryStorage();
        String c=getCpuName();
        setEditText(R.id.type,c);
        setEditText(R.id.tolstorge,temp_storge.get(0));
        setEditText(R.id.curstorge,temp_storge.get(1));
        setEditText(R.id.CPUEdit,decimalFormat.format(((double)mem.getmem_UNUSED(DevInfoActivity.this))/(1024*1024))+"GB/"+decimalFormat2.format(((double)mem.getmem_TOLAL())/(1024*1024))+"GB");
        setEditText(R.id.device,android.os.Build.DEVICE);
        setEditText(R.id.radiovis, SystemUtils.getDeviceBrand());
//        setEditText(R.id.type, android.os.Build.BOARD);
        setEditText(R.id.disply, ((int)SystemUtils.getBatteryTotal( mParam1 )) + "mAh");
        setEditText(R.id.model, android.os.Build.MODEL);
        setEditText(R.id.width,display.getWidth()+"×"+display.getHeight());
        setEditText(R.id.release, "Android  "+Build.VERSION.RELEASE);

      }

    private void setEditText(int id, String s) {
        ((TextView) this.findViewById(id)).setText(s);
    }
    /**
     * 获取CPU型号
     * @return
     */
    public static String getCpuName(){

        String str1 = "/proc/cpuinfo";
        String str2 = "";

        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr);
            while ((str2=localBufferedReader.readLine()) != null) {
                if (str2.contains("Hardware")) {
                    return str2.split(":")[1];
                }
            }
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return null;

    }



    public class memInfo {

        // 获得可用的内存
        private long getmem_UNUSED(Context mContext) {
            long MEM_UNUSED;
            // 得到ActivityManager
            ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
            // 创建ActivityManager.MemoryInfo对象

            ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
            am.getMemoryInfo(mi);

            // 取得剩余的内存空间

            MEM_UNUSED = mi.availMem / 1024;
            return MEM_UNUSED;
        }

        // 获得总内存
        private long getmem_TOLAL() {
            long mTotal;
            // /proc/meminfo读出的内核信息进行解释
            String path = "/proc/meminfo";
            String content = null;
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(path), 8);
                String line;
                if ((line = br.readLine()) != null) {
                    content = line;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            // beginIndex
            int begin = content.indexOf(':');
            // endIndex
            int end = content.indexOf('k');
            // 截取字符串信息

            content = content.substring(begin + 1, end).trim();
            mTotal = Integer.parseInt(content);
            return mTotal;
        }
    }




}