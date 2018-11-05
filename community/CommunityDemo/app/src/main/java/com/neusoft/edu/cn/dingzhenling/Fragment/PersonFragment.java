package com.neusoft.edu.cn.dingzhenling.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.activity.PersonFeedbackActivity;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;


public class PersonFragment extends BaseFragment  implements View.OnClickListener {
    private LinearLayout ll_collect,ll_setting,ll_feedback;

    public Context context;
    private TextView username;
   // Context context;
    BmobUser user;

    public View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = this.getActivity();
       // Bmob.initialize(this, "5f113daad74ad3772deed9628bffa81d");
        View view = inflater.inflate(R.layout.activity_personal, container, false);
        Bmob.initialize(this.getActivity(), "5f113daad74ad3772deed9628bffa81d");
        username=(TextView)view.findViewById(R.id.username);
        ll_collect = (LinearLayout) view.findViewById(R.id.collect);
        ll_feedback=(LinearLayout)view.findViewById(R.id.feedback);
        ll_setting=(LinearLayout)view.findViewById(R.id.setting);
        user = BmobUser.getCurrentUser();
        username.setText(user.getUsername());
        ll_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                changeFrament(new PersonSettingFragment(), bundle, "personSettingFragment");
            }
        });
        ll_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                changeFrament(new CollectFragment(), bundle, "CollectFragment");
            }
        });
        return view;
    }
    public void initEvents() {
        ll_feedback.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.feedback:
                startActivity(new Intent(this.getActivity(), PersonFeedbackActivity.class));
                break;
            default:
                break;
        }
    }


}


