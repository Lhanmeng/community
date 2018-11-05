package com.neusoft.edu.cn.dingzhenling.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.bean.MyUser;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity{

    private TextView tv_back;
    private EditText et_name,et_password,et_password2;
    private Button btn_reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);
        initialize();
    }
    private void initialize(){

        et_name=(EditText)findViewById(R.id.username);
        et_password=(EditText)findViewById(R.id.password);
        et_password2=(EditText)findViewById(R.id.password2);

        tv_back=(TextView) findViewById(R.id.back);
        btn_reg=(Button)findViewById(R.id.register);
       tv_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View V) {
                Intent intent = new Intent();
                intent.setClass(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                RegisterActivity.this.finish();
            }
        });
        btn_reg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                register();

            }
        });
    }

    //注册
    private void register() {
        final String username = et_name.getText().toString();
       /* final String password = et_password.getText().toString();;*/
        final String password = et_password.getText().toString();;
   /*     et_password.getText().toString().equals(et_password2.getText().toString())*/
        MyUser user = new MyUser();
        user.setUsername(username);
        user.setPassword(password);
        user.signUp(new SaveListener<MyUser>(){
            public void done(MyUser myUser, BmobException e) {
                if (e == null) {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    //注册成功1秒 跳转到登录页面
                    Timer timer=new Timer();
                    TimerTask timerTask=new TimerTask() {
                        @Override
                        public void run() {
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        }
                    };
                 timer.schedule(timerTask,1000);
                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                }
            }


        });
    }


}
