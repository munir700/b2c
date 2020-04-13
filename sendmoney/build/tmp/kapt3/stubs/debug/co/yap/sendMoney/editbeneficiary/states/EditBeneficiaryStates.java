package co.yap.sendmoney.editbeneficiary.states;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\u000b\n\u0002\b\u001c\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u0012\u0010C\u001a\u00020D2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000fH\u0002R*\u0010\b\u001a\u0004\u0018\u00010\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR*\u0010\u0010\u001a\u0004\u0018\u00010\u000f2\b\u0010\u0006\u001a\u0004\u0018\u00010\u000f8W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R*\u0010\u0015\u001a\u0004\u0018\u00010\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\n\"\u0004\b\u0017\u0010\fR*\u0010\u0018\u001a\u0004\u0018\u00010\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\n\"\u0004\b\u001a\u0010\fR*\u0010\u001b\u001a\u0004\u0018\u00010\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\n\"\u0004\b\u001d\u0010\fR*\u0010\u001e\u001a\u0004\u0018\u00010\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\n\"\u0004\b \u0010\fR*\u0010!\u001a\u0004\u0018\u00010\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\n\"\u0004\b#\u0010\fR*\u0010$\u001a\u0004\u0018\u00010\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\n\"\u0004\b&\u0010\fR,\u0010(\u001a\u0004\u0018\u00010\'2\b\u0010\u0006\u001a\u0004\u0018\u00010\'8W@VX\u0096\u000e\u00a2\u0006\u0010\n\u0002\u0010-\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R,\u0010.\u001a\u0004\u0018\u00010\'2\b\u0010\u0006\u001a\u0004\u0018\u00010\'8W@VX\u0096\u000e\u00a2\u0006\u0010\n\u0002\u0010-\u001a\u0004\b/\u0010*\"\u0004\b0\u0010,R*\u00101\u001a\u0004\u0018\u00010\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b2\u0010\n\"\u0004\b3\u0010\fR*\u00104\u001a\u0004\u0018\u00010\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b5\u0010\n\"\u0004\b6\u0010\fR,\u00107\u001a\u0004\u0018\u00010\'2\b\u0010\u0006\u001a\u0004\u0018\u00010\'8W@VX\u0096\u000e\u00a2\u0006\u0010\n\u0002\u0010-\u001a\u0004\b8\u0010*\"\u0004\b9\u0010,R*\u0010:\u001a\u0004\u0018\u00010\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b;\u0010\n\"\u0004\b<\u0010\fR*\u0010=\u001a\u0004\u0018\u00010\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b>\u0010\n\"\u0004\b?\u0010\fR,\u0010@\u001a\u0004\u0018\u00010\'2\b\u0010\u0006\u001a\u0004\u0018\u00010\'8W@VX\u0096\u000e\u00a2\u0006\u0010\n\u0002\u0010-\u001a\u0004\bA\u0010*\"\u0004\bB\u0010,\u00a8\u0006E"}, d2 = {"Lco/yap/sendmoney/editbeneficiary/states/EditBeneficiaryStates;", "Lco/yap/yapcore/BaseState;", "Lco/yap/sendmoney/editbeneficiary/interfaces/IEditBeneficiary$State;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "value", "", "accountNumber", "getAccountNumber", "()Ljava/lang/String;", "setAccountNumber", "(Ljava/lang/String;)V", "getApplication", "()Landroid/app/Application;", "Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;", "beneficiary", "getBeneficiary", "()Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;", "setBeneficiary", "(Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;)V", "country", "getCountry", "setCountry", "countryBankRequirementFieldCode", "getCountryBankRequirementFieldCode", "setCountryBankRequirementFieldCode", "countryCode", "getCountryCode", "setCountryCode", "currency", "getCurrency", "setCurrency", "firstName", "getFirstName", "setFirstName", "lastName", "getLastName", "setLastName", "", "needIban", "getNeedIban", "()Ljava/lang/Boolean;", "setNeedIban", "(Ljava/lang/Boolean;)V", "Ljava/lang/Boolean;", "needOverView", "getNeedOverView", "setNeedOverView", "nickName", "getNickName", "setNickName", "phoneNumber", "getPhoneNumber", "setPhoneNumber", "showIban", "getShowIban", "setShowIban", "swiftCode", "getSwiftCode", "setSwiftCode", "transferType", "getTransferType", "setTransferType", "valid", "getValid", "setValid", "updateAllFields", "", "sendmoney_debug"})
public final class EditBeneficiaryStates extends co.yap.yapcore.BaseState implements co.yap.sendmoney.editbeneficiary.interfaces.IEditBeneficiary.State {
    @org.jetbrains.annotations.Nullable()
    private java.lang.String country;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String transferType;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String currency;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String nickName;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String firstName;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String lastName;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String phoneNumber;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String accountNumber;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String swiftCode;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String countryBankRequirementFieldCode;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Boolean needOverView;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Boolean needIban;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Boolean showIban;
    @org.jetbrains.annotations.Nullable()
    private co.yap.networking.customers.responsedtos.sendmoney.Beneficiary beneficiary;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String countryCode;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Boolean valid;
    @org.jetbrains.annotations.NotNull()
    private final android.app.Application application = null;
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getCountry() {
        return null;
    }
    
    @java.lang.Override()
    public void setCountry(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getTransferType() {
        return null;
    }
    
    @java.lang.Override()
    public void setTransferType(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getCurrency() {
        return null;
    }
    
    @java.lang.Override()
    public void setCurrency(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getNickName() {
        return null;
    }
    
    @java.lang.Override()
    public void setNickName(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getFirstName() {
        return null;
    }
    
    @java.lang.Override()
    public void setFirstName(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getLastName() {
        return null;
    }
    
    @java.lang.Override()
    public void setLastName(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getPhoneNumber() {
        return null;
    }
    
    @java.lang.Override()
    public void setPhoneNumber(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getAccountNumber() {
        return null;
    }
    
    @java.lang.Override()
    public void setAccountNumber(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getSwiftCode() {
        return null;
    }
    
    @java.lang.Override()
    public void setSwiftCode(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getCountryBankRequirementFieldCode() {
        return null;
    }
    
    @java.lang.Override()
    public void setCountryBankRequirementFieldCode(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.Boolean getNeedOverView() {
        return null;
    }
    
    @java.lang.Override()
    public void setNeedOverView(@org.jetbrains.annotations.Nullable()
    java.lang.Boolean value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.Boolean getNeedIban() {
        return null;
    }
    
    @java.lang.Override()
    public void setNeedIban(@org.jetbrains.annotations.Nullable()
    java.lang.Boolean value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.Boolean getShowIban() {
        return null;
    }
    
    @java.lang.Override()
    public void setShowIban(@org.jetbrains.annotations.Nullable()
    java.lang.Boolean value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public co.yap.networking.customers.responsedtos.sendmoney.Beneficiary getBeneficiary() {
        return null;
    }
    
    @java.lang.Override()
    public void setBeneficiary(@org.jetbrains.annotations.Nullable()
    co.yap.networking.customers.responsedtos.sendmoney.Beneficiary value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getCountryCode() {
        return null;
    }
    
    @java.lang.Override()
    public void setCountryCode(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.Boolean getValid() {
        return null;
    }
    
    @java.lang.Override()
    public void setValid(@org.jetbrains.annotations.Nullable()
    java.lang.Boolean value) {
    }
    
    private final void updateAllFields(co.yap.networking.customers.responsedtos.sendmoney.Beneficiary beneficiary) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.app.Application getApplication() {
        return null;
    }
    
    public EditBeneficiaryStates(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super();
    }
}