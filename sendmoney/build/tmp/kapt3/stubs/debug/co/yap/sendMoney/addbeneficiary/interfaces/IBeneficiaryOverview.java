package co.yap.sendMoney.addbeneficiary.interfaces;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u00a8\u0006\u0005"}, d2 = {"Lco/yap/sendMoney/addbeneficiary/interfaces/IBeneficiaryOverview;", "", "State", "View", "ViewModel", "sendmoney_debug"})
public abstract interface IBeneficiaryOverview {
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u001a\n\u0002\u0010\u000b\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001R\u0018\u0010\u0002\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u0004\u0018\u00010\tX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0018\u0010\u000e\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u000f\u0010\u0005\"\u0004\b\u0010\u0010\u0007R\u0018\u0010\u0011\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0012\u0010\u0005\"\u0004\b\u0013\u0010\u0007R\u0018\u0010\u0014\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0015\u0010\u0005\"\u0004\b\u0016\u0010\u0007R\u001a\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u0018\u0010\u001d\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u001e\u0010\u0005\"\u0004\b\u001f\u0010\u0007R\u0018\u0010 \u001a\u00020!X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u0018\u0010&\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\'\u0010\u0005\"\u0004\b(\u0010\u0007R\u001a\u0010)\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b*\u0010\u0005\"\u0004\b+\u0010\u0007R\u0018\u0010,\u001a\u00020!X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b-\u0010#\"\u0004\b.\u0010%R\u0018\u0010/\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b0\u0010\u0005\"\u0004\b1\u0010\u0007R\u001a\u00102\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b3\u0010\u0005\"\u0004\b4\u0010\u0007R\u0018\u00105\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b6\u0010\u0005\"\u0004\b7\u0010\u0007R\u0018\u00108\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b9\u0010\u0005\"\u0004\b:\u0010\u0007R\u0018\u0010;\u001a\u00020<X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b=\u0010>\"\u0004\b?\u0010@\u00a8\u0006A"}, d2 = {"Lco/yap/sendMoney/addbeneficiary/interfaces/IBeneficiaryOverview$State;", "Lco/yap/yapcore/IBase$State;", "accountIban", "", "getAccountIban", "()Ljava/lang/String;", "setAccountIban", "(Ljava/lang/String;)V", "beneficiary", "Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;", "getBeneficiary", "()Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;", "setBeneficiary", "(Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;)V", "country", "getCountry", "setCountry", "countryBankRequirementFieldCode", "getCountryBankRequirementFieldCode", "setCountryBankRequirementFieldCode", "currency", "getCurrency", "setCurrency", "drawbleRight", "Landroid/graphics/drawable/Drawable;", "getDrawbleRight", "()Landroid/graphics/drawable/Drawable;", "setDrawbleRight", "(Landroid/graphics/drawable/Drawable;)V", "firstName", "getFirstName", "setFirstName", "flagDrawableResId", "", "getFlagDrawableResId", "()I", "setFlagDrawableResId", "(I)V", "lastName", "getLastName", "setLastName", "mobile", "getMobile", "setMobile", "mobileNoLength", "getMobileNoLength", "setMobileNoLength", "nickName", "getNickName", "setNickName", "phoneNumber", "getPhoneNumber", "setPhoneNumber", "swiftCode", "getSwiftCode", "setSwiftCode", "transferType", "getTransferType", "setTransferType", "valid", "", "getValid", "()Z", "setValid", "(Z)V", "sendmoney_debug"})
    public static abstract interface State extends co.yap.yapcore.IBase.State {
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getCountry();
        
        public abstract void setCountry(@org.jetbrains.annotations.NotNull()
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
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getPhoneNumber();
        
        public abstract void setPhoneNumber(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        public abstract int getFlagDrawableResId();
        
        public abstract void setFlagDrawableResId(int p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getMobile();
        
        public abstract void setMobile(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract android.graphics.drawable.Drawable getDrawbleRight();
        
        public abstract void setDrawbleRight(@org.jetbrains.annotations.Nullable()
        android.graphics.drawable.Drawable p0);
        
        public abstract int getMobileNoLength();
        
        public abstract void setMobileNoLength(int p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getAccountIban();
        
        public abstract void setAccountIban(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getCountryBankRequirementFieldCode();
        
        public abstract void setCountryBankRequirementFieldCode(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getSwiftCode();
        
        public abstract void setSwiftCode(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        public abstract boolean getValid();
        
        public abstract void setValid(boolean p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract co.yap.networking.customers.responsedtos.sendmoney.Beneficiary getBeneficiary();
        
        public abstract void setBeneficiary(@org.jetbrains.annotations.Nullable()
        co.yap.networking.customers.responsedtos.sendmoney.Beneficiary p0);
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0016H&J\u0010\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0016H&J\b\u0010\u001d\u001a\u00020\u001aH&R\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\u00020\tX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0018\u0010\u000e\u001a\u00020\u000fX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0018\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018\u00a8\u0006\u001e"}, d2 = {"Lco/yap/sendMoney/addbeneficiary/interfaces/IBeneficiaryOverview$ViewModel;", "Lco/yap/yapcore/IBase$ViewModel;", "Lco/yap/sendMoney/addbeneficiary/interfaces/IBeneficiaryOverview$State;", "backButtonPressEvent", "Lco/yap/yapcore/SingleLiveEvent;", "", "getBackButtonPressEvent", "()Lco/yap/yapcore/SingleLiveEvent;", "beneficiary", "Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;", "getBeneficiary", "()Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;", "setBeneficiary", "(Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;)V", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "setClickEvent", "(Lco/yap/yapcore/SingleClickEvent;)V", "onDeleteSuccess", "Landroidx/lifecycle/MutableLiveData;", "", "getOnDeleteSuccess", "()Landroidx/lifecycle/MutableLiveData;", "handlePressOnAddNow", "", "id", "handlePressOnConfirm", "requestUpdateBeneficiary", "sendmoney_debug"})
    public static abstract interface ViewModel extends co.yap.yapcore.IBase.ViewModel<co.yap.sendMoney.addbeneficiary.interfaces.IBeneficiaryOverview.State> {
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.yapcore.SingleLiveEvent<java.lang.Boolean> getBackButtonPressEvent();
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.yapcore.SingleClickEvent getClickEvent();
        
        public abstract void setClickEvent(@org.jetbrains.annotations.NotNull()
        co.yap.yapcore.SingleClickEvent p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.networking.customers.responsedtos.sendmoney.Beneficiary getBeneficiary();
        
        public abstract void setBeneficiary(@org.jetbrains.annotations.NotNull()
        co.yap.networking.customers.responsedtos.sendmoney.Beneficiary p0);
        
        public abstract void handlePressOnAddNow(int id);
        
        public abstract void handlePressOnConfirm(int id);
        
        public abstract void requestUpdateBeneficiary();
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.Integer> getOnDeleteSuccess();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001\u00a8\u0006\u0003"}, d2 = {"Lco/yap/sendMoney/addbeneficiary/interfaces/IBeneficiaryOverview$View;", "Lco/yap/yapcore/IBase$View;", "Lco/yap/sendMoney/addbeneficiary/interfaces/IBeneficiaryOverview$ViewModel;", "sendmoney_debug"})
    public static abstract interface View extends co.yap.yapcore.IBase.View<co.yap.sendMoney.addbeneficiary.interfaces.IBeneficiaryOverview.ViewModel> {
    }
}