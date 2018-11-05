package com.neusoft.edu.cn.dingzhenling.config;

import android.app.Activity;
import android.app.Application;

import com.neusoft.edu.cn.dingzhenling.ActivityManagerUtils;
import com.neusoft.edu.cn.dingzhenling.BmobUserManager;

/**
 * Created by hasee on 2016/11/10.
 */
public class NBDemo extends Application{

    public static NBDemo mInstance;
    public static String TAG;

    public void onCreate() {
        super.onCreate();
        TAG = this.getClass().getSimpleName();
        mInstance = this;
//        initImageLoader();

    }

    public void addActivity(Activity ac){
        ActivityManagerUtils.getInstance().addActivity(ac);
    }

    public static NBDemo getInstance() {
        return mInstance;
    }

    /**
     * 退出登录,清空缓存数据
     */
    public void logout() {
        BmobUserManager.getInstance(getApplicationContext()).logout();
    }
}
