package com.helabs.eltonjhony.udacitymovies.details;

/**
 * Created by eltonjhony on 3/31/17.
 */

public interface DetailsContract {

    interface View {

        void setOverview(String overview);
    }

    interface Actions {

        void defineOverviewMessage(String overview);

        void onDestroy();
    }
}
