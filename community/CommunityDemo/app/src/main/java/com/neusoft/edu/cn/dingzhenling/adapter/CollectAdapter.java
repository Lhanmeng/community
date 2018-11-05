package com.neusoft.edu.cn.dingzhenling.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.bean.Collect;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2016/11/9 0009.
 */
public class CollectAdapter extends RecyclerView.Adapter<CollectAdapter.MyViewHolder>implements  View.OnClickListener
{
    List<Collect> mDatas;
    Context context;
    public CollectAdapter adapter;
    public interface OnItemClickListener {
       /* public void onItemClick(Button button, int tag);*/
    }

    private OnItemClickListener mListener;

    //写一个设置接口监听的方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }



    public CollectAdapter(Context context, List<Collect> list)
    {
        this.context=context;
        this.mDatas=list;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collect, parent, false);
        view.setOnClickListener(this);
        MyViewHolder holder = new MyViewHolder(view);
      //  MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }
    /*
    3.数据绑定
    */
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        final Collect collect = mDatas.get(position);
        holder.Campaign_name.setText(mDatas.get(position).getCampaignname());
        holder.Campaign_ShortInfo.setText(mDatas.get(position).getShortIntro());
        holder.Group_name.setText(mDatas.get(position).getGname());
        holder.itemView.setTag(mDatas.get(position).getObjectId());
        Picasso.with(context).load(mDatas.get(position).getPic()).into(holder.Campaign_pic);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("删除后需重新收藏！！！");
                //    设置Content来显示一个信息
                builder.setMessage("确定删除吗？");
                //    设置一个PositiveButton
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Bmob删除
                        collect.delete(new UpdateListener() {
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

    }

   /*  holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                collect .delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e==null){
                            Toast.makeText(context,"已删除",Toast.LENGTH_LONG).show();
                            mDatas.remove(position);
                            notifyDataSetChanged();

                        }else {
                            Toast.makeText(context,"删除失败",Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });*/

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
        Button delete;
        public MyViewHolder(View view)
        {
            super(view);
            delete=(Button)view.findViewById(R.id.delete);
            Campaign_pic= (ImageView) view.findViewById(R.id.campaign_pic);
            Campaign_name = (TextView) view.findViewById(R.id.campaign_name);
            Group_name = (TextView) view.findViewById(R.id.Groups_name);
            Campaign_ShortInfo = (TextView) view.findViewById(R.id.campaign_info);

        }
    }
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , String data);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

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