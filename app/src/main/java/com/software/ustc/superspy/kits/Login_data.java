package com.software.ustc.superspy.kits;

import org.litepal.crud.LitePalSupport;

public class Login_data extends LitePalSupport {
    private  String username;
    private  String passward;
    private int id;
    // private String username;
//    private String passward;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public  String getUsername() {
        return username;
    }

    public  String getPassward() {
        return passward;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassward(String passward) {
        this.passward = passward;
    }
}


