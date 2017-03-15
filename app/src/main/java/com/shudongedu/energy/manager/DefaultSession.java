/*
 * 文件名: DefaultSession
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/4/27
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.shudongedu.energy.manager;

/**
 * 默认Session<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/27]
 */
public class DefaultSession implements ISession {
  @Override
  public String getIdentifier() {
    return null;
  }

  @Override
  public String userName() {
    return null;
  }

  @Override
  public String deviceId() {
    return null;
  }

  @Override
  public int profitToday() {
    return 0;
  }

  @Override
  public int profitTotal() {
    return 0;
  }
}
