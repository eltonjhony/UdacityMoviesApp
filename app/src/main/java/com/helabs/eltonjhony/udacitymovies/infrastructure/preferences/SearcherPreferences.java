package com.helabs.eltonjhony.udacitymovies.infrastructure.preferences;

import android.content.Context;

import javax.inject.Inject;

/**
 * Created by eltonjhony on 13/04/17.
 */

public class SearcherPreferences extends SimplePreferences {

    @Inject
    public SearcherPreferences(Context context) {
        super(context);
    }

    @Override
    protected String providePreferencesString() {
        return "com.helabs.eltonjhony.SEARCH_PREF";
    }
}
