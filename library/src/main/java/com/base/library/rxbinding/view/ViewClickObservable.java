package com.base.library.rxbinding.view;

import android.view.View;
import android.view.View.OnClickListener;

import com.base.library.rxbinding.internal.Notification;
import com.base.library.rxbinding.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;


final class ViewClickObservable extends Observable<Object> {
  private final View view;

  ViewClickObservable(View view) {
    this.view = view;
  }

  @Override protected void subscribeActual(Observer<? super Object> observer) {
    if (!Preconditions.checkMainThread(observer)) {
      return;
    }
    Listener listener = new Listener(view, observer);
    observer.onSubscribe(listener);
    view.setOnClickListener(listener);
  }

  static final class Listener extends MainThreadDisposable implements OnClickListener {
    private final View view;
    private final Observer<? super Object> observer;

    Listener(View view, Observer<? super Object> observer) {
      this.view = view;
      this.observer = observer;
    }

    @Override public void onClick(View v) {
      if (!isDisposed()) {
        observer.onNext(Notification.INSTANCE);
      }
    }

    @Override protected void onDispose() {
      view.setOnClickListener(null);
    }
  }
}
