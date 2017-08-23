package com.helabs.eltonjhony.udacitymovies.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.helabs.eltonjhony.udacitymovies.common.BaseTextView;
import com.helabs.eltonjhony.udacitymovies.utils.FontCacheUtil;

/**
 * Created by eltonjhony on 20/05/17.
 */

public class RalewayTextView extends BaseTextView {

    public RalewayTextView(Context context) {
        super(context);
    }

    public RalewayTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RalewayTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void applyCustomFont(Context context) {
        Typeface customFont = FontCacheUtil.getTypeface("fonts/Raleway-Medium.ttf", context);
        setTypeface(customFont);
    }
}
