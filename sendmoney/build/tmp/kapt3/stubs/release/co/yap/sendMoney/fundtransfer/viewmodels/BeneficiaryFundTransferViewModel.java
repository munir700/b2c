package co.yap.sendMoney.fundtransfer.viewmodels;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+H\u0016J\b\u0010,\u001a\u00020)H\u0016R \u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000fX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R \u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\bX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u000b\"\u0004\b\u0015\u0010\rR\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u0014\u0010\u001c\u001a\u00020\u001dX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR \u0010 \u001a\b\u0012\u0004\u0012\u00020!0\bX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u000b\"\u0004\b#\u0010\rR \u0010$\u001a\b\u0012\u0004\u0012\u00020%0\bX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u000b\"\u0004\b\'\u0010\r\u00a8\u0006-"}, d2 = {"Lco/yap/sendMoney/fundtransfer/viewmodels/BeneficiaryFundTransferViewModel;", "Lco/yap/yapcore/BaseViewModel;", "Lco/yap/sendMoney/fundtransfer/interfaces/IBeneficiaryFundTransfer$State;", "Lco/yap/sendMoney/fundtransfer/interfaces/IBeneficiaryFundTransfer$ViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "beneficiary", "Landroidx/lifecycle/MutableLiveData;", "Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;", "getBeneficiary", "()Landroidx/lifecycle/MutableLiveData;", "setBeneficiary", "(Landroidx/lifecycle/MutableLiveData;)V", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "errorEvent", "", "getErrorEvent", "setErrorEvent", "selectedPop", "Lco/yap/networking/transactions/responsedtos/purposepayment/PurposeOfPayment;", "getSelectedPop", "()Lco/yap/networking/transactions/responsedtos/purposepayment/PurposeOfPayment;", "setSelectedPop", "(Lco/yap/networking/transactions/responsedtos/purposepayment/PurposeOfPayment;)V", "state", "Lco/yap/sendMoney/fundtransfer/states/BeneficiaryFundTransferState;", "getState", "()Lco/yap/sendMoney/fundtransfer/states/BeneficiaryFundTransferState;", "transactionThreshold", "Lco/yap/networking/transactions/responsedtos/TransactionThresholdModel;", "getTransactionThreshold", "setTransactionThreshold", "transferData", "Lco/yap/sendMoney/fundtransfer/models/TransferFundData;", "getTransferData", "setTransferData", "handlePressOnView", "", "id", "", "onCreate", "sendmoney_release"})
public final class BeneficiaryFundTransferViewModel extends co.yap.yapcore.BaseViewModel<co.yap.sendMoney.fundtransfer.interfaces.IBeneficiaryFundTransfer.State> implements co.yap.sendMoney.fundtransfer.interfaces.IBeneficiaryFundTransfer.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final co.yap.sendMoney.fundtransfer.states.BeneficiaryFundTransferState state = null;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.yapcore.SingleClickEvent clickEvent = null;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.String> errorEvent;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<co.yap.networking.customers.responsedtos.sendmoney.Beneficiary> beneficiary;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<co.yap.sendMoney.fundtransfer.models.TransferFundData> transferData;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<co.yap.networking.transactions.responsedtos.TransactionThresholdModel> transactionThreshold;
    @org.jetbrains.annotations.Nullable()
    private co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment selectedPop;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.sendMoney.fundtransfer.states.BeneficiaryFundTransferState getState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.yapcore.SingleClickEvent getClickEvent() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.lang.String> getErrorEvent() {
        return null;
    }
    
    @java.lang.Override()
    public void setErrorEvent(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<co.yap.networking.customers.responsedtos.sendmoney.Beneficiary> getBeneficiary() {
        return null;
    }
    
    @java.lang.Override()
    public void setBeneficiary(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<co.yap.networking.customers.responsedtos.sendmoney.Beneficiary> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<co.yap.sendMoney.fundtransfer.models.TransferFundData> getTransferData() {
        return null;
    }
    
    @java.lang.Override()
    public void setTransferData(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<co.yap.sendMoney.fundtransfer.models.TransferFundData> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<co.yap.networking.transactions.responsedtos.TransactionThresholdModel> getTransactionThreshold() {
        return null;
    }
    
    @java.lang.Override()
    public void setTransactionThreshold(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<co.yap.networking.transactions.responsedtos.TransactionThresholdModel> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment getSelectedPop() {
        return null;
    }
    
    @java.lang.Override()
    public void setSelectedPop(@org.jetbrains.annotations.Nullable()
    co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment p0) {
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @java.lang.Override()
    public void handlePressOnView(int id) {
    }
    
    public BeneficiaryFundTransferViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
}