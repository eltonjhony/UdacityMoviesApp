package com.helabs.eltonjhony.udacitymovies.common;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by eltonjhony on 09/09/17.
 */
public class ViewBuilder {

    private ViewBuilder() {
    }

    public static BaseBuilder with(Context context) {
        return new BaseBuilder(context);
    }

    public static class BaseBuilder {

        Context context;
        int color;
        int left, right, bottom, top;
        LinearLayout.LayoutParams params;

        BaseBuilder(Context context) {
            this.context = context;
        }

        public BaseBuilder backgroundColor(int color) {
            this.color = color;
            return this;
        }

        public BaseBuilder params(LinearLayout.LayoutParams params) {
            this.params = params;
            return this;
        }

        public BaseBuilder padding(int left, int top, int right, int bottom) {
            this.left = left;
            this.top = top;
            this.bottom = bottom;
            this.right = right;
            return this;
        }

        public void into(ViewGroup viewGroup) {
            View view = new View(context);
            view.setBackgroundColor(color);
            view.setLayoutParams(params);
            view.setPadding(left, top, right, bottom);
            viewGroup.addView(view);
        }
    }
}
