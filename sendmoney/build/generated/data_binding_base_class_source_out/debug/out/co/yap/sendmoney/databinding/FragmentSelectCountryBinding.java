// Generated by data binding compiler. Do not edit!
package co.yap.sendmoney.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import co.yap.sendMoney.addbeneficiary.viewmodels.SelectCountryViewModel;
import co.yap.sendmoney.R;
import co.yap.widgets.CoreButton;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FragmentSelectCountryBinding extends ViewDataBinding {
  @NonNull
  public final Spinner countriesSpinner;

  @NonNull
  public final CoreButton nextButton;

  @NonNull
  public final RelativeLayout spinnerContainer;

  @NonNull
  public final TextView tvHeadingDetail;

  @NonNull
  public final TextView tvSelectCountryHeading;

  @Bindable
  protected SelectCountryViewModel mViewModel;

  protected FragmentSelectCountryBinding(Object _bindingComponent, View _root, int _localFieldCount,
      Spinner countriesSpinner, CoreButton nextButton, RelativeLayout spinnerContainer,
      TextView tvHeadingDetail, TextView tvSelectCountryHeading) {
    super(_bindingComponent, _root, _localFieldCount);
    this.countriesSpinner = countriesSpinner;
    this.nextButton = nextButton;
    this.spinnerContainer = spinnerContainer;
    this.tvHeadingDetail = tvHeadingDetail;
    this.tvSelectCountryHeading = tvSelectCountryHeading;
  }

  public abstract void setViewModel(@Nullable SelectCountryViewModel viewModel);

  @Nullable
  public SelectCountryViewModel getViewModel() {
    return mViewModel;
  }

  @NonNull
  public static FragmentSelectCountryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_select_country, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentSelectCountryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentSelectCountryBinding>inflateInternal(inflater, R.layout.fragment_select_country, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentSelectCountryBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_select_country, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentSelectCountryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentSelectCountryBinding>inflateInternal(inflater, R.layout.fragment_select_country, null, false, component);
  }

  public static FragmentSelectCountryBinding bind(@NonNull View view) {
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
  public static FragmentSelectCountryBinding bind(@NonNull View view, @Nullable Object component) {
    return (FragmentSelectCountryBinding)bind(component, view, R.layout.fragment_select_country);
  }
}
