package com.shudongedu.energy;

import android.content.Intent;
import android.os.Bundle;
import com.shudongedu.energy.ui.basic.BasicActivity;
import com.shudongedu.energy.ui.login.LoginActivity;
import com.shudongedu.energy.ui.tab.MainTabActivity;

public class BootActivity extends BasicActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_boot);
    enterPage();
  }

  private void enterPage() {
    if (isLogin()) {
      startActivity(new Intent(this, MainTabActivity.class));
    } else {
      startActivity(new Intent(this, LoginActivity.class));
    }
    finish();
  }
}
