package co.yap.multicurrency.currencyexchange.confirmation;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0011\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003R\"\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\"\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\nR\"\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\b\"\u0004\b\u0010\u0010\nR\"\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\b\"\u0004\b\u0013\u0010\nR\"\u0010\u0014\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\b\"\u0004\b\u0016\u0010\n\u00a8\u0006\u0017"}, d2 = {"Lco/yap/multicurrency/currencyexchange/confirmation/CurrencyExchangeConfirmationState;", "Lco/yap/yapcore/BaseState;", "Lco/yap/multicurrency/currencyexchange/confirmation/ICurrencyExchangeConfirmation$State;", "()V", "selectedCurrency", "Landroidx/lifecycle/MutableLiveData;", "", "getSelectedCurrency", "()Landroidx/lifecycle/MutableLiveData;", "setSelectedCurrency", "(Landroidx/lifecycle/MutableLiveData;)V", "selectedCurrencyAmount", "getSelectedCurrencyAmount", "setSelectedCurrencyAmount", "targetCurrency", "getTargetCurrency", "setTargetCurrency", "targetCurrencyAmount", "getTargetCurrencyAmount", "setTargetCurrencyAmount", "yapRateValue", "getYapRateValue", "setYapRateValue", "multicurrency_debug"})
public final class CurrencyExchangeConfirmationState extends co.yap.yapcore.BaseState implements co.yap.multicurrency.currencyexchange.confirmation.ICurrencyExchangeConfirmation.State {
    @org.jetbrains.annotations.Nullable()
    private androidx.lifecycle.MutableLiveData<java.lang.String> yapRateValue;
    @org.jetbrains.annotations.Nullable()
    private androidx.lifecycle.MutableLiveData<java.lang.String> selectedCurrency;
    @org.jetbrains.annotations.Nullable()
    private androidx.lifecycle.MutableLiveData<java.lang.String> targetCurrency;
    @org.jetbrains.annotations.Nullable()
    private androidx.lifecycle.MutableLiveData<java.lang.String> selectedCurrencyAmount;
    @org.jetbrains.annotations.Nullable()
    private androidx.lifecycle.MutableLiveData<java.lang.String> targetCurrencyAmount;
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.lang.String> getYapRateValue() {
        return null;
    }
    
    @java.lang.Override()
    public void setYapRateValue(@org.jetbrains.annotations.Nullable()
    androidx.lifecycle.MutableLiveData<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.lang.String> getSelectedCurrency() {
        return null;
    }
    
    @java.lang.Override()
    public void setSelectedCurrency(@org.jetbrains.annotations.Nullable()
    androidx.lifecycle.MutableLiveData<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.lang.String> getTargetCurrency() {
        return null;
    }
    
    @java.lang.Override()
    public void setTargetCurrency(@org.jetbrains.annotations.Nullable()
    androidx.lifecycle.MutableLiveData<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.lang.String> getSelectedCurrencyAmount() {
        return null;
    }
    
    @java.lang.Override()
    public void setSelectedCurrencyAmount(@org.jetbrains.annotations.Nullable()
    androidx.lifecycle.MutableLiveData<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.lang.String> getTargetCurrencyAmount() {
        return null;
    }
    
    @java.lang.Override()
    public void setTargetCurrencyAmount(@org.jetbrains.annotations.Nullable()
    androidx.lifecycle.MutableLiveData<java.lang.String> p0) {
    }
    
    public CurrencyExchangeConfirmationState() {
        super();
    }
}