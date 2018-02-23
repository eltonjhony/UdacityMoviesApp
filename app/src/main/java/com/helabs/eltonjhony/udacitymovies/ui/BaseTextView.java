package com.helabs.eltonjhony.udacitymovies.ui;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by eltonjhony on 20/05/17.
 */

public abstract class BaseTextView extends android.support.v7.widget.AppCompatTextView {

    public BaseTextView(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public BaseTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public BaseTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    protected abstract void applyCustomFont(Context context);
}
