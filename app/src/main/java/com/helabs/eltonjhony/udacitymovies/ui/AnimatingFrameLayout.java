package com.helabs.eltonjhony.udacitymovies.ui;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.helabs.eltonjhony.udacitymovies.R;

/**
 * Created by eltonjhony on 11/04/17.
 */
public class AnimatingFrameLayout extends FrameLayout {

    private Context context;
    private Animation inAnimation;
    private Animation outAnimation;

    public AnimatingFrameLayout(@NonNull Context context) {
        super(context);
        this.context = context;
        initAnimations();
    }

    public AnimatingFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAnimations();
    }

    public AnimatingFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAnimations();
    }


    private void initAnimations() {
        inAnimation = AnimationUtils.loadAnimation(context, R.anim.in_animation);
        outAnimation = AnimationUtils.loadAnimation(context, R.anim.out_animation);
    }

    public void active(boolean active) {
        if (active) {
            show(true);
        } else {
            hide(true);
        }
    }

    public void show(boolean withAnimation) {
        if (withAnimation) this.startAnimation(inAnimation);
        this.setVisibility(View.VISIBLE);
    }

    public void hide(boolean withAnimation) {
        if (withAnimation) this.startAnimation(outAnimation);
        this.setVisibility(View.GONE);
    }

    public boolean isVisible() {
        return (this.getVisibility() == View.VISIBLE);
    }
}
