package com.neusoft.edu.cn.dingzhenling.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/11/10 0010.
 */
public class FeedBack extends BmobObject {
    private String content;
    private MyUser user;
    private String tele;
    private String tel;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
