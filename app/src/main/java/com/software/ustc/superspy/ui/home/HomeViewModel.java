package com.software.ustc.superspy.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("git@github.com:jiawei96-liu/SuperSpy.git\n刘佳维\n刘猛锐");
    }

    public LiveData<String> getText() {
        return mText;
    }
}