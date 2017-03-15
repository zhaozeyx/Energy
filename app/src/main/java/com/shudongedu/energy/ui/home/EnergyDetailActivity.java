package com.shudongedu.energy.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.shudongedu.energy.R;
import com.shudongedu.energy.ui.basic.BaseWebActivity;

/*
 * 文件名: EnergyDetailActivity
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:2017/3/10
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */

public class EnergyDetailActivity extends BaseWebActivity {
  public static final String RESULT_KEY_DATA = "data";
  private static final String BUNDLE_KEY_URL = "BUNDLE_KEY_URL";

  private String mUrl;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    mUrl = getIntent().getStringExtra(BUNDLE_KEY_URL);
    super.onCreate(savedInstanceState);

    mWebView.registerHandler("onClick", new BridgeHandler() {
      @Override
      public void handler(String data, CallBackFunction function) {
        // TODO 关闭界面 返回值到前一页面
        Intent intent = new Intent();
        intent.putExtra(RESULT_KEY_DATA, data);
        setResult(RESULT_OK, intent);
        finish();
      }
    });
  }

  @Override
  public boolean initializeTitleBar() {
    setLeftTitleButton(R.drawable.btn_title_bar_back_selector, new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });
    return true;
  }

  @Override
  protected String getUrl() {
    return "file:///android_asset/html/" + mUrl;
  }

  public static Intent makeIntent(Context context, String url) {
    Intent intent = new Intent(context, EnergyDetailActivity.class);
    intent.putExtra(BUNDLE_KEY_URL, url);
    return intent;
  }
}
