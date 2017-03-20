/*
 * 文件名: ProfileFragment
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/9/18
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.shudongedu.energy.ui.profile;

import android.os.Bundle;
import android.view.View;
import com.shudongedu.energy.R;
import com.shudongedu.energy.ui.basic.BaseWebFragment;
import com.shudongedu.energy.ui.basic.BasicTitleBarFragment;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/9/18]
 */
public class ProfileFragment extends BaseWebFragment {

  @Override
  protected String getUrl() {
    return "file:///android_asset/html/Me.html";
  }

  @Override
  public boolean initializeTitleBar() {
    return true;
  }
}
