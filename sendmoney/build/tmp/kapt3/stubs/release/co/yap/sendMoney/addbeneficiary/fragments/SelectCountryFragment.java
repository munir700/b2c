package co.yap.sendMoney.addbeneficiary.fragments;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0005\u00a2\u0006\u0002\u0010\u0004J\b\u0010\n\u001a\u00020\u000bH\u0016J\n\u0010\f\u001a\u0004\u0018\u00010\u0006H\u0002J\b\u0010\r\u001a\u00020\u000bH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0002J\b\u0010\u0012\u001a\u00020\u0011H\u0002J\b\u0010\u0013\u001a\u00020\u0011H\u0016J\b\u0010\u0014\u001a\u00020\u0011H\u0016J\u001a\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\u00028VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\t\u00a8\u0006\u001a"}, d2 = {"Lco/yap/sendMoney/addbeneficiary/fragments/SelectCountryFragment;", "Lco/yap/sendMoney/fragments/SendMoneyBaseFragment;", "Lco/yap/sendMoney/addbeneficiary/interfaces/ISelectCountry$ViewModel;", "Lco/yap/sendMoney/addbeneficiary/interfaces/ISelectCountry$View;", "()V", "countryAdapter", "Lco/yap/sendMoney/adapters/CountryAdapter;", "viewModel", "getViewModel", "()Lco/yap/sendMoney/addbeneficiary/interfaces/ISelectCountry$ViewModel;", "getBindingVariable", "", "getCountryAdapter", "getLayoutId", "isDefaultCurrencyExist", "", "moveToAddBeneficiary", "", "moveToTransferType", "onPause", "onResume", "onViewCreated", "view", "Landroid/view/View;", "savedInstanceState", "Landroid/os/Bundle;", "sendmoney_release"})
public final class SelectCountryFragment extends co.yap.sendMoney.fragments.SendMoneyBaseFragment<co.yap.sendMoney.addbeneficiary.interfaces.ISelectCountry.ViewModel> implements co.yap.sendMoney.addbeneficiary.interfaces.ISelectCountry.View {
    private co.yap.sendMoney.adapters.CountryAdapter countryAdapter;
    private java.util.HashMap _$_findViewCache;
    
    @java.lang.Override()
    public int getBindingVariable() {
        return 0;
    }
    
    @java.lang.Override()
    public int getLayoutId() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.sendMoney.addbeneficiary.interfaces.ISelectCountry.ViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    public void onPause() {
    }
    
    @java.lang.Override()
    public void onResume() {
    }
    
    private final boolean isDefaultCurrencyExist() {
        return false;
    }
    
    private final void moveToAddBeneficiary() {
    }
    
    private final void moveToTransferType() {
    }
    
    private final co.yap.sendMoney.adapters.CountryAdapter getCountryAdapter() {
        return null;
    }
    
    public SelectCountryFragment() {
        super();
    }
}