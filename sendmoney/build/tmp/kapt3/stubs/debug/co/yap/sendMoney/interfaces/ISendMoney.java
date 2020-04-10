package co.yap.sendMoney.interfaces;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u00a8\u0006\u0005"}, d2 = {"Lco/yap/sendMoney/interfaces/ISendMoney;", "", "State", "View", "ViewModel", "sendmoney_debug"})
public abstract interface ISendMoney {
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\bf\u0018\u00002\u00020\u0001R\u0018\u0010\u0002\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\t\u0010\u0005\"\u0004\b\n\u0010\u0007R\u0018\u0010\u000b\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\f\u0010\u0005\"\u0004\b\r\u0010\u0007\u00a8\u0006\u000e"}, d2 = {"Lco/yap/sendMoney/interfaces/ISendMoney$State;", "Lco/yap/yapcore/IBase$State;", "leftIcon", "Landroidx/databinding/ObservableBoolean;", "getLeftIcon", "()Landroidx/databinding/ObservableBoolean;", "setLeftIcon", "(Landroidx/databinding/ObservableBoolean;)V", "rightIcon", "getRightIcon", "setRightIcon", "toolbarVisibility", "getToolbarVisibility", "setToolbarVisibility", "sendmoney_debug"})
    public static abstract interface State extends co.yap.yapcore.IBase.State {
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableBoolean getToolbarVisibility();
        
        public abstract void setToolbarVisibility(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableBoolean p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableBoolean getRightIcon();
        
        public abstract void setRightIcon(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableBoolean p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableBoolean getLeftIcon();
        
        public abstract void setLeftIcon(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableBoolean p0);
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H&R\u001e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\rR\u001e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0004X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0010\u0010\u0007\"\u0004\b\u0011\u0010\tR\u001e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u0004X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0014\u0010\u0007\"\u0004\b\u0015\u0010\t\u00a8\u0006\u001a"}, d2 = {"Lco/yap/sendMoney/interfaces/ISendMoney$ViewModel;", "Lco/yap/yapcore/IBase$ViewModel;", "Lco/yap/sendMoney/interfaces/ISendMoney$State;", "beneficiary", "Landroidx/lifecycle/MutableLiveData;", "Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;", "getBeneficiary", "()Landroidx/lifecycle/MutableLiveData;", "setBeneficiary", "(Landroidx/lifecycle/MutableLiveData;)V", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "otpSuccess", "", "getOtpSuccess", "setOtpSuccess", "selectedCountry", "Lco/yap/countryutils/country/Country;", "getSelectedCountry", "setSelectedCountry", "handlePressButton", "", "id", "", "sendmoney_debug"})
    public static abstract interface ViewModel extends co.yap.yapcore.IBase.ViewModel<co.yap.sendMoney.interfaces.ISendMoney.State> {
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.yapcore.SingleClickEvent getClickEvent();
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<co.yap.countryutils.country.Country> getSelectedCountry();
        
        public abstract void setSelectedCountry(@org.jetbrains.annotations.NotNull()
        androidx.lifecycle.MutableLiveData<co.yap.countryutils.country.Country> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<co.yap.networking.customers.responsedtos.sendmoney.Beneficiary> getBeneficiary();
        
        public abstract void setBeneficiary(@org.jetbrains.annotations.NotNull()
        androidx.lifecycle.MutableLiveData<co.yap.networking.customers.responsedtos.sendmoney.Beneficiary> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.Boolean> getOtpSuccess();
        
        public abstract void setOtpSuccess(@org.jetbrains.annotations.NotNull()
        androidx.lifecycle.MutableLiveData<java.lang.Boolean> p0);
        
        public abstract void handlePressButton(int id);
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001\u00a8\u0006\u0003"}, d2 = {"Lco/yap/sendMoney/interfaces/ISendMoney$View;", "Lco/yap/yapcore/IBase$View;", "Lco/yap/sendMoney/interfaces/ISendMoney$ViewModel;", "sendmoney_debug"})
    public static abstract interface View extends co.yap.yapcore.IBase.View<co.yap.sendMoney.interfaces.ISendMoney.ViewModel> {
    }
}