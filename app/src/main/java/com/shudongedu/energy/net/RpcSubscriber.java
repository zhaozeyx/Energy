package com.shudongedu.energy.net;

import io.reactivex.subscribers.DisposableSubscriber;

public abstract class RpcSubscriber<T> extends DisposableSubscriber<T> {
  public RpcSubscriber() {
  }

  @Override
  public void onStart() {
    request(Integer.MAX_VALUE);
  }

  @Override
  public void onComplete() {
    this.onEnd();
  }

  public void onError(Throwable e) {
    this.onEnd();
  }

  public void onNext(T result) {
    this.onSucceed(result);
  }

  public void onEnd() {
  }

  public abstract void onSucceed(T var1);

  public abstract void onApiError(RpcApiError var1);

  public abstract void onHttpError(RpcHttpError var1);
}