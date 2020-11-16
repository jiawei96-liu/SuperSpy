package com.software.ustc.superspy.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.software.ustc.superspy.R;
import com.software.ustc.superspy.activity.DonateActivity;
import com.software.ustc.superspy.activity.WebViewActivity;
import com.software.ustc.superspy.activity.WebViewActivity2;
import com.software.ustc.superspy.activity.WebViewActivity3;
import com.software.ustc.superspy.kits.CommonUtil;
import com.software.ustc.superspy.kits.Constant;
import com.software.ustc.superspy.kits.ImageViewPlus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

//public class NotificationsFragment extends Fragment {
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
//        return root;
//    }
//}
    //implements View.OnClickListener, View.OnLongClickListener
public class NotificationsFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "NotificationsFragment";
    private static final int RESULT_OK = -1;
    private ImageViewPlus ivHead;//头像显示
    private TextView btnPhotos;//相册
    private Bitmap head;//头像Bitmap
    private static String path = "/sdcard/DemoHead/";//sd路径
    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    /**启动这个Activity的Intent
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, NotificationsFragment.class);
    }

    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        return root;
    }

//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
//        return root;
////        initView();
////        initData();
////        initEvent();
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        TextView btnApps = (TextView) getActivity().findViewById(R.id.tllAboutUpdate);
        btnApps.setOnClickListener(this);
        TextView btnSys = (TextView) getActivity().findViewById(R.id.tllAboutShare);
        btnSys.setOnClickListener(this);
        TextView btnAppUsages = (TextView) getActivity().findViewById(R.id.tllAboutComment);
        btnAppUsages.setOnClickListener(this);
        TextView btnAccount = (TextView) getActivity().findViewById(R.id.rllAboutDeveloper);
        btnAccount.setOnClickListener(this);
        TextView btnAuth = (TextView) getActivity().findViewById(R.id.tllAboutWeibo);
        btnAuth.setOnClickListener(this);
        TextView btnAppRecomend = (TextView) getActivity().findViewById(R.id.tllAboutContactUs);
        btnAppRecomend.setOnClickListener(this);
        btnPhotos = (TextView) getActivity().findViewById(R.id.photo);
        btnPhotos.setOnClickListener(this);
        ivHead = getActivity(). findViewById(R.id.ph);
        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");//从Sd中找头像，转换成Bitmap
        if (bt != null) {
            //如果本地有头像图片的话
            ivHead.setImageBitmap(bt);
        } else {
            //如果本地没有头像图片则从服务器取头像，然后保存在SD卡中，本Demo的网络请求头像部分忽略

        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tllAboutUpdate:
                startActivity(new Intent(getActivity(), WebViewActivity.class));
                break;
            case R.id.tllAboutShare:
                CommonUtil.shareInfo(getActivity(), "我发现了一款很好用的Android快速开发框架，叫Superspy ，快去GitHub上看看吧" + "\n 点击链接直接下载Superspy\n" + Constant.APP_DOWNLOAD_WEBSITE);
                break;
            case R.id.tllAboutComment:
                startActivity(new Intent(getActivity(), DonateActivity.class));
                break;
            case R.id.rllAboutDeveloper:
                startActivity(new Intent(getActivity(), WebViewActivity2.class));
                break;
            case R.id.tllAboutWeibo:
                startActivity(new Intent(getActivity(), WebViewActivity3.class));
                break;
            case R.id.tllAboutContactUs:
                CommonUtil.sendEmail(getActivity(), Constant.APP_OFFICIAL_EMAIL);
                break;
            case R.id.photo://从相册里面取照片
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);//返回被选中项的URI
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");//得到所有图片的URI
//                System.out.println("MediaStore.Images.Media.EXTERNAL_CONTENT_URI  ------------>   "
//                        + MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//   content://media/external/images/media
                startActivityForResult(intent1, 1);
                break;
            default:
                break;
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //从相册里面取相片的返回结果
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());//裁剪图片
                }

                break;
            //调用系统裁剪图片后
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */

                        ;
                        ;
//                        setPicToView(head);//// 保存在SD卡中

                        ivHead.setImageBitmap(head);//用ImageView显示出来
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    ;

    /**
     * 调用系统的裁剪
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        //找到指定URI对应的资源图片
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        //进入系统裁剪图片的界面
        startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd卡是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建以此File对象为名（path）的文件夹
        String fileName = path + "head.jpg";//图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件（compress：压缩）

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



}
