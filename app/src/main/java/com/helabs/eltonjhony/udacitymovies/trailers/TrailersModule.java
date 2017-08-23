package com.helabs.eltonjhony.udacitymovies.trailers;

import com.helabs.eltonjhony.udacitymovies.data.repository.TrailersRepository;
import com.helabs.eltonjhony.udacitymovies.di.FragmentScoped;

import java.lang.ref.WeakReference;

import dagger.Module;
import dagger.Provides;

/**
 * Created by eltonjhony on 04/08/17.
 */
@Module
public class TrailersModule {

    private final WeakReference<TrailersContract.View> mView;

    public TrailersModule(TrailersContract.View view) {
        mView = new WeakReference<>(view);
    }

    @Provides
    @FragmentScoped
    WeakReference<TrailersContract.View> provideView() {
        return mView;
    }

    @Provides
    @FragmentScoped
    TrailersContract.Actions provideTrailersPresenter(TrailersRepository trailersRepository) {
        return new TrailersPresenter(mView, trailersRepository);
    }
}
