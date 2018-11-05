package com.neusoft.edu.cn.dingzhenling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.bean.Campaign;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by liningning on 2016/10/26.
 */
public class CampaignAdapter extends RecyclerView.Adapter<CampaignAdapter.MyViewHolder> implements View.OnClickListener
{
    List<Campaign> mDatas;
    Context context;
    public CampaignAdapter(Context context, List<Campaign> list)
    {
        this.context=context;
        this.mDatas=list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.campaign_card_view, parent, false);
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
        holder.Campaign_name.setText(mDatas.get(position).getCampaignname());
        holder.Campaign_ShortInfo.setText(mDatas.get(position).getShortIntro());
        holder.Group_name.setText(mDatas.get(position).getGname());
        holder.itemView.setTag(mDatas.get(position).getObjectId());
        Picasso.with(context).load(mDatas.get(position).getPic()).into(holder.Campaign_pic);
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
        ImageView Campaign_pic;
        TextView Campaign_name,Group_name,Campaign_ShortInfo;
        public MyViewHolder(View view)
        {
            super(view);
            Campaign_pic= (ImageView) view.findViewById(R.id.campaign_pic);
            Campaign_name = (TextView) view.findViewById(R.id.campaign_name);
            Group_name = (TextView) view.findViewById(R.id.Groups_name);
            Campaign_ShortInfo = (TextView) view.findViewById(R.id.campaign_info);

        }
    }
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据  getStringExtra
            mOnItemClickListener.onItemClick(v,(String)v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
