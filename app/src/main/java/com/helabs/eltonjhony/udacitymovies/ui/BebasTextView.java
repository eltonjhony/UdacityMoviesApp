package com.helabs.eltonjhony.udacitymovies.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.helabs.eltonjhony.udacitymovies.utils.FontCacheUtil;

/**
 * Created by eltonjhony on 20/05/17.
 */

public class BebasTextView extends BaseTextView {

    public BebasTextView(Context context) {
        super(context);
    }

    public BebasTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BebasTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void applyCustomFont(Context context) {
        Typeface customFont = FontCacheUtil.getTypeface("fonts/BebasNeue.otf", context);
        setTypeface(customFont);
    }
}
