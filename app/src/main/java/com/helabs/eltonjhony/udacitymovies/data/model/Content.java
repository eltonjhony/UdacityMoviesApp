package com.helabs.eltonjhony.udacitymovies.data.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.io.Serializable;

/**
 * Created by eltonjhony on 3/31/17.
 */
@Parcel
public class Content implements Serializable {

    private static final String BASE_POSTER_URL = "http://image.tmdb.org/t/p/";
    private static final String IMG_SIZE_185 = "w185";
    private static final String IMG_SIZE_780 = "w780";

    @SerializedName("id")
    String id;

    @SerializedName("poster_path")
    String posterUrl;

    @SerializedName("backdrop_path")
    String backdropPath;

    public Content() {
    }

    public Content(String id, String posterUrl, String backdropPath) {
        this.id = id;
        this.posterUrl = posterUrl;
        this.backdropPath = backdropPath;
    }

    public String getId() {
        return id;
    }

    public String getPosterUrl() {
        if (TextUtils.isEmpty(this.posterUrl)) {
            return null;
        }
        StringBuilder builder = new StringBuilder(BASE_POSTER_URL);
        builder.append(IMG_SIZE_185).append("/").append(posterUrl);
        return builder.toString();
    }

    public String getBackdropPath() {
        if (TextUtils.isEmpty(this.backdropPath)) {
            return null;
        }
        StringBuilder builder = new StringBuilder(BASE_POSTER_URL);
        builder.append(IMG_SIZE_780).append("/").append(this.backdropPath);
        return builder.toString();
    }
}
