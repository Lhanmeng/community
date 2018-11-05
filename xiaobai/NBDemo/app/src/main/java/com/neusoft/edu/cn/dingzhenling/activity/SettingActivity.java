package com.neusoft.edu.cn.dingzhenling.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.bean.MyUser;
import com.neusoft.edu.cn.dingzhenling.config.NBDemo;
import com.neusoft.edu.cn.dingzhenling.fragment.SkinFragment;
import com.squareup.picasso.Picasso;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Administrator on 2017/3/12 0012.
 */
public class SettingActivity extends AppCompatActivity  implements View.OnClickListener {
    private LinearLayout pwdupdate,feedback ,quit,huanfu;
    private ImageButton back;
    private TextView username;
    BmobUser user;
    private de.hdodenhof.circleimageview.CircleImageView username_image;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //获取当前用户名 用户头像
        Bmob.initialize(this, "de7dad987e06923148923eb46e46dae4");
        user = BmobUser.getCurrentUser();
        pwdupdate=(LinearLayout)findViewById(R.id.pwdupdate);
        feedback=(LinearLayout)findViewById(R.id.feedback);
        quit=(LinearLayout)findViewById(R.id.quit);
        back=(ImageButton)findViewById(R.id.back) ;
        huanfu=(LinearLayout)findViewById(R.id.huanfu) ;
        username=(TextView)findViewById(R.id.username);
        username_image=(de.hdodenhof.circleimageview.CircleImageView)findViewById(R.id.username_image);
        initEvents();
        initData();
    }
    //获取当前用户名 用户头像
    private void initData() {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<MyUser> query = new BmobQuery<>();
        query.getObject(user.getObjectId(), new QueryListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if (e == null) {
                    username.setText(myUser.getUsername());
                    Picasso.with(SettingActivity.this).load(myUser.getTouxiang().getFileUrl()).into(username_image);
                }
            }
        });
    }

    public void initEvents() {
        pwdupdate.setOnClickListener(this);
        feedback.setOnClickListener(this);
        quit.setOnClickListener(this);
        back.setOnClickListener(this);
        huanfu. setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.pwdupdate:
                startActivity(new Intent(this.getApplication(), PersonActivity.class));
                break;
            case R.id.feedback:
                startActivity(new Intent(this.getApplication(), PersonFeedbackActivity.class));
                break;
            case R.id.back:
               finish();
                break;
            case R.id.huanfu:
                startActivity(new Intent(SettingActivity.this, SkinActivity.class));
                break;
            case R.id.quit:
//                exitLogin();
//                NBDemo.getInstance().logout();
                BmobUser.logOut();
                finish();
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                break;

            default:
                break;
        }
    }

    /**
     * 退出后清空缓存的token
     */
    private void exitLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定要退出当前账号？");
        builder.setTitle("温馨提示：");
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            /*   SharedPreferenceUtil.putString(SharedConstant.OBJECT_ID,"");
               PrefManager.getP().finishAllActivity();*/

//                goActivity(LoginActivity.class,false);
            }
        });

        builder.setPositiveButton("取消",null);
        builder.show();


    }
    private void goActivity(Class<?> cls,boolean isFinish) {
        Intent intent = new Intent(this, cls);
        this.startActivity(intent);
        if(isFinish){
            finish();
        }
    }
}
