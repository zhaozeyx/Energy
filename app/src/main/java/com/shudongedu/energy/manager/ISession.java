/*
 * 文件名: ISession
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
 * [一句话功能简述]<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/27]
 */
public interface ISession {
  String getIdentifier();

  String userName();

  String deviceId();

  int profitToday();

  int profitTotal();
}
