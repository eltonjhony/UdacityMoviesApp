package com.helabs.eltonjhony.udacitymovies.infrastructure;

import android.annotation.SuppressLint;

import com.helabs.eltonjhony.udacitymovies.BuildConfig;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by eltonjhony on 4/6/17.
 */
@SuppressWarnings("unused")
public class ApplicationConfiguration {

    @SuppressLint("UseSparseArrays")
    private static final Map<String, String> applicationParameterCacheMap = new HashMap<>();

    static {
        applicationParameterCacheMap.put(Constants.ApplicationConfigurationParameter.BASE_URL, BuildConfig.BASE_URL);
        applicationParameterCacheMap.put(Constants.ApplicationConfigurationParameter.API_KEY, BuildConfig.API_KEY);
        applicationParameterCacheMap.put(Constants.ApplicationConfigurationParameter.YOUTUBE_KEY, BuildConfig.YOUTUBE_KEY);
    }

    public static String getBaseUrl() {
        return applicationParameterCacheMap.get(Constants.ApplicationConfigurationParameter.BASE_URL);
    }

    public static String getApiKey() {
        return applicationParameterCacheMap.get(Constants.ApplicationConfigurationParameter.API_KEY);
    }

    public static String getYoutubeKey() {
        return applicationParameterCacheMap.get(Constants.ApplicationConfigurationParameter.YOUTUBE_KEY);
    }

    public static String getLanguage() {
        return Locale.getDefault().getLanguage();
    }

}
