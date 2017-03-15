/*
 * 文件名: CodeBarCaptureActivity.java
 * 版    权：  Copyright Paitao Tech. Co. Ltd. All Rights Reserved.
 * 描    述: 二维码扫描的入口Activity
 * 创建人: jiazhenkai
 * 创建时间:2014-3-31
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.shudongedu.energy.codebar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import com.shudongedu.energy.R;
import com.shudongedu.energy.codebar.view.ViewfinderView;
import com.shudongedu.energy.ui.basic.titlebar.TitleBar;

/**
 * 二维码扫描的入口Activity
 *
 * @author jiazhenkai
 * @version [Paitao Client V20130911, 2014-3-31]
 */
public class CodeBarCaptureActivity extends CodeBarCaptureBaseActivity {

  /**
   * 扫码倒计时 60s
   */
  private static final long AUTH_CODE_FUTURE_INTERVAL = 60 * 1000L;

  /**
   * AuthCodeTimeCount
   */
  private AuthCodeTimeCount mTimeCount;

  /**
   * 闪光灯
   */
  private CheckBox mFlashLightBtn;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mTimeCount = new AuthCodeTimeCount(AUTH_CODE_FUTURE_INTERVAL, AUTH_CODE_FUTURE_INTERVAL);
    mTimeCount.start();
    initFlashLightView();
  }

  /**
   * 初始化闪光灯View
   */
  private void initFlashLightView() {
    //mFlashLightBtn = (CheckBox) findViewById(R.id.flashlight_btn);
    //if (!isFlashLightSupported()) {
    //  mFlashLightBtn.setVisibility(View.GONE);
    //  return;
    //}
    //mFlashLightBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    //  @Override
    //  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    //    if (isChecked) {
    //      CameraManager.get().openFlashLight();
    //    } else {
    //      CameraManager.get().offFlashLight();
    //    }
    //  }
    //});
  }

  /**
   * 是否支持闪光灯功能
   *
   * @return 是否支持闪光灯
   */
  private boolean isFlashLightSupported() {
    return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
  }

  @Override
  protected void onResume() {
    super.onResume();
    //mFlashLightBtn.setChecked(false);
  }

  @Override
  public int getLayoutId() {
    return R.layout.codebar_capture_activity;
  }

  @Override
  public ViewfinderView getViewfinderView() {
    return (ViewfinderView) findViewById(R.id.viewfinder_view);
  }

  @Override
  public SurfaceView getSurfaceView() {
    return (SurfaceView) findViewById(R.id.preview_view);
  }

  @Override
  public TitleBar.TitleBarStyle getTitleBarStyle() {
    return TitleBar.TitleBarStyle.TRANSPARENT;
  }

  @SuppressLint("ResourceAsColor")
  @Override
  public boolean initializeTitleBar() {
    setTitleBarBackground(R.color.viewfinder_mask);
    setMiddleTitle(R.string.code_bar_capture_title_txt);
    setLeftTitleButton(R.drawable.btn_title_bar_back_selector, new OnClickListener() {
      @Override
      public void onClick(View v) {
        onBackPressed();
      }
    });
    return true;
  }


  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mTimeCount != null) {
      mTimeCount.cancel();
      mTimeCount = null;
    }
  }

  @Override
  protected void handleScanResult(String codebar) {
    // 向主界面发Intent
    // TODO
    //startActivity(HomeActivity.makeJumpToScanQrCodeIntent(this, codebar));
    finish();
  }

  /**
   * 定义一个倒计时的内部类<BR>
   *
   * @author chunjiang.shieh <chunjiang.shieh@gmail.com>
   * @version [Paitao Client V20130911, 2014-4-1]
   */
  class AuthCodeTimeCount extends CountDownTimer {
    public AuthCodeTimeCount(long millisInFuture, long countDownInterval) {
      super(millisInFuture, countDownInterval);
    }

    @Override
    public void onFinish() {
      // 无法识别
      new AlertDialog.Builder(CodeBarCaptureActivity.this).setPositiveButton(R.string
          .btn_confirm, new DialogInterface
          .OnClickListener() {


        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
          restartScan();
          // 重启扫码倒计时
          mTimeCount.start();
        }
      }).setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

        }
      }).show();

    }

    @Override
    public void onTick(long millisUntilFinished) {
    }
  }

  /**
   * 生成二维码扫描的Intent
   *
   * @param context Context
   * @return 返回Intent
   */
  public static Intent makeCodeBarCaptureIntent(Context context) {
    return new Intent(context, CodeBarCaptureActivity.class);
  }
}
