package co.yap.sendMoney.fundtransfer.states;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u000f\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003R\u001a\u0010\u0004\u001a\u00020\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR&\u0010\f\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000b8W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R*\u0010\u0012\u001a\u0004\u0018\u00010\u00112\b\u0010\n\u001a\u0004\u0018\u00010\u00118W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\u00020\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0007\"\u0004\b\u0019\u0010\tR*\u0010\u001a\u001a\u0004\u0018\u00010\u00112\b\u0010\n\u001a\u0004\u0018\u00010\u00118W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0014\"\u0004\b\u001c\u0010\u0016R\u001a\u0010\u001d\u001a\u00020\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0007\"\u0004\b\u001f\u0010\t\u00a8\u0006 "}, d2 = {"Lco/yap/sendMoney/fundtransfer/states/BeneficiaryFundTransferState;", "Lco/yap/yapcore/BaseState;", "Lco/yap/sendMoney/fundtransfer/interfaces/IBeneficiaryFundTransfer$State;", "()V", "leftIcon", "Landroidx/databinding/ObservableBoolean;", "getLeftIcon", "()Landroidx/databinding/ObservableBoolean;", "setLeftIcon", "(Landroidx/databinding/ObservableBoolean;)V", "value", "", "position", "getPosition", "()I", "setPosition", "(I)V", "", "rightButtonText", "getRightButtonText", "()Ljava/lang/String;", "setRightButtonText", "(Ljava/lang/String;)V", "rightIcon", "getRightIcon", "setRightIcon", "toolBarTitle", "getToolBarTitle", "setToolBarTitle", "toolbarVisibility", "getToolbarVisibility", "setToolbarVisibility", "sendmoney_debug"})
public final class BeneficiaryFundTransferState extends co.yap.yapcore.BaseState implements co.yap.sendMoney.fundtransfer.interfaces.IBeneficiaryFundTransfer.State {
    @org.jetbrains.annotations.Nullable()
    private java.lang.String toolBarTitle;
    @org.jetbrains.annotations.NotNull()
    private androidx.databinding.ObservableBoolean toolbarVisibility;
    @org.jetbrains.annotations.NotNull()
    private androidx.databinding.ObservableBoolean rightIcon;
    @org.jetbrains.annotations.NotNull()
    private androidx.databinding.ObservableBoolean leftIcon;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String rightButtonText;
    private int position;
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getToolBarTitle() {
        return null;
    }
    
    @java.lang.Override()
    public void setToolBarTitle(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.databinding.ObservableBoolean getToolbarVisibility() {
        return null;
    }
    
    @java.lang.Override()
    public void setToolbarVisibility(@org.jetbrains.annotations.NotNull()
    androidx.databinding.ObservableBoolean p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.databinding.ObservableBoolean getRightIcon() {
        return null;
    }
    
    @java.lang.Override()
    public void setRightIcon(@org.jetbrains.annotations.NotNull()
    androidx.databinding.ObservableBoolean p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.databinding.ObservableBoolean getLeftIcon() {
        return null;
    }
    
    @java.lang.Override()
    public void setLeftIcon(@org.jetbrains.annotations.NotNull()
    androidx.databinding.ObservableBoolean p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getRightButtonText() {
        return null;
    }
    
    @java.lang.Override()
    public void setRightButtonText(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public int getPosition() {
        return 0;
    }
    
    @java.lang.Override()
    public void setPosition(int value) {
    }
    
    public BeneficiaryFundTransferState() {
        super();
    }
}