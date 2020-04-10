package co.yap.sendMoney.fundtransfer.viewmodels;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u00032\b\u0012\u0004\u0012\u00020\u00050\u0004B\r\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\b\u0010 \u001a\u00020\u001cH\u0016R\u0014\u0010\t\u001a\u00020\nX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\nX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\f\"\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u00020\u0015X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\nX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\f\"\u0004\b\u001a\u0010\u0010\u00a8\u0006!"}, d2 = {"Lco/yap/sendMoney/fundtransfer/viewmodels/TransferSuccessViewModel;", "Lco/yap/sendMoney/fundtransfer/viewmodels/BeneficiaryFundTransferBaseViewModel;", "Lco/yap/sendMoney/fundtransfer/interfaces/ITransferSuccess$State;", "Lco/yap/sendMoney/fundtransfer/interfaces/ITransferSuccess$ViewModel;", "Lco/yap/networking/interfaces/IRepositoryHolder;", "Lco/yap/networking/customers/CustomersRepository;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "backButtonPressEvent", "Lco/yap/yapcore/SingleClickEvent;", "getBackButtonPressEvent", "()Lco/yap/yapcore/SingleClickEvent;", "clickEvent", "getClickEvent", "setClickEvent", "(Lco/yap/yapcore/SingleClickEvent;)V", "repository", "getRepository", "()Lco/yap/networking/customers/CustomersRepository;", "state", "Lco/yap/sendMoney/fundtransfer/states/TransferSuccessState;", "getState", "()Lco/yap/sendMoney/fundtransfer/states/TransferSuccessState;", "updatedCardBalanceEvent", "getUpdatedCardBalanceEvent", "setUpdatedCardBalanceEvent", "getAccountBalanceRequest", "", "handlePressOnButtonClick", "id", "", "onResume", "sendmoney_debug"})
public final class TransferSuccessViewModel extends co.yap.sendMoney.fundtransfer.viewmodels.BeneficiaryFundTransferBaseViewModel<co.yap.sendMoney.fundtransfer.interfaces.ITransferSuccess.State> implements co.yap.sendMoney.fundtransfer.interfaces.ITransferSuccess.ViewModel, co.yap.networking.interfaces.IRepositoryHolder<co.yap.networking.customers.CustomersRepository> {
    @org.jetbrains.annotations.NotNull()
    private final co.yap.networking.customers.CustomersRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.sendMoney.fundtransfer.states.TransferSuccessState state = null;
    @org.jetbrains.annotations.NotNull()
    private co.yap.yapcore.SingleClickEvent clickEvent;
    @org.jetbrains.annotations.NotNull()
    private co.yap.yapcore.SingleClickEvent updatedCardBalanceEvent;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.yapcore.SingleClickEvent backButtonPressEvent = null;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.networking.customers.CustomersRepository getRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.sendMoney.fundtransfer.states.TransferSuccessState getState() {
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
    public co.yap.yapcore.SingleClickEvent getUpdatedCardBalanceEvent() {
        return null;
    }
    
    @java.lang.Override()
    public void setUpdatedCardBalanceEvent(@org.jetbrains.annotations.NotNull()
    co.yap.yapcore.SingleClickEvent p0) {
    }
    
    @java.lang.Override()
    public void handlePressOnButtonClick(int id) {
    }
    
    @java.lang.Override()
    public void getAccountBalanceRequest() {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.yapcore.SingleClickEvent getBackButtonPressEvent() {
        return null;
    }
    
    @java.lang.Override()
    public void onResume() {
    }
    
    public TransferSuccessViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
}