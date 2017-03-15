/*
 * 文件名: LogInSession
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

import android.text.TextUtils;
import com.shudongedu.energy.CustomApp;
import com.shudongedu.energy.injection.GlobalModule;
import com.shudongedu.energy.login.LoginEvent;
import com.shudongedu.energy.net.rpc.model.UserInfo;
import com.shudongedu.energy.net.rpc.service.AuthService;
import com.shudongedu.energy.net.rpc.service.UserService;
import com.shudongedu.energy.ui.serviceinjection.DaggerServiceComponent;
import com.shudongedu.energy.ui.serviceinjection.ServiceModule;
import com.shudongedu.energy.utils.JsonConverter;
import com.shudongedu.energy.utils.preferences.CustomAppPreferences;
import javax.inject.Inject;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Session<BR>
 * 处理登录登出相关
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/27]
 */
public class LoginSession {

  private CustomApp mContext;
  private UserInfo mUserInfo;
  @Inject
  AuthService mAuthService;
  @Inject
  UserService mUserService;

  private CompositeSubscription mSubscriptions = new CompositeSubscription();

  public LoginSession(CustomApp app) {
    mContext = app;
    DaggerServiceComponent.builder().globalModule(new GlobalModule(mContext)).serviceModule(new
        ServiceModule()).build().inject(this);
    switchUserInfo();
  }

  public int getUserId() {
    return 0;
  }

  private void switchUserInfo() {
    String userJson = mContext.getGlobalComponent().appPreferences().getString
        (CustomAppPreferences.KEY_USER_INFO, "");
    if (!TextUtils.isEmpty(userJson)) {
      mUserInfo = JsonConverter.jsonToObject(UserInfo.class, userJson);
      return;
    }
    mUserInfo = new UserInfo();
  }

  public UserInfo getUserInfo() {
    return mUserInfo;
  }

  public void login(UserInfo userInfo) {
    saveUserInfo(userInfo);
    onLoginStatusChanged();
    mContext.getGlobalComponent().getGlobalBus().post(new LoginEvent());
  }

  public Subscription loadUserInfo() {
    //Subscription getUserInfoSubscription = mAuthService.getUserInfo(new GetUserInfoParams
    //    (mUserInfo.getUserId
    //        ())).subscribeOn(Schedulers.newThread())
    //    .observeOn(AndroidSchedulers.mainThread()).subscribe(new UiRpcSubscriber<UserInfo>
    //        (mContext) {
    //      @Override
    //      protected void onSuccess(UserInfo info) {
    //        saveUserInfo(info);
    //        switchUserInfo();
    //        mContext.getGlobalComponent().getGlobalBus().post(new UserInfoChangedEvent());
    //      }
    //
    //      @Override
    //      protected void onEnd() {
    //
    //      }
    //    });
    //mSubscriptions.add(getUserInfoSubscription);
    //return getUserInfoSubscription;
    return null;
  }

  private Subscription updateUserInfo() {
    //Subscription updateUserInfoSubscription = mUserService.updateUser(mUserInfo).subscribeOn
    //    (Schedulers
    //        .newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new UiRpcSubscriber<UserInfo>(mContext) {
    //
    //  @Override
    //  protected void onSuccess(UserInfo userInfo) {
    //    saveUserInfo(userInfo);
    //    switchUserInfo();
    //    mContext.getGlobalComponent().getGlobalBus().post(new UserInfoChangedEvent());
    //  }
    //
    //  @Override
    //  public void onApiError(RpcApiError apiError) {
    //    super.onApiError(apiError);
    //    mContext.getGlobalComponent().getGlobalBus().post(new UserInfoChangedEvent(false));
    //  }
    //
    //  @Override
    //  protected void onEnd() {
    //
    //  }
    //
    //});
    //mSubscriptions.add(updateUserInfoSubscription);
    //return updateUserInfoSubscription;
    return null;
  }

  private void saveUserInfo(UserInfo userInfo) {
    //mContext.getGlobalComponent().appPreferences().put(CustomAppPreferences.KEY_USER_INFO,
    //    JsonConverter
    //        .objectToJson(userInfo));
    //mContext.getGlobalComponent().appPreferences().put(CustomAppPreferences.KEY_USER_ID, userInfo
    //    .getUserId());
    //mContext.getGlobalComponent().appPreferences().put(CustomAppPreferences.KEY_USER_TYPE,
    //    userInfo.getType());
  }

  public void logout() {
    //mContext.getGlobalComponent().appPreferences().remove(CustomAppPreferences.KEY_USER_INFO);
    //mContext.getGlobalComponent().appPreferences().remove(CustomAppPreferences.KEY_USER_ID);
    //mContext.getGlobalComponent().appPreferences().remove(CustomAppPreferences.KEY_USER_TYPE);
    //mContext.getGlobalComponent().appPreferences().remove(CustomAppPreferences.KEY_COOKIE_SESSION_ID);
    //mContext.getGlobalComponent().getGlobalBus().post(new LogoutEvent());
    onLoginStatusChanged();
  }

  public void onLoginStatusChanged() {
    switchUserInfo();
  }

  public void onDestroy() {
    //mSubscriptions.unsubscribe();
  }

