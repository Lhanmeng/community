package com.neusoft.edu.cn.dingzhenling.fragment;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.neusoft.edu.cn.dingzhenling.R;

public abstract class BaseFragment extends Fragment {
    public Context context;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context=this.getActivity();
        View view = initViews(inflater, container, savedInstanceState);
        initData();
        initEvents();
        return view;
    }
    public abstract View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    public  void initEvents(){};
    public void initData(){};
    public void changeFrament(Fragment fragment, Bundle bundle, String tag) {

        FragmentManager fgManager = (this.getActivity()).getFragmentManager();
        for (int i = 0, count = fgManager.getBackStackEntryCount(); i < count; i++) {
            fgManager.popBackStack();
        }
        FragmentTransaction fg = fgManager.beginTransaction();
        fragment.setArguments(bundle);
        fg.add(R.id.toolbar, fragment, tag);
        fg.addToBackStack(tag);
        fg.commit();
    }

    public void getToast(String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public void getNetError() {
        getToast("亲~网络连接失败！");
    }
}
