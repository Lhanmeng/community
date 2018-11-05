package com.neusoft.edu.cn.dingzhenling.bean;
import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2016/10/21 0021.
 */
public class MyUser extends BmobUser {
    private Integer age;
    private String user;
    private String Email;
    private String Photo;
    private  String telenumble;

    public String getTelenumble() {
        return telenumble;
    }

    public void setTelenumble(String telenumble) {
        this.telenumble = telenumble;
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "age=" + age +
                ", user='" + user + '\'' +
                ", Email='" + Email + '\'' +
                ", Photo='" + Photo + '\'' +
                '}';
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

}
