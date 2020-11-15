package com.software.ustc.superspy.activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

import com.software.ustc.superspy.R;

import com.software.ustc.superspy.kits.BaseActivity;


/**通用网页Activity
 * @author Lemon
 * @use toActivity(WebViewActivity.createIntent(...));
 */

///implements  OnClickListener
public class WebViewActivity2 extends BaseActivity {

    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    private WebView webView;
    private ProgressBar progressBar;

    /**获取启动这个Activity的Intent
     * @param title
     * @param url
     */
//    public static Intent createIntent(Context context, String title, String url) {
//        return new Intent(context, WebViewActivity.class).
//                putExtra("更新日志", title).
//                putExtra(WebViewActivity.INTENT_URL, url);
//
//    }

    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



    private String url;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view2);//传this是为了全局滑动返回

        //////////////////////////
        progressBar= (ProgressBar)findViewById(R.id.pbWebView2);//进度条

        webView = (WebView) findViewById(R.id.wvWebView2);
//        webView.loadUrl("file:///android_asset/test.html");//加载asset文件夹下html
        webView.loadUrl("http://139.196.35.30:8080/OkHttpTest/apppackage/test.html");//加载url
        webView.loadUrl("https://github.com/jiawei96-liu");

        //使用webview显示html代码
//        webView.loadDataWithBaseURL(null,"<html><head><title> 欢迎您 </title></head>" +
//                "<body><h2>使用webview显示 html代码</h2></body></html>", "text/html" , "utf-8", null);

//        webView.addJavascriptInterface(this,"android");//添加js监听 这样html就能调用客户端
//        webView.setWebChromeClient(webChromeClient);
//        webView.setWebViewClient(webViewClient);

        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js

        /**
         * LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
         * LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
         * LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
         * LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
         */
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存，只从网络获取数据.

        //支持屏幕缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);

        //不显示webview缩放按钮
