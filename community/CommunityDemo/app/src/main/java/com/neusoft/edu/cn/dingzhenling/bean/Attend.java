package com.neusoft.edu.cn.dingzhenling.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/11/14 0014.
 */
public class Attend extends BmobObject {
    private String user;
    private String tele;
    private String Campaignname;
    private String campaign_id;

    public String getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(String campaign_id) {
        this.campaign_id = campaign_id;
    }

    public String getCampaignname() {
        return Campaignname;
    }

    public void setCampaignname(String campaignname) {
        Campaignname = campaignname;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
