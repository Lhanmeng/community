package com.neusoft.edu.cn.dingzhenling.activity;

/**
 * Created by Administrator on 2017/3/23 0023.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.adapter.BookgzAdapter;
import com.neusoft.edu.cn.dingzhenling.adapter.IssueAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

import com.neusoft.edu.cn.dingzhenling.bean.Bookdt;
import com.neusoft.edu.cn.dingzhenling.bean.Bookgenre;
import com.neusoft.edu.cn.dingzhenling.bean.Bookgz;
import com.neusoft.edu.cn.dingzhenling.bean.Issue;

/**
 * Created by Administrator on 2017/3/19 0019.
 */
public class BookgzActivity extends AppCompatActivity  implements View.OnClickListener {
    public static final String TAG = "BookgzActivity";
    public Context context;
    String bookgenre_id;
    private TextView bookgenre_name;
    //显示对应问题列表
    private RecyclerView mRecyclerView;
    private List<Bookgz> mDatas;
    private  BookgzAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this.getApplication();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookgz_activity);
        bookgenre_name=(TextView)findViewById(R.id.bookgenre_name);
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getApplication()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        initRecyclerView();
        Intent _getIntent = this.getIntent();
        bookgenre_id = _getIntent.getStringExtra("bookgenre_id");
        showBook();
        showBookgz();
        showBookdt();
    }

    private void showBookdt() {
        BmobQuery<Bookdt> query=new BmobQuery<>();
//        query.addWhereEqualTo("bookgz",new BmobPointer(bookgz));
    }

    public void initRecyclerView() {
        mAdapter = new BookgzAdapter(this);
        mAdapter.setOnItemClickListener(new BookgzAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Intent intent = new Intent();
                intent.setClass(BookgzActivity.this,BookdtActivity.class);
                intent.putExtra("bookgz_id", data);
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

    }

    //显示问题
    public void showBookgz() {
        mDatas = new ArrayList<Bookgz>();
        BmobQuery<Bookgz> query = new BmobQuery<Bookgz>();
        //根据类别id获取问题
        Bookgenre bookgenre = new Bookgenre();
        bookgenre.setObjectId(bookgenre_id);
        //根据bookgz表中字段查询
        query.addWhereEqualTo("genere", bookgenre);
        query.include("bookgenre");
        //按照时间降序
        query.order("-createdAt");
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects(new FindListener<Bookgz>() {
            @Override
            public void done(List<Bookgz> list, BmobException e) {
                if (e == null) {

                    Log.e(TAG ,"listSize:" +  list.size());
                    Log.e(TAG, list.toString());
                    mAdapter.setData(list);

                }
            }
        });
    }

    // 显示类别
    public void showBook() {

        BmobQuery<Bookgenre> bmobQuery = new BmobQuery<Bookgenre>();
        bmobQuery.getObject(bookgenre_id, new QueryListener<Bookgenre>() {
            @Override
            public void done(Bookgenre object, BmobException e) {
                if (e == null) {
                    bookgenre_name.setText(object.getBook_name());


                    bookgenre_id = getIntent().getExtras().getString("bookgenre_id");
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
