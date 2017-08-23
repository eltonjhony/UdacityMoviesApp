package com.helabs.eltonjhony.udacitymovies.data.model;

import com.google.gson.annotations.SerializedName;
import com.helabs.eltonjhony.udacitymovies.infrastructure.MyLog;

import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by eltonjhony on 4/7/17.
 */
@Parcel
public class MovieDetail extends ContentDetail {

    private static final String RELEASE_DATE_FINAL_PATTERN = "dd-MMM-yyyy";
    private static final String RELEASE_DATE_API_PATTERN = "yyyy-mm-dd";

    @SerializedName("title")
    String title;

    @SerializedName("release_date")
    String released;

    public MovieDetail() {
    }

    public MovieDetail(String id, String poster,
                       String popularity, String overview, String backdropPath,
                       String voteAverage, String voteCount,
                       String title, String released) {
        super(id, poster, popularity, overview, backdropPath, voteAverage, voteCount);
        this.title = title;
        this.released = released;
    }

    public String getTitle() {
        return title;
    }

    public String getReleased() {
        return released;
    }

    public String getReleasedDateFormatted() {
        final SimpleDateFormat sdf = new SimpleDateFormat(RELEASE_DATE_FINAL_PATTERN);
        final SimpleDateFormat sdf2 = new SimpleDateFormat(RELEASE_DATE_API_PATTERN);
        try {
            return sdf.format(sdf2.parse(getReleased()));
        } catch (ParseException e) {
            MyLog.info("An error occurred when convert release date: " + e.getMessage());
            return getReleased();
        }
    }
}
