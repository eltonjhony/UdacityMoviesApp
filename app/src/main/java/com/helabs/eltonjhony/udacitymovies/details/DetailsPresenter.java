package com.helabs.eltonjhony.udacitymovies.details;

import android.text.TextUtils;

import com.helabs.eltonjhony.udacitymovies.infrastructure.ApplicationMessages;
import com.helabs.eltonjhony.udacitymovies.common.BasePresenter;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

/**
 * Created by eltonjhony on 3/31/17.
 */
public class DetailsPresenter extends BasePresenter<DetailsContract.View> implements DetailsContract.Actions {

    private ApplicationMessages applicationMessages;

    @Inject
    public DetailsPresenter(WeakReference<DetailsContract.View> view, ApplicationMessages applicationMessages) {
        super(view);
        this.applicationMessages = applicationMessages;
    }

    @Override
    public void defineOverviewMessage(String overview) {
        if (TextUtils.isEmpty(overview)) {
            getView().setOverview(applicationMessages.getDefaultOverviewMessage());
        } else {
            getView().setOverview(overview);
        }
    }

    @Override
    public void onDestroy() {
        detachView();
    }
}
