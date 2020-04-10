package co.yap.sendMoney.addbeneficiary.interfaces;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u00a8\u0006\u0005"}, d2 = {"Lco/yap/sendMoney/addbeneficiary/interfaces/IBeneficiaryAccountDetails;", "", "State", "View", "ViewModel", "sendmoney_release"})
public abstract interface IBeneficiaryAccountDetails {
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u000f\bf\u0018\u00002\u00020\u0001R\u0018\u0010\u0002\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\t\u0010\u0005\"\u0004\b\n\u0010\u0007R\u0018\u0010\u000b\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\f\u0010\u0005\"\u0004\b\r\u0010\u0007R\u0018\u0010\u000e\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u000f\u0010\u0005\"\u0004\b\u0010\u0010\u0007R\u0018\u0010\u0011\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0012\u0010\u0005\"\u0004\b\u0013\u0010\u0007R\u0018\u0010\u0014\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0015\u0010\u0005\"\u0004\b\u0016\u0010\u0007R\u0018\u0010\u0017\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0018\u0010\u0005\"\u0004\b\u0019\u0010\u0007R\u001e\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u001a\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001e\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b!\u0010\u001d\"\u0004\b\"\u0010\u001fR\u0018\u0010#\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b$\u0010\u0005\"\u0004\b%\u0010\u0007R\u0018\u0010&\u001a\u00020\u001cX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\'\u0010(\"\u0004\b)\u0010*\u00a8\u0006+"}, d2 = {"Lco/yap/sendMoney/addbeneficiary/interfaces/IBeneficiaryAccountDetails$State;", "Lco/yap/yapcore/IBase$State;", "accountIban", "", "getAccountIban", "()Ljava/lang/String;", "setAccountIban", "(Ljava/lang/String;)V", "bankAddress", "getBankAddress", "setBankAddress", "bankName", "getBankName", "setBankName", "bankPhoneNumber", "getBankPhoneNumber", "setBankPhoneNumber", "beneficiaryAccountNumber", "getBeneficiaryAccountNumber", "setBeneficiaryAccountNumber", "countryBankRequirementFieldCode", "getCountryBankRequirementFieldCode", "setCountryBankRequirementFieldCode", "idCode", "getIdCode", "setIdCode", "isIbanMandatory", "Landroidx/databinding/ObservableField;", "", "()Landroidx/databinding/ObservableField;", "setIbanMandatory", "(Landroidx/databinding/ObservableField;)V", "showlyIban", "getShowlyIban", "setShowlyIban", "swiftCode", "getSwiftCode", "setSwiftCode", "valid", "getValid", "()Z", "setValid", "(Z)V", "sendmoney_release"})
    public static abstract interface State extends co.yap.yapcore.IBase.State {
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getAccountIban();
        
        public abstract void setAccountIban(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getCountryBankRequirementFieldCode();
        
        public abstract void setCountryBankRequirementFieldCode(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getBeneficiaryAccountNumber();
        
        public abstract void setBeneficiaryAccountNumber(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getSwiftCode();
        
        public abstract void setSwiftCode(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        public abstract boolean getValid();
        
        public abstract void setValid(boolean p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getBankName();
        
        public abstract void setBankName(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getIdCode();
        
        public abstract void setIdCode(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getBankAddress();
        
        public abstract void setBankAddress(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getBankPhoneNumber();
        
        public abstract void setBankPhoneNumber(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.lang.Boolean> getShowlyIban();
        
        public abstract void setShowlyIban(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.lang.Boolean> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.lang.Boolean> isIbanMandatory();
        
        public abstract void setIbanMandatory(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.lang.Boolean> p0);
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\b\u0010\u001b\u001a\u00020\u001cH&J\u0010\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u001fH&J\b\u0010 \u001a\u00020\u001cH&J\u0010\u0010!\u001a\u00020\u001c2\u0006\u0010\b\u001a\u00020\tH&R\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u0004\u0018\u00010\tX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0018\u0010\u000e\u001a\u00020\u000fX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0018\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00050\u0015X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0016R\u0018\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00050\u0015X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0018\u0010\u0016R\u0018\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00050\u0015X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001a\u0010\u0016\u00a8\u0006\""}, d2 = {"Lco/yap/sendMoney/addbeneficiary/interfaces/IBeneficiaryAccountDetails$ViewModel;", "Lco/yap/yapcore/IBase$ViewModel;", "Lco/yap/sendMoney/addbeneficiary/interfaces/IBeneficiaryAccountDetails$State;", "backButtonPressEvent", "Lco/yap/yapcore/SingleLiveEvent;", "", "getBackButtonPressEvent", "()Lco/yap/yapcore/SingleLiveEvent;", "beneficiary", "Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;", "getBeneficiary", "()Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;", "setBeneficiary", "(Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;)V", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "setClickEvent", "(Lco/yap/yapcore/SingleClickEvent;)V", "isBeneficiaryValid", "Landroidx/lifecycle/MutableLiveData;", "()Landroidx/lifecycle/MutableLiveData;", "otpCreateObserver", "getOtpCreateObserver", "success", "getSuccess", "createBeneficiaryRequest", "", "handlePressOnAddBank", "id", "", "retry", "validateBeneficiaryDetails", "sendmoney_release"})
    public static abstract interface ViewModel extends co.yap.yapcore.IBase.ViewModel<co.yap.sendMoney.addbeneficiary.interfaces.IBeneficiaryAccountDetails.State> {
        
        @org.jetbrains.annotations.Nullable()
        public abstract co.yap.networking.customers.responsedtos.sendmoney.Beneficiary getBeneficiary();
        
        public abstract void setBeneficiary(@org.jetbrains.annotations.Nullable()
        co.yap.networking.customers.responsedtos.sendmoney.Beneficiary p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.yapcore.SingleLiveEvent<java.lang.Boolean> getBackButtonPressEvent();
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.Boolean> getSuccess();
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.Boolean> isBeneficiaryValid();
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.yapcore.SingleClickEvent getClickEvent();
        
        public abstract void setClickEvent(@org.jetbrains.annotations.NotNull()
        co.yap.yapcore.SingleClickEvent p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.Boolean> getOtpCreateObserver();
        
        public abstract void createBeneficiaryRequest();
        
        public abstract void validateBeneficiaryDetails(@org.jetbrains.annotations.NotNull()
        co.yap.networking.customers.responsedtos.sendmoney.Beneficiary beneficiary);
        
        public abstract void handlePressOnAddBank(int id);
        
        public abstract void retry();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001\u00a8\u0006\u0003"}, d2 = {"Lco/yap/sendMoney/addbeneficiary/interfaces/IBeneficiaryAccountDetails$View;", "Lco/yap/yapcore/IBase$View;", "Lco/yap/sendMoney/addbeneficiary/interfaces/IBeneficiaryAccountDetails$ViewModel;", "sendmoney_release"})
    public static abstract interface View extends co.yap.yapcore.IBase.View<co.yap.sendMoney.addbeneficiary.interfaces.IBeneficiaryAccountDetails.ViewModel> {
    }
}