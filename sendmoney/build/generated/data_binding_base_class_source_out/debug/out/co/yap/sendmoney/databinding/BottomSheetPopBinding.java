// Generated by data binding compiler. Do not edit!
package co.yap.sendmoney.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import co.yap.sendmoney.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class BottomSheetPopBinding extends ViewDataBinding {
  @NonNull
  public final RecyclerView recyclerView;

  @NonNull
  public final View sepretorTop;

  @NonNull
  public final TextView tvlabel;

  protected BottomSheetPopBinding(Object _bindingComponent, View _root, int _localFieldCount,
      RecyclerView recyclerView, View sepretorTop, TextView tvlabel) {
    super(_bindingComponent, _root, _localFieldCount);
    this.recyclerView = recyclerView;
    this.sepretorTop = sepretorTop;
    this.tvlabel = tvlabel;
  }

  @NonNull
  public static BottomSheetPopBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_pop, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static BottomSheetPopBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<BottomSheetPopBinding>inflateInternal(inflater, R.layout.bottom_sheet_pop, root, attachToRoot, component);
  }

  @NonNull
  public static BottomSheetPopBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_pop, null, false, component)
   */
  @NonNull
  @Deprecated
  public static BottomSheetPopBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<BottomSheetPopBinding>inflateInternal(inflater, R.layout.bottom_sheet_pop, null, false, component);
  }

  public static BottomSheetPopBinding bind(@NonNull View view) {
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
  public static BottomSheetPopBinding bind(@NonNull View view, @Nullable Object component) {
    return (BottomSheetPopBinding)bind(component, view, R.layout.bottom_sheet_pop);
  }
}
