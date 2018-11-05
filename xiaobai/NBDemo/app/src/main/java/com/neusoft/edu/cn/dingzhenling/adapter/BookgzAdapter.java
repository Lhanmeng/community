package com.neusoft.edu.cn.dingzhenling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.bean.Bookgz;
import com.neusoft.edu.cn.dingzhenling.bean.Issue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/23 0023.
 */
public class BookgzAdapter extends RecyclerView.Adapter<BookgzAdapter.MyViewHolder> implements View.OnClickListener
{
    public static final String TAG = "BookgzAdapter";
    List<Bookgz> mDatas = new ArrayList<>();
    Context context;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public BookgzAdapter(Context context, List<Bookgz> list)
    {
        this.context=context;
        this.mDatas=list;
    }

    public BookgzAdapter(Context context) {
        this.context = context;

    }

    public void setData(List<Bookgz> data) {
        this.mDatas.clear();
        this.mDatas.addAll(data);
        this.notifyDataSetChanged();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookgz_card_view, parent, false);
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

        Bookgz bookgz = mDatas.get(position);



        holder.Book_name.setText(bookgz.getName());

        holder.itemView.setTag(bookgz.getObjectId());



     /*   Log.i(TAG, "onBindViewHolder: user:" + mDatas.get(position).getUser());*/


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

        TextView Book_name;
        public MyViewHolder(View view)
        {
            super(view);
            Book_name = (TextView) view.findViewById(R.id.book_name);

           /* ImageButton comment_button=(ImageButton)view.findViewById(R.id.comment_button);*/
          /*  Campaign_ShortInfo = (TextView) view.findViewById(R.id.campaign_info);*/
        }
    }

}

   /* List<Bookgz> mDatas;
    Context context;
    public BookgzAdapter(Context context, List<Bookgz> list)
    {
        this.context=context;
        this.mDatas=list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookgz_card_view, parent, false);
        view.setOnClickListener(this);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }
    *//*
    3.数据绑定
    *//*
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        holder.Book_name.setText(mDatas.get(position).getName());
       *//* holder.Group_content.setText(mDatas.get(position).getContent());
        holder.title.setText(mDatas.get(position).getTitle());*//*
         holder.itemView.setTag(mDatas.get(position).getObjectId());
      *//* Picasso.with(context).load(mDatas.get(position).get()).into(holder.userimg);*//*
    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }
    *//*
    2.自定义Holder类，与item中的内容一一对应
    *//*
    class MyViewHolder extends RecyclerView.ViewHolder
    {
        *//*ImageView userimg;*//*
        TextView Book_name;
        public MyViewHolder(View view)
        {
            super(view);

            Book_name = (TextView) view.findViewById(R.id.book_name);

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

*/