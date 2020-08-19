package co.yap.multicurrency.currency;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u00a8\u0006\u0005"}, d2 = {"Lco/yap/multicurrency/currency/ICurrencyPicker;", "", "State", "View", "ViewModel", "multicurrency_debug"})
public abstract interface ICurrencyPicker {
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0004H&\u00a8\u0006\u0006"}, d2 = {"Lco/yap/multicurrency/currency/ICurrencyPicker$View;", "Lco/yap/yapcore/IBase$View;", "Lco/yap/multicurrency/currency/ICurrencyPicker$ViewModel;", "removeObservers", "", "setObservers", "multicurrency_debug"})
    public static abstract interface View extends co.yap.yapcore.IBase.View<co.yap.multicurrency.currency.ICurrencyPicker.ViewModel> {
        
        public abstract void setObservers();
        
        public abstract void removeObservers();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH&J\b\u0010\u001d\u001a\u00020\u001aH&J\u0010\u0010\u001e\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH&R\u001e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0012\u0010\u000e\u001a\u00020\u000fX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u001e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018\u00a8\u0006\u001f"}, d2 = {"Lco/yap/multicurrency/currency/ICurrencyPicker$ViewModel;", "Lco/yap/yapcore/IBase$ViewModel;", "Lco/yap/multicurrency/currency/ICurrencyPicker$State;", "availableCurrenciesList", "", "Lco/yap/multicurrency/wallets/MultiCurrencyWallet;", "getAvailableCurrenciesList", "()Ljava/util/List;", "setAvailableCurrenciesList", "(Ljava/util/List;)V", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "currencyAdapter", "Lco/yap/multicurrency/currency/adapters/CurrencyAdapter;", "getCurrencyAdapter", "()Lco/yap/multicurrency/currency/adapters/CurrencyAdapter;", "searchQuery", "Landroidx/lifecycle/MutableLiveData;", "", "getSearchQuery", "()Landroidx/lifecycle/MutableLiveData;", "setSearchQuery", "(Landroidx/lifecycle/MutableLiveData;)V", "deleteWalletRequest", "", "id", "", "editWallet", "handlePressOnView", "multicurrency_debug"})
    public static abstract interface ViewModel extends co.yap.yapcore.IBase.ViewModel<co.yap.multicurrency.currency.ICurrencyPicker.State> {
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.yapcore.SingleClickEvent getClickEvent();
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.multicurrency.currency.adapters.CurrencyAdapter getCurrencyAdapter();
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.util.List<co.yap.multicurrency.wallets.MultiCurrencyWallet> getAvailableCurrenciesList();
        
        public abstract void setAvailableCurrenciesList(@org.jetbrains.annotations.NotNull()
        java.util.List<co.yap.multicurrency.wallets.MultiCurrencyWallet> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.String> getSearchQuery();
        
        public abstract void setSearchQuery(@org.jetbrains.annotations.NotNull()
        androidx.lifecycle.MutableLiveData<java.lang.String> p0);
        
        public abstract void handlePressOnView(int id);
        
        public abstract void deleteWalletRequest(int id);
        
        public abstract void editWallet();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001R\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, d2 = {"Lco/yap/multicurrency/currency/ICurrencyPicker$State;", "Lco/yap/yapcore/IBase$State;", "hintText", "Landroidx/databinding/ObservableField;", "", "getHintText", "()Landroidx/databinding/ObservableField;", "setHintText", "(Landroidx/databinding/ObservableField;)V", "multicurrency_debug"})
    public static abstract interface State extends co.yap.yapcore.IBase.State {
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.lang.String> getHintText();
        
        public abstract void setHintText(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.lang.String> p0);
    }
}