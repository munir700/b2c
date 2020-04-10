package co.yap.sendMoney.fundtransfer.viewmodels;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\n\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0002J\u0010\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u0010\u000b\u001a\u00020\rH\u0002J\b\u0010\u001c\u001a\u00020\u0016H\u0016J\b\u0010\u001d\u001a\u00020\u0016H\u0016J\b\u0010\u001e\u001a\u00020\u0016H\u0016J\u0012\u0010\u001f\u001a\u00020\u00162\b\u0010 \u001a\u0004\u0018\u00010\u0018H\u0016J\u0012\u0010!\u001a\u00020\u00162\b\u0010 \u001a\u0004\u0018\u00010\u0018H\u0016R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\u00020\u0012X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\""}, d2 = {"Lco/yap/sendMoney/fundtransfer/viewmodels/InternationalTransactionConfirmationViewModel;", "Lco/yap/sendMoney/fundtransfer/viewmodels/BeneficiaryFundTransferBaseViewModel;", "Lco/yap/sendMoney/fundtransfer/interfaces/IInternationalTransactionConfirmation$State;", "Lco/yap/sendMoney/fundtransfer/interfaces/IInternationalTransactionConfirmation$ViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "isOtpRequired", "Landroidx/lifecycle/MutableLiveData;", "", "()Landroidx/lifecycle/MutableLiveData;", "mTransactionsRepository", "Lco/yap/networking/transactions/TransactionsRepository;", "state", "Lco/yap/sendMoney/fundtransfer/states/InternationalTransactionConfirmationState;", "getState", "()Lco/yap/sendMoney/fundtransfer/states/InternationalTransactionConfirmationState;", "getCutOffTimeConfiguration", "", "getProductCode", "", "handlePressOnButtonClick", "id", "", "onCreate", "proceedToTransferAmount", "requestForTransfer", "rmtTransferRequest", "beneficiaryId", "swiftTransferRequest", "sendmoney_debug"})
public final class InternationalTransactionConfirmationViewModel extends co.yap.sendMoney.fundtransfer.viewmodels.BeneficiaryFundTransferBaseViewModel<co.yap.sendMoney.fundtransfer.interfaces.IInternationalTransactionConfirmation.State> implements co.yap.sendMoney.fundtransfer.interfaces.IInternationalTransactionConfirmation.ViewModel {
    private co.yap.networking.transactions.TransactionsRepository mTransactionsRepository;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.sendMoney.fundtransfer.states.InternationalTransactionConfirmationState state = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.Boolean> isOtpRequired = null;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.yapcore.SingleClickEvent clickEvent = null;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.sendMoney.fundtransfer.states.InternationalTransactionConfirmationState getState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.lang.Boolean> isOtpRequired() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.yapcore.SingleClickEvent getClickEvent() {
        return null;
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @java.lang.Override()
    public void handlePressOnButtonClick(int id) {
    }
    
    @java.lang.Override()
    public void rmtTransferRequest(@org.jetbrains.annotations.Nullable()
    java.lang.String beneficiaryId) {
    }
    
    @java.lang.Override()
    public void swiftTransferRequest(@org.jetbrains.annotations.Nullable()
    java.lang.String beneficiaryId) {
    }
    
    @java.lang.Override()
    public void requestForTransfer() {
    }
    
    private final boolean isOtpRequired() {
        return false;
    }
    
    @java.lang.Override()
    public void proceedToTransferAmount() {
    }
    
    @java.lang.Override()
    public void getCutOffTimeConfiguration() {
    }
    
    private final java.lang.String getProductCode() {
        return null;
    }
    
    public InternationalTransactionConfirmationViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
}