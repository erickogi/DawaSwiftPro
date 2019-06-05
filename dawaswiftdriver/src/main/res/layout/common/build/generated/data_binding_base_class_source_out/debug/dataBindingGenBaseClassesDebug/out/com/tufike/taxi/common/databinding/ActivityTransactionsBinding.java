package com.tufike.taxi.common.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.tylersuehr.esr.EmptyStateRecyclerView;

public abstract class ActivityTransactionsBinding extends ViewDataBinding {
  @NonNull
  public final EmptyStateRecyclerView recyclerView;

  protected ActivityTransactionsBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, EmptyStateRecyclerView recyclerView) {
    super(_bindingComponent, _root, _localFieldCount);
    this.recyclerView = recyclerView;
  }

  @NonNull
  public static ActivityTransactionsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityTransactionsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityTransactionsBinding>inflate(inflater, com.tufike.taxi.common.R.layout.activity_transactions, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityTransactionsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityTransactionsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityTransactionsBinding>inflate(inflater, com.tufike.taxi.common.R.layout.activity_transactions, null, false, component);
  }

  public static ActivityTransactionsBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ActivityTransactionsBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ActivityTransactionsBinding)bind(component, view, com.tufike.taxi.common.R.layout.activity_transactions);
  }
}
