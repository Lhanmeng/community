package com.neusoft.edu.cn.dingzhenling.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.neusoft.edu.cn.dingzhenling.R;

/**
 * Created by Administrator on 2016/10/27 0027.
 */
public class IndexZhaoxinFragment extends BaseFragment {
    private ImageButton ibn_back;
  /*  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.index_zhaoxin, container, false);
        return  view;
    }
*/
    @Override
    public View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.index_zhaoxin, container, false);
        ibn_back = (ImageButton) view.findViewById(R.id.back);

        ibn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                MyHomeFragment myHomeFragment = new MyHomeFragment();
                changeFrament(myHomeFragment, bundle, " myHomeFragment");
            }
        });

        return  view;
    }
}