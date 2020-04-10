package co.yap.sendMoney.addbeneficiary.states;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b&\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b/\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u0006\u0010o\u001a\u00020pR*\u0010\b\u001a\u0004\u0018\u00010\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR*\u0010\r\u001a\u0004\u0018\u00010\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\n\"\u0004\b\u000f\u0010\fR*\u0010\u0010\u001a\u0004\u0018\u00010\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\n\"\u0004\b\u0012\u0010\fR*\u0010\u0013\u001a\u0004\u0018\u00010\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\n\"\u0004\b\u0015\u0010\fR*\u0010\u0016\u001a\u0004\u0018\u00010\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\n\"\u0004\b\u0018\u0010\fR*\u0010\u0019\u001a\u0004\u0018\u00010\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\n\"\u0004\b\u001b\u0010\fR*\u0010\u001c\u001a\u0004\u0018\u00010\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\n\"\u0004\b\u001e\u0010\fR&\u0010\u001f\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010\n\"\u0004\b!\u0010\fR&\u0010\"\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\n\"\u0004\b$\u0010\fR&\u0010%\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\n\"\u0004\b\'\u0010\fR&\u0010(\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\n\"\u0004\b*\u0010\fR&\u0010+\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\n\"\u0004\b-\u0010\fR*\u0010/\u001a\u0004\u0018\u00010.2\b\u0010\u0006\u001a\u0004\u0018\u00010.8W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b0\u00101\"\u0004\b2\u00103R&\u00104\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b5\u0010\n\"\u0004\b6\u0010\fR&\u00108\u001a\u0002072\u0006\u0010\u0006\u001a\u0002078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b9\u0010:\"\u0004\b;\u0010<R&\u0010=\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b>\u0010\n\"\u0004\b?\u0010\fR&\u0010@\u001a\u0002072\u0006\u0010\u0006\u001a\u0002078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bA\u0010:\"\u0004\bB\u0010<R*\u0010C\u001a\u0004\u0018\u00010\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bD\u0010\n\"\u0004\bE\u0010\fR*\u0010F\u001a\u0004\u0018\u00010\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bG\u0010\n\"\u0004\bH\u0010\fR&\u0010I\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bJ\u0010\n\"\u0004\bK\u0010\fR*\u0010L\u001a\u0004\u0018\u00010\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bM\u0010\n\"\u0004\bN\u0010\fR&\u0010O\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bP\u0010\n\"\u0004\bQ\u0010\fR&\u0010R\u001a\u0002072\u0006\u0010\u0006\u001a\u0002078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bS\u0010:\"\u0004\bT\u0010<R&\u0010U\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bV\u0010\n\"\u0004\bW\u0010\fR*\u0010X\u001a\u0004\u0018\u00010\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bY\u0010\n\"\u0004\bZ\u0010\fR*\u0010[\u001a\u0004\u0018\u00010\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\\\u0010\n\"\u0004\b]\u0010\fR*\u0010^\u001a\u0004\u0018\u00010\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b_\u0010\n\"\u0004\b`\u0010\fR*\u0010a\u001a\u0004\u0018\u00010\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bb\u0010\n\"\u0004\bc\u0010\fR&\u0010d\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\be\u0010\n\"\u0004\bf\u0010\fR&\u0010h\u001a\u00020g2\u0006\u0010\u0006\u001a\u00020g8W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bi\u0010j\"\u0004\bk\u0010lR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bm\u0010n\u00a8\u0006q"}, d2 = {"Lco/yap/sendMoney/addbeneficiary/states/AddBeneficiaryStates;", "Lco/yap/yapcore/BaseState;", "Lco/yap/sendMoney/addbeneficiary/interfaces/IAddBeneficiary$State;", "viewModel", "Lco/yap/sendMoney/addbeneficiary/viewmodels/AddBeneficiaryViewModel;", "(Lco/yap/sendMoney/addbeneficiary/viewmodels/AddBeneficiaryViewModel;)V", "value", "", "accountNo", "getAccountNo", "()Ljava/lang/String;", "setAccountNo", "(Ljava/lang/String;)V", "accountUuid", "getAccountUuid", "setAccountUuid", "bankName", "getBankName", "setBankName", "beneficiaryId", "getBeneficiaryId", "setBeneficiaryId", "beneficiaryType", "getBeneficiaryType", "setBeneficiaryType", "branchAddress", "getBranchAddress", "setBranchAddress", "branchName", "getBranchName", "setBranchName", "confirmIban", "getConfirmIban", "setConfirmIban", "country", "getCountry", "setCountry", "country2DigitIsoCode", "getCountry2DigitIsoCode", "setCountry2DigitIsoCode", "countryCode", "getCountryCode", "setCountryCode", "currency", "getCurrency", "setCurrency", "Landroid/graphics/drawable/Drawable;", "drawbleRight", "getDrawbleRight", "()Landroid/graphics/drawable/Drawable;", "setDrawbleRight", "(Landroid/graphics/drawable/Drawable;)V", "firstName", "getFirstName", "setFirstName", "", "flagDrawableResId", "getFlagDrawableResId", "()I", "setFlagDrawableResId", "(I)V", "iban", "getIban", "setIban", "id", "getId", "setId", "identifierCode1", "getIdentifierCode1", "setIdentifierCode1", "identifierCode2", "getIdentifierCode2", "setIdentifierCode2", "lastName", "getLastName", "setLastName", "lastUsedDate", "getLastUsedDate", "setLastUsedDate", "mobileNo", "getMobileNo", "setMobileNo", "mobileNoLength", "getMobileNoLength", "setMobileNoLength", "nickName", "getNickName", "setNickName", "otpType", "getOtpType", "setOtpType", "selectedBeneficiaryType", "getSelectedBeneficiaryType", "setSelectedBeneficiaryType", "swiftCode", "getSwiftCode", "setSwiftCode", "title", "getTitle", "setTitle", "transferType", "getTransferType", "setTransferType", "", "valid", "getValid", "()Z", "setValid", "(Z)V", "getViewModel", "()Lco/yap/sendMoney/addbeneficiary/viewmodels/AddBeneficiaryViewModel;", "validate", "", "sendmoney_debug"})
public final class AddBeneficiaryStates extends co.yap.yapcore.BaseState implements co.yap.sendMoney.addbeneficiary.interfaces.IAddBeneficiary.State {
    @org.jetbrains.annotations.Nullable()
    private java.lang.String otpType;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String selectedBeneficiaryType;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String countryCode;
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
    private android.graphics.drawable.Drawable drawbleRight;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String mobileNo;
    private int mobileNoLength;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String country2DigitIsoCode;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String iban;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String confirmIban;
    private int id;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String beneficiaryId;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String accountUuid;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String beneficiaryType;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String title;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String accountNo;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String lastUsedDate;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String swiftCode;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String bankName;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String branchName;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String branchAddress;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String identifierCode1;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String identifierCode2;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.sendMoney.addbeneficiary.viewmodels.AddBeneficiaryViewModel viewModel = null;
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getOtpType() {
        return null;
    }
    
    @java.lang.Override()
    public void setOtpType(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
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
    public java.lang.String getCountryCode() {
        return null;
    }
    
    @java.lang.Override()
    public void setCountryCode(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
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
    public android.graphics.drawable.Drawable getDrawbleRight() {
        return null;
    }
    
    @java.lang.Override()
    public void setDrawbleRight(@org.jetbrains.annotations.Nullable()
    android.graphics.drawable.Drawable value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getMobileNo() {
        return null;
    }
    
    @java.lang.Override()
    public void setMobileNo(@org.jetbrains.annotations.NotNull()
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
    public java.lang.String getCountry2DigitIsoCode() {
        return null;
    }
    
    @java.lang.Override()
    public void setCountry2DigitIsoCode(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getIban() {
        return null;
    }
    
    @java.lang.Override()
    public void setIban(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getConfirmIban() {
        return null;
    }
    
    @java.lang.Override()
    public void setConfirmIban(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public int getId() {
        return 0;
    }
    
    @java.lang.Override()
    public void setId(int value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getBeneficiaryId() {
        return null;
    }
    
    @java.lang.Override()
    public void setBeneficiaryId(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getAccountUuid() {
        return null;
    }
    
    @java.lang.Override()
    public void setAccountUuid(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getBeneficiaryType() {
        return null;
    }
    
    @java.lang.Override()
    public void setBeneficiaryType(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getTitle() {
        return null;
    }
    
    @java.lang.Override()
    public void setTitle(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getAccountNo() {
        return null;
    }
    
    @java.lang.Override()
    public void setAccountNo(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getLastUsedDate() {
        return null;
    }
    
    @java.lang.Override()
    public void setLastUsedDate(@org.jetbrains.annotations.Nullable()
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
    public java.lang.String getBankName() {
        return null;
    }
    
    @java.lang.Override()
    public void setBankName(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getBranchName() {
        return null;
    }
    
    @java.lang.Override()
    public void setBranchName(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getBranchAddress() {
        return null;
    }
    
    @java.lang.Override()
    public void setBranchAddress(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getIdentifierCode1() {
        return null;
    }
    
    @java.lang.Override()
    public void setIdentifierCode1(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getIdentifierCode2() {
        return null;
    }
    
    @java.lang.Override()
    public void setIdentifierCode2(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    public final void validate() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final co.yap.sendMoney.addbeneficiary.viewmodels.AddBeneficiaryViewModel getViewModel() {
        return null;
    }
    
    public AddBeneficiaryStates(@org.jetbrains.annotations.NotNull()
    co.yap.sendMoney.addbeneficiary.viewmodels.AddBeneficiaryViewModel viewModel) {
        super();
    }
}