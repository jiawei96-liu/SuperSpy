package com.software.ustc.superspy.db.litepal;

import org.litepal.crud.LitePalSupport;

public class LoginData extends LitePalSupport {
    private  String username;
    private  String passward;
    private int id;
    private String vertifyid1;
    private String vertify1;
    private String vertifyid2;
    private String vertify2;

    public String getVertifyid2() {
        return vertifyid2;
    }

    public void setVertifyid2(String vertifyid2) {
        this.vertifyid2 = vertifyid2;
    }

    public String getVertify2() {
        return vertify2;
    }

    public void setVertify2(String vertify2) {
        this.vertify2 = vertify2;
    }

    public String getVertifyid1() {
        return vertifyid1;
    }

    public void setVertifyid1(String vertifyid1) {
        this.vertifyid1 = vertifyid1;
    }

    public String getVertify1() {
        return vertify1;
    }

    public void setVertify1(String vertify1) {
        this.vertify1 = vertify1;
    }

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


