/*
 * 文件名: MainTabActivity
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/4/19
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.shudongedu.energy.ui.tab;

import com.shudongedu.energy.R;
import com.shudongedu.energy.ui.analysis.AnalysisFragment;
import com.shudongedu.energy.ui.basic.tab.BaseTabActivity;
import com.shudongedu.energy.ui.device.DeviceStateFragment;
import com.shudongedu.energy.ui.home.HomeFragment;
import com.shudongedu.energy.ui.parameter.ParameterFragment;
import com.shudongedu.energy.ui.profile.ProfileFragment;

/**
 * 主界面<BR>
 *
 * @author zhaozeyang
 */
public class MainTabActivity extends BaseTabActivity {

  @Override
  protected int getLayoutId() {
    return R.layout.main_tab;
  }

  @Override
  protected Class[] getContentClazzes() {
    return new Class[]{HomeFragment.class, AnalysisFragment.class, ParameterFragment.class,
        DeviceStateFragment.class, ProfileFragment.class};
  }

  @Override
  protected String[] getTabTitles() {
    return getResources().getStringArray(R.array.main_tab_titles);
  }

  @Override
  protected int[] getTabIcons() {
    return new int[]{R.drawable.icon_tab_home, R.drawable.icon_tab_analysis, R
        .drawable.icon_tab_param, R.drawable.icon_tab_device, R.drawable.icon_tab_profile};
  }
}
