package com.neusoft.edu.cn.dingzhenling.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/3/16 0016.
 */
public class Genre extends BmobObject {
    private  String  genrename;
    private String desc;

    public String getGenrename() {
        return genrename;
    }

    public void setGenrename(String genrename) {
        this.genrename = genrename;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
