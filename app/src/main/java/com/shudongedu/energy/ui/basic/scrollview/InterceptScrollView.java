package com.shudongedu.energy.ui.basic.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

public class InterceptScrollView extends ScrollView {
  private int downX;
  private int downY;
  private int mTouchSlop;
  private OnOverScrolledListener mOverScrolledListener;
  private OnScrollChangedListener mOnScrollChangeListener;

  public InterceptScrollView(Context context) {
    super(context);
    mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
  }

  public InterceptScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);
    mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
  }

  public InterceptScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent e) {
    int action = e.getAction();
    switch (action) {
      case MotionEvent.ACTION_DOWN:
        downX = (int) e.getRawX();
        downY = (int) e.getRawY();
        break;
      case MotionEvent.ACTION_MOVE:
        int moveY = (int) e.getRawY();
        if (Math.abs(moveY - downY) > mTouchSlop) {
          return true;
        }
    }
    return super.onInterceptTouchEvent(e);
  }

  @Override
  protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
    super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    if (null != mOverScrolledListener) {
      mOverScrolledListener.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }
  }

  @Override
  protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    super.onScrollChanged(l, t, oldl, oldt);
    if(null != mOnScrollChangeListener) {
      mOnScrollChangeListener.onScrollChanged(l, t, oldl, oldt);
    }
  }

  public void setOnOverScrolledListener(OnOverScrolledListener listener) {
    mOverScrolledListener = listener;
  }

  public void setOnScrollChangeListener(OnScrollChangedListener listener) {
    mOnScrollChangeListener = listener;
  }

  public interface OnOverScrolledListener {
    void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY);
  }

  public interface OnScrollChangedListener {
    void onScrollChanged(int l, int t, int oldl, int oldt);
  }
}