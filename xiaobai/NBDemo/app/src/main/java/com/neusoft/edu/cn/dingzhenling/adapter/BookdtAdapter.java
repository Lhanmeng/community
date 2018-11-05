package com.neusoft.edu.cn.dingzhenling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.bean.Bookdt;
import com.neusoft.edu.cn.dingzhenling.bean.Bookgz;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/23 0023.
 */
public class BookdtAdapter extends RecyclerView.Adapter<BookdtAdapter.MyViewHolder> implements View.OnClickListener
{

    public static final String TAG = "BookdtAdapter";
    List<Bookdt> mDatas = new ArrayList<>();
    Context context;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public BookdtAdapter(Context context, List<Bookdt> list)
    {
        this.context=context;
        this.mDatas=list;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookdt_card_view, parent, false);
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

        Bookdt bookdt = mDatas.get(position);
        holder.gz_content.setText(bookdt.getContent());
        holder.itemView.setTag(bookdt.getObjectId());
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

        TextView gz_content;
        public MyViewHolder(View view)
        {
            super(view);
            gz_content = (TextView) view.findViewById(R.id.dt_content);
        }
    }
}

