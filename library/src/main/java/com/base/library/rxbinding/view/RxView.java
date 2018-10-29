package com.base.library.rxbinding.view;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.view.View;

import com.base.library.rxbinding.internal.Preconditions;

import io.reactivex.Observable;

/**
 * RxView
 * @author xuechao
 * @date 2018/10/29 上午11:19
 * @copyright cpx
 */

public final class RxView {

    /**
     * Create an observable which emits on {@code view} click events. The emitted value is
     * unspecified and should only be used as notification.
     * <p>
     * <em>Warning:</em> The created observable keeps a strong reference to {@code view}. Unsubscribe
     * to free this reference.
     * <p>
     * <em>Warning:</em> The created observable uses {@link View#setOnClickListener} to observe
     * clicks. Only one observable can be used for a view at a time.
     */
    @CheckResult
    @NonNull
    public static Observable<Object> clicks(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return new ViewClickObservable(view);
    }

    private RxView() {
        throw new AssertionError("No instances.");
    }

}
