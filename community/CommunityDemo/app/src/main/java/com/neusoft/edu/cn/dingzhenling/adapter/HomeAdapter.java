package com.neusoft.edu.cn.dingzhenling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.bean.Groups;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by liningning on 2016/10/26.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> implements View.OnClickListener
{
    List<Groups> mDatas;
    Context context;
    public HomeAdapter(Context context, List<Groups> list)
    {
        this.context=context;
        this.mDatas=list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.index_communitylistcard_view, parent, false);
        view.setOnClickListener(this);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    /*
    3.数据绑定
    */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        holder.groups_name.setText(mDatas.get(position).getGroupname());
        holder.groups_ShortInfo.setText(mDatas.get(position).getShortInfo());
        holder.itemView.setTag(mDatas.get(position).getObjectId());
        Picasso.with(context).load(mDatas.get(position).getLogo()).into(holder.groups_logo);
      /*  Picasso.with(IndexCommunityFragment.this.getActivity()).load(mDatas.get(position).getLogo()).into(holder.groups_logo);*/
    /*    holder.itemView.setTag(mDatas.get(position).getObjectId());
        Picasso.with(context).load(mDatas.get(position).getNews_thumb()).into(holder.news_thumb);*/
    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }
    /*
    2.自定义Holder类，与item中的内容一一对应
    */
    class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView groups_logo;
        TextView groups_name,groups_ShortInfo;

        public MyViewHolder(View view)
        {
            super(view);
            groups_logo= (ImageView) view.findViewById(R.id.Groups_logo);
            groups_name = (TextView) view.findViewById(R.id.Groups_name);
            groups_ShortInfo = (TextView) view.findViewById(R.id.Groups_info);
        }
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v,(String)v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
