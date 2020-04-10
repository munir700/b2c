package co.yap.sendMoney.editbeneficiary.interfaces;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u00a8\u0006\u0005"}, d2 = {"Lco/yap/sendMoney/editbeneficiary/interfaces/IEditBeneficiary;", "", "State", "View", "ViewModel", "sendmoney_release"})
public abstract interface IEditBeneficiary {
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\b\u0010\u0003\u001a\u00020\u0004H&\u00a8\u0006\u0005"}, d2 = {"Lco/yap/sendMoney/editbeneficiary/interfaces/IEditBeneficiary$View;", "Lco/yap/yapcore/IBase$View;", "Lco/yap/sendMoney/editbeneficiary/interfaces/IEditBeneficiary$ViewModel;", "setObservers", "", "sendmoney_release"})
    public static abstract interface View extends co.yap.yapcore.IBase.View<co.yap.sendMoney.editbeneficiary.interfaces.IEditBeneficiary.ViewModel> {
        
        public abstract void setObservers();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\b\u0010\u0015\u001a\u00020\u0016H&J\u0010\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0019H&J\b\u0010\u001a\u001a\u00020\u0016H&J\b\u0010\u001b\u001a\u00020\u0016H&J\u0010\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u001d\u001a\u00020\u001eH&R\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\t\u0010\f\"\u0004\b\r\u0010\u000eR\u001e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000eR\u001e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0013\u0010\f\"\u0004\b\u0014\u0010\u000e\u00a8\u0006\u001f"}, d2 = {"Lco/yap/sendMoney/editbeneficiary/interfaces/IEditBeneficiary$ViewModel;", "Lco/yap/yapcore/IBase$ViewModel;", "Lco/yap/sendMoney/editbeneficiary/interfaces/IEditBeneficiary$State;", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "setClickEvent", "(Lco/yap/yapcore/SingleClickEvent;)V", "isBeneficiaryValid", "Landroidx/lifecycle/MutableLiveData;", "", "()Landroidx/lifecycle/MutableLiveData;", "setBeneficiaryValid", "(Landroidx/lifecycle/MutableLiveData;)V", "onBeneficiaryCreatedSuccess", "getOnBeneficiaryCreatedSuccess", "setOnBeneficiaryCreatedSuccess", "onUpdateSuccess", "getOnUpdateSuccess", "setOnUpdateSuccess", "createBeneficiaryRequest", "", "handlePressOnConfirm", "id", "", "requestCountryInfo", "requestUpdateBeneficiary", "validateBeneficiaryDetails", "beneficiary", "Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;", "sendmoney_release"})
    public static abstract interface ViewModel extends co.yap.yapcore.IBase.ViewModel<co.yap.sendMoney.editbeneficiary.interfaces.IEditBeneficiary.State> {
        
        @org.jetbrains.annotations.Nullable()
        public abstract co.yap.yapcore.SingleClickEvent getClickEvent();
        
        public abstract void setClickEvent(@org.jetbrains.annotations.Nullable()
        co.yap.yapcore.SingleClickEvent p0);
        
        public abstract void handlePressOnConfirm(int id);
        
        public abstract void requestUpdateBeneficiary();
        
        public abstract void requestCountryInfo();
        
        public abstract void validateBeneficiaryDetails(@org.jetbrains.annotations.NotNull()
        co.yap.networking.customers.responsedtos.sendmoney.Beneficiary beneficiary);
        
        public abstract void createBeneficiaryRequest();
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.Boolean> getOnUpdateSuccess();
        
        public abstract void setOnUpdateSuccess(@org.jetbrains.annotations.NotNull()
        androidx.lifecycle.MutableLiveData<java.lang.Boolean> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.Boolean> isBeneficiaryValid();
        
        public abstract void setBeneficiaryValid(@org.jetbrains.annotations.NotNull()
        androidx.lifecycle.MutableLiveData<java.lang.Boolean> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.Boolean> getOnBeneficiaryCreatedSuccess();
        
        public abstract void setOnBeneficiaryCreatedSuccess(@org.jetbrains.annotations.NotNull()
        androidx.lifecycle.MutableLiveData<java.lang.Boolean> p0);
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\u000b\n\u0002\b\u001a\bf\u0018\u00002\u00020\u0001R\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u0004\u0018\u00010\tX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u000f\u0010\u0005\"\u0004\b\u0010\u0010\u0007R\u001a\u0010\u0011\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0012\u0010\u0005\"\u0004\b\u0013\u0010\u0007R\u001a\u0010\u0014\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0015\u0010\u0005\"\u0004\b\u0016\u0010\u0007R\u001a\u0010\u0017\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0018\u0010\u0005\"\u0004\b\u0019\u0010\u0007R\u001a\u0010\u001a\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u001b\u0010\u0005\"\u0004\b\u001c\u0010\u0007R\u001a\u0010\u001d\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u001e\u0010\u0005\"\u0004\b\u001f\u0010\u0007R\u001a\u0010 \u001a\u0004\u0018\u00010!X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001a\u0010&\u001a\u0004\u0018\u00010!X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\'\u0010#\"\u0004\b(\u0010%R\u001a\u0010)\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b*\u0010\u0005\"\u0004\b+\u0010\u0007R\u001a\u0010,\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b-\u0010\u0005\"\u0004\b.\u0010\u0007R\u001a\u0010/\u001a\u0004\u0018\u00010!X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b0\u0010#\"\u0004\b1\u0010%R\u001a\u00102\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b3\u0010\u0005\"\u0004\b4\u0010\u0007R\u001a\u00105\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b6\u0010\u0005\"\u0004\b7\u0010\u0007R\u001a\u00108\u001a\u0004\u0018\u00010!X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b9\u0010#\"\u0004\b:\u0010%\u00a8\u0006;"}, d2 = {"Lco/yap/sendMoney/editbeneficiary/interfaces/IEditBeneficiary$State;", "Lco/yap/yapcore/IBase$State;", "accountNumber", "", "getAccountNumber", "()Ljava/lang/String;", "setAccountNumber", "(Ljava/lang/String;)V", "beneficiary", "Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;", "getBeneficiary", "()Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;", "setBeneficiary", "(Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;)V", "country", "getCountry", "setCountry", "countryBankRequirementFieldCode", "getCountryBankRequirementFieldCode", "setCountryBankRequirementFieldCode", "countryCode", "getCountryCode", "setCountryCode", "currency", "getCurrency", "setCurrency", "firstName", "getFirstName", "setFirstName", "lastName", "getLastName", "setLastName", "needIban", "", "getNeedIban", "()Ljava/lang/Boolean;", "setNeedIban", "(Ljava/lang/Boolean;)V", "needOverView", "getNeedOverView", "setNeedOverView", "nickName", "getNickName", "setNickName", "phoneNumber", "getPhoneNumber", "setPhoneNumber", "showIban", "getShowIban", "setShowIban", "swiftCode", "getSwiftCode", "setSwiftCode", "transferType", "getTransferType", "setTransferType", "valid", "getValid", "setValid", "sendmoney_release"})
    public static abstract interface State extends co.yap.yapcore.IBase.State {
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getCountry();
        
        public abstract void setCountry(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getCountryCode();
        
        public abstract void setCountryCode(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getTransferType();
        
        public abstract void setTransferType(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getCurrency();
        
        public abstract void setCurrency(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getNickName();
        
        public abstract void setNickName(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getFirstName();
        
        public abstract void setFirstName(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getLastName();
        
        public abstract void setLastName(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getPhoneNumber();
        
        public abstract void setPhoneNumber(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getAccountNumber();
        
        public abstract void setAccountNumber(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getSwiftCode();
        
        public abstract void setSwiftCode(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getCountryBankRequirementFieldCode();
        
        public abstract void setCountryBankRequirementFieldCode(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract co.yap.networking.customers.responsedtos.sendmoney.Beneficiary getBeneficiary();
        
        public abstract void setBeneficiary(@org.jetbrains.annotations.Nullable()
        co.yap.networking.customers.responsedtos.sendmoney.Beneficiary p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.Boolean getNeedOverView();
        
        public abstract void setNeedOverView(@org.jetbrains.annotations.Nullable()
        java.lang.Boolean p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.Boolean getNeedIban();
        
        public abstract void setNeedIban(@org.jetbrains.annotations.Nullable()
        java.lang.Boolean p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.Boolean getShowIban();
        
        public abstract void setShowIban(@org.jetbrains.annotations.Nullable()
        java.lang.Boolean p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.Boolean getValid();
        
        public abstract void setValid(@org.jetbrains.annotations.Nullable()
        java.lang.Boolean p0);
    }
}