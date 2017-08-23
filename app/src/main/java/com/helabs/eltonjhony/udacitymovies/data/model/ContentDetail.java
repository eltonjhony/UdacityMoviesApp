package com.helabs.eltonjhony.udacitymovies.data.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by eltonjhony on 4/3/17.
 */
@Parcel
public class ContentDetail extends Content {

    @SerializedName("popularity")
    String popularity;

    @SerializedName("overview")
    String overview;

    @SerializedName("vote_average")
    String voteAverage;

    @SerializedName("vote_count")
    String voteCount;

    public ContentDetail() {
    }

    public ContentDetail(String id, String poster,
                         String popularity, String overview, String backdropPath,
                         String voteAverage, String voteCount) {
        super(id, poster, backdropPath);
        this.popularity = popularity;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    public String getOverview() {
        return overview;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public String getPopularityConverted() {
        return String.format("%.1f", Float.parseFloat(getPopularity()));
    }

    public Float getVoteAverageAsFloat() {
        return (Float.parseFloat(getVoteAverage()) / 2);
    }
}
