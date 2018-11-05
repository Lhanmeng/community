package com.neusoft.edu.cn.dingzhenling.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import com.neusoft.edu.cn.dingzhenling.Fragment.CampaignFragment;
import com.neusoft.edu.cn.dingzhenling.Fragment.MessageFragment;
import com.neusoft.edu.cn.dingzhenling.Fragment.MyHomeFragment;
import com.neusoft.edu.cn.dingzhenling.Fragment.PersonFragment;
import com.neusoft.edu.cn.dingzhenling.R;

public class ActivityIndex  extends AppCompatActivity
{

    private RadioButton ra_home_bt, ra_collect_bt, ra_search_bt, ra_wo_bt;
    private Fragment myHomeFragment, campaignFragment, messageFragment, personFragment;
    private FragmentManager fgManager;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this);
    }
    protected void init(Context context) {
        setContentView(R.layout.activity_index);
        fgManager = getFragmentManager();
        ra_home_bt = (RadioButton) findViewById(R.id.ra_shouye_bt);
        ra_collect_bt = (RadioButton) findViewById(R.id.ra_collect_bt);
        ra_search_bt = (RadioButton) findViewById(R.id.ra_search_bt);
        ra_wo_bt = (RadioButton) findViewById(R.id.ra_wo_bt);

        myHomeFragment = new MyHomeFragment();
        campaignFragment = new CampaignFragment();
        messageFragment = new MessageFragment();
        personFragment = new PersonFragment();
        //默认进入首页
        changeFrament(myHomeFragment, null, "myHomeFragment");
        ra_home_bt.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.index1, 0, 0);

        ra_home_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myHomeFragment = new MyHomeFragment();
                changeFrament(myHomeFragment, null, "myHomeFragment");
                changeRadioButtonImage(v.getId());
            }
        });
        ra_collect_bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                campaignFragment = new CampaignFragment();
                changeFrament(campaignFragment, null, "campaignFragment");
                changeRadioButtonImage(v.getId());
            }
        });
        ra_search_bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                messageFragment = new MessageFragment();
                changeFrament(messageFragment, null, "messageFragment");
                changeRadioButtonImage(v.getId());
            }
        });
        ra_wo_bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                personFragment = new PersonFragment();
                changeFrament(personFragment, null, "personFragment");
                changeRadioButtonImage(v.getId());
            }
        });
    }
    public void changeFrament(Fragment fragment, Bundle bundle, String tag) {

        for (int i = 0, count = fgManager.getBackStackEntryCount(); i < count; i++) {
            fgManager.popBackStack();
        }
        FragmentTransaction fg = fgManager.beginTransaction();
        fragment.setArguments(bundle);
        fg.add(R.id.fragmentRoot, fragment, tag);
        fg.addToBackStack(tag);
        fg.commit();
    }

    public void changeRadioButtonImage(int btids) {
        int[] imageh = {R.drawable.index, R.drawable.huodong,
                R.drawable.xiaoxi, R.drawable.wo};
        int[] imagel = {R.drawable.index1, R.drawable.huodong1,
                R.drawable.xiaoxi1, R.drawable.wo1};
        int[] rabt = {R.id.ra_shouye_bt, R.id.ra_collect_bt, R.id.ra_search_bt,
                R.id.ra_wo_bt};
        switch (btids) {
            case R.id.ra_shouye_bt:
                changeImage(imageh, imagel, rabt, 0);
                break;
            case R.id.ra_collect_bt:
                changeImage(imageh, imagel, rabt, 1);
                break;
            case R.id.ra_wo_bt:
                changeImage(imageh, imagel, rabt, 3);
                break;
            case R.id.ra_search_bt:
                changeImage(imageh, imagel, rabt, 2);
                break;
            default:
                break;
        }
    }
    public void changeImage(int[] image1, int[] image2, int[] rabtid, int index) {
        for (int i = 0; i < image1.length; i++) {
            if (i != index) {
                ((RadioButton) findViewById(rabtid[i]))
                        .setCompoundDrawablesWithIntrinsicBounds(0, image1[i],
                                0, 0);
                ((RadioButton)findViewById(rabtid[i])).setTextColor(getResources().getColor(R.color.black));
            } else {
                ((RadioButton) findViewById(rabtid[i]))
                        .setCompoundDrawablesWithIntrinsicBounds(0, image2[i],
                                0, 0);
                ((RadioButton)findViewById(rabtid[i])).setTextColor(getResources().getColor(R.color.colorMain));
            }
        }
    }

}

