// Generated by data binding compiler. Do not edit!
package com.digitify.identityscanner.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.digitify.identityscanner.R;
import com.digitify.identityscanner.models.KeyValue;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ItemTextTitleSubtitleBinding extends ViewDataBinding {
  @Bindable
  protected KeyValue mData;

  protected ItemTextTitleSubtitleBinding(Object _bindingComponent, View _root,
      int _localFieldCount) {
    super(_bindingComponent, _root, _localFieldCount);
  }

  public abstract void setData(@Nullable KeyValue data);

  @Nullable
  public KeyValue getData() {
    return mData;
  }

  @NonNull
  public static ItemTextTitleSubtitleBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_text_title_subtitle, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ItemTextTitleSubtitleBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ItemTextTitleSubtitleBinding>inflateInternal(inflater, R.layout.item_text_title_subtitle, root, attachToRoot, component);
  }

  @NonNull
  public static ItemTextTitleSubtitleBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_text_title_subtitle, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ItemTextTitleSubtitleBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ItemTextTitleSubtitleBinding>inflateInternal(inflater, R.layout.item_text_title_subtitle, null, false, component);
  }

  public static ItemTextTitleSubtitleBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static ItemTextTitleSubtitleBinding bind(@NonNull View view, @Nullable Object component) {
    return (ItemTextTitleSubtitleBinding)bind(component, view, R.layout.item_text_title_subtitle);
  }
}
