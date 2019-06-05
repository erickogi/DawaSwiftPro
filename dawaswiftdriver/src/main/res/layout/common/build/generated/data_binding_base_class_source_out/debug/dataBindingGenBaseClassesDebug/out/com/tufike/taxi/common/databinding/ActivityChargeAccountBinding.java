package com.tufike.taxi.common.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.google.android.material.textfield.TextInputLayout;
import com.savvyapps.togglebuttonlayout.ToggleButtonLayout;

public abstract class ActivityChargeAccountBinding extends ViewDataBinding {
  @NonNull
  public final Button chargeAddFirst;

  @NonNull
  public final Button chargeAddSecond;

  @NonNull
  public final Button chargeAddThird;

  @NonNull
  public final AppCompatButton checkoutButton;

  @NonNull
  public final EditText editText;

  @NonNull
  public final LinearLayout layoutCharges;

  @NonNull
  public final ToggleButtonLayout paymentToggleLayout;

  @NonNull
  public final TextInputLayout priceTextLayout;

  @NonNull
  public final TextView textCurrentBalance;

  @NonNull
  public final TextView titleMethod;

  protected ActivityChargeAccountBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, Button chargeAddFirst, Button chargeAddSecond, Button chargeAddThird,
      AppCompatButton checkoutButton, EditText editText, LinearLayout layoutCharges,
      ToggleButtonLayout paymentToggleLayout, TextInputLayout priceTextLayout,
      TextView textCurrentBalance, TextView titleMethod) {
    super(_bindingComponent, _root, _localFieldCount);
    this.chargeAddFirst = chargeAddFirst;
    this.chargeAddSecond = chargeAddSecond;
    this.chargeAddThird = chargeAddThird;
    this.checkoutButton = checkoutButton;
    this.editText = editText;
    this.layoutCharges = layoutCharges;
    this.paymentToggleLayout = paymentToggleLayout;
    this.priceTextLayout = priceTextLayout;
    this.textCurrentBalance = textCurrentBalance;
    this.titleMethod = titleMethod;
  }

  @NonNull
  public static ActivityChargeAccountBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityChargeAccountBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityChargeAccountBinding>inflate(inflater, com.tufike.taxi.common.R.layout.activity_charge_account, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityChargeAccountBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityChargeAccountBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityChargeAccountBinding>inflate(inflater, com.tufike.taxi.common.R.layout.activity_charge_account, null, false, component);
  }

  public static ActivityChargeAccountBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ActivityChargeAccountBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ActivityChargeAccountBinding)bind(component, view, com.tufike.taxi.common.R.layout.activity_charge_account);
  }
}
