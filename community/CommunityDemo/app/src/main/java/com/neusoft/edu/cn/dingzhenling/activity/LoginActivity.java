package com.neusoft.edu.cn.dingzhenling.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.neusoft.edu.cn.dingzhenling.Fragment.MyHomeFragment;
import com.neusoft.edu.cn.dingzhenling.Fragment.PersonFragment;
import com.neusoft.edu.cn.dingzhenling.R;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity {
    private EditText et_user,et_pass;
    private TextView tv_reg;
    private CheckBox rem_pw, auto_login;
    private String userNameValue,passwordValue;
    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "5f113daad74ad3772deed9628bffa81d");
        setContentView(R.layout.fragment_login);

        sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);

        rem_pw = (CheckBox) findViewById(R.id.cb_mima);
        auto_login = (CheckBox) findViewById(R.id.cb_auto);
        tv_reg=(TextView)findViewById(R.id.reg);

       /* //判断记住密码多选框的状态
      if(sp.getBoolean("ISCHECK", false))
        {
            //设置默认是记录密码状态
            rem_pw.setChecked(true);
            et_user.setText(sp.getString("USER_NAME", ""));
            et_pass.setText(sp.getString("PASSWORD", ""));
            //判断自动登陆多选框状态
            if(sp.getBoolean("AUTO_ISCHECK", false))
            {
                //设置默认是自动登录状态
                auto_login.setChecked(true);
                //跳转界面
                Intent intent = new Intent(LoginActivity.this,LoginActivity.class);
                LoginActivity.this.startActivity(intent);

            }
        }*/

        tv_reg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();//Intent机制来协助应用间的交互与通讯
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
    public void doLogin(View view)
    {
        et_user=(EditText)findViewById(R.id.et_login);
        et_pass=(EditText)findViewById(R.id.et_password);
      /*  String username=et_user.getText().toString();
        String password=et_pass.getText().toString();*/
        userNameValue = et_user.getText().toString();
        passwordValue = et_pass.getText().toString();
        BmobUser bu2 = new BmobUser();
        bu2.setUsername(userNameValue);
        bu2.setPassword(passwordValue);

      bu2.login(new SaveListener<BmobUser>() {

            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if(e==null){
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                    if(rem_pw.isChecked())
                    {
                        //记住用户名、密码、
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("USER_NAME", userNameValue);
                        editor.putString("PASSWORD",passwordValue);
                        editor.commit();
                    }
                    Intent intent=new Intent(LoginActivity.this,ActivityIndex.class);
                    startActivity(intent);
                    //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                    //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
                }else{
                    Toast.makeText(LoginActivity.this,"登录失败,请重新登录",Toast.LENGTH_LONG).show();
                }
            }
        });

        //监听记住密码多选框按钮事件
        rem_pw.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rem_pw.isChecked()) {

                    System.out.println("记住密码已选中");
                    sp.edit().putBoolean("ISCHECK", true).commit();

                }else {

                    System.out.println("记住密码没有选中");
                    sp.edit().putBoolean("ISCHECK", false).commit();

                }

            }
        });

        //监听自动登录多选框事件
        auto_login.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (auto_login.isChecked()) {
                    System.out.println("自动登录已选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", true).commit();

                } else {
                    System.out.println("自动登录没有选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
                }
            }
        });

    }
}
