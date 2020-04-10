package co.yap.sendMoney.addbeneficiary.fragments;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0005\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0012\u001a\u00020\u0013H\u0002J\b\u0010\u0014\u001a\u00020\u000bH\u0016J\b\u0010\u0015\u001a\u00020\u000bH\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0013H\u0016J\b\u0010\u0019\u001a\u00020\u0013H\u0016J\u001a\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\b\u0010\u001f\u001a\u00020 H\u0002J\b\u0010!\u001a\u00020\u0013H\u0002J\u0016\u0010\"\u001a\u00020\u00132\f\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$H\u0002R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006&"}, d2 = {"Lco/yap/sendMoney/addbeneficiary/fragments/AddBankDetailsFragment;", "Lco/yap/sendMoney/fragments/SendMoneyBaseFragment;", "Lco/yap/sendMoney/addbeneficiary/interfaces/IBankDetails$ViewModel;", "Lco/yap/sendMoney/addbeneficiary/interfaces/IBankDetails$View;", "()V", "listener", "Lco/yap/yapcore/interfaces/OnItemClickListener;", "getListener", "()Lco/yap/yapcore/interfaces/OnItemClickListener;", "observer", "Landroidx/lifecycle/Observer;", "", "getObserver", "()Landroidx/lifecycle/Observer;", "viewModel", "Lco/yap/sendMoney/addbeneficiary/viewmodels/BankDetailsViewModel;", "getViewModel", "()Lco/yap/sendMoney/addbeneficiary/viewmodels/BankDetailsViewModel;", "addListener", "", "getBindingVariable", "getLayoutId", "onBackPressed", "", "onDestroy", "onPause", "onViewCreated", "view", "Landroid/view/View;", "savedInstanceState", "Landroid/os/Bundle;", "otherSearchParams", "Lco/yap/networking/customers/requestdtos/OtherBankQuery;", "setIntentResult", "setupAdaptorBanks", "list", "", "Lco/yap/networking/customers/responsedtos/sendmoney/RAKBank$Bank;", "sendmoney_release"})
public final class AddBankDetailsFragment extends co.yap.sendMoney.fragments.SendMoneyBaseFragment<co.yap.sendMoney.addbeneficiary.interfaces.IBankDetails.ViewModel> implements co.yap.sendMoney.addbeneficiary.interfaces.IBankDetails.View {
    @org.jetbrains.annotations.NotNull()
    private final co.yap.yapcore.interfaces.OnItemClickListener listener = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.Observer<java.lang.Integer> observer = null;
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
    public co.yap.sendMoney.addbeneficiary.viewmodels.BankDetailsViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void addListener() {
    }
    
    private final void setIntentResult() {
    }
    
    private final void setupAdaptorBanks(java.util.List<co.yap.networking.customers.responsedtos.sendmoney.RAKBank.Bank> list) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final co.yap.yapcore.interfaces.OnItemClickListener getListener() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.Observer<java.lang.Integer> getObserver() {
        return null;
    }
    
    private final co.yap.networking.customers.requestdtos.OtherBankQuery otherSearchParams() {
        return null;
    }
    
    @java.lang.Override()
    public void onPause() {
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    @java.lang.Override()
    public boolean onBackPressed() {
        return false;
    }
    
    public AddBankDetailsFragment() {
        super();
    }
}