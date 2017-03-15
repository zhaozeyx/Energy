/*
 * 文件名: LoginActivity
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:2017/2/28
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.shudongedu.energy.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.widget.EditText;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.shudongedu.energy.R;
import com.shudongedu.energy.ui.basic.BasicTitleBarActivity;
import com.shudongedu.energy.ui.tab.MainTabActivity;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 2017/2/28]
 */
public class LoginActivity extends BasicTitleBarActivity {

  @Bind(R.id.account)
  EditText account;
  @Bind(R.id.password)
  EditText password;
  @Bind(R.id.btn_submit)
  AppCompatButton btnSubmit;

  @Override
  public int getLayoutId() {
    return R.layout.activity_login;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.btn_submit)
  public void onClick() {
    if (TextUtils.isEmpty(account.getText())) {
      showShortToast(R.string.account_error_empty);
      return;
    }
    if (TextUtils.isEmpty(password.getText())) {
      showShortToast(R.string.password_error_empty);
      return;
    }
    //  TODO 调用接口
    performLogin();
  }

  private void performLogin() {
    startActivity(new Intent(this, MainTabActivity.class));
    finish();
  }
}
