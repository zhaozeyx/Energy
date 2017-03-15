/*
 * 文件名: BasicTitleBarActivity
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/4/18
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.shudongedu.energy.ui.basic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.shudongedu.energy.R;
import com.shudongedu.energy.ui.basic.titlebar.TitleBar;

/**
 * 带标题栏的Activity基类<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/18]
 */
public abstract class BasicTitleBarActivity extends BasicActivity implements
    BasicTitleBarInterface {

  /**
   * TitleBar
   */
  private TitleBar mTitleBar;

  /**
   * 子视图的容器
   */
  private FrameLayout mContentContainer;

  private BasicTitleBarInterface mTitleBarInterface;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LayoutInflater inflater = LayoutInflater.from(this);

    initTitleBarViews();

    mContentContainer = (FrameLayout) findViewById(R.id.content_container);
    if (getContentContainerBgId() != -1) {
      mContentContainer.setBackgroundResource(getContentContainerBgId());
    }

    View contentView = inflater.inflate(getLayoutId(), null);
    mContentContainer.addView(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
        .LayoutParams.MATCH_PARENT);
    afterCreate(savedInstanceState);
  }

  protected void afterCreate(Bundle savedInstance) {

  }

  /**
   * 标题栏初始化 <BR>
   */
  private void initTitleBarViews() {
    TitleBar.TitleBarStyle style = getTitleBarStyle();
    switch (style) {
      case NORMAL:
        setContentView(R.layout.content_container);
        break;
      case TRANSPARENT:
        setContentView(R.layout.content_container_trans);
        break;
      default:
        setContentView(R.layout.content_container);
        break;
    }
    mTitleBar = (TitleBar) findViewById(R.id.title_bar_layout);
    mTitleBarInterface = new BasicTitleBarInterfaceImp(mTitleBar);
    if (!initializeTitleBar()) {
      mTitleBar.hideTitleBar();
    }
  }

  @Override
  public void setLeftButtonListener(View.OnClickListener listener) {
    mTitleBarInterface.setLeftButtonListener(listener);
  }

  @Override
  public void setRightImgBtnClickListener(View.OnClickListener listener) {
    mTitleBarInterface.setRightImgBtnClickListener(listener);
  }

  @Override
  public void setRightTexBtnListener(View.OnClickListener listener) {
    mTitleBarInterface.setRightTexBtnListener(listener);
  }

  @Override
  public void setLeftSubTitle(CharSequence subTitle, View.OnClickListener listener) {
    mTitleBarInterface.setLeftSubTitle(subTitle, listener);
  }

  @Override
  public void setLeftSubTitle(CharSequence subTitle) {
    mTitleBarInterface.setLeftSubTitle(subTitle);
  }

  @Override
  public int getLayoutId() {
    return mTitleBarInterface.getLayoutId();
  }

  @Override
  public void setLeftTitleButton(int lDrawableId, View.OnClickListener listener) {
    mTitleBarInterface.setLeftTitleButton(lDrawableId, listener);
  }

  @Override
  public void setRightImgButton(int rDrawableId, View.OnClickListener listener) {
    mTitleBarInterface.setRightImgButton(rDrawableId, listener);
  }

  @Override
  public void setRightImgButtonVisible(boolean visible) {
    mTitleBarInterface.setRightImgButtonVisible(visible);
  }

  @Override
  public void setRightTextButton(int textId, View.OnClickListener listener) {
    mTitleBarInterface.setRightTextButton(textId, listener);
  }

  @Override
  public void setRightTextButtonTextColor(int colorId) {
    mTitleBarInterface.setRightTextButtonTextColor(colorId);
  }

  @Override
  public void setRightTextButtonVisible(boolean visible) {
    mTitleBarInterface.setRightTextButtonVisible(visible);
  }

  @Override
  public void setMiddleTitle(int titleId) {
    mTitleBarInterface.setMiddleTitle(titleId);
  }

  @Override
  public void setMiddleTitle(CharSequence titleStr) {
    mTitleBarInterface.setMiddleTitle(titleStr);
  }

  @Override
  public void setMiddleTitleDrawable(int drawableId) {
    mTitleBarInterface.setMiddleTitleDrawable(drawableId);
  }

  @Override
  public void setTitle(int textTitle, int lDrawableId, int rImgBtnSrc, int rTextBtnSrc) {
    mTitleBarInterface.setTitle(textTitle, lDrawableId, rImgBtnSrc, rTextBtnSrc);
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
    return mTitleBarInterface.getContentContainerBgId();
  }

  @Override
  public void setTitleBarBackground(int backgroundId) {
    mTitleBarInterface.setTitleBarBackground(backgroundId);
  }

  @Override
  public void setTitleBarVisible(boolean isVisible) {
    mTitleBarInterface.setTitleBarVisible(isVisible);
  }

  @Override
  public boolean getTitleBarVisible() {
    return mTitleBarInterface.getTitleBarVisible();
  }

  @Override
  public void setTitleBarTextColor(int color) {
    mTitleBarInterface.setTitleBarTextColor(color);
  }

  @Override
  public void setUserAvatarVisible(boolean show) {
    mTitleBarInterface.setUserAvatarVisible(show);
  }

  @Override
  public ImageView getUserAvatarView() {
    return mTitleBarInterface.getUserAvatarView();
  }

  @Override
  public void showOrDismissNewMsg(boolean show) {
    mTitleBarInterface.showOrDismissNewMsg(show);
  }

  @Override
  public void setTitleBarAlpha(float alpha) {
    mTitleBarInterface.setTitleBarAlpha(alpha);
  }
}
