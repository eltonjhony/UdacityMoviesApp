package com.helabs.eltonjhony.udacitymovies.ui.trailers;

import android.graphics.Bitmap;

import com.helabs.eltonjhony.udacitymovies.data.model.Video;

import java.io.IOException;
import java.util.List;

/**
 * Created by eltonjhony on 3/31/17.
 */

public interface TrailersContract {

    interface View {

        void showTrailers(List<Video> trailers);

        void addTrailerToGallery(Video video, Bitmap bitmap);

        void showError(String message);

        void hideTrailerSection();
    }

    interface Actions {
        void fetchTrailers(String movieId);

        void loadThumbnail(Video video, String url) throws IOException;

        void onDestroy();
    }
}
