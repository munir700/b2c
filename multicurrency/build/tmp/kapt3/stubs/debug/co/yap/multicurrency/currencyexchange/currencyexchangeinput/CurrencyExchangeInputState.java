package co.yap.multicurrency.currencyexchange.currencyexchangeinput;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003R \u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\"\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\nR\"\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\b\"\u0004\b\u0010\u0010\nR\"\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\b\"\u0004\b\u0013\u0010\nR\"\u0010\u0014\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\b\"\u0004\b\u0016\u0010\nR\"\u0010\u0017\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\b\"\u0004\b\u0019\u0010\nR \u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\b\"\u0004\b\u001d\u0010\nR\"\u0010\u001e\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\b\"\u0004\b \u0010\n\u00a8\u0006!"}, d2 = {"Lco/yap/multicurrency/currencyexchange/currencyexchangeinput/CurrencyExchangeInputState;", "Lco/yap/yapcore/BaseState;", "Lco/yap/multicurrency/currencyexchange/currencyexchangeinput/ICurrencyExchangeInput$State;", "()V", "amount", "Landroidx/lifecycle/MutableLiveData;", "", "getAmount", "()Landroidx/lifecycle/MutableLiveData;", "setAmount", "(Landroidx/lifecycle/MutableLiveData;)V", "selectedCurrency", "getSelectedCurrency", "setSelectedCurrency", "selectedCurrencyAvailableBalance", "getSelectedCurrencyAvailableBalance", "setSelectedCurrencyAvailableBalance", "targetAmount", "getTargetAmount", "setTargetAmount", "targetCurrency", "getTargetCurrency", "setTargetCurrency", "targetCurrencyAvailableBalance", "getTargetCurrencyAvailableBalance", "setTargetCurrencyAvailableBalance", "valid", "", "getValid", "setValid", "yapRateValue", "getYapRateValue", "setYapRateValue", "multicurrency_debug"})
public final class CurrencyExchangeInputState extends co.yap.yapcore.BaseState implements co.yap.multicurrency.currencyexchange.currencyexchangeinput.ICurrencyExchangeInput.State {
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.String> amount;
    @org.jetbrains.annotations.Nullable()
    private androidx.lifecycle.MutableLiveData<java.lang.String> yapRateValue;
    @org.jetbrains.annotations.Nullable()
    private androidx.lifecycle.MutableLiveData<java.lang.String> targetAmount;
    @org.jetbrains.annotations.Nullable()
    private androidx.lifecycle.MutableLiveData<java.lang.String> selectedCurrency;
    @org.jetbrains.annotations.Nullable()
    private androidx.lifecycle.MutableLiveData<java.lang.String> targetCurrency;
    @org.jetbrains.annotations.Nullable()
    private androidx.lifecycle.MutableLiveData<java.lang.String> selectedCurrencyAvailableBalance;
    @org.jetbrains.annotations.Nullable()
    private androidx.lifecycle.MutableLiveData<java.lang.String> targetCurrencyAvailableBalance;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Boolean> valid;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.lang.String> getAmount() {
        return null;
    }
    
    @java.lang.Override()
    public void setAmount(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.String> p0) {
    }
    
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
    public androidx.lifecycle.MutableLiveData<java.lang.String> getTargetAmount() {
        return null;
    }
    
    @java.lang.Override()
    public void setTargetAmount(@org.jetbrains.annotations.Nullable()
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
    public androidx.lifecycle.MutableLiveData<java.lang.String> getSelectedCurrencyAvailableBalance() {
        return null;
    }
    
    @java.lang.Override()
    public void setSelectedCurrencyAvailableBalance(@org.jetbrains.annotations.Nullable()
    androidx.lifecycle.MutableLiveData<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.lang.String> getTargetCurrencyAvailableBalance() {
        return null;
    }
    
    @java.lang.Override()
    public void setTargetCurrencyAvailableBalance(@org.jetbrains.annotations.Nullable()
    androidx.lifecycle.MutableLiveData<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.lang.Boolean> getValid() {
        return null;
    }
    
    @java.lang.Override()
    public void setValid(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Boolean> p0) {
    }
    
    public CurrencyExchangeInputState() {
        super();
    }
}