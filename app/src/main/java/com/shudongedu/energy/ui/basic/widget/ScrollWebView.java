package com.shudongedu.energy.ui.basic.widget;

import android.content.Context;
import android.util.AttributeSet;
import com.github.lzyzsd.jsbridge.BridgeWebView;

/*
 * 文件名: ScrollWebView
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:2017/3/13
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */

public class ScrollWebView extends BridgeWebView {
  private ScrollCallback mScrollCallback;

  public ScrollWebView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    super.onScrollChanged(l, t, oldl, oldt);
    if (null != mScrollCallback) {
      mScrollCallback.callback(l, t, oldl, oldt);
    }
  }

  public void setScrollCallback(ScrollCallback callback) {
    mScrollCallback = callback;
  }

  public interface ScrollCallback {
    void callback(int l, int t, int oldl, int oldt);
  }
}
