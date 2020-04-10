package co.yap.sendMoney.addbeneficiary.interfaces;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u00a8\u0006\u0005"}, d2 = {"Lco/yap/sendMoney/addbeneficiary/interfaces/IBankDetails;", "", "State", "View", "ViewModel", "sendmoney_debug"})
public abstract interface IBankDetails {
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0010\bf\u0018\u00002\u00020\u0001R\u0018\u0010\u0002\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\t\u0010\u0005\"\u0004\b\n\u0010\u0007R\u0018\u0010\u000b\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\f\u0010\u0005\"\u0004\b\r\u0010\u0007R\u0018\u0010\u000e\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u000f\u0010\u0005\"\u0004\b\u0010\u0010\u0007R\u0018\u0010\u0011\u001a\u00020\u0012X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00120\u0018X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0017\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u001d\u0010\u0005\"\u0004\b\u001e\u0010\u0007R\u0018\u0010\u001f\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b \u0010\u0005\"\u0004\b!\u0010\u0007R\u001e\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00030\u0018X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b#\u0010\u0019\"\u0004\b$\u0010\u001bR\u0018\u0010%\u001a\u00020\u0012X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b&\u0010\u0014\"\u0004\b\'\u0010\u0016\u00a8\u0006("}, d2 = {"Lco/yap/sendMoney/addbeneficiary/interfaces/IBankDetails$State;", "Lco/yap/yapcore/IBase$State;", "bankBranch", "", "getBankBranch", "()Ljava/lang/String;", "setBankBranch", "(Ljava/lang/String;)V", "bankCity", "getBankCity", "setBankCity", "bankName", "getBankName", "setBankName", "buttonText", "getButtonText", "setButtonText", "hideSwiftSection", "", "getHideSwiftSection", "()Z", "setHideSwiftSection", "(Z)V", "isRmt", "Landroidx/databinding/ObservableField;", "()Landroidx/databinding/ObservableField;", "setRmt", "(Landroidx/databinding/ObservableField;)V", "selectedBeneficiaryType", "getSelectedBeneficiaryType", "setSelectedBeneficiaryType", "swiftCode", "getSwiftCode", "setSwiftCode", "txtCount", "getTxtCount", "setTxtCount", "valid", "getValid", "setValid", "sendmoney_debug"})
    public static abstract interface State extends co.yap.yapcore.IBase.State {
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getBankName();
        
        public abstract void setBankName(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getBankBranch();
        
        public abstract void setBankBranch(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getBankCity();
        
        public abstract void setBankCity(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getSwiftCode();
        
        public abstract void setSwiftCode(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        public abstract boolean getValid();
        
        public abstract void setValid(boolean p0);
        
        public abstract boolean getHideSwiftSection();
        
        public abstract void setHideSwiftSection(boolean p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.lang.Boolean> isRmt();
        
        public abstract void setRmt(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.lang.Boolean> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getButtonText();
        
        public abstract void setButtonText(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.lang.String> getTxtCount();
        
        public abstract void setTxtCount(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.lang.String> p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getSelectedBeneficiaryType();
        
        public abstract void setSelectedBeneficiaryType(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH&J\b\u0010\u001c\u001a\u00020\u0019H&J\u0010\u0010\u001d\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00020\u001fH&R$\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR$\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00050\fX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0018\u0010\u0012\u001a\u00020\u0013X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017\u00a8\u0006 "}, d2 = {"Lco/yap/sendMoney/addbeneficiary/interfaces/IBankDetails$ViewModel;", "Lco/yap/yapcore/IBase$ViewModel;", "Lco/yap/sendMoney/addbeneficiary/interfaces/IBankDetails$State;", "bankList", "Landroidx/lifecycle/MutableLiveData;", "", "Lco/yap/networking/customers/responsedtos/sendmoney/RAKBank$Bank;", "getBankList", "()Landroidx/lifecycle/MutableLiveData;", "setBankList", "(Landroidx/lifecycle/MutableLiveData;)V", "bankParams", "Landroidx/databinding/ObservableField;", "Lco/yap/networking/customers/responsedtos/beneficiary/BankParams;", "getBankParams", "()Landroidx/databinding/ObservableField;", "setBankParams", "(Landroidx/databinding/ObservableField;)V", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "setClickEvent", "(Lco/yap/yapcore/SingleClickEvent;)V", "handlePressOnView", "", "id", "", "retry", "searchRMTBanks", "otherBankQuery", "Lco/yap/networking/customers/requestdtos/OtherBankQuery;", "sendmoney_debug"})
    public static abstract interface ViewModel extends co.yap.yapcore.IBase.ViewModel<co.yap.sendMoney.addbeneficiary.interfaces.IBankDetails.State> {
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.yapcore.SingleClickEvent getClickEvent();
        
        public abstract void setClickEvent(@org.jetbrains.annotations.NotNull()
        co.yap.yapcore.SingleClickEvent p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.util.List<co.yap.networking.customers.responsedtos.beneficiary.BankParams>> getBankParams();
        
        public abstract void setBankParams(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.util.List<co.yap.networking.customers.responsedtos.beneficiary.BankParams>> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.util.List<co.yap.networking.customers.responsedtos.sendmoney.RAKBank.Bank>> getBankList();
        
        public abstract void setBankList(@org.jetbrains.annotations.NotNull()
        androidx.lifecycle.MutableLiveData<java.util.List<co.yap.networking.customers.responsedtos.sendmoney.RAKBank.Bank>> p0);
        
        public abstract void handlePressOnView(int id);
        
        public abstract void searchRMTBanks(@org.jetbrains.annotations.NotNull()
        co.yap.networking.customers.requestdtos.OtherBankQuery otherBankQuery);
        
        public abstract void retry();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001\u00a8\u0006\u0003"}, d2 = {"Lco/yap/sendMoney/addbeneficiary/interfaces/IBankDetails$View;", "Lco/yap/yapcore/IBase$View;", "Lco/yap/sendMoney/addbeneficiary/interfaces/IBankDetails$ViewModel;", "sendmoney_debug"})
    public static abstract interface View extends co.yap.yapcore.IBase.View<co.yap.sendMoney.addbeneficiary.interfaces.IBankDetails.ViewModel> {
    }
}