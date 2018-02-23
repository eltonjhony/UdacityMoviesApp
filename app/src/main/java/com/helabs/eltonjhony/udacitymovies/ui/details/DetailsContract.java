package com.helabs.eltonjhony.udacitymovies.ui.details;

import com.helabs.eltonjhony.udacitymovies.data.model.MovieDetail;

/**
 * Created by eltonjhony on 3/31/17.
 */

public interface DetailsContract {

    interface View {

        void setOverview(String overview);

        void favoriteMarked(boolean updateFavorites);

        void favoriteUnMarked(boolean updateFavorites);
    }

    interface Actions {

        void defineOverviewMessage(String overview);

        void onDestroy();

        void markAsFavorite(MovieDetail movieDetail);

        void checkFavorites(String movieId);
    }
}
