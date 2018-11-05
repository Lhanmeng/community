package com.neusoft.edu.cn.dingzhenling;

import android.content.Context;

import com.neusoft.edu.cn.dingzhenling.bean.MyUser;

import cn.bmob.v3.BmobUser;

/**
 * Created by hasee on 2016/11/10.
 */
public class BmobUserManager {


    Context context;

    private static BmobUserManager instance;

    private BmobUserManager(){

    }

    public static BmobUserManager getInstance(Context context){
        if(instance==null){
            synchronized (BmobUserManager.class) {
                if(instance==null){
                    instance=new BmobUserManager();
                }
                instance.init(context);
            }
        }
        return instance;
    }

    private void init(Context c){
        this.context=c;
    }

    /**
     * 退出登陆
     */
    public void logout(){

        BmobUser.logOut();
        BmobUser currentUser = BmobUser.getCurrentUser(); // 现在的currentUser是null了
    }
    /**
     * 获取当前登陆用户对象 getCurrentUser
     */
    public MyUser getCurrentUser(){
        return BmobUser.getCurrentUser(MyUser.class);
    }
}
