package com.helabs.eltonjhony.udacitymovies.trailers;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.helabs.eltonjhony.udacitymovies.R;
import com.helabs.eltonjhony.udacitymovies.common.BaseFragment;
import com.helabs.eltonjhony.udacitymovies.data.model.Video;
import com.helabs.eltonjhony.udacitymovies.databinding.FragmentTrailerBinding;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by eltonjhony on 20/05/17.
 */

public class TrailersFragment extends BaseFragment implements TrailersContract.View {

    public static final String MOVIE_ID_TAG = "com.helabs.eltonjhony.MOVIE.ID.TAG";

    @Inject
    TrailersPresenter mPresenter;

    private FragmentTrailerBinding mBinding;

    public static TrailersFragment newInstance(String id) {
        TrailersFragment f = new TrailersFragment();
        Bundle args = new Bundle();
        args.putString(MOVIE_ID_TAG, id);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().plus(new TrailersModule(this)).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_trailer, container, false);

        String movieId = getArguments().getString(MOVIE_ID_TAG);
        if (!TextUtils.isEmpty(movieId)) {
            mPresenter.fetchTrailers(movieId);
        }

        return mBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showTrailers(List<Video> trailers) {
        for (Video video : trailers) {
            mPresenter.loadThumbnail(video, video.getThumbnail());
        }
    }

    @Override
    public void addTrailerToGallery(Video video, Bitmap bitmap) {
        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(1100, 900));
        imageView.setPadding(0,0,25,0);
        imageView.setImageBitmap(bitmap);
        imageView.setOnClickListener(v -> {
            Intent ytPlayerIntent = new Intent(getContext(), PlayerActivity.class);
            ytPlayerIntent.putExtra(PlayerActivity.VIDEO_TAG, video.getKey());
            getContext().startActivity(ytPlayerIntent);
        });
        getLayout().trailersGallery.addView(imageView);
    }

    @Override
    public void showError(String message) {
        Snackbar.make(getLayout().scrollLayout, message, Snackbar.LENGTH_LONG).show();
    }

    private FragmentTrailerBinding getLayout() {
        return mBinding;
    }
}
