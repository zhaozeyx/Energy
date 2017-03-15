/*
 * 文件名: UserInfoChangedEvent
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/5/16
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.shudongedu.energy.manager;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/5/16]
 */
public class UserInfoChangedEvent {
  public boolean changeSuccess = true;

  public UserInfoChangedEvent(boolean changeSuccess) {
    this.changeSuccess = changeSuccess;
  }

  public UserInfoChangedEvent() {
  }
}
