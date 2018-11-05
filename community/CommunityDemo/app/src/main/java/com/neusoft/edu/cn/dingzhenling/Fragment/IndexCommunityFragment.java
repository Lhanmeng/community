package com.neusoft.edu.cn.dingzhenling.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.adapter.HomeAdapter;
import com.neusoft.edu.cn.dingzhenling.bean.Groups;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class IndexCommunityFragment  extends BaseFragment {

    private RecyclerView mRecyclerView;
    private List<Groups> mDatas;
    private HomeAdapter mAdapter;
    private ImageButton ibn_back,ibn_search;


    @Override
    public View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.index_communitylist,container,false);
        ibn_back=(ImageButton) view.findViewById(R.id.back);
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
        mDatas = new ArrayList<Groups>();
        BmobQuery<Groups> query = new BmobQuery<Groups>();
        //按照时间降序
        query.order("-createdAt");
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects( new FindListener<Groups>() {
            @Override
            public void done(List<Groups> list, BmobException e) {
                if(e==null)
                {
                    mDatas=list;
                    mAdapter=new HomeAdapter(IndexCommunityFragment.this.getActivity(),mDatas);
                    mAdapter.setOnItemClickListener(new HomeAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, String data) {
                            Bundle bundle=new Bundle();
                            bundle.putString("groups_id",data);
                            bundle.putString("flag","0");
                            changeFrament(new IndexCommunityDetail(), bundle, "indexCommunityDetail");
                        }
                    });
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        });

        ibn_back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            MyHomeFragment myHomeFragment= new  MyHomeFragment();
            changeFrament(myHomeFragment, bundle, " myHomeFragment");
        }
    });

        ibn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                SearchFragment searchFragment= new  SearchFragment();
                changeFrament(searchFragment, bundle, "searchFragment");
            }
        });

    }




}

