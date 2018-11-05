package com.neusoft.edu.cn.dingzhenling.activity;

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
import com.neusoft.edu.cn.dingzhenling.adapter.CollectAdapter;
import com.neusoft.edu.cn.dingzhenling.adapter.IssueAdapter;
import com.neusoft.edu.cn.dingzhenling.adapter.LikeAdapter;
import com.neusoft.edu.cn.dingzhenling.bean.Issue;
import com.neusoft.edu.cn.dingzhenling.bean.Like;
import com.neusoft.edu.cn.dingzhenling.bean.MyUser;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/3/12 0012.
 */
public class CollectionActivity extends AppCompatActivity {
    private static final String TAG = "CollectionActivity";
    public Context context;
    //显示对应问题列表
    private RecyclerView mRecyclerView;
    public CollectAdapter collectAdapter;
    private List<Issue> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this.getApplication();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getApplication()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        initData();
    }

    private void initData() {
        MyUser user= BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<Issue> query=new BmobQuery<>();
        query.include("user");
        query.addWhereEqualTo("collect",new BmobPointer(user));
        query.findObjects(new FindListener<Issue>() {
            @Override
            public void done(List<Issue> list, BmobException e) {
                if(e==null){
                    mDatas = list;
                    collectAdapter=new CollectAdapter(CollectionActivity.this,mDatas);
                    CollectAdapter.setOnItemClickListener(new CollectAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                        Intent intent=new Intent(CollectionActivity.this,IssuedetaiActivity.class);
                        intent.putExtra("issue_id",mDatas.get(position).getObjectId());
                        startActivity(intent);
                        }

                        @Override
                        public void onItemLongClick(View view, int position) {

                        }
                    });
//                    collectAdapter.OnItemClickListener(new CollectAdapter.OnRecyclerViewItemClickListener(){
//                    @Override
//                    public void onItemClick(View view, String data) {
//                        Intent intent=new Intent(CollectionActivity.this,IssuedetaiActivity.class);
//                        intent.putExtra("issue_id",data);
//                        startActivity(intent);
//                    }
//                });

                    mRecyclerView.setAdapter(collectAdapter);
                    initData();
                }
            }
        });
    }

    public void clickBack(View v) {
        finish();
    }
}

