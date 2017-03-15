package com.shudongedu.energy.ui.basic.titlebar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.shudongedu.energy.R;

public class TitleBar extends FrameLayout {
  /**
   * 标题栏左侧按钮
   */
  private ImageView mLeftIconButton;

  /**
   * 标题栏左侧标题
   */
  private Button mLeftSubTitleButton;

  /**
   * 标题栏右边第一个按钮
   */
  private ImageView mRightIconButtonFirst;

  /**
   * 标题栏右边第二个按钮
   */
  private TextView mRightTextButtonSecond;


  /**
   * 标题栏
   */
  private TextView mTitleView;

  /**
   * TitleBar的总体布局
   */
  private View mTitleBarLayout;

  private View mAvatarContainer;

  private ImageView mAvatarImgView;

  private View mAvatarNewMsgView;

  private View mBgView;

  /**
   * 标题栏
   *
   * @param context Context
   * @param attrs AttributeSet
   */
  public TitleBar(Context context, AttributeSet attrs) {
    super(context, attrs);
    LayoutInflater inflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    mTitleBarLayout = inflater.inflate(R.layout.title_bar_base, null);
    LinearLayout.LayoutParams layoutParams =
        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
    addView(mTitleBarLayout, layoutParams);
    init();
  }

  /**
   * 不显示标题栏
   */
  public void hideTitleBar() {
    setVisibility(View.GONE);
  }

  /**
   * 显示标题栏
   */
  public void showTitleBar() {
    setVisibility(View.VISIBLE);
  }

  /**
   * 设置标题<BR>
   *
   * @param textTitle 中间
   * @param leftDrawableId 左边图片
   * @param rFirstDrawableId 右边第一个button图片
   * @param rSecondTextId 右边第二个button文字
   */
  public void setTitle(int textTitle, int leftDrawableId, int rFirstDrawableId, int rSecondTextId) {
    setTitleText(textTitle);
    setLeftButtonDrawable(leftDrawableId);
    setRightImgButtonDrawable(rFirstDrawableId);
    setRightTextButtonText(rSecondTextId);
  }

  /**
   * 设置标题为图片<BR>
   *
   * @param drawableId 图片id
   */
  public void setTitleDrawable(int drawableId) {
    mTitleView.setVisibility(View.VISIBLE);
    mTitleView.setBackgroundResource(drawableId);
  }

  /**
   * 设置左边button图片<BR>
   *
   * @param drawableId 图片资源ID
   */
  public void setLeftButtonDrawable(int drawableId) {
    mLeftIconButton.setImageResource(drawableId);
  }

  /**
   * 设置title文字<BR>
   *
   * @param titleId 文字资源ID
   */
  public void setTitleText(int titleId) {
    mTitleView.setText(titleId);
  }

  /**
   * 设置title文字<BR>
   *
   * @param titleStr 文字字符串
   */
  public void setTitleText(CharSequence titleStr) {
    mTitleView.setText(titleStr);
  }

  /**
   * 设置右边第一个button图片<BR>
   *
   * @param drawableId 图片资源ID
   */
  public void setRightImgButtonDrawable(int drawableId) {
    mRightIconButtonFirst.setImageResource(drawableId);
  }

  /**
   * 设置右边第二个button文字<BR>
   *
   * @param textId 文字资源ID
   */
  public void setRightTextButtonText(int textId) {
    mRightTextButtonSecond.setText(textId);
  }

  /**
   * 设置右边第二个button文字颜色<BR>
   *
   * @param colorId 颜色资源ID
   */
  public void setRightTextButtonTextColor(int colorId) {
    mRightTextButtonSecond.setTextColor(colorId);
  }

  /**
   * 获得左边button<BR>
   *
   * @return 左边button
   */
  public ImageView getLeftButton() {
    return mLeftIconButton;
  }

  /**
   * 获得左边副标题的对象<BR>
   *
   * @return 左边副标题的对象
   */
  public Button getLeftSubTitleBtn() {
    return mLeftSubTitleButton;
  }

  /**
   * 获得右边第二个button<BR>
   *
   * @return 右边button
   */
  public TextView getRightButtonText() {
    return mRightTextButtonSecond;
  }

  /**
   * 获得右边第一个button<BR>
   *
   * @return 右边button
   */
  public ImageView getRightButtonImg() {
    return mRightIconButtonFirst;
  }

  /**
   * 设置左边button事件监听<BR>
   *
   * @param listener OnClickListener
   */
  public void setLeftButtonClickListener(OnClickListener listener) {
    mLeftIconButton.setOnClickListener(listener);
  }

