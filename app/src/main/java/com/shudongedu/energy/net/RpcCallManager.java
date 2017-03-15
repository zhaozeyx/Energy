package com.shudongedu.energy.net;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subscribers.DisposableSubscriber;

public interface RpcCallManager {

  <T> void manageRpcCall(Flowable<T> observable, DisposableSubscriber<T> subscribe);

  /**
   * RpcCallManager实现类
   */
  class RpcCallManagerImpl implements RpcCallManager {

    private CompositeDisposable mCompositeSubscription = new CompositeDisposable();

    public <T> void manageRpcCall(Flowable<T> observable, final DisposableSubscriber<T> subscribe) {
      mCompositeSubscription.add(observable.observeOn(io.reactivex.android.schedulers
          .AndroidSchedulers.mainThread()).subscribeWith
          (subscribe));
    }

    public void unSubscribeAll() {
      mCompositeSubscription.dispose();
    }
  }

}