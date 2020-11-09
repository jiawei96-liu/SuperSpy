package com.software.ustc.superspy.kits;

public class AppUsageInfo {
    public int id;
    public  String app_name;
    public  String first_start_time ;
    public  String last_start_time ;
    public  String foreground_time ;
    public  String run_times ;

    public AppUsageInfo( int id , String app_name, String first_start_time, String last_start_time, String foreground_time, String run_times) {
        this.id = id;
        this.app_name = app_name;
        this.first_start_time = first_start_time;
        this.last_start_time = last_start_time;
        this.foreground_time = foreground_time;
        this.run_times = run_times;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getFirst_start_time() {
        return first_start_time;
    }

    public void setFirst_start_time(String first_start_time) {
        this.first_start_time = first_start_time;
    }

    public String getLast_start_time() {
        return last_start_time;
    }

    public void setLast_start_time(String last_start_time) {
        this.last_start_time = last_start_time;
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
}
