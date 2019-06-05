package com.tufike.taxi.common.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.ViewPager;

public abstract class ActivityLoginBinding extends ViewDataBinding {
  @NonNull
  public final ViewPager viewpager;

  protected ActivityLoginBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, ViewPager viewpager) {
    super(_bindingComponent, _root, _localFieldCount);
    this.viewpager = viewpager;
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityLoginBinding>inflate(inflater, com.tufike.taxi.common.R.layout.activity_login, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityLoginBinding>inflate(inflater, com.tufike.taxi.common.R.layout.activity_login, null, false, component);
  }

  public static ActivityLoginBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ActivityLoginBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ActivityLoginBinding)bind(component, view, com.tufike.taxi.common.R.layout.activity_login);
  }
}
