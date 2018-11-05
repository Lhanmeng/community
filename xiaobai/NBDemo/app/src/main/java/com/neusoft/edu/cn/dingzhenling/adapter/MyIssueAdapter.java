package com.neusoft.edu.cn.dingzhenling.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.bean.Issue;
import com.neusoft.edu.cn.dingzhenling.bean.Like;
import com.neusoft.edu.cn.dingzhenling.bean.MyUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;


/**
 * Created by Administrator on 2017/3/23 0023.
 */
public class MyIssueAdapter extends RecyclerView.Adapter<MyIssueAdapter.MyViewHolder> implements View.OnClickListener
{
    public static final String TAG = "IssueAdapter";
    List<Issue> mDatas = new ArrayList<>();
    Context context;
/*    List<String> Issueid;*/
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public MyIssueAdapter(Context context, List<Issue> list)
    {
        this.context=context;
        this.mDatas=list;

    }

    public MyIssueAdapter(Context context) {
        this.context = context;

    }

    public void setData(List<Issue> data) {
        this.mDatas.clear();
      /*  this.mDatas.addAll(data);*/
        this.notifyDataSetChanged();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.issuemy_card_view, parent, false);
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

        Log.i(TAG, "onBindViewHolder: user:" + mDatas.get(position).getUser());

        final Issue issue = mDatas.get(position);
        BmobQuery<MyUser> query=new BmobQuery<>();
        query.getObject(mDatas.get(position).getUser().getObjectId(), new QueryListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if(e==null){
                    if (myUser.getTouxiang() != null) {
                        Glide.with(context).load(myUser.getTouxiang().getFileUrl()).into(holder.userImage);

                    }

                    holder.username.setText(myUser.getUsername());
                }
            }
        });
        if(issue.getImage()!=null){
            Picasso.with(context).load(issue.getImage().getFileUrl()).into(holder.image);
        }else{
            holder.image.setVisibility(View.GONE);
        }

        holder.title.setText("标题："+issue.getTitle());
        holder.content.setText("内容："+issue.getContent());
        holder.time.setText(issue.getCreatedAt());
        if(issue.getBeizhu()!=null){
            holder.beizhu.setText("备注："+issue.getBeizhu());
        }else{
            holder.beizhu.setVisibility(View.GONE);
        }

        holder.itemView.setTag(issue.getObjectId());

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
                        issue.delete(new UpdateListener() {
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
      /*  AlertDialog imageDialog = new AlertDialog.Builder(Activity.this).setTitle("状态操作").setItems(items, listener).create();
        imageDialog.show();*/
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
        ImageView image;
        ImageButton delete;
        de.hdodenhof.circleimageview.CircleImageView userImage;
        /* ImageButton comment_button;*/
        public MyViewHolder(View view)
        {
            super(view);
          /*  Campaign_pic= (ImageView) view.findViewById(R.id.campaign_pic);*/

            title = (TextView) view.findViewById(R.id.title);
            content=(TextView)view.findViewById(R.id.content);
            image=(ImageView) view.findViewById(R.id.image);
            beizhu=(TextView)view.findViewById(R.id.beizhu);
            delete=(ImageButton)view.findViewById(R.id.delete);
            username=(TextView) view.findViewById(R.id.username);
            time=(TextView)view.findViewById(R.id.time);
            userImage = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.UserImage);

        }
    }

    }