  /**
   * 设置右边button事件监听<BR>
   *
   * @param listener OnClickListener
   */
  public void setRightImgButtonClickListener(OnClickListener listener) {
    mRightIconButtonFirst.setOnClickListener(listener);
  }

  /**
   * 设置右边button事件监听<BR>
   *
   * @param listener OnClickListener
   */
  public void setRightTextButtonClickListener(OnClickListener listener) {
    mRightTextButtonSecond.setOnClickListener(listener);
  }

  /**
   * 设置右边第一个button是否显示<BR>
   *
   * @param show 是否显示
   */
  public void setRightImgButtonVisible(boolean show) {
    mRightIconButtonFirst.setVisibility(show ? View.VISIBLE : View.GONE);
  }

  /**
   * 设置右边第二个button是否显示<BR>
   *
   * @param show 是否显示
   */
  public void setRightTextButtonVisible(boolean show) {
    mRightTextButtonSecond.setVisibility(show ? VISIBLE : GONE);
  }

  /**
   * 设置左边button是否显示<BR>
   *
   * @param show 是否显示
   */
  public void setLeftButtonVisible(boolean show) {
    mLeftIconButton.setVisibility(show ? VISIBLE : GONE);
  }

  /**
   * 设置左边副标题是否显示<BR>
   *
   * @param show 是否显示
   */
  public void setLeftSubtTitleVisible(boolean show) {
    mLeftSubTitleButton.setVisibility(show ? VISIBLE : GONE);
  }


  /**
   * 设置titleBar类型<BR>
   *
   * @param style titleBar类型
   */
  public void setTitleBarStyle(TitleBarStyle style) {
    style.setStyleForTitleBar(mTitleBarLayout, mLeftIconButton, mRightIconButtonFirst, mTitleView);
  }

  /**
   * 设置左侧副标题文字<BR>
   *
   * @param subtTitle 文字
   */
  public void setLeftSubTitle(CharSequence subtTitle) {
    mLeftSubTitleButton.setVisibility(View.VISIBLE);
    mLeftSubTitleButton.setText(subtTitle);
  }

  /**
   * 设置左侧副标题监听<BR>
   *
   * @param listener 监听
   */
  public void setLeftSubTitleListener(OnClickListener listener) {
    mLeftSubTitleButton.setOnClickListener(listener);
  }

  public void setUserAvatarVisible(boolean show) {
    mAvatarContainer.setVisibility(show ? VISIBLE : GONE);
  }

  public ImageView getUserAvatarView() {
    return mAvatarImgView;
  }

  public void showOrDismissNewMsg(boolean show) {
    mAvatarNewMsgView.setVisibility(show ? View.VISIBLE : View.GONE);
  }

  /**
   * 设置标题字体颜色<BR>
   *
   * @param color 颜色id
   */
  public void setTextColor(int color) {
    mTitleView.setTextColor(color);
  }

  public void setBgAlpha(float alpha) {
    mBgView.setAlpha(alpha);
  }

  private void init() {
    mLeftIconButton = (ImageView) findViewById(R.id.title_bar_btn_left);
    mLeftSubTitleButton = (Button) findViewById(R.id.title_bar_subtitle_left);
    mRightIconButtonFirst = (ImageView) findViewById(R.id.title_bar_btn_right_img);
    mRightTextButtonSecond = (TextView) findViewById(R.id.title_bar_btn_right_text);
    mTitleView = (TextView) findViewById(R.id.title_bar_text_title);
    mAvatarContainer = findViewById(R.id.user_avatar_container);
    mAvatarImgView = (ImageView) findViewById(R.id.user_avatar);
    mAvatarNewMsgView = findViewById(R.id.status_new_msg);
    mBgView = findViewById(R.id.title_bg);
  }

  /**
   * 标题栏类型<BR>
   *
   * @author zhaozeyang
   */
  public enum TitleBarStyle {
    /**
     * 普通
     */
    NORMAL {
      @Override
      void setStyleForTitleBar(View titleBar, ImageView leftButton, ImageView rightButton,
                               TextView titleView) {
        //titleBar.setBackgroundResource(R.drawable.bg_titlebar_repeat);
      }
    },
    /**
     * 透明
     */
    TRANSPARENT {
      @Override
      void setStyleForTitleBar(View titleBar, ImageView leftButton, ImageView rightButton,
                               TextView titleView) {
        titleBar.setBackgroundResource(android.R.color.transparent);
        leftButton.setBackgroundResource(android.R.color.transparent);
        rightButton.setBackgroundResource(android.R.color.transparent);
      }
    };

    abstract void setStyleForTitleBar(View titleBar, ImageView leftButton, ImageView rightButton,
                                      TextView titleView);
  }
}