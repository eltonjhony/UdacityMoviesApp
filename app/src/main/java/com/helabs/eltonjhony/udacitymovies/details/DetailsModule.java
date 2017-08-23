package com.helabs.eltonjhony.udacitymovies.details;

import com.helabs.eltonjhony.udacitymovies.di.FragmentScoped;
import com.helabs.eltonjhony.udacitymovies.infrastructure.ApplicationMessages;

import java.lang.ref.WeakReference;

import dagger.Module;
import dagger.Provides;

/**
 * Created by eltonjhony on 04/08/17.
 */
@Module
public class DetailsModule {

    private final WeakReference<DetailsContract.View> mView;

    public DetailsModule(DetailsContract.View view) {
        mView = new WeakReference<>(view);
    }

    @Provides
    @FragmentScoped
    WeakReference<DetailsContract.View> provideView() {
        return mView;
    }

    @Provides
    @FragmentScoped
    DetailsContract.Actions provideDetailsPresenter(ApplicationMessages applicationMessages) {
        return new DetailsPresenter(mView, applicationMessages);
    }
}
