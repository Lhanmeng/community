package com.neusoft.edu.cn.dingzhenling.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.adapter.BookgenreAdapter;
import com.neusoft.edu.cn.dingzhenling.bean.Bookgenre;
import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/3/12 0012.
 */
public class BookActivity extends AppCompatActivity   implements View.OnClickListener{
    public static final String BOOKGENRE_ID = "bookgenreId";

    private RecyclerView mRecyclerView;
    private List<Bookgenre> mDatas;
    private BookgenreAdapter mAdapter;
    String bookgenre_id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getApplication()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        initData();

    }

    /*
1.初始化数据集合
        */
    public void initData() {
        mDatas = new ArrayList<Bookgenre>();
        BmobQuery<Bookgenre> query = new BmobQuery<Bookgenre>();
        //按照时间降序
        query.order("-createdAt");
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects(new FindListener<Bookgenre>() {
            @Override
            public void done(List<Bookgenre> list, BmobException e) {
                if (e == null) {
                    mDatas = list;
                    mAdapter = new BookgenreAdapter(BookActivity.this.getApplication(), mDatas);
                    mAdapter.setOnItemClickListener(new BookgenreAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, String data) {

                            Intent intent = new Intent();
                            intent.setClass(BookActivity.this,BookgzActivity.class);
                            intent.putExtra("bookgenre_id", data);
                            startActivity(intent);

                        }
                    });
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        });
    }
    @Override
    public void onClick(View v) {

    }
    private void back() {
        finish();
    }
    public void clickBack(View v) {
        finish();
    }

}
