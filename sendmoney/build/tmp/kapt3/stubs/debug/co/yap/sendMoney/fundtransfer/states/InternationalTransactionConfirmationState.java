package co.yap.sendMoney.fundtransfer.states;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\r\n\u0002\b\f\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003R*\u0010\u0006\u001a\u0004\u0018\u00010\u00052\b\u0010\u0004\u001a\u0004\u0018\u00010\u00058W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR*\u0010\u000b\u001a\u0004\u0018\u00010\u00052\b\u0010\u0004\u001a\u0004\u0018\u00010\u00058W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\nR*\u0010\u000f\u001a\u0004\u0018\u00010\u000e2\b\u0010\u0004\u001a\u0004\u0018\u00010\u000e8W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R*\u0010\u0014\u001a\u0004\u0018\u00010\u000e2\b\u0010\u0004\u001a\u0004\u0018\u00010\u000e8W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0011\"\u0004\b\u0016\u0010\u0013R*\u0010\u0017\u001a\u0004\u0018\u00010\u000e2\b\u0010\u0004\u001a\u0004\u0018\u00010\u000e8W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0011\"\u0004\b\u0019\u0010\u0013\u00a8\u0006\u001a"}, d2 = {"Lco/yap/sendMoney/fundtransfer/states/InternationalTransactionConfirmationState;", "Lco/yap/yapcore/BaseState;", "Lco/yap/sendMoney/fundtransfer/interfaces/IInternationalTransactionConfirmation$State;", "()V", "value", "", "confirmHeading", "getConfirmHeading", "()Ljava/lang/String;", "setConfirmHeading", "(Ljava/lang/String;)V", "cutOffTimeMsg", "getCutOffTimeMsg", "setCutOffTimeMsg", "", "receivingAmountDescription", "getReceivingAmountDescription", "()Ljava/lang/CharSequence;", "setReceivingAmountDescription", "(Ljava/lang/CharSequence;)V", "transferDescription", "getTransferDescription", "setTransferDescription", "transferFeeDescription", "getTransferFeeDescription", "setTransferFeeDescription", "sendmoney_debug"})
public final class InternationalTransactionConfirmationState extends co.yap.yapcore.BaseState implements co.yap.sendMoney.fundtransfer.interfaces.IInternationalTransactionConfirmation.State {
    @org.jetbrains.annotations.Nullable()
    private java.lang.CharSequence transferDescription;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String confirmHeading;
    @org.jetbrains.annotations.Nullable()
    private java.lang.CharSequence receivingAmountDescription;
    @org.jetbrains.annotations.Nullable()
    private java.lang.CharSequence transferFeeDescription;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String cutOffTimeMsg;
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.CharSequence getTransferDescription() {
        return null;
    }
    
    @java.lang.Override()
    public void setTransferDescription(@org.jetbrains.annotations.Nullable()
    java.lang.CharSequence value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getConfirmHeading() {
        return null;
    }
    
    @java.lang.Override()
    public void setConfirmHeading(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.CharSequence getReceivingAmountDescription() {
        return null;
    }
    
    @java.lang.Override()
    public void setReceivingAmountDescription(@org.jetbrains.annotations.Nullable()
    java.lang.CharSequence value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.CharSequence getTransferFeeDescription() {
        return null;
    }
    
    @java.lang.Override()
    public void setTransferFeeDescription(@org.jetbrains.annotations.Nullable()
    java.lang.CharSequence value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getCutOffTimeMsg() {
        return null;
    }
    
    @java.lang.Override()
    public void setCutOffTimeMsg(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    public InternationalTransactionConfirmationState() {
        super();
    }
}