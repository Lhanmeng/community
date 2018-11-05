package com.neusoft.edu.cn.dingzhenling.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.activity.ActivityIndex;

/**
 * Created by Administrator on 2016/10/17 0017.
 */
public class MyHomeFragment extends BaseFragment {
    private RollPagerView mRollViewPager;
    private LinearLayout ll_all,ll_tz, ll_zx;
    Context context;

    @Nullable
    @Override
   /* public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)*/
    public View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //
        context=this.getActivity();
        View view = inflater.inflate(R.layout.index_home,container,false);
        ll_all=(LinearLayout)view.findViewById(R.id.allcommunity);
        ll_tz=(LinearLayout) view.findViewById(R.id.tongzhi);
        ll_zx=(LinearLayout) view.findViewById(R.id.zhaoxin);
        mRollViewPager = (RollPagerView) view.findViewById(R.id.roll_view_pager);
        ll_zx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                changeFrament(new IndexZhaoxinFragment(), bundle, "indexZhaoxinFragment");
            }
        });


        ll_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                changeFrament(new IndexCommunityFragment(), bundle, "indexCommunityFragment");
            }
        });


        mRollViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                changeFrament(new IndexZhaoxinFragment(), bundle, "indexZhaoxinFragment");
            }
        });


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

    private class TestNormalAdapter extends StaticPagerAdapter {

        private int[] imgs = {
                R.drawable.dot_no,
                R.drawable.dot_yes,
                R.drawable.dot_no,
                R.drawable.dot_yes,
        };

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }
        @Override
        public int getCount() {
            return imgs.length;
        }
    }


}
