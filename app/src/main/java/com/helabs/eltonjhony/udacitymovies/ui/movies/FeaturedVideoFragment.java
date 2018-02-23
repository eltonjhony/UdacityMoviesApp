package com.helabs.eltonjhony.udacitymovies.ui.movies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.helabs.eltonjhony.udacitymovies.R;
import com.helabs.eltonjhony.udacitymovies.infrastructure.ApplicationConfiguration;
import com.helabs.eltonjhony.udacitymovies.infrastructure.MyLog;

/**
 * Created by eltonjhony on 10/04/17.
 */
public class FeaturedVideoFragment extends YouTubePlayerSupportFragment {

    public static final String VIDEO_URL_EXTRA = "com.helabs.eltonjhony.VIDEO_URL";
    private static final String TAG = FeaturedVideoFragment.class.getSimpleName();

    private YouTubePlayer mYoutubePlayer;

    public static FeaturedVideoFragment newInstance(String videoUrl) {
        Bundle bundle = new Bundle();
        bundle.putString(VIDEO_URL_EXTRA, videoUrl);
        FeaturedVideoFragment fragment = new FeaturedVideoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(ApplicationConfiguration.getYoutubeKey(), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                mYoutubePlayer = youTubePlayer;
                mYoutubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                if (!b) {
                    mYoutubePlayer.cueVideo(getArguments().getString(FeaturedVideoFragment.VIDEO_URL_EXTRA), 0);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                MyLog.info(TAG, getString(R.string.generic_error_message));
            }
        });
    }

}
