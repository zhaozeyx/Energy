package com.shudongedu.energy.net;

import android.content.Context;
import com.shudongedu.energy.CustomApp;
import com.shudongedu.energy.log.Logger;
import com.shudongedu.energy.net.rpc.service.NetConstant;
import io.reactivex.subscribers.DisposableSubscriber;
import rx.Subscriber;

/**
 * 简化的，供UI调用网络接口使用的RpcSubscriber，统一处理HttpError提示
 */
public abstract class UiRpcSubscriber1<T> extends DisposableSubscriber<T> {
  private static final String TAG = "SERVER_ERROR";
  HttpErrorUiNotifier httpErrorUiNotifier;
  private Context mContext;

  public UiRpcSubscriber1(Context context) {
    mContext = context;
    httpErrorUiNotifier =
        ((CustomApp) context.getApplicationContext()).getGlobalComponent().httpErrorUiNotifier();
  }

  @Override
  public final void onComplete() {
    onEnd();
  }

  @Override
  public final void onError(Throwable e) {
    onHttpError(new RpcHttpError(NetConstant.HttpCodeConstant.UNKNOWN_ERROR, ""));
    Logger.e(TAG, e, e.getMessage());
    onComplete();
  }


  @Override
  public final void onNext(T t) {
    onSuccess(t);
    onComplete();
  }

  public void onApiError(RpcApiError apiError) {
  }

  public void onHttpError(RpcHttpError httpError) {
    httpErrorUiNotifier.notifyUi(httpError);
  }

  protected abstract void onSuccess(T t);

  protected abstract void onEnd();

}
