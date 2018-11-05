package com.neusoft.edu.cn.dingzhenling.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.Util;
import com.neusoft.edu.cn.dingzhenling.activity.MainActivity;


/**
 * Created by lql on 2016/10/31.
 */
public class SkinFragment  extends Fragment implements View.OnClickListener{
    private static String KEY = SkinFragment.class.getSimpleName();

    private Button  blue_btn, pink_btn, yellow_btn, sblue_btn;
    public static SkinFragment newInstance(String name) {

        Bundle args = new Bundle();
        SkinFragment fragment = new SkinFragment();
        args.putString(KEY, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView
            (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_skin, container, false);
        blue_btn = (Button)view.findViewById(R.id.blue_btn);
        blue_btn.setOnClickListener(this);
        pink_btn = (Button)view.findViewById(R.id.pink_btn);
        pink_btn.setOnClickListener(this);
        yellow_btn = (Button)view.findViewById(R.id.yellow_btn);
        yellow_btn.setOnClickListener(this);
        sblue_btn = (Button)view.findViewById(R.id.sblue_btn);
        sblue_btn.setOnClickListener(this);
        String name = getArguments().getString(KEY, "");


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.blue_btn:
                MainActivity.currentColour = getActivity().getResources().getColor(R.color.skin_blue);
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
                break;
            case R.id.pink_btn:
                MainActivity.currentColour = getActivity().getResources().getColor(R.color.skin_pink);
                break;

            case R.id.yellow_btn:
                MainActivity.currentColour = getActivity().getResources().getColor(R.color.skin_yellow);
                break;

            case R.id.sblue_btn:
                MainActivity.currentColour = getActivity().getResources().getColor(R.color.skin_sblue);
                break;
        }
        MainActivity.setColour(MainActivity.currentColour);
        Util.saveColor(getActivity(),MainActivity.currentColour);
    }
}
