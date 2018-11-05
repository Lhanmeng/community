package com.neusoft.edu.cn.dingzhenling.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.adapter.HomeAdapter;
import com.neusoft.edu.cn.dingzhenling.bean.Groups;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

//社团查询
public class SearchFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private List<Groups> mDatas;
    private HomeAdapter mAdapter;
    private EditText search_name_et;
    private ImageButton do_search_btn,back;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search, container, false);
        search_name_et = (EditText)view.findViewById(R.id.search_name_et);
        do_search_btn = (ImageButton) view.findViewById(R.id.do_search_btn);
        back=(ImageButton)view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                IndexCommunityFragment indexCommunityFragment = new IndexCommunityFragment();
                changeFrament(indexCommunityFragment, bundle, " indexCommunityFragment");
            }
        });


        do_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (search_name_et.getText() == null || search_name_et.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "查询为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                queryData();
            }
        });

        mRecyclerView = (RecyclerView) view.findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        return view;
    }

    private void queryData() {
        BmobQuery<Groups> query = new BmobQuery<>();
        query.addWhereEqualTo("groupname", search_name_et.getText().toString());
        query.findObjects(new FindListener<Groups>() {
            @Override
            public void done(List<Groups> object, BmobException e) {
                if (e == null) {
                        mDatas = object;
                    mAdapter=new HomeAdapter(SearchFragment.this.getActivity(),mDatas);
                    mAdapter.setOnItemClickListener(new HomeAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, String data) {
                            Bundle bundle=new Bundle();
                            bundle.putString("groups_id",data);
                            bundle.putString("flag","1");
                            changeFrament(new IndexCommunityDetail(), bundle, "indexCommunityDetail");
                        }
                    });
                    mRecyclerView.setAdapter(mAdapter);
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
     }
        });
    }
    public void changeFrament(android.app.Fragment fragment, Bundle bundle, String tag) {

        android.app.FragmentManager fgManager = (this.getActivity()).getFragmentManager();
        for (int i = 0, count = fgManager.getBackStackEntryCount(); i < count; i++) {
            fgManager.popBackStack();
        }
        android.app.FragmentTransaction fg = fgManager.beginTransaction();
        fragment.setArguments(bundle);
        fg.add(R.id.fragmentRoot, fragment, tag);
        fg.addToBackStack(tag);
        fg.commit();
    }
}