  //public UserInfoChangeBuilder userInfoChangeBuilder() {
  //  return new UserInfoChangeBuilder();
  //}

  //public class UserInfoChangeBuilder {
  //
  //  public Subscription update() {
  //    return updateUserInfo();
  //  }
  //
  //  public UserInfoChangeBuilder setAvart(String avart) {
  //    mUserInfo.setAvart(avart);
  //    return this;
  //  }
  //
  //  public UserInfoChangeBuilder setBirthPlace(String birthPlace) {
  //    mUserInfo.setBirthPlace(birthPlace);
  //    return this;
  //  }
  //
  //  public UserInfoChangeBuilder setBirthday(String birthday) {
  //    mUserInfo.setBirthday(birthday);
  //    return this;
  //  }
  //
  //  public UserInfoChangeBuilder setCertified(String certified) {
  //    mUserInfo.setCertified(certified);
  //    return this;
  //  }
  //
  //  public UserInfoChangeBuilder setDevId(String devId) {
  //    mUserInfo.setDevId(devId);
  //    return this;
  //  }
  //
  //  public UserInfoChangeBuilder setEducation(String education) {
  //    mUserInfo.setEducation(education);
  //    return this;
  //  }
  //
  //  public UserInfoChangeBuilder setGender(String gender) {
  //    mUserInfo.setGender(gender);
  //    return this;
  //  }
  //
  //  public UserInfoChangeBuilder setIdCardImg(String idCardImg) {
  //    mUserInfo.setIdCardImg(idCardImg);
  //    return this;
  //  }
  //
  //  public UserInfoChangeBuilder setIntroduce(String introduce) {
  //    mUserInfo.setIntroduce(introduce);
  //    return this;
  //  }
  //
  //  public UserInfoChangeBuilder setInviteCode(String inviteCode) {
  //    mUserInfo.setInviteCode(inviteCode);
  //    return this;
  //  }
  //
  //  public UserInfoChangeBuilder setLoginNum(int loginNum) {
  //    mUserInfo.setLoginNum(loginNum);
  //    return this;
  //  }
  //
  //  public UserInfoChangeBuilder setLoginTime(Object loginTime) {
  //    mUserInfo.setLoginTime(loginTime);
  //    return this;
  //  }
  //
  //  public UserInfoChangeBuilder setMobileNo(String mobileNo) {
  //    mUserInfo.setMobileNo(mobileNo);
  //    return this;
  //  }
  //
  //  public UserInfoChangeBuilder setMoney(int money) {
  //    mUserInfo.setMoney(money);
  //    return this;
  //  }
  //
  //  public UserInfoChangeBuilder setMsgCounts(int msgCounts) {
  //    mUserInfo.setMsgCounts(msgCounts);
  //    return this;
  //  }
  //
  //  public UserInfoChangeBuilder setMyCity(String myCity) {
  //    mUserInfo.setMyCity(myCity);
  //    return this;
  //  }
  //
  //  public UserInfoChangeBuilder setOccupation(String occupation) {
  //    mUserInfo.setOccupation(occupation);
  //    return this;
  //  }
  //
  //  public UserInfoChangeBuilder setPassword(String password) {
  //    mUserInfo.setPassword(password);
  //    return this;
  //  }
  //
  //  public UserInfoChangeBuilder setPayPwd(String payPwd) {
  //    mUserInfo.setPayPwd(payPwd);
  //    return this;
  //  }
  //
  //  public UserInfoChangeBuilder setRegisterDate(Object registerDate) {
  //    mUserInfo.setRegisterDate(registerDate);
  //    return this;
  //  }
  //
  //  public UserInfoChangeBuilder setResult(String result) {
  //    mUserInfo.setResult(result);
  //    return this;
  //  }
  //
  //  public UserInfoChangeBuilder setSecurityLevel(String securityLevel) {
  //    mUserInfo.setSecurityLevel(securityLevel);
  //    return this;
  //  }
  //
  //  public UserInfoChangeBuilder setState(String state) {
  //    mUserInfo.setState(state);
  //    return this;
  //  }
  //
  //  public UserInfoChangeBuilder setThirdAccount(String thirdAccount) {
  //    mUserInfo.setThirdAccount(thirdAccount);
  //    return this;
  //  }
  //
  //  public UserInfoChangeBuilder setTodayBenefit(int todayBenefit) {
  //    mUserInfo.setTodayBenefit(todayBenefit);
  //    return this;
  //  }
  //
  //  public UserInfoChangeBuilder setType(String type) {
  //    mUserInfo.setType(type);
  //    return this;
  //  }
  //
  //  public UserInfoChangeBuilder setUserId(int userId) {
  //    mUserInfo.setUserId(userId);
  //    return this;
  //  }
  //
  //  public UserInfoChangeBuilder setUserLabel(String userLabel) {
  //    mUserInfo.setUserLabel(userLabel);
  //    return this;
  //  }
  //
  //  public UserInfoChangeBuilder setUserName(String userName) {
  //    mUserInfo.setUserName(userName);
  //    return this;
  //  }
  //
  //  public UserInfoChangeBuilder setVirtualMoney(int virtualMoney) {
  //    mUserInfo.setVirtualMoney(virtualMoney);
  //    return this;
  //  }
  //}
}
