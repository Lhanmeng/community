package com.neusoft.edu.cn.dingzhenling.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.bean.Collect;
import com.squareup.picasso.Picasso;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Administrator on 2016/11/9 0009.
 */
public class CollectDetailFragment extends  BaseFragment{

/*    TextView campaign_name,Groups_name,campaign_info;
    String campaign_id;
    ImageView campaign_pic;
    private ImageButton btn_back;*/
    String campaign_id;
    ImageButton ibn_back;
    ImageView img_img;
    TextView  tv_time,tv_name,tv_info,tv_place,tv_leader,tv_group;

    @Override
    public View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.collect_detail,container,false);
        ibn_back=(ImageButton) view.findViewById(R.id.back);
        img_img=(ImageView) view.findViewById(R.id.image);
        tv_time=(TextView)view.findViewById(R.id.campaign_time);
        tv_place=(TextView)view.findViewById(R.id.campaign_place);
        tv_leader=(TextView)view.findViewById(R.id.h_leader);
        tv_group=(TextView)view.findViewById(R.id.h_groupname);
        tv_name=(TextView) view.findViewById(R.id.campaign_name);
        tv_info=(TextView)view.findViewById(R.id.campaign_info);
        campaign_id=getArguments().getString("campaign_id");
        ibn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                CollectFragment collectFragment= new  CollectFragment();
                changeFrament(collectFragment, bundle, " collectFragment");
            }
        });
        return view;
    }
    @Override
    public void initData() {
       // tv_id.setText(course_id);
        BmobQuery<Collect> bmobQuery = new BmobQuery<Collect>();
        bmobQuery.getObject(campaign_id, new QueryListener<Collect>() {
            @Override
            public void done(final Collect object, BmobException e) {
                if(e==null){
                    tv_time.setText(object.getTime());
                    tv_name.setText(object.getCampaignname());
                    tv_group.setText(object.getGname());
                    tv_info.setText(object.getIntro());
                    tv_place.setText(object.getPlace());
                    tv_leader.setText(object.getLeader());
                    Picasso.with(context).load(object.getPic()).into(img_img);
                    campaign_id=getArguments().getString("campaign_id");

                }else{
                    getToast("查询失败：" + e.getMessage());
                }
            }
        });
    }
}

