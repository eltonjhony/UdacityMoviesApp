package com.helabs.eltonjhony.udacitymovies.di;

import android.content.Context;

import com.helabs.eltonjhony.udacitymovies.infrastructure.preferences.CollapseFeaturedVideoPreferences;
import com.helabs.eltonjhony.udacitymovies.infrastructure.preferences.SearcherPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by eltonjhony on 11/08/17.
 */
@Module
public class PreferencesModule {

    @Singleton
    @Provides
    SearcherPreferences provideSearcherPreferences(Context context) {
        return new SearcherPreferences(context);
    }

    @Singleton
    @Provides
    CollapseFeaturedVideoPreferences provideCollapseFeaturedVideoPreferences(Context context) {
        return new CollapseFeaturedVideoPreferences(context);
    }
}
