// Generated by data binding compiler. Do not edit!
package co.yap.multicurrency.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import co.yap.multicurrency.R;
import co.yap.multicurrency.wallet2.WalletViewModel;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FragmentWalletV2Binding extends ViewDataBinding {
  @NonNull
  public final View divider;

  @NonNull
  public final LinearLayoutCompat foregroundContainer;

  @NonNull
  public final AppCompatImageView ivArrow;

  @NonNull
  public final AppCompatImageView ivFlag;

  @NonNull
  public final RecyclerView recyclerView;

  @NonNull
  public final AppCompatTextView tvAmount;

  @NonNull
  public final AppCompatTextView tvCurrencyUnit;

  @NonNull
  public final AppCompatTextView tvEdit;

  @NonNull
  public final AppCompatTextView tvHeading;

  @Bindable
  protected WalletViewModel mViewModel;

  protected FragmentWalletV2Binding(Object _bindingComponent, View _root, int _localFieldCount,
      View divider, LinearLayoutCompat foregroundContainer, AppCompatImageView ivArrow,
      AppCompatImageView ivFlag, RecyclerView recyclerView, AppCompatTextView tvAmount,
      AppCompatTextView tvCurrencyUnit, AppCompatTextView tvEdit, AppCompatTextView tvHeading) {
    super(_bindingComponent, _root, _localFieldCount);
    this.divider = divider;
    this.foregroundContainer = foregroundContainer;
    this.ivArrow = ivArrow;
    this.ivFlag = ivFlag;
    this.recyclerView = recyclerView;
    this.tvAmount = tvAmount;
    this.tvCurrencyUnit = tvCurrencyUnit;
    this.tvEdit = tvEdit;
    this.tvHeading = tvHeading;
  }

  public abstract void setViewModel(@Nullable WalletViewModel viewModel);

  @Nullable
  public WalletViewModel getViewModel() {
    return mViewModel;
  }

  @NonNull
  public static FragmentWalletV2Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_wallet_v2, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentWalletV2Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentWalletV2Binding>inflateInternal(inflater, R.layout.fragment_wallet_v2, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentWalletV2Binding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_wallet_v2, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentWalletV2Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentWalletV2Binding>inflateInternal(inflater, R.layout.fragment_wallet_v2, null, false, component);
  }

  public static FragmentWalletV2Binding bind(@NonNull View view) {
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
  public static FragmentWalletV2Binding bind(@NonNull View view, @Nullable Object component) {
    return (FragmentWalletV2Binding)bind(component, view, R.layout.fragment_wallet_v2);
  }
}
