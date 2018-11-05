package com.neusoft.edu.cn.dingzhenling.Fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.adapter.CampaignAdapter;
import com.neusoft.edu.cn.dingzhenling.bean.Campaign;
import com.neusoft.edu.cn.dingzhenling.bean.Groups;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class IndexCommunityDetail extends BaseFragment {
    // 活动详情
    private RecyclerView mRecyclerView;
    private List<Campaign> mDatas;
    private CampaignAdapter mAdapter;
    ImageButton ibn_back;
    ImageView img_img;
    TextView tv_name, tv_create_time, tv_leader, tv_team, tv_state, tv_info;
    String groups_id;
    String flag;

    @Override
    public View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.index_communitydetaillist, container, false);
        //活动
        mRecyclerView = (RecyclerView) view.findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ibn_back = (ImageButton) view.findViewById(R.id.back);
        img_img = (ImageView) view.findViewById(R.id.image);
        tv_name = (TextView) view.findViewById(R.id.groupname);
        tv_create_time = (TextView) view.findViewById(R.id.create_time);
        tv_leader = (TextView) view.findViewById(R.id.leader);
        tv_team = (TextView) view.findViewById(R.id.team);
        tv_state = (TextView) view.findViewById(R.id.state);
        tv_info = (TextView) view.findViewById(R.id.info);
        groups_id = getArguments().getString("groups_id");
        flag=getArguments().getString("flag");
     /*   ibn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                IndexCommunityFragment indexCommunityFragment = new IndexCommunityFragment();
                changeFrament(indexCommunityFragment, bundle, "indexCommunityFragment");
            }
        });*/
        if (flag.contains("0")){
            ibn_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    IndexCommunityFragment fragment = new IndexCommunityFragment();
                    changeFrament(fragment, bundle, " ");
                }
            });
        }
        else {
            ibn_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    SearchFragment searchFragment = new SearchFragment();
                    changeFrament(searchFragment, bundle, " ");
                }
            });
        }

        return view;
    }

    @Override
    public void initData() {
        showActivities();
        showGroups();
    }
//活动
    public void showActivities() {
        mDatas = new ArrayList<Campaign>();
        BmobQuery<Campaign> query = new BmobQuery<Campaign>();
        //根据社团id获取活动
        Groups groups = new Groups();
        groups.setObjectId(groups_id);
        query.addWhereEqualTo("groups", groups);
        query.include("groups");
        //按照时间降序
        query.order("-createdAt");
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects(new FindListener<Campaign>() {
            @Override
            public void done(List<Campaign> list, BmobException e) {
                if (e == null) {
                    mDatas = list;
                    Log.e("list",mDatas.toString());
                    mAdapter = new CampaignAdapter(IndexCommunityDetail.this.getActivity(), mDatas);
                    mAdapter.setOnItemClickListener(new CampaignAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, String data) {
                         /*   Intent intent=new Intent(IndexCommunityDetail.this.getActivity(), CampaignDetailactivity.class);
                            intent.putExtra("campaign_id",data);
                            //跳转到activity this 为当前的意思
                             startActivity(intent);*/
                           /* Bundle bundle = new Bundle();
                            bundle.putString("campaign_id", data);
                            changeFrament(new CampaignDetailFragment(), bundle, "indexCommunityDetail");*/
                        }
                    });
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        });
    }
    // 社团
    public void showGroups() {
      /*  tv_id.setText(groups_id);*/
        BmobQuery<Groups> bmobQuery = new BmobQuery<Groups>();
        bmobQuery.getObject(groups_id, new QueryListener<Groups>() {
            @Override
            public void done(Groups object, BmobException e) {
                if (e == null) {
                    tv_name.setText(object.getGroupname());
                    tv_create_time.setText(object.getCreatetime());
                    tv_leader.setText(object.getLeader());
                    tv_team.setText(object.getTeam());
                    tv_state.setText(object.getState());
                    tv_info.setText(object.getIntro());
                    Picasso.with(context).load(object.getLogo()).into(img_img);
                } else {
                    getToast("查询失败：" + e.getMessage());
                }
            }
        });
    }

}