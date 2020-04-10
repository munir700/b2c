package co.yap.sendMoney.fundtransfer.viewmodels;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u000f\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0017\u00105\u001a\u0002062\b\u00107\u001a\u0004\u0018\u00010!H\u0016\u00a2\u0006\u0002\u00108J\u0012\u00109\u001a\u0002062\b\b\u0002\u0010:\u001a\u00020!H\u0002J\b\u0010;\u001a\u000206H\u0016J\u0012\u0010<\u001a\u0002062\b\u0010=\u001a\u0004\u0018\u00010\u0011H\u0016J\u0010\u0010>\u001a\u0002062\u0006\u0010=\u001a\u00020\u0011H\u0016J\u0006\u0010?\u001a\u00020@J\b\u0010A\u001a\u000206H\u0016J\u0010\u0010B\u001a\u0002062\u0006\u0010:\u001a\u00020!H\u0016J\b\u0010C\u001a\u00020DH\u0002J\b\u0010E\u001a\u00020DH\u0002J\b\u0010F\u001a\u00020DH\u0002J\u0006\u0010G\u001a\u00020DJ\b\u0010H\u001a\u000206H\u0016J\b\u0010I\u001a\u000206H\u0016J\b\u0010J\u001a\u000206H\u0016J \u0010K\u001a\u0002062\u0016\u0010L\u001a\u0012\u0012\u0004\u0012\u00020\u00130\u001aj\b\u0012\u0004\u0012\u00020\u0013`\u001bH\u0016J\u0017\u0010M\u001a\u0002062\b\u0010N\u001a\u0004\u0018\u00010@H\u0002\u00a2\u0006\u0002\u0010OJ\b\u0010P\u001a\u000206H\u0002J\u0006\u0010Q\u001a\u00020DJ\u0006\u0010R\u001a\u000206R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\bX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\nR0\u0010\u000f\u001a\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u0012\u0018\u00010\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R0\u0010\u0018\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u00130\u001aj\b\u0012\u0004\u0012\u00020\u0013`\u001b0\u0019X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001a\u0010 \u001a\u00020!X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001a\u0010&\u001a\u00020\u0011X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\'\u0010(\"\u0004\b)\u0010*R\u0014\u0010+\u001a\u00020,X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010.R0\u0010/\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u0002000\u001aj\b\u0012\u0004\u0012\u000200`\u001b0\u0019X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b1\u0010\u001d\"\u0004\b2\u0010\u001fR\u000e\u00103\u001a\u000204X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006S"}, d2 = {"Lco/yap/sendMoney/fundtransfer/viewmodels/CashTransferViewModel;", "Lco/yap/sendMoney/fundtransfer/viewmodels/BeneficiaryFundTransferBaseViewModel;", "Lco/yap/sendMoney/fundtransfer/interfaces/ICashTransfer$State;", "Lco/yap/sendMoney/fundtransfer/interfaces/ICashTransfer$ViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "customersRepository", "Lco/yap/networking/customers/CustomersRepository;", "errorEvent", "getErrorEvent", "purposeCategories", "", "", "", "Lco/yap/networking/transactions/responsedtos/purposepayment/PurposeOfPayment;", "getPurposeCategories", "()Ljava/util/Map;", "setPurposeCategories", "(Ljava/util/Map;)V", "purposeOfPaymentList", "Landroidx/lifecycle/MutableLiveData;", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "getPurposeOfPaymentList", "()Landroidx/lifecycle/MutableLiveData;", "setPurposeOfPaymentList", "(Landroidx/lifecycle/MutableLiveData;)V", "reasonPosition", "", "getReasonPosition", "()I", "setReasonPosition", "(I)V", "receiverUUID", "getReceiverUUID", "()Ljava/lang/String;", "setReceiverUUID", "(Ljava/lang/String;)V", "state", "Lco/yap/sendMoney/fundtransfer/states/CashTransferState;", "getState", "()Lco/yap/sendMoney/fundtransfer/states/CashTransferState;", "transactionData", "Lco/yap/networking/transactions/responsedtos/InternationalFundsTransferReasonList$ReasonList;", "getTransactionData", "setTransactionData", "transactionRepository", "Lco/yap/networking/transactions/TransactionsRepository;", "cashPayoutTransferRequest", "", "beneficiaryId", "(Ljava/lang/Integer;)V", "createOtp", "id", "getCountryLimit", "getMoneyTransferLimits", "productCode", "getPurposeOfPayment", "getTotalAmountWithFee", "", "getTransactionThresholds", "handlePressOnView", "isDailyLimitReached", "", "isOnlyUAEFTS", "isOtpRequired", "isUaeftsBeneficiary", "onCreate", "onResume", "proceedToTransferAmount", "processPurposeList", "list", "setMaxMinLimits", "limit", "(Ljava/lang/Double;)V", "setToolbarData", "shouldFeeApply", "updateFees", "sendmoney_release"})
public final class CashTransferViewModel extends co.yap.sendMoney.fundtransfer.viewmodels.BeneficiaryFundTransferBaseViewModel<co.yap.sendMoney.fundtransfer.interfaces.ICashTransfer.State> implements co.yap.sendMoney.fundtransfer.interfaces.ICashTransfer.ViewModel {
    private final co.yap.networking.transactions.TransactionsRepository transactionRepository = null;
    private final co.yap.networking.customers.CustomersRepository customersRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.sendMoney.fundtransfer.states.CashTransferState state = null;
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
    @org.jetbrains.annotations.Nullable()
    private java.util.Map<java.lang.String, ? extends java.util.List<co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment>> purposeCategories;
    private int reasonPosition;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.sendMoney.fundtransfer.states.CashTransferState getState() {
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
    
    public final double getTotalAmountWithFee() {
        return 0.0;
    }
    
    @java.lang.Override()
    public void processPurposeList(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment> list) {
    }
    
    public final boolean isUaeftsBeneficiary() {
        return false;
    }
    
    private final boolean isOnlyUAEFTS() {
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