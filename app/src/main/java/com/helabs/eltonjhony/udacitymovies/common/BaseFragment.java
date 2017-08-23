package com.helabs.eltonjhony.udacitymovies.common;

import android.support.v4.app.Fragment;

import com.helabs.eltonjhony.udacitymovies.MyApplication;
import com.helabs.eltonjhony.udacitymovies.di.ApplicationComponent;

/**
 * Created by eltonjhony on 11/08/17.
 */
public abstract class BaseFragment extends Fragment {

    /**
     * Get the Main Application component for dependency injection.
     */
    protected ApplicationComponent getApplicationComponent() {
        return ((MyApplication) getActivity().getApplication()).getApplicationComponent();
    }
}
