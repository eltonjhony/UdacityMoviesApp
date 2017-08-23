package com.helabs.eltonjhony.udacitymovies.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eltonjhony on 3/31/17.
 */
public class DataResultWrapper<T> extends ResponseWrapper {

    @SerializedName("page")
    private int page;

    @SerializedName("success")
    private boolean success;

    @SerializedName("results")
    private List<T> results;

    private String videoKey;

    public DataResultWrapper() {
    }

    public DataResultWrapper(List<T> results) {
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<T> getData() {
        if (results == null) return new ArrayList<>();
        return results;
    }

    public String getVideoKey() {
        return videoKey;
    }

    public void setVideoKey(String videoKey) {
        this.videoKey = videoKey;
    }
}
