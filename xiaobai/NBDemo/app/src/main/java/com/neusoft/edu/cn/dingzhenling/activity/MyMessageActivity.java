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
import com.neusoft.edu.cn.dingzhenling.adapter.MessageAdapter;
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
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/3/26 0026.
 */
public class MyMessageActivity  extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = "MyMessageActivity";
    public Context context;
    BmobUser user;
    //显示对应问题列表
    private RecyclerView mRecyclerView;
    private List<Comment> mDatas;
    private MessageAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this.getApplication();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.issue_message);
        user = BmobUser.getCurrentUser();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_message);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getApplication()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        showcomment();
    }


    //显示留言
    public void showcomment() {
        mDatas = new ArrayList<Comment>();
        BmobQuery<Comment> query = new BmobQuery<Comment>();
        //根据当前用户名查询
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        query.addWhereEqualTo("user", user);
        //按照时间降序
        query.order("-createdAt");
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if (e == null) {
                    mDatas=list;
                 /*   Log.e(TAG ,"listSize:" +  list.size());
                    Log.e(TAG, list.toString());*/

                    mAdapter=new MessageAdapter(MyMessageActivity.this,list);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(new MessageAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, String data) {
                            BmobQuery<Comment> query1=new BmobQuery<Comment>();
                            query1.getObject(data, new QueryListener<Comment>() {
                                @Override
                                public void done(Comment comment, BmobException e) {
                                    if(e==null){
                                        Intent intent = new Intent();
                                        intent.setClass(MyMessageActivity.this,IssuedetaiActivity.class);
                                        intent.putExtra("issue_id", comment.getIssue().getObjectId());
                                        startActivity(intent);
                                    }
                                }
                            });

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

