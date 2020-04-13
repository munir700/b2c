package co.yap.sendmoney.fundtransfer.states;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\r\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0010\u0006\n\u0002\b\u0018\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\b\u0010H\u001a\u00020IH\u0016R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R*\u0010\n\u001a\u0004\u0018\u00010\t2\b\u0010\b\u001a\u0004\u0018\u00010\t8W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0019\u0010\u000f\u001a\n \u0011*\u0004\u0018\u00010\u00100\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R \u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR&\u0010\u001b\u001a\u00020\u00162\u0006\u0010\b\u001a\u00020\u00168W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR*\u0010 \u001a\u0004\u0018\u00010\u00162\b\u0010\b\u001a\u0004\u0018\u00010\u00168W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u001d\"\u0004\b\"\u0010\u001fR*\u0010#\u001a\u0004\u0018\u00010\u00162\b\u0010\b\u001a\u0004\u0018\u00010\u00168W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u001d\"\u0004\b%\u0010\u001fR*\u0010&\u001a\u0004\u0018\u00010\u00162\b\u0010\b\u001a\u0004\u0018\u00010\u00168W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\'\u0010\u001d\"\u0004\b(\u0010\u001fR,\u0010*\u001a\u0004\u0018\u00010)2\b\u0010\b\u001a\u0004\u0018\u00010)8W@VX\u0096\u000e\u00a2\u0006\u0010\n\u0002\u0010/\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R,\u00100\u001a\u0004\u0018\u00010)2\b\u0010\b\u001a\u0004\u0018\u00010)8W@VX\u0096\u000e\u00a2\u0006\u0010\n\u0002\u0010/\u001a\u0004\b1\u0010,\"\u0004\b2\u0010.R \u00103\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b4\u0010\u0018\"\u0004\b5\u0010\u001aR*\u00106\u001a\u0004\u0018\u00010\u00162\b\u0010\b\u001a\u0004\u0018\u00010\u00168W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b7\u0010\u001d\"\u0004\b8\u0010\u001fR \u00109\u001a\b\u0012\u0004\u0012\u00020)0\u0015X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b:\u0010\u0018\"\u0004\b;\u0010\u001aR \u0010<\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b=\u0010\u0018\"\u0004\b>\u0010\u001aR*\u0010?\u001a\u0004\u0018\u00010\t2\b\u0010\b\u001a\u0004\u0018\u00010\t8W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b@\u0010\f\"\u0004\bA\u0010\u000eR&\u0010C\u001a\u00020B2\u0006\u0010\b\u001a\u00020B8W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bD\u0010E\"\u0004\bF\u0010G\u00a8\u0006J"}, d2 = {"Lco/yap/sendmoney/fundtransfer/states/InternationalFundsTransferState;", "Lco/yap/yapcore/BaseState;", "Lco/yap/sendmoney/fundtransfer/interfaces/IInternationalFundsTransfer$State;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "getApplication", "()Landroid/app/Application;", "value", "", "availableBalanceString", "getAvailableBalanceString", "()Ljava/lang/CharSequence;", "setAvailableBalanceString", "(Ljava/lang/CharSequence;)V", "context", "Landroid/content/Context;", "kotlin.jvm.PlatformType", "getContext", "()Landroid/content/Context;", "destinationCurrency", "Landroidx/databinding/ObservableField;", "", "getDestinationCurrency", "()Landroidx/databinding/ObservableField;", "setDestinationCurrency", "(Landroidx/databinding/ObservableField;)V", "errorDescription", "getErrorDescription", "()Ljava/lang/String;", "setErrorDescription", "(Ljava/lang/String;)V", "etInputAmount", "getEtInputAmount", "setEtInputAmount", "etOutputAmount", "getEtOutputAmount", "setEtOutputAmount", "fromFxRate", "getFromFxRate", "setFromFxRate", "", "maxLimit", "getMaxLimit", "()Ljava/lang/Double;", "setMaxLimit", "(Ljava/lang/Double;)V", "Ljava/lang/Double;", "minLimit", "getMinLimit", "setMinLimit", "sourceCurrency", "getSourceCurrency", "setSourceCurrency", "toFxRate", "getToFxRate", "setToFxRate", "totalTransferAmount", "getTotalTransferAmount", "setTotalTransferAmount", "transactionNote", "getTransactionNote", "setTransactionNote", "transferFeeSpannable", "getTransferFeeSpannable", "setTransferFeeSpannable", "", "valid", "getValid", "()Z", "setValid", "(Z)V", "clearError", "", "sendmoney_debug"})
public final class InternationalFundsTransferState extends co.yap.yapcore.BaseState implements co.yap.sendmoney.fundtransfer.interfaces.IInternationalFundsTransfer.State {
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private androidx.databinding.ObservableField<java.lang.Double> totalTransferAmount;
    @org.jetbrains.annotations.NotNull()
    private androidx.databinding.ObservableField<java.lang.String> sourceCurrency;
    @org.jetbrains.annotations.NotNull()
    private androidx.databinding.ObservableField<java.lang.String> destinationCurrency;
    @org.jetbrains.annotations.NotNull()
    private androidx.databinding.ObservableField<java.lang.String> transactionNote;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String errorDescription;
    @org.jetbrains.annotations.Nullable()
    private java.lang.CharSequence availableBalanceString;
    @org.jetbrains.annotations.Nullable()
    private java.lang.CharSequence transferFeeSpannable;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String etInputAmount;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String etOutputAmount;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String fromFxRate;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String toFxRate;
    private boolean valid;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Double maxLimit;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Double minLimit;
    @org.jetbrains.annotations.NotNull()
    private final android.app.Application application = null;
    
    public final android.content.Context getContext() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.databinding.ObservableField<java.lang.Double> getTotalTransferAmount() {
        return null;
    }
    
    @java.lang.Override()
    public void setTotalTransferAmount(@org.jetbrains.annotations.NotNull()
    androidx.databinding.ObservableField<java.lang.Double> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.databinding.ObservableField<java.lang.String> getSourceCurrency() {
        return null;
    }
    
    @java.lang.Override()
    public void setSourceCurrency(@org.jetbrains.annotations.NotNull()
    androidx.databinding.ObservableField<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.databinding.ObservableField<java.lang.String> getDestinationCurrency() {
        return null;
    }
    
    @java.lang.Override()
    public void setDestinationCurrency(@org.jetbrains.annotations.NotNull()
    androidx.databinding.ObservableField<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.databinding.ObservableField<java.lang.String> getTransactionNote() {
        return null;
    }
    
    @java.lang.Override()
    public void setTransactionNote(@org.jetbrains.annotations.NotNull()
    androidx.databinding.ObservableField<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getErrorDescription() {
        return null;
    }
    
    @java.lang.Override()
    public void setErrorDescription(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.CharSequence getAvailableBalanceString() {
        return null;
    }
    
    @java.lang.Override()
    public void setAvailableBalanceString(@org.jetbrains.annotations.Nullable()
    java.lang.CharSequence value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.CharSequence getTransferFeeSpannable() {
        return null;
    }
    
    @java.lang.Override()
    public void setTransferFeeSpannable(@org.jetbrains.annotations.Nullable()
    java.lang.CharSequence value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getEtInputAmount() {
        return null;
    }
    
    @java.lang.Override()
    public void setEtInputAmount(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getEtOutputAmount() {
        return null;
    }
    
    @java.lang.Override()
    public void setEtOutputAmount(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getFromFxRate() {
        return null;
    }
    
    @java.lang.Override()
    public void setFromFxRate(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getToFxRate() {
        return null;
    }
    
    @java.lang.Override()
    public void setToFxRate(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public boolean getValid() {
        return false;
    }
    
    @java.lang.Override()
    public void setValid(boolean value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.Double getMaxLimit() {
        return null;
    }
    
    @java.lang.Override()
    public void setMaxLimit(@org.jetbrains.annotations.Nullable()
    java.lang.Double value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.Double getMinLimit() {
        return null;
    }
    
    @java.lang.Override()
    public void setMinLimit(@org.jetbrains.annotations.Nullable()
    java.lang.Double value) {
    }
    
    @java.lang.Override()
    public void clearError() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.app.Application getApplication() {
        return null;
    }
    
    public InternationalFundsTransferState(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super();
    }
}