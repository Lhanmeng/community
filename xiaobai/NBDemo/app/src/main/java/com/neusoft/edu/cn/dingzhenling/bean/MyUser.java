package com.neusoft.edu.cn.dingzhenling.bean;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2016/10/21 0021.
 */
public class MyUser extends BmobUser {
    private String user;
    private  BmobFile touxiang;
    private String address;

    public MyUser() {
        this.user = user;
        this.touxiang = touxiang;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BmobFile getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(BmobFile touxiang) {
        this.touxiang = touxiang;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
