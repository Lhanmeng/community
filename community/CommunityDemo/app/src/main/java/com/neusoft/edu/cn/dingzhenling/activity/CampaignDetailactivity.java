package com.neusoft.edu.cn.dingzhenling.activity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.adapter.CommentAdapter;
import com.neusoft.edu.cn.dingzhenling.bean.Attend;
import com.neusoft.edu.cn.dingzhenling.bean.Campaign;
import com.neusoft.edu.cn.dingzhenling.bean.Collect;
import com.neusoft.edu.cn.dingzhenling.bean.Comment;
import com.neusoft.edu.cn.dingzhenling.bean.MyUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

//活动详情 搜索返回 searchFragment1  直接返回CampaignFragment
public class CampaignDetailactivity extends AppCompatActivity {
    public static final String CAMPAIGN_ID = "campaignId";
    //活动评论
    private RecyclerView mRecyclerView;
    private List<Comment> mDatas;
    private CommentAdapter mAdapter;
    private ImageView ra_collect_bt;
  /*  ImageButton ibn_back;*/
    ImageView img_img;
    TextView tv_time,tv_name,tv_info,tv_place,tv_leader,tv_group;
    String campaign_id;
    //
    RadioButton rbtn_comment;
    private FragmentManager fgManager;
    protected Context context;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent _getIntent = this.getIntent();
        campaign_id = _getIntent.getStringExtra("campaign_id");
        init(this);
        showComment();
        showCampaign();
    }
    protected void init(Context context) {
        setContentView(R.layout.activity_campaign_detail);
        //活动评论
         mRecyclerView = (RecyclerView) findViewById(R.id.rv_comments);
         mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
         mRecyclerView.setItemAnimator(new DefaultItemAnimator());
         fgManager = getFragmentManager();
        img_img=(ImageView)findViewById(R.id.image);
        tv_time=(TextView)findViewById(R.id.campaign_time);
        tv_name=(TextView)findViewById(R.id.campaign_name);
        tv_info=(TextView)findViewById(R.id.campaign_info);
        tv_place=(TextView)findViewById(R.id.campaign_place);
        tv_leader=(TextView)findViewById(R.id.h_leader);
        tv_group=(TextView)findViewById(R.id.h_groupname);
        rbtn_comment=(RadioButton)findViewById(R.id.ra_shouye_bt);
        ra_collect_bt=(ImageView)findViewById(R.id.ra_collect_bt);
        rbtn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(CampaignDetailactivity.this, CommentActivity.class);
                intent.putExtra(CAMPAIGN_ID, campaign_id);
              //  this.startActivity(intent);
                startActivity(intent);
            }
        });

    }
    //显示评论
  public void showComment() {
        mDatas = new ArrayList<Comment>();
        BmobQuery<Comment> query = new BmobQuery<Comment>();
        //根据社团id获取活动
       Campaign campaign = new Campaign();
       campaign.setObjectId(campaign_id);
       query.addWhereEqualTo("campaign",campaign);
       query.include("campaign");
        query.include("user");
        //按照时间降序
        query.order("-createdAt");

        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if (e == null) {
                    mDatas = list;
                    mAdapter = new CommentAdapter(CampaignDetailactivity.this.getApplicationContext(), mDatas);
                    mAdapter.setOnItemClickListener(new CommentAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, String data) {
                            //无跳转
                        }
                    });
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        });
    }
    // 活动
    public void showCampaign() {
        BmobQuery<Campaign> bmobQuery = new BmobQuery<Campaign>();
        bmobQuery.getObject(campaign_id, new QueryListener<Campaign>() {
            @Override
            public void done(final Campaign object, BmobException e) {
                if(e==null){
                    Log.e("object",object.getIntro());
                    tv_time.setText(object.getTime());
                    tv_name.setText(object.getCampaignname());
                    tv_group.setText(object.getGname());
                    tv_info.setText(object.getIntro());
                    tv_place.setText(object.getPlace());
                    tv_leader.setText(object.getLeader());
                    Picasso.with(context).load(object.getPic()).into(img_img);
                    campaign_id=getIntent().getExtras().getString("campaign_id");

                }else {
                    getToast("查询失败：" + e.getMessage());
                }
               ra_collect_bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Collect collect = new Collect();
                        MyUser user = MyUser.getCurrentUser(MyUser.class);
                        collect.setUser(user.getUsername());
                        BmobQuery<Collect> query1 = new BmobQuery<Collect>();
                        query1.findObjects(new FindListener<Collect>() {
                            @Override
                            public void done(List<Collect> list, BmobException e) {
                                int j = 0;
                                for (Collect collect:list) {
                                    if (collect.getCampaignname().equals(object.getCampaignname())) {
                                        j++;
                                    }
                                }
                                if (j == 0) {
                                    collect.setCampaign_id(object.getObjectId());
                                    collect.setCampaignname(object.getCampaignname());
                                    collect.setGname(object.getGname());
                                    collect.setShortIntro(object.getShortIntro());
                                    collect.setIntro(object.getIntro());
                                    collect.setPlace(object.getPlace());
                                    collect.setTime(object.getTime());
                                    collect.setPic(object.getPic());
                                    collect.setLeader(object.getLeader());
                                    collect.save(new SaveListener<String>() {
                                        @Override
                                        public void done(String s, BmobException e) {

                                            if (e == null) {

                                                ra_collect_bt.setImageResource(R.drawable.collect1);

                                                Toast.makeText(CampaignDetailactivity.this, "收藏成功", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(CampaignDetailactivity.this, "收藏失败", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                } else {
                                    ra_collect_bt.setImageResource(R.drawable.collect1);
                                    Toast.makeText(CampaignDetailactivity.this, "已收藏", Toast.LENGTH_LONG).show();
                                }

                            }
                        });
                    }
                });
                }
        });
    }
   public void click2(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.arrow_down_float);
        builder.setIcon(R.drawable.queren);
        builder.setTitle("是否确认：");
        final String[] items = new String[]{
                "是",
                "否"
        };
        //设置单选选择项                                                     //选项      //默认选项      //选择后发生事件
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
               switch (which){
                   case 0:
                           MyUser user = MyUser.getCurrentUser(MyUser.class);
                           final Attend attend = new Attend();
                           //获取当前用户 姓名 电话
                       attend.setUser(user.getUsername());
                       attend.setTele(user.getTelenumble());
                       //获取当前活动名 及活动id
                       BmobQuery<Campaign> bmobQuery = new BmobQuery<Campaign>();
                       bmobQuery.getObject(campaign_id, new QueryListener<Campaign>() {
                                   @Override
                                   public void done(Campaign campaign, BmobException e) {
                                       attend.setCampaign_id(campaign.getObjectId());
                                       Log.e("campaign.getObjectId()",campaign.getObjectId());
                                       attend.setCampaignname(campaign.getCampaignname());
                                       attend.save(new SaveListener<String>() {
                                           @Override
                                           public void done(String s, BmobException e) {
                                               if(e==null){
                                                  // toast("提交成功, 会尽快回复");
                                                   Toast.makeText(CampaignDetailactivity.this, "报名成功，请耐心等待通知" , Toast.LENGTH_SHORT).show();
                                               }
                                               else {
                                                  Toast.makeText(CampaignDetailactivity.this,"您取消了本次报名",Toast.LENGTH_SHORT).show();
                                                  toast("提交失败");
                                               }
                                           }
                                       });
                                   }
                               });
                     //  Toast.makeText(CampaignDetailactivity.this,"您取消了本次报名"+ items[1],Toast.LENGTH_SHORT).show();
                  /*     Toast.makeText(CampaignDetailactivity.this, "报名成功，请耐心等待通知" + items[which], Toast.LENGTH_SHORT).show();
                     */  //attend.setCampaign_id(object.getObjectId());

                       dialog.dismiss();

                  case 1:
                      /* Toast.makeText(CampaignDetailactivity.this,"您取消了本次报名"+ items[which],Toast.LENGTH_SHORT).show();*/
               }
                //使对话框消失
                dialog.dismiss();
            }
        });
        builder.show();
    }


    public void getToast(String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
    private void toast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }
    public void getNetError() {
        getToast("亲~网络连接失败！");
    }
    public void Back(View v) {
        finish();
    }

}