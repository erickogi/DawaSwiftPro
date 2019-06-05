package com.tufike.taxi.common.utils;

import android.content.Context;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tufike.taxi.common.R;
import com.tufike.taxi.common.models.Gender;
import com.tufike.taxi.common.models.Media;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import de.hdodenhof.circleimageview.CircleImageView;

public final class DataBinder {

    private DataBinder() {
        //NO-OP
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String address) {
        Context context = imageView.getContext();
        Glide.with(context).load(address).into(imageView);
    }

    @BindingAdapter("media")
    public static void setMedia(ImageView imageView, Media media) {
        Context context = imageView.getContext();
        if (media == null)
            return;
        String address = media.getPathType() == Media.PathType.absolute ? media.getAddress() : context.getText(R.string.server_address) + media.getAddress();
        Glide.with(context).load(address).into(imageView);
    }

    @BindingAdapter("media")
    public static void setMedia(CircleImageView circleImageView, Media media) {
        Context context = circleImageView.getContext();
        if (media == null)
            return;
        String address = media.getPathType() == Media.PathType.absolute ? media.getAddress() : context.getText(R.string.server_address) + media.getAddress();
        Glide.with(context).load(address).into(circleImageView);
    }

    @BindingAdapter("gender")
    public static void setGender(MaterialBetterSpinner spinner, Gender gender) {
        switch (gender) {
            case unknown:
                spinner.setText(spinner.getContext().getString(R.string.gender_unknown));
                break;

            case male:
                spinner.setText(spinner.getContext().getString(R.string.gender_male));
                break;

            case female:
                spinner.setText(spinner.getContext().getString(R.string.gender_female));
                break;
        }
    }
    @InverseBindingAdapter(attribute = "gender")
    public static Gender getGender(MaterialBetterSpinner spinner) {
        spinner.clearFocus();
        String unknown = spinner.getContext().getString(R.string.gender_unknown);
        String male = spinner.getContext().getString(R.string.gender_male);
        String female = spinner.getContext().getString(R.string.gender_female);
        if(spinner.getText().toString().equals(female))
            return Gender.female;
        if(spinner.getText().toString().equals(male))
            return Gender.male;
        return Gender.unknown;
    }

    @BindingAdapter(value = "genderAttrChanged")
    public static void bindGenderChanged(MaterialBetterSpinner pAppCompatSpinner, final InverseBindingListener newTextAttrChanged) {
        pAppCompatSpinner.setOnItemClickListener((adapterView, view, i, l) -> newTextAttrChanged.onChange());

    }
}
