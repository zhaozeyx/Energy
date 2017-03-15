package com.shudongedu.energy.ui.basic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.shudongedu.energy.R;
import com.shudongedu.energy.ui.basic.titlebar.TitleBar;

public abstract class BasicTitleBarFragment extends BasicFragment
    implements BasicTitleBarInterface {

  /**
   * TitleBar
   */
  private TitleBar mTitleBar;

  /**
   * 子视图的容器
   */
  private FrameLayout mContentContainer;

  /**
   * 根视图
   */
  private View mRootView = null;

  /**
   * 默认视图 在未登录的时候展示
   */
  //private DefaultView mDefaultView;

  /**
   * 登录后显示的真实数据视图
   */
  private View mContentView;

  private BasicTitleBarInterface mTitleBarInterface;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    mRootView = inflater.inflate(R.layout.content_container, null);

    initViews(inflater);

    mContentContainer = (FrameLayout) mRootView.findViewById(R.id.content_container);

    initContainer(inflater);
    return mRootView;
  }

  private void initContainer(LayoutInflater inflater) {

    mContentView = inflater.inflate(getLayoutId(), null);
    mContentContainer.addView(mContentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
        .LayoutParams.MATCH_PARENT);
  }

  /**
   * 界面初始化 <BR>
   */
  private void initViews(LayoutInflater inflater) {
    TitleBar.TitleBarStyle style = getTitleBarStyle();
    switch (style) {
      case NORMAL:
        mRootView = inflater.inflate(R.layout.content_container, null);
        break;
      case TRANSPARENT:
        mRootView = inflater.inflate(R.layout.content_container_trans, null);
        break;
      default:
        mRootView = inflater.inflate(R.layout.content_container, null);
        break;
    }
    mTitleBar = (TitleBar) mRootView.findViewById(R.id.title_bar_layout);
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