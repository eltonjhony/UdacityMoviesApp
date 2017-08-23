package com.helabs.eltonjhony.udacitymovies.trailers;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.helabs.eltonjhony.udacitymovies.R;
import com.helabs.eltonjhony.udacitymovies.databinding.ActivityPlayerBinding;
import com.helabs.eltonjhony.udacitymovies.infrastructure.ApplicationConfiguration;

/**
 * Created by eltonjhony on 20/05/17.
 */

public class PlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    public static final String VIDEO_TAG = "com.helabs.eltonjhony.VIDEO_TAG";

    private ActivityPlayerBinding mBinding;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_player);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_player);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLayout().playerView.initialize(ApplicationConfiguration.getYoutubeKey(), this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean restored) {
        if (!restored) {
            player.loadVideo(getIntent().getStringExtra(VIDEO_TAG));
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        Toast.makeText(this, getString(R.string.failed), Toast.LENGTH_LONG).show();
    }

    private ActivityPlayerBinding getLayout() {
        return mBinding;
    }
}
