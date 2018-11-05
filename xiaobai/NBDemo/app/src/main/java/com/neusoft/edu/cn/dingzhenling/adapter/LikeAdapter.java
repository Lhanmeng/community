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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.bean.Issue;
import com.neusoft.edu.cn.dingzhenling.bean.Like;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/4/6 0006.
 */
public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.MyViewHolder> implements View.OnClickListener {
    public static final String TAG = "IssueAdapter";
    List<Issue> mDatas = new ArrayList<>();
    List<String> Likeid;
    Context context;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

 /*   public CollectAdapter(Context context, List<Job> list,List<String> Likeid)
    {
        this.context=context;
        this.mDatas=list;
        this.Likeid=Likeid;

    }*/
    public LikeAdapter(Context context, List<Issue> list,List<String> Likeid)
    {
        this.context=context;
        this.mDatas=list;
        this.Likeid=Likeid;
    }

    public LikeAdapter(Context context) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.like_card_view, parent, false);
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
      /*  final Issue like = mDatas.get(position);*/
   /*     holder.jobname.setText(mDatas.get(position).getJobname());*/
      /*  holder.username.setText(mDatas.get(position).getUser().getUsername());*/
        Log.i(TAG, "onBindViewHolder: user:" + mDatas.get(position).getUser());
        Issue like = mDatas.get(position);
        if (like.getUser().getTouxiang() != null) {
            Glide.with(context).load(like.getUser().getTouxiang().getFileUrl()).into(holder.userImage);
        }

        /*holder.user.setText(mDatas.get(position).getUser().getUsername());*/
        holder.username.setText(like.getUser().getUsername());
        holder.title.setText(like.getTitle());
        holder.content.setText(like.getContent());
        holder.itemView.setTag(like.getObjectId());

        //弹窗
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                //    设置Content来显示一个信息
                builder.setMessage("确定删除吗？");
                //    设置一个PositiveButton
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Bmob删除
                        canaelLike(position);
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
        TextView title,content,username;
        ImageButton delete;
        de.hdodenhof.circleimageview.CircleImageView userImage;
        /* ImageButton comment_button;*/
        public MyViewHolder(View view)
        {
            super(view);
          /*  Campaign_pic= (ImageView) view.findViewById(R.id.campaign_pic);*/

            title = (TextView) view.findViewById(R.id.title);
            content=(TextView)view.findViewById(R.id.content);
            username=(TextView) view.findViewById(R.id.username);
            delete=(ImageButton)view.findViewById(R.id.delete);
            userImage = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.user_img);

           /* ImageButton comment_button=(ImageButton)view.findViewById(R.id.comment_button);*/
          /*  Campaign_ShortInfo = (TextView) view.findViewById(R.id.campaign_info);*/
        }
    }


    //取消收藏
    private void canaelLike(final int position){

        Like p2 = new Like();

        p2.setObjectId(Likeid.get(position));

        p2.delete(new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    mDatas.remove(position);
                    LikeAdapter.this.notifyDataSetChanged();
                    Toast.makeText(context, "删除成功", Toast.LENGTH_LONG).show();
                }else{

                    Toast.makeText(context, "删除失败", Toast.LENGTH_LONG).show();
                }
            }

        });

    }
}