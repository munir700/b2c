package co.yap.sendMoney.fundtransfer.fragments;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0005\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\b\u0010\u0010\u001a\u00020\u0007H\u0016J\u0006\u0010\u0011\u001a\u00020\u0012J\b\u0010\u0013\u001a\u00020\u0007H\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010\u0018\u001a\u00020\u0017H\u0002J\b\u0010\u0019\u001a\u00020\u000fH\u0002J\u0012\u0010\u001a\u001a\u00020\u000f2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u000fH\u0016J\b\u0010\u001e\u001a\u00020\u000fH\u0016J\u001a\u0010\u001f\u001a\u00020\u000f2\u0006\u0010 \u001a\u00020!2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J\b\u0010\"\u001a\u00020\u000fH\u0002J\b\u0010#\u001a\u00020\u000fH\u0016J\u0012\u0010$\u001a\u00020\u000f2\b\u0010%\u001a\u0004\u0018\u00010\u0015H\u0002J&\u0010&\u001a\u00020\u000f2\u001c\u0010\'\u001a\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0012\n\u0012\b\u0012\u0004\u0012\u00020*0)\u0018\u00010(H\u0002J\b\u0010+\u001a\u00020\u000fH\u0002J\b\u0010,\u001a\u00020\u000fH\u0002J\b\u0010-\u001a\u00020\u000fH\u0002J\u0010\u0010.\u001a\u00020\u000f2\u0006\u0010/\u001a\u00020\u0015H\u0002J\b\u00100\u001a\u00020\u000fH\u0002R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\r\u00a8\u00061"}, d2 = {"Lco/yap/sendMoney/fundtransfer/fragments/CashTransferFragment;", "Lco/yap/sendMoney/fundtransfer/fragments/BeneficiaryFundTransferBaseFragment;", "Lco/yap/sendMoney/fundtransfer/interfaces/ICashTransfer$ViewModel;", "Lco/yap/sendMoney/fundtransfer/interfaces/ICashTransfer$View;", "()V", "clickEvent", "Landroidx/lifecycle/Observer;", "", "getClickEvent", "()Landroidx/lifecycle/Observer;", "viewModel", "Lco/yap/sendMoney/fundtransfer/viewmodels/CashTransferViewModel;", "getViewModel", "()Lco/yap/sendMoney/fundtransfer/viewmodels/CashTransferViewModel;", "checkOnTextChangeValidation", "", "getBindingVariable", "getBindings", "Lco/yap/sendmoney/databinding/FragmentCashTransferBinding;", "getLayoutId", "getProductCode", "", "isBalanceAvailable", "", "isDailyLimitReached", "moveToConfirmationScreen", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onPause", "onViewCreated", "view", "Landroid/view/View;", "setEditTextWatcher", "setObservers", "setSpannableFee", "totalFeeAmount", "setupPOP", "purposeCategories", "", "", "Lco/yap/networking/transactions/responsedtos/purposepayment/PurposeOfPayment;", "showBalanceNotAvailableError", "showLimitError", "skipCashTransferFragment", "startFlows", "productCode", "startOtpFragment", "sendmoney_release"})
public final class CashTransferFragment extends co.yap.sendMoney.fundtransfer.fragments.BeneficiaryFundTransferBaseFragment<co.yap.sendMoney.fundtransfer.interfaces.ICashTransfer.ViewModel> implements co.yap.sendMoney.fundtransfer.interfaces.ICashTransfer.View {
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
    public co.yap.sendMoney.fundtransfer.viewmodels.CashTransferViewModel getViewModel() {
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
    
    @java.lang.Override()
    public void setObservers() {
    }
    
    private final void setSpannableFee(java.lang.String totalFeeAmount) {
    }
    
    private final void setupPOP(java.util.Map<java.lang.String, ? extends java.util.List<co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment>> purposeCategories) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.Observer<java.lang.Integer> getClickEvent() {
        return null;
    }
    
    private final void startOtpFragment() {
    }
    
    private final void moveToConfirmationScreen() {
    }
    
    private final void showBalanceNotAvailableError() {
    }
    
    private final void showLimitError() {
    }
    
    private final boolean isBalanceAvailable() {
        return false;
    }
    
    private final boolean isDailyLimitReached() {
        return false;
    }
    
    private final void startFlows(java.lang.String productCode) {
    }
    
    private final java.lang.String getProductCode() {
        return null;
    }
    
    @java.lang.Override()
    public void onPause() {
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    private final void skipCashTransferFragment() {
    }
    
    private final void setEditTextWatcher() {
    }
    
    private final void checkOnTextChangeValidation() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final co.yap.sendmoney.databinding.FragmentCashTransferBinding getBindings() {
        return null;
    }
    
    public CashTransferFragment() {
        super();
    }
}