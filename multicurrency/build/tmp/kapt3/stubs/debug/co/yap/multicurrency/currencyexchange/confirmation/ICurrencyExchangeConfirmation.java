package co.yap.multicurrency.currencyexchange.confirmation;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u00a8\u0006\u0005"}, d2 = {"Lco/yap/multicurrency/currencyexchange/confirmation/ICurrencyExchangeConfirmation;", "", "State", "View", "ViewModel", "multicurrency_debug"})
public abstract interface ICurrencyExchangeConfirmation {
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0004H&\u00a8\u0006\u0006"}, d2 = {"Lco/yap/multicurrency/currencyexchange/confirmation/ICurrencyExchangeConfirmation$View;", "Lco/yap/yapcore/IBase$View;", "Lco/yap/multicurrency/currencyexchange/confirmation/ICurrencyExchangeConfirmation$ViewModel;", "removeObservers", "", "setObservers", "multicurrency_debug"})
    public static abstract interface View extends co.yap.yapcore.IBase.View<co.yap.multicurrency.currencyexchange.confirmation.ICurrencyExchangeConfirmation.ViewModel> {
        
        public abstract void setObservers();
        
        public abstract void removeObservers();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH&R\u0012\u0010\u0003\u001a\u00020\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u000b"}, d2 = {"Lco/yap/multicurrency/currencyexchange/confirmation/ICurrencyExchangeConfirmation$ViewModel;", "Lco/yap/yapcore/IBase$ViewModel;", "Lco/yap/multicurrency/currencyexchange/confirmation/ICurrencyExchangeConfirmation$State;", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "handlePressOnView", "", "id", "", "multicurrency_debug"})
    public static abstract interface ViewModel extends co.yap.yapcore.IBase.ViewModel<co.yap.multicurrency.currencyexchange.confirmation.ICurrencyExchangeConfirmation.State> {
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.yapcore.SingleClickEvent getClickEvent();
        
        public abstract void handlePressOnView(int id);
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0011\bf\u0018\u00002\u00020\u0001R \u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR \u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR \u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR \u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR \u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\b\u00a8\u0006\u0015"}, d2 = {"Lco/yap/multicurrency/currencyexchange/confirmation/ICurrencyExchangeConfirmation$State;", "Lco/yap/yapcore/IBase$State;", "selectedCurrency", "Landroidx/lifecycle/MutableLiveData;", "", "getSelectedCurrency", "()Landroidx/lifecycle/MutableLiveData;", "setSelectedCurrency", "(Landroidx/lifecycle/MutableLiveData;)V", "selectedCurrencyAmount", "getSelectedCurrencyAmount", "setSelectedCurrencyAmount", "targetCurrency", "getTargetCurrency", "setTargetCurrency", "targetCurrencyAmount", "getTargetCurrencyAmount", "setTargetCurrencyAmount", "yapRateValue", "getYapRateValue", "setYapRateValue", "multicurrency_debug"})
    public static abstract interface State extends co.yap.yapcore.IBase.State {
        
        @org.jetbrains.annotations.Nullable()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.String> getYapRateValue();
        
        public abstract void setYapRateValue(@org.jetbrains.annotations.Nullable()
        androidx.lifecycle.MutableLiveData<java.lang.String> p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.String> getSelectedCurrency();
        
        public abstract void setSelectedCurrency(@org.jetbrains.annotations.Nullable()
        androidx.lifecycle.MutableLiveData<java.lang.String> p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.String> getTargetCurrency();
        
        public abstract void setTargetCurrency(@org.jetbrains.annotations.Nullable()
        androidx.lifecycle.MutableLiveData<java.lang.String> p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.String> getSelectedCurrencyAmount();
        
        public abstract void setSelectedCurrencyAmount(@org.jetbrains.annotations.Nullable()
        androidx.lifecycle.MutableLiveData<java.lang.String> p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.String> getTargetCurrencyAmount();
        
        public abstract void setTargetCurrencyAmount(@org.jetbrains.annotations.Nullable()
        androidx.lifecycle.MutableLiveData<java.lang.String> p0);
    }
}