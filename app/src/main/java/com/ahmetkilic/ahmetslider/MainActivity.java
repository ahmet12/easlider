package com.ahmetkilic.ahmetslider;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.ahmetkilic.imageslider.EASlide;
import com.ahmetkilic.imageslider.EASlider;
import com.ahmetkilic.imageslider.EASliderAdapter;
import com.ahmetkilic.imageslider.SliderClickListener;
import com.ahmetkilic.imageslider.indicator.EAIndicator;
import com.ahmetkilic.imageslider.transformers.TabletTransformer;

public class MainActivity extends AppCompatActivity implements SliderClickListener {

    private EASlider eaSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSlider();
    }

    private void initSlider() {
        eaSlider = (EASlider) findViewById(R.id.slider);
        /*
        Default Values
            eaSlider.enableSwipe();
            eaSlider.setBlockTimeMillis(100);
            eaSlider.setAutoScrollDuration(4000);
            eaSlider.setScrollDuration(400);
        */

        EASliderAdapter eaSliderAdapter = new EASliderAdapter(getSupportFragmentManager());
        eaSliderAdapter.setProgressLayoutId(R.layout.progress_layout);
        eaSliderAdapter.setErrorLayoutId(R.layout.error_layout);

        /*
        Default Values
            EASliderAdapter.setDisableLoop(false);
            EASliderAdapter.setDisableLoopForOneElement(true);
        */
        addSlides(eaSliderAdapter);

        eaSlider.setAdapter(eaSliderAdapter);
        eaSlider.setPageTransformer(false, new TabletTransformer());

        EAIndicator indicator = (EAIndicator) findViewById(R.id.indicator);
        indicator.setIconSelectedResId(R.drawable.ic_car);
        indicator.attachToSlider(eaSlider);
    }

    private void addSlides(EASliderAdapter EASliderAdapter) {
        EASlide EASlide1 = new EASlide("http://www.menucool.com/slider/prod/image-slider-1.jpg");
        EASlide EASlide2 = new EASlide("http://www.menucool.com/slider/prod/image-slider-2.jpg");
        EASlide EASlide3 = new EASlide("http://www.menucool.com/slider/prod/image-slider-3.jpg");
        EASlide EASlide4 = new EASlide("http://www.menucool.com/slider/prod/image-slider-4.jpg");
        EASlide EASlide5 = new EASlide("http://www.menucool.com/slider/prod/image-slider-5.jpg");

        EASlide1.getExtras().putString("title", "EASlide 1");
        EASlide2.getExtras().putString("title", "EASlide 2");
        EASlide3.getExtras().putString("title", "EASlide 3");
        EASlide4.getExtras().putString("title", "EASlide 4");
        EASlide5.getExtras().putString("title", "EASlide 5");

        EASliderAdapter.addSlide(EASlide1);
        EASliderAdapter.addSlide(EASlide2);
        EASliderAdapter.addSlide(EASlide3);
        EASliderAdapter.addSlide(EASlide4);
        EASliderAdapter.addSlide(EASlide5);
    }

    @Override
    public void onSliderClicked(EASlide EASlide) {
        if (EASlide.getExtras() != null) {
            String extra_title = EASlide.getExtras().getString("title", "");
            Toast.makeText(this, "EASlide Title:" + extra_title, Toast.LENGTH_SHORT).show();
            if (eaSlider.isAutoScrollActive())
                eaSlider.stopAutoScroll();
            else
                eaSlider.startAutoScroll();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        eaSlider.stopAutoScroll();
    }

    @Override
    protected void onResume() {
        super.onResume();
        eaSlider.startAutoScroll();
    }

}
