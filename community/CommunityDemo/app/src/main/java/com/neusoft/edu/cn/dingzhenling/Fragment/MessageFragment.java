package com.neusoft.edu.cn.dingzhenling.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neusoft.edu.cn.dingzhenling.R;

/**
 * Created by Administrator on 2016/10/17 0017.
 */
public class MessageFragment extends BaseFragment {


  /*  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_message, container, false);
        return  view;
    }*/

    @Override
    public View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_message, container, false);
        return  view;
    }
}


