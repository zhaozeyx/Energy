package com.shudongedu.energy.injection;

import android.content.Context;
import com.shudongedu.energy.CustomApp;
import com.shudongedu.energy.manager.LoginSession;
import com.shudongedu.energy.utils.DeviceUuidFactory;
import com.shudongedu.energy.utils.preferences.CustomAppPreferences;
import com.squareup.otto.Bus;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by zhaozeyang on 16/4/13.
 */
@Module
public class GlobalModule {
  private CustomApp mApplication;

  @Singleton
  public GlobalModule(CustomApp app) {
    mApplication = app;
  }

  @Singleton
  @Provides
  public Context providesApplicationContext() {
    return mApplication;
  }

  @Singleton
  @Provides
  @GlobalBus
  public Bus providesGlobalBus() {
    return new Bus();
  }

  @Singleton
  @Provides
  public CustomAppPreferences providesGlobalPreferences() {
    return new CustomAppPreferences(mApplication);
  }

  @Provides
  public boolean providesLoginStatus(CustomAppPreferences preferences) {
    String type = preferences.getString(CustomAppPreferences.KEY_USER_TYPE, "");
    int uid = preferences.getInt(CustomAppPreferences.KEY_USER_ID, 0);
    return false;
  }

  @Provides
  @Singleton
  public LoginSession providesLoginSession() {
    return new LoginSession(mApplication);
  }

  @Provides
  @DeviceId
  public String providesDeviceId() {
    return DeviceUuidFactory.getInstance(mApplication).getDeviceUuidString();
  }
}
