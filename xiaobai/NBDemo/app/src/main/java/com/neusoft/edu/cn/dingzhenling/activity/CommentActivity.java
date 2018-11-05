package com.neusoft.edu.cn.dingzhenling.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.adapter.CommentAdapter;
import com.neusoft.edu.cn.dingzhenling.adapter.IssueAdapter;
import com.neusoft.edu.cn.dingzhenling.bean.Comment;
import com.neusoft.edu.cn.dingzhenling.bean.Issue;
import com.neusoft.edu.cn.dingzhenling.bean.MyUser;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/11/8 0008.
 */
public class CommentActivity  extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    public Context context;
    private Button btn_comment;
    private EditText et_comment;
    Issue issue;
    String issue_id;
    Intent intent = this.getIntent();
    CommentAdapter mAdapter;
    List<Comment> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_comment);
        mRecyclerView=(RecyclerView)findViewById(R.id.rv_comments);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //评论
        btn_comment=(Button)findViewById(R.id.btn_comment);
        et_comment=(EditText)findViewById(R.id.et_comment);
        issue=new Issue();

        //  user=(User)intent.getSerializableExtra("user");
        initData();

    }

    public void initData() {;

        showComments();
        initEvents();
    }
    public void showComments() {
      /*  results = new ArrayList<Comment>();*/
        results= new ArrayList<Comment>();
        BmobQuery<Comment> query = new BmobQuery<Comment>();
        //根据社团id获取活动
        Issue issue = new Issue();
        issue.setObjectId(issue_id);
        query.addWhereEqualTo("issue", issue);
        query.include("issue");
        query.include("user");
        //按照时间降序
        query.order("-createdAt");
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if (e == null) {
                    results = list;
                    mAdapter = new CommentAdapter(CommentActivity.this.getApplicationContext(), results);
                    mAdapter.setOnItemClickListener(new CommentAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, String data) {
                           /* Bundle bundle = new Bundle();
                            bundle.putString("community_id", data);
                            changeFrament(new CommunityDetail(), bundle, "communityDetail");*/
                        }
                    });
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        });
    }

    public void initEvents() {
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUser user = BmobUser.getCurrentUser(MyUser.class);
                final Comment comment = new Comment();
                comment.setUser((MyUser) user);
                //  comment.setUser(user);
                Issue issue = new Issue();
                issue.setObjectId(issue_id);
                comment.setIssue(issue);
                comment.setContent(et_comment.getText().toString());
                //发表评论
                comment.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId,BmobException e) {
                        if(e==null){
                            getToast("评论成功");
                            results.add(0,comment);
                            // mAdapter=new CommentAdapter(CommentActivity.this.getActivity(),results);
                            mRecyclerView.setAdapter(mAdapter);
                        }else{
                            getToast("评论失败："+e.getMessage());
                        }
                    }
                });
            }
        });
    }

    public void getToast(String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public void getNetError() {
        getToast("亲~网络连接失败！");
    }
}


