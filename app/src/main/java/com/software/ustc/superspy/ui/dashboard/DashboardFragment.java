package com.software.ustc.superspy.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.software.ustc.superspy.activity.AppInfoShowActivity;
import com.software.ustc.superspy.activity.AppUsageAnalysisActivity;
import com.software.ustc.superspy.R;
import com.software.ustc.superspy.activity.SysInfoShowActivity;

public class DashboardFragment extends Fragment implements View.OnClickListener {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button btnApps = (Button) getActivity().findViewById(R.id.btn_apps);
        btnApps.setOnClickListener(this);
        Button btnSyss = (Button) getActivity().findViewById(R.id.btn_sys);
        btnSyss.setOnClickListener(this);
        Button btnAppUsages = (Button) getActivity().findViewById(R.id.btn_app_usage_list);
        btnAppUsages.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_apps:
                startActivity(new Intent(getActivity(), AppInfoShowActivity.class));
                break;
            case R.id.btn_sys:
                startActivity(new Intent(getActivity(), SysInfoShowActivity.class));
                break;
            case R.id.btn_app_usage_list:
                startActivity(new Intent(getActivity(), AppUsageAnalysisActivity.class));
                break;
            default:
                break;
        }
    }
}