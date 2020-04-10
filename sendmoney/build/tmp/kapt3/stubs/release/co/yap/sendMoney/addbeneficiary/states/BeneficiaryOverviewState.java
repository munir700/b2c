package co.yap.sendMoney.addbeneficiary.states;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u001a\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0010\u0010D\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u000bH\u0002J\u0010\u0010E\u001a\u00020F2\u0006\u0010\f\u001a\u00020\u000bH\u0002R&\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00058W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR*\u0010\f\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0004\u001a\u0004\u0018\u00010\u000b8W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R&\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00058W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\b\"\u0004\b\u0013\u0010\nR&\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00058W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\b\"\u0004\b\u0016\u0010\nR&\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00058W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\b\"\u0004\b\u0019\u0010\nR*\u0010\u001b\u001a\u0004\u0018\u00010\u001a2\b\u0010\u0004\u001a\u0004\u0018\u00010\u001a8W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR&\u0010 \u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00058W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\b\"\u0004\b\"\u0010\nR&\u0010$\u001a\u00020#2\u0006\u0010\u0004\u001a\u00020#8W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b\'\u0010(R&\u0010)\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00058W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\b\"\u0004\b+\u0010\nR*\u0010,\u001a\u0004\u0018\u00010\u00052\b\u0010\u0004\u001a\u0004\u0018\u00010\u00058W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b-\u0010\b\"\u0004\b.\u0010\nR&\u0010/\u001a\u00020#2\u0006\u0010\u0004\u001a\u00020#8W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b0\u0010&\"\u0004\b1\u0010(R&\u00102\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00058W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b3\u0010\b\"\u0004\b4\u0010\nR*\u00105\u001a\u0004\u0018\u00010\u00052\b\u0010\u0004\u001a\u0004\u0018\u00010\u00058W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b6\u0010\b\"\u0004\b7\u0010\nR&\u00108\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00058W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b9\u0010\b\"\u0004\b:\u0010\nR&\u0010;\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00058W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b<\u0010\b\"\u0004\b=\u0010\nR&\u0010?\u001a\u00020>2\u0006\u0010\u0004\u001a\u00020>8W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b@\u0010A\"\u0004\bB\u0010C\u00a8\u0006G"}, d2 = {"Lco/yap/sendMoney/addbeneficiary/states/BeneficiaryOverviewState;", "Lco/yap/yapcore/BaseState;", "Lco/yap/sendMoney/addbeneficiary/interfaces/IBeneficiaryOverview$State;", "()V", "value", "", "accountIban", "getAccountIban", "()Ljava/lang/String;", "setAccountIban", "(Ljava/lang/String;)V", "Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;", "beneficiary", "getBeneficiary", "()Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;", "setBeneficiary", "(Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;)V", "country", "getCountry", "setCountry", "countryBankRequirementFieldCode", "getCountryBankRequirementFieldCode", "setCountryBankRequirementFieldCode", "currency", "getCurrency", "setCurrency", "Landroid/graphics/drawable/Drawable;", "drawbleRight", "getDrawbleRight", "()Landroid/graphics/drawable/Drawable;", "setDrawbleRight", "(Landroid/graphics/drawable/Drawable;)V", "firstName", "getFirstName", "setFirstName", "", "flagDrawableResId", "getFlagDrawableResId", "()I", "setFlagDrawableResId", "(I)V", "lastName", "getLastName", "setLastName", "mobile", "getMobile", "setMobile", "mobileNoLength", "getMobileNoLength", "setMobileNoLength", "nickName", "getNickName", "setNickName", "phoneNumber", "getPhoneNumber", "setPhoneNumber", "swiftCode", "getSwiftCode", "setSwiftCode", "transferType", "getTransferType", "setTransferType", "", "valid", "getValid", "()Z", "setValid", "(Z)V", "formateIban", "updateAllFields", "", "sendmoney_release"})
public final class BeneficiaryOverviewState extends co.yap.yapcore.BaseState implements co.yap.sendMoney.addbeneficiary.interfaces.IBeneficiaryOverview.State {
    @org.jetbrains.annotations.Nullable()
    private co.yap.networking.customers.responsedtos.sendmoney.Beneficiary beneficiary;
    private int flagDrawableResId;
    private boolean valid;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String country;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String transferType;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String currency;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String nickName;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String firstName;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String lastName;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String phoneNumber;
    @org.jetbrains.annotations.Nullable()
    private android.graphics.drawable.Drawable drawbleRight;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String mobile;
    private int mobileNoLength;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String accountIban;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String swiftCode;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String countryBankRequirementFieldCode;
    
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
    
    private final void updateAllFields(co.yap.networking.customers.responsedtos.sendmoney.Beneficiary beneficiary) {
    }
    
    private final java.lang.String formateIban(co.yap.networking.customers.responsedtos.sendmoney.Beneficiary beneficiary) {
        return null;
    }
    
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public int getFlagDrawableResId() {
        return 0;
    }
    
    @java.lang.Override()
    public void setFlagDrawableResId(int value) {
    }
    
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public boolean getValid() {
        return false;
    }
    
    @java.lang.Override()
    public void setValid(boolean value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getCountry() {
        return null;
    }
    
    @java.lang.Override()
    public void setCountry(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getTransferType() {
        return null;
    }
    
    @java.lang.Override()
    public void setTransferType(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getCurrency() {
        return null;
    }
    
    @java.lang.Override()
    public void setCurrency(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getNickName() {
        return null;
    }
    
    @java.lang.Override()
    public void setNickName(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getFirstName() {
        return null;
    }
    
    @java.lang.Override()
    public void setFirstName(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getLastName() {
        return null;
    }
    
    @java.lang.Override()
    public void setLastName(@org.jetbrains.annotations.NotNull()
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
    public android.graphics.drawable.Drawable getDrawbleRight() {
        return null;
    }
    
    @java.lang.Override()
    public void setDrawbleRight(@org.jetbrains.annotations.Nullable()
    android.graphics.drawable.Drawable value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getMobile() {
        return null;
    }
    
    @java.lang.Override()
    public void setMobile(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public int getMobileNoLength() {
        return 0;
    }
    
    @java.lang.Override()
    public void setMobileNoLength(int value) {
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
    public java.lang.String getCountryBankRequirementFieldCode() {
        return null;
    }
    
    @java.lang.Override()
    public void setCountryBankRequirementFieldCode(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public BeneficiaryOverviewState() {
        super();
    }
}