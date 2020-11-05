package com.software.ustc.superspy;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.software.ustc.superspy.kits.AppInfo;
import com.software.ustc.superspy.kits.BaseActivity;
import com.software.ustc.superspy.kits.PicUtil;

public class AppUsageShowActivity extends BaseActivity {
    private AppInfo appInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_usage);
        //获取传递过来的数据
        Bundle bundle=getIntent().getBundleExtra("appInfo");
        Bitmap appIron=getIntent().getParcelableExtra("appIron");
        appInfo = new AppInfo(appIron,bundle.getString("appName",""),
                bundle.getString("appPkgName",""),bundle.getString("appVersion",""),
                bundle.getString("appDir",""),bundle.getInt("appSize",0));

        ImageView appIcon = (ImageView)findViewById(R.id.iv_icon_in_usage);
        TextView appName = (TextView)findViewById(R.id.txt_app_name_in_usage);
        TextView appVersion = (TextView)findViewById(R.id.txt_app_version_in_usage);
        TextView appPackageName = (TextView)findViewById(R.id.txt_app_package_name_in_usage);
        TextView appDir = (TextView)findViewById(R.id.txt_app_dir_in_usage);
        TextView appSize = (TextView)findViewById(R.id.txt_app_size_in_usage);

        appIcon.setImageDrawable(PicUtil.BitmapToDrawable(PicUtil.CreateReflectionImageWithOrigin(appInfo.getAppIcon())));
        appName.setText(appInfo.getAppName());
        appVersion.setText("版本号: "+appInfo.getAppVersion());
        appPackageName.setText("包名: "+appInfo.getAppPackageName());
        appDir.setText("路径: "+appInfo.getAppDir());
        appSize.setText("大小: "+Integer.toString(appInfo.getAppSize())+" M");
    }
}