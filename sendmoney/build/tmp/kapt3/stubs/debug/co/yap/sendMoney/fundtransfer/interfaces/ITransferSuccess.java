package co.yap.sendmoney.fundtransfer.interfaces;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u00a8\u0006\u0005"}, d2 = {"Lco/yap/sendmoney/fundtransfer/interfaces/ITransferSuccess;", "", "State", "View", "ViewModel", "sendmoney_debug"})
public abstract interface ITransferSuccess {
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\b\u0010\u0003\u001a\u00020\u0004H&\u00a8\u0006\u0005"}, d2 = {"Lco/yap/sendmoney/fundtransfer/interfaces/ITransferSuccess$View;", "Lco/yap/yapcore/IBase$View;", "Lco/yap/sendmoney/fundtransfer/interfaces/ITransferSuccess$ViewModel;", "setObservers", "", "sendmoney_debug"})
    public static abstract interface View extends co.yap.yapcore.IBase.View<co.yap.sendmoney.fundtransfer.interfaces.ITransferSuccess.ViewModel> {
        
        public abstract void setObservers();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\b\u0010\u000e\u001a\u00020\u000fH&J\u0010\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H&R\u0012\u0010\u0003\u001a\u00020\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\u00020\u0004X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\b\u0010\u0006\"\u0004\b\t\u0010\nR\u0018\u0010\u000b\u001a\u00020\u0004X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\f\u0010\u0006\"\u0004\b\r\u0010\n\u00a8\u0006\u0013"}, d2 = {"Lco/yap/sendmoney/fundtransfer/interfaces/ITransferSuccess$ViewModel;", "Lco/yap/yapcore/IBase$ViewModel;", "Lco/yap/sendmoney/fundtransfer/interfaces/ITransferSuccess$State;", "backButtonPressEvent", "Lco/yap/yapcore/SingleClickEvent;", "getBackButtonPressEvent", "()Lco/yap/yapcore/SingleClickEvent;", "clickEvent", "getClickEvent", "setClickEvent", "(Lco/yap/yapcore/SingleClickEvent;)V", "updatedCardBalanceEvent", "getUpdatedCardBalanceEvent", "setUpdatedCardBalanceEvent", "getAccountBalanceRequest", "", "handlePressOnButtonClick", "id", "", "sendmoney_debug"})
    public static abstract interface ViewModel extends co.yap.yapcore.IBase.ViewModel<co.yap.sendmoney.fundtransfer.interfaces.ITransferSuccess.State> {
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.yapcore.SingleClickEvent getBackButtonPressEvent();
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.yapcore.SingleClickEvent getClickEvent();
        
        public abstract void setClickEvent(@org.jetbrains.annotations.NotNull()
        co.yap.yapcore.SingleClickEvent p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.yapcore.SingleClickEvent getUpdatedCardBalanceEvent();
        
        public abstract void setUpdatedCardBalanceEvent(@org.jetbrains.annotations.NotNull()
        co.yap.yapcore.SingleClickEvent p0);
        
        public abstract void handlePressOnButtonClick(int id);
        
        public abstract void getAccountBalanceRequest();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u000b\bf\u0018\u00002\u00020\u0001R\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u0004\u0018\u00010\tX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u000fX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u0004\u0018\u00010\tX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u001b\u0010\u000b\"\u0004\b\u001c\u0010\rR\u001a\u0010\u001d\u001a\u0004\u0018\u00010\tX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u001e\u0010\u000b\"\u0004\b\u001f\u0010\r\u00a8\u0006 "}, d2 = {"Lco/yap/sendmoney/fundtransfer/interfaces/ITransferSuccess$State;", "Lco/yap/yapcore/IBase$State;", "availableBalanceString", "", "getAvailableBalanceString", "()Ljava/lang/CharSequence;", "setAvailableBalanceString", "(Ljava/lang/CharSequence;)V", "buttonTitle", "", "getButtonTitle", "()Ljava/lang/String;", "setButtonTitle", "(Ljava/lang/String;)V", "cutOffMessage", "Landroidx/databinding/ObservableField;", "getCutOffMessage", "()Landroidx/databinding/ObservableField;", "setCutOffMessage", "(Landroidx/databinding/ObservableField;)V", "flagLayoutVisibility", "", "getFlagLayoutVisibility", "()Ljava/lang/Boolean;", "setFlagLayoutVisibility", "(Ljava/lang/Boolean;)V", "successHeader", "getSuccessHeader", "setSuccessHeader", "transferAmountHeading", "getTransferAmountHeading", "setTransferAmountHeading", "sendmoney_debug"})
    public static abstract interface State extends co.yap.yapcore.IBase.State {
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getSuccessHeader();
        
        public abstract void setSuccessHeader(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.Boolean getFlagLayoutVisibility();
        
        public abstract void setFlagLayoutVisibility(@org.jetbrains.annotations.Nullable()
        java.lang.Boolean p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getTransferAmountHeading();
        
        public abstract void setTransferAmountHeading(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getButtonTitle();
        
        public abstract void setButtonTitle(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.CharSequence getAvailableBalanceString();
        
        public abstract void setAvailableBalanceString(@org.jetbrains.annotations.Nullable()
        java.lang.CharSequence p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.lang.String> getCutOffMessage();
        
        public abstract void setCutOffMessage(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.lang.String> p0);
    }
}