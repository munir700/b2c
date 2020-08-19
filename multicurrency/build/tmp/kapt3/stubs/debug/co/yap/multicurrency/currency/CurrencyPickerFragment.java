package co.yap.multicurrency.currency;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0005\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u000e\u001a\u00020\u0007H\u0016J\b\u0010\u000f\u001a\u00020\u0007H\u0016J\u0012\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0011H\u0016J\u001a\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016J\b\u0010\u0018\u001a\u00020\u0011H\u0016J\b\u0010\u0019\u001a\u00020\u0011H\u0002J\b\u0010\u001a\u001a\u00020\u0011H\u0016J\u000e\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u001dJ\b\u0010\u001e\u001a\u00020\u0011H\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\r\u00a8\u0006\u001f"}, d2 = {"Lco/yap/multicurrency/currency/CurrencyPickerFragment;", "Lco/yap/yapcore/BaseBindingFragment;", "Lco/yap/multicurrency/currency/ICurrencyPicker$ViewModel;", "Lco/yap/multicurrency/currency/ICurrencyPicker$View;", "()V", "clickObserver", "Landroidx/lifecycle/Observer;", "", "currencySelectedItemClickListener", "Lco/yap/yapcore/interfaces/OnItemClickListener;", "viewModel", "Lco/yap/multicurrency/currency/CurrencyPickerViewModel;", "getViewModel", "()Lco/yap/multicurrency/currency/CurrencyPickerViewModel;", "getBindingVariable", "getLayoutId", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onViewCreated", "view", "Landroid/view/View;", "removeObservers", "setListeners", "setObservers", "setResultData", "multiCurrencyWallet", "Lco/yap/multicurrency/wallets/MultiCurrencyWallet;", "setSearchView", "multicurrency_debug"})
public final class CurrencyPickerFragment extends co.yap.yapcore.BaseBindingFragment<co.yap.multicurrency.currency.ICurrencyPicker.ViewModel> implements co.yap.multicurrency.currency.ICurrencyPicker.View {
    private final co.yap.yapcore.interfaces.OnItemClickListener currencySelectedItemClickListener = null;
    private final androidx.lifecycle.Observer<java.lang.Integer> clickObserver = null;
    private java.util.HashMap _$_findViewCache;
    
    @java.lang.Override()
    public int getBindingVariable() {
        return 0;
    }
    
    @java.lang.Override()
    public int getLayoutId() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.multicurrency.currency.CurrencyPickerViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    public void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setListeners() {
    }
    
    @java.lang.Override()
    public void setObservers() {
    }
    
    @java.lang.Override()
    public void removeObservers() {
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    private final void setSearchView() {
    }
    
    public final void setResultData(@org.jetbrains.annotations.NotNull()
    co.yap.multicurrency.wallets.MultiCurrencyWallet multiCurrencyWallet) {
    }
    
    public CurrencyPickerFragment() {
        super();
    }
}