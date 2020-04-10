package co.yap.sendMoney.fundtransfer.fragments;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010$\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0005\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\b\u0010\u0010\u001a\u00020\u0007H\u0016J\u0006\u0010\u0011\u001a\u00020\u0012J\b\u0010\u0013\u001a\u00020\u0007H\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0002J\u0012\u0010\u0016\u001a\u00020\u000f2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0002J\b\u0010\u0019\u001a\u00020\u001aH\u0002J\b\u0010\u001b\u001a\u00020\u001aH\u0002J\b\u0010\u001c\u001a\u00020\u000fH\u0002J\u0012\u0010\u001d\u001a\u00020\u000f2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\u0012\u0010 \u001a\u00020\u000f2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\b\u0010!\u001a\u00020\u000fH\u0016J\b\u0010\"\u001a\u00020\u000fH\u0016J\b\u0010#\u001a\u00020\u000fH\u0016J\u001a\u0010$\u001a\u00020\u000f2\u0006\u0010%\u001a\u00020&2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\b\u0010\'\u001a\u00020\u000fH\u0002J\b\u0010(\u001a\u00020\u000fH\u0002J\b\u0010)\u001a\u00020\u000fH\u0002J\u0012\u0010*\u001a\u00020\u000f2\b\u0010+\u001a\u0004\u0018\u00010\u0015H\u0002J&\u0010,\u001a\u00020\u000f2\u001c\u0010-\u001a\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0012\n\u0012\b\u0012\u0004\u0012\u0002000/\u0018\u00010.H\u0002J\b\u00101\u001a\u00020\u000fH\u0002J\b\u00102\u001a\u00020\u000fH\u0002J\b\u00103\u001a\u00020\u000fH\u0002R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\r\u00a8\u00064"}, d2 = {"Lco/yap/sendMoney/fundtransfer/fragments/InternationalFundsTransferFragment;", "Lco/yap/sendMoney/fundtransfer/fragments/BeneficiaryFundTransferBaseFragment;", "Lco/yap/sendMoney/fundtransfer/interfaces/IInternationalFundsTransfer$ViewModel;", "Lco/yap/sendMoney/fundtransfer/interfaces/IInternationalFundsTransfer$View;", "()V", "clickEvent", "Landroidx/lifecycle/Observer;", "", "getClickEvent", "()Landroidx/lifecycle/Observer;", "viewModel", "Lco/yap/sendMoney/fundtransfer/viewmodels/InternationalFundsTransferViewModel;", "getViewModel", "()Lco/yap/sendMoney/fundtransfer/viewmodels/InternationalFundsTransferViewModel;", "checkOnTextChangeValidation", "", "getBindingVariable", "getBindings", "Lco/yap/sendmoney/databinding/FragmentInternationalFundsTransferBinding;", "getLayoutId", "getProductCode", "", "handleFxRateResponse", "it", "Lco/yap/networking/transactions/responsedtos/transaction/FxRateResponse$Data;", "isBalanceAvailable", "", "isDailyLimitReached", "moveToConfirmTransferScreen", "onActivityCreated", "savedInstanceState", "Landroid/os/Bundle;", "onCreate", "onDestroyView", "onPause", "onResume", "onViewCreated", "view", "Landroid/view/View;", "setEditTextWatcher", "setLowerAndUpperLimitError", "setObservers", "setSpannableFee", "feeAmount", "setupPOP", "purposeCategories", "", "", "Lco/yap/networking/transactions/responsedtos/purposepayment/PurposeOfPayment;", "showBalanceNotAvailableError", "showLimitError", "startFlow", "sendmoney_release"})
public final class InternationalFundsTransferFragment extends co.yap.sendMoney.fundtransfer.fragments.BeneficiaryFundTransferBaseFragment<co.yap.sendMoney.fundtransfer.interfaces.IInternationalFundsTransfer.ViewModel> implements co.yap.sendMoney.fundtransfer.interfaces.IInternationalFundsTransfer.View {
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
    public co.yap.sendMoney.fundtransfer.viewmodels.InternationalFundsTransferViewModel getViewModel() {
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
    public void onActivityCreated(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setObservers() {
    }
    
    private final void handleFxRateResponse(co.yap.networking.transactions.responsedtos.transaction.FxRateResponse.Data it) {
    }
    
    private final void setSpannableFee(java.lang.String feeAmount) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.Observer<java.lang.Integer> getClickEvent() {
        return null;
    }
    
    private final void setupPOP(java.util.Map<java.lang.String, ? extends java.util.List<co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment>> purposeCategories) {
    }
    
    private final boolean isBalanceAvailable() {
        return false;
    }
    
    private final void startFlow() {
    }
    
    private final void setLowerAndUpperLimitError() {
    }
    
    private final void showBalanceNotAvailableError() {
    }
    
    private final void showLimitError() {
    }
    
    private final boolean isDailyLimitReached() {
        return false;
    }
    
    private final void moveToConfirmTransferScreen() {
    }
    
    private final java.lang.String getProductCode() {
        return null;
    }
    
    private final void setEditTextWatcher() {
    }
    
    private final void checkOnTextChangeValidation() {
    }
    
    @java.lang.Override()
    public void onResume() {
    }
    
    @java.lang.Override()
    public void onPause() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final co.yap.sendmoney.databinding.FragmentInternationalFundsTransferBinding getBindings() {
        return null;
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
    
    public InternationalFundsTransferFragment() {
        super();
    }
}