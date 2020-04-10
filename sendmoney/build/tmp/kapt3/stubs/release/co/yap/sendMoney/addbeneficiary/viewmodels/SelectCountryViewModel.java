package co.yap.sendMoney.addbeneficiary.viewmodels;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0006\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u00032\b\u0012\u0004\u0012\u00020\u00050\u0004B\r\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010%\u001a\u00020&H\u0002J&\u0010\'\u001a\u0004\u0018\u00010(2\u000e\u0010)\u001a\n\u0012\u0004\u0012\u00020*\u0018\u00010\u00192\n\b\u0002\u0010+\u001a\u0004\u0018\u00010,H\u0002J$\u0010-\u001a\u0004\u0018\u00010(2\u000e\u0010.\u001a\n\u0012\u0004\u0012\u00020*\u0018\u00010\u00192\b\u0010+\u001a\u0004\u0018\u00010,H\u0002J\u001a\u0010/\u001a\u0004\u0018\u00010(2\u000e\u0010)\u001a\n\u0012\u0004\u0012\u00020*\u0018\u00010\u0019H\u0002J\u0010\u00100\u001a\u00020&2\u0006\u00101\u001a\u000202H\u0016J\u0010\u00103\u001a\u00020&2\u0006\u00104\u001a\u000202H\u0016J\b\u00105\u001a\u00020&H\u0016J\b\u00106\u001a\u00020&H\u0016J\u0010\u00107\u001a\u00020&2\u0006\u00101\u001a\u000202H\u0016R\u001a\u0010\t\u001a\u00020\nX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR*\u0010\u000f\u001a\u0012\u0012\u0004\u0012\u00020\u00110\u0010j\b\u0012\u0004\u0012\u00020\u0011`\u0012X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R&\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00190\u0018X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001e\u001a\u00020\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0014\u0010!\u001a\u00020\"X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$\u00a8\u00068"}, d2 = {"Lco/yap/sendMoney/addbeneficiary/viewmodels/SelectCountryViewModel;", "Lco/yap/sendMoney/viewmodels/SendMoneyBaseViewModel;", "Lco/yap/sendMoney/addbeneficiary/interfaces/ISelectCountry$State;", "Lco/yap/sendMoney/addbeneficiary/interfaces/ISelectCountry$ViewModel;", "Lco/yap/networking/interfaces/IRepositoryHolder;", "Lco/yap/networking/customers/CustomersRepository;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "setClickEvent", "(Lco/yap/yapcore/SingleClickEvent;)V", "countries", "Ljava/util/ArrayList;", "Lco/yap/countryutils/country/Country;", "Lkotlin/collections/ArrayList;", "getCountries", "()Ljava/util/ArrayList;", "setCountries", "(Ljava/util/ArrayList;)V", "populateSpinnerData", "Landroidx/lifecycle/MutableLiveData;", "", "getPopulateSpinnerData", "()Landroidx/lifecycle/MutableLiveData;", "setPopulateSpinnerData", "(Landroidx/lifecycle/MutableLiveData;)V", "repository", "getRepository", "()Lco/yap/networking/customers/CustomersRepository;", "state", "Lco/yap/sendMoney/addbeneficiary/states/SelectCountryState;", "getState", "()Lco/yap/sendMoney/addbeneficiary/states/SelectCountryState;", "getAllCountries", "", "getDefaultCurrency", "Lco/yap/countryutils/country/utils/Currency;", "activeCurrencies", "Lco/yap/networking/customers/responsedtos/sendmoney/Currency;", "isoCountryCode2Digit", "", "getDefaultCurrencyIfNull", "currencyList", "getFirst", "handlePressOnSeclectCountry", "id", "", "onCountrySelected", "pos", "onCreate", "onResume", "onTransparentViewClick", "sendmoney_release"})
public final class SelectCountryViewModel extends co.yap.sendMoney.viewmodels.SendMoneyBaseViewModel<co.yap.sendMoney.addbeneficiary.interfaces.ISelectCountry.State> implements co.yap.sendMoney.addbeneficiary.interfaces.ISelectCountry.ViewModel, co.yap.networking.interfaces.IRepositoryHolder<co.yap.networking.customers.CustomersRepository> {
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.util.List<co.yap.countryutils.country.Country>> populateSpinnerData;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<co.yap.countryutils.country.Country> countries;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.networking.customers.CustomersRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.sendMoney.addbeneficiary.states.SelectCountryState state = null;
    @org.jetbrains.annotations.NotNull()
    private co.yap.yapcore.SingleClickEvent clickEvent;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.util.List<co.yap.countryutils.country.Country>> getPopulateSpinnerData() {
        return null;
    }
    
    public void setPopulateSpinnerData(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.util.List<co.yap.countryutils.country.Country>> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.util.ArrayList<co.yap.countryutils.country.Country> getCountries() {
        return null;
    }
    
    @java.lang.Override()
    public void setCountries(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<co.yap.countryutils.country.Country> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.networking.customers.CustomersRepository getRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.sendMoney.addbeneficiary.states.SelectCountryState getState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.yapcore.SingleClickEvent getClickEvent() {
        return null;
    }
    
    @java.lang.Override()
    public void setClickEvent(@org.jetbrains.annotations.NotNull()
    co.yap.yapcore.SingleClickEvent p0) {
    }
    
    @java.lang.Override()
    public void onTransparentViewClick(int id) {
    }
    
    @java.lang.Override()
    public void handlePressOnSeclectCountry(int id) {
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @java.lang.Override()
    public void onResume() {
    }
    
    private final void getAllCountries() {
    }
    
    private final co.yap.countryutils.country.utils.Currency getDefaultCurrency(java.util.List<co.yap.networking.customers.responsedtos.sendmoney.Currency> activeCurrencies, java.lang.String isoCountryCode2Digit) {
        return null;
    }
    
    private final co.yap.countryutils.country.utils.Currency getFirst(java.util.List<co.yap.networking.customers.responsedtos.sendmoney.Currency> activeCurrencies) {
        return null;
    }
    
    private final co.yap.countryutils.country.utils.Currency getDefaultCurrencyIfNull(java.util.List<co.yap.networking.customers.responsedtos.sendmoney.Currency> currencyList, java.lang.String isoCountryCode2Digit) {
        return null;
    }
    
    @java.lang.Override()
    public void onCountrySelected(int pos) {
    }
    
    public SelectCountryViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
}