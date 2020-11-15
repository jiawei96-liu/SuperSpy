package com.software.ustc.superspy.kits;

import android.content.Context;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Describe: 系统工具类
 */
public class SystemUtils {

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return  语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机设备名
     *
     * @return  手机设备名
     */
    public static String getSystemDevice() {
        return Build.DEVICE;
    }

    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机主板名
     *
     * @return  主板名
     */
    public static String getDeviceBoand() {
        return Build.BOARD;
    }


    /**
     * 获取手机厂商名
     *
     * @return  手机厂商名
     */
    public static String getDeviceManufacturer() {
        return Build.MANUFACTURER;
    }


    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return  手机IMEI
     */
    public static String getIMEI(Context ctx) {
//        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
//        if (tm != null) {
//            return tm.getDeviceId();
//        }
        return null;
    }

    public static ArrayList<String> queryStorage(){

        ArrayList<String> result  = new ArrayList<String>();
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());

        //存储块总数量
        long blockCount = statFs.getBlockCount();
        //块大小
        long blockSize = statFs.getBlockSize();
        //可用块数量
        long availableCount = statFs.getAvailableBlocks();
        //剩余块数量，注：这个包含保留块（including reserved blocks）即应用无法使用的空间
        long freeBlocks = statFs.getFreeBlocks();
        //这两个方法是直接输出总内存和可用空间，也有getFreeBytes
        //API level 18（JELLY_BEAN_MR2）引入
        long totalSize = statFs.getTotalBytes();
        long availableSize = statFs.getAvailableBytes();

        Log.d("statfs","total = " + getUnit(totalSize));
        Log.d("statfs","availableSize = " + getUnit(availableSize));

        result.add(getUnit(totalSize));
        result.add(getUnit(availableSize));

        //这里可以看出 available 是小于 free ,free 包括保留块。
        Log.d("statfs","total = " + getUnit(blockSize * blockCount));
        Log.d("statfs","available = " + getUnit(blockSize * availableCount));
        Log.d("statfs","free = " + getUnit(blockSize * freeBlocks));

        return result;
    }

    private static String[] units = {"B", "KB", "MB", "GB", "TB"};

    /**
     * 单位转换
     */
    private static String getUnit(float size) {
        int index = 0;
        while (size > 1024 && index < 4) {
            size = size / 1024;
            index++;
        }
        return String.format(Locale.getDefault(), " %.2f %s", size, units[index]);
    }


    /**
     * 获取电池的容量
     */
    public static double getBatteryTotal(Context context) {
        double batteryCapacity = 0;
//        if (batteryCapacity > 0) {
//            return batteryCapacity;
//        }
        Object mPowerProfile;
        final String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";
        try {
            mPowerProfile = Class.forName(POWER_PROFILE_CLASS).getConstructor(Context.class).newInstance(context);
            batteryCapacity = (double) Class.forName(POWER_PROFILE_CLASS).getMethod("getBatteryCapacity").invoke(mPowerProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return batteryCapacity;
    }

    /**
     * 获取当前电量百分比
     *
     * @param context
     * @return
     */
    public static int getBatteryCurrent(Context context) {
        int capacity = 0;
        try {
            BatteryManager manager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
            capacity = manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);//当前电量剩余百分比
        } catch (Exception e) {

        }
        return capacity;
    }

//    public static final int BATTERY_STATUS_UNKNOWN = 1;
//    public static final int BATTERY_STATUS_CHARGING = 2;
//    public static final int BATTERY_STATUS_DISCHARGING = 3;
//    public static final int BATTERY_STATUS_NOT_CHARGING = 4;
//    public static final int BATTERY_STATUS_FULL = 5;

    public static int getBatteryStatus(Context context) {
        int status = 0;
        try {
            BatteryManager manager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
            status = manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_STATUS);
        } catch (Exception e) {

        }
        return status;
    }

}
