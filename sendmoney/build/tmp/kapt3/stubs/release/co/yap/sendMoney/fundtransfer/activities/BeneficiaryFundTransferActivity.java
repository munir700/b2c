package co.yap.sendMoney.fundtransfer.activities;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u00032\u00020\u0004B\u0005\u00a2\u0006\u0002\u0010\u0005J\b\u0010\u0015\u001a\u00020\u0016H\u0002J\b\u0010\u0017\u001a\u00020\bH\u0016J\b\u0010\u0018\u001a\u00020\bH\u0016J\b\u0010\u0019\u001a\u00020\u0016H\u0002J\b\u0010\u001a\u001a\u00020\u0016H\u0016J\u0012\u0010\u001b\u001a\u00020\u00162\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0014J\b\u0010\u001e\u001a\u00020\u0016H\u0014J\u0010\u0010\u001f\u001a\u00020\u00162\u0006\u0010 \u001a\u00020\fH\u0002R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00028VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006!"}, d2 = {"Lco/yap/sendMoney/fundtransfer/activities/BeneficiaryFundTransferActivity;", "Lco/yap/yapcore/BaseBindingActivity;", "Lco/yap/sendMoney/fundtransfer/interfaces/IBeneficiaryFundTransfer$ViewModel;", "Lco/yap/yapcore/IFragmentHolder;", "Lco/yap/yapcore/defaults/INavigator;", "()V", "clickEvent", "Landroidx/lifecycle/Observer;", "", "getClickEvent", "()Landroidx/lifecycle/Observer;", "errorEvent", "", "getErrorEvent", "navigator", "Lco/yap/yapcore/interfaces/IBaseNavigator;", "getNavigator", "()Lco/yap/yapcore/interfaces/IBaseNavigator;", "viewModel", "getViewModel", "()Lco/yap/sendMoney/fundtransfer/interfaces/IBeneficiaryFundTransfer$ViewModel;", "getBeneficiary", "", "getBindingVariable", "getLayoutId", "hideErrorSnackBar", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "showErrorSnackBar", "errorMessage", "sendmoney_release"})
public final class BeneficiaryFundTransferActivity extends co.yap.yapcore.BaseBindingActivity<co.yap.sendMoney.fundtransfer.interfaces.IBeneficiaryFundTransfer.ViewModel> implements co.yap.yapcore.IFragmentHolder, co.yap.yapcore.defaults.INavigator {
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.Observer<java.lang.String> errorEvent = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.Observer<java.lang.Integer> clickEvent = null;
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
    public co.yap.sendMoney.fundtransfer.interfaces.IBeneficiaryFundTransfer.ViewModel getViewModel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.yapcore.interfaces.IBaseNavigator getNavigator() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.Observer<java.lang.String> getErrorEvent() {
        return null;
    }
    
    private final void showErrorSnackBar(java.lang.String errorMessage) {
    }
    
    private final void hideErrorSnackBar() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.Observer<java.lang.Integer> getClickEvent() {
        return null;
    }
    
    private final void getBeneficiary() {
    }
    
    @java.lang.Override()
    public void onBackPressed() {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    public BeneficiaryFundTransferActivity() {
        super();
    }
    
    public void onFragmentAttached() {
    }
    
    public void onFragmentDetached(@org.jetbrains.annotations.NotNull()
    java.lang.String tag) {
    }
}