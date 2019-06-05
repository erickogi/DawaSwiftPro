package com.dev.common.slider.SliderTypes;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dev.common.R;
import com.dev.common.utils.CommonUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;


/**
 * @author kogi
 */
public abstract class BaseSliderView implements MyImageRequestListener.Callback{

    private Context mContext;
    private OnSliderClickListener mOnSliderClickListener;
    private Bundle mBundle;
    /**
     * Error place holder logo_image.
     */
    private int mErrorPlaceHolderRes;
    private View v;
    /**
     * Empty imageView placeholder.
     */
    private int mEmptyPlaceHolderRes;
    private String mUrl;
    private File mFile;
    private int mRes;
    private boolean mErrorDisappear;

    private ImageLoadListener mLoadListener;

    private String mDescription;

//    private Picasso mPicasso;

    /**
     * Scale type of the logo_image.
     */
    private ScaleType mScaleType = ScaleType.Fit;

    protected BaseSliderView(Context context) {
        mContext = context;
    }

    /**
     * the placeholder logo_image when loading logo_image from url or file.
     *
     * @param resId Image resource id
     * @return
     */
    public BaseSliderView empty(int resId) {
        mEmptyPlaceHolderRes = resId;
        return this;
    }

    /**
     * determine whether remove the logo_image which failed to download or load from file
     *
     * @param disappear
     * @return
     */
    public BaseSliderView errorDisappear(boolean disappear) {
        mErrorDisappear = disappear;
        return this;
    }

    /**
     * if you set errorDisappear false, this will set a error placeholder logo_image.
     *
     * @param resId logo_image resource id
     * @return
     */
    public BaseSliderView error(int resId) {
        mErrorPlaceHolderRes = resId;
        return this;
    }

    /**
     * the description of a slider logo_image.
     *
     * @param description
     * @return
     */
    public BaseSliderView description(String description) {
        mDescription = description;
        return this;
    }

    /**
     * set a url as a logo_image that preparing to load
     *
     * @param url
     * @return
     */
    public BaseSliderView image(String url) {
        if (mFile != null || mRes != 0) {
            throw new IllegalStateException("Call multi logo_image function," +
                    "you only have permission to call it once");
        }
        mUrl = url;
        return this;
    }

    /**
     * set a file as a logo_image that will to load
     *
     * @param file
     * @return
     */
    public BaseSliderView image(File file) {
        if (mUrl != null || mRes != 0) {
            throw new IllegalStateException("Call multi logo_image function," +
                    "you only have permission to call it once");
        }
        mFile = file;
        return this;
    }

    public BaseSliderView image(int res) {
        if (mUrl != null || mFile != null) {
            throw new IllegalStateException("Call multi logo_image function," +
                    "you only have permission to call it once");
        }
        mRes = res;
        return this;
    }

    /**
     * lets users add a bundle of additional information
     *
     * @param bundle
     * @return
     */
    public BaseSliderView bundle(Bundle bundle) {
        mBundle = bundle;
        return this;
    }

    public String getUrl() {
        return mUrl;
    }

    public boolean isErrorDisappear() {
        return mErrorDisappear;
    }

    public int getEmpty() {
        return mEmptyPlaceHolderRes;
    }

    public int getError() {
        return mErrorPlaceHolderRes;
    }

