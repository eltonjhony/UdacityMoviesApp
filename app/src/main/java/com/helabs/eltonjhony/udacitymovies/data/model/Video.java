package com.helabs.eltonjhony.udacitymovies.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by eltonjhony on 10/04/17.
 */
public class Video implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("key")
    private String key;

    @SerializedName("name")
    private String name;

    public Video() {
    }

    public Video(String id, String key, String name) {
        this.id = id;
        this.key = key;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getThumbnail() {
        return "http://img.youtube.com/vi/" + this.getKey() + "/0.jpg";
    }
}
