// Generated by data binding compiler. Do not edit!
package co.yap.sendmoney.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import co.yap.sendmoney.R;
import co.yap.sendmoney.addbeneficiary.viewmodels.BankDetailsViewModel;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class LayoutBankDetailToolbarBinding extends ViewDataBinding {
  @NonNull
  public final ImageView tbBtnBack;

  @NonNull
  public final ImageView tbBtnSettings;

  @NonNull
  public final TextView tvTitle;

  @Bindable
  protected BankDetailsViewModel mViewModel;

  protected LayoutBankDetailToolbarBinding(Object _bindingComponent, View _root,
      int _localFieldCount, ImageView tbBtnBack, ImageView tbBtnSettings, TextView tvTitle) {
    super(_bindingComponent, _root, _localFieldCount);
    this.tbBtnBack = tbBtnBack;
    this.tbBtnSettings = tbBtnSettings;
    this.tvTitle = tvTitle;
  }

  public abstract void setViewModel(@Nullable BankDetailsViewModel viewModel);

  @Nullable
  public BankDetailsViewModel getViewModel() {
    return mViewModel;
  }

  @NonNull
  public static LayoutBankDetailToolbarBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_bank_detail_toolbar, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static LayoutBankDetailToolbarBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<LayoutBankDetailToolbarBinding>inflateInternal(inflater, R.layout.layout_bank_detail_toolbar, root, attachToRoot, component);
  }

  @NonNull
  public static LayoutBankDetailToolbarBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.layout_bank_detail_toolbar, null, false, component)
   */
  @NonNull
  @Deprecated
  public static LayoutBankDetailToolbarBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<LayoutBankDetailToolbarBinding>inflateInternal(inflater, R.layout.layout_bank_detail_toolbar, null, false, component);
  }

  public static LayoutBankDetailToolbarBinding bind(@NonNull View view) {
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
  public static LayoutBankDetailToolbarBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (LayoutBankDetailToolbarBinding)bind(component, view, R.layout.layout_bank_detail_toolbar);
  }
}
