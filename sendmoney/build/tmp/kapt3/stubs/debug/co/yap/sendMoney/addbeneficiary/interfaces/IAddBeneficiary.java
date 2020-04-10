package co.yap.sendMoney.addbeneficiary.interfaces;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u00a8\u0006\u0005"}, d2 = {"Lco/yap/sendMoney/addbeneficiary/interfaces/IAddBeneficiary;", "", "State", "View", "ViewModel", "sendmoney_debug"})
public abstract interface IAddBeneficiary {
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b&\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b/\n\u0002\u0010\u000b\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001R\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\t\u0010\u0005\"\u0004\b\n\u0010\u0007R\u001a\u0010\u000b\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\f\u0010\u0005\"\u0004\b\r\u0010\u0007R\u001a\u0010\u000e\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u000f\u0010\u0005\"\u0004\b\u0010\u0010\u0007R\u001a\u0010\u0011\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0012\u0010\u0005\"\u0004\b\u0013\u0010\u0007R\u001a\u0010\u0014\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0015\u0010\u0005\"\u0004\b\u0016\u0010\u0007R\u001a\u0010\u0017\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0018\u0010\u0005\"\u0004\b\u0019\u0010\u0007R\u0018\u0010\u001a\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u001b\u0010\u0005\"\u0004\b\u001c\u0010\u0007R\u0018\u0010\u001d\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u001e\u0010\u0005\"\u0004\b\u001f\u0010\u0007R\u0018\u0010 \u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b!\u0010\u0005\"\u0004\b\"\u0010\u0007R\u0018\u0010#\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b$\u0010\u0005\"\u0004\b%\u0010\u0007R\u0018\u0010&\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\'\u0010\u0005\"\u0004\b(\u0010\u0007R\u001a\u0010)\u001a\u0004\u0018\u00010*X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u0018\u0010/\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b0\u0010\u0005\"\u0004\b1\u0010\u0007R\u0018\u00102\u001a\u000203X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b4\u00105\"\u0004\b6\u00107R\u0018\u00108\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b9\u0010\u0005\"\u0004\b:\u0010\u0007R\u0018\u0010;\u001a\u000203X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b<\u00105\"\u0004\b=\u00107R\u001a\u0010>\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b?\u0010\u0005\"\u0004\b@\u0010\u0007R\u001a\u0010A\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\bB\u0010\u0005\"\u0004\bC\u0010\u0007R\u0018\u0010D\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\bE\u0010\u0005\"\u0004\bF\u0010\u0007R\u001a\u0010G\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\bH\u0010\u0005\"\u0004\bI\u0010\u0007R\u0018\u0010J\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\bK\u0010\u0005\"\u0004\bL\u0010\u0007R\u0018\u0010M\u001a\u000203X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\bN\u00105\"\u0004\bO\u00107R\u0018\u0010P\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\bQ\u0010\u0005\"\u0004\bR\u0010\u0007R\u001a\u0010S\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\bT\u0010\u0005\"\u0004\bU\u0010\u0007R\u001a\u0010V\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\bW\u0010\u0005\"\u0004\bX\u0010\u0007R\u001a\u0010Y\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\bZ\u0010\u0005\"\u0004\b[\u0010\u0007R\u001a\u0010\\\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b]\u0010\u0005\"\u0004\b^\u0010\u0007R\u0018\u0010_\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b`\u0010\u0005\"\u0004\ba\u0010\u0007R\u0018\u0010b\u001a\u00020cX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\bd\u0010e\"\u0004\bf\u0010g\u00a8\u0006h"}, d2 = {"Lco/yap/sendMoney/addbeneficiary/interfaces/IAddBeneficiary$State;", "Lco/yap/yapcore/IBase$State;", "accountNo", "", "getAccountNo", "()Ljava/lang/String;", "setAccountNo", "(Ljava/lang/String;)V", "accountUuid", "getAccountUuid", "setAccountUuid", "bankName", "getBankName", "setBankName", "beneficiaryId", "getBeneficiaryId", "setBeneficiaryId", "beneficiaryType", "getBeneficiaryType", "setBeneficiaryType", "branchAddress", "getBranchAddress", "setBranchAddress", "branchName", "getBranchName", "setBranchName", "confirmIban", "getConfirmIban", "setConfirmIban", "country", "getCountry", "setCountry", "country2DigitIsoCode", "getCountry2DigitIsoCode", "setCountry2DigitIsoCode", "countryCode", "getCountryCode", "setCountryCode", "currency", "getCurrency", "setCurrency", "drawbleRight", "Landroid/graphics/drawable/Drawable;", "getDrawbleRight", "()Landroid/graphics/drawable/Drawable;", "setDrawbleRight", "(Landroid/graphics/drawable/Drawable;)V", "firstName", "getFirstName", "setFirstName", "flagDrawableResId", "", "getFlagDrawableResId", "()I", "setFlagDrawableResId", "(I)V", "iban", "getIban", "setIban", "id", "getId", "setId", "identifierCode1", "getIdentifierCode1", "setIdentifierCode1", "identifierCode2", "getIdentifierCode2", "setIdentifierCode2", "lastName", "getLastName", "setLastName", "lastUsedDate", "getLastUsedDate", "setLastUsedDate", "mobileNo", "getMobileNo", "setMobileNo", "mobileNoLength", "getMobileNoLength", "setMobileNoLength", "nickName", "getNickName", "setNickName", "otpType", "getOtpType", "setOtpType", "selectedBeneficiaryType", "getSelectedBeneficiaryType", "setSelectedBeneficiaryType", "swiftCode", "getSwiftCode", "setSwiftCode", "title", "getTitle", "setTitle", "transferType", "getTransferType", "setTransferType", "valid", "", "getValid", "()Z", "setValid", "(Z)V", "sendmoney_debug"})
    public static abstract interface State extends co.yap.yapcore.IBase.State {
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getCountry();
        
        public abstract void setCountry(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getCountryCode();
        
        public abstract void setCountryCode(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getTransferType();
        
        public abstract void setTransferType(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getCurrency();
        
        public abstract void setCurrency(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getNickName();
        
        public abstract void setNickName(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getFirstName();
        
        public abstract void setFirstName(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getLastName();
        
        public abstract void setLastName(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        public abstract int getFlagDrawableResId();
        
        public abstract void setFlagDrawableResId(int p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getMobileNo();
        
        public abstract void setMobileNo(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getIban();
        
        public abstract void setIban(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getConfirmIban();
        
        public abstract void setConfirmIban(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract android.graphics.drawable.Drawable getDrawbleRight();
        
        public abstract void setDrawbleRight(@org.jetbrains.annotations.Nullable()
        android.graphics.drawable.Drawable p0);
        
        public abstract int getMobileNoLength();
        
        public abstract void setMobileNoLength(int p0);
        
        public abstract boolean getValid();
        
        public abstract void setValid(boolean p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getCountry2DigitIsoCode();
        
        public abstract void setCountry2DigitIsoCode(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        public abstract int getId();
        
        public abstract void setId(int p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getBeneficiaryId();
        
        public abstract void setBeneficiaryId(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getAccountUuid();
        
        public abstract void setAccountUuid(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getBeneficiaryType();
        
        public abstract void setBeneficiaryType(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getTitle();
        
        public abstract void setTitle(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getAccountNo();
        
        public abstract void setAccountNo(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getLastUsedDate();
        
        public abstract void setLastUsedDate(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getSwiftCode();
        
        public abstract void setSwiftCode(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getBankName();
        
        public abstract void setBankName(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getBranchName();
        
        public abstract void setBranchName(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getBranchAddress();
        
        public abstract void setBranchAddress(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getIdentifierCode1();
        
        public abstract void setIdentifierCode1(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getIdentifierCode2();
        
        public abstract void setIdentifierCode2(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getSelectedBeneficiaryType();
        
        public abstract void setSelectedBeneficiaryType(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getOtpType();
        
        public abstract void setOtpType(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\b\u0010\u0018\u001a\u00020\u0019H&J\u0012\u0010\u001a\u001a\u00020\u00192\b\u0010\u001b\u001a\u0004\u0018\u00010\u000bH&J\u0010\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u001eH&J\u0010\u0010\u001f\u001a\u00020\u00192\u0006\u0010 \u001a\u00020!H&J\u0010\u0010\"\u001a\u00020\u00192\u0006\u0010 \u001a\u00020!H&J\u0018\u0010#\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u000b2\u0006\u0010$\u001a\u00020\u001eH&R\u001e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u0004\u0018\u00010\u000bX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u0018\u0010\u0010\u001a\u00020\u0011X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0018\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0007\u00a8\u0006%"}, d2 = {"Lco/yap/sendMoney/addbeneficiary/interfaces/IAddBeneficiary$ViewModel;", "Lco/yap/yapcore/IBase$ViewModel;", "Lco/yap/sendMoney/addbeneficiary/interfaces/IAddBeneficiary$State;", "addBeneficiarySuccess", "Landroidx/lifecycle/MutableLiveData;", "", "getAddBeneficiarySuccess", "()Landroidx/lifecycle/MutableLiveData;", "setAddBeneficiarySuccess", "(Landroidx/lifecycle/MutableLiveData;)V", "beneficiary", "Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;", "getBeneficiary", "()Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;", "setBeneficiary", "(Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;)V", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "setClickEvent", "(Lco/yap/yapcore/SingleClickEvent;)V", "otpCreateObserver", "getOtpCreateObserver", "addCashPickupBeneficiary", "", "addDomesticBeneficiary", "objBeneficiary", "createOtp", "action", "", "handlePressOnAddDomestic", "id", "", "handlePressOnAddNow", "validateBeneficiaryDetails", "otpType", "sendmoney_debug"})
    public static abstract interface ViewModel extends co.yap.yapcore.IBase.ViewModel<co.yap.sendMoney.addbeneficiary.interfaces.IAddBeneficiary.State> {
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.yapcore.SingleClickEvent getClickEvent();
        
        public abstract void setClickEvent(@org.jetbrains.annotations.NotNull()
        co.yap.yapcore.SingleClickEvent p0);
        
        public abstract void handlePressOnAddNow(int id);
        
        public abstract void handlePressOnAddDomestic(int id);
        
        public abstract void addCashPickupBeneficiary();
        
        public abstract void validateBeneficiaryDetails(@org.jetbrains.annotations.NotNull()
        co.yap.networking.customers.responsedtos.sendmoney.Beneficiary objBeneficiary, @org.jetbrains.annotations.NotNull()
        java.lang.String otpType);
        
        public abstract void addDomesticBeneficiary(@org.jetbrains.annotations.Nullable()
        co.yap.networking.customers.responsedtos.sendmoney.Beneficiary objBeneficiary);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.Boolean> getAddBeneficiarySuccess();
        
        public abstract void setAddBeneficiarySuccess(@org.jetbrains.annotations.NotNull()
        androidx.lifecycle.MutableLiveData<java.lang.Boolean> p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract co.yap.networking.customers.responsedtos.sendmoney.Beneficiary getBeneficiary();
        
        public abstract void setBeneficiary(@org.jetbrains.annotations.Nullable()
        co.yap.networking.customers.responsedtos.sendmoney.Beneficiary p0);
        
        public abstract void createOtp(@org.jetbrains.annotations.NotNull()
        java.lang.String action);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.Boolean> getOtpCreateObserver();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001\u00a8\u0006\u0003"}, d2 = {"Lco/yap/sendMoney/addbeneficiary/interfaces/IAddBeneficiary$View;", "Lco/yap/yapcore/IBase$View;", "Lco/yap/sendMoney/addbeneficiary/interfaces/IAddBeneficiary$ViewModel;", "sendmoney_debug"})
    public static abstract interface View extends co.yap.yapcore.IBase.View<co.yap.sendMoney.addbeneficiary.interfaces.IAddBeneficiary.ViewModel> {
    }
}