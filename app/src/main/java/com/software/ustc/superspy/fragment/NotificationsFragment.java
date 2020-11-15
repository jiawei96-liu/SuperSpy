package com.software.ustc.superspy.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.software.ustc.superspy.R;
import com.software.ustc.superspy.activity.AccountMangementActivity;
import com.software.ustc.superspy.activity.AppInfoShowActivity;
import com.software.ustc.superspy.activity.AppUsageAnalysisActivity;
import com.software.ustc.superspy.activity.AppsRecomendActivity;
import com.software.ustc.superspy.activity.DevInfoActivity;
import com.software.ustc.superspy.activity.DonateActivity;
import com.software.ustc.superspy.activity.LoginActivity;
import com.software.ustc.superspy.activity.RollActivity;
import com.software.ustc.superspy.activity.WebViewActivity;
import com.software.ustc.superspy.activity.WebViewActivity2;
import com.software.ustc.superspy.activity.WebViewActivity3;
import com.software.ustc.superspy.kits.BaseActivity;
import com.software.ustc.superspy.kits.CommonUtil;
import com.software.ustc.superspy.kits.Constant;

import java.io.File;

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
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tllAboutUpdate:
                startActivity(new Intent(getActivity(), WebViewActivity.class));
                break;
            case R.id.tllAboutShare:
                CommonUtil.shareInfo(getActivity(), "我发现了一款很好用的Android快速开发框架，叫 Android－ZBLibrary ，快去GitHub上看看吧" + "\n 点击链接直接查看ZBLibrary\n" + Constant.APP_DOWNLOAD_WEBSITE);
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
            default:
                break;
        }
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_notifications, this);
//
//        //功能归类分区方法，必须调用<<<<<<<<<<
//
//        //功能归类分区方法，必须调用>>>>>>>>>>
//
//        if (SettingUtil.isOnTestMode) {
//            showShortToast("测试服务器\n" + HttpRequest.URL_BASE);
//        }
//
//
//        //仅测试用
//        HttpRequest.translate("library", 0, new OnHttpResponseListener() {
//
//            @Override
//            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
//                showShortToast("测试Http请求:翻译library结果为\n" + resultJson);
//            }
//        });
//
//    }

    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    private ImageView ivAboutGesture;

    private TextView tvAboutAppInfo;

    private ImageView ivAboutQRCode;
//    @Override
//    public void initView() {
//
//        ivAboutGesture = findView(R.id.ivAboutGesture);
//        ivAboutGesture.setVisibility(SettingUtil.isFirstStart ? View.VISIBLE : View.GONE);
//        if (SettingUtil.isFirstStart) {
//            ivAboutGesture.setImageResource(R.drawable.gesture_left);
//        }
//
//        tvAboutAppInfo = findView(R.id.tvAboutAppInfo);
//
//        ivAboutQRCode = findView(R.id.ivAboutQRCode, this);
//    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








//
//
//    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
//
//    @Override
//    public void initData() {
//
//        tvAboutAppInfo.setText(DemoApplication.getInstance().getAppName()
//                + "\n" + DemoApplication.getInstance().getAppVersion());
//
//        setQRCode();
//    }
//
//
//    private Bitmap qRCodeBitmap;
//    /**显示二维码
//     */
//    protected void setQRCode() {
//        runThread(TAG + "setQRCode", new Runnable() {
//
//            @Override
//            public void run() {
//
//                try {
//                    qRCodeBitmap = EncodingHandler.createQRCode(Constant.APP_DOWNLOAD_WEBSITE
//                            , (int) (2 * getResources().getDimension(R.dimen.qrcode_size)));
//                } catch (WriterException e) {
//                    e.printStackTrace();
//                    Log.e(TAG, "initData  try {Bitmap qrcode = EncodingHandler.createQRCode(contactJson, ivContactQRCodeCode.getWidth());" +
//                            " >> } catch (WriterException e) {" + e.getMessage());
//                }
//
//                runUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        ivAboutQRCode.setImageBitmap(qRCodeBitmap);
//                    }
//                });
//            }
//        });
//    }

    /**下载应用
//     */
//    private void downloadApp() {
//        showProgressDialog("正在下载...");
//        runThread(TAG + "downloadApp", new Runnable() {
//            @Override
//            public void run() {
//                File file = DownloadUtil.downLoadFile(context, "ZBLibraryDemo", ".apk", Constant.APP_DOWNLOAD_WEBSITE);
//                dismissProgressDialog();
//                DownloadUtil.openFile(context, file);
//            }
//        });
//    }

    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

