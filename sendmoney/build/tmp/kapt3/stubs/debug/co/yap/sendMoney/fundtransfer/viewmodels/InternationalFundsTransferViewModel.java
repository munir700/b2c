package co.yap.sendmoney.fundtransfer.viewmodels;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\t\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u00032\b\u0012\u0004\u0012\u00020\u00050\u0004B\r\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010=\u001a\u00020>H\u0016J\u0012\u0010?\u001a\u0004\u0018\u00010@2\b\u0010A\u001a\u0004\u0018\u00010@J\u0012\u0010B\u001a\u00020>2\b\u0010C\u001a\u0004\u0018\u00010@H\u0016J\u0012\u0010D\u001a\u00020>2\b\u0010C\u001a\u0004\u0018\u00010@H\u0016J\u0006\u0010E\u001a\u00020FJ\u0012\u0010G\u001a\u00020>2\b\u0010C\u001a\u0004\u0018\u00010@H\u0016J\u0012\u0010H\u001a\u00020>2\b\u0010C\u001a\u0004\u0018\u00010@H\u0016J\b\u0010I\u001a\u00020>H\u0016J\u0010\u0010J\u001a\u00020>2\u0006\u0010K\u001a\u00020(H\u0016J\b\u0010L\u001a\u00020>H\u0016J\b\u0010M\u001a\u00020>H\u0016J\u0006\u0010N\u001a\u00020>R\u001a\u0010\t\u001a\u00020\nX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR \u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R \u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR \u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u0017X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001a\"\u0004\b\u001f\u0010\u001cR\u000e\u0010 \u001a\u00020!X\u0082\u000e\u00a2\u0006\u0002\n\u0000R*\u0010\"\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020$0#j\b\u0012\u0004\u0012\u00020$`%0\u0017X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001aR\u001a\u0010\'\u001a\u00020(X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\u0014\u0010-\u001a\u00020\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u0014\u00100\u001a\u000201X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u00103R*\u00104\u001a\u0012\u0012\u0004\u0012\u00020$0#j\b\u0012\u0004\u0012\u00020$`%X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b5\u00106\"\u0004\b7\u00108R \u00109\u001a\b\u0012\u0004\u0012\u00020:0\u0017X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b;\u0010\u001a\"\u0004\b<\u0010\u001c\u00a8\u0006O"}, d2 = {"Lco/yap/sendmoney/fundtransfer/viewmodels/InternationalFundsTransferViewModel;", "Lco/yap/sendmoney/fundtransfer/viewmodels/BeneficiaryFundTransferBaseViewModel;", "Lco/yap/sendmoney/fundtransfer/interfaces/IInternationalFundsTransfer$State;", "Lco/yap/sendmoney/fundtransfer/interfaces/IInternationalFundsTransfer$ViewModel;", "Lco/yap/networking/interfaces/IRepositoryHolder;", "Lco/yap/networking/customers/CustomersRepository;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "setClickEvent", "(Lco/yap/yapcore/SingleClickEvent;)V", "feeTiers", "", "Lco/yap/networking/transactions/responsedtos/transaction/RemittanceFeeResponse$RemittanceFee$TierRateDTO;", "getFeeTiers", "()Ljava/util/List;", "setFeeTiers", "(Ljava/util/List;)V", "fxRateResponse", "Landroidx/lifecycle/MutableLiveData;", "Lco/yap/networking/transactions/responsedtos/transaction/FxRateResponse$Data;", "getFxRateResponse", "()Landroidx/lifecycle/MutableLiveData;", "setFxRateResponse", "(Landroidx/lifecycle/MutableLiveData;)V", "isAPIFailed", "", "setAPIFailed", "mTransactionsRepository", "Lco/yap/networking/transactions/TransactionsRepository;", "populateSpinnerData", "Ljava/util/ArrayList;", "Lco/yap/networking/transactions/responsedtos/InternationalFundsTransferReasonList$ReasonList;", "Lkotlin/collections/ArrayList;", "getPopulateSpinnerData", "reasonPosition", "", "getReasonPosition", "()I", "setReasonPosition", "(I)V", "repository", "getRepository", "()Lco/yap/networking/customers/CustomersRepository;", "state", "Lco/yap/sendmoney/fundtransfer/states/InternationalFundsTransferState;", "getState", "()Lco/yap/sendmoney/fundtransfer/states/InternationalFundsTransferState;", "transactionData", "getTransactionData", "()Ljava/util/ArrayList;", "setTransactionData", "(Ljava/util/ArrayList;)V", "transactionFeeResponse", "Lco/yap/networking/transactions/responsedtos/transaction/RemittanceFeeResponse$RemittanceFee;", "getTransactionFeeResponse", "setTransactionFeeResponse", "getCountryLimits", "", "getFeeFromTier", "", "enterAmount", "getMoneyTransferLimits", "productCode", "getReasonList", "getTotalAmountWithFee", "", "getTransactionFeeInternational", "getTransactionInternationalfxList", "getTransactionThresholds", "handlePressOnButton", "id", "onCreate", "onResume", "setDestinationAmount", "sendmoney_debug"})
public final class InternationalFundsTransferViewModel extends co.yap.sendmoney.fundtransfer.viewmodels.BeneficiaryFundTransferBaseViewModel<co.yap.sendmoney.fundtransfer.interfaces.IInternationalFundsTransfer.State> implements co.yap.sendmoney.fundtransfer.interfaces.IInternationalFundsTransfer.ViewModel, co.yap.networking.interfaces.IRepositoryHolder<co.yap.networking.customers.CustomersRepository> {
    private co.yap.networking.transactions.TransactionsRepository mTransactionsRepository;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.networking.customers.CustomersRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.sendmoney.fundtransfer.states.InternationalFundsTransferState state = null;
    @org.jetbrains.annotations.NotNull()
    private co.yap.yapcore.SingleClickEvent clickEvent;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Boolean> isAPIFailed;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList.ReasonList> transactionData;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.util.ArrayList<co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList.ReasonList>> populateSpinnerData = null;
    private int reasonPosition;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<co.yap.networking.transactions.responsedtos.transaction.FxRateResponse.Data> fxRateResponse;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse.RemittanceFee> transactionFeeResponse;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse.RemittanceFee.TierRateDTO> feeTiers;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.networking.customers.CustomersRepository getRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.sendmoney.fundtransfer.states.InternationalFundsTransferState getState() {
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
    public androidx.lifecycle.MutableLiveData<java.lang.Boolean> isAPIFailed() {
        return null;
    }
    
    @java.lang.Override()
    public void setAPIFailed(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Boolean> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.util.ArrayList<co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList.ReasonList> getTransactionData() {
        return null;
    }
    
    @java.lang.Override()
    public void setTransactionData(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList.ReasonList> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.util.ArrayList<co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList.ReasonList>> getPopulateSpinnerData() {
        return null;
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
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse.RemittanceFee> getTransactionFeeResponse() {
        return null;
    }
    
    @java.lang.Override()
    public void setTransactionFeeResponse(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse.RemittanceFee> p0) {
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
    public void getTransactionFeeInternational(@org.jetbrains.annotations.Nullable()
    java.lang.String productCode) {
    }
    
    @java.lang.Override()
    public void getTransactionInternationalfxList(@org.jetbrains.annotations.Nullable()
    java.lang.String productCode) {
    }
    
    @java.lang.Override()
    public void getReasonList(@org.jetbrains.annotations.Nullable()
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
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getFeeFromTier(@org.jetbrains.annotations.Nullable()
    java.lang.String enterAmount) {
        return null;
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