package com.neusoft.edu.cn.dingzhenling.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.neusoft.edu.cn.dingzhenling.R;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
public class PersonActivity extends AppCompatActivity   implements View.OnClickListener  {
    public Context context;
    private EditText et_new_pass,et_new_pass2;
   /* private TextView et_username*/;
    private ImageButton ibn_back;
    BmobUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_update);
/*        Bmob.initialize(this.getActivity(), "5f113daad74ad3772deed9628bffa81d");*/
        Bmob.initialize(this, "de7dad987e06923148923eb46e46dae4");
       /* et_username = (EditText) findViewById(R.id.et_username);*/
     /*   et_username = (TextView) findViewById(R.id.et_username);*/
        et_new_pass = (EditText) findViewById(R.id.et_new_pass);
        et_new_pass2 = (EditText) findViewById(R.id.et_new_pass2);
       /*   user = BmobUser.getCurrentUser();
      et_username.setText(user.getUsername());*/
    }
    public void changeInfo(View v)
    {
        user=BmobUser.getCurrentUser();
        if(!et_new_pass.getText().toString().equals(et_new_pass2.getText().toString())) {
            Toast.makeText(PersonActivity.this, "新密码确认有误，请重新输入！", Toast.LENGTH_LONG).show();
            return;
        }
        // user.setUsername(et_username.getText().toString());
        user.setPassword(et_new_pass.getText().toString());
        user.update(user.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    /*Toast.makeText(PersonalInfoActivity.this,"修改成功 请重新登录:" + user.getUpdatedAt(),Toast.LENGTH_LONG).show();*/
                    Toast.makeText(PersonActivity.this,"修改成功 请重新登录:" ,Toast.LENGTH_LONG).show();

                    Timer timer=new Timer();
                    TimerTask timerTask=new TimerTask() {
                        @Override
                        public void run() {
                            startActivity(new Intent(PersonActivity.this, LoginActivity.class));
                        }
                    };
                    timer.schedule(timerTask,1000);
                }else{
                    Toast.makeText(PersonActivity.this,"修改失败:" + e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }





    public void clickBack(View v) {
        finish();
    }
    private void back() {
        finish();
    }
  /*  public void Back(View v) {
        finish();
    }*/

    @Override
    public void onClick(View v) {

    }
}
