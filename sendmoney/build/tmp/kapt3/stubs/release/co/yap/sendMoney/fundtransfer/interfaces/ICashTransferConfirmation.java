package co.yap.sendMoney.fundtransfer.interfaces;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u00a8\u0006\u0005"}, d2 = {"Lco/yap/sendMoney/fundtransfer/interfaces/ICashTransferConfirmation;", "", "State", "View", "ViewModel", "sendmoney_release"})
public abstract interface ICashTransferConfirmation {
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\u0004H&\u00a8\u0006\b"}, d2 = {"Lco/yap/sendMoney/fundtransfer/interfaces/ICashTransferConfirmation$View;", "Lco/yap/yapcore/IBase$View;", "Lco/yap/sendMoney/fundtransfer/interfaces/ICashTransferConfirmation$ViewModel;", "addObservers", "", "getViewBinding", "Lco/yap/sendmoney/databinding/FragmentCashTransferConfirmationBinding;", "removeObservers", "sendmoney_release"})
    public static abstract interface View extends co.yap.yapcore.IBase.View<co.yap.sendMoney.fundtransfer.interfaces.ICashTransferConfirmation.ViewModel> {
        
        public abstract void addObservers();
        
        public abstract void removeObservers();
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.sendmoney.databinding.FragmentCashTransferConfirmationBinding getViewBinding();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0012\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH&J\b\u0010\r\u001a\u00020\nH&J\u0010\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u0010H&J\b\u0010\u0011\u001a\u00020\nH&J\u0012\u0010\u0012\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH&R\u0018\u0010\u0003\u001a\u00020\u0004X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\u0013"}, d2 = {"Lco/yap/sendMoney/fundtransfer/interfaces/ICashTransferConfirmation$ViewModel;", "Lco/yap/yapcore/IBase$ViewModel;", "Lco/yap/sendMoney/fundtransfer/interfaces/ICashTransferConfirmation$State;", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "setClickEvent", "(Lco/yap/yapcore/SingleClickEvent;)V", "domesticTransferRequest", "", "beneficiaryId", "", "getCutOffTimeConfiguration", "handlePressOnView", "id", "", "proceedToTransferAmount", "uaeftsTransferRequest", "sendmoney_release"})
    public static abstract interface ViewModel extends co.yap.yapcore.IBase.ViewModel<co.yap.sendMoney.fundtransfer.interfaces.ICashTransferConfirmation.State> {
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.yapcore.SingleClickEvent getClickEvent();
        
        public abstract void setClickEvent(@org.jetbrains.annotations.NotNull()
        co.yap.yapcore.SingleClickEvent p0);
        
        public abstract void handlePressOnView(int id);
        
        public abstract void proceedToTransferAmount();
        
        public abstract void getCutOffTimeConfiguration();
        
        public abstract void uaeftsTransferRequest(@org.jetbrains.annotations.Nullable()
        java.lang.String beneficiaryId);
        
        public abstract void domesticTransferRequest(@org.jetbrains.annotations.Nullable()
        java.lang.String beneficiaryId);
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\r\n\u0002\b\t\bf\u0018\u00002\u00020\u0001R\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u000b\u0010\u0006\"\u0004\b\f\u0010\bR\u001e\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u000e\u0010\u0006\"\u0004\b\u000f\u0010\bR\u001e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\n0\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0011\u0010\u0006\"\u0004\b\u0012\u0010\b\u00a8\u0006\u0013"}, d2 = {"Lco/yap/sendMoney/fundtransfer/interfaces/ICashTransferConfirmation$State;", "Lco/yap/yapcore/IBase$State;", "cutOffTimeMsg", "Landroidx/databinding/ObservableField;", "", "getCutOffTimeMsg", "()Landroidx/databinding/ObservableField;", "setCutOffTimeMsg", "(Landroidx/databinding/ObservableField;)V", "description", "", "getDescription", "setDescription", "productCode", "getProductCode", "setProductCode", "transferFeeDescription", "getTransferFeeDescription", "setTransferFeeDescription", "sendmoney_release"})
    public static abstract interface State extends co.yap.yapcore.IBase.State {
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.lang.CharSequence> getDescription();
        
        public abstract void setDescription(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.lang.CharSequence> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.lang.String> getCutOffTimeMsg();
        
        public abstract void setCutOffTimeMsg(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.lang.String> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.lang.String> getProductCode();
        
        public abstract void setProductCode(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.lang.String> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.lang.CharSequence> getTransferFeeDescription();
        
        public abstract void setTransferFeeDescription(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.lang.CharSequence> p0);
    }
}