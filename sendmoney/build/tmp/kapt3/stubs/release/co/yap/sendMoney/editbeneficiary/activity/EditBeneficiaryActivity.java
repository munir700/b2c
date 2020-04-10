package co.yap.sendMoney.editbeneficiary.activity;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0005\u00a2\u0006\u0002\u0010\u0004J\r\u0010\u000e\u001a\u00020\u000fH\u0016\u00a2\u0006\u0002\u0010\u0010J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0002J\"\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u00122\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0014J\u0012\u0010\u001b\u001a\u00020\u00162\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0014J\u0012\u0010\u001e\u001a\u00020\u00162\b\b\u0002\u0010\u001f\u001a\u00020\tH\u0002J\b\u0010 \u001a\u00020\u0016H\u0016J\u0010\u0010!\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020\u001dH\u0002R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\u00028VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\r\u00a8\u0006#"}, d2 = {"Lco/yap/sendMoney/editbeneficiary/activity/EditBeneficiaryActivity;", "Lco/yap/yapcore/BaseBindingActivity;", "Lco/yap/sendMoney/editbeneficiary/interfaces/IEditBeneficiary$ViewModel;", "Lco/yap/sendMoney/editbeneficiary/interfaces/IEditBeneficiary$View;", "()V", "currencyPopMenu", "Lco/yap/widgets/popmenu/PopupMenu;", "isBeneficiaryValidObserver", "Landroidx/lifecycle/Observer;", "", "onBeneficiaryCreatedSuccessObserver", "viewModel", "getViewModel", "()Lco/yap/sendMoney/editbeneficiary/interfaces/IEditBeneficiary$ViewModel;", "getBindingVariable", "error/NonExistentClass", "()Lerror/NonExistentClass;", "getLayoutId", "", "getbinding", "Lco/yap/sendmoney/databinding/ActivityEditBeneficiaryBinding;", "onActivityResult", "", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "setIntentResult", "isMoneyTransfer", "setObservers", "updateAccountTitle", "bundleData", "sendmoney_release"})
public final class EditBeneficiaryActivity extends co.yap.yapcore.BaseBindingActivity<co.yap.sendMoney.editbeneficiary.interfaces.IEditBeneficiary.ViewModel> implements co.yap.sendMoney.editbeneficiary.interfaces.IEditBeneficiary.View {
    private co.yap.widgets.popmenu.PopupMenu currencyPopMenu;
    private final androidx.lifecycle.Observer<java.lang.Boolean> isBeneficiaryValidObserver = null;
    private final androidx.lifecycle.Observer<java.lang.Boolean> onBeneficiaryCreatedSuccessObserver = null;
    private java.util.HashMap _$_findViewCache;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public error.NonExistentClass getBindingVariable() {
        return null;
    }
    
    @java.lang.Override()
    public int getLayoutId() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.sendMoney.editbeneficiary.interfaces.IEditBeneficiary.ViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void updateAccountTitle(android.os.Bundle bundleData) {
    }
    
    @java.lang.Override()
    public void setObservers() {
    }
    
    private final void setIntentResult(boolean isMoneyTransfer) {
    }
    
    @java.lang.Override()
    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable()
    android.content.Intent data) {
    }
    
    private final co.yap.sendmoney.databinding.ActivityEditBeneficiaryBinding getbinding() {
        return null;
    }
    
    public EditBeneficiaryActivity() {
        super();
    }
}