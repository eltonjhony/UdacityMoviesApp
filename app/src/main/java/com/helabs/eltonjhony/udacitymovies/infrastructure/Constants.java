package com.helabs.eltonjhony.udacitymovies.infrastructure;

/**
 * Created by eltonjhony on 4/6/17.
 */
public class Constants {

    private Constants() {
    }

    public interface PreferenceKeys {

        String SEARCHER_KEY = new String(new char[]{'a', 's', 'd', 'e', 'c', 'a', 'f', 'd', 'g',
                'h', 'u', 'i', 'r', 'x', 'e', 'r', 'd', 't', 'y', 't', 'q', 's', 't', 'd', 's', 'e' });

        String SHOW_HIDE_KEY = new String(new char[]{'e', 'y', 'b', 'f', 'e', 'a', 'x', 'd', 'x',
                'q', 'e', 'r', 'c', 'r', 'r', 'j', 'g', 'r', 'e', 'i', 'u', 'y', 'f', 'r', 't', 'w' });;
    }

    public interface ApplicationConfigurationParameter {
        String BASE_URL = "base_url";
        String API_KEY = "api_key";
        String YOUTUBE_KEY = "yt_key";
    }
}
