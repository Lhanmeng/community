package com.neusoft.edu.cn.dingzhenling.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.activity.CollectionActivity;
import com.neusoft.edu.cn.dingzhenling.activity.MyMessageActivity;
import com.neusoft.edu.cn.dingzhenling.activity.MyissueActivity;
import com.neusoft.edu.cn.dingzhenling.activity.PersonalActivity;
import com.neusoft.edu.cn.dingzhenling.bean.MyUser;
import com.squareup.picasso.Picasso;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class MeFragemt extends BaseFragment  implements View.OnClickListener {
    private LinearLayout me_update,my_issue ,my_solve,collection;
    private TextView username;
    private de.hdodenhof.circleimageview.CircleImageView userImage;
    public Context context;
    BmobUser user;
    @Override
    public View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_fragment, container, false);
        Bmob.initialize(this.getActivity(), "de7dad987e06923148923eb46e46dae4");
        me_update=(LinearLayout)view.findViewById(R.id.me_update);
        my_issue=(LinearLayout)view.findViewById(R.id.my_issue);
        my_solve=(LinearLayout)view.findViewById(R.id.my_solve);
        userImage=(de.hdodenhof.circleimageview.CircleImageView)view.findViewById(R.id.userImage);
        collection=(LinearLayout)view.findViewById(R.id.collection);
       Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("个人管理");
        username=(TextView)view.findViewById(R.id.username);
        initEvents();
        initDatas();
        return view;

    }

    private void initDatas() {
        MyUser user=BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<MyUser> query=new BmobQuery<>();
        query.getObject(user.getObjectId(), new QueryListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if(e==null){
                    username.setText(myUser.getUsername());
                    Picasso.with(getActivity()).load(myUser.getTouxiang().getFileUrl()).into(userImage);
                }
            }
        });
    }

    public void initEvents() {
        me_update.setOnClickListener(this);
        my_issue.setOnClickListener(this);
        my_solve.setOnClickListener(this);
        collection.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.me_update:
                startActivity(new Intent(this.getActivity(), PersonalActivity.class));
                break;
            case R.id.my_issue:
                startActivity(new Intent(this.getActivity(),MyissueActivity.class));
                break;
            case R.id.my_solve:
                startActivity(new Intent(this.getActivity(), MyMessageActivity.class));
                break;
            case R.id.collection:
                startActivity(new Intent(this.getActivity(), CollectionActivity.class));
                break;

            default:
                break;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        initDatas();
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();

        System.out.println("暂停");
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        System.out.println("消亡");
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        System.out.println("开始");
    }

}
