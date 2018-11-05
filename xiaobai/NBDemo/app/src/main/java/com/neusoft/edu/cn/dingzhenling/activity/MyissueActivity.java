package com.neusoft.edu.cn.dingzhenling.activity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.adapter.IssueAdapter;
import com.neusoft.edu.cn.dingzhenling.adapter.MyIssueAdapter;
import com.neusoft.edu.cn.dingzhenling.bean.Genre;
import com.neusoft.edu.cn.dingzhenling.bean.Issue;
import com.neusoft.edu.cn.dingzhenling.bean.MyUser;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Administrator on 2017/4/7 0007.
 */
public class MyissueActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "IssueActivity";
    public Context context;
    //显示对应问题列表
    private RecyclerView mRecyclerView;
    private List<Issue> mDatas;
    private MyIssueAdapter mAdapter;
    private TextView genrename;
    String user_id;
    BmobUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this.getApplication();
        super.onCreate(savedInstanceState);
        init(this);
        showIssue();
    }

    protected void init(Context context) {
        setContentView(R.layout.activity_myissue);
        user = BmobUser.getCurrentUser();
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getApplication()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        genrename = (TextView) findViewById(R.id.genrename);
    }



    public void showIssue() {
        mDatas = new ArrayList<Issue>();
        BmobQuery<Issue> query = new BmobQuery<Issue>();
        //根据当前用户名查询
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        query.addWhereEqualTo("user", user);
        query.order("-createdAt");
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects(new FindListener<Issue>() {
            @Override
            public void done(List<Issue> list, BmobException e) {
                if (e == null) {
                    mDatas = list;
                    //AlertDialog不能使用getApplicationContext()得到的context，而必须使用Activity
                    mAdapter = new MyIssueAdapter(MyissueActivity.this, mDatas);
                    mAdapter.setOnItemClickListener(new MyIssueAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, String data) {
                            Intent intent = new Intent();
                            intent.setClass(MyissueActivity.this,IssuedetaiActivity.class);
                            intent.putExtra("issue_id", data);
                            //  this.startActivity(intent);user_id
                            startActivity(intent);
                        }
                    });
                    mRecyclerView.setAdapter(mAdapter);

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