//        webSettings.setDisplayZoomControls(false);
    }

    //WebViewClient主要帮助WebView处理各种通知、请求事件
    private WebViewClient webViewClient=new WebViewClient(){
        @Override
        public void onPageFinished(WebView view, String url) {//页面加载完成
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i("ansen","拦截url:"+url);
            if(url.equals("http://www.google.com/")){
                Toast.makeText(WebViewActivity2.this,"国内不能访问google,拦截该url",Toast.LENGTH_LONG).show();
                return true;//表示我已经处理过了
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

    };

    //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
    private WebChromeClient webChromeClient=new WebChromeClient(){
        //不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
        @Override
        public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
            androidx.appcompat.app.AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
            localBuilder.setMessage(message).setPositiveButton("确定",null);
            localBuilder.setCancelable(false);
            localBuilder.create().show();

            //注意:
            //必须要这一句代码:result.confirm()表示:
            //处理结果为确定状态同时唤醒WebCore线程
            //否则不能继续点击按钮
            result.confirm();
            return true;
        }

        //获取网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            Log.i("ansen","网页标题:"+title);
        }

        //加载进度回调
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            progressBar.setProgress(newProgress);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i("ansen","是否有上一个页面:"+webView.canGoBack());
        if (webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK){//点击返回按钮的时候判断有没有上一页
            webView.goBack(); // goBack()表示返回webView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    /**
     * JS调用android的方法
     * @param str
     * @return
     */
    @JavascriptInterface //仍然必不可少
    public void  getClient(String str){
        Log.i("ansen","html调用客户端:"+str);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //释放资源
        webView.destroy();
        webView=null;
    }
    //////////////////
//
//
//
////
////    url = StringUtil.getCorrectUrl(getIntent().getStringExtra(INTENT_URL));
////        if (StringUtil.isNotEmpty(url, true) == false) {
////            Log.e(TAG, "initData  StringUtil.isNotEmpty(url, true) == false >> finish(); return;");
////            enterAnim = exitAnim = R.anim.null_anim;
////            finish();
////
////            initView();
////            initData();
//
////            initEvent();
////            wvWebView = findView(R.id.wvWebView);
//
//
////            return;
////        }
//
//        //功能归类分区方法，必须调用<<<<<<<<<<
//
//        //功能归类分区方法，必须调用>>>>>>>>>>
//
//
//
//
//    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
//
//    private ProgressBar pbWebView;
//    private WebView wvWebView;
////    @Override
//    public void initView() {
//        String str = "更新日志";
//        autoSetTitle(str);
//
//        pbWebView = findView(R.id.pbWebView);
//        wvWebView = findView(R.id.wvWebView);
//        wvWebView.loadUrl("http://www.baidu.com/");
//        wvWebView.onResume();
//    }
//
//
//
//    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//
//
//
//
//
//
//
//
//
//
//    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
//
//
//    @SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
////    @Override
//    public void initData() {
//
//        WebSettings webSettings = wvWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//
//        wvWebView.requestFocus();
//
//        // 设置setWebChromeClient对象
//        wvWebView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onReceivedTitle(WebView view, String title) {
//                super.onReceivedTitle(view, title);
//                tvBaseTitle.setText(StringUtil.getTrimedString(title));
//            }
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                super.onProgressChanged(view, newProgress);
//                pbWebView.setProgress(newProgress);
//            }
//        });
//
//        wvWebView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url){
//                wvWebView.loadUrl(url);
//                return true;
//            }
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                tvBaseTitle.setText(StringUtil.getTrimedString(wvWebView.getUrl()));
//                pbWebView.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                tvBaseTitle.setText(StringUtil.getTrimedString(wvWebView.getTitle()));
//                pbWebView.setVisibility(View.GONE);
//            }
//        });
//
//        wvWebView.loadUrl(url);
//    }
//
//
//
//    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//
//
//
//
//
//
//
//
//    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
//
////    @Override
//    public void initEvent() {
//
//        tvBaseTitle.setOnClickListener(this);
//    }
//
////    @Override
//    public void onDragBottom(boolean rightToLeft) {
//        if (rightToLeft) {
//            if (wvWebView.canGoForward()) {
//                wvWebView.goForward();
//            }
//            return;
//        }
//        onBackPressed();
//    }
//
////    @Override
//    public void onReturnClick(View v) {
//        finish();
//    }
//
////
////    @Override
////    public void onClick(View v) {
////        if (v.getId() == R.id.tvBaseTitle) {
////            toActivity(EditTextInfoWindow.createIntent(context
////                    , EditTextInfoWindow.TYPE_WEBSITE
////                    , StringUtil.getTrimedString(tvBaseTitle)
////                    , wvWebView.getUrl()),
////                    REQUEST_TO_EDIT_TEXT_WINDOW, false);
////        }
////    }
//
//    //系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
//
//    @Override
//    public void onBackPressed() {
//        if (wvWebView.canGoBack()) {
//            wvWebView.goBack();
//            return;
//        }
//
//        super.onBackPressed();
//    }
//
//    //类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        wvWebView.onPause();
//    }
//
//    @Override
//    protected void onResume() {
//        wvWebView.onResume();
//        super.onResume();
//    }
//
////    @Override
////    protected void onDestroy() {
////        if (wvWebView != null) {
////            wvWebView.destroy();
////            wvWebView = null;
////        }
////        super.onDestroy();
////    }
//
//    @Override
//    public void onClick(View v) {
//
//    }
//
////    protected static final int REQUEST_TO_EDIT_TEXT_WINDOW = 1;
////    @Override
////    public void onActivityResult(int requestCode, int resultCode, Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////        if (resultCode != RESULT_OK) {
////            return;
////        }
////        switch (requestCode) {
////            case REQUEST_TO_EDIT_TEXT_WINDOW:
////                if (data != null) {
////                    wvWebView.loadUrl(StringUtil.getCorrectUrl(data.getStringExtra(EditTextInfoWindow.RESULT_VALUE)));
////                }
////                break;
////        }
////    }
//
//    //类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//
//    //系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
//
//
//    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//
//
//
}