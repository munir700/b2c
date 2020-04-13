package co.yap.sendmoney.fundtransfer.viewmodels;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u0006\n\u0002\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0017\u0010C\u001a\u00020D2\b\u0010E\u001a\u0004\u0018\u00010/H\u0016\u00a2\u0006\u0002\u0010FJ\u0012\u0010G\u001a\u00020D2\b\b\u0002\u0010H\u001a\u00020/H\u0002J\b\u0010I\u001a\u00020DH\u0016J\n\u0010J\u001a\u0004\u0018\u00010\u0017H\u0002J\b\u0010K\u001a\u00020LH\u0002J\u0012\u0010M\u001a\u00020D2\b\u0010N\u001a\u0004\u0018\u00010\u0017H\u0016J\u0010\u0010O\u001a\u00020D2\u0006\u0010N\u001a\u00020\u0017H\u0016J\u0006\u0010P\u001a\u00020LJ\b\u0010Q\u001a\u00020DH\u0016J\u0012\u0010R\u001a\u00020D2\b\u0010N\u001a\u0004\u0018\u00010\u0017H\u0016J\u0010\u0010S\u001a\u00020D2\u0006\u0010H\u001a\u00020/H\u0016J\b\u0010T\u001a\u00020\u001eH\u0002J\b\u0010U\u001a\u00020\u001eH\u0002J\u0006\u0010V\u001a\u00020\u001eJ\b\u0010W\u001a\u00020DH\u0016J\b\u0010X\u001a\u00020DH\u0016J\b\u0010Y\u001a\u00020DH\u0016J \u0010Z\u001a\u00020D2\u0016\u0010[\u001a\u0012\u0012\u0004\u0012\u00020$0*j\b\u0012\u0004\u0012\u00020$`+H\u0016J\u0017\u0010\\\u001a\u00020D2\b\u0010]\u001a\u0004\u0018\u00010LH\u0002\u00a2\u0006\u0002\u0010^J\b\u0010_\u001a\u00020DH\u0002J\u0006\u0010`\u001a\u00020\u001eJ\u0006\u0010a\u001a\u00020DR\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\bX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\nR \u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0017X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR \u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001f\"\u0004\b \u0010!R0\u0010\"\u001a\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u0017\u0012\n\u0012\b\u0012\u0004\u0012\u00020$0\u0010\u0018\u00010#X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b\'\u0010(R0\u0010)\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020$0*j\b\u0012\u0004\u0012\u00020$`+0\u001dX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\u001f\"\u0004\b-\u0010!R\u001a\u0010.\u001a\u00020/X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\u001a\u00104\u001a\u00020\u0017X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b5\u0010\u0019\"\u0004\b6\u0010\u001bR\u0014\u00107\u001a\u000208X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b9\u0010:R0\u0010;\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020<0*j\b\u0012\u0004\u0012\u00020<`+0\u001dX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b=\u0010\u001f\"\u0004\b>\u0010!R\u000e\u0010?\u001a\u00020@X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010A\u001a\b\u0012\u0004\u0012\u00020\u00170\u001dX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bB\u0010\u001f\u00a8\u0006b"}, d2 = {"Lco/yap/sendmoney/fundtransfer/viewmodels/CashTransferViewModel;", "Lco/yap/sendmoney/fundtransfer/viewmodels/BeneficiaryFundTransferBaseViewModel;", "Lco/yap/sendmoney/fundtransfer/interfaces/ICashTransfer$State;", "Lco/yap/sendmoney/fundtransfer/interfaces/ICashTransfer$ViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "customersRepository", "Lco/yap/networking/customers/CustomersRepository;", "errorEvent", "getErrorEvent", "feeTiers", "", "Lco/yap/networking/transactions/responsedtos/transaction/RemittanceFeeResponse$RemittanceFee$TierRateDTO;", "getFeeTiers", "()Ljava/util/List;", "setFeeTiers", "(Ljava/util/List;)V", "feeType", "", "getFeeType", "()Ljava/lang/String;", "setFeeType", "(Ljava/lang/String;)V", "isAPIFailed", "Landroidx/lifecycle/MutableLiveData;", "", "()Landroidx/lifecycle/MutableLiveData;", "setAPIFailed", "(Landroidx/lifecycle/MutableLiveData;)V", "purposeCategories", "", "Lco/yap/networking/transactions/responsedtos/purposepayment/PurposeOfPayment;", "getPurposeCategories", "()Ljava/util/Map;", "setPurposeCategories", "(Ljava/util/Map;)V", "purposeOfPaymentList", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "getPurposeOfPaymentList", "setPurposeOfPaymentList", "reasonPosition", "", "getReasonPosition", "()I", "setReasonPosition", "(I)V", "receiverUUID", "getReceiverUUID", "setReceiverUUID", "state", "Lco/yap/sendmoney/fundtransfer/states/CashTransferState;", "getState", "()Lco/yap/sendmoney/fundtransfer/states/CashTransferState;", "transactionData", "Lco/yap/networking/transactions/responsedtos/InternationalFundsTransferReasonList$ReasonList;", "getTransactionData", "setTransactionData", "transactionRepository", "Lco/yap/networking/transactions/TransactionsRepository;", "updatedFee", "getUpdatedFee", "cashPayoutTransferRequest", "", "beneficiaryId", "(Ljava/lang/Integer;)V", "createOtp", "id", "getCountryLimit", "getFeeFromTier", "getFlatFee", "", "getMoneyTransferLimits", "productCode", "getPurposeOfPayment", "getTotalAmountWithFee", "getTransactionThresholds", "getTransferFees", "handlePressOnView", "isDailyLimitReached", "isOtpRequired", "isUaeftsBeneficiary", "onCreate", "onResume", "proceedToTransferAmount", "processPurposeList", "list", "setMaxMinLimits", "limit", "(Ljava/lang/Double;)V", "setToolbarData", "shouldFeeApply", "updateFees", "sendmoney_debug"})
public final class CashTransferViewModel extends co.yap.sendmoney.fundtransfer.viewmodels.BeneficiaryFundTransferBaseViewModel<co.yap.sendmoney.fundtransfer.interfaces.ICashTransfer.State> implements co.yap.sendmoney.fundtransfer.interfaces.ICashTransfer.ViewModel {
    private final co.yap.networking.transactions.TransactionsRepository transactionRepository = null;
    private final co.yap.networking.customers.CustomersRepository customersRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.sendmoney.fundtransfer.states.CashTransferState state = null;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.yapcore.SingleClickEvent clickEvent = null;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.yapcore.SingleClickEvent errorEvent = null;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.util.ArrayList<co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList.ReasonList>> transactionData;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String receiverUUID;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.util.ArrayList<co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment>> purposeOfPaymentList;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String feeType;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse.RemittanceFee.TierRateDTO> feeTiers;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Boolean> isAPIFailed;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.String> updatedFee = null;
    @org.jetbrains.annotations.Nullable()
    private java.util.Map<java.lang.String, ? extends java.util.List<co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment>> purposeCategories;
    private int reasonPosition;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.sendmoney.fundtransfer.states.CashTransferState getState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.yapcore.SingleClickEvent getClickEvent() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.yapcore.SingleClickEvent getErrorEvent() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.util.ArrayList<co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList.ReasonList>> getTransactionData() {
        return null;
    }
    
    @java.lang.Override()
    public void setTransactionData(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.util.ArrayList<co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList.ReasonList>> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String getReceiverUUID() {
        return null;
    }
    
    @java.lang.Override()
    public void setReceiverUUID(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.util.ArrayList<co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment>> getPurposeOfPaymentList() {
        return null;
    }
    
    @java.lang.Override()
    public void setPurposeOfPaymentList(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.util.ArrayList<co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment>> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String getFeeType() {
        return null;
    }
    
    @java.lang.Override()
    public void setFeeType(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.util.List<co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse.RemittanceFee.TierRateDTO> getFeeTiers() {
        return null;
    }
    
    @java.lang.Override()
    public void setFeeTiers(@org.jetbrains.annotations.NotNull()
    java.util.List<co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse.RemittanceFee.TierRateDTO> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.lang.Boolean> isAPIFailed() {
        return null;
    }
    
    @java.lang.Override()
    public void setAPIFailed(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Boolean> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.lang.String> getUpdatedFee() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.Map<java.lang.String, java.util.List<co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment>> getPurposeCategories() {
        return null;
    }
    
    public final void setPurposeCategories(@org.jetbrains.annotations.Nullable()
    java.util.Map<java.lang.String, ? extends java.util.List<co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment>> p0) {
    }
    
    @java.lang.Override()
    public int getReasonPosition() {
        return 0;
    }
    
    @java.lang.Override()
    public void setReasonPosition(int p0) {
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @java.lang.Override()
    public void onResume() {
    }
    
    private final void setToolbarData() {
    }
    
    public final void updateFees() {
    }
    
    @java.lang.Override()
    public void handlePressOnView(int id) {
    }
    
    private final boolean isDailyLimitReached() {
        return false;
    }
    
    private final boolean isOtpRequired() {
        return false;
    }
    
    @java.lang.Override()
    public void proceedToTransferAmount() {
    }
    
    private final void createOtp(int id) {
    }
    
    @java.lang.Override()
    public void getPurposeOfPayment(@org.jetbrains.annotations.NotNull()
    java.lang.String productCode) {
    }
    
    @java.lang.Override()
    public void cashPayoutTransferRequest(@org.jetbrains.annotations.Nullable()
    java.lang.Integer beneficiaryId) {
    }
    
    @java.lang.Override()
    public void getTransferFees(@org.jetbrains.annotations.Nullable()
    java.lang.String productCode) {
    }
    
    @java.lang.Override()
    public void getMoneyTransferLimits(@org.jetbrains.annotations.Nullable()
    java.lang.String productCode) {
    }
    
    @java.lang.Override()
    public void getCountryLimit() {
    }
    
    @java.lang.Override()
    public void getTransactionThresholds() {
    }
    
    private final void setMaxMinLimits(java.lang.Double limit) {
    }
    
    private final java.lang.String getFeeFromTier() {
        return null;
    }
    
    public final double getTotalAmountWithFee() {
        return 0.0;
    }
    
    private final double getFlatFee() {
        return 0.0;
    }
    
    @java.lang.Override()
    public void processPurposeList(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment> list) {
    }
    
    public final boolean isUaeftsBeneficiary() {
        return false;
    }
    
    public final boolean shouldFeeApply() {
        return false;
    }
    
    public CashTransferViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
}