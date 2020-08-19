package co.yap.multicurrency.ratesalerts.alerts.addpricealert;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u00a8\u0006\u0005"}, d2 = {"Lco/yap/multicurrency/ratesalerts/alerts/addpricealert/IMCAddPriceAlerts;", "", "State", "View", "ViewModel", "multicurrency_debug"})
public abstract interface IMCAddPriceAlerts {
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\u0006H&\u00a8\u0006\b"}, d2 = {"Lco/yap/multicurrency/ratesalerts/alerts/addpricealert/IMCAddPriceAlerts$View;", "Lco/yap/yapcore/IBase$View;", "Lco/yap/multicurrency/ratesalerts/alerts/addpricealert/IMCAddPriceAlerts$ViewModel;", "getBinding", "Lco/yap/multicurrency/databinding/FragmentMcAddPriceAlertBinding;", "removeObservers", "", "setObservers", "multicurrency_debug"})
    public static abstract interface View extends co.yap.yapcore.IBase.View<co.yap.multicurrency.ratesalerts.alerts.addpricealert.IMCAddPriceAlerts.ViewModel> {
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.multicurrency.databinding.FragmentMcAddPriceAlertBinding getBinding();
        
        public abstract void setObservers();
        
        public abstract void removeObservers();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0016\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0010H&J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0013H&J\b\u0010\u0014\u001a\u00020\u000eH&R\u0012\u0010\u0003\u001a\u00020\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\u0004\u0018\u00010\bX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f\u00a8\u0006\u0015"}, d2 = {"Lco/yap/multicurrency/ratesalerts/alerts/addpricealert/IMCAddPriceAlerts$ViewModel;", "Lco/yap/yapcore/IBase$ViewModel;", "Lco/yap/multicurrency/ratesalerts/alerts/addpricealert/IMCAddPriceAlerts$State;", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "priceAlert", "Lco/yap/networking/customers/responsedtos/mcratesandalerts/MCPriceAlert;", "getPriceAlert", "()Lco/yap/networking/customers/responsedtos/mcratesandalerts/MCPriceAlert;", "setPriceAlert", "(Lco/yap/networking/customers/responsedtos/mcratesandalerts/MCPriceAlert;)V", "addPriceAlert", "", "success", "Lkotlin/Function0;", "handlePressOnView", "id", "", "validate", "multicurrency_debug"})
    public static abstract interface ViewModel extends co.yap.yapcore.IBase.ViewModel<co.yap.multicurrency.ratesalerts.alerts.addpricealert.IMCAddPriceAlerts.State> {
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.yapcore.SingleClickEvent getClickEvent();
        
        @org.jetbrains.annotations.Nullable()
        public abstract co.yap.networking.customers.responsedtos.mcratesandalerts.MCPriceAlert getPriceAlert();
        
        public abstract void setPriceAlert(@org.jetbrains.annotations.Nullable()
        co.yap.networking.customers.responsedtos.mcratesandalerts.MCPriceAlert p0);
        
        public abstract void handlePressOnView(int id);
        
        public abstract void validate();
        
        public abstract void addPriceAlert(@org.jetbrains.annotations.NotNull()
        kotlin.jvm.functions.Function0<kotlin.Unit> success);
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\b\bf\u0018\u00002\u00020\u0001R\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u0018\u0010\u0015\u001a\u00020\u0016X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001e\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u001c\u0010\u0006\"\u0004\b\u001d\u0010\b\u00a8\u0006\u001e"}, d2 = {"Lco/yap/multicurrency/ratesalerts/alerts/addpricealert/IMCAddPriceAlerts$State;", "Lco/yap/yapcore/IBase$State;", "etAmount", "Landroidx/databinding/ObservableField;", "", "getEtAmount", "()Landroidx/databinding/ObservableField;", "setEtAmount", "(Landroidx/databinding/ObservableField;)V", "exchangeRate", "getExchangeRate", "setExchangeRate", "srcCountry2DigitCode", "getSrcCountry2DigitCode", "setSrcCountry2DigitCode", "srcCurrencyCode", "getSrcCurrencyCode", "setSrcCurrencyCode", "srcCurrencyName", "getSrcCurrencyName", "setSrcCurrencyName", "valid", "Landroidx/databinding/ObservableBoolean;", "getValid", "()Landroidx/databinding/ObservableBoolean;", "setValid", "(Landroidx/databinding/ObservableBoolean;)V", "yapRateString", "getYapRateString", "setYapRateString", "multicurrency_debug"})
    public static abstract interface State extends co.yap.yapcore.IBase.State {
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.lang.String> getEtAmount();
        
        public abstract void setEtAmount(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.lang.String> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.lang.String> getSrcCurrencyCode();
        
        public abstract void setSrcCurrencyCode(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.lang.String> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.lang.String> getSrcCurrencyName();
        
        public abstract void setSrcCurrencyName(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.lang.String> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.lang.String> getSrcCountry2DigitCode();
        
        public abstract void setSrcCountry2DigitCode(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.lang.String> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.lang.String> getExchangeRate();
        
        public abstract void setExchangeRate(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.lang.String> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.lang.String> getYapRateString();
        
        public abstract void setYapRateString(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.lang.String> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableBoolean getValid();
        
        public abstract void setValid(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableBoolean p0);
    }
}