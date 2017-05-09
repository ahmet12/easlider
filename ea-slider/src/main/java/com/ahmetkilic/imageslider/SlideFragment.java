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

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class SlideFragment extends Fragment {

    private static final String ARG_SLIDE = "EASlide";
    private static final String ARG_PROGRESS = "progress_id";
    private static final String ARG_ERROR = "error_id";
    private EASlide EASlide;
    private SliderClickListener mListener;
    private View progressView;
    private int progressViewResId, errorViewResId;

    public SlideFragment() {
    }

    public static SlideFragment newInstance(EASlide EASlide, int progressViewResId, int errorViewResId) {
        SlideFragment fragment = new SlideFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SLIDE, EASlide);
        args.putInt(ARG_PROGRESS, progressViewResId);
        args.putInt(ARG_ERROR, errorViewResId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            EASlide = (EASlide) getArguments().getSerializable(ARG_SLIDE);
            progressViewResId = getArguments().getInt(ARG_PROGRESS);
            errorViewResId = getArguments().getInt(ARG_ERROR);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_slide, container, false);
        AppCompatImageView imageView = (AppCompatImageView) view.findViewById(R.id.slider_image);

        if (progressViewResId != 0) {
            progressView = inflater.inflate(progressViewResId, (ViewGroup) view, false);
            ((ViewGroup) view).addView(progressView);
        }

        Callback callback = new Callback() {
            @Override
            public void onSuccess() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    progressView.animate().alpha(0.0f).setDuration(500).start();
                } else
                    progressView.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    progressView.animate().alpha(0.0f).setDuration(500).start();
                } else
                    progressView.setVisibility(View.GONE);
                showImageError(inflater, view);
            }
        };

        if (EASlide.getFile() != null)
            Picasso.with(getContext()).load(EASlide.getFile()).fit().centerCrop().into(imageView, callback);
        else if (EASlide.getPath() != null)
            Picasso.with(getContext()).load(EASlide.getPath()).fit().centerCrop().into(imageView, callback);
        else if (EASlide.getUri() != null)
            Picasso.with(getContext()).load(EASlide.getUri()).fit().centerCrop().into(imageView, callback);
        else if (EASlide.getResourceId() != 0)
            Picasso.with(getContext()).load(EASlide.getResourceId()).fit().centerCrop().into(imageView, callback);
        else
            showImageError(inflater, view);

        if (mListener != null)
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null)
                        mListener.onSliderClicked(EASlide);
                }
            });

        return view;
    }

    private void showImageError(LayoutInflater inflater, View view) {
        if (errorViewResId != 0 && view != null && inflater != null) {
            View errorView = inflater.inflate(errorViewResId, (ViewGroup) view, false);
            ((ViewGroup) view).addView(errorView);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SliderClickListener)
            mListener = (SliderClickListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
