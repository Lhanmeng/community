
package com.neusoft.edu.cn.dingzhenling.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.neusoft.edu.cn.dingzhenling.R;
import com.neusoft.edu.cn.dingzhenling.bean.Genre;
import com.neusoft.edu.cn.dingzhenling.bean.Issue;
import com.neusoft.edu.cn.dingzhenling.bean.MyUser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;


/**
 * Created by Administrator on 2016/12/26 0026.
 */

public class ReleaseActivity extends AppCompatActivity
//        implements View.OnClickListener
{
    private static final String TAG = "ReleaseActivity";
    private EditText etContent,etTitle,release_remarks;
    private TextView tv_Submit;
    private ImageView image;
    private Spinner spinner;
    AlertDialog albumDialog;
    String dateTime;
    private static final String fileName = "temp.jpg";
    private static final int REQUEST_TO_CAMERA = 1;
    private File tempFile;
    String iconUrl;

   /* BmobUser user;*/

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);
        etContent = (EditText) findViewById(R.id.release_content);
        release_remarks=(EditText)findViewById(R.id.release_remarks);
        etTitle=(EditText)findViewById(R.id.title) ;
        tv_Submit = (TextView) findViewById(R.id.release_submit);

        spinner = (Spinner) findViewById(R.id.spinner2);
        image=(ImageView)findViewById(R.id.image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlbumDialog();
            }
        });

        tv_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit(iconUrl);
            }
        });
    }
    private void submit(final String iconUrl) {
        final Issue fb = new Issue();
        if (iconUrl != null) {
            final BmobFile file = new BmobFile(new File(iconUrl));
            //回调
            file.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        fb.setImage(file);
                     update(fb);
                    } else {
                        Log.i(TAG, "done: " + e);
                    }
                }
            });
        }else{
            update(fb);
        }
    }
    private void update(Issue fb) {
        final ProgressDialog progressDialog = new ProgressDialog(ReleaseActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        MyUser user= BmobUser.getCurrentUser(MyUser.class);
        String content = etContent.getText().toString();
        String title=etTitle.getText().toString();
        String beizhu=release_remarks.getText().toString();
        String select_item = spinner.getSelectedItem().toString();
        String object_id = null;
        switch (select_item){
            case "操作系统":object_id = "41gN666i";break;
            case "应用程序":object_id = "EOUQ333N";break;
            case "硬件设备":object_id = "3YMHbbbj";break;
            case "其他"    :object_id = "jokdEEEJ";break;
        }
        if(title.equals("")||content.equals("")) {
            toast("请先写点东西吧......");
            return;
        } else {

            fb.setUser(user);
            fb.setContent(content);
            fb.setTitle(title);
            fb.setBeizhu(beizhu);
            Genre genre = new Genre();
            genre.setObjectId(object_id);
            fb.setGenre(genre);
            fb.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if(e==null){
                        back();
                    }
                    else {
                        toast("发布失败");
                    }
                }
            });
        }
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.release_submit:
//                submit();
//                break;
//            default:
//                break;
//        }
//    }


    private void back() {
        finish();
    }

    public void clickBack(View v) {
        finish();
    }

    private void toast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    public void showAlbumDialog() {
        albumDialog = new AlertDialog.Builder(ReleaseActivity.this).create();
        albumDialog.setCanceledOnTouchOutside(true);
        View v = LayoutInflater.from(ReleaseActivity.this).inflate(R.layout.dialog_usericon, null);
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
                            image.setImageBitmap(bitmap);
                            //edit_photo.setImageResource(DEFAULT_PIC);
//                            submit(iconUrl);
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
        String files = CacheUtils.getCacheDirectory(ReleaseActivity.this, true, "icon") + dateTime + "_12";
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
