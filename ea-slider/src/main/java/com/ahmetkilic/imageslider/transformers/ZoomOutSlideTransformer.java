package com.ahmetkilic.imageslider.transformers;

import android.view.View;

public class ZoomOutSlideTransformer extends BaseTransformer {

    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;

    @Override
    protected void onTransform(View view, float position) {
        if (position >= -1 || position <= 1) {

            final float height = view.getHeight();
            final float width = view.getWidth();
            final float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            final float verticalMargin = height * (1 - scaleFactor) / 2;
            final float horizontalMargin = width * (1 - scaleFactor) / 2;

            view.setPivotY(0.5f * height);
            view.setPivotX(0.5f * width);

            if (position < 0) {
                view.setTranslationX(horizontalMargin - verticalMargin / 2);
            } else {
                view.setTranslationX(-horizontalMargin + verticalMargin / 2);
            }

            // Scale the page down (between MIN_SCALE and 1)
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

            // Fade the page relative to its size.
            view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
        }
    }

}
