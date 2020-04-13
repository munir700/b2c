package co.yap.sendmoney.addbeneficiary.fragments;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0005\u00a2\u0006\u0002\u0010\u0004J\b\u0010\f\u001a\u00020\rH\u0002J\b\u0010\u000e\u001a\u00020\u0007H\u0016J\b\u0010\u000f\u001a\u00020\u0007H\u0016J\"\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00072\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J\u0012\u0010\u0015\u001a\u00020\r2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\b\u0010\u0018\u001a\u00020\rH\u0016J\u0012\u0010\u0019\u001a\u00020\r2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0002J\u001c\u0010\u001c\u001a\u00020\r2\b\b\u0002\u0010\u001d\u001a\u00020\u001e2\b\b\u0002\u0010\u001a\u001a\u00020\u001bH\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u001f"}, d2 = {"Lco/yap/sendmoney/addbeneficiary/fragments/BeneficiaryAccountDetailsFragment;", "Lco/yap/sendmoney/fragments/SendMoneyBaseFragment;", "Lco/yap/sendmoney/addbeneficiary/interfaces/IBeneficiaryAccountDetails$ViewModel;", "Lco/yap/sendmoney/addbeneficiary/interfaces/IBeneficiaryAccountDetails$View;", "()V", "observer", "Landroidx/lifecycle/Observer;", "", "viewModel", "Lco/yap/sendmoney/addbeneficiary/viewmodels/BeneficiaryAccountDetailsViewModel;", "getViewModel", "()Lco/yap/sendmoney/addbeneficiary/viewmodels/BeneficiaryAccountDetailsViewModel;", "addObservers", "", "getBindingVariable", "getLayoutId", "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "openEditBeneficiary", "beneficiary", "Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;", "setIntentResult", "isMoneyTransfer", "", "sendmoney_debug"})
public final class BeneficiaryAccountDetailsFragment extends co.yap.sendmoney.fragments.SendMoneyBaseFragment<co.yap.sendmoney.addbeneficiary.interfaces.IBeneficiaryAccountDetails.ViewModel> implements co.yap.sendmoney.addbeneficiary.interfaces.IBeneficiaryAccountDetails.View {
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
    public co.yap.sendmoney.addbeneficiary.viewmodels.BeneficiaryAccountDetailsViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    public void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void addObservers() {
    }
    
    private final void openEditBeneficiary(co.yap.networking.customers.responsedtos.sendmoney.Beneficiary beneficiary) {
    }
    
    @java.lang.Override()
    public void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable()
    android.content.Intent data) {
    }
    
    private final void setIntentResult(boolean isMoneyTransfer, co.yap.networking.customers.responsedtos.sendmoney.Beneficiary beneficiary) {
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    public BeneficiaryAccountDetailsFragment() {
        super();
    }
}