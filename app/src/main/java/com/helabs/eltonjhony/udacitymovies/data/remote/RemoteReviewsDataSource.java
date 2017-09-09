package com.helabs.eltonjhony.udacitymovies.data.remote;

import com.helabs.eltonjhony.udacitymovies.data.ReviewsDataSource;
import com.helabs.eltonjhony.udacitymovies.data.exceptions.NoInternetException;
import com.helabs.eltonjhony.udacitymovies.data.model.DataResultWrapper;
import com.helabs.eltonjhony.udacitymovies.data.model.Review;
import com.helabs.eltonjhony.udacitymovies.infrastructure.ApplicationConfiguration;
import com.helabs.eltonjhony.udacitymovies.infrastructure.ApplicationMessages;
import com.helabs.eltonjhony.udacitymovies.utils.NetworkUtil;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.helabs.eltonjhony.udacitymovies.infrastructure.ApplicationConfiguration.getApiKey;
import static com.helabs.eltonjhony.udacitymovies.infrastructure.ApplicationConfiguration.getLanguage;

/**
 * Created by eltonjhony on 31/08/17.
 */

public class RemoteReviewsDataSource extends BaseDataSource implements ReviewsDataSource {

    private Api api;
    private NetworkUtil networkUtil;
    private ApplicationMessages applicationMessages;

    public RemoteReviewsDataSource(Api api, NetworkUtil networkUtil, ApplicationMessages applicationMessages) {
        this.api = api;
        this.networkUtil = networkUtil;
        this.applicationMessages = applicationMessages;
    }

    @Override
    public Observable<DataResultWrapper<Review>> findUserReviews(String movieId) {

        if (!networkUtil.isNetworkAvailable()) {
            return Observable.error(new NoInternetException(applicationMessages.getNoNetworkAvailableMessage()));
        }

        return this.api.getReviews(movieId, getApiKey(), getLanguage())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(this::handleServerResponse);
    }
}