//    @Override
//    public void initEvent() {
//
////        findView(R.id.llAboutMainTabActivity).setOnClickListener(this);
////        findView(R.id.llAboutZBLibraryMainActivity).setOnClickListener(this);
//
//        findView(R.id.llAboutUpdate).setOnClickListener(this);
//        findView(R.id.llAboutShare).setOnClickListener(this);
//        findView(R.id.llAboutComment).setOnClickListener(this);
//
//        findView(R.id.llAboutDeveloper, this).setOnLongClickListener(this);
//        findView(R.id.llAboutWeibo, this).setOnLongClickListener(this);
//        findView(R.id.llAboutContactUs, this).setOnLongClickListener(this);
//    }


//    @Override
//    public void onDragBottom(boolean rightToLeft) {
//        if (rightToLeft) {
//            toActivity(WebViewActivity.createIntent(context, "博客", Constant.APP_OFFICIAL_BLOG));
//
//            ivAboutGesture.setImageResource(R.drawable.gesture_right);
//            return;
//        }
//
//        if (SettingUtil.isFirstStart) {
//            runThread(TAG + "onDragBottom", new Runnable() {
//                @Override
//                public void run() {
//                    Log.i(TAG, "onDragBottom  >> SettingUtil.putBoolean(context, SettingUtil.KEY_IS_FIRST_IN, false);");
//                    SettingUtil.putBoolean(SettingUtil.KEY_IS_FIRST_START, false);
//                }
//            });
//        }
//
//        finish();
//    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.llAboutMainTabActivity:
//                startActivity(MainTabActivity.createIntent(context).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//                overridePendingTransition(R.anim.bottom_push_in, R.anim.hold);
//
//                enterAnim = exitAnim = R.anim.null_anim;
//                finish();
//                break;
////            case R.id.llAboutZBLibraryMainActivity:
////                startActivity(DemoMainActivity.createIntent(context));
////                overridePendingTransition(R.anim.bottom_push_in, R.anim.hold);
////                break;
//
//            case R.id.llAboutUpdate:
//                toActivity(WebViewActivity.createIntent(context, "更新日志", Constant.UPDATE_LOG_WEBSITE));
//                break;
//            case R.id.llAboutShare:
//                CommonUtil.shareInfo(context, getString(R.string.share_app) + "\n 点击链接直接查看ZBLibrary\n" + Constant.APP_DOWNLOAD_WEBSITE);
//                break;
//            case R.id.llAboutComment:
//                showShortToast("应用未上线不能查看");
//                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("market://details?id=" + getPackageName())));
//                break;
//
//            case R.id.llAboutDeveloper:
//                toActivity(WebViewActivity.createIntent(context, "开发者", Constant.APP_DEVELOPER_WEBSITE));
//                break;
//            case R.id.llAboutWeibo:
//                toActivity(WebViewActivity.createIntent(context, "博客", Constant.APP_OFFICIAL_BLOG));
//                break;
//            case R.id.llAboutContactUs:
//                CommonUtil.sendEmail(context, Constant.APP_OFFICIAL_EMAIL);
//                break;
//
//            case R.id.ivAboutQRCode:
//                downloadApp();
//                break;
//            default:
//                break;
//        }
//    }

//    @Override
//    public boolean onLongClick(View v) {
//        switch (v.getId()) {
//            case R.id.llAboutDeveloper:
//                CommonUtil.copyText(context, Constant.APP_DEVELOPER_WEBSITE);
//                return true;
//            case R.id.llAboutWeibo:
//                CommonUtil.copyText(context, Constant.APP_OFFICIAL_BLOG);
//                return true;
//            case R.id.llAboutContactUs:
//                CommonUtil.copyText(context, Constant.APP_OFFICIAL_EMAIL);
//                return true;
//            default:
//                break;
//        }
//        return false;
//    }



    //生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



    //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>







    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
