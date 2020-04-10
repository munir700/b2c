package co.yap.sendMoney.base;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001a\u0010&\u001a\u0004\u0018\u00010\b2\u0006\u0010\'\u001a\u00020\b2\u0006\u0010(\u001a\u00020\u000fH\u0002J\u001a\u0010)\u001a\u0004\u0018\u00010\b2\u0006\u0010\'\u001a\u00020\b2\b\b\u0002\u0010*\u001a\u00020\u0019J\u001a\u0010+\u001a\u0004\u0018\u00010\b2\u0006\u0010\'\u001a\u00020\b2\u0006\u0010(\u001a\u00020\u000fH\u0002J\u001a\u0010,\u001a\u0004\u0018\u00010\b2\u0006\u0010\'\u001a\u00020\b2\b\b\u0002\u0010*\u001a\u00020\u0019J\u001a\u0010-\u001a\u00020.2\b\u0010/\u001a\u0004\u0018\u00010\b2\b\b\u0002\u00100\u001a\u000201J\u0018\u00102\u001a\u00020.2\u0006\u0010\'\u001a\u00020\b2\b\b\u0002\u0010*\u001a\u00020\u0019R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR \u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\n\"\u0004\b\u0016\u0010\fR \u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u001a\"\u0004\b\u001b\u0010\u001cR \u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001a\"\u0004\b\u001e\u0010\u001cR\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010!\u001a\b\u0012\u0004\u0012\u00020\b0\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001aR\u001a\u0010#\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\n\"\u0004\b%\u0010\f\u00a8\u00063"}, d2 = {"Lco/yap/sendMoney/base/SMFeeViewModel;", "S", "Lco/yap/yapcore/IBase$State;", "Lco/yap/yapcore/BaseViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "feeAmount", "", "getFeeAmount", "()Ljava/lang/String;", "setFeeAmount", "(Ljava/lang/String;)V", "feeTiers", "", "Lco/yap/networking/transactions/responsedtos/transaction/RemittanceFeeResponse$RemittanceFee$TierRateDTO;", "getFeeTiers", "()Ljava/util/List;", "setFeeTiers", "(Ljava/util/List;)V", "feeType", "getFeeType", "setFeeType", "isAPIFailed", "Landroidx/lifecycle/MutableLiveData;", "", "()Landroidx/lifecycle/MutableLiveData;", "setAPIFailed", "(Landroidx/lifecycle/MutableLiveData;)V", "isFeeReceived", "setFeeReceived", "transactionRepository", "Lco/yap/networking/transactions/TransactionsRepository;", "updatedFee", "getUpdatedFee", "vat", "getVat", "setVat", "calFeeInPercentage", "enterAmount", "fee", "getFeeFromTier", "isTopUpFee", "getFeeInPercentageForTopup", "getFlatFee", "getTransferFees", "", "productCode", "feeRequest", "Lco/yap/networking/transactions/requestdtos/RemittanceFeeRequest;", "updateFees", "sendmoney_release"})
public abstract class SMFeeViewModel<S extends co.yap.yapcore.IBase.State> extends co.yap.yapcore.BaseViewModel<S> {
    private final co.yap.networking.transactions.TransactionsRepository transactionRepository = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse.RemittanceFee.TierRateDTO> feeTiers;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Boolean> isFeeReceived;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Boolean> isAPIFailed;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String feeType;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String feeAmount;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String vat;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.String> updatedFee = null;
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse.RemittanceFee.TierRateDTO> getFeeTiers() {
        return null;
    }
    
    public final void setFeeTiers(@org.jetbrains.annotations.NotNull()
    java.util.List<co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse.RemittanceFee.TierRateDTO> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Boolean> isFeeReceived() {
        return null;
    }
    
    public final void setFeeReceived(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Boolean> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Boolean> isAPIFailed() {
        return null;
    }
    
    public final void setAPIFailed(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Boolean> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFeeType() {
        return null;
    }
    
    public final void setFeeType(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFeeAmount() {
        return null;
    }
    
    public final void setFeeAmount(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getVat() {
        return null;
    }
    
    public final void setVat(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getUpdatedFee() {
        return null;
    }
    
    public final void getTransferFees(@org.jetbrains.annotations.Nullable()
    java.lang.String productCode, @org.jetbrains.annotations.NotNull()
    co.yap.networking.transactions.requestdtos.RemittanceFeeRequest feeRequest) {
    }
    
    public final void updateFees(@org.jetbrains.annotations.NotNull()
    java.lang.String enterAmount, boolean isTopUpFee) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getFeeFromTier(@org.jetbrains.annotations.NotNull()
    java.lang.String enterAmount, boolean isTopUpFee) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getFlatFee(@org.jetbrains.annotations.NotNull()
    java.lang.String enterAmount, boolean isTopUpFee) {
        return null;
    }
    
    private final java.lang.String calFeeInPercentage(java.lang.String enterAmount, co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse.RemittanceFee.TierRateDTO fee) {
        return null;
    }
    
    private final java.lang.String getFeeInPercentageForTopup(java.lang.String enterAmount, co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse.RemittanceFee.TierRateDTO fee) {
        return null;
    }
    
    public SMFeeViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
}