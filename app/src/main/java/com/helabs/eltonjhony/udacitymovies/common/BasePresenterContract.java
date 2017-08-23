package com.helabs.eltonjhony.udacitymovies.common;

/**
 * Created by eltonjhony on 02/05/17.
 */

public interface BasePresenterContract<V> {

    V getViewOrThrow();

    V getView();

    void detachView();

    boolean isViewAttached();
}
