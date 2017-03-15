/*
 * 文件名: BaseTabActivity.java
 * 版    权：  Copyright Paitao Tech. Co. Ltd. All Rights Reserved.
 * 描    述: TAB 基类
 * 创建人: zhaozeyang
 * 创建时间:2014-10-6
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.shudongedu.energy.ui.basic.tab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import com.shudongedu.energy.R;
import com.shudongedu.energy.ui.basic.BasicActivity;

/**
 * TAB 基类<BR>
 *
 * @author zhaozeyang
 */
public abstract class BaseTabActivity extends BasicActivity {
  /**
   * TAG 前缀
   */
  private static final String TAGS_PREFIX = "main_tab_";

  @SuppressWarnings("rawtypes")
  private Class[] mContentClazzes;

  /**
   * TabHost
   */
  private TabHost mTabHost;

  /**
   * Tab管理类，用来添加fragment到每个tab中
   */
  private TabManager mTabManager;

  private TabIndicatorView[] mIndicatorViews;

  /**
   * TAB IDS
   */
  private String[] mTabIds;

  /**
   * 当前可见的fragment
   */
  private Fragment mCurrentVisibleFragment;

  private OnTabChangedListener mOnTabChangedListener;

  private PerformContentActionListener mContentActionListener;

  @Override
  protected void onCreate(Bundle savedInstance) {
    super.onCreate(savedInstance);
    setContentView(getLayoutId());
    initContentActionListener();
    initTabHost();
    setTabContent();
  }

  private void setTabContent() {
    // 获得需要展示的数据内容
    mContentClazzes = getContentClazzes();

    // widget 标题
    String[] titles = getTabTitles();

    // widget 图标
    int[] icons = getTabIcons();

    if (null != titles && mContentClazzes.length != titles.length) {
      throw new RuntimeException("标题数目与展示的标签数目不同");
    }
    if (null != icons && mContentClazzes.length != icons.length) {
      throw new RuntimeException("标题数目与展示的标签图标数目不同");
    }

    mIndicatorViews = new TabIndicatorView[mContentClazzes.length];
    mTabIds = new String[mContentClazzes.length];

    for (int i = 0; i < mContentClazzes.length; i++) {
      mIndicatorViews[i] = getIndicatorView((null != titles && titles.length > 0) ? titles[i]
              : "",
          (null != icons && icons.length > 0) ? icons[i] : -1);
      mTabIds[i] = getFragmentTag(i);
      mTabManager.addTab(mTabHost.newTabSpec(mTabIds[i])
              .setIndicator(mIndicatorViews[i]),
          mContentClazzes[i],
          null);
    }
    mCurrentVisibleFragment = mTabManager.getFragmentById(mTabIds[0]);
    mTabManager.setOnTabChangedListener(new OnTabChangeListener() {

      @Override
      public void onTabChanged(String tabId) {
        mCurrentVisibleFragment = mTabManager.getFragmentById(tabId);
        if (null != mOnTabChangedListener) {
          mOnTabChangedListener.onTabChanged(tabId);
        }
        Fragment fragment = mTabManager.getFragmentById(tabId);
        if (fragment instanceof ContentAction) {
          ((ContentAction) fragment).setContentActionListener(mContentActionListener);
        }
      }
    });

    // 设置默认的显示页面
    mTabManager.onTabChanged(mTabIds[0]);
  }

  protected abstract int getLayoutId();

  /**
   * 页签内容<BR>
   *
   * @return 显示页签内容的fragments
   */
  @SuppressWarnings("rawtypes")
  protected abstract Class[] getContentClazzes();

  /**
   * 页签指示器标题<BR>
   *
   * @return 指示器标题
   */
  protected abstract String[] getTabTitles();

  /**
   * 页签指示器图标<BR>
   *
   * @return 指示器图标
   */
  protected int[] getTabIcons() {
    return null;
  }

  /**
   * 图标相对文字的方向<BR>
   *
   * @return 图标相对文字的方向
   */
  protected int getTabIconDirection() {
    return TabIndicatorView.DRAWABLE_TOP;
  }

  /**
   * 获得当前可见fragment<BR>
   *
   * @return 当前可见的fragment
   */
  protected Fragment getVisbileFragment() {
    return mCurrentVisibleFragment;
  }

  /**
   * 根据id获取fragment<BR>
   *
   * @param tag fragment的ID
   * @return fragment
   */
  protected Fragment getTabById(String tag) {
    return mTabManager.getFragmentById(tag);
  }

  /**
   * 根据索引获取fragment<BR>
   *
   * @param index 索引
   * @return fragment
   */
  protected Fragment getTabByIndex(int index) {
    return mTabManager.getFragmentById(getTabId(index));
  }

  /**
   * 根据tabId获取索引值<BR>
   *
   * @param tabId tabId
   * @return 索引
   */
  protected int getIndexByTabId(String tabId) {
    for (int i = 0; i < mTabIds.length; i++) {
      if (TextUtils.equals(tabId, mTabIds[i])) {
        return i;
      }
    }
    return -1;
  }

  /**
   * 设置页签切换的事件监听<BR>
   */
  protected void setOnTabChangedListener(OnTabChangedListener listener) {
    mOnTabChangedListener = listener;
  }

  /**
   * 切换显示的tab<BR>
   *
   * @param index 显示的tab索引
   */
  protected void setCurrentTab(int index) {
    mTabHost.setCurrentTab(index);
  }

  /**
   * 当页签切换<BR>
   *
   * @param tabId 页签ID
   */
  protected void onContentTabChanged(String tabId) {
    mTabManager.onTabChanged(tabId);
  }

  /**
   * 根据索引获得页签ID<BR>
   *
   * @param index 索引
   * @return 页签
   */
  protected String getTabId(int index) {
    if (index >= mTabIds.length) {
      throw new IndexOutOfBoundsException("当前索引超过显示的页签个数");
    }
    return mTabIds[index];
  }

  /**
   * 设置指示器数字<BR>
   *
   * @param index 指示器索引
   * @param count 个数
   */
  protected void setIndicatorCount(int index, int count) {
    mIndicatorViews[index].setUnreadCount(count);
  }

  /**
   * 获得指示器数字<BR>
   *
   * @param index 索引
   * @return 指示器数字
   */
  protected int getIndicatorCount(int index) {
    return mIndicatorViews[index].getUnreadCount();
  }

  /**
   * 显示或者隐藏新消息标志视图<BR>
   *
   * @param index 索引
   * @param show 是否显示
   */
  protected void showOrDissmissFlag(int index, boolean show) {
    mIndicatorViews[index].showOrDismissFlag(show);
  }

  private String getFragmentTag(int index) {
    return TAGS_PREFIX + mContentClazzes[index].getName();
  }

  private TabIndicatorView getIndicatorView(String title, int drawableId) {
    return new TabIndicatorView(this, title, drawableId,
        getTabIconDirection());
  }

  private void initTabHost() {
    mTabHost = (TabHost) findViewById(android.R.id.tabhost);
    mTabHost.setup();

    mTabManager = new TabManager(this, mTabHost, R.id.real_tabcontent);
  }

  private void initContentActionListener() {
    mContentActionListener = new PerformContentActionListener() {

      @Override
      public void performAction(Message msg) {
        performContentAction(msg);
      }
    };
  }

  protected void performContentAction(Message msg) {

  }


  @SuppressLint("InflateParams")
  public class TabIndicatorView extends RelativeLayout {
    /**
     * 图标方向 左
     */
    private static final int DRAWABLE_LEFT = 0x01;

    /**
     * 图标方向 上
     */
    private static final int DRAWABLE_TOP = 0x02;

    /**
     * 图标方向 右
     */
    private static final int DRAWABLE_RIHT = 0x03;

    /**
     * 图标方向 下
     */
    private static final int DRAWABLE_BOTTOM = 0x04;

    /**
     * 标题字符串
     */
    private String mTabTitleStr;

    /**
     * 标题视图
     */
    private TextView mTitleView;

    /**
     * 新消息视图
     */
    private ImageView mNewFlagView;

    /**
     * 计数视图
     */
    private TextView mCountView;

    /**
     * 计数
     */
    private int mCount;

    /**
     * 标志资源ID
     */
    private int mDrawableId;

    /**
     * 标志方向
     */
    private int mDrawableDirection;

    public TabIndicatorView(Context context) {
      this(context, null);
    }

    public TabIndicatorView(Context context, String tabTitleStr) {
      this(context, tabTitleStr, -1, DRAWABLE_TOP);

    }

    public TabIndicatorView(Context context, String tabTitleStr,
                            int drawableId, int drawableDirection) {
      super(context);
      mTabTitleStr = tabTitleStr;
      mDrawableId = drawableId;
      mDrawableDirection = drawableDirection;
      initView(context);
    }

    private void initView(Context context) {
      View view = LayoutInflater.from(context)
          .inflate(R.layout.tab_indicator, null);
      mTitleView = (TextView) view.findViewById(R.id.tab_title);
      mCountView = (TextView) view.findViewById(R.id.tab_unread_msg);
      mNewFlagView = (ImageView) view.findViewById(R.id.tab_new_flag);
      mTitleView.setText(mTabTitleStr);

      if (mDrawableId > 0) {
        switch (mDrawableDirection) {
          case DRAWABLE_TOP:
            mTitleView.setCompoundDrawablesWithIntrinsicBounds(0,
                mDrawableId,
                0,
                0);
            break;
          case DRAWABLE_LEFT:
            mTitleView.setCompoundDrawablesWithIntrinsicBounds(mDrawableId,
                0,
                0,
                0);
            break;
          case DRAWABLE_RIHT:
            mTitleView.setCompoundDrawablesWithIntrinsicBounds(0,
                0,
                mDrawableId,
                0);
            break;
          case DRAWABLE_BOTTOM:
            mTitleView.setCompoundDrawablesWithIntrinsicBounds(0,
                0,
                0,
                mDrawableId);
            break;
          default:
            mTitleView.setCompoundDrawablesWithIntrinsicBounds(0,
                mDrawableId,
                0,
                0);
            break;
        }
      }
      addView(view, new LayoutParams(
          LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
      setUnreadCount(0);
    }

    public void setUnreadCount(int count) {
      mCount = count;
      mCountView.setVisibility(mCount > 0 ? View.VISIBLE : View.GONE);
      mCountView.setText(String.valueOf(count));
    }

    public int getUnreadCount() {
      return mCount;
    }

    public void showOrDismissFlag(boolean show) {
      mNewFlagView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

  }

  /**
   * tab切换监听<BR>
   *
   * @author zhaozeyang
   * @version [Paitao Client V20130911, 2014-10-6]
   */
  public interface OnTabChangedListener {
    void onTabChanged(String tabId);
  }

  /**
   * 用于处理子页面事件的接口<BR>
   *
   * @author zhaozeyang
   * @version [Paitao Client V20130911, 2014-11-1]
   */
  public interface PerformContentActionListener {
    // 消息类型的定义
    // 为避免多个子页面的消息处理的类型重复[Message 的 what 值重复]，强烈建议在统一的位置给每个子页面的action定义类型
    void performAction(Message msg);
  }

  /**
   * 用于实现设置页面事件的接口<BR>
   *
   * @author zhaozeyang
   * @version [Paitao Client V20130911, 2014-11-3]
   */
  public interface ContentAction {
    void setContentActionListener(PerformContentActionListener listener);
  }
}
