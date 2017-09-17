package com.helabs.eltonjhony.udacitymovies.common;

import android.support.annotation.UiThread;

import java.lang.ref.WeakReference;

/**
 * Created by eltonjhony on 02/05/17.
 */
public abstract class BasePresenter<V> implements BasePresenterContract<V> {

    private WeakReference<V> mView;

    public BasePresenter(WeakReference<V> view) {
        this.mView = view;
    }

    @UiThread
    @Override
    public V getViewOrThrow() {
        V view = this.getView();
        if (view == null) {
            throw new IllegalStateException("The view must be attached!");
        }
        return view;
    }

    @UiThread
    @Override
    public V getView() {
        return this.mView != null ? this.mView.get() : null;
    }

    @UiThread
    @Override
    public void detachView() {
        if (this.mView != null); {
            this.mView.clear();
            this.mView = null;
        }
    }

    @UiThread
    @Override
    public boolean isViewAttached() {
        return getView() != null;
    }
}
