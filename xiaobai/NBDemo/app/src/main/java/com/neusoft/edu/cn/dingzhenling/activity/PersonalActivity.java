package com.neusoft.edu.cn.dingzhenling.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.bean.MyUser;
import com.squareup.picasso.Picasso;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class PersonalActivity extends AppCompatActivity  implements View.OnClickListener {
    public Context context;
    private EditText telephone, email;
    private TextView tv_username;
    private ImageButton ibn_back;
    private ImageView touxiang;
    BmobUser user;
    private File tempFile;
    AlertDialog albumDialog;
    String dateTime;
    String iconUrl;
    private static final int RESIZE_REQUEST_CODE = 2;
    private static final String IMAGE_FILE_NAME = "header.jpg";
    private static final String fileName = "temp.jpg";
    private static final int REQUEST_TO_CAMERA = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_update);
        Bmob.initialize(this, "de7dad987e06923148923eb46e46dae4");
        user = BmobUser.getCurrentUser();
        tv_username = (TextView) findViewById(R.id.username);
        telephone = (EditText) findViewById(R.id.telephone);
        email = (EditText) findViewById(R.id.email);
        touxiang=(ImageView)findViewById(R.id.touxiang);

        initData();

    }

    private void initData() {
        MyUser user=BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<MyUser> query=new BmobQuery<>();
        query.getObject(user.getObjectId(), new QueryListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if(e==null){
                    email.setText(myUser.getEmail());
                    tv_username.setText(myUser.getUsername());
                    telephone.setText(myUser.getMobilePhoneNumber());
                    Picasso.with(PersonalActivity.this).load(myUser.getTouxiang().getFileUrl()).into(touxiang);
                }
            }
        });
    }

    public void changeInfo(View v)
    {
        user=BmobUser.getCurrentUser();
        user.setEmail(email.getText().toString());
        user.setMobilePhoneNumber(telephone.getText().toString());
        user.update(user.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    finish();

                }else{
                    Toast.makeText(PersonalActivity.this,"保存失败失败:" + e.getMessage(),Toast.LENGTH_LONG).show();
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

    @Override
    public void onClick(View v) {

    }
    public void showDialog(View v) {
        showAlbumDialog();
    }
    public void showAlbumDialog() {
        albumDialog = new AlertDialog.Builder(PersonalActivity.this).create();
        albumDialog.setCanceledOnTouchOutside(true);
        View v = LayoutInflater.from(PersonalActivity.this).inflate(R.layout.dialog_usericon, null);
        albumDialog.show();
        albumDialog.setContentView(v);
        albumDialog.getWindow().setGravity(Gravity.CENTER);
        TextView albumPic = (TextView) v.findViewById(R.id.album_pic);
        TextView cameraPic = (TextView) v.findViewById(R.id.camera_pic);
        albumPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                albumDialog.dismiss();
                Date date1 = new Date(System.currentTimeMillis());
                dateTime = date1.getTime() + "";
                getAvataFromAlbum();
            }
        });
        cameraPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                albumDialog.dismiss();
                Date date = new Date(System.currentTimeMillis());
                dateTime = date.getTime() + "";
                getAvataFromCamera();
            }
        });
    }
    public void getAvataFromCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (isExistSd()) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(Environment.getExternalStorageDirectory(),fileName)));
        }
        startActivityForResult(intent,REQUEST_TO_CAMERA);
    }
    private boolean isExistSd() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }
    private void getAvataFromAlbum() {
        Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
        intent2.setType("image/*");
        startActivityForResult(intent2, 2);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1:
                    if (isExistSd()){
                        tempFile = new File(Environment.getExternalStorageDirectory(),fileName);
                        startPhotoZoom(Uri.fromFile(tempFile));
                    }else {
                        Toast.makeText(this, "SD卡不存在，图片保存失败", Toast.LENGTH_SHORT).show();

                    }
                    break;
                case 2:
                    if (data == null) {
                        return;
                    }
                    startPhotoZoom(data.getData());
                    break;
                case 3:
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            Bitmap bitmap = extras.getParcelable("data");
                            iconUrl = saveToSdCard(bitmap);
                            touxiang.setImageBitmap(bitmap);
                            //edit_photo.setImageResource(DEFAULT_PIC);
                            updateIcon(iconUrl);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
//修改头像
        private void updateIcon(final String iconUrl) {
            if (iconUrl != null) {
                final BmobFile file = new BmobFile(new File(iconUrl));
                file.upload( new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            MyUser currentUser = BmobUser.getCurrentUser(MyUser.class);
                            currentUser.setTouxiang(file);
                             currentUser.update(user.getObjectId(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e==null){
                                        Toast.makeText(PersonalActivity.this,"修改头像成功",Toast.LENGTH_SHORT).show();
                                        initData();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }



    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 120);
        intent.putExtra("outputY", 120);
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    public String saveToSdCard(Bitmap bitmap) {
        String files = CacheUtils.getCacheDirectory(PersonalActivity.this, true, "icon") + dateTime + "_12";
        File file = new File(files);
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
                out.flush();
                out.close();
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
//      LogUtils.i(TAG, file.getAbsolutePath());
        return file.getAbsolutePath();
    }
}
