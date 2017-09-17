package com.helabs.eltonjhony.udacitymovies.common;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

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

    protected String getBackStackKey(Fragment fragment) {
        return fragment.getClass().getName();
    }

    protected void replaceFragment(Fragment fragment, int id, FragmentManager manager) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(getBackStackKey(fragment));
        transaction.replace(id, fragment);
        transaction.commitAllowingStateLoss();
    }

    protected void popBackStack(FragmentManager manager) {
        if (manager.getBackStackEntryCount() > 0) {
            manager.popBackStack();
        }
    }
}
