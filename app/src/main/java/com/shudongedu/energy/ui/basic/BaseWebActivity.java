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
import android.content.Context;
import android.content.Intent;
import android.content.UriMatcher;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.shudongedu.energy.R;
import com.shudongedu.energy.log.Logger;
import com.shudongedu.energy.ui.basic.widget.ScrollWebView;

/**
 * 用来显示webview的页面<BR>
 */
public abstract class BaseWebActivity extends BasicTitleBarActivity {

  /**
   * url
   */
  public static final String EXTRA_KEY_URL = "url";

  /**
   * data
   */
  private static final String EXTRA_KEY_DATA = "data";

  /**
   * 日志打印的log
   */
  private static final String TAG = "BaseWebActivity";

  /**
   * 小美快购scheme前缀
   */
  private static final String XMLIFE_SCHEME_PREFIX = "com.paitao.xmlife";

  /**
   * 产品id
   */
  private static final int PRODUCT_ID = 2;

  /**
   * WebView
   */
  protected ScrollWebView mWebView;
  private View mLoadingLayout;

  /**
   * UriMatcher
   */
  private UriMatcher mUriMatcher;

  @Override
  public int getLayoutId() {
    return R.layout.xm_life_web;
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initViews();
    initUriMatcher();
    mLoadingLayout.setVisibility(View.VISIBLE);
    if (!TextUtils.isEmpty(getUrl())) {
      loadUrl(mWebView, getUrl());
    }
    String loadData = getIntent().getStringExtra(EXTRA_KEY_DATA);
    if (!TextUtils.isEmpty(loadData)) {
      loadData(mWebView, loadData);
    }
  }

  protected abstract String getUrl();

  @SuppressLint({"SetJavaScriptEnabled", "NewApi"})
  private void initViews() {
    mWebView = (ScrollWebView) findViewById(R.id.xm_life_webview);
    mLoadingLayout = findViewById(R.id.loading_layout);
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
    webSettings.setAllowUniversalAccessFromFileURLs(true);

    webSettings.setUseWideViewPort(true);//设置webview自适应屏幕大小
    webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//设置，可能的话使所有列的宽度不超过屏幕宽度
    webSettings.setLoadWithOverviewMode(true);//设置webview自适应屏幕大小

    //添加JS调用java接口
    //mWebView.addJavascriptInterface(new DealJs(), JS_INTERFACE_NAME);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
      //不显示缩放的按钮
      webSettings.setDisplayZoomControls(false);
    }

    mWebView.setWebViewClient(new BridgeWebViewClient(mWebView));
    mWebView.setWebChromeClient(new WebChromeClient() {
      @Override
      public void onProgressChanged(WebView view, int progress) {
        Logger.d(TAG, "current progress: " + progress);
        //载入进度改变而触发
        if (progress == 100) {
          //如果全部载入,隐藏进度对话框
          mLoadingLayout.setVisibility(View.GONE);
        }
        super.onProgressChanged(view, progress);
      }

      @Override
      public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
      }
    });
  }

  /**
   * 初始化和注册需要的Uri<BR>
   */
  private void initUriMatcher() {
    mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    mUriMatcher.addURI("view", "product/*", PRODUCT_ID);
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
  }

  private void loadData(WebView view, String data) {
    if (view != null) {
      view.loadData(data, "text/html; charset=UTF-8", null);
    }
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    //捕捉返回键
    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
      mWebView.goBack();
      return true;
    } else if (keyCode == KeyEvent.KEYCODE_BACK) {
      onBackPressed();
      return true;
    }
    return super.onKeyDown(keyCode, event);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mWebView.removeAllViews();
    mWebView.destroy();
  }

  public static Intent makeXMLifeWebIntent(Context context, String url) {
    Intent intent = new Intent(context, BaseWebActivity.class);
    intent.putExtra(EXTRA_KEY_URL, url);
    return intent;
  }

  public static Intent makeServerUpholdIntent(Context context, String data) {
    return new Intent(context, BaseWebActivity.class).putExtra(EXTRA_KEY_DATA, data);
  }
}
