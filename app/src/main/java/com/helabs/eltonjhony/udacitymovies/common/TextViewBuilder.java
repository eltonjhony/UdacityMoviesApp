package com.helabs.eltonjhony.udacitymovies.common;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by eltonjhony on 09/09/17.
 */
public class TextViewBuilder {

    private TextViewBuilder() {
    }

    public static Builder with(Context context) {
        return new Builder(context);
    }

    public static class Builder extends ViewBuilder.BaseBuilder {

        private String text;
        private int color;

        Builder(Context context) {
            super(context);
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder textColor(int color) {
            this.color = color;
            return this;
        }

        @Override
        public void into(ViewGroup viewGroup) {
            TextView textView = new TextView(context);
            textView.setText(text);
            textView.setTextColor(color);
            textView.setPadding(left, top, right, bottom);
            viewGroup.addView(textView);
        }
    }
}
