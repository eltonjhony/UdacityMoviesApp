package com.helabs.eltonjhony.udacitymovies.utils;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

/**
 * Created by eltonjhony on 20/05/17.
 */

public class FontCacheUtil {

    private static HashMap<String, Typeface> fontCache = new HashMap<>();

    private FontCacheUtil() {
    }

    public static Typeface getTypeface(String fontName, Context context) {
        Typeface typeface = fontCache.get(fontName);

        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), fontName);
            } catch (Exception e) {
                return null;
            }

            fontCache.put(fontName, typeface);
        }

        return typeface;
    }
}
