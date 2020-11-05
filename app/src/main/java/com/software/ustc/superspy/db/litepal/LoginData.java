package com.software.ustc.superspy.db.litepal;

import org.litepal.crud.LitePalSupport;

public class LoginData extends LitePalSupport {
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


