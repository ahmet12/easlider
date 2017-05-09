package com.ahmetkilic.imageslider;

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

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.ArrayList;
import java.util.List;

/*
 * Created by AhmetPC on 26.04.2017.
 */

public class EASliderAdapter extends FragmentPagerAdapter {

    private int progressViewResId;
    private int errorViewResId;
    private List<EASlide> EASlides;
    private boolean disableLoopForOneElement;
    private boolean disableLoop;

    public EASliderAdapter(FragmentManager fm) {
        super(fm);
        EASlides = new ArrayList<>();
        setProgressLayoutId(0);
        setDisableLoop(false);
        setDisableLoopForOneElement(true);
    }

    public void setProgressLayoutId(int progressViewResId) {
        this.progressViewResId = progressViewResId;
    }


    public void setErrorLayoutId(int errorViewResId) {
        this.errorViewResId = errorViewResId;
    }

    public void addSlide(EASlide EASlide) {
        EASlides.add(EASlide);
        notifyDataSetChanged();
    }

    public void setDisableLoop(boolean disableLoop) {
        this.disableLoop = disableLoop;
    }

    public void setDisableLoopForOneElement(boolean disableLoopForOneElement) {
        this.disableLoopForOneElement = disableLoopForOneElement;
    }

    @Override
    public Fragment getItem(int position) {
        return SlideFragment.newInstance(EASlides.get(position % getRealCount()), progressViewResId, errorViewResId);
    }

    @Override
    public int getCount() {
        if (disableLoop)
            return getRealCount();
        else
            return disableLoopForOneElement && getRealCount() == 1 ? 1 : Integer.MAX_VALUE;
    }

    public int getRealCount() {
        return EASlides.size();
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
