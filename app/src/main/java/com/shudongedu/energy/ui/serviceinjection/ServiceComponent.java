/*
 * 文件名: AppServiceComponent
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/4/19
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.shudongedu.energy.ui.serviceinjection;

import com.shudongedu.energy.manager.LoginSession;
import com.shudongedu.energy.manager.UpgradeHelper;
import com.shudongedu.energy.net.RpcCallManager;
import com.shudongedu.energy.net.rpc.service.AppService;
import com.shudongedu.energy.net.rpc.service.AuthService;
import com.shudongedu.energy.net.rpc.service.UserService;
import dagger.Component;
import javax.inject.Singleton;

/**
 * 服务器接口 Component<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/19]
 */
@Singleton
@Component(modules = ServiceModule.class)
public interface ServiceComponent {
  RpcCallManager rpcCallManager();

  AppService appService();

  AuthService authService();

  UserService userService();

  void inject(LoginSession loginSession);

  void inject(UpgradeHelper upgradeHelper);
}
