package com.neusoft.edu.cn.dingzhenling.activity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.RadioButton;
import android.widget.TextView;


import com.neusoft.edu.cn.dingzhenling.R;

import com.neusoft.edu.cn.dingzhenling.Util;
import com.neusoft.edu.cn.dingzhenling.bean.MyUser;
import com.neusoft.edu.cn.dingzhenling.fragment.IssueFragment;
import com.neusoft.edu.cn.dingzhenling.fragment.MeFragemt;
import com.neusoft.edu.cn.dingzhenling.fragment.MyHomeFragment;
import com.squareup.picasso.Picasso;
import com.stylingandroid.prism.Prism;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class MainActivity extends AppCompatActivity
     implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
      public static final String GENRE_ID = "genreId";
      public Context context;
    //读取当前用户信息
      BmobUser user;
 //底部bar
    private RadioButton ra_home_bt, ra_issue_bt, ra_wo_bt;
    private Fragment myHomeFragment,IssueFragment,MeFragment;
    private FragmentManager fgManager;
    private int i = 0;
     PrefManager prefManager;
    public static Prism prism;
    public static int currentColour;
    public static final String TAG = "MainActivity";
    private  de.hdodenhof.circleimageview.CircleImageView username_image;
    private TextView username;
    private NavigationView nav_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context=this.getApplication();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //读取当前用户信息
        Bmob.initialize(this.getApplication(), "de7dad987e06923148923eb46e46dae4");
        prefManager = new PrefManager(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        nav_view = (NavigationView) findViewById(R.id.nav_view);
        //底部bar
        fgManager = getFragmentManager();
        ra_home_bt = (RadioButton) findViewById(R.id.ra_shouye_bt);
        ra_issue_bt = (RadioButton) findViewById(R.id.ra_collect_bt);
        ra_wo_bt = (RadioButton) findViewById(R.id.ra_wo_bt);
        myHomeFragment = new MyHomeFragment();
        MyUser user=BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<MyUser> query=new BmobQuery<>();
        //读取当前用户信息
        query.getObject(user.getObjectId(), new QueryListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if(e==null){
                    username.setText(myUser.getUsername());
                    Picasso.with(getApplication()).load(myUser.getTouxiang().getFileUrl()).into(username_image);
                }
            }
        });

        currentColour = Util.getColor(this, getResources().getColor(R.color.skin_blue));
        prism = Prism.Builder.newInstance()
                .background(toolbar)
                .background(getWindow())
                .build();
        setColour(currentColour);
        //默认进入首页
        changeFrament(myHomeFragment, null, "myHomeFragment");
        ra_home_bt.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.sindex1, 0, 0);


        View headerView = nav_view.getHeaderView(0);
        username=(TextView)headerView.findViewById(R.id.username);
        username_image = (de.hdodenhof.circleimageview.CircleImageView)headerView.findViewById(R.id.username_image);

      /*  initData();*/

        username_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PersonalActivity.class);
                startActivity(intent);
            }
        });
        //点击首页跳转
        ra_home_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myHomeFragment = new MyHomeFragment();
                changeFrament(myHomeFragment, null, "myHomeFragment");
                changeRadioButtonImage(v.getId());
            }
        });
        ra_issue_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IssueFragment=new IssueFragment();
                changeFrament(IssueFragment, null, "myHomeFragment");
                changeRadioButtonImage(v.getId());
            }
        });
        ra_wo_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MeFragment=new MeFragemt();
                changeFrament(MeFragment, null, "meFragment");
                changeRadioButtonImage(v.getId());
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ll_userArea:
                onUserAreaClick();
                break;
        }
    }

    private void onUserAreaClick() {
        Log.d(TAG, "onUserAreaClick:点击用户头像");
    }


    public static void setColour(int colour) {
        if (prism != null) {
            prism.setColour(colour);
        }
    }

        @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
/*why*/
   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.release) {
            Intent intent = new Intent();//Intent机制来协助应用间的交互与通讯
            intent.setClass(MainActivity.this,ReleaseActivity.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.search) {
            Intent intent = new Intent();//Intent机制来协助应用间的交互与通讯
            intent.setClass(MainActivity.this,SearchActivity.class);
            startActivity(intent);

        } else if (id == R.id.collection) {
            Intent intent = new Intent();//Intent机制来协助应用间的交互与通讯
            intent.setClass(MainActivity.this,CollectionActivity.class);
            startActivity(intent);

        } else if (id == R.id.book) {
            Intent intent = new Intent();//Intent机制来协助应用间的交互与通讯
            intent.setClass(MainActivity.this,BookActivity.class);
            startActivity(intent);

        } else if (id == R.id.settings) {
            Intent intent = new Intent();//Intent机制来协助应用间的交互与通讯
            intent.setClass(MainActivity.this,SettingActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        int[] imageh = {R.drawable.sindex, R.drawable.issue,
                R.drawable.swo};
        int[] imagel = {R.drawable.sindex1, R.drawable.issue1,
               R.drawable.swo1};
        int[] rabt = {R.id.ra_shouye_bt, R.id.ra_collect_bt,
                R.id.ra_wo_bt};
        switch (btids) {
            case R.id.ra_shouye_bt:
                changeImage(imageh, imagel, rabt, 0);
                break;
            case R.id.ra_collect_bt:
                changeImage(imageh, imagel, rabt, 1);
                break;
            case R.id.ra_wo_bt:
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
                ((RadioButton)findViewById(rabtid[i])).setTextColor(getResources().getColor(R.color.colorBlack));
            } else {
                ((RadioButton) findViewById(rabtid[i]))
                        .setCompoundDrawablesWithIntrinsicBounds(0, image2[i],
                                0, 0);
                ((RadioButton)findViewById(rabtid[i])).setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            // 创建退出对话框
            AlertDialog isExit = new AlertDialog.Builder(this).create();
            // 设置对话框标题
            isExit.setTitle("系统提示");
            // 设置对话框消息
            isExit.setMessage("确定要退出登录吗");
            // 添加选择按钮并注册监听
            isExit.setButton("确定", listener);
            isExit.setButton2("取消", listener);
            // 显示对话框
            isExit.show();

        }

        return false;

    }
    /**监听对话框里面的button点击事件*/
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
    {
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
        /*            BmobUser.logOut(MainActivity.this);*/
                    finish();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };

}
