package co.yap.sendMoney.viewmodels;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0016J\b\u0010\"\u001a\u00020\u001fH\u0016R \u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000fX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R \u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\bX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u000b\"\u0004\b\u0015\u0010\rR \u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\bX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u000b\"\u0004\b\u0019\u0010\rR\u0014\u0010\u001a\u001a\u00020\u001bX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001d\u00a8\u0006#"}, d2 = {"Lco/yap/sendMoney/viewmodels/SendMoneyViewModel;", "Lco/yap/yapcore/BaseViewModel;", "Lco/yap/sendMoney/interfaces/ISendMoney$State;", "Lco/yap/sendMoney/interfaces/ISendMoney$ViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "beneficiary", "Landroidx/lifecycle/MutableLiveData;", "Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;", "getBeneficiary", "()Landroidx/lifecycle/MutableLiveData;", "setBeneficiary", "(Landroidx/lifecycle/MutableLiveData;)V", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "otpSuccess", "", "getOtpSuccess", "setOtpSuccess", "selectedCountry", "Lco/yap/countryutils/country/Country;", "getSelectedCountry", "setSelectedCountry", "state", "Lco/yap/sendMoney/states/SendMoneyState;", "getState", "()Lco/yap/sendMoney/states/SendMoneyState;", "handlePressButton", "", "id", "", "onCreate", "sendmoney_debug"})
public final class SendMoneyViewModel extends co.yap.yapcore.BaseViewModel<co.yap.sendMoney.interfaces.ISendMoney.State> implements co.yap.sendMoney.interfaces.ISendMoney.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final co.yap.yapcore.SingleClickEvent clickEvent = null;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<co.yap.countryutils.country.Country> selectedCountry;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<co.yap.networking.customers.responsedtos.sendmoney.Beneficiary> beneficiary;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.sendMoney.states.SendMoneyState state = null;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Boolean> otpSuccess;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.yapcore.SingleClickEvent getClickEvent() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<co.yap.countryutils.country.Country> getSelectedCountry() {
        return null;
    }
    
    @java.lang.Override()
    public void setSelectedCountry(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<co.yap.countryutils.country.Country> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<co.yap.networking.customers.responsedtos.sendmoney.Beneficiary> getBeneficiary() {
        return null;
    }
    
    @java.lang.Override()
    public void setBeneficiary(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<co.yap.networking.customers.responsedtos.sendmoney.Beneficiary> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.sendMoney.states.SendMoneyState getState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.lang.Boolean> getOtpSuccess() {
        return null;
    }
    
    @java.lang.Override()
    public void setOtpSuccess(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Boolean> p0) {
    }
    
    @java.lang.Override()
    public void handlePressButton(int id) {
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    public SendMoneyViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
}