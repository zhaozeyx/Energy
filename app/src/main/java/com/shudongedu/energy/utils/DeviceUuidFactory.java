package com.shudongedu.energy.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 生成UUID工具类<BR>
 */
public class DeviceUuidFactory {
  /**
   * file name
   */
  private static final String PREFS_FILE = ".device_info";

  /**
   * new file name
   */
  private static final String NEW_PREFS_FILE = ".boot";

  /**
   * new file dir
   */
  private static final String NEW_PREFS_DIR = ".system";

  /**
   * key name
   */
  private static final String PREFS_UUID = "device_id";

  /**
   * 需要存放UUID的目录
   */
  private static final String[] DIRS = {"/DCIM", "/Movies", "/Music", "/Pictures", "/"};

  //.system .boot
  //DCIM Movies Movies Pictures

  /**
   * 单例对象
   */
  private static DeviceUuidFactory mInstance;

  /**
   * UUID
   */
  private UUID uuid;

  /**
   * context
   */
  private Context mContext;

  /**
   * 单例 对象
   *
   * @param context Context
   * @return DeviceInfoUtil
   */
  public static synchronized DeviceUuidFactory getInstance(@NonNull Context context) {
    if (mInstance == null) {
      mInstance = new DeviceUuidFactory(context.getApplicationContext());
    }
    return mInstance;
  }

  /**
   * 构造函数
   */
  public DeviceUuidFactory(Context context) {
    mContext = context;
    if (uuid == null) {
      synchronized (DeviceUuidFactory.class) {
        if (uuid == null) {
          String uuidStr = readUuidStr();
          if (uuidStr != null) {
            // 获取保存的UUID
            try {
              uuid = UUID.fromString(uuidStr);
              //读取成功也要保存一次，这样可以保证每个地方的保存都是最新状态
              saveUuidStr();
              return;
            } catch (Exception e) {
              e.printStackTrace();
            }
          }

          uuid = getNewUUID();
          saveUuidStr();
        }
      }
    }
  }

  /**
   * 获取UUID<BR>
   *
   * @return UUID
   */
  public UUID getDeviceUuid() {
    return uuid;
  }

  /**
   * 获取UUID祖父穿<BR>
   *
   * @return UUID字符串
   */
  public String getDeviceUuidString() {
    return fomatUuidString();
  }

  /**
   * 格式化UUID 字符串<BR/>
   * 去除首尾空格、去掉“-”、全部变为大写
   */
  private String fomatUuidString() {
    String uuidString = uuid.toString();
    uuidString = uuidString.replace("-", "").trim().toUpperCase();
    return uuidString;
  }

  /**
   * 生成一个新的UUID<BR>
   * 生成顺序是deviceId、wifimac、randomUUID
   *
   * @return 返回新生成的UUID
   */
  private UUID getNewUUID() {
    UUID newUuid;
    String uuidSeed = getDeviceId();
    if (TextUtils.isEmpty(uuidSeed)) {
      uuidSeed = getWifiMac();
    }

    try {
      if (!TextUtils.isEmpty(uuidSeed)) {
        newUuid = UUID.nameUUIDFromBytes(uuidSeed.getBytes("utf8"));
      } else {
        newUuid = UUID.randomUUID();
      }
    } catch (UnsupportedEncodingException e) {
      newUuid = UUID.randomUUID();
    }
    return newUuid;
  }

