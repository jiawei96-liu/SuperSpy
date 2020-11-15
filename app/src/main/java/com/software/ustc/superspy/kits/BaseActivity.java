package com.software.ustc.superspy.kits;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.software.ustc.superspy.R;

import java.util.List;

//AppCompatActivity和Activity和使用上区别是：顶部是否有工具条；
//public class MainActivity extends AppCompatActivity
public class BaseActivity extends AppCompatActivity /*Activity*/ {
    private static final String TAG = "BaseActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//隐藏标题栏
        Log.d(TAG, getClass().getSimpleName());
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }



    /**
     * 退出时之前的界面进入动画,可在finish();前通过改变它的值来改变动画效果
     */
    protected int enterAnim = R.anim.fade;
    /**
     * 退出时该界面动画,可在finish();前通过改变它的值来改变动画效果
     */
    protected int exitAnim = R.anim.right_push_out;

    /**通过id查找并获取控件，使用时不需要强转
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public <V extends View> V findView(int id) {
        return (V) findViewById(id);
    }
    /**通过id查找并获取控件，并setOnClickListener
     * @param id
     * @param l
     * @return
     */
    public <V extends View> V findView(int id, View.OnClickListener l) {
        V v = findView(id);
        v.setOnClickListener(l);
        return v;
    }
    /**通过id查找并获取控件，并setOnClickListener
     * @param id
     * @param l
     * @return
     */
    public <V extends View> V findViewById(int id, View.OnClickListener l) {
        return findView(id, l);
    }
    //自动设置标题方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**自动把标题设置为上个Activity传入的INTENT_TITLE，建议在子类initView中使用
     * *这个方法没有return，tvTitle = tvBaseTitle，直接用tvBaseTitle
     * @must 在UI线程中调用
     */
    String INTENT_TITLE;
    protected TextView tvBaseTitle;
    protected void autoSetTitle(String str) {
        INTENT_TITLE = str;
        tvBaseTitle = autoSetTitle(tvBaseTitle);
    }
    /**自动把标题设置为上个Activity传入的INTENT_TITLE，建议在子类initView中使用
     * @param tvTitle
     * @return tvTitle 返回tvTitle是为了可以写成一行，如 tvTitle = autoSetTitle((TextView) findViewById(titleResId));
     * @must 在UI线程中调用
     */
    protected TextView autoSetTitle(TextView tvTitle) {
        if (tvTitle != null && StringUtil.isNotEmpty(getIntent().getStringExtra(INTENT_TITLE), false)) {
            tvTitle.setText(StringUtil.getCurrentString());
        }
        return tvTitle;
    }

    //自动设置标题方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //启动新Activity方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**打开新的Activity，向左滑入效果
     * @param intent
     */
    public void toActivity(Intent intent) {
        toActivity(intent, true);
    }
    /**打开新的Activity
     * @param intent
     * @param showAnimation
     */
    public void toActivity(Intent intent, boolean showAnimation) {
        toActivity(intent, -1, showAnimation);
    }
    /**打开新的Activity，向左滑入效果
     * @param intent
     * @param requestCode
     */
    public void toActivity(Intent intent, int requestCode) {
        toActivity(intent, requestCode, true);
    }
    /**打开新的Activity
     * @param intent
     * @param requestCode
     * @param showAnimation
     */
    public void toActivity(final Intent intent, final int requestCode, final boolean showAnimation) {
        runUiThread(new Runnable() {
            @Override
            public void run() {
                if (intent == null) {
                    Log.w(TAG, "toActivity  intent == null >> return;");
                    return;
                }
                //fragment中使用context.startActivity会导致在fragment中不能正常接收onActivityResult
                if (requestCode < 0) {
                    startActivity(intent);
                } else {
                    startActivityForResult(intent, requestCode);
                }
                if (showAnimation) {
                    overridePendingTransition(R.anim.right_push_in, R.anim.hold);
                } else {
                    overridePendingTransition(R.anim.null_anim, R.anim.null_anim);
                }
            }
        });
    }
    //启动新Activity方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private boolean isAlive = false;
    protected BaseActivity context = null;

//    @Override

    public final boolean isAlive() {
        return isAlive && context != null;// & ! isFinishing();导致finish，onDestroy内runUiThread不可用
    }

    //运行线程 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**在UI线程中运行，建议用这个方法代替runOnUiThread
     * @param action
     */
    public final void runUiThread(Runnable action) {
        if (isAlive() == false) {
            Log.w(TAG, "runUiThread  isAlive() == false >> return;");
            return;
        }
        runOnUiThread(action);
    }
    /**
     * 线程名列表
     */
    protected List<String> threadNameList;
    /**运行线程
     * @param name
     * @param runnable
     * @return
     */
//    public final Handler runThread(String name, Runnable runnable) {
//        if (isAlive() == false) {
//            Log.w(TAG, "runThread  isAlive() == false >> return null;");
//            return null;
//        }
//        name = StringUtil.getTrimedString(name);
//        Handler handler = ThreadManager.getInstance().runThread(name, runnable);
//        if (handler == null) {
//            Log.e(TAG, "runThread handler == null >> return null;");
//            return null;
//        }
//
//        if (threadNameList.contains(name) == false) {
//            threadNameList.add(name);
//        }
//        return handler;
//    }

    //运行线程 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
