package com.neusoft.edu.cn.dingzhenling.activity;

/**
 * Created by Administrator on 2017/3/23 0023.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.neusoft.edu.cn.dingzhenling.R;



import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

import com.neusoft.edu.cn.dingzhenling.adapter.BookdtAdapter;
import com.neusoft.edu.cn.dingzhenling.bean.Bookdt;
import com.neusoft.edu.cn.dingzhenling.bean.Bookgenre;
import com.neusoft.edu.cn.dingzhenling.bean.Bookgz;
import com.neusoft.edu.cn.dingzhenling.bean.Issue;

/**
 * Created by Administrator on 2017/3/19 0019.
 */
public class BookdtActivity extends AppCompatActivity  implements View.OnClickListener {
    public static final String TAG = "BookdtActivity";
    public Context context;
    String bookgz_id;
    private TextView bookgz_name,content;
    //显示对应问题列表
    private RecyclerView mRecyclerView;
    private List<Bookdt> mDatas;
    private BookdtAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this.getApplication();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookdt_activity);
        Intent _getIntent = this.getIntent();
        bookgz_id = _getIntent.getStringExtra("bookgz_id");
        bookgz_name=(TextView)findViewById(R.id.bookgz_name);
        content=(TextView)findViewById(R.id.content);
        mRecyclerView = (RecyclerView) findViewById(R.id.id_bookdt);
        showBookgz();
        showBookdt();
    }

    //显示故障问题解决
    public void showBookdt() {
        mDatas = new ArrayList<Bookdt>();
        BmobQuery<Bookdt> query = new BmobQuery<Bookdt>();
        //根据类别id获取
        Bookgz bookgz= new Bookgz();
        bookgz.setObjectId(bookgz_id);
        //根据bookdt表中字段查询
//        query.addWhereEqualTo("bookgz", bookgz);
        query.addWhereEqualTo("bookgz",new BmobPointer(bookgz));
//        query.include("bookgz");
        //按照时间降序
        query.order("-createdAt");
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects(new FindListener<Bookdt>() {
            @Override
            public void done(List<Bookdt> list, BmobException e) {
                if (e == null) {
//                    Log.e("222222222222222",bookgz_id);
                    Log.e(TAG ,"listSize:" +  list.size());
                    Log.e(TAG, list.get(0).getObjectId());
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(BookdtActivity.this));
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    mAdapter=new BookdtAdapter(BookdtActivity.this,list);
//                    mAdapter.setData(list);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(new BookdtAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, String data) {

                        }
                    });



                }
            }
        });
    }

    // 显示类别
    public void showBookgz() {

        BmobQuery<Bookgz> bmobQuery = new BmobQuery<Bookgz>();
        bmobQuery.getObject(bookgz_id, new QueryListener<Bookgz>() {
            @Override
            public void done(Bookgz object, BmobException e) {
                if (e == null) {
                    bookgz_name.setText(object.getName());
                    content.setText(object.getContent());

                    bookgz_id = getIntent().getExtras().getString("bookgz_id");
                } else {
                    getToast("查询失败：" + e.getMessage());
                }
            }
        });
    }


    public void getToast(String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
    }
    public void clickBack(View v) {
        finish();
    }
}