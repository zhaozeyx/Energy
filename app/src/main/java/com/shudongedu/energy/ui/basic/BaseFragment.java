package com.shudongedu.energy.ui.basic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import java.util.LinkedList;
import java.util.Queue;

public class BaseFragment extends Fragment {
  // 过场动画播放时长
  public static final int ANIMATION_DURATION = 400;

  public static final String KEY_CONTAINER_VIEW_IS_VISIBLE = "ContainerViewIsVisible";
  private BaseFragmentActivity mXmActivity;

  private BaseFragment mBelowFragment;
  private View mFragmentContainer;

  private Animation.AnimationListener mEnterAnimListener = new Animation.AnimationListener() {
    @Override
    public void onAnimationStart(Animation animation) {
      notifyEnterAnimStart();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
      notifyEnterAnimEnd();
    }
  };

  private Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
    @Override
    public boolean handleMessage(Message msg) {
      return BaseFragment.this.handleMessage(msg);
    }
  });

  private boolean mIsEnterAnimRunning = false;

  private Queue<Runnable> mDoAfterEnterAnim = new LinkedList<Runnable>();

  // 是否锁定的标志
  private boolean mLocked = false;

  // 解除锁定的Runnable
  private Runnable mUnlockRunnable = new Runnable() {
    @Override
    public void run() {
      mLocked = false;
    }
  };

  /**
   * 将fragment压入堆栈<BR>
   *
   * @param fragment fragment
   */
  protected void push(BaseFragment fragment) {
    mXmActivity.push(fragment);
  }

  /**
   * 将栈顶的fragment出栈<BR>
   */
  protected void pop() {
    mXmActivity.pop();
  }

  protected <T extends BaseFragment> boolean popToExistedInstance(Class<T> fragmentClass) {
    return mXmActivity.popToExistedInstance(fragmentClass);
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof BaseFragmentActivity) {
      mXmActivity = (BaseFragmentActivity) activity;
    }
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mBelowFragment = getBelowFragment();
    mFragmentContainer = view;
    mFragmentContainer.setVisibility(
        savedInstanceState == null || savedInstanceState.getBoolean(KEY_CONTAINER_VIEW_IS_VISIBLE,
            true) ? View.VISIBLE : View.INVISIBLE);
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    if (mFragmentContainer != null) {
      outState.putBoolean(KEY_CONTAINER_VIEW_IS_VISIBLE,
          mFragmentContainer.getVisibility() == View.VISIBLE);
    }
  }

  public BaseFragment getBelowFragment() {
    return mXmActivity != null ? mXmActivity.getFragmentBelow(this) : null;
  }

  public void hideBelowFragmentView() {
    BaseFragment belowFragment = mBelowFragment;
    if (belowFragment != null) {
      View fragmentView = belowFragment.mFragmentContainer;
      if (fragmentView != null) {
        fragmentView.setVisibility(View.INVISIBLE);
      }
    }
  }

  public void showBelowFragmentView() {
    BaseFragment belowFragment = mBelowFragment;
    if (belowFragment != null) {
      View fragmentView = belowFragment.mFragmentContainer;
      if (fragmentView != null) {
        fragmentView.setVisibility(View.VISIBLE);
      }
    }
  }

  @Override
  public final Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
    Animation anim;
    if (enter) {
      anim = onCreateEnterAnimation();
      if (anim != null) {
        anim.setAnimationListener(mEnterAnimListener);
      }
    } else {
      anim = onCreateExitAnimation();
      if (anim != null) {
        notifyExitAnimStart();
      }
    }
    return anim;
  }

  protected Animation onCreateEnterAnimation() {
    return null;
  }

  protected Animation onCreateExitAnimation() {
    return null;
  }

  /**
   * 在enter过场动画播放完毕后执行<BR>
   *
   * @param runnable 执行的Runnable
   */
  protected void doAfterEnterAnim(Runnable runnable) {
    if (!mIsEnterAnimRunning) {
      runnable.run();
    } else {
      mDoAfterEnterAnim.offer(runnable);
    }
  }

  protected void notifyEnterAnimStart() {
    mIsEnterAnimRunning = true;
  }

  protected void notifyEnterAnimEnd() {
    mIsEnterAnimRunning = false;
    hideBelowFragmentView();
    while (!mDoAfterEnterAnim.isEmpty()) {
      mDoAfterEnterAnim.poll().run();
    }
  }

  protected void notifyExitAnimStart() {
    showBelowFragmentView();
  }

  /**
   * 锁定该fragment 500ms<BR>
   */
  protected void lockUi() {
    lockUi(500);
  }

  /**
   * 锁定该fragment<BR>
   *
   * @param duration 锁定时长
   */
  protected void lockUi(long duration) {
    mLocked = true;
    mHandler.postDelayed(mUnlockRunnable, duration);
  }

  /**
   * 判读该fragment是否处于锁定状态<BR>
   *
   * @return 是否处于锁定状态
   */
  protected boolean isUiLocked() {
    return mLocked;
  }

  /**
   * 获取私有handler<BR>
   *
   * @return handler
   */
  protected Handler getHandler() {
    return mHandler;
  }

  protected boolean handleMessage(Message msg) {
    return false;
  }

  /**
   * back键被按下时的回调<BR>
   *
   * @return 是否消费该事件, false 不消费,事件将交给activity处理; true 消费该事件, activity不会处理.
   */
  public boolean onBackPressed() {
    return false;
  }

  @Override
  public LayoutInflater getLayoutInflater(Bundle savedInstanceState) {
    if (getThemeRes() >= 0x01000000) {
      return (LayoutInflater) new ContextThemeWrapper(getActivity(),
          getThemeRes()).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    return super.getLayoutInflater(savedInstanceState);
  }

  public int getThemeRes() {
    return -1;
  }
}