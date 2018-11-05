package com.neusoft.edu.cn.dingzhenling.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.activity.CampaignDetailactivity;
import com.neusoft.edu.cn.dingzhenling.activity.LoginActivity;
import com.neusoft.edu.cn.dingzhenling.adapter.CampaignAdapter;
import com.neusoft.edu.cn.dingzhenling.bean.Campaign;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.b.I;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2016/10/17 0017.
 */
public class CampaignFragment extends BaseFragment {
  private RecyclerView mRecyclerView;
    private List<Campaign> mDatas;
    private CampaignAdapter mAdapter;
    private ImageButton ibn_search;
    @Override
    public View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_campaign,container,false);
        ibn_search=(ImageButton)view.findViewById(R.id.search);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;
    }
    /*
    1.初始化数据集合
            */
    public void initData()
    {
        mDatas = new ArrayList<Campaign>();
        BmobQuery<Campaign> query = new BmobQuery<Campaign>();
        //按照时间降序
        query.order("-createdAt");
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects( new FindListener<Campaign>() {
            @Override
            public void done(List<Campaign> list, BmobException e) {
                if(e==null)
                {
                    mDatas=list;
                    mAdapter=new CampaignAdapter(CampaignFragment.this.getActivity(),mDatas);
                    mAdapter.setOnItemClickListener(new CampaignAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, String data) {
                          Intent intent=new Intent(CampaignFragment.this.getActivity(), CampaignDetailactivity.class);
                            intent.putExtra("campaign_id",data);
                            //跳转到activity this 为当前的意思
                            startActivity(intent);
                           // 跳转到Fragment
                            /*changeFrament(new CampaignDetailFragment(), bundle, "campaignDetailFragment");*/
                        }
                    });
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        });
        ibn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                SearchFragment1 searchFragment1= new  SearchFragment1();
                changeFrament(searchFragment1, bundle, "searchFragmen1");
            }
        });

    }

}

