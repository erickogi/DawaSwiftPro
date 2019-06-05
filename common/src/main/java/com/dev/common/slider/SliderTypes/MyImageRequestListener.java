package com.dev.common.slider.SliderTypes;

import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

class MyImageRequestListener implements RequestListener<Drawable> {
    public MyImageRequestListener(BaseSliderView baseSliderView) {

    }

    @Override
    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {


        return false;
    }

    @Override
    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
        return false;
    }

    public interface Callback {
        void onFailure( String message);
        void onSuccess( String dataSource);
    }
}
