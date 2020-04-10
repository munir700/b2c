package co.yap.sendMoney.fundtransfer.interfaces;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u00a8\u0006\u0005"}, d2 = {"Lco/yap/sendMoney/fundtransfer/interfaces/IInternationalTransactionConfirmation;", "", "State", "View", "ViewModel", "sendmoney_release"})
public abstract interface IInternationalTransactionConfirmation {
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0004H&\u00a8\u0006\u0006"}, d2 = {"Lco/yap/sendMoney/fundtransfer/interfaces/IInternationalTransactionConfirmation$View;", "Lco/yap/yapcore/IBase$View;", "Lco/yap/sendMoney/fundtransfer/interfaces/IInternationalTransactionConfirmation$ViewModel;", "setData", "", "setObservers", "sendmoney_release"})
    public static abstract interface View extends co.yap.yapcore.IBase.View<co.yap.sendMoney.fundtransfer.interfaces.IInternationalTransactionConfirmation.ViewModel> {
        
        public abstract void setObservers();
        
        public abstract void setData();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\b\u0010\u000b\u001a\u00020\fH&J\u0010\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH&J\b\u0010\u0010\u001a\u00020\fH&J\b\u0010\u0011\u001a\u00020\fH&J\u0012\u0010\u0012\u001a\u00020\f2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H&J\u0012\u0010\u0015\u001a\u00020\f2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H&R\u0012\u0010\u0003\u001a\u00020\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\n\u00a8\u0006\u0016"}, d2 = {"Lco/yap/sendMoney/fundtransfer/interfaces/IInternationalTransactionConfirmation$ViewModel;", "Lco/yap/yapcore/IBase$ViewModel;", "Lco/yap/sendMoney/fundtransfer/interfaces/IInternationalTransactionConfirmation$State;", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "isOtpRequired", "Landroidx/lifecycle/MutableLiveData;", "", "()Landroidx/lifecycle/MutableLiveData;", "getCutOffTimeConfiguration", "", "handlePressOnButtonClick", "id", "", "proceedToTransferAmount", "requestForTransfer", "rmtTransferRequest", "beneficiaryId", "", "swiftTransferRequest", "sendmoney_release"})
    public static abstract interface ViewModel extends co.yap.yapcore.IBase.ViewModel<co.yap.sendMoney.fundtransfer.interfaces.IInternationalTransactionConfirmation.State> {
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.yapcore.SingleClickEvent getClickEvent();
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.Boolean> isOtpRequired();
        
        public abstract void handlePressOnButtonClick(int id);
        
        public abstract void rmtTransferRequest(@org.jetbrains.annotations.Nullable()
        java.lang.String beneficiaryId);
        
        public abstract void swiftTransferRequest(@org.jetbrains.annotations.Nullable()
        java.lang.String beneficiaryId);
        
        public abstract void requestForTransfer();
        
        public abstract void proceedToTransferAmount();
        
        public abstract void getCutOffTimeConfiguration();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\r\n\u0002\b\u000b\bf\u0018\u00002\u00020\u0001R\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\t\u0010\u0005\"\u0004\b\n\u0010\u0007R\u001a\u0010\u000b\u001a\u0004\u0018\u00010\fX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u0004\u0018\u00010\fX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0012\u0010\u000e\"\u0004\b\u0013\u0010\u0010R\u001a\u0010\u0014\u001a\u0004\u0018\u00010\fX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0015\u0010\u000e\"\u0004\b\u0016\u0010\u0010\u00a8\u0006\u0017"}, d2 = {"Lco/yap/sendMoney/fundtransfer/interfaces/IInternationalTransactionConfirmation$State;", "Lco/yap/yapcore/IBase$State;", "confirmHeading", "", "getConfirmHeading", "()Ljava/lang/String;", "setConfirmHeading", "(Ljava/lang/String;)V", "cutOffTimeMsg", "getCutOffTimeMsg", "setCutOffTimeMsg", "receivingAmountDescription", "", "getReceivingAmountDescription", "()Ljava/lang/CharSequence;", "setReceivingAmountDescription", "(Ljava/lang/CharSequence;)V", "transferDescription", "getTransferDescription", "setTransferDescription", "transferFeeDescription", "getTransferFeeDescription", "setTransferFeeDescription", "sendmoney_release"})
    public static abstract interface State extends co.yap.yapcore.IBase.State {
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.CharSequence getTransferDescription();
        
        public abstract void setTransferDescription(@org.jetbrains.annotations.Nullable()
        java.lang.CharSequence p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getConfirmHeading();
        
        public abstract void setConfirmHeading(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.CharSequence getReceivingAmountDescription();
        
        public abstract void setReceivingAmountDescription(@org.jetbrains.annotations.Nullable()
        java.lang.CharSequence p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.CharSequence getTransferFeeDescription();
        
        public abstract void setTransferFeeDescription(@org.jetbrains.annotations.Nullable()
        java.lang.CharSequence p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getCutOffTimeMsg();
        
        public abstract void setCutOffTimeMsg(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
    }
}