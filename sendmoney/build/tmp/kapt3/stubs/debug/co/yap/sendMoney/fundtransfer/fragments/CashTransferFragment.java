package co.yap.sendMoney.fundtransfer.fragments;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003:\u00016B\u0005\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\b\u0010\u0010\u001a\u00020\u0007H\u0016J\u0006\u0010\u0011\u001a\u00020\u0012J\b\u0010\u0013\u001a\u00020\u0007H\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010\u0018\u001a\u00020\u0017H\u0002J\b\u0010\u0019\u001a\u00020\u000fH\u0002J\u0012\u0010\u001a\u001a\u00020\u000f2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u000fH\u0016J\b\u0010\u001e\u001a\u00020\u000fH\u0016J\u001a\u0010\u001f\u001a\u00020\u000f2\u0006\u0010 \u001a\u00020!2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J\b\u0010\"\u001a\u00020\u000fH\u0002J\b\u0010#\u001a\u00020\u000fH\u0016J\u0012\u0010$\u001a\u00020\u000f2\b\u0010%\u001a\u0004\u0018\u00010\u0015H\u0002J$\u0010&\u001a\u00020\u000f2\u001a\u0010\'\u001a\u0016\u0012\u0004\u0012\u00020)\u0018\u00010(j\n\u0012\u0004\u0012\u00020)\u0018\u0001`*H\u0002J&\u0010+\u001a\u00020\u000f2\u001c\u0010,\u001a\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0012\n\u0012\b\u0012\u0004\u0012\u00020/0.\u0018\u00010-H\u0002J\b\u00100\u001a\u00020\u000fH\u0002J\b\u00101\u001a\u00020\u000fH\u0002J\b\u00102\u001a\u00020\u000fH\u0002J\u0010\u00103\u001a\u00020\u000f2\u0006\u00104\u001a\u00020\u0015H\u0002J\b\u00105\u001a\u00020\u000fH\u0002R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\r\u00a8\u00067"}, d2 = {"Lco/yap/sendMoney/fundtransfer/fragments/CashTransferFragment;", "Lco/yap/sendMoney/fundtransfer/fragments/BeneficiaryFundTransferBaseFragment;", "Lco/yap/sendMoney/fundtransfer/interfaces/ICashTransfer$ViewModel;", "Lco/yap/sendMoney/fundtransfer/interfaces/ICashTransfer$View;", "()V", "clickEvent", "Landroidx/lifecycle/Observer;", "", "getClickEvent", "()Landroidx/lifecycle/Observer;", "viewModel", "Lco/yap/sendMoney/fundtransfer/viewmodels/CashTransferViewModel;", "getViewModel", "()Lco/yap/sendMoney/fundtransfer/viewmodels/CashTransferViewModel;", "checkOnTextChangeValidation", "", "getBindingVariable", "getBindings", "Lco/yap/sendmoney/databinding/FragmentCashTransferBinding;", "getLayoutId", "getProductCode", "", "isBalanceAvailable", "", "isDailyLimitReached", "moveToConfirmationScreen", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onPause", "onViewCreated", "view", "Landroid/view/View;", "setEditTextWatcher", "setObservers", "setSpannableFee", "totalFeeAmount", "setSpinnerAdapter", "list", "Ljava/util/ArrayList;", "Lco/yap/networking/transactions/responsedtos/InternationalFundsTransferReasonList$ReasonList;", "Lkotlin/collections/ArrayList;", "setupPOP", "purposeCategories", "", "", "Lco/yap/networking/transactions/responsedtos/purposepayment/PurposeOfPayment;", "showBalanceNotAvailableError", "showLimitError", "skipCashTransferFragment", "startFlows", "productCode", "startOtpFragment", "ReasonDropDownViewHolder", "sendmoney_debug"})
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
    
    private final void setSpinnerAdapter(java.util.ArrayList<co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList.ReasonList> list) {
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
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eR\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n\u00a8\u0006\u0010"}, d2 = {"Lco/yap/sendMoney/fundtransfer/fragments/CashTransferFragment$ReasonDropDownViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "view", "Landroid/view/View;", "(Landroid/view/View;)V", "title", "Landroid/widget/TextView;", "getTitle", "()Landroid/widget/TextView;", "setTitle", "(Landroid/widget/TextView;)V", "bind", "", "reason", "Lco/yap/networking/transactions/responsedtos/InternationalFundsTransferReasonList$ReasonList;", "Companion", "sendmoney_debug"})
    public static final class ReasonDropDownViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private android.widget.TextView title;
        public static final co.yap.sendMoney.fundtransfer.fragments.CashTransferFragment.ReasonDropDownViewHolder.Companion Companion = null;
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getTitle() {
            return null;
        }
        
        public final void setTitle(@org.jetbrains.annotations.NotNull()
        android.widget.TextView p0) {
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList.ReasonList reason) {
        }
        
        public ReasonDropDownViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.View view) {
            super(null);
        }
        
        @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\b"}, d2 = {"Lco/yap/sendMoney/fundtransfer/fragments/CashTransferFragment$ReasonDropDownViewHolder$Companion;", "", "()V", "inflate", "Lco/yap/sendMoney/fundtransfer/fragments/CashTransferFragment$ReasonDropDownViewHolder;", "parent", "Landroid/view/ViewGroup;", "inflateSelectedView", "sendmoney_debug"})
        public static final class Companion {
            
            @org.jetbrains.annotations.NotNull()
            public final co.yap.sendMoney.fundtransfer.fragments.CashTransferFragment.ReasonDropDownViewHolder inflate(@org.jetbrains.annotations.NotNull()
            android.view.ViewGroup parent) {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final co.yap.sendMoney.fundtransfer.fragments.CashTransferFragment.ReasonDropDownViewHolder inflateSelectedView(@org.jetbrains.annotations.NotNull()
            android.view.ViewGroup parent) {
                return null;
            }
            
            private Companion() {
                super();
            }
        }
    }
}