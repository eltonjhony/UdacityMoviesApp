package com.helabs.eltonjhony.udacitymovies.details;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.transition.Slide;
import android.view.MenuItem;

import com.helabs.eltonjhony.udacitymovies.R;
import com.helabs.eltonjhony.udacitymovies.common.BaseActivity;
import com.helabs.eltonjhony.udacitymovies.data.model.MovieDetail;
import com.helabs.eltonjhony.udacitymovies.databinding.ActivityDetailsBinding;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

public class DetailsActivity extends BaseActivity {

    public static final String MOVIE_EXTRA = "com.helabs.eltonjhony.MOVIE_DETAIL";

    private ActivityDetailsBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        initialize();
        setupAppBar();

        MovieDetail movieDetail = Parcels.unwrap(getIntent().getParcelableExtra(MOVIE_EXTRA));
        Picasso.with(this).load(movieDetail.getBackdropPath()).into(getLayout().image);
        getLayout().collapsingToolbar.setTitle(movieDetail.getTitle());

        if (savedInstanceState == null) {
            addFragment(R.id.container_detail, DetailsFragment.newInstance(movieDetail));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public ActivityDetailsBinding getLayout() {
        return mBinding;
    }

    private void initialize() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }

    private void setupAppBar() {
        ViewCompat.setTransitionName(getLayout().appBarLayout, getString(R.string.app_name));
        supportPostponeEnterTransition();
        setSupportActionBar(getLayout().toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
