package com.ahmetkilic.imageslider.indicator;

/*
 * Copyright 2017 Ahmet Kılıç
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ahmetkilic.imageslider.EASlider;
import com.ahmetkilic.imageslider.R;

/*
 * Created by AhmetPC on 27.04.2017.
 */

public class EAIndicator extends FrameLayout implements ViewPager.OnPageChangeListener {

    private EASlider mSlider;
    private int iconNormalResId;
    private int iconSelectedResId;
    private LinearLayout holder;
    private RelativeLayout currentIconView;
    private RelativeLayout tempView;
    private int slider_width;
    private int item_width;

    private IndicatorAttr indicatorAttr;

    public EAIndicator(Context context) {
        super(context);
        setupAttrs(null);
        init();
    }

    public EAIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupAttrs(attrs);
        init();
    }

    public EAIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupAttrs(attrs);
        init();
    }

    private void setupAttrs(AttributeSet attrs) {
        indicatorAttr = new IndicatorAttr(attrs, getContext());
    }

    private void init() {
        holder = new LinearLayout(getContext());
        holder.setOrientation(LinearLayout.HORIZONTAL);
        addView(holder);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.START;
        setLayoutParams(params);

        item_width = 0;
        slider_width = 0;
    }

    private void setIconNormalResId(int iconNormalResId) {
        this.iconNormalResId = iconNormalResId;
    }

    public void setIconSelectedResId(int iconSelectedResId) {
        this.iconSelectedResId = iconSelectedResId;
    }

    public void attachToSlider(EASlider slider) {
        if (slider != null) {
            this.mSlider = slider;
            initSlider();
        } else
            throw new NullPointerException("EASlide is NULL!");
    }

    private void initSlider() {
        for (int i = 0; i < mSlider.getAdapter().getRealCount(); i++)
            addIconNormal();
        addIconCurrent();
        mSlider.addOnPageChangeListener(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (mSlider != null)
            slider_width = mSlider.getWidth();
        if (holder.getChildAt(0) != null)
            item_width = holder.getChildAt(0).getWidth();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (slider_width != 0 && item_width != 0) {
            int real_pos = position % mSlider.getAdapter().getRealCount();
            float rate = slider_width / item_width;

            float translation = (item_width * real_pos) + (positionOffsetPixels / rate);

            setXForView(currentIconView, translation);
            validateTempViewTranslation();
        }
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private void validateTranslationX(int real_pos) {
        if (slider_width != 0 && item_width != 0) {
            setXForView(currentIconView, item_width * real_pos);
            validateTempViewTranslation();
        }
    }

    private void validateTempViewTranslation() {
        setXForView(tempView, currentIconView.getTranslationX() - (item_width * (mSlider.getAdapter().getRealCount())));
    }

    private void setXForView(RelativeLayout view, float x) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            view.animate().x(x).setDuration(0).start();
        else
            view.setX(x);
    }

    private void addIconNormal() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.indicator_single_icon, holder, false);
        view.setLayoutParams(getItemParams());

        AppCompatImageView imageView = (AppCompatImageView) view.findViewById(R.id.ic_single);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        params.setMargins(indicatorAttr.eai_ic_margin, indicatorAttr.eai_ic_margin, indicatorAttr.eai_ic_margin, indicatorAttr.eai_ic_margin);
        imageView.setLayoutParams(params);
        if (iconNormalResId != 0)
            imageView.setImageResource(iconNormalResId);
        else
            imageView.setImageDrawable(indicatorAttr.eai_ic_src);
        imageView.invalidate();
        holder.addView(view);
        holder.invalidate();
    }

    private void addIconCurrent() {
        currentIconView = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.indicator_single_icon, this, false);
        currentIconView.setLayoutParams(getItemParams());

        AppCompatImageView imageView = (AppCompatImageView) currentIconView.findViewById(R.id.ic_single);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        params.setMargins(indicatorAttr.eai_ic_current_margin, indicatorAttr.eai_ic_current_margin, indicatorAttr.eai_ic_current_margin, indicatorAttr.eai_ic_current_margin);

        imageView.setLayoutParams(params);
        if (iconSelectedResId != 0)
            imageView.setImageResource(iconSelectedResId);
        else
            imageView.setImageDrawable(indicatorAttr.eai_ic_current_src);
        imageView.invalidate();

        tempView = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.indicator_single_icon, this, false);
        tempView.setLayoutParams(getItemParams());

        AppCompatImageView imageView2 = (AppCompatImageView) tempView.findViewById(R.id.ic_single);
        imageView2.setLayoutParams(params);
        if (iconSelectedResId != 0)
            imageView2.setImageResource(iconSelectedResId);
        else
            imageView2.setImageDrawable(indicatorAttr.eai_ic_current_src);
        imageView2.invalidate();

        addView(currentIconView);
        addView(tempView);

        invalidate();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                int real_pos = mSlider.getCurrentItem() % mSlider.getAdapter().getRealCount();
                validateTranslationX(real_pos);
            }
        }, 100);
    }

    private LinearLayout.LayoutParams getItemParams() {
        return new LinearLayout.LayoutParams(indicatorAttr.eai_item_size, indicatorAttr.eai_item_size);
    }
}
