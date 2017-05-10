# easlider
An image slider for android.

### Gradle
```gradle
compile 'com.ahmetkilic.ahmetslider:ea-slider:1.0.1'
```

### Usage
```java

        EASlider eaSlider = (EASlider) findViewById(R.id.slider);
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
            eaSliderAdapter.setDisableLoop(false);
            eaSliderAdapter.setDisableLoopForOneElement(true);
        */

        EASlide EASlide1 = new EASlide("http://www.menucool.com/slider/prod/image-slider-1.jpg");
        EASlide1.getExtras().putString("title", "EASlide 1");
        eaSliderAdapter.addSlide(EASlide1);

        eaSlider.setAdapter(eaSliderAdapter);
        eaSlider.setPageTransformer(false, new TabletTransformer());

        EAIndicator indicator = (EAIndicator) findViewById(R.id.indicator);
        indicator.setIconSelectedResId(R.drawable.ic_car);
        indicator.attachToSlider(eaSlider);
```
