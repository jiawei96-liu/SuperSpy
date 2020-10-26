package com.software.ustc.superspy.kits;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.software.ustc.superspy.R;

import java.util.List;

public class AppInfoAdapter extends ArrayAdapter<AppInfo> {
    private int resId;

    public AppInfoAdapter(@NonNull Context context, int resource, @NonNull List<AppInfo> objects) {
        super(context, resource, objects);
        resId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AppInfo appInfo = getItem(position);
        View view=LayoutInflater.from(getContext()).inflate(resId,parent,false);

        ImageView appIcon = (ImageView)view.findViewById(R.id.iv_icon);
        TextView appName = (TextView)view.findViewById(R.id.txt_app_name);
//        TextView appVersion = (TextView)view.findViewById(R.id.txt_app_version);
        TextView appPackageName = (TextView)view.findViewById(R.id.txt_app_package_name);
//        TextView appDir = (TextView)view.findViewById(R.id.txt_app_dir);
        TextView appSize = (TextView)view.findViewById(R.id.txt_app_size);

        appIcon.setImageDrawable(appInfo.getAppIcon());
        appName.setText(appInfo.getAppName());
//        appVersion.setText("版本:"+appInfo.getAppVersion());
        appPackageName.setText(appInfo.getAppPackageName());
//        appDir.setText(appInfo.getAppDir());
        appSize.setText(Integer.toString(appInfo.getAppSize())+"M");

        return view;
    }
}
