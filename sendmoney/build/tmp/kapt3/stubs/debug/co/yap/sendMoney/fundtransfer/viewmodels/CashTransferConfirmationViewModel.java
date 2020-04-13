package co.yap.sendmoney.fundtransfer.viewmodels;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u00032\b\u0012\u0004\u0012\u00020\u00050\u0004B\r\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0012\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u0017H\u0016J\b\u0010\u001b\u001a\u00020\u0019H\u0002J\u0010\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\b\u0010\u001f\u001a\u00020\u0017H\u0016J\b\u0010 \u001a\u00020\u0017H\u0016J\u0012\u0010!\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016R\u001a\u0010\t\u001a\u00020\nX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u0013X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006\""}, d2 = {"Lco/yap/sendmoney/fundtransfer/viewmodels/CashTransferConfirmationViewModel;", "Lco/yap/sendmoney/fundtransfer/viewmodels/BeneficiaryFundTransferBaseViewModel;", "Lco/yap/sendmoney/fundtransfer/interfaces/ICashTransferConfirmation$State;", "Lco/yap/sendmoney/fundtransfer/interfaces/ICashTransferConfirmation$ViewModel;", "Lco/yap/networking/interfaces/IRepositoryHolder;", "Lco/yap/networking/transactions/TransactionsRepository;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "setClickEvent", "(Lco/yap/yapcore/SingleClickEvent;)V", "repository", "getRepository", "()Lco/yap/networking/transactions/TransactionsRepository;", "state", "Lco/yap/sendmoney/fundtransfer/states/CashTransferConfirmationState;", "getState", "()Lco/yap/sendmoney/fundtransfer/states/CashTransferConfirmationState;", "domesticTransferRequest", "", "beneficiaryId", "", "getCutOffTimeConfiguration", "getProductCode", "handlePressOnView", "id", "", "onCreate", "proceedToTransferAmount", "uaeftsTransferRequest", "sendmoney_debug"})
public final class CashTransferConfirmationViewModel extends co.yap.sendmoney.fundtransfer.viewmodels.BeneficiaryFundTransferBaseViewModel<co.yap.sendmoney.fundtransfer.interfaces.ICashTransferConfirmation.State> implements co.yap.sendmoney.fundtransfer.interfaces.ICashTransferConfirmation.ViewModel, co.yap.networking.interfaces.IRepositoryHolder<co.yap.networking.transactions.TransactionsRepository> {
    @org.jetbrains.annotations.NotNull()
    private final co.yap.networking.transactions.TransactionsRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.sendmoney.fundtransfer.states.CashTransferConfirmationState state = null;
    @org.jetbrains.annotations.NotNull()
    private co.yap.yapcore.SingleClickEvent clickEvent;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.networking.transactions.TransactionsRepository getRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.sendmoney.fundtransfer.states.CashTransferConfirmationState getState() {
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
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @java.lang.Override()
    public void handlePressOnView(int id) {
    }
    
    @java.lang.Override()
    public void proceedToTransferAmount() {
    }
    
    @java.lang.Override()
    public void getCutOffTimeConfiguration() {
    }
    
    @java.lang.Override()
    public void uaeftsTransferRequest(@org.jetbrains.annotations.Nullable()
    java.lang.String beneficiaryId) {
    }
    
    @java.lang.Override()
    public void domesticTransferRequest(@org.jetbrains.annotations.Nullable()
    java.lang.String beneficiaryId) {
    }
    
    private final java.lang.String getProductCode() {
        return null;
    }
    
    public CashTransferConfirmationViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
}