package com.helabs.eltonjhony.udacitymovies.details;

import android.text.TextUtils;

import com.helabs.eltonjhony.udacitymovies.data.local.LocalFavoritesDataSource;
import com.helabs.eltonjhony.udacitymovies.data.model.Favorites;
import com.helabs.eltonjhony.udacitymovies.data.model.MovieDetail;
import com.helabs.eltonjhony.udacitymovies.infrastructure.ApplicationMessages;
import com.helabs.eltonjhony.udacitymovies.common.BasePresenter;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

/**
 * Created by eltonjhony on 3/31/17.
 */
public class DetailsPresenter extends BasePresenter<DetailsContract.View> implements DetailsContract.Actions {

    private LocalFavoritesDataSource localFavoritesDataSource;
    private ApplicationMessages applicationMessages;

    @Inject
    public DetailsPresenter(WeakReference<DetailsContract.View> view,
                            LocalFavoritesDataSource localFavoritesDataSource,
                            ApplicationMessages applicationMessages) {
        super(view);
        this.localFavoritesDataSource = localFavoritesDataSource;
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

    @Override
    public void markAsFavorite(MovieDetail movieDetail) {
        final Favorites request = Favorites.set(movieDetail);
        localFavoritesDataSource.getFavoritesById(request.getMovieId(), favorites -> {
            if (favorites != null) {
                localFavoritesDataSource.delete(favorites);
                getView().favoriteUnMarked(true);
            } else {
                localFavoritesDataSource.insert(request);
                getView().favoriteMarked();
            }
        });
    }

    @Override
    public void checkFavorites(String movieId) {
        localFavoritesDataSource.getFavoritesById(movieId, favorites -> {
            if (favorites != null) {
                getView().favoriteMarked();
            } else {
                getView().favoriteUnMarked(false);
            }
        });
    }
}
