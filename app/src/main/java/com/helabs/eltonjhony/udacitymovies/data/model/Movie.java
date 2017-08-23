package com.helabs.eltonjhony.udacitymovies.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by eltonjhony on 4/6/17.
 */
public class Movie extends Content implements Serializable {

    @SerializedName("title")
    private String title;

    public Movie() {
    }

    public Movie(String id, String posterUrl, String backdropPath, String title) {
        super(id, posterUrl, backdropPath);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
