package co.yap.multicurrency.currency;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#H\u0016J\b\u0010$\u001a\u00020!H\u0016J\u0010\u0010%\u001a\u00020!2\u0006\u0010\"\u001a\u00020#H\u0016J\b\u0010&\u001a\u00020!H\u0016J\u0006\u0010\'\u001a\u00020!R \u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000fX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u0013X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R \u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u0014\u0010\u001d\u001a\u00020\u0002X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001f\u00a8\u0006("}, d2 = {"Lco/yap/multicurrency/currency/CurrencyPickerViewModel;", "Lco/yap/multicurrency/base/MCLandingBaseViewModel;", "Lco/yap/multicurrency/currency/ICurrencyPicker$State;", "Lco/yap/multicurrency/currency/ICurrencyPicker$ViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "availableCurrenciesList", "", "Lco/yap/multicurrency/wallets/MultiCurrencyWallet;", "getAvailableCurrenciesList", "()Ljava/util/List;", "setAvailableCurrenciesList", "(Ljava/util/List;)V", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "currencyAdapter", "Lco/yap/multicurrency/currency/adapters/CurrencyAdapter;", "getCurrencyAdapter", "()Lco/yap/multicurrency/currency/adapters/CurrencyAdapter;", "searchQuery", "Landroidx/lifecycle/MutableLiveData;", "", "getSearchQuery", "()Landroidx/lifecycle/MutableLiveData;", "setSearchQuery", "(Landroidx/lifecycle/MutableLiveData;)V", "state", "getState", "()Lco/yap/multicurrency/currency/ICurrencyPicker$State;", "deleteWalletRequest", "", "id", "", "editWallet", "handlePressOnView", "onCreate", "setUpDummyData", "multicurrency_debug"})
public final class CurrencyPickerViewModel extends co.yap.multicurrency.base.MCLandingBaseViewModel<co.yap.multicurrency.currency.ICurrencyPicker.State> implements co.yap.multicurrency.currency.ICurrencyPicker.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final co.yap.multicurrency.currency.ICurrencyPicker.State state = null;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.yapcore.SingleClickEvent clickEvent = null;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.multicurrency.currency.adapters.CurrencyAdapter currencyAdapter = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<co.yap.multicurrency.wallets.MultiCurrencyWallet> availableCurrenciesList;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.String> searchQuery;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.multicurrency.currency.ICurrencyPicker.State getState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.yapcore.SingleClickEvent getClickEvent() {
        return null;
    }
    
    @java.lang.Override()
    public void handlePressOnView(int id) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.multicurrency.currency.adapters.CurrencyAdapter getCurrencyAdapter() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.util.List<co.yap.multicurrency.wallets.MultiCurrencyWallet> getAvailableCurrenciesList() {
        return null;
    }
    
    @java.lang.Override()
    public void setAvailableCurrenciesList(@org.jetbrains.annotations.NotNull()
    java.util.List<co.yap.multicurrency.wallets.MultiCurrencyWallet> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.lang.String> getSearchQuery() {
        return null;
    }
    
    @java.lang.Override()
    public void setSearchQuery(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.String> p0) {
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    public final void setUpDummyData() {
    }
    
    @java.lang.Override()
    public void deleteWalletRequest(int id) {
    }
    
    @java.lang.Override()
    public void editWallet() {
    }
    
    public CurrencyPickerViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
}