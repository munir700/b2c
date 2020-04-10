package co.yap.sendMoney.fundtransfer.states;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\r\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003R*\u0010\u0006\u001a\u0004\u0018\u00010\u00052\b\u0010\u0004\u001a\u0004\u0018\u00010\u00058W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR*\u0010\f\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0004\u001a\u0004\u0018\u00010\u000b8W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R \u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0012X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R,\u0010\u0018\u001a\u0004\u0018\u00010\u00172\b\u0010\u0004\u001a\u0004\u0018\u00010\u00178W@VX\u0096\u000e\u00a2\u0006\u0010\n\u0002\u0010\u001d\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR*\u0010\u001e\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0004\u001a\u0004\u0018\u00010\u000b8W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u000e\"\u0004\b \u0010\u0010R*\u0010!\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0004\u001a\u0004\u0018\u00010\u000b8W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u000e\"\u0004\b#\u0010\u0010\u00a8\u0006$"}, d2 = {"Lco/yap/sendMoney/fundtransfer/states/TransferSuccessState;", "Lco/yap/yapcore/BaseState;", "Lco/yap/sendMoney/fundtransfer/interfaces/ITransferSuccess$State;", "()V", "value", "", "availableBalanceString", "getAvailableBalanceString", "()Ljava/lang/CharSequence;", "setAvailableBalanceString", "(Ljava/lang/CharSequence;)V", "", "buttonTitle", "getButtonTitle", "()Ljava/lang/String;", "setButtonTitle", "(Ljava/lang/String;)V", "cutOffMessage", "Landroidx/databinding/ObservableField;", "getCutOffMessage", "()Landroidx/databinding/ObservableField;", "setCutOffMessage", "(Landroidx/databinding/ObservableField;)V", "", "flagLayoutVisibility", "getFlagLayoutVisibility", "()Ljava/lang/Boolean;", "setFlagLayoutVisibility", "(Ljava/lang/Boolean;)V", "Ljava/lang/Boolean;", "successHeader", "getSuccessHeader", "setSuccessHeader", "transferAmountHeading", "getTransferAmountHeading", "setTransferAmountHeading", "sendmoney_release"})
public final class TransferSuccessState extends co.yap.yapcore.BaseState implements co.yap.sendMoney.fundtransfer.interfaces.ITransferSuccess.State {
    @org.jetbrains.annotations.NotNull()
    private androidx.databinding.ObservableField<java.lang.String> cutOffMessage;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String successHeader;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Boolean flagLayoutVisibility;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String transferAmountHeading;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String buttonTitle;
    @org.jetbrains.annotations.Nullable()
    private java.lang.CharSequence availableBalanceString;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.databinding.ObservableField<java.lang.String> getCutOffMessage() {
        return null;
    }
    
    @java.lang.Override()
    public void setCutOffMessage(@org.jetbrains.annotations.NotNull()
    androidx.databinding.ObservableField<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getSuccessHeader() {
        return null;
    }
    
    @java.lang.Override()
    public void setSuccessHeader(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.Boolean getFlagLayoutVisibility() {
        return null;
    }
    
    @java.lang.Override()
    public void setFlagLayoutVisibility(@org.jetbrains.annotations.Nullable()
    java.lang.Boolean value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getTransferAmountHeading() {
        return null;
    }
    
    @java.lang.Override()
    public void setTransferAmountHeading(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getButtonTitle() {
        return null;
    }
    
    @java.lang.Override()
    public void setButtonTitle(@org.jetbrains.annotations.Nullable()
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
    
    public TransferSuccessState() {
        super();
    }
}