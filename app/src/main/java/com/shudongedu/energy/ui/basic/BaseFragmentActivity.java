package com.shudongedu.energy.ui.basic;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import com.bugtags.library.Bugtags;
import com.shudongedu.energy.ui.basic.BaseFragment;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BaseFragmentActivity extends AppCompatActivity {
  private int mContainerId;
  private ArrayList<BaseFragment> mFragments = new ArrayList<BaseFragment>();
  private FragmentManager mFragmentManager;
  // 是否已经保存了状态
  private boolean mStateSaved = false;

  private Queue<Runnable> mDoAfterStateRestored = new LinkedList<Runnable>();

  private FragmentManager.OnBackStackChangedListener mBackStackChangedListener =
      new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
          onFragmentBackStackChanged();
        }
      };

  private Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
    @Override
    public boolean handleMessage(Message msg) {
      return BaseFragmentActivity.this.handleMessage(msg);
    }
  });

  private Runnable invalidatePropertyRunnable = new Runnable() {
    @Override
    public void run() {
      internalInvalidateProperty();
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mStateSaved = false;
  }

  protected void init(Bundle savedInstanceState, int containerId, int homeFragmentId) {
    mContainerId = containerId;
    mFragmentManager = getSupportFragmentManager();
    mFragmentManager.addOnBackStackChangedListener(mBackStackChangedListener);
    mFragments.clear();
    if (savedInstanceState != null) {
      // activity销毁后，recreate的情况下需要恢复以前创建的所有fragment
      restoreAllFragments(savedInstanceState);
    } else {
      mFragments.add((BaseFragment) mFragmentManager.findFragmentById(homeFragmentId));
    }
    invalidateProperty();
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    mStateSaved = false;
  }

  @Override
  protected void onStart() {
    super.onStart();
    mStateSaved = false;
  }

  @Override
  protected void onResume() {
    super.onResume();
    mStateSaved = false;
    while (!mDoAfterStateRestored.isEmpty()) {
      mDoAfterStateRestored.poll().run();
    }
    Bugtags.onResume(this);
  }
  @Override
  protected void onPause() {
    super.onPause();
    //注：回调 2
    Bugtags.onPause(this);
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent event) {
    //注：回调 3
    Bugtags.onDispatchTouchEvent(this, event);
    return super.dispatchTouchEvent(event);
  }


  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mStateSaved = true;
    // 保存所有fragments
    for (int i = 0; i < mFragments.size(); i++) {
      Fragment f = mFragments.get(i);
      if (f != null) {
        String key = "f" + i;
        mFragmentManager.putFragment(outState, key, f);
      }
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    // 移除所有pending的操作
    handler.removeCallbacksAndMessages(null);
  }

  protected void doAfterStateRestored(Runnable runnable) {
    if (mStateSaved) {
      mDoAfterStateRestored.offer(runnable);
    } else {
      runnable.run();
    }
  }

  // 恢复所有fragments
  private void restoreAllFragments(Bundle savedInstanceState) {
    Iterable<String> keys = savedInstanceState.keySet();
    for (String key : keys) {
      if (key.startsWith("f")) {
        int index = Integer.parseInt(key.substring(1));
        BaseFragment f = (BaseFragment) mFragmentManager.getFragment(savedInstanceState, key);
        if (f != null) {
          while (mFragments.size() <= index) {
            mFragments.add(null);
          }
          mFragments.set(index, f);
        }
      }
    }
  }

  /**
   * 将fragment压入堆栈<BR>
   *
   * @param fragment fragment
   */
  public void push(final BaseFragment fragment) {
    doAfterStateRestored(new Runnable() {
      public void run() {
        mFragments.add(fragment);
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.add(mContainerId, fragment, fragment.getClass().getSimpleName());
        // 将本次操作加入回退堆栈，以便用户按back键时做回退
        ft.addToBackStack(null);
        ft.commit();
        mFragmentManager.executePendingTransactions();
      }
    });
  }

  /**
   * 将栈顶的fragment出栈<BR>
   */
  public void pop() {
    doAfterStateRestored(new Runnable() {
      @Override
      public void run() {
        mFragmentManager.popBackStackImmediate();
      }
    });
  }

  public void popAll() {
    doAfterStateRestored(new Runnable() {
      @Override
      public void run() {
        for (int i = mFragments.size() - 1; i > 0; i--) {
          mFragments.get(i).pop();
        }
      }
    });
  }

  /**
   * 根据指定类型找到已经存在的fragment实例，并将其上其他页面关闭
   *
   * @param fragmentClass 指定的fragment的Class
   * @param <T> 所有继承自XmFragment的类型
   * @return 是否存在已经打开的实例
   */
  public <T extends BaseFragment> boolean popToExistedInstance(Class<T> fragmentClass) {
    int existedInstanceIndex = -1;
    for (int i = mFragments.size() - 1; i >= 0; i--) {
      if (mFragments.get(i).getClass() == fragmentClass) {
        existedInstanceIndex = i;
        break;
      }
    }
    boolean alreadyExisted = existedInstanceIndex != -1;
    if (alreadyExisted) {
      for (int i = mFragments.size() - 1; i > existedInstanceIndex; i--) {
        mFragments.get(i).pop();
      }
    }
    return alreadyExisted;
  }

  public BaseFragment getCurrentFragment() {
    int fragmentSize = mFragments.size();
    return fragmentSize > 0 ? mFragments.get(mFragments.size() - 1) : null;
  }

  public List<BaseFragment> getFragments() {
    return mFragments;
  }

  public BaseFragment getFragmentBelow(BaseFragment fragment) {
    List<BaseFragment> fragments = getFragments();
    int index = fragments.indexOf(fragment);
    return index >= 1 ? fragments.get(index - 1) : null;
  }

  /**
   * 判断fragment是否在最顶部<BR>
   *
   * @param fragment fragment
   * @return 是否在最顶部
   */
  public boolean isTopFragment(BaseFragment fragment) {
    return mFragments.indexOf(fragment) == mFragments.size() - 1;
  }

  private void onFragmentBackStackChanged() {
    // 当系统pop出加入回退堆栈的fragment的时候，需要将其从我们的容器中移除，以达到两者同步的目的
    // 因为HomeFragment不需要在回退堆栈中，所以系统中的总堆栈大小始终比mFragment的大小小1.
    int backStackEntryCount = mFragmentManager.getBackStackEntryCount();
    while (mFragments.size() - 1 > backStackEntryCount) {
      // 如果大于系统堆栈大小，需要将容器的顶部fragment移除，以达到两者同步的目的·
      mFragments.remove(mFragments.size() - 1);
    }
    invalidateProperty();
  }

  @Override
  public void onBackPressed() {
    BaseFragment current = getCurrentFragment();
    if (current == null || !getCurrentFragment().onBackPressed()) {
      super.onBackPressed();
    }
  }

  /**
   * 使当前TitleBar失效，并重新创建<BR>
   */
  public void invalidateProperty() {
    handler.removeCallbacks(invalidatePropertyRunnable);
    handler.post(invalidatePropertyRunnable);
  }

  private void internalInvalidateProperty() {
    BaseFragment fragment = getCurrentFragment();
    if (fragment == null) {
      return;
    }
    onPropertyChanged(fragment);
  }

  protected void onPropertyChanged(BaseFragment fragment) {
  }

  protected final Handler getHandler() {
    return handler;
  }

  protected boolean handleMessage(Message msg) {
    return false;
  }
}