package co.yap.multicurrency.currencyexchange.currencyexchangeinput;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u00a8\u0006\u0005"}, d2 = {"Lco/yap/multicurrency/currencyexchange/currencyexchangeinput/ICurrencyExchangeInput;", "", "State", "View", "ViewModel", "multicurrency_debug"})
public abstract interface ICurrencyExchangeInput {
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\b\u0010\u0003\u001a\u00020\u0004H&\u00a8\u0006\u0005"}, d2 = {"Lco/yap/multicurrency/currencyexchange/currencyexchangeinput/ICurrencyExchangeInput$View;", "Lco/yap/yapcore/IBase$View;", "Lco/yap/multicurrency/currencyexchange/currencyexchangeinput/ICurrencyExchangeInput$ViewModel;", "setObservers", "", "multicurrency_debug"})
    public static abstract interface View extends co.yap.yapcore.IBase.View<co.yap.multicurrency.currencyexchange.currencyexchangeinput.ICurrencyExchangeInput.ViewModel> {
        
        public abstract void setObservers();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH&R\u0018\u0010\u0003\u001a\u00020\u0004X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\r"}, d2 = {"Lco/yap/multicurrency/currencyexchange/currencyexchangeinput/ICurrencyExchangeInput$ViewModel;", "Lco/yap/yapcore/IBase$ViewModel;", "Lco/yap/multicurrency/currencyexchange/currencyexchangeinput/ICurrencyExchangeInput$State;", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "setClickEvent", "(Lco/yap/yapcore/SingleClickEvent;)V", "handlePressOnView", "", "id", "", "multicurrency_debug"})
    public static abstract interface ViewModel extends co.yap.yapcore.IBase.ViewModel<co.yap.multicurrency.currencyexchange.currencyexchangeinput.ICurrencyExchangeInput.State> {
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.yapcore.SingleClickEvent getClickEvent();
        
        public abstract void setClickEvent(@org.jetbrains.annotations.NotNull()
        co.yap.yapcore.SingleClickEvent p0);
        
        public abstract void handlePressOnView(int id);
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001R\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR \u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR \u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR \u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR \u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR \u0010\u0015\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\bR\u001e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u001a\u0010\u0006\"\u0004\b\u001b\u0010\bR \u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u001d\u0010\u0006\"\u0004\b\u001e\u0010\b\u00a8\u0006\u001f"}, d2 = {"Lco/yap/multicurrency/currencyexchange/currencyexchangeinput/ICurrencyExchangeInput$State;", "Lco/yap/yapcore/IBase$State;", "amount", "Landroidx/lifecycle/MutableLiveData;", "", "getAmount", "()Landroidx/lifecycle/MutableLiveData;", "setAmount", "(Landroidx/lifecycle/MutableLiveData;)V", "selectedCurrency", "getSelectedCurrency", "setSelectedCurrency", "selectedCurrencyAvailableBalance", "getSelectedCurrencyAvailableBalance", "setSelectedCurrencyAvailableBalance", "targetAmount", "getTargetAmount", "setTargetAmount", "targetCurrency", "getTargetCurrency", "setTargetCurrency", "targetCurrencyAvailableBalance", "getTargetCurrencyAvailableBalance", "setTargetCurrencyAvailableBalance", "valid", "", "getValid", "setValid", "yapRateValue", "getYapRateValue", "setYapRateValue", "multicurrency_debug"})
    public static abstract interface State extends co.yap.yapcore.IBase.State {
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.String> getAmount();
        
        public abstract void setAmount(@org.jetbrains.annotations.NotNull()
        androidx.lifecycle.MutableLiveData<java.lang.String> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.Boolean> getValid();
        
        public abstract void setValid(@org.jetbrains.annotations.NotNull()
        androidx.lifecycle.MutableLiveData<java.lang.Boolean> p0);
        
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
        public abstract androidx.lifecycle.MutableLiveData<java.lang.String> getTargetAmount();
        
        public abstract void setTargetAmount(@org.jetbrains.annotations.Nullable()
        androidx.lifecycle.MutableLiveData<java.lang.String> p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.String> getSelectedCurrencyAvailableBalance();
        
        public abstract void setSelectedCurrencyAvailableBalance(@org.jetbrains.annotations.Nullable()
        androidx.lifecycle.MutableLiveData<java.lang.String> p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.String> getTargetCurrencyAvailableBalance();
        
        public abstract void setTargetCurrencyAvailableBalance(@org.jetbrains.annotations.Nullable()
        androidx.lifecycle.MutableLiveData<java.lang.String> p0);
    }
}