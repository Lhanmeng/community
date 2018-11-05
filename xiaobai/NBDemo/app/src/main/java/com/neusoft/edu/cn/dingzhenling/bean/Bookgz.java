package com.neusoft.edu.cn.dingzhenling.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/3/24 0024.
 */
public class Bookgz extends BmobObject {
    private String name;
    private Bookgenre genere;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Bookgenre getGenere() {
        return genere;
    }

    public void setGenere(Bookgenre genere) {
        this.genere = genere;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
