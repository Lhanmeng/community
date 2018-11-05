package com.neusoft.edu.cn.dingzhenling.activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.bean.FeedBack;
import com.neusoft.edu.cn.dingzhenling.bean.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/11/10 0010.
 */
public class PersonFeedbackActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "PersonFeedBackActivity";
    private EditText etContent,et_Tel;
    private TextView tv_Submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initView();
    }
    private void initView() {
        etContent = (EditText) findViewById(R.id.feedback_content);
        tv_Submit = (TextView) findViewById(R.id.feedback_submit);
        et_Tel=(EditText) findViewById(R.id.tele);
        tv_Submit.setOnClickListener(this);
    }

    private void submit() {
        String content = etContent.getText().toString();
        String tel=et_Tel.getText().toString();
        if(content.equals("")) {
            toast("请先写点东西吧......");
        } else {
            MyUser user = MyUser.getCurrentUser(MyUser.class);
            FeedBack fb = new FeedBack();
            //获取当前用户 姓名 电话
            fb.setUser(user);
            // 获取回馈电话 内容
            fb.setContent(content);
            fb.setTel(tel);
            fb.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if(e==null){
                        toast("提交成功, 会尽快回复");
                                back();
                    }
                    else {
                        toast("提交失败");
                    }
                }
            });
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.feedback_submit:
                submit();
                break;
            default:
                break;
        }
    }

    private void back() {
        finish();
    }

    public void clickFeedBack(View v) {
        finish();
    }

    private void toast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }


}

