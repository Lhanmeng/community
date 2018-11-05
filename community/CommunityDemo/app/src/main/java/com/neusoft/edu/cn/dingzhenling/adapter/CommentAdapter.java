package com.neusoft.edu.cn.dingzhenling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.bean.Comment;

import java.util.List;


/**
 * Created by Administrator on 2016/11/7 0007.
 */
public class CommentAdapter  extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> implements View.OnClickListener {
    public static final String TAG = "CommentAdapter";
    List<Comment> mDatas;
    Context context;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public CommentAdapter(Context context, List<Comment> list)
    {
        this.context=context;
        this.mDatas=list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_card_view, parent, false);
        view.setOnClickListener(this);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }
    /*
    3.数据绑定
    */
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        holder.content.setText(mDatas.get(position).getContent());
        holder.time.setText(mDatas.get(position).getCreatedAt().toString());
        holder.user.setText(mDatas.get(position).getUser().getUsername());
        Log.i(TAG, "onBindViewHolder: user:" + mDatas.get(position).getUser());
    }
    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }

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
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }
    /*
    2.自定义Holder类，与item中的内容一一对应
    */
    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView content,time,user;
        ImageView img_photo;
        public MyViewHolder(View view)
        {
            super(view);
            content = (TextView) view.findViewById(R.id.content);
            time = (TextView) view.findViewById(R.id.time);
            user = (TextView) view.findViewById(R.id.cuser);
           /* img_photo=(ImageView)view.findViewById(R.id.photo);*/
        }
    }
}
