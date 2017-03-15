/*
 * 文件名: BasicTitlebarInterfaceImp.java
 * 版    权：  Copyright Paitao Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:2013-11-12
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.shudongedu.energy.ui.basic;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.shudongedu.energy.ui.basic.titlebar.TitleBar;


/**
 * 定义公共的titleBar方法<BR>
 *
 * @author zhaozeyang
 */
public class BasicTitleBarInterfaceImp implements BasicTitleBarInterface {

  private TitleBar mTitleBar;

  /**
   * 构造方法
   *
   * @param titleBar TitleBar对象
   */
  public BasicTitleBarInterfaceImp(TitleBar titleBar) {
    mTitleBar = titleBar;
  }

  @Override
  public void setLeftButtonListener(OnClickListener listener) {
    mTitleBar.setLeftButtonClickListener(listener);
  }

  @Override
  public void setRightImgBtnClickListener(OnClickListener listener) {
    mTitleBar.setRightImgButtonClickListener(listener);
  }

  @Override
  public void setRightTexBtnListener(OnClickListener listener) {
    mTitleBar.setRightTextButtonClickListener(listener);
  }

  @Override
  public int getLayoutId() {
    return 0;
  }

  @Override
  public void setLeftTitleButton(int ldrawableId, OnClickListener listener) {
    if (0 == ldrawableId) {
      mTitleBar.setLeftButtonVisible(false);
      return;
    }
    mTitleBar.setLeftButtonVisible(true);
    if (0 != ldrawableId) {
      mTitleBar.setLeftButtonDrawable(ldrawableId);
    }
    if (null != listener) {
      mTitleBar.getLeftButton().setOnClickListener(listener);
    }
  }

  @Override
  public void setLeftSubTitle(CharSequence subTitle, OnClickListener listener) {
    mTitleBar.setLeftSubTitle(subTitle);
    mTitleBar.setLeftSubTitleListener(listener);
  }

  @Override
  public void setLeftSubTitle(CharSequence subTitle) {
    mTitleBar.setLeftSubTitle(subTitle);
  }

  @Override
  public void setRightImgButton(int rDrawableId, OnClickListener listener) {
    if (0 == rDrawableId) {
      mTitleBar.setRightImgButtonVisible(false);
      return;
    }
    mTitleBar.setRightImgButtonVisible(true);
    if (0 != rDrawableId) {
      mTitleBar.setRightImgButtonDrawable(rDrawableId);
    }
    if (null != listener) {
      mTitleBar.getRightButtonImg().setOnClickListener(listener);
    }
  }

  @Override
  public void setRightTextButton(int textId, OnClickListener listener) {
    if (0 == textId) {
      mTitleBar.setRightTextButtonVisible(false);
      return;
    }
    mTitleBar.setRightTextButtonVisible(true);
    if (0 != textId) {
      mTitleBar.setRightTextButtonText(textId);
    }
    if (null != listener) {
      mTitleBar.getRightButtonText().setOnClickListener(listener);
    }
  }

  @Override
  public void setMiddleTitle(int titleId) {
    mTitleBar.setTitleText(titleId);
  }

  @Override
  public void setMiddleTitle(CharSequence titleStr) {
    mTitleBar.setTitleText(titleStr);
  }

  @Override
  public void setTitle(int textTitle, int lDrawableId, int rImgBtnSrc,
                       int rTextBtnSrc) {
    mTitleBar.setTitle(textTitle, lDrawableId, rImgBtnSrc, rTextBtnSrc);
  }

  @Override
  public boolean initializeTitleBar() {
    return false;
  }

  @Override
  public TitleBar.TitleBarStyle getTitleBarStyle() {
    return TitleBar.TitleBarStyle.NORMAL;
  }

  @Override
  public int getContentContainerBgId() {
    return -1;
  }

  @Override
  public void setTitleBarBackground(int backgroundId) {
    mTitleBar.setBackgroundResource(backgroundId);
  }

  @Override
  public void setTitleBarVisible(boolean isVisible) {
    mTitleBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
  }

  @Override
  public boolean getTitleBarVisible() {
    return mTitleBar.getVisibility() == View.VISIBLE;
  }

  @Override
  public void setMiddleTitleDrawable(int drawableId) {
    mTitleBar.setTitleDrawable(drawableId);
  }

  @Override
  public void setRightImgButtonVisible(boolean visible) {
    mTitleBar.setRightImgButtonVisible(visible);
  }

  @Override
  public void setRightTextButtonVisible(boolean visible) {
    mTitleBar.setRightTextButtonVisible(visible);
  }

  @Override
  public void setTitleBarTextColor(int color) {
    mTitleBar.setTextColor(color);
  }

  @Override
  public void setUserAvatarVisible(boolean show) {
    mTitleBar.setUserAvatarVisible(show);
  }

  @Override
  public ImageView getUserAvatarView() {
    return mTitleBar.getUserAvatarView();
  }

  @Override
  public void showOrDismissNewMsg(boolean show) {
    mTitleBar.showOrDismissNewMsg(show);
  }

  @Override
  public void setTitleBarAlpha(float alpha) {
    mTitleBar.setBgAlpha(alpha);
  }

  /**
   * 设置右边第二个button文字颜色<BR>
   *
   * @param colorId 颜色资源ID
   */
  @Override
  public void setRightTextButtonTextColor(int colorId) {
    mTitleBar.setRightTextButtonTextColor(colorId);
  }
}
