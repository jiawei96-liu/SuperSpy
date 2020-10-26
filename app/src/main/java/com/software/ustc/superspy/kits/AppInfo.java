package com.software.ustc.superspy.kits;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class AppInfo {

    protected String appName;
    protected String appPackageName;
    protected int appVersion;
    protected Drawable appIcon;

    public AppInfo(String appName, String appPackageName, int appVersion, Drawable appIcon)
    {
        this.appName=appName;
        this.appPackageName=appPackageName;
        this.appVersion=appVersion;
        this.appIcon=appIcon;
    }

    public String getAppName() {
        return appName;
    }

    public String getAppPackageName() {
        return appPackageName;
    }

    public int getAppVersion() {
        return appVersion;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }
}
