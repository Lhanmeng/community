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
import com.neusoft.edu.cn.dingzhenling.adapter.IssueAdapter;

import java.util.ArrayList;
import java.util.List;
;

import com.neusoft.edu.cn.dingzhenling.bean.Genre;
import com.neusoft.edu.cn.dingzhenling.bean.Issue;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Administrator on 2017/3/19 0019.
 */
public class IssueActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "IssueActivity";
    public Context context;
    /* ImageButton comment_button;*/
    String genre_id;
    /*PrefManager prefManager;*/
    //显示对应问题列表
    private RecyclerView mRecyclerView;
    private List<Issue> mDatas;
    private IssueAdapter mAdapter;
    private TextView genrename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this.getApplication();
        super.onCreate(savedInstanceState);
        Intent _getIntent = this.getIntent();
        genre_id = _getIntent.getStringExtra("genre_id");
        init(this);

        showIssue();
        showGenre();

    }

    protected void init(Context context) {
        setContentView(R.layout.activity_issue);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getApplication()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        initRecyclerView();
        genrename = (TextView) findViewById(R.id.genrename);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IssueActivity.this, ReleaseActivity.class);
                startActivity(intent);
            }

        });

    }

    public void initRecyclerView() {
        mAdapter = new IssueAdapter(this);
        mAdapter.setOnItemClickListener(new IssueAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Intent intent = new Intent();
                intent.setClass(IssueActivity.this,IssuedetaiActivity.class);
                intent.putExtra("issue_id", data);
                //  this.startActivity(intent);
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

    }

    //显示问题
    public void showIssue() {
        mDatas = new ArrayList<Issue>();
        BmobQuery<Issue> query = new BmobQuery<Issue>();
        //根据类别id获取问题
        Genre genre = new Genre();
        genre.setObjectId(genre_id);
        query.addWhereEqualTo("genre", genre);
        query.include("genre,user");
        //按照时间降序
        query.order("-createdAt");
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects(new FindListener<Issue>() {
            @Override
            public void done(List<Issue> list, BmobException e) {
                if (e == null) {
                   Log.e(TAG ,"listSize:" +  list.size());
                    Log.e(TAG, list.toString());
                    mAdapter.setData(list);
                }
            }
        });
    }

    // 显示类别
    public void showGenre() {
        BmobQuery<Genre> bmobQuery = new BmobQuery<Genre>();
        bmobQuery.getObject(genre_id, new QueryListener<Genre>() {
            @Override
            public void done(Genre object, BmobException e) {
                if (e == null) {
                    Log.e("object", object.getGenrename());
                    genrename.setText(object.getGenrename());
                    genre_id = getIntent().getExtras().getString("genre_id");
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


