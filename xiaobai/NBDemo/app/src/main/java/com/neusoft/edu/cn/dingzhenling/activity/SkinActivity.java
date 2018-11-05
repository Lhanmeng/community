package com.neusoft.edu.cn.dingzhenling.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.Util;

/**
 * Created by Administrator on 2017/4/13 0013.
 */
public class SkinActivity extends AppCompatActivity implements View.OnClickListener{
//    private static String KEY = SkinActivity.class.getSimpleName();

    private Button blue_btn, pink_btn, yellow_btn, sblue_btn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_skin);
        blue_btn = (Button)findViewById(R.id.blue_btn);
        blue_btn.setOnClickListener(this);
        pink_btn = (Button)findViewById(R.id.pink_btn);
        pink_btn.setOnClickListener(this);
        yellow_btn = (Button)findViewById(R.id.yellow_btn);
        yellow_btn.setOnClickListener(this);
        sblue_btn = (Button)findViewById(R.id.sblue_btn);
        sblue_btn.setOnClickListener(this);
//        String name = getArguments().getString(KEY, "");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.blue_btn:
                MainActivity.currentColour = this.getResources().getColor(R.color.skin_blue);
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.pink_btn:
                MainActivity.currentColour = this.getResources().getColor(R.color.skin_pink);
                break;

            case R.id.yellow_btn:
                MainActivity.currentColour = this.getResources().getColor(R.color.skin_yellow);
                break;

            case R.id.sblue_btn:
                MainActivity.currentColour = this.getResources().getColor(R.color.skin_sblue);
                break;
        }
        MainActivity.setColour(MainActivity.currentColour);
        Util.saveColor(this,MainActivity.currentColour);
    }
}
