package com.neusoft.edu.cn.dingzhenling.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.activity.IssueActivity;
import com.neusoft.edu.cn.dingzhenling.activity.MainActivity;
import com.neusoft.edu.cn.dingzhenling.adapter.GenreAdapter;
import com.neusoft.edu.cn.dingzhenling.bean.Genre;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/3/27 0027.
 */
public class MyHomeFragment extends BaseFragment {
    private RollPagerView mRollViewPager;
    //分类列表
    private RecyclerView mRecyclerView;
    private List<Genre> mDatas;
    private GenreAdapter mAdapter;
    Context context;

    @Nullable
    @Override
    public View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        context=this.getActivity();
        View view = inflater.inflate(R.layout.home_activity,container,false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setTitle("电脑故障类型");

        mRollViewPager = (RollPagerView) view.findViewById(R.id.roll_view_pager);

        //设置播放时间间隔
        mRollViewPager.setPlayDelay(1000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(500);
        //设置适配器
        mRollViewPager.setAdapter(new TestNormalAdapter());
        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器
        //mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
        mRollViewPager.setHintView(new ColorPointHintView(getActivity(), Color.YELLOW, Color.WHITE));
        return view;
    }
    /*
1.初始化数据集合
      */
    public void initData() {
        mDatas = new ArrayList<Genre>();
        BmobQuery<Genre> query = new BmobQuery<Genre>();
        //按照时间降序
        query.order("-createdAt");
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects(new FindListener<Genre>() {
            @Override
            public void done(List<Genre> list, BmobException e) {
                if (e == null) {
                    mDatas = list;
                    Log.e("list",mDatas.toString());
                    mAdapter = new GenreAdapter(MyHomeFragment.this.getActivity(), mDatas);
                    mAdapter.setOnItemClickListener(new GenreAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, String data) {
                            Intent intent=new Intent(MyHomeFragment.this.getActivity(), IssueActivity.class);
                            intent.putExtra("genre_id",data);
                            //跳转到activity this 为当前的意思
                            startActivity(intent);
                                               }
                    });
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        });
    }
    private class TestNormalAdapter extends StaticPagerAdapter {

        private int[] imgs = {
                R.drawable.hardware,
                R.drawable.application,
                R.drawable.operating,
                R.drawable.other,
        };
        @Override
        public View getView(ViewGroup container, final int position) {
            ImageView view = new ImageView(container.getContext());
            //根据图片位置显示 绑定图片位置 mDatas根据类别表list坐标
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(),IssueActivity.class);
                    intent.putExtra("genre_id",mDatas.get(position).getObjectId());
                    startActivity(intent);
                }
            });
            return view;
        }
        @Override
        public int getCount() {
            return imgs.length;
        }
    }


}
