/*
 * 文件名: TaskFragment
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:2016/12/13
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.shudongedu.energy.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.shudongedu.energy.R;
import com.shudongedu.energy.codebar.CodeBarCaptureActivity;
import com.shudongedu.energy.log.Logger;
import com.shudongedu.energy.ui.basic.BaseWebFragment;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 2016/12/13]
 */
public class HomeFragment extends BaseWebFragment {
  private static final int REQUEST_CODE_DETAIL = 1;
  private int mBarHeight;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
      savedInstanceState) {
    View view = super.onCreateView(inflater, container, savedInstanceState);
    return view;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mBarHeight = getResources().getDimensionPixelOffset(R.dimen.title_bar_height);
    mWebView.registerHandler("submitFromWeb", new BridgeHandler() {
      @Override
      public void handler(String data, CallBackFunction function) {
        startActivityForResult(EnergyDetailActivity.makeIntent(getActivity(), data),
            REQUEST_CODE_DETAIL);
      }
    });
  }

  @Override
  protected String getUrl() {
    return "file:///android_asset/html/index.html";
  }

  @Override
  public boolean initializeTitleBar() {
    setLeftTitleButton(R.drawable.icon_scan, new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(CodeBarCaptureActivity.makeCodeBarCaptureIntent(getActivity()));
      }
    });
    return true;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode != Activity.RESULT_OK) {
      return;
    }
    switch (requestCode) {
      case REQUEST_CODE_DETAIL:
        String returnData = data.getStringExtra(EnergyDetailActivity.RESULT_KEY_DATA);
        mWebView.callHandler("functionInJs", returnData, new CallBackFunction() {
          @Override
          public void onCallBack(String data) {

          }
        });
        break;
    }
  }

  @Override
  protected void onScrollChanged(int i, int i1, int i2, int i3) {
    float alpha = (float) i / mBarHeight;
    Logger.d("YZZ", "alpha ---> %f, mBarHeight %d ", alpha, mBarHeight);
    setTitleBarAlpha(alpha >= 1.0f ? 1.0f : (alpha));
  }
}
