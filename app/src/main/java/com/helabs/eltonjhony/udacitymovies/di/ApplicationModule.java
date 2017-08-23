package com.helabs.eltonjhony.udacitymovies.di;

import android.content.Context;

import com.helabs.eltonjhony.udacitymovies.MyApplication;
import com.helabs.eltonjhony.udacitymovies.infrastructure.ApplicationMessages;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by eltonjhony on 4/3/17.
 */
@Module
public class ApplicationModule {

    private final MyApplication app;

    public ApplicationModule(MyApplication app) {
        this.app = app;
    }

    @Singleton
    @Provides
    Context provideAppContext() {
        return app;
    }

    @Singleton
    @Provides
    ApplicationMessages provideApplicationMessages(Context context) {
        return new ApplicationMessages(context);
    }
}
