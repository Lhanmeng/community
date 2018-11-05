package com.neusoft.edu.cn.dingzhenling.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.bean.Bookgenre;
import com.neusoft.edu.cn.dingzhenling.bean.Genre;
import java.util.List;



public class BookgenreAdapter extends RecyclerView.Adapter<BookgenreAdapter.MyViewHolder> implements View.OnClickListener
{
    List<Bookgenre> mDatas;
    Context context;
    public BookgenreAdapter(Context context, List<Bookgenre> list)
    {
        this.context=context;
        this.mDatas=list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_card_view, parent, false);
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
        holder.book_name.setText(mDatas.get(position).getBook_name());
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

        TextView book_name;
        public MyViewHolder(View view)
        {
            super(view);

            book_name = (TextView) view.findViewById(R.id.book_name);



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
