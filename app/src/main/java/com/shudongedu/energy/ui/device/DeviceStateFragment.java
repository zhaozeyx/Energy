/*
 * 文件名: ApproveFragment
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:2016/12/13
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.shudongedu.energy.ui.device;

import com.shudongedu.energy.R;
import com.shudongedu.energy.ui.basic.BaseWebFragment;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 2016/12/13]
 */
public class DeviceStateFragment extends BaseWebFragment {

  @Override
  protected String getUrl() {
    return "file:///android_asset/html/state.html";
  }

  @Override
  public boolean initializeTitleBar() {
    setMiddleTitle(R.string.fragment_device_state_title);
    return true;
  }
}
