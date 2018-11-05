package com.neusoft.edu.cn.dingzhenling.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.adapter.IssueAdapter;
import com.neusoft.edu.cn.dingzhenling.bean.Issue;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/4/4 0004.
 */
public class SearchActivity  extends AppCompatActivity implements View.OnClickListener{
  private RecyclerView mRecyclerView;
    //List<Issue> 是泛型
  /*  private List<Issue> mDatas;*/
    private IssueAdapter mAdapter;
    public Context context;
    private EditText search_name_et;
    private ImageButton do_search_btn;

    private boolean mIsLoading = false;
    private List<Issue> mSearchData;
    //onCreate()函数是在activity初始化的时候调用的，都是绑定界面布局和初始化界面
    // onsaveInsanceState方法是用来保存Activity的状态的。当一个Activity在生命周期结束前，会调用该方法保存状态。
    //bundle Bundle类用作携带数据  Bundle主要用于传递数据;它保存的数据,是以key-value(键值对)的形式存在的（value，1）
    protected void onCreate(Bundle savedInstanceState) {
      //  getApplication():andorid 开发中共享全局数据;
        context = this.getApplication();
        // super父类 onCreate消息响应函数
        super.onCreate(savedInstanceState);
        initData();
        init(this);
//setAdapter 添加数据 本文对应的是RecyclerView添加数据
        mRecyclerView.setAdapter(mAdapter);
       mAdapter.setOnItemClickListener(new IssueAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Intent intent=new Intent(getApplication(), IssuedetaiActivity.class);
                //putExtra 传递数据
                intent.putExtra("issue_id",data);
                //startActivity开始跳转
                startActivity(intent);
            }
        });

    }
//Content 上下文的意思
    protected void init(Context context) {
        //1、setContentView的作用是将View加载到根view之上，这样当显示view时，先显示根view，然后在显示子view，以此类推，最终将所有view显示出来。
        //2、setContentView必须要放在findviewbyid之前，因为view在加载之前是无法引用的。
        //3、setContentView最本质的作用是为要显示的view分配内存。
        //R.layout 实自己在res下写的布局文件
        setContentView(R.layout.activity_search);
        //   ArrayList就是动态数组
        //ArrayList的使用 首先实例化一个 进行初始化
        mSearchData = new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getApplication()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        search_name_et = (EditText)findViewById(R.id.search_name_et);
        do_search_btn = (ImageButton) findViewById(R.id.do_search_btn);
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getApplication()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter=new IssueAdapter(SearchActivity.this.getApplication());
        do_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (search_name_et.getText() == null || search_name_et.getText().toString().equals("")) {
                    Toast.makeText(getApplication(), "查询为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                queryData(search_name_et.getText().toString().trim());
            }
        });

    }


    public void initData() {
        BmobQuery<Issue> query  = new BmobQuery<>();
        query.include("user");

        query.findObjects(new FindListener<Issue>() {
            @Override
            public void done(List<Issue> list, BmobException e) {

                if (e == null && list !=null && !list.isEmpty()){
                    mSearchData = list;
                /*    Log.e("tms", "done:--------------serach success " );*/
                } else {
                    Toast.makeText(SearchActivity.this,"未搜索到数据",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void queryData(String string) {

        if (TextUtils.isEmpty(string) && mIsLoading){
            return;
        }

        ArrayList<Issue> searchList = new ArrayList<>();
        if (!mSearchData.isEmpty()){
            mIsLoading = true;
            for (Issue issue : mSearchData){
                if (issue !=null && issue.getTitle() != null && issue.getTitle().contains(string)){
                    searchList.add(issue);
                 /*   Log.e("tms", "queryData: -----------find str" + issue.getTitle());*/
                }
            }
            mIsLoading = false;
            mAdapter.setData(searchList);
        }

    }

    @Override
    public void onClick(View v) {

    }
    public void clickBack(View v) {
        finish();
    }
}
  /*  public Context context;

    private RecyclerView mRecyclerView;
    private List<Issue> mDatas;
    private IssueAdapter mAdapter;
    private EditText search_name_et;
    private ImageButton do_search_btn,back;

    protected void onCreate(Bundle savedInstanceState) {
        context = this.getApplication();
        super.onCreate(savedInstanceState);
        init(this);

    }

    protected void init(Context context) {
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       *//* ImageButton comment_button=(ImageButton)findViewById(R.id.comment_button);*//*
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getApplication()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        search_name_et = (EditText)findViewById(R.id.search_name_et);
        do_search_btn = (ImageButton) findViewById(R.id.do_search_btn);
        *//*initRecyclerView();*//*
        do_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (search_name_et.getText() == null || search_name_et.getText().toString().equals("")) {
                    Toast.makeText(getApplication(), "查询为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                queryData();
            }
        });

    }

    private void queryData() {
        BmobQuery<Issue> query = new BmobQuery<>();
        query.addWhereEqualTo("title", search_name_et.getText().toString());
        query.findObjects(new FindListener<Issue>() {
            @Override
            public void done(List<Issue> object, BmobException e) {
                if (e == null) {
                    mDatas = object;
                    mAdapter=new IssueAdapter(SearchActivity.this.getApplication(),mDatas);
                    mAdapter.setOnItemClickListener(new IssueAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, String data) {
                            Intent intent=new Intent(SearchActivity.this,IssuedetaiActivity.class);
                            intent.putExtra("issue_id",data);
                            startActivity(intent);

                        }
                    });
                    mRecyclerView.setAdapter(mAdapter);
                    if(object.size()==0){
                        Toast.makeText(SearchActivity.this,"未搜索到数据",Toast.LENGTH_SHORT).show();
                    }
                }else{

                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }


    @Override
    public void onClick(View v) {

    }
    public void clickBack(View v) {
        finish();
    }*/

