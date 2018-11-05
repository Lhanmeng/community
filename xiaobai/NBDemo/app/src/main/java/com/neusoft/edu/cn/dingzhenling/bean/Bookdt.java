package com.neusoft.edu.cn.dingzhenling.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/3/25 0025.
 */
public class Bookdt extends BmobObject {
    private String content;
    private String xiangxi;
    private Bookgz bookgz;

    public String getXiangxi() {
        return xiangxi;
    }

    public void setXiangxi(String xiangxi) {
        this.xiangxi = xiangxi;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Bookgz getBookgz() {
        return bookgz;
    }

    public void setBookgz(Bookgz bookgz) {
        this.bookgz = bookgz;
    }
}
