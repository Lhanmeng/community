package com.neusoft.edu.cn.dingzhenling.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.bean.Comment;
import com.neusoft.edu.cn.dingzhenling.bean.Issue;
import com.neusoft.edu.cn.dingzhenling.bean.MyUser;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/3/6 0006.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> implements View.OnClickListener {
    public static final String TAG = "CommentAdapter";
    List<Comment> mDatas = new ArrayList<>();
    Context context;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public MessageAdapter(Context context, List<Comment> list)
    {
        this.context=context;
        this.mDatas=list;
    }

    public MessageAdapter(Context context) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_card_view, parent, false);
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
      /*  holder.username.setText(mDatas.get(position).getUser().getUsername());*/
        Log.i(TAG, "onBindViewHolder: user:" + mDatas.get(position).getUser());
        final Comment comment = mDatas.get(position);
        if (comment.getUser().getTouxiang() != null) {
            Glide.with(context).load(comment.getUser().getTouxiang().getFileUrl()).into(holder.userImage);
        }
        BmobQuery<Issue> query=new BmobQuery<>();
        query.getObject(comment.getIssue().getObjectId(), new QueryListener<Issue>() {
            @Override
            public void done(Issue issue, BmobException e) {
                if(e==null){
                    holder.issue_title.setText("问题标题:"+issue.getTitle());
                    BmobQuery<MyUser> query1=new BmobQuery<MyUser>();
                    query1.getObject(issue.getUser().getObjectId(), new QueryListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            if(e==null){
                                holder.issue_author.setText("发布者:"+myUser.getUsername());
                            }

                        }
                    });

            }
            }
        });

        holder.time.setText(comment.getCreatedAt());
        holder.content.setText("我的回答:"+comment.getContent());
        holder.itemView.setTag(comment.getObjectId());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
            /*    builder.setTitle("删除后需重新收藏！！！");*/
                //    设置Content来显示一个信息
                builder.setMessage("确定删除吗？");
                //    设置一个PositiveButton
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Bmob删除
                        comment.delete(new UpdateListener() {
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(context, "删除成功", Toast.LENGTH_LONG).show();
                                    mDatas.remove(position);
                                    notifyDataSetChanged();

                                }
                                else {
                                    Toast.makeText(context, "删除失败", Toast.LENGTH_LONG).show();
                                }

                            }

                        });
                    }
                });
                //    设置一个NegativeButton
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "取消删除" , Toast.LENGTH_SHORT).show();
                    }
                });
                //    显示出该对话框
                builder.show();
            }
        });

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
        TextView issue_title,issue_author,content,time;
        ImageButton delete;
        de.hdodenhof.circleimageview.CircleImageView userImage;
        /* ImageButton comment_button;*/
        public MyViewHolder(View view)
        {
            super(view);
            issue_title = (TextView) view.findViewById(R.id.issue_title);
            issue_author = (TextView) view.findViewById(R.id.issue_author);
            content = (TextView) view.findViewById(R.id.content);
            time = (TextView) view.findViewById(R.id.time);
            delete=(ImageButton)view.findViewById(R.id.delete);
            userImage = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.user_img);

        }
    }

}

