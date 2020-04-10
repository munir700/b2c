package co.yap.sendMoney.addbeneficiary.interfaces;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u00a8\u0006\u0005"}, d2 = {"Lco/yap/sendMoney/addbeneficiary/interfaces/ISelectCountry;", "", "State", "View", "ViewModel", "sendmoney_debug"})
public abstract interface ISelectCountry {
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001R\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\u00020\tX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r\u00a8\u0006\u000e"}, d2 = {"Lco/yap/sendMoney/addbeneficiary/interfaces/ISelectCountry$State;", "Lco/yap/yapcore/IBase$State;", "selectedCountry", "Lco/yap/countryutils/country/Country;", "getSelectedCountry", "()Lco/yap/countryutils/country/Country;", "setSelectedCountry", "(Lco/yap/countryutils/country/Country;)V", "valid", "", "getValid", "()Z", "setValid", "(Z)V", "sendmoney_debug"})
    public static abstract interface State extends co.yap.yapcore.IBase.State {
        
        @org.jetbrains.annotations.Nullable()
        public abstract co.yap.countryutils.country.Country getSelectedCountry();
        
        public abstract void setSelectedCountry(@org.jetbrains.annotations.Nullable()
        co.yap.countryutils.country.Country p0);
        
        public abstract boolean getValid();
        
        public abstract void setValid(boolean p0);
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H&J\u0010\u0010\u001a\u001a\u00020\u00172\u0006\u0010\u001b\u001a\u00020\u0019H&J\u0010\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H&R\u0018\u0010\u0003\u001a\u00020\u0004X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR(\u0010\t\u001a\u0012\u0012\u0004\u0012\u00020\u000b0\nj\b\u0012\u0004\u0012\u00020\u000b`\fX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001e\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00130\u0012X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006\u001d"}, d2 = {"Lco/yap/sendMoney/addbeneficiary/interfaces/ISelectCountry$ViewModel;", "Lco/yap/yapcore/IBase$ViewModel;", "Lco/yap/sendMoney/addbeneficiary/interfaces/ISelectCountry$State;", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "setClickEvent", "(Lco/yap/yapcore/SingleClickEvent;)V", "countries", "Ljava/util/ArrayList;", "Lco/yap/countryutils/country/Country;", "Lkotlin/collections/ArrayList;", "getCountries", "()Ljava/util/ArrayList;", "setCountries", "(Ljava/util/ArrayList;)V", "populateSpinnerData", "Landroidx/lifecycle/MutableLiveData;", "", "getPopulateSpinnerData", "()Landroidx/lifecycle/MutableLiveData;", "handlePressOnSeclectCountry", "", "id", "", "onCountrySelected", "pos", "onTransparentViewClick", "sendmoney_debug"})
    public static abstract interface ViewModel extends co.yap.yapcore.IBase.ViewModel<co.yap.sendMoney.addbeneficiary.interfaces.ISelectCountry.State> {
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.yapcore.SingleClickEvent getClickEvent();
        
        public abstract void setClickEvent(@org.jetbrains.annotations.NotNull()
        co.yap.yapcore.SingleClickEvent p0);
        
        public abstract void handlePressOnSeclectCountry(int id);
        
        public abstract void onTransparentViewClick(int id);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.util.ArrayList<co.yap.countryutils.country.Country> getCountries();
        
        public abstract void setCountries(@org.jetbrains.annotations.NotNull()
        java.util.ArrayList<co.yap.countryutils.country.Country> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.util.List<co.yap.countryutils.country.Country>> getPopulateSpinnerData();
        
        public abstract void onCountrySelected(int pos);
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001\u00a8\u0006\u0003"}, d2 = {"Lco/yap/sendMoney/addbeneficiary/interfaces/ISelectCountry$View;", "Lco/yap/yapcore/IBase$View;", "Lco/yap/sendMoney/addbeneficiary/interfaces/ISelectCountry$ViewModel;", "sendmoney_debug"})
    public static abstract interface View extends co.yap.yapcore.IBase.View<co.yap.sendMoney.addbeneficiary.interfaces.ISelectCountry.ViewModel> {
    }
}