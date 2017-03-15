/*
 * 文件名: XMLifeWebActivity.java
 * 版    权：  Copyright Paitao Tech. Co. Ltd. All Rights Reserved.
 * 描    述: 用来显示webview的页面
 * 创建人: gongshenghu
 * 创建时间:Jul 17, 2014
 *
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.shudongedu.energy.ui.basic;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.shudongedu.energy.R;
import com.shudongedu.energy.log.Logger;
import com.shudongedu.energy.ui.basic.widget.ScrollWebView;

/**
 * 用来显示webview的页面<BR>
 */
public abstract class BaseWebFragment extends BasicTitleBarFragment {


  /**
   * 日志打印的log
   */
  private static final String TAG = "BaseWebFragment";

  /**
   * WebView
   */
  protected ScrollWebView mWebView;

  private View mLoadingLayout;


  @Override
  public int getLayoutId() {
    return R.layout.xm_life_web;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initViews(view);
    mLoadingLayout.setVisibility(View.VISIBLE);
    if (!TextUtils.isEmpty(getUrl())) {
      loadUrl(mWebView, getUrl());
    }
  }

  protected abstract String getUrl();

  @SuppressLint({"SetJavaScriptEnabled", "NewApi"})
  private void initViews(View rootView) {
    mWebView = (ScrollWebView) rootView.findViewById(R.id.xm_life_webview);
    mLoadingLayout = rootView.findViewById(R.id.loading_layout);
    WebSettings webSettings = mWebView.getSettings();
    //设置支持JS
    webSettings.setJavaScriptEnabled(true);
    //设置加载进来的页面自适应手机屏幕
    webSettings.setUseWideViewPort(true);
    //设置WebView加载的页面的模式，适应屏幕宽度
    webSettings.setLoadWithOverviewMode(true);
    //设置支持缩放
    webSettings.setSupportZoom(true);
    webSettings.setBuiltInZoomControls(true);
    webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    webSettings.setAllowFileAccess(true);
    webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
    webSettings.setDomStorageEnabled(true);
    webSettings.setDatabaseEnabled(true);

    //添加JS调用java接口
    //mWebView.addJavascriptInterface(new DealJs(), JS_INTERFACE_NAME);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
      //不显示缩放的按钮
      webSettings.setDisplayZoomControls(false);
    }
    mWebView.setScrollCallback(new ScrollWebView.ScrollCallback() {
      @Override
      public void callback(int l, int t, int oldl, int oldt) {
        Logger.d(TAG, "i : %d, i1 : %d, i2 : %d, i3 : %d", l, t, oldl, oldt);
        onScrollChanged(l, t, oldl, oldt);
      }
    });
    mWebView.setWebViewClient(new BridgeWebViewClient(mWebView));
    mWebView.setWebChromeClient(new WebChromeClient() {
      @Override
      public void onProgressChanged(WebView view, int progress) {
        Logger.d(TAG, "current progress: " + progress);
        //载入进度改变而触发
        if (progress == 100) {
          //如果全部载入,隐藏进度对话框
          //closeProgressDialog();
          mLoadingLayout.setVisibility(View.GONE);
        }
        super.onProgressChanged(view, progress);
      }

      @Override
      public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        setMiddleTitle(title);
      }
    });
  }

  protected void onScrollChanged(int i, int i1, int i2, int i3) {

  }

  /**
   * 加载Url<BR>
   *
   * @param view WebView
   * @param url URL
   */
  private void loadUrl(WebView view, String url) {
    if (view != null) {
      view.loadUrl(url);
    }
    //showProgressDialog(getString(R.string.dialog_loading), true);
  }

  private void loadData(WebView view, String data) {
    if (view != null) {
      view.loadData(data, "text/html; charset=UTF-8", null);
    }
    //showProgressDialog(getString(R.string.dialog_loading), true);
  }


  @Override
  public void onDestroy() {
    super.onDestroy();
    mWebView.removeAllViews();
    mWebView.destroy();
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  private class CustomScrollChangeListener implements View.OnScrollChangeListener {
    private int mLastPos = 0;

    @Override
    public void onScrollChange(View view, int i, int i1, int i2, int i3) {

    }
  }

}
