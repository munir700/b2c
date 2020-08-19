package co.yap.multicurrency.ratesalerts.rates;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0005\u00a2\u0006\u0002\u0010\u0004J\b\u0010\f\u001a\u00020\rH\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0007H\u0016J\b\u0010\u0011\u001a\u00020\u0007H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0002J\u0012\u0010\u0014\u001a\u00020\r2\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0016J\b\u0010\u0017\u001a\u00020\rH\u0016J\b\u0010\u0018\u001a\u00020\rH\u0016J\b\u0010\u0019\u001a\u00020\rH\u0016J\b\u0010\u001a\u001a\u00020\rH\u0016J\b\u0010\u001b\u001a\u00020\rH\u0016J\u0006\u0010\u001c\u001a\u00020\rJ\u0010\u0010\u001d\u001a\u00020\r2\u0006\u0010\u001e\u001a\u00020\u0016H\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\u00028VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u001f"}, d2 = {"Lco/yap/multicurrency/ratesalerts/rates/MCRatesFragment;", "Lco/yap/multicurrency/ratesalerts/base/RatesAndAlertsBaseFragment;", "Lco/yap/multicurrency/ratesalerts/rates/IMCRates$ViewModel;", "Lco/yap/multicurrency/ratesalerts/rates/IMCRates$View;", "()V", "clickObserver", "Landroidx/lifecycle/Observer;", "", "deleteWalletObserver", "viewModel", "getViewModel", "()Lco/yap/multicurrency/ratesalerts/rates/IMCRates$ViewModel;", "confirmDeleteWallet", "", "getBinding", "Lco/yap/multicurrency/databinding/FragmentMcRatesBinding;", "getBindingVariable", "getLayoutId", "getSwipeListener", "Lcom/nikhilpanju/recyclerviewenhanced/RecyclerTouchListener;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onPause", "onResume", "removeObservers", "setObservers", "startAddCurrency", "startCurrencyPickerFragment", "bundle", "multicurrency_debug"})
public final class MCRatesFragment extends co.yap.multicurrency.ratesalerts.base.RatesAndAlertsBaseFragment<co.yap.multicurrency.ratesalerts.rates.IMCRates.ViewModel> implements co.yap.multicurrency.ratesalerts.rates.IMCRates.View {
    private final androidx.lifecycle.Observer<java.lang.Integer> deleteWalletObserver = null;
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
    public co.yap.multicurrency.databinding.FragmentMcRatesBinding getBinding() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.multicurrency.ratesalerts.rates.IMCRates.ViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    public void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    public void setObservers() {
    }
    
    @java.lang.Override()
    public void onResume() {
    }
    
    @java.lang.Override()
    public void onPause() {
    }
    
    private final com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener getSwipeListener() {
        return null;
    }
    
    private final void confirmDeleteWallet() {
    }
    
    public final void startAddCurrency() {
    }
    
    private final void startCurrencyPickerFragment(android.os.Bundle bundle) {
    }
    
    @java.lang.Override()
    public void removeObservers() {
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    public MCRatesFragment() {
        super();
    }
}