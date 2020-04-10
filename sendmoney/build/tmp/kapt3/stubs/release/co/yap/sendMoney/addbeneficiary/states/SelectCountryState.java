package co.yap.sendMoney.addbeneficiary.states;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R*\u0010\n\u001a\u0004\u0018\u00010\t2\b\u0010\b\u001a\u0004\u0018\u00010\t8\u0016@VX\u0097\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR&\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\u000f8W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0015"}, d2 = {"Lco/yap/sendMoney/addbeneficiary/states/SelectCountryState;", "Lco/yap/yapcore/BaseState;", "Lco/yap/sendMoney/addbeneficiary/interfaces/ISelectCountry$State;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "getApplication", "()Landroid/app/Application;", "value", "Lco/yap/countryutils/country/Country;", "selectedCountry", "getSelectedCountry", "()Lco/yap/countryutils/country/Country;", "setSelectedCountry", "(Lco/yap/countryutils/country/Country;)V", "", "valid", "getValid", "()Z", "setValid", "(Z)V", "sendmoney_release"})
public final class SelectCountryState extends co.yap.yapcore.BaseState implements co.yap.sendMoney.addbeneficiary.interfaces.ISelectCountry.State {
    @org.jetbrains.annotations.Nullable()
    @androidx.databinding.Bindable()
    private co.yap.countryutils.country.Country selectedCountry;
    private boolean valid;
    @org.jetbrains.annotations.NotNull()
    private final android.app.Application application = null;
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public co.yap.countryutils.country.Country getSelectedCountry() {
        return null;
    }
    
    @java.lang.Override()
    public void setSelectedCountry(@org.jetbrains.annotations.Nullable()
    co.yap.countryutils.country.Country value) {
    }
    
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public boolean getValid() {
        return false;
    }
    
    @java.lang.Override()
    public void setValid(boolean value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.app.Application getApplication() {
        return null;
    }
    
    public SelectCountryState(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super();
    }
}