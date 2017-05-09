package com.ahmetkilic.imageslider;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/*
 * Created by AhmetPC on 2.05.2017.
 */

class FixedSpeedScroller extends Scroller {

    private int mDuration = 500;

    FixedSpeedScroller(Context context) {
        super(context);
    }

    FixedSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    void setFixedDuration(int mDuration) {
        this.mDuration = mDuration;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
}