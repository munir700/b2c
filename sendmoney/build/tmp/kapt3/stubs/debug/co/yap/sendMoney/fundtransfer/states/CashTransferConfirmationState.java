package co.yap.sendMoney.fundtransfer.states;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\r\n\u0002\b\t\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003R \u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR \u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\b\"\u0004\b\u000e\u0010\nR \u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\b\"\u0004\b\u0011\u0010\nR \u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\f0\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\b\"\u0004\b\u0014\u0010\n\u00a8\u0006\u0015"}, d2 = {"Lco/yap/sendMoney/fundtransfer/states/CashTransferConfirmationState;", "Lco/yap/yapcore/BaseState;", "Lco/yap/sendMoney/fundtransfer/interfaces/ICashTransferConfirmation$State;", "()V", "cutOffTimeMsg", "Landroidx/databinding/ObservableField;", "", "getCutOffTimeMsg", "()Landroidx/databinding/ObservableField;", "setCutOffTimeMsg", "(Landroidx/databinding/ObservableField;)V", "description", "", "getDescription", "setDescription", "productCode", "getProductCode", "setProductCode", "transferFeeDescription", "getTransferFeeDescription", "setTransferFeeDescription", "sendmoney_debug"})
public final class CashTransferConfirmationState extends co.yap.yapcore.BaseState implements co.yap.sendMoney.fundtransfer.interfaces.ICashTransferConfirmation.State {
    @org.jetbrains.annotations.NotNull()
    private androidx.databinding.ObservableField<java.lang.CharSequence> description;
    @org.jetbrains.annotations.NotNull()
    private androidx.databinding.ObservableField<java.lang.String> cutOffTimeMsg;
    @org.jetbrains.annotations.NotNull()
    private androidx.databinding.ObservableField<java.lang.String> productCode;
    @org.jetbrains.annotations.NotNull()
    private androidx.databinding.ObservableField<java.lang.CharSequence> transferFeeDescription;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.databinding.ObservableField<java.lang.CharSequence> getDescription() {
        return null;
    }
    
    @java.lang.Override()
    public void setDescription(@org.jetbrains.annotations.NotNull()
    androidx.databinding.ObservableField<java.lang.CharSequence> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.databinding.ObservableField<java.lang.String> getCutOffTimeMsg() {
        return null;
    }
    
    @java.lang.Override()
    public void setCutOffTimeMsg(@org.jetbrains.annotations.NotNull()
    androidx.databinding.ObservableField<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.databinding.ObservableField<java.lang.String> getProductCode() {
        return null;
    }
    
    @java.lang.Override()
    public void setProductCode(@org.jetbrains.annotations.NotNull()
    androidx.databinding.ObservableField<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.databinding.ObservableField<java.lang.CharSequence> getTransferFeeDescription() {
        return null;
    }
    
    @java.lang.Override()
    public void setTransferFeeDescription(@org.jetbrains.annotations.NotNull()
    androidx.databinding.ObservableField<java.lang.CharSequence> p0) {
    }
    
    public CashTransferConfirmationState() {
        super();
    }
}