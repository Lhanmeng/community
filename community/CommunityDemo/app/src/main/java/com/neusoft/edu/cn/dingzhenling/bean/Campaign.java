package com.neusoft.edu.cn.dingzhenling.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/10/27 0027.
 */
public class Campaign extends BmobObject {
    private String Campaignname;
    private String place;
    private String leader;
    private String Intro;
    private String ShortIntro;
    private String Time;
    private String Pic;
    private String gname;
    private Groups groups;

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getCampaignname() {
        return Campaignname;
    }

    public void setCampaignname(String campaignname) {
        Campaignname = campaignname;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getIntro() {
        return Intro;
    }

    public void setIntro(String intro) {
        Intro = intro;
    }

    public String getShortIntro() {
        return ShortIntro;
    }

    public void setShortIntro(String shortIntro) {
        ShortIntro = shortIntro;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getPic() {
        return Pic;
    }

    public void setPic(String pic) {
        Pic = pic;
    }

    public Groups getGroups() {
        return groups;
    }

    public void setGroups(Groups groups) {
        this.groups = groups;
    }


}
