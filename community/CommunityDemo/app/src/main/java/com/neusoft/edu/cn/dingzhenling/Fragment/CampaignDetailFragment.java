package com.neusoft.edu.cn.dingzhenling.Fragment;
import android.os.Bundle;
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
import com.neusoft.edu.cn.dingzhenling.adapter.CampaignAdapter;
import com.neusoft.edu.cn.dingzhenling.adapter.CommentAdapter;
import com.neusoft.edu.cn.dingzhenling.bean.Campaign;
import com.neusoft.edu.cn.dingzhenling.bean.Comment;
import com.neusoft.edu.cn.dingzhenling.bean.Groups;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class CampaignDetailFragment extends BaseFragment{
    //活动评论
    private RecyclerView mRecyclerView;
    private List<Comment> mDatas;
    private CommentAdapter mAdapter;


    ImageButton ibn_back;
    ImageView img_img;
    TextView  tv_time,tv_name,tv_info,tv_place,tv_leader,tv_group;

    String campaign_id;
    @Override
    public View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.campaigndetail,container,false);
        //活动评论
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_comments);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //活动
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
                bundle.putString("campaign_id",campaign_id);
                SearchFragment1 searchFragment1= new  SearchFragment1();
                changeFrament(searchFragment1, bundle, "searchFragment1");
            }

        });
        return view;
    }
    @Override
    public void initData() {
    /*    showActivities();*/
        showComment();/*评论*/
        showCampaign();
    }
    // 社团
    public void showCampaign() {
      /*  tv_id.setText(groups_id);*/
        BmobQuery<Campaign> bmobQuery = new BmobQuery<Campaign>();
        bmobQuery.getObject(campaign_id, new QueryListener<Campaign>() {
            @Override
            public void done(Campaign object,BmobException e) {
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
    //显示评论
    public void showComment() {
        mDatas = new ArrayList<Comment>();
        BmobQuery<Comment> query = new BmobQuery<Comment>();
        //根据社团id获取评论
        Campaign campaign = new Campaign();
        campaign.setObjectId(campaign_id);
        query.addWhereEqualTo("campaign",campaign);
        query.include("campaign");
        //按照时间降序
        query.order("-createdAt");
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if (e == null) {
                    mDatas = list;
                     mAdapter = new CommentAdapter(CampaignDetailFragment.this.getActivity(), mDatas);
                    mAdapter.setOnItemClickListener(new CommentAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, String data) {
                        /*   Intent intent=new Intent(IndexCommunityDetail.this.getActivity(), CampaignDetailactivity.class);
                            intent.putExtra("campaign_id",data);
                            //跳转到activity this 为当前的意思
                            startActivity(intent);
                            Bundle bundle = new Bundle();
                            bundle.putString("campaign_id", data);
                            changeFrament(new CampaignDetailFragment(), bundle, "indexCommunityDetail");*/
                        }
                    });
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        });
    }
}