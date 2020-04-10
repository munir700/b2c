package co.yap.sendMoney.addbeneficiary.states;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0006\u0010+\u001a\u00020,R&\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00058W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR&\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00058W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\nR&\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00058W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\b\"\u0004\b\u0010\u0010\nR&\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00058W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\b\"\u0004\b\u0013\u0010\nR&\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0004\u001a\u00020\u00148W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R \u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00140\u001bX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001c\"\u0004\b\u001d\u0010\u001eR*\u0010\u001f\u001a\u0004\u0018\u00010\u00052\b\u0010\u0004\u001a\u0004\u0018\u00010\u00058W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010\b\"\u0004\b!\u0010\nR&\u0010\"\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00058W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\b\"\u0004\b$\u0010\nR \u0010%\u001a\b\u0012\u0004\u0012\u00020\u00050\u001bX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u001c\"\u0004\b\'\u0010\u001eR&\u0010(\u001a\u00020\u00142\u0006\u0010\u0004\u001a\u00020\u00148W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\u0017\"\u0004\b*\u0010\u0019\u00a8\u0006-"}, d2 = {"Lco/yap/sendMoney/addbeneficiary/states/BankDetailsState;", "Lco/yap/yapcore/BaseState;", "Lco/yap/sendMoney/addbeneficiary/interfaces/IBankDetails$State;", "()V", "value", "", "bankBranch", "getBankBranch", "()Ljava/lang/String;", "setBankBranch", "(Ljava/lang/String;)V", "bankCity", "getBankCity", "setBankCity", "bankName", "getBankName", "setBankName", "buttonText", "getButtonText", "setButtonText", "", "hideSwiftSection", "getHideSwiftSection", "()Z", "setHideSwiftSection", "(Z)V", "isRmt", "Landroidx/databinding/ObservableField;", "()Landroidx/databinding/ObservableField;", "setRmt", "(Landroidx/databinding/ObservableField;)V", "selectedBeneficiaryType", "getSelectedBeneficiaryType", "setSelectedBeneficiaryType", "swiftCode", "getSwiftCode", "setSwiftCode", "txtCount", "getTxtCount", "setTxtCount", "valid", "getValid", "setValid", "validate", "", "sendmoney_debug"})
public final class BankDetailsState extends co.yap.yapcore.BaseState implements co.yap.sendMoney.addbeneficiary.interfaces.IBankDetails.State {
    @org.jetbrains.annotations.Nullable()
    private java.lang.String selectedBeneficiaryType;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String buttonText;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String bankName;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String swiftCode;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String bankCity;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String bankBranch;
    @org.jetbrains.annotations.NotNull()
    private androidx.databinding.ObservableField<java.lang.Boolean> isRmt;
    @org.jetbrains.annotations.NotNull()
    private androidx.databinding.ObservableField<java.lang.String> txtCount;
    private boolean valid;
    private boolean hideSwiftSection;
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getSelectedBeneficiaryType() {
        return null;
    }
    
    @java.lang.Override()
    public void setSelectedBeneficiaryType(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getButtonText() {
        return null;
    }
    
    @java.lang.Override()
    public void setButtonText(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getBankName() {
        return null;
    }
    
    @java.lang.Override()
    public void setBankName(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getSwiftCode() {
        return null;
    }
    
    @java.lang.Override()
    public void setSwiftCode(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getBankCity() {
        return null;
    }
    
    @java.lang.Override()
    public void setBankCity(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getBankBranch() {
        return null;
    }
    
    @java.lang.Override()
    public void setBankBranch(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.databinding.ObservableField<java.lang.Boolean> isRmt() {
        return null;
    }
    
    @java.lang.Override()
    public void setRmt(@org.jetbrains.annotations.NotNull()
    androidx.databinding.ObservableField<java.lang.Boolean> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.databinding.ObservableField<java.lang.String> getTxtCount() {
        return null;
    }
    
    @java.lang.Override()
    public void setTxtCount(@org.jetbrains.annotations.NotNull()
    androidx.databinding.ObservableField<java.lang.String> p0) {
    }
    
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public boolean getValid() {
        return false;
    }
    
    @java.lang.Override()
    public void setValid(boolean value) {
    }
    
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public boolean getHideSwiftSection() {
        return false;
    }
    
    @java.lang.Override()
    public void setHideSwiftSection(boolean value) {
    }
    
    public final void validate() {
    }
    
    public BankDetailsState() {
        super();
    }
}