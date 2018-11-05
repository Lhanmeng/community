package com.neusoft.edu.cn.dingzhenling.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.activity.IssuedetaiActivity;
import com.neusoft.edu.cn.dingzhenling.activity.ReleaseActivity;
import com.neusoft.edu.cn.dingzhenling.adapter.IssueAdapter;
import com.neusoft.edu.cn.dingzhenling.bean.Genre;
import com.neusoft.edu.cn.dingzhenling.bean.Issue;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/3/27 0027.
 */
public class IssueFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private List<Issue> mDatas;
    private IssueAdapter mAdapter;

    public View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.issue_fragment, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("所有问题");
        //发布
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(IssueFragment.this.getActivity(), ReleaseActivity.class);
              /*  intent.putExtra("campaign_id",data);*/
                //跳转到activity this 为当前的意思
                startActivity(intent);

            }

        });

        return view;
    }
    /*
   1.初始化数据集合
           */
    public void initData() {
        mDatas = new ArrayList<Issue>();
        BmobQuery<Issue> query = new BmobQuery<Issue>();
        query.include("user");
        //按照时间降序
        query.order("-createdAt");
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects(new FindListener<Issue>() {
            @Override
            public void done(List<Issue> list, BmobException e) {
                if (e == null) {
                    mDatas = list;
                    mAdapter = new IssueAdapter(IssueFragment.this.getActivity(), mDatas);
                    mAdapter.setOnItemClickListener(new IssueAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, String data) {
                            Intent intent = new Intent(IssueFragment.this.getActivity(), IssuedetaiActivity.class);
                            intent.putExtra("issue_id", data);
                            startActivity(intent);
                            // 跳转到Fragment
                            /*changeFrament(new CampaignDetailFragment(), bundle, "campaignDetailFragment");*/
                        }
                    });
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        initData();
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
