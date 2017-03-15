package com.shudongedu.energy.utils;

import android.os.Environment;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * 设备信息工具类<BR>
 */
public class DeviceUtil {

  /**
   * 判断SdCard是否可用
   */
  public static boolean isExternalStorageAvailable() {
    try {
      return MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    } catch (NullPointerException | IncompatibleClassChangeError e) {
      return false;
    }
  }
}
