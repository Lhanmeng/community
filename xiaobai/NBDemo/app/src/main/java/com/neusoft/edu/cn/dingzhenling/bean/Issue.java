package com.neusoft.edu.cn.dingzhenling.bean;

import java.io.File;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by Administrator on 2017/3/23 0023.
 */
public class Issue extends BmobObject {


    private String content;
    private String title;
    private String beizhu;
    private String time;
    private MyUser user;
    private Genre genre;
    private BmobFile image;
    private BmobRelation collect;

    public BmobRelation getCollect() {
        return collect;
    }

    public void setCollect(BmobRelation collect) {
        this.collect = collect;
    }

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public String getBeizhu(String beizhu) {
        return this.beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", beizhu='" + beizhu + '\'' +
                ", user=" + user +
                ", genre=" + genre +
                '}';
    }
}
