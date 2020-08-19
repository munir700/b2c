package co.yap.multicurrency.currencyexchange.exchangesuccess;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003R\"\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n\u00a8\u0006\u000b"}, d2 = {"Lco/yap/multicurrency/currencyexchange/exchangesuccess/CurrencyExchangeSuccessState;", "Lco/yap/yapcore/BaseState;", "Lco/yap/multicurrency/currencyexchange/exchangesuccess/ICurrencyExchangeSuccess$State;", "()V", "selectedAndConvertedAmount", "Landroidx/lifecycle/MutableLiveData;", "", "getSelectedAndConvertedAmount", "()Landroidx/lifecycle/MutableLiveData;", "setSelectedAndConvertedAmount", "(Landroidx/lifecycle/MutableLiveData;)V", "multicurrency_debug"})
public final class CurrencyExchangeSuccessState extends co.yap.yapcore.BaseState implements co.yap.multicurrency.currencyexchange.exchangesuccess.ICurrencyExchangeSuccess.State {
    @org.jetbrains.annotations.Nullable()
    private androidx.lifecycle.MutableLiveData<java.lang.String> selectedAndConvertedAmount;
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.lang.String> getSelectedAndConvertedAmount() {
        return null;
    }
    
    @java.lang.Override()
    public void setSelectedAndConvertedAmount(@org.jetbrains.annotations.Nullable()
    androidx.lifecycle.MutableLiveData<java.lang.String> p0) {
    }
    
    public CurrencyExchangeSuccessState() {
        super();
    }
}