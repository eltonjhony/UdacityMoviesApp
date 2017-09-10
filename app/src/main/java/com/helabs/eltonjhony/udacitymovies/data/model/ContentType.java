package com.helabs.eltonjhony.udacitymovies.data.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.helabs.eltonjhony.udacitymovies.data.model.ContentType.FAVORITE;
import static com.helabs.eltonjhony.udacitymovies.data.model.ContentType.NOW_PLAYING;
import static com.helabs.eltonjhony.udacitymovies.data.model.ContentType.POPULAR;
import static com.helabs.eltonjhony.udacitymovies.data.model.ContentType.SEARCH;
import static com.helabs.eltonjhony.udacitymovies.data.model.ContentType.TOP_RATED;

/**
 * Created by eltonjhony on 10/04/17.
 */
@IntDef({POPULAR, NOW_PLAYING, TOP_RATED, SEARCH, FAVORITE})
@Retention(RetentionPolicy.SOURCE)
public @interface ContentType {

    int POPULAR = 0;
    int NOW_PLAYING = 1;
    int TOP_RATED = 2;
    int SEARCH = 3;
    int FAVORITE = 4;
}



