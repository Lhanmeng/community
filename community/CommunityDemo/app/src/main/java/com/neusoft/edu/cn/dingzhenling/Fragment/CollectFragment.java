package com.neusoft.edu.cn.dingzhenling.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.adapter.CollectAdapter;
import com.neusoft.edu.cn.dingzhenling.bean.Collect;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2016/11/9 0009.
 */
public class CollectFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private List<Collect> mDatas;
    private CollectAdapter mAdapter;
    private ImageButton ibn_back;

    @Override
    public View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_collect,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ibn_back=(ImageButton)view.findViewById(R.id.back);
        ibn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                PersonFragment personFragment= new   PersonFragment ();
                changeFrament(personFragment, bundle, "personFragment ");
            }
        });
      /* mAdapter.setOnItemClickListener(new CollectAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(Button button, int tag) {
               mDatas(new UpdateListener() {
                   @Override
                   public void done(BmobException e) {
                       if (e==null){
                           Toast.makeText(context,"已删除",Toast.LENGTH_LONG).show();
                           //  mDatas .clear();
                           mAdapter.notifyDataSetChanged();

                       }else {
                           Toast.makeText(context,"删除失败",Toast.LENGTH_LONG).show();
                       }

                   }
           }
       });*/
        return view;
    }
    /*
    1.初始化数据集合
            */
    public void initData()
    {
        mDatas = new ArrayList<Collect>();
        BmobQuery<Collect> query = new BmobQuery<Collect>();
        //按照时间降序
        query.order("-createdAt");
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects( new FindListener<Collect>() {
            @Override
            public void done(List<Collect> list, BmobException e) {
                if(e==null)
                {
                    mDatas=list;
                    mAdapter=new CollectAdapter(CollectFragment.this.getActivity(),mDatas);
                    mAdapter.setOnItemClickListener(new CollectAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, String data) {
                            Bundle bundle = new Bundle();
                            bundle.putString("campaign_id", data);
                            changeFrament(new CollectDetailFragment(),bundle,"");
                        }
                    });
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        });

    }

}
