package com.helabs.eltonjhony.udacitymovies;

import android.app.Application;
import android.content.Context;

import com.helabs.eltonjhony.udacitymovies.di.ApplicationComponent;
import com.helabs.eltonjhony.udacitymovies.di.ApplicationModule;
import com.helabs.eltonjhony.udacitymovies.di.DaggerApplicationComponent;

/**
 * Created by eltonjhony on 4/3/17.
 */

public class MyApplication extends Application {

    private static MyApplication application;
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        initializeInjector();
    }

    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    public static MyApplication getApplication() {
        return application;
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}
