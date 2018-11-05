package com.neusoft.edu.cn.dingzhenling.Fragment;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.activity.LoginActivity;
import com.neusoft.edu.cn.dingzhenling.activity.PersonalInfoActivity;
import com.neusoft.edu.cn.dingzhenling.activity.SkinActivity;

public class PersonSettingFragment extends BaseFragment implements View.OnClickListener {
   private LinearLayout et_skin,ll_etpwd,ll_exit;
   private ImageButton ibn_back;
    public View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.person_setting, container, false);
        et_skin = (LinearLayout) view.findViewById(R.id.et_skin);
        ll_etpwd= (LinearLayout) view.findViewById(R.id.et_password);
        ll_exit=(LinearLayout)view.findViewById(R.id.exit);
        ibn_back=(ImageButton) view.findViewById(R.id.back);
        ibn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                PersonFragment personFragment= new   PersonFragment ();
                changeFrament(personFragment, bundle, "personFragment ");
            }
        });
        return view;
    }
    public void initEvents() {
        et_skin.setOnClickListener(this);
        ll_etpwd.setOnClickListener(this);
        ll_exit.setOnClickListener(this);
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_skin:
                startActivity(new Intent(this.getActivity(), SkinActivity.class));
                break;
         case R.id.et_password:
                startActivity(new Intent(this.getActivity(), PersonalInfoActivity.class));
                break;
            case R.id.exit:
                startActivity(new Intent(this.getActivity(), LoginActivity.class));
                break;
            default:
                break;
        }
    }
}
