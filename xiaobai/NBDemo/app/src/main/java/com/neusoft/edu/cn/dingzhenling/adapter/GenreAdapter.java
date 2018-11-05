package com.neusoft.edu.cn.dingzhenling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.bean.Genre;
import java.util.List;



public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.MyViewHolder> implements View.OnClickListener
{
    List<Genre> mDatas;
    Context context;
    public GenreAdapter(Context context, List<Genre> list)
    {
        this.context=context;
        this.mDatas=list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_card_view, parent, false);
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
        holder.genre_name.setText(mDatas.get(position).getGenrename());
        holder.itemView.setTag(mDatas.get(position).getObjectId());
    }

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

        TextView genre_name;
        public MyViewHolder(View view)
        {
            super(view);

            genre_name = (TextView) view.findViewById(R.id.genre_name);


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