  /**
   * 返回设备的device id<BR>
   *
   * @return device id
   */
  private String getDeviceId() {
    TelephonyManager TelephonyMgr =
        (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
    String deviceId = TelephonyMgr.getDeviceId();
    //水货手机可能返回此device id
    if (!TextUtils.isEmpty(deviceId) && !TextUtils.equals("000000000000000", deviceId)) {
      return deviceId;
    }
    return null;
  }

  /**
   * 获取设备的Wifi mac地址<BR>
   *
   * @return 设备wifi的mac地址
   */
  private String getWifiMac() {
    // TODO WifiInfo#getMacAddress will always return 02:00:00:00:00:00 on Android 6.0
    WifiManager wm = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
    return wm.getConnectionInfo().getMacAddress();
  }

  /**
   * 从本地保存中读取UUID string<BR>
   * 读取的顺序是SharedPreferences file
   *
   * @return uuid string
   */
  private String readUuidStr() {
    //首先从文件中读取
    String seedId = readUuidFile();
    if (!TextUtils.isEmpty(seedId)) {
      return seedId;
    }

    //最后从SharedPreferences中读取
    SharedPreferences prefs = mContext.getSharedPreferences(PREFS_FILE, 0);
    seedId = prefs.getString(PREFS_UUID, null);
    if (!TextUtils.isEmpty(seedId)) {
      return seedId;
    }
    return null;
  }

  /**
   * 读取所有存放UUID的文件
   */
  private String readUuidFile() {
    File firstFile = null;
    List<File> files = getFilesPath();
    //读取1.6.1 新加的存放文件
    for (File file : files) {
      File uuidFile = new File(file, NEW_PREFS_FILE);
      if (uuidFile.exists()) {
        //比较一个最原始的文件
        if (null == firstFile || uuidFile.lastModified() < firstFile.lastModified()) {
          firstFile = uuidFile;
        }
      }
    }

    //读取原始旧的储存文件
    File uuidFile = getFilePath();
    if (uuidFile != null && uuidFile.exists() && (null == firstFile
        || uuidFile.lastModified() < firstFile.lastModified())) {
      firstFile = uuidFile;
    }

    if (null != firstFile && firstFile.exists()) {
      try {
        RandomAccessFile f = new RandomAccessFile(firstFile, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();
        return new String(bytes);
      } catch (IOException e) {
        return null;
      }
    }

    return null;
  }

  /**
   * 保存UUID String<BR>
   */

  private void saveUuidStr() {
    final SharedPreferences prefs = mContext.getSharedPreferences(PREFS_FILE, 0);
    prefs.edit().putString(PREFS_UUID, uuid.toString()).apply();

    new Thread(new Runnable() {
      @Override
      public void run() {
        writeUuidFile();
      }
    }).start();
  }

  /**
   * 保存到文件<BR>
   */
  private void writeUuidFile() {

    List<File> files = getFilesPath();
    for (File file : files) {
      try {
        File installation = new File(file, NEW_PREFS_FILE);
        FileOutputStream out = new FileOutputStream(installation);
        String id = uuid.toString();
        out.write(id.getBytes());
        out.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 获取要保存的文件目录<BR>
   * 这个方法是获取原始存储的地方
   *
   * @return 要保存的文件目录
   */
  private File getFilePath() {
    File fileDir = null;
    //判断sd卡是否存在
    boolean sdCardExist = DeviceUtil.isExternalStorageAvailable();
    if (sdCardExist) {
      fileDir = new File(Environment.getExternalStorageDirectory().getPath()
          + "/"
          + PREFS_FILE
          + "/"
          + PREFS_FILE);
    }

    return fileDir;
  }

  /**
   * 获取要保存的文件目录<BR>
   * 多个目录存储
   *
   * @return 要保存的文件目录
   */
  private List<File> getFilesPath() {
    List<File> files = new ArrayList<>();
    File fileDir;
    //判断sd卡是否存在
    boolean sdCardExist = DeviceUtil.isExternalStorageAvailable();
    //1.6.1 新添加的存储UUID的地址
    for (String dir : DIRS) {
      if (sdCardExist) {
        //父级目录
        fileDir = new File(Environment.getExternalStorageDirectory().getPath() + dir);

        if (fileDir.exists()) {
          fileDir = new File(
              Environment.getExternalStorageDirectory().getPath() + dir + "/" + NEW_PREFS_DIR);
          fileDir.mkdirs();
        }
        if (fileDir.exists()) {
          files.add(fileDir);
        }
      }
    }

    //  data/data目录
    fileDir = mContext.getFilesDir();
    files.add(fileDir);

    return files;
  }
}
