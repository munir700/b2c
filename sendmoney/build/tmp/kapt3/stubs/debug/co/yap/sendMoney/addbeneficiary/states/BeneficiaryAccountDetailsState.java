package co.yap.sendMoney.addbeneficiary.states;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0011\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u0006\u00102\u001a\u000203J\b\u00104\u001a\u000203H\u0002R&\u0010\b\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR&\u0010\r\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\n\"\u0004\b\u000f\u0010\fR&\u0010\u0010\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\n\"\u0004\b\u0012\u0010\fR&\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\n\"\u0004\b\u0015\u0010\fR&\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\n\"\u0004\b\u0018\u0010\fR&\u0010\u0019\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\n\"\u0004\b\u001b\u0010\fR&\u0010\u001c\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\n\"\u0004\b\u001e\u0010\fR \u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\"\"\u0004\b#\u0010$R \u0010%\u001a\b\u0012\u0004\u0012\u00020!0 X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\"\"\u0004\b\'\u0010$R&\u0010(\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\n\"\u0004\b*\u0010\fR&\u0010+\u001a\u00020!2\u0006\u0010\u0006\u001a\u00020!8W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u00101\u00a8\u00065"}, d2 = {"Lco/yap/sendMoney/addbeneficiary/states/BeneficiaryAccountDetailsState;", "Lco/yap/yapcore/BaseState;", "Lco/yap/sendMoney/addbeneficiary/interfaces/IBeneficiaryAccountDetails$State;", "viewModel", "Lco/yap/sendMoney/addbeneficiary/viewmodels/BeneficiaryAccountDetailsViewModel;", "(Lco/yap/sendMoney/addbeneficiary/viewmodels/BeneficiaryAccountDetailsViewModel;)V", "value", "", "accountIban", "getAccountIban", "()Ljava/lang/String;", "setAccountIban", "(Ljava/lang/String;)V", "bankAddress", "getBankAddress", "setBankAddress", "bankName", "getBankName", "setBankName", "bankPhoneNumber", "getBankPhoneNumber", "setBankPhoneNumber", "beneficiaryAccountNumber", "getBeneficiaryAccountNumber", "setBeneficiaryAccountNumber", "countryBankRequirementFieldCode", "getCountryBankRequirementFieldCode", "setCountryBankRequirementFieldCode", "idCode", "getIdCode", "setIdCode", "isIbanMandatory", "Landroidx/databinding/ObservableField;", "", "()Landroidx/databinding/ObservableField;", "setIbanMandatory", "(Landroidx/databinding/ObservableField;)V", "showlyIban", "getShowlyIban", "setShowlyIban", "swiftCode", "getSwiftCode", "setSwiftCode", "valid", "getValid", "()Z", "setValid", "(Z)V", "getViewModel", "()Lco/yap/sendMoney/addbeneficiary/viewmodels/BeneficiaryAccountDetailsViewModel;", "validate", "", "validateNonRmt", "sendmoney_debug"})
public final class BeneficiaryAccountDetailsState extends co.yap.yapcore.BaseState implements co.yap.sendMoney.addbeneficiary.interfaces.IBeneficiaryAccountDetails.State {
    @org.jetbrains.annotations.NotNull()
    private androidx.databinding.ObservableField<java.lang.Boolean> showlyIban;
    @org.jetbrains.annotations.NotNull()
    private androidx.databinding.ObservableField<java.lang.Boolean> isIbanMandatory;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String accountIban;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String swiftCode;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String beneficiaryAccountNumber;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String countryBankRequirementFieldCode;
    private boolean valid;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String bankName;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String idCode;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String bankAddress;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String bankPhoneNumber;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.sendMoney.addbeneficiary.viewmodels.BeneficiaryAccountDetailsViewModel viewModel = null;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.databinding.ObservableField<java.lang.Boolean> getShowlyIban() {
        return null;
    }
    
    @java.lang.Override()
    public void setShowlyIban(@org.jetbrains.annotations.NotNull()
    androidx.databinding.ObservableField<java.lang.Boolean> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.databinding.ObservableField<java.lang.Boolean> isIbanMandatory() {
        return null;
    }
    
    @java.lang.Override()
    public void setIbanMandatory(@org.jetbrains.annotations.NotNull()
    androidx.databinding.ObservableField<java.lang.Boolean> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getAccountIban() {
        return null;
    }
    
    @java.lang.Override()
    public void setAccountIban(@org.jetbrains.annotations.NotNull()
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
    public java.lang.String getBeneficiaryAccountNumber() {
        return null;
    }
    
    @java.lang.Override()
    public void setBeneficiaryAccountNumber(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getCountryBankRequirementFieldCode() {
        return null;
    }
    
    @java.lang.Override()
    public void setCountryBankRequirementFieldCode(@org.jetbrains.annotations.NotNull()
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
    
    private final void validateNonRmt() {
    }
    
    public final void validate() {
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
    public java.lang.String getIdCode() {
        return null;
    }
    
    @java.lang.Override()
    public void setIdCode(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getBankAddress() {
        return null;
    }
    
    @java.lang.Override()
    public void setBankAddress(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getBankPhoneNumber() {
        return null;
    }
    
    @java.lang.Override()
    public void setBankPhoneNumber(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final co.yap.sendMoney.addbeneficiary.viewmodels.BeneficiaryAccountDetailsViewModel getViewModel() {
        return null;
    }
    
    public BeneficiaryAccountDetailsState(@org.jetbrains.annotations.NotNull()
    co.yap.sendMoney.addbeneficiary.viewmodels.BeneficiaryAccountDetailsViewModel viewModel) {
        super();
    }
}