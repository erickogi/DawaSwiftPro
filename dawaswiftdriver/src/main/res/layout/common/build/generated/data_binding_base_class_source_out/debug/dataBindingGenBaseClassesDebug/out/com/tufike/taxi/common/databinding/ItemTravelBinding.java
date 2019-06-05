package com.tufike.taxi.common.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.tufike.taxi.common.models.Travel;

public abstract class ItemTravelBinding extends ViewDataBinding {
  @NonNull
  public final Button buttonComplaint;

  @NonNull
  public final Button buttonHideTravel;

  @NonNull
  public final CardView cellContentView;

  @NonNull
  public final ConstraintLayout constraintId;

  @NonNull
  public final ImageView dividerAddress;

  @NonNull
  public final ImageView dividerDetails;

  @NonNull
  public final ImageView imageMap;

  @NonNull
  public final TextView labelDetailsCost;

  @NonNull
  public final TextView labelDetailsDistance;

  @NonNull
  public final TextView labelFinishTime;

  @NonNull
  public final TextView labelFrom;

  @NonNull
  public final TextView labelRequestTime;

  @NonNull
  public final TextView labelTo;

  @NonNull
  public final TextView textDetailsCost;

  @NonNull
  public final TextView textDetailsDistance;

  @NonNull
  public final TextView textFinishDate;

  @NonNull
  public final TextView textFinishTime;

  @NonNull
  public final TextView textFrom;

  @NonNull
  public final TextView textRequestDate;

  @NonNull
  public final TextView textRequestTime;

  @NonNull
  public final TextView textStatus;

  @NonNull
  public final TextView textTo;

  @Bindable
  protected Travel mItem;

  protected ItemTravelBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, Button buttonComplaint, Button buttonHideTravel,
      CardView cellContentView, ConstraintLayout constraintId, ImageView dividerAddress,
      ImageView dividerDetails, ImageView imageMap, TextView labelDetailsCost,
      TextView labelDetailsDistance, TextView labelFinishTime, TextView labelFrom,
      TextView labelRequestTime, TextView labelTo, TextView textDetailsCost,
      TextView textDetailsDistance, TextView textFinishDate, TextView textFinishTime,
      TextView textFrom, TextView textRequestDate, TextView textRequestTime, TextView textStatus,
      TextView textTo) {
    super(_bindingComponent, _root, _localFieldCount);
    this.buttonComplaint = buttonComplaint;
    this.buttonHideTravel = buttonHideTravel;
    this.cellContentView = cellContentView;
    this.constraintId = constraintId;
    this.dividerAddress = dividerAddress;
    this.dividerDetails = dividerDetails;
    this.imageMap = imageMap;
    this.labelDetailsCost = labelDetailsCost;
    this.labelDetailsDistance = labelDetailsDistance;
    this.labelFinishTime = labelFinishTime;
    this.labelFrom = labelFrom;
    this.labelRequestTime = labelRequestTime;
    this.labelTo = labelTo;
    this.textDetailsCost = textDetailsCost;
    this.textDetailsDistance = textDetailsDistance;
    this.textFinishDate = textFinishDate;
    this.textFinishTime = textFinishTime;
    this.textFrom = textFrom;
    this.textRequestDate = textRequestDate;
    this.textRequestTime = textRequestTime;
    this.textStatus = textStatus;
    this.textTo = textTo;
  }

  public abstract void setItem(@Nullable Travel item);

  @Nullable
  public Travel getItem() {
    return mItem;
  }

  @NonNull
  public static ItemTravelBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ItemTravelBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ItemTravelBinding>inflate(inflater, com.tufike.taxi.common.R.layout.item_travel, root, attachToRoot, component);
  }

  @NonNull
  public static ItemTravelBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ItemTravelBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ItemTravelBinding>inflate(inflater, com.tufike.taxi.common.R.layout.item_travel, null, false, component);
  }

  public static ItemTravelBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ItemTravelBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ItemTravelBinding)bind(component, view, com.tufike.taxi.common.R.layout.item_travel);
  }
}
