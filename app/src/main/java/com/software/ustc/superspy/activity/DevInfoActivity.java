package com.software.ustc.superspy.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.software.ustc.superspy.R;
import com.software.ustc.superspy.kits.BaseActivity;

import android.os.Bundle;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

public class DevInfoActivity extends BaseActivity {

    private TelephonyManager phone;
    private WifiManager wifi;
    private Display display;
    private DisplayMetrics metrics;
     ImageView im;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_info);

        phone = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        display = getWindowManager().getDefaultDisplay();

        metrics = getResources().getDisplayMetrics();



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


//        try {
//            Class localClass = Class.forName("android.os.SystemProperties");
//            Object localObject1 = localClass.newInstance();
//            Object localObject2 = localClass.getMethod("get", new Class[]{String.class, String.class}).invoke(localObject1, new Object[]{"gsm.version.baseband", "no message"});
//            Object localObject3 = localClass.getMethod("get", new Class[]{String.class, String.class}).invoke(localObject1, new Object[]{"ro.build.display.id", ""});
//
//
////            setEditText(R.id.get, localObject2 + "");
//
////            setEditText(R.id.osVersion, localObject3 + "");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        //获取网络连接管理者
//        ConnectivityManager connectionManager = (ConnectivityManager)
//                getSystemService(CONNECTIVITY_SERVICE);
        //获取网络的状态信息，有下面三种方式
        //NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();

//        setEditText(R.id.lianwang, networkInfo.getType() + "");
//        setEditText(R.id.lianwangname, networkInfo.getTypeName());
//        setEditText(R.id.imei, phone.getDeviceId());
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        setEditText(R.id.deviceversion, phone.getDeviceSoftwareVersion());
//        setEditText(R.id.imsi, phone.getSubscriberId());
//        setEditText(R.id.number, phone.getLine1Number());
//        setEditText(R.id.simserial, phone.getSimSerialNumber());
//        setEditText(R.id.simoperator,phone.getSimOperator());
//        setEditText(R.id.simoperatorname, phone.getSimOperatorName());
//        setEditText(R.id.simcountryiso, phone.getSimCountryIso());
//        setEditText(R.id.workType,phone.getNetworkType()+"");
//        setEditText(R.id.netcountryiso,phone.getNetworkCountryIso());
//        setEditText(R.id.netoperator,phone.getNetworkOperator());
//        setEditText(R.id.netoperatorname,phone.getNetworkOperatorName());



//        setEditText(R.id.wifimac, wifi.getConnectionInfo().getMacAddress());
//        setEditText(R.id.getssid,wifi.getConnectionInfo().getSSID());
//        setEditText(R.id.getbssid,wifi.getConnectionInfo().getBSSID());
//        setEditText(R.id.ip,wifi.getConnectionInfo().getIpAddress()+"");
//        setEditText(R.id.bluemac, BluetoothAdapter.getDefaultAdapter()
//                .getAddress());
//        setEditText(R.id.bluname, BluetoothAdapter.getDefaultAdapter().getName()
//        );
        DecimalFormat decimalFormat=new DecimalFormat("#.#");
        DecimalFormat decimalFormat2=new DecimalFormat("#");
        memInfo mem=new memInfo();

        String c=getCpuName();
        setEditText(R.id.type,c);
        setEditText(R.id.CPUEdit,decimalFormat.format(((double)mem.getmem_UNUSED(DevInfoActivity.this))/(1024*1024))+"GB/"+decimalFormat2.format(((double)mem.getmem_TOLAL())/(1024*1024))+"GB");
        setEditText(R.id.device,android.os.Build.DEVICE);
        setEditText(R.id.radiovis,android.os.Build.getRadioVersion());
//        setEditText(R.id.type, android.os.Build.BOARD);
        setEditText(R.id.disply, android.os.Build.DISPLAY);
        setEditText(R.id.model, android.os.Build.MODEL);
        setEditText(R.id.width,display.getWidth()+"×"+display.getHeight());
        setEditText(R.id.release, Build.VERSION.RELEASE);
//        double usd=((double)mem.getmem_UNUSED(DevInfoActivity.this))/(1024*1024);
//        double tol=((double)mem.getmem_TOLAL())/(1024*1024);
//        double l=usd/tol;
//        im=findViewById(R.id.im);
//        ViewGroup.LayoutParams lp1;
//        lp1=  im.getLayoutParams();
//        lp1=im.getHeight();
//        lp1.height=(int)(lp1.height*l);
//        im.setLayoutParams(lp1);

//        ViewGroup.LayoutParams params =  im.getLayoutParams();
//        params.height=200;
//        params.width =100;
//        im.setLayoutParams(params);

//        setEditText(R.id.andrlid_id,
//                Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
//        setEditText(R.id.serial,android.os.Build.SERIAL);
//        setEditText(R.id.brand,android.os.Build.BRAND);
//        setEditText(R.id.tags, android.os.Build.TAGS);
//        setEditText(R.id.fingerprint,android.os.Build.FINGERPRINT);
//        setEditText(R.id.bootloader, Build.BOOTLOADER);

//        setEditText(R.id.sdk,Build.VERSION.SDK);
//        setEditText(R.id.sdk_INT,Build.VERSION.SDK_INT+"");
//        setEditText(R.id.codename,Build.VERSION.CODENAME);
//        setEditText(R.id.incremental,Build.VERSION.INCREMENTAL);
//        setEditText(R.id.cpuabi, android.os.Build.CPU_ABI);
//        setEditText(R.id.cpuabi2, android.os.Build.CPU_ABI2);
//        setEditText(R.id.board, android.os.Build.BOARD);
//        setEditText(R.id.product, android.os.Build.PRODUCT);
//        setEditText(R.id.user, android.os.Build.USER);
//        setEditText(R.id.hardware, android.os.Build.HARDWARE);
//        setEditText(R.id.host, android.os.Build.HOST);
//        setEditText(R.id.changshang, android.os.Build.MANUFACTURER);
//        setEditText(R.id.phonetype,phone.getPhoneType()+"");
//        setEditText(R.id.simstate,phone.getSimState()+"");
//        setEditText(R.id.b_id, Build.ID);
//        setEditText(R.id.gjtime,android.os.Build.TIME+"");

//        setEditText(R.id.height,display.getHeight()+"");
//        setEditText(R.id.dpi,book.densityDpi+"");
//        setEditText(R.id.density,book.density+"");
//        setEditText(R.id.xdpi,book.xdpi+"");
//        setEditText(R.id.ydpi,book.ydpi+"");
//        setEditText(R.id.scaledDensity,book.scaledDensity+"");


//
//        //setEditText(R.id.wl,getNetworkState(this)+"");
//        // 方法2
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        int width=dm.widthPixels;
//        int  height=dm.heightPixels;
//
//        setEditText(R.id.xwidth,width+"");
//        setEditText(R.id.xheight,height+"");
//
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