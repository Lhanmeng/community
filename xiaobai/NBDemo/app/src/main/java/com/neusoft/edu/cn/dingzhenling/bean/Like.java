package com.neusoft.edu.cn.dingzhenling.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/4/5 0005.
 */
public class Like  extends BmobObject {
    private String issueid;
    private String userid;
    private MyUser user;
    private Issue issue;

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public String getIssueid() {
        return issueid;
    }

    public void setIssueid(String issueid) {
        this.issueid = issueid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
