package co.yap.sendMoney.fundtransfer.viewmodels;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u000b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u00032\b\u0012\u0004\u0012\u00020\u00050\u0004B\r\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\b\u00102\u001a\u000203H\u0016J\u0012\u00104\u001a\u0002032\b\u00105\u001a\u0004\u0018\u00010\u001aH\u0016J\u0010\u00106\u001a\u0002032\u0006\u00105\u001a\u00020\u001aH\u0016J\u0006\u00107\u001a\u000208J\u0012\u00109\u001a\u0002032\b\u00105\u001a\u0004\u0018\u00010\u001aH\u0016J\b\u0010:\u001a\u000203H\u0016J\u0010\u0010;\u001a\u0002032\u0006\u0010<\u001a\u00020&H\u0016J\b\u0010=\u001a\u000203H\u0016J\b\u0010>\u001a\u000203H\u0016J\u0016\u0010?\u001a\u0002032\f\u0010@\u001a\b\u0012\u0004\u0012\u00020\u001c0\"H\u0016J\u0006\u0010A\u001a\u000203J\u0006\u0010B\u001a\u000203R\u001a\u0010\t\u001a\u00020\nX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR \u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R0\u0010\u0018\u001a\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\u001b\u0018\u00010\u0019X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R&\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\"0\u0010X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u0013\"\u0004\b$\u0010\u0015R\u001a\u0010%\u001a\u00020&X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\'\u0010(\"\u0004\b)\u0010*R\u0014\u0010+\u001a\u00020\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u0014\u0010.\u001a\u00020/X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u00101\u00a8\u0006C"}, d2 = {"Lco/yap/sendMoney/fundtransfer/viewmodels/InternationalFundsTransferViewModel;", "Lco/yap/sendMoney/fundtransfer/viewmodels/BeneficiaryFundTransferBaseViewModel;", "Lco/yap/sendMoney/fundtransfer/interfaces/IInternationalFundsTransfer$State;", "Lco/yap/sendMoney/fundtransfer/interfaces/IInternationalFundsTransfer$ViewModel;", "Lco/yap/networking/interfaces/IRepositoryHolder;", "Lco/yap/networking/customers/CustomersRepository;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "setClickEvent", "(Lco/yap/yapcore/SingleClickEvent;)V", "fxRateResponse", "Landroidx/lifecycle/MutableLiveData;", "Lco/yap/networking/transactions/responsedtos/transaction/FxRateResponse$Data;", "getFxRateResponse", "()Landroidx/lifecycle/MutableLiveData;", "setFxRateResponse", "(Landroidx/lifecycle/MutableLiveData;)V", "mTransactionsRepository", "Lco/yap/networking/transactions/TransactionsRepository;", "purposeCategories", "", "", "", "Lco/yap/networking/transactions/responsedtos/purposepayment/PurposeOfPayment;", "getPurposeCategories", "()Ljava/util/Map;", "setPurposeCategories", "(Ljava/util/Map;)V", "purposeOfPaymentList", "Ljava/util/ArrayList;", "getPurposeOfPaymentList", "setPurposeOfPaymentList", "reasonPosition", "", "getReasonPosition", "()I", "setReasonPosition", "(I)V", "repository", "getRepository", "()Lco/yap/networking/customers/CustomersRepository;", "state", "Lco/yap/sendMoney/fundtransfer/states/InternationalFundsTransferState;", "getState", "()Lco/yap/sendMoney/fundtransfer/states/InternationalFundsTransferState;", "getCountryLimits", "", "getMoneyTransferLimits", "productCode", "getReasonList", "getTotalAmountWithFee", "", "getTransactionInternationalfxList", "getTransactionThresholds", "handlePressOnButton", "id", "onCreate", "onResume", "processPurposeList", "list", "setDestinationAmount", "updateFees", "sendmoney_release"})
public final class InternationalFundsTransferViewModel extends co.yap.sendMoney.fundtransfer.viewmodels.BeneficiaryFundTransferBaseViewModel<co.yap.sendMoney.fundtransfer.interfaces.IInternationalFundsTransfer.State> implements co.yap.sendMoney.fundtransfer.interfaces.IInternationalFundsTransfer.ViewModel, co.yap.networking.interfaces.IRepositoryHolder<co.yap.networking.customers.CustomersRepository> {
    private co.yap.networking.transactions.TransactionsRepository mTransactionsRepository;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.networking.customers.CustomersRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.sendMoney.fundtransfer.states.InternationalFundsTransferState state = null;
    @org.jetbrains.annotations.NotNull()
    private co.yap.yapcore.SingleClickEvent clickEvent;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.util.ArrayList<co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment>> purposeOfPaymentList;
    private int reasonPosition;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<co.yap.networking.transactions.responsedtos.transaction.FxRateResponse.Data> fxRateResponse;
    @org.jetbrains.annotations.Nullable()
    private java.util.Map<java.lang.String, ? extends java.util.List<co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment>> purposeCategories;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.networking.customers.CustomersRepository getRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.sendMoney.fundtransfer.states.InternationalFundsTransferState getState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.yapcore.SingleClickEvent getClickEvent() {
        return null;
    }
    
    @java.lang.Override()
    public void setClickEvent(@org.jetbrains.annotations.NotNull()
    co.yap.yapcore.SingleClickEvent p0) {
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
    
    @java.lang.Override()
    public int getReasonPosition() {
        return 0;
    }
    
    @java.lang.Override()
    public void setReasonPosition(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<co.yap.networking.transactions.responsedtos.transaction.FxRateResponse.Data> getFxRateResponse() {
        return null;
    }
    
    @java.lang.Override()
    public void setFxRateResponse(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<co.yap.networking.transactions.responsedtos.transaction.FxRateResponse.Data> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.Map<java.lang.String, java.util.List<co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment>> getPurposeCategories() {
        return null;
    }
    
    public final void setPurposeCategories(@org.jetbrains.annotations.Nullable()
    java.util.Map<java.lang.String, ? extends java.util.List<co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment>> p0) {
    }
    
    @java.lang.Override()
    public void handlePressOnButton(int id) {
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @java.lang.Override()
    public void onResume() {
    }
    
    @java.lang.Override()
    public void getTransactionInternationalfxList(@org.jetbrains.annotations.Nullable()
    java.lang.String productCode) {
    }
    
    @java.lang.Override()
    public void getReasonList(@org.jetbrains.annotations.NotNull()
    java.lang.String productCode) {
    }
    
    @java.lang.Override()
    public void getMoneyTransferLimits(@org.jetbrains.annotations.Nullable()
    java.lang.String productCode) {
    }
    
    @java.lang.Override()
    public void getCountryLimits() {
    }
    
    @java.lang.Override()
    public void getTransactionThresholds() {
    }
    
    @java.lang.Override()
    public void processPurposeList(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment> list) {
    }
    
    public final void updateFees() {
    }
    
    public final double getTotalAmountWithFee() {
        return 0.0;
    }
    
    public final void setDestinationAmount() {
    }
    
    public InternationalFundsTransferViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
}