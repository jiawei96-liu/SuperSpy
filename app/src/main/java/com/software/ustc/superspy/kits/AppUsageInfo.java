package com.software.ustc.superspy.kits;

public class AppUsageInfo {
    protected String apk_name;
    protected String app_name;
    protected String first_timestamp;
    protected String last_timestamp;
    protected String foreground_time;
    protected String last_start_time;
    protected String run_times;
    protected String app_tag;

    public AppUsageInfo(String apk_name, String app_name, String first_timestamp, String last_timestamp, String foreground_time, String last_start_time, String run_times, String app_tag) {
        this.apk_name = apk_name;
        this.app_name = app_name;
        this.first_timestamp = first_timestamp;
        this.last_timestamp = last_timestamp;
        this.foreground_time = foreground_time;
        this.last_start_time = last_start_time;
        this.run_times = run_times;
        this.app_tag = app_tag;
    }

    //set,get
    public String getApk_name() {
        return apk_name;
    }

    public void setApk_name(String apk_name) {
        this.apk_name = apk_name;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getForeground_time() {
        return foreground_time;
    }

    public void setForeground_time(String foreground_time) {
        this.foreground_time = foreground_time;
    }

    public String getRun_times() {
        return run_times;
    }

    public void setRun_times(String run_times) {
        this.run_times = run_times;
    }

    public String getFirst_timestamp() {
        return first_timestamp;
    }

    public void setFirst_timestamp(String first_timestamp) {
        this.first_timestamp = first_timestamp;
    }

    public String getLast_timestamp() {
        return last_timestamp;
    }

    public void setLast_timestamp(String last_timestamp) {
        this.last_timestamp = last_timestamp;
    }

    public String getLast_start_time() {
        return last_start_time;
    }

    public void setLast_start_time(String last_start_time) {
        this.last_start_time = last_start_time;
    }

    public String getApp_tag() {
        return app_tag;
    }

    public void setApp_tag(String app_tag) {
        this.app_tag = app_tag;
    }
}
