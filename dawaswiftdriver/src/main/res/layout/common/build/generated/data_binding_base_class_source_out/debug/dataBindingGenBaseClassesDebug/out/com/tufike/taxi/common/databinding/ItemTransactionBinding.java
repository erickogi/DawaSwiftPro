package com.tufike.taxi.common.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.tufike.taxi.common.models.Transaction;

public abstract class ItemTransactionBinding extends ViewDataBinding {
  @NonNull
  public final CardView cardTransaction;

  @NonNull
  public final ConstraintLayout constraintHeader;

  @NonNull
  public final TextView textAmount;

  @NonNull
  public final TextView textTime;

  @NonNull
  public final TextView textTitle;

  @NonNull
  public final TextView titleDay;

  @NonNull
  public final TextView titleMonth;

  @Bindable
  protected Transaction mItem;

  protected ItemTransactionBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, CardView cardTransaction, ConstraintLayout constraintHeader,
      TextView textAmount, TextView textTime, TextView textTitle, TextView titleDay,
      TextView titleMonth) {
    super(_bindingComponent, _root, _localFieldCount);
    this.cardTransaction = cardTransaction;
    this.constraintHeader = constraintHeader;
    this.textAmount = textAmount;
    this.textTime = textTime;
    this.textTitle = textTitle;
    this.titleDay = titleDay;
    this.titleMonth = titleMonth;
  }

  public abstract void setItem(@Nullable Transaction item);

  @Nullable
  public Transaction getItem() {
    return mItem;
  }

  @NonNull
  public static ItemTransactionBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ItemTransactionBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ItemTransactionBinding>inflate(inflater, com.tufike.taxi.common.R.layout.item_transaction, root, attachToRoot, component);
  }

  @NonNull
  public static ItemTransactionBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ItemTransactionBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ItemTransactionBinding>inflate(inflater, com.tufike.taxi.common.R.layout.item_transaction, null, false, component);
  }

  public static ItemTransactionBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ItemTransactionBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ItemTransactionBinding)bind(component, view, com.tufike.taxi.common.R.layout.item_transaction);
  }
}
