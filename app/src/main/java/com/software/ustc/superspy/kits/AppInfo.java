package com.software.ustc.superspy.kits;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class AppInfo {

    protected Bitmap appIcon;
    protected String appName;
    protected String appPackageName;
    protected String appVersion;
    protected String appDir;
    protected int appSize;
//    protected boolean appIsSys;


    public AppInfo(Bitmap appIcon, String appName, String appPackageName, String appVersion, String appDir, int appSize)
    {
        this.appIcon=appIcon;
        this.appName=appName;
        this.appPackageName=appPackageName;
        this.appVersion=appVersion;
        this.appDir=appDir;
        this.appSize=appSize;
    }

    public Bitmap getAppIcon() {
        return appIcon;
    }

    public String getAppName() {
        return appName;
    }

    public String getAppPackageName() {
        return appPackageName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getAppDir() {
        return appDir;
    }

    public int getAppSize() {
        return appSize;
    }
}
