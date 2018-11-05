package com.neusoft.edu.cn.dingzhenling.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.neusoft.edu.cn.dingzhenling.bean.Issue;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

import com.neusoft.edu.cn.dingzhenling.R;

import com.neusoft.edu.cn.dingzhenling.bean.MyUser;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.squareup.picasso.Picasso;


/**
 * Created by wang on 2016/12/9.
 */

public class CollectAdapter extends RecyclerView.Adapter<CollectAdapter.ViewHolder> {
    private static RecyclerView.Adapter Adapter;
    private static OnItemClickListener mOnItemClickListener;
    public static final String TAG = "CollectAdapter";
    public static final int SAVE_FAVOURITE = 2;
    private List<Issue> contentList;
    //try
/*    List<Issue> mDatas = new ArrayList<>();*/
    private Context context;
    private  BmobUser bmobUser;

    public CollectAdapter(Context context, List<Issue> list) {
        this.context = context;
        this.contentList = list;
    }

    //设置点击和长按事件
    public interface OnItemClickListener {
        void onItemClick(View view, int position); //设置点击事件

        void onItemLongClick(View view, int position); //设置长按事件
    }

    public static void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.like_card_view, parent, false);
        return new ViewHolder(itemView);
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final MyUser user = BmobUser.getCurrentUser(MyUser.class);
        /*String id=contentList.get(position).getUser().getObjectId();
        BmobQuery<MyUser> query=new BmobQuery<>();
        query.getObject(contentList.get(position).getUser().getObjectId(), new QueryListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if(e==null){
                    if(myUser!=null){
                        Log.e("222222222",myUser.getUsername());
                        Log.e("11111111",myUser.getTouxiang().getFileUrl());
                        bmobUser = myUser;
                        holder.username.setText("发布者"+myUser.getUsername());
//                    Toast.makeText(context,"用户名是"+myUser.getUsername(),Toast.LENGTH_SHORT).show();

                        Picasso.with(context).load(myUser.getTouxiang().getFileUrl()).into(holder.userImage);
                    }
                }
            }
        });*/
        Issue issue = contentList.get(position);
        if (issue.getUser().getTouxiang() != null) {
            Glide.with(context).load(issue.getUser().getTouxiang().getFileUrl()).into(holder.userImage);
        }
        holder.username.setText("发布者:"+issue.getUser().getUsername());
        holder.title.setText("标题："+contentList.get(position).getTitle());
        holder.content.setText("内容："+contentList.get(position).getContent());

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
                        BmobRelation relation=new BmobRelation();
                        relation.remove(user);
                        Issue issue=new Issue();
                        issue.setCollect(relation);
                        issue.update(contentList.get(position).getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    Toast.makeText(context,"删除收藏成功",Toast.LENGTH_SHORT).show();

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

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() { //itemview是holder里的所有控件，可以单独设置比如ImageButton Button等
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() { //长按事件
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemLongClick(holder.itemView, position);
                    return false;
                }
            });
        }
    }



    @Override
    public int getItemCount() {
        return contentList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton delete;
        CircleImageView userImage;
        TextView title, content, username;
        LinearLayout collect1;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            content=(TextView)view.findViewById(R.id.content);
            username=(TextView) view.findViewById(R.id.username);
            delete=(ImageButton)view.findViewById(R.id.delete);
            userImage = (CircleImageView) view.findViewById(R.id.user_img);
        }
    }
}