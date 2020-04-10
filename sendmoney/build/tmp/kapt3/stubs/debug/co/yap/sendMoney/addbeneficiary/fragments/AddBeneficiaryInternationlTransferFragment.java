package co.yap.sendMoney.addbeneficiary.fragments;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0005\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0013\u001a\u00020\u0014H\u0002J\b\u0010\u0015\u001a\u00020\tH\u0016J(\u0010\u0016\u001a\u0012\u0012\u0004\u0012\u00020\u000e0\u0017j\b\u0012\u0004\u0012\u00020\u000e`\u00182\u000e\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\u001b\u0018\u00010\u001aH\u0002J\b\u0010\u001c\u001a\u00020\tH\u0016J\b\u0010\u001d\u001a\u00020\u0014H\u0002J\b\u0010\u001e\u001a\u00020\u000bH\u0016J\u0012\u0010\u001f\u001a\u00020\u00142\b\u0010 \u001a\u0004\u0018\u00010!H\u0016J\b\u0010\"\u001a\u00020\u0014H\u0016J\u001a\u0010#\u001a\u00020\u00142\u0006\u0010$\u001a\u00020%2\b\u0010 \u001a\u0004\u0018\u00010!H\u0016J\b\u0010&\u001a\u00020\u0014H\u0002J\b\u0010\'\u001a\u00020\u0014H\u0002J\b\u0010(\u001a\u00020\u0014H\u0002R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006)"}, d2 = {"Lco/yap/sendMoney/addbeneficiary/fragments/AddBeneficiaryInternationlTransferFragment;", "Lco/yap/sendMoney/fragments/SendMoneyBaseFragment;", "Lco/yap/sendMoney/addbeneficiary/interfaces/IAddBeneficiary$ViewModel;", "Lco/yap/sendMoney/addbeneficiary/interfaces/IAddBeneficiary$View;", "()V", "currencyPopMenu", "Lco/yap/widgets/popmenu/PopupMenu;", "observer", "Landroidx/lifecycle/Observer;", "", "otpCreateObserver", "", "popupItemClickListener", "Lco/yap/widgets/popmenu/OnMenuItemClickListener;", "Lco/yap/widgets/popmenu/PopupMenuItem;", "viewModel", "Lco/yap/sendMoney/addbeneficiary/viewmodels/AddBeneficiaryViewModel;", "getViewModel", "()Lco/yap/sendMoney/addbeneficiary/viewmodels/AddBeneficiaryViewModel;", "addBeneficiaryDialog", "", "getBindingVariable", "getCurrencyList", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "currencies", "", "Lco/yap/countryutils/country/utils/Currency;", "getLayoutId", "initComponents", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onViewCreated", "view", "Landroid/view/View;", "setIntentResult", "startMoneyTransfer", "startOtpFragment", "sendmoney_debug"})
public final class AddBeneficiaryInternationlTransferFragment extends co.yap.sendMoney.fragments.SendMoneyBaseFragment<co.yap.sendMoney.addbeneficiary.interfaces.IAddBeneficiary.ViewModel> implements co.yap.sendMoney.addbeneficiary.interfaces.IAddBeneficiary.View {
    private co.yap.widgets.popmenu.PopupMenu currencyPopMenu;
    private final androidx.lifecycle.Observer<java.lang.Integer> observer = null;
    private final androidx.lifecycle.Observer<java.lang.Boolean> otpCreateObserver = null;
    private final co.yap.widgets.popmenu.OnMenuItemClickListener<co.yap.widgets.popmenu.PopupMenuItem> popupItemClickListener = null;
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
    public co.yap.sendMoney.addbeneficiary.viewmodels.AddBeneficiaryViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    public void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initComponents() {
    }
    
    private final java.util.ArrayList<co.yap.widgets.popmenu.PopupMenuItem> getCurrencyList(java.util.List<co.yap.countryutils.country.utils.Currency> currencies) {
        return null;
    }
    
    private final void startOtpFragment() {
    }
    
    private final void addBeneficiaryDialog() {
    }
    
    private final void startMoneyTransfer() {
    }
    
    private final void setIntentResult() {
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    @java.lang.Override()
    public boolean onBackPressed() {
        return false;
    }
    
    public AddBeneficiaryInternationlTransferFragment() {
        super();
    }
}