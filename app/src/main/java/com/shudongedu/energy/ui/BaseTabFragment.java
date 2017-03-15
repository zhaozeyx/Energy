/*
 * 文件名: BaseTabFragment
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:2016/12/15
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.shudongedu.energy.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.shudongedu.energy.R;
import com.shudongedu.energy.ui.basic.BasicTitleBarFragment;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 2016/12/15]
 */
public abstract class BaseTabFragment extends BasicTitleBarFragment {

  private View[] mIconViews;

  @Bind(R.id.tabs)
  TabLayout tabs;
  @Bind(R.id.pagers)
  ViewPager pagers;

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initView();
  }

  private void initView() {
    if (null == getTabTitles() || null == getPagerAdapter()) {
      return;
    }
    pagers.setAdapter(getPagerAdapter());
    pagers.setOffscreenPageLimit(getTabTitles().length);
    tabs.setupWithViewPager(pagers);
    mIconViews = new View[getTabTitles().length];
    for (int i = 0; i < getTabTitles().length; i++) {
      View view = LayoutInflater.from(getActivity()).inflate(R.layout.base_tab_widget, tabs,
          false);
      TextView titleView = (TextView) view.findViewById(R.id.title);
      titleView.setText(getTabTitles()[i]);
      mIconViews[i] = view.findViewById(R.id.icon_status);
      tabs.getTabAt(i).setCustomView(view);
    }
  }

  @Override
  public int getLayoutId() {
    return R.layout.base_tab_fragment;
  }

  protected abstract String[] getTabTitles();

  protected abstract PagerAdapter getPagerAdapter();

  protected void showUnReadMsg(int index) {
    mIconViews[index].setVisibility(View.VISIBLE);
  }

  protected void dismissUnReadMsg(int index) {
    mIconViews[index].setVisibility(View.GONE);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
      savedInstanceState) {
    // TODO: inflate a fragment view
    View rootView = super.onCreateView(inflater, container, savedInstanceState);
    ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }
}
