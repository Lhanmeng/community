package com.neusoft.edu.cn.dingzhenling.bean;

import cn.bmob.v3.BmobObject;
/**
 * Created by Administrator on 2016/11/7 0007.
 */
public class Comment extends BmobObject {
    private String content;
    private Campaign campaign;
    private MyUser user;

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }




}
