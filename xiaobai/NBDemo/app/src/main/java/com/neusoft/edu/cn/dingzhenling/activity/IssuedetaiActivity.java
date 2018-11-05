package com.neusoft.edu.cn.dingzhenling.activity;

import android.app.FragmentManager;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.adapter.CommentAdapter;
import com.neusoft.edu.cn.dingzhenling.adapter.IssueAdapter;
import com.neusoft.edu.cn.dingzhenling.bean.Comment;
import com.neusoft.edu.cn.dingzhenling.bean.Genre;
import com.neusoft.edu.cn.dingzhenling.bean.Issue;
import com.neusoft.edu.cn.dingzhenling.bean.Like;
import com.neusoft.edu.cn.dingzhenling.bean.MyUser;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.datatype.a.I;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/3/26 0026.
 */
public class IssuedetaiActivity extends AppCompatActivity implements View.OnClickListener{
    public Context context;
    String issue_id;
    private  Button btn_comment;
    private  EditText et_comment;
    //显示对应问题列表
    private RecyclerView mRecyclerView;
    private List<Comment> mDatas;
    private CommentAdapter mAdapter;
    private TextView username,title,content,time,beizhu;
    private de.hdodenhof.circleimageview.CircleImageView UserImage;
    private ShineButton like_button;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getApplication();
        setContentView(R.layout.issue_detail);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_comments);
        initRecyclerView();
        btn_comment=(Button) findViewById(R.id.btn_comment);
        et_comment=(EditText)findViewById(R.id.et_comment);
        username=(TextView)findViewById(R.id.username);
        title=(TextView)findViewById(R.id.title);
        content=(TextView)findViewById(R.id.content);
        time=(TextView)findViewById(R.id.time);
        beizhu=(TextView)findViewById(R.id.beizhu);
        UserImage=(de.hdodenhof.circleimageview.CircleImageView)findViewById(R.id.UserImage);
        like_button=(ShineButton)findViewById(R.id.like_button);
        image=(ImageView)findViewById(R.id.image);
        Intent _getIntent = this.getIntent();
        issue_id = _getIntent.getStringExtra("issue_id");
//        issue_id = getIntent().getExtras().getString("issue_id");
        showIssue();
        showcomment();
        initEvents();
        initcollect();
    }

    private void initcollect() {
        final MyUser user=BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<Issue> query=new BmobQuery<>();
        query.addWhereEqualTo("collect", new BmobPointer(user));
        query.findObjects(new FindListener<Issue>() {
            @Override
            public void done(List<Issue> list, BmobException e) {
                like_button.setChecked(false);
                like_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addCollect();
                    }
                });
                for(Issue collect:list){
                    if(collect.getObjectId().equals(issue_id)){
                        like_button.setChecked(true);
                        like_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                removeCollect();
                            }
                        });
                    }
                }
            }

            private void addCollect() {
                BmobRelation relation = new BmobRelation();
                relation.add(user);
                Issue collect=new Issue();
                collect.setCollect(relation);
                collect.update(issue_id, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            like_button.setChecked(true);
                            Toast.makeText(IssuedetaiActivity.this,"收藏成功", Toast.LENGTH_SHORT).show();
                            initcollect();
                        }
                    }
                });

            }
            private void removeCollect() {
                BmobRelation relation = new BmobRelation();
                relation.remove(user);
                Issue collect=new Issue();
                collect.setCollect(relation);
                collect.update(issue_id, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            like_button.setChecked(false);
                            Toast.makeText(IssuedetaiActivity.this,"取消收藏成功", Toast.LENGTH_SHORT).show();
                            initcollect();
                        }
                    }
                });

            }

        });
    }


    public void initRecyclerView() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getApplication()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new CommentAdapter(this);
        mAdapter.setOnItemClickListener(new CommentAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
            }
        });
        mRecyclerView.setAdapter(mAdapter);

    }

    //显示留言
    public void showcomment() {
        mDatas = new ArrayList<Comment>();
        BmobQuery<Comment> query = new BmobQuery<Comment>();
        //根据类别id获取问题
        Issue issue = new Issue();
        issue.setObjectId(issue_id);
        query.addWhereEqualTo("issue", issue);
        query.include("issue,user");
        //按照时间降序
        query.order("-createdAt");
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if (e == null) {
                    mAdapter.setData(list);

                }
            }
        });
    }

    // 显示问题
    public void showIssue() {
        BmobQuery<Issue> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(issue_id, new QueryListener<Issue>() {
            @Override
            public void done(Issue object, BmobException e) {
                if (e == null) {
                    title.setText(object.getTitle());
                    content.setText(object.getContent());
                    time.setText(object.getCreatedAt());
                    beizhu.setText(object.getBeizhu());
                    if(object.getImage()!=null){
                        Picasso.with(IssuedetaiActivity.this).load(object.getImage().getFileUrl()).into(image);
                    }else{
                        image.setVisibility(View.GONE);
                    }

                    BmobQuery<MyUser> query=new BmobQuery<>();
                    query.getObject(object.getUser().getObjectId(), new QueryListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            if(e==null){
                                username.setText(myUser.getUsername());
                                if (myUser.getTouxiang() != null) {
                                    Picasso.with(IssuedetaiActivity.this).load(myUser.getTouxiang().getFileUrl()).into(UserImage);
                                }
                            }
                        }
                    });

                } else {
                    getToast("查询失败：" + e.getMessage());
                }
                }
            });
    }
    public void initEvents() {
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_comment.getText().toString().trim().equals("")){
                    Toast.makeText(IssuedetaiActivity.this,"请先输入内容",Toast.LENGTH_SHORT).show();
                }else{
                    MyUser user = BmobUser.getCurrentUser(MyUser.class);
                    final Comment comment = new Comment();
                    comment.setUser(user);
                    Issue issue = new Issue();
                    issue.setObjectId(issue_id);
                    comment.setIssue(issue);
                    comment.setContent(et_comment.getText().toString());
                    //发表评论
                    comment.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId,BmobException e) {
                            if(e==null){
                                getToast("回答成功");
                                showcomment();
                            }else{
                                getToast("回答失败："+e.getMessage());
                            }
                        }
                    });
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

