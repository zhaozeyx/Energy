/*
 * Copyright (C) 2010 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.shudongedu.energy.codebar.decoding;

import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.shudongedu.energy.R;
import com.shudongedu.energy.codebar.CodeBarCaptureBaseActivity;
import com.shudongedu.energy.codebar.camera.CameraManager;
import com.shudongedu.energy.log.Logger;
import java.lang.ref.WeakReference;
import java.util.Hashtable;
import net.sourceforge.zbar.Image;

/**
 * 子线程中接受扫描结果并进行解析的Handler<BR>
 *
 * @version [Paitao Client V20130911, 2014-1-4]
 */
final class DecodeHandler extends Handler {
  private static final String TAG = "CodeBarCaptureActivity";

  /**
   * zing zbar 切换解析
   */
  private boolean mDecoderSwitch = false;

  private final WeakReference<CodeBarCaptureBaseActivity> mWeakActivity;

  private final MultiFormatReader multiFormatReader;

  DecodeHandler(CodeBarCaptureBaseActivity activity, Hashtable<DecodeHintType, Object> hints) {
    multiFormatReader = new MultiFormatReader();
    multiFormatReader.setHints(hints);
    mWeakActivity = new WeakReference<CodeBarCaptureBaseActivity>(activity);
  }

  @Override
  public void handleMessage(Message message) {
    switch (message.what) {
      case R.id.decode:
        decode((byte[]) message.obj, message.arg1, message.arg2);
        break;
      case R.id.quit:
        Looper.myLooper().quit();
        break;
    }
  }

  /**
   * Decode the data within the viewfinder rectangle, and time how long it took. For efficiency,
   * reuse the same reader objects from one decode to the next.
   *
   * @param data The YUV preview frame.
   * @param width The width of the preview frame.
   * @param height The height of the preview frame.
   */
  private void decode(byte[] data, int width, int height) {
    mDecoderSwitch = !mDecoderSwitch;
    if (mDecoderSwitch) {
      Logger.e(TAG, "switch to zxing");
      decodeByZxing(data, width, height);
    } else {
      Logger.e(TAG, "switch to zbar");
      decodeByZbar(data, width, height);
    }
  }

  //Zxing解析
  private void decodeByZxing(byte[] data, int width, int height) {
    Result rawResult = null;
    //modify here
    byte[] rotatedData = new byte[data.length];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        rotatedData[x * height + height - y - 1] = data[x + y * width];
      }
    }
    // Here we are swapping, that's the difference to #11
    int tmp = width;
    width = height;
    height = tmp;

    PlanarYUVLuminanceSource source =
        CameraManager.get().buildLuminanceSource(rotatedData, width, height);
    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
    try {
      rawResult = multiFormatReader.decodeWithState(bitmap);
    } catch (ReaderException re) {
      re.printStackTrace();
      // continue
    } finally {
      multiFormatReader.reset();
    }

    CodeBarCaptureBaseActivity activity = mWeakActivity.get();
    if (null == activity) {
      return;
    }

    if (rawResult != null && rawResult.getText() != null) {
      Message message =
          Message.obtain(activity.getCodeBarHandler(), R.id.decode_succeeded, rawResult);
      message.sendToTarget();
    } else {
      Message message = Message.obtain(activity.getCodeBarHandler(), R.id.decode_failed);
      message.sendToTarget();
    }
  }

  //Zbar解析
  private void decodeByZbar(byte[] data, int width, int height) {
    CodeBarCaptureBaseActivity activity = mWeakActivity.get();
    if (null == activity) {
      return;
    }

    Image barcode = new Image(width, height, "Y800");
    Rect scanRect = activity.getViewfinderView().getScanRect();
    barcode.setCrop(scanRect.top - 30, scanRect.left - 30, scanRect.width() + 60,
        scanRect.height() + 60);
    barcode.setData(data);

    int resultCode = activity.getImageScanner().scanImage(barcode);

    if (resultCode != 0) {
      Message message = Message.obtain(activity.getCodeBarHandler(), R.id.decode_succeeded,
          activity.getImageScanner().getResults());
      message.sendToTarget();
    } else {
      Message message = Message.obtain(activity.getCodeBarHandler(), R.id.decode_failed);
      message.sendToTarget();
    }
  }
}
