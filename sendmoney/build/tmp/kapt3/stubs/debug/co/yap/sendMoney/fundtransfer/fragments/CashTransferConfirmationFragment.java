package co.yap.sendmoney.fundtransfer.fragments;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0005\u00a2\u0006\u0002\u0010\u0004J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\rH\u0002J\b\u0010\u000f\u001a\u00020\u0007H\u0016J\b\u0010\u0010\u001a\u00020\u0007H\u0016J\n\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0002J\u0012\u0010\u0017\u001a\u00020\r2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\b\u0010\u001a\u001a\u00020\rH\u0016J\u001a\u0010\u001b\u001a\u00020\r2\u0006\u0010\u001c\u001a\u00020\u001d2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\b\u0010\u001e\u001a\u00020\rH\u0016J\b\u0010\u001f\u001a\u00020\rH\u0002J\b\u0010 \u001a\u00020\rH\u0002J\b\u0010!\u001a\u00020\rH\u0002J\b\u0010\"\u001a\u00020\rH\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000b\u00a8\u0006#"}, d2 = {"Lco/yap/sendmoney/fundtransfer/fragments/CashTransferConfirmationFragment;", "Lco/yap/sendmoney/fundtransfer/fragments/BeneficiaryFundTransferBaseFragment;", "Lco/yap/sendmoney/fundtransfer/interfaces/ICashTransferConfirmation$ViewModel;", "Lco/yap/sendmoney/fundtransfer/interfaces/ICashTransferConfirmation$View;", "()V", "clickObserver", "Landroidx/lifecycle/Observer;", "", "viewModel", "Lco/yap/sendmoney/fundtransfer/viewmodels/CashTransferConfirmationViewModel;", "getViewModel", "()Lco/yap/sendmoney/fundtransfer/viewmodels/CashTransferConfirmationViewModel;", "addObservers", "", "cashTransferSuccess", "getBindingVariable", "getLayoutId", "getOtpAction", "", "getViewBinding", "Lco/yap/sendmoney/databinding/FragmentCashTransferConfirmationBinding;", "isOtpRequired", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onViewCreated", "view", "Landroid/view/View;", "removeObservers", "setDisclaimerText", "setTransferAmountString", "setTransferFeeAmountString", "startOtpFragment", "sendmoney_debug"})
public final class CashTransferConfirmationFragment extends co.yap.sendmoney.fundtransfer.fragments.BeneficiaryFundTransferBaseFragment<co.yap.sendmoney.fundtransfer.interfaces.ICashTransferConfirmation.ViewModel> implements co.yap.sendmoney.fundtransfer.interfaces.ICashTransferConfirmation.View {
    private final androidx.lifecycle.Observer<java.lang.Integer> clickObserver = null;
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
    public co.yap.sendmoney.fundtransfer.viewmodels.CashTransferConfirmationViewModel getViewModel() {
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
    
    private final void setTransferAmountString() {
    }
    
    private final void setDisclaimerText() {
    }
    
    private final void setTransferFeeAmountString() {
    }
    
    @java.lang.Override()
    public void addObservers() {
    }
    
    @java.lang.Override()
    public void removeObservers() {
    }
    
    private final void cashTransferSuccess() {
    }
    
    private final boolean isOtpRequired() {
        return false;
    }
    
    private final void startOtpFragment() {
    }
    
    private final java.lang.String getOtpAction() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.sendmoney.databinding.FragmentCashTransferConfirmationBinding getViewBinding() {
        return null;
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    public CashTransferConfirmationFragment() {
        super();
    }
}