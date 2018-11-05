package com.neusoft.edu.cn.dingzhenling.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.bean.MyUser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int RESIZE_REQUEST_CODE = 2;
    private static final String IMAGE_FILE_NAME = "header.jpg";
    private static final String fileName = "temp.jpg";
    private static final int REQUEST_TO_CAMERA = 1;
    public static final int SEND_SEARCH_NAME = 4;// 向联系人
    public static final int SEND_SEARCH_PHONE = 5;// 向联系人
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private File tempFile;
    AlertDialog albumDialog;
    String dateTime;
    String iconUrl;
    private BmobFile bmobFile;
    private de.hdodenhof.circleimageview.CircleImageView touxiang;
    private EditText nameText,passwordText;
    private Button  loginLink,signupButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bmob.initialize(this, "de7dad987e06923148923eb46e46dae4");
        //初始化控件
       /* ButterKnife.bind(this);*/
        touxiang = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.touxiang);
        nameText=(EditText)findViewById(R.id.et_username);
        passwordText=(EditText)findViewById(R.id.et_password) ;
        loginLink=(Button)findViewById(R.id.login_back);
        signupButton = (Button) findViewById(R.id.registre_button);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIcon(iconUrl);

            }
        });
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

    }
    private void updateIcon(final String pic1Path) {
        if (pic1Path != null) {
            final BmobFile file = new BmobFile(new File(pic1Path));
            //回调
            file.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Log.d(TAG, "Signup");
                        if (!validate()) {
                            return;
                        }
                        signupButton.setEnabled(false);
                        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this,
                                R.style.AppTheme_Dark_Dialog);
                        progressDialog.setIndeterminate(true);
                        String username = nameText.getText().toString();
                        String password = passwordText.getText().toString();
                        MyUser user=new MyUser();
                        user.setUsername(username);
                        user.setPassword(password);
                        user.setTouxiang(file);
                        user.signUp(new SaveListener<MyUser>() {
                            @Override
                            public void done(MyUser myUser, BmobException e) {
                                if (e==null){
                                    Toast.makeText(RegisterActivity.this, "注册成功:", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "注册失败:", Toast.LENGTH_LONG).show();

                                }
                            }
                        });
                        // TODO: Implement your own signup logic here.
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        signupButton.setEnabled(true);
                                        finish();
                                        // onSignupFailed();
                                        progressDialog.dismiss();
                                    }
                                }, 3000);
                    } else {
                        Log.i(TAG, "done: " + e);
                    }
                }
            });
        }
    }


    public void showDialog(View v) {
        showAlbumDialog();
    }
    public void showAlbumDialog() {
        albumDialog = new AlertDialog.Builder(RegisterActivity.this).create();
        albumDialog.setCanceledOnTouchOutside(true);
        View v = LayoutInflater.from(RegisterActivity.this).inflate(R.layout.dialog_usericon, null);
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

    public boolean validate() {
        boolean valid = true;
        String name = nameText.getText().toString();
        String password = passwordText.getText().toString();
        return valid;
    }

    public void getAvataFromCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (isExistSd()) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(Environment.getExternalStorageDirectory(),fileName)));
        }
        startActivityForResult(intent,REQUEST_TO_CAMERA);
    }
    //是否存在sd
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
                        }
                    }
                    break;
                default:
                    break;
            }
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
        String files = CacheUtils.getCacheDirectory(RegisterActivity.this, true, "icon") + dateTime + "_12";
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
    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private boolean isSdcardExisting() {//判断SD卡是否存在
        final String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
    public void resizeImage(Uri uri) {//重塑图片大小
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");//可以裁剪
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESIZE_REQUEST_CODE);
    }

    private void showResizeImage(Intent data) {//显示图片
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(photo);
            touxiang.setImageDrawable(drawable);
        }
    }

    private Uri getImageUri() {//获取路径
        return Uri.fromFile(new File(Environment.getExternalStorageDirectory(),IMAGE_FILE_NAME
        ));
    }

    private void upload(String img_url) {
    }

}