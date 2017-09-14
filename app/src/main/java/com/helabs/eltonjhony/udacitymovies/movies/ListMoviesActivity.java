package com.helabs.eltonjhony.udacitymovies.movies;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.helabs.eltonjhony.udacitymovies.R;
import com.helabs.eltonjhony.udacitymovies.bus.ToggleMenuEvent;
import com.helabs.eltonjhony.udacitymovies.common.BaseActivity;
import com.helabs.eltonjhony.udacitymovies.databinding.ActivityMainBinding;
import com.helabs.eltonjhony.udacitymovies.leftMenu.MenuFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by eltonjhony on 19/05/17.
 */
public class ListMoviesActivity extends BaseActivity {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setupToolbar();
        setupDrawerToggle(getLayout().drawerLayout, getLayout().toolbarLayout.toolbar);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        replaceFragment(R.id.menu_container, MenuFragment.newInstance(R.id.main_content));
        replaceFragment(R.id.main_content, MoviesFragment.newInstance());
    }

    private void setupToolbar(){
        setSupportActionBar(getLayout().toolbarLayout.toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowHomeEnabled(true);
            supportActionBar.setDisplayHomeAsUpEnabled(false);
            supportActionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ToggleMenuEvent event) {
        getLayout().drawerLayout.closeDrawers();
    }

    private ActivityMainBinding getLayout() {
        return mBinding;
    }
}
