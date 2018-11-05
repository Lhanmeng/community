package com.neusoft.edu.cn.dingzhenling.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.bean.Issue;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/23 0023.
 */
public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.MyViewHolder> implements View.OnClickListener
{
    public static final String TAG = "IssueAdapter";
    List<Issue> mDatas = new ArrayList<>();
    Context context;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public IssueAdapter(Context context, List<Issue> list)
    {
        this.context=context;
        this.mDatas=list;
    }

    public IssueAdapter(Context context) {
        this.context = context;

    }

    public void setData(List<Issue> data) {
        this.mDatas.clear();
        this.mDatas.addAll(data);
        this.notifyDataSetChanged();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.issue_card_view, parent, false);
        view.setOnClickListener(this);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }
    /*
    3.数据绑定
    */
    @Override
   /* public void onBindViewHolder(MyViewHolder holder, int position)*/
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
      /*  Log.i(TAG, "onBindViewHolder: user:" + mDatas.get(position).getUser());*/
        Issue issue = mDatas.get(position);
        if (issue.getUser().getTouxiang() != null) {
            Glide.with(context).load(issue.getUser().getTouxiang().getFileUrl()).into(holder.userImage);
        }

        holder.username.setText(issue.getUser().getUsername());
        holder.title.setText("标题:"+issue.getTitle());
        holder.content.setText("内容:"+issue.getContent());
        holder.time.setText(issue.getCreatedAt());
        holder.beizhu.setText(issue.getBeizhu());
        holder.itemView.setTag(issue.getObjectId());
        if(issue.getImage()!=null){
            Picasso.with(context).load(issue.getImage().getFileUrl()).into(holder.image);
        }else{
            holder.image.setVisibility(View.GONE);
        }


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
        TextView title,content,username,time,beizhu;
        de.hdodenhof.circleimageview.CircleImageView userImage;
        ImageView image;
       /* ImageButton comment_button;*/
        public MyViewHolder(View view)
        {
            super(view);
          /*  Campaign_pic= (ImageView) view.findViewById(R.id.campaign_pic);*/

            title = (TextView) view.findViewById(R.id.title);
            content=(TextView)view.findViewById(R.id.content);
            time=(TextView)view.findViewById(R.id.time);
            beizhu=(TextView)view.findViewById(R.id.beizhu);
            username=(TextView) view.findViewById(R.id.username);
            userImage = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.UserImage);
            image=(ImageView)view.findViewById(R.id.image);
           /* ImageButton comment_button=(ImageButton)view.findViewById(R.id.comment_button);*/
          /*  Campaign_ShortInfo = (TextView) view.findViewById(R.id.campaign_info);*/
        }
    }
}

