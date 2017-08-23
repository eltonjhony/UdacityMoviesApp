package com.helabs.eltonjhony.udacitymovies.infrastructure.preferences;

import android.content.Context;

import javax.inject.Inject;

/**
 * Created by eltonjhony on 13/04/17.
 */
public class CollapseFeaturedVideoPreferences extends SimplePreferences {

    @Inject
    public CollapseFeaturedVideoPreferences(Context context) {
        super(context);
    }

    @Override
    protected String providePreferencesString() {
        return "com.helabs.eltonjhony.SHOW_HIDE_PREF";
    }
}