    public String getDescription() {
        return mDescription;
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * set a slider logo_image click listener
     *
     * @param l
     * @return
     */
    public BaseSliderView setOnSliderClickListener(OnSliderClickListener l) {
        mOnSliderClickListener = l;
        return this;
    }

    /**
     * When you want to implement your own slider view, please call this method in the end in `getView()` method
     *
     * @param v               the whole view
     * @param targetImageView where to place logo_image
     */
    protected void bindEventAndShow(final View v, ImageView targetImageView) {
        final BaseSliderView me = this;

        this.v = v;
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSliderClickListener != null) {
                    mOnSliderClickListener.onSliderClick(me);
                }
            }
        });

        if (targetImageView == null)
            return;

        if (mLoadListener != null) {
            mLoadListener.onStart(me);
        }

        // Picasso p = (mPicasso != null) ? mPicasso : Picasso.get();
        if (mUrl != null) {
//            Glide.with(getContext()).load(mUrl).apply(new RequestOptions().placeholder(R.drawable.ic_sentiment_dissatisfied)
//                    .error(R.drawable.ic_sentiment_dissatisfied)
//                    .centerCrop().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).listener(new MyImageRequestListener(this)).into(targetImageView);

            new CommonUtils().loadImage(getContext(),mUrl,targetImageView);

        } else if (mFile != null) {
            //rq = p.load(mFile);

//            Glide.with(getContext()).load(mFile).apply(new RequestOptions().placeholder(R.drawable.ic_sentiment_dissatisfied)
//                    .error(R.drawable.ic_sentiment_dissatisfied)
//                    .centerCrop().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).listener(new MyImageRequestListener(this)).into(targetImageView);

            //new CommonUtils().loadImage(getContext(),mUrl,targetImageView);

        } else if (mRes != 0) {
            // rq = p.load(mRes);
//            Glide.with(getContext()).load(mRes).apply(new RequestOptions().placeholder(R.drawable.ic_sentiment_dissatisfied)
//                    .error(R.drawable.ic_sentiment_dissatisfied)
//
//                    .centerCrop().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).listener(new MyImageRequestListener(this)).into(targetImageView);
//
           // new CommonUtils().loadImage(getContext(),mUrl,targetImageView);


            if (v.findViewById(R.id.loading_bar) != null) {
                v.findViewById(R.id.loading_bar).setVisibility(View.INVISIBLE);
            }
        } else {
            return;
        }


//        if (rq == null) {
//            return;
//        }
//
//        if (getEmpty() != 0) {
//            rq.placeholder(getEmpty());
//        }
//
//        if (getError() != 0) {
//            rq.error(getError());
//        }
//
//        switch (mScaleType) {
//            case Fit:
//                rq.fit();
//                break;
//            case CenterCrop:
//                rq.fit().centerCrop();
//                break;
//            case CenterInside:
//                rq.fit().centerInside();
//                break;
//        }
//
//        rq.into(targetImageView, new Callback() {
//            @Override
//            public void onSuccess() {
//                if (v.findViewById(R.id.loading_bar) != null) {
//                    v.findViewById(R.id.loading_bar).setVisibility(View.INVISIBLE);
//                }
//            }
//
//            @Override
//            public void onError(Exception e) {
//                if (mLoadListener != null) {
//                    mLoadListener.onEnd(false, me);
//                }
//                if (v.findViewById(R.id.loading_bar) != null) {
//                    v.findViewById(R.id.loading_bar).setVisibility(View.INVISIBLE);
//                }
//            }
//
//
//        });
    }

    public ScaleType getScaleType() {
        return mScaleType;
    }

    public BaseSliderView setScaleType(ScaleType type) {
        mScaleType = type;
        return this;
    }

    /**
     * the extended class have to implement getView(), which is called by the adapter,
     * every extended class response to render their own view.
     *
     * @return
     */
    public abstract View getView();

    /**
     * set a listener to get a message , if load error.
     *
     * @param l
     */
    public void setOnImageLoadListener(ImageLoadListener l) {
        mLoadListener = l;
    }

    /**
     * when you have some extra information, please put it in this bundle.
     *
     * @return
     */
    public Bundle getBundle() {
        return mBundle;
    }

    @Override
    public void onFailure(@Nullable String message) {

    }


    @Override
    public void onSuccess(@NotNull String dataSource) {
        if (v.findViewById(R.id.loading_bar) != null) {
            v.findViewById(R.id.loading_bar).setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Get the last instance set via setPicasso(), or null if no user provided instance was set
     *
     * @return The current user-provided Picasso instance, or null if none
     */


    public enum ScaleType {
        CenterCrop, CenterInside, Fit, FitCenterCrop
    }

    public interface OnSliderClickListener {
        void onSliderClick(BaseSliderView slider);
    }

    public interface ImageLoadListener {
        void onStart(BaseSliderView target);

        void onEnd(boolean result, BaseSliderView target);
    }
}
