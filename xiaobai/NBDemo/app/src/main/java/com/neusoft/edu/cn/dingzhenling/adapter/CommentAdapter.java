package com.neusoft.edu.cn.dingzhenling.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.bean.Comment;
import com.neusoft.edu.cn.dingzhenling.bean.Genre;
import com.neusoft.edu.cn.dingzhenling.bean.Issue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/7 0007.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> implements View.OnClickListener {
    public static final String TAG = "CommentAdapter";
    List<Comment> mDatas = new ArrayList<>();
    Context context;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public CommentAdapter(Context context, List<Comment> list)
    {
        this.context=context;
        this.mDatas=list;
    }

    public CommentAdapter(Context context) {
        this.context = context;

    }

    public void setData(List<Comment> data) {
        this.mDatas.clear();
        this.mDatas.addAll(data);
        this.notifyDataSetChanged();

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
   /* public void onBindViewHolder(MyViewHolder holder, int position)*/
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
      /*  holder.username.setText(mDatas.get(position).getUser().getUsername());*/
        Log.i(TAG, "onBindViewHolder: user:" + mDatas.get(position).getUser());
        Comment comment = mDatas.get(position);
        if (comment.getUser().getTouxiang() != null) {
            Glide.with(context).load(comment.getUser().getTouxiang().getFileUrl()).into(holder.userImage);
        }

        holder.user.setText(comment.getUser().getUsername());
        holder.content.setText(comment.getContent());
        holder.time.setText(comment.getCreatedAt());
        holder.itemView.setTag(comment.getObjectId());

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
        /*ImageView userimg;*/
        TextView content,time,user;
        de.hdodenhof.circleimageview.CircleImageView userImage;
        /* ImageButton comment_button;*/
        public MyViewHolder(View view)
        {
            super(view);
          /*  Campaign_pic= (ImageView) view.findViewById(R.id.campaign_pic);*/
            content = (TextView) view.findViewById(R.id.content);
            time = (TextView) view.findViewById(R.id.time);
            user = (TextView) view.findViewById(R.id.cuser);
            userImage = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.user_img);

           /* ImageButton comment_button=(ImageButton)view.findViewById(R.id.comment_button);*/
          /*  Campaign_ShortInfo = (TextView) view.findViewById(R.id.campaign_info);*/
        }
    }

}
