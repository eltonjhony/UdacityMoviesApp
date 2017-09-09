package com.helabs.eltonjhony.udacitymovies.bus;

/**
 * Created by eltonjhony on 09/09/17.
 */

public final class UpdateTrailersEvent {

    private boolean trailersLoaded;

    public UpdateTrailersEvent(boolean trailersLoaded) {
        this.trailersLoaded = trailersLoaded;
    }

    public boolean isTrailersLoaded() {
        return trailersLoaded;
    }
}
