package com.helabs.eltonjhony.udacitymovies.details;

import android.text.TextUtils;

import com.helabs.eltonjhony.udacitymovies.data.local.FavoritesDAO;
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

    private FavoritesDAO favoritesDAO;
    private ApplicationMessages applicationMessages;

    @Inject
    public DetailsPresenter(WeakReference<DetailsContract.View> view, FavoritesDAO favoritesDAO, ApplicationMessages applicationMessages) {
        super(view);
        this.favoritesDAO = favoritesDAO;
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
        final Favorites favorites = favoritesDAO.getById(request.getMovieId());
        if (favorites != null) {
            favoritesDAO.delete(favorites);
            getView().favoriteUnMarked(true);
        } else {
            favoritesDAO.insert(request);
            getView().favoriteMarked();
        }
    }

    @Override
    public void checkFavorites(String movieId) {
        Favorites favorites = favoritesDAO.getById(movieId);
        if (favorites != null) {
            getView().favoriteMarked();
        } else {
            getView().favoriteUnMarked(false);
        }
    }
}
