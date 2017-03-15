package com.shudongedu.energy;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import com.bugtags.library.Bugtags;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.shudongedu.energy.injection.DaggerGlobalComponent;
import com.shudongedu.energy.injection.GlobalComponent;
import com.shudongedu.energy.injection.GlobalModule;
import com.shudongedu.energy.log.Logger;

/**
 * Created by zhaozeyang on 16/4/13.
 */
public class CustomApp extends Application {

  private GlobalComponent mGlobalComponent;

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
  }

  @Override
  public void onCreate() {
    super.onCreate();
    Logger.init();
    Fresco.initialize(this);
    //RealmFactory.initRealm(this);

    mGlobalComponent = DaggerGlobalComponent.builder().globalModule(new GlobalModule(this)).build();
    //JPushInterface.setDebugMode(true);  // 设置开启日志,发布时请关闭日志
    //JPushInterface.init(this);
    Bugtags.start("67fa6fe3a054500306262132ef4bfe55", this, BuildConfig.DEBUG ? Bugtags
        .BTGInvocationEventNone : Bugtags.BTGInvocationEventNone);
  }

  public GlobalComponent getGlobalComponent() {
    return mGlobalComponent;
  }


}
