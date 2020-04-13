package co.yap.sendmoney.fundtransfer.interfaces;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u00a8\u0006\u0005"}, d2 = {"Lco/yap/sendmoney/fundtransfer/interfaces/IBeneficiaryFundTransfer;", "", "State", "View", "ViewModel", "sendmoney_debug"})
public abstract interface IBeneficiaryFundTransfer {
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001\u00a8\u0006\u0003"}, d2 = {"Lco/yap/sendmoney/fundtransfer/interfaces/IBeneficiaryFundTransfer$View;", "Lco/yap/yapcore/IBase$View;", "Lco/yap/sendmoney/fundtransfer/interfaces/IBeneficiaryFundTransfer$ViewModel;", "sendmoney_debug"})
    public static abstract interface View extends co.yap.yapcore.IBase.View<co.yap.sendmoney.fundtransfer.interfaces.IBeneficiaryFundTransfer.ViewModel> {
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0010\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#H&R\u001e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\rR\u001e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0004X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0010\u0010\u0007\"\u0004\b\u0011\u0010\tR\u001a\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u0004X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u001a\u0010\u0007\"\u0004\b\u001b\u0010\tR\u001e\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0004X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u001e\u0010\u0007\"\u0004\b\u001f\u0010\t\u00a8\u0006$"}, d2 = {"Lco/yap/sendmoney/fundtransfer/interfaces/IBeneficiaryFundTransfer$ViewModel;", "Lco/yap/yapcore/IBase$ViewModel;", "Lco/yap/sendmoney/fundtransfer/interfaces/IBeneficiaryFundTransfer$State;", "beneficiary", "Landroidx/lifecycle/MutableLiveData;", "Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;", "getBeneficiary", "()Landroidx/lifecycle/MutableLiveData;", "setBeneficiary", "(Landroidx/lifecycle/MutableLiveData;)V", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "errorEvent", "", "getErrorEvent", "setErrorEvent", "selectedPop", "Lco/yap/networking/transactions/responsedtos/purposepayment/PurposeOfPayment;", "getSelectedPop", "()Lco/yap/networking/transactions/responsedtos/purposepayment/PurposeOfPayment;", "setSelectedPop", "(Lco/yap/networking/transactions/responsedtos/purposepayment/PurposeOfPayment;)V", "transactionThreshold", "Lco/yap/networking/transactions/responsedtos/TransactionThresholdModel;", "getTransactionThreshold", "setTransactionThreshold", "transferData", "Lco/yap/sendmoney/fundtransfer/models/TransferFundData;", "getTransferData", "setTransferData", "handlePressOnView", "", "id", "", "sendmoney_debug"})
    public static abstract interface ViewModel extends co.yap.yapcore.IBase.ViewModel<co.yap.sendmoney.fundtransfer.interfaces.IBeneficiaryFundTransfer.State> {
        
        public abstract void handlePressOnView(int id);
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.yapcore.SingleClickEvent getClickEvent();
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.String> getErrorEvent();
        
        public abstract void setErrorEvent(@org.jetbrains.annotations.NotNull()
        androidx.lifecycle.MutableLiveData<java.lang.String> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<co.yap.networking.customers.responsedtos.sendmoney.Beneficiary> getBeneficiary();
        
        public abstract void setBeneficiary(@org.jetbrains.annotations.NotNull()
        androidx.lifecycle.MutableLiveData<co.yap.networking.customers.responsedtos.sendmoney.Beneficiary> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<co.yap.sendmoney.fundtransfer.models.TransferFundData> getTransferData();
        
        public abstract void setTransferData(@org.jetbrains.annotations.NotNull()
        androidx.lifecycle.MutableLiveData<co.yap.sendmoney.fundtransfer.models.TransferFundData> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<co.yap.networking.transactions.responsedtos.TransactionThresholdModel> getTransactionThreshold();
        
        public abstract void setTransactionThreshold(@org.jetbrains.annotations.NotNull()
        androidx.lifecycle.MutableLiveData<co.yap.networking.transactions.responsedtos.TransactionThresholdModel> p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment getSelectedPop();
        
        public abstract void setSelectedPop(@org.jetbrains.annotations.Nullable()
        co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment p0);
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u000e\bf\u0018\u00002\u00020\u0001R\u0018\u0010\u0002\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\u00020\tX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0018\u0010\u0014\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0015\u0010\u0005\"\u0004\b\u0016\u0010\u0007R\u001a\u0010\u0017\u001a\u0004\u0018\u00010\u000fX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0018\u0010\u0011\"\u0004\b\u0019\u0010\u0013R\u0018\u0010\u001a\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u001b\u0010\u0005\"\u0004\b\u001c\u0010\u0007\u00a8\u0006\u001d"}, d2 = {"Lco/yap/sendmoney/fundtransfer/interfaces/IBeneficiaryFundTransfer$State;", "Lco/yap/yapcore/IBase$State;", "leftIcon", "Landroidx/databinding/ObservableBoolean;", "getLeftIcon", "()Landroidx/databinding/ObservableBoolean;", "setLeftIcon", "(Landroidx/databinding/ObservableBoolean;)V", "position", "", "getPosition", "()I", "setPosition", "(I)V", "rightButtonText", "", "getRightButtonText", "()Ljava/lang/String;", "setRightButtonText", "(Ljava/lang/String;)V", "rightIcon", "getRightIcon", "setRightIcon", "toolBarTitle", "getToolBarTitle", "setToolBarTitle", "toolbarVisibility", "getToolbarVisibility", "setToolbarVisibility", "sendmoney_debug"})
    public static abstract interface State extends co.yap.yapcore.IBase.State {
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableBoolean getToolbarVisibility();
        
        public abstract void setToolbarVisibility(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableBoolean p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableBoolean getRightIcon();
        
        public abstract void setRightIcon(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableBoolean p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableBoolean getLeftIcon();
        
        public abstract void setLeftIcon(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableBoolean p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getRightButtonText();
        
        public abstract void setRightButtonText(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getToolBarTitle();
        
        public abstract void setToolBarTitle(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        public abstract int getPosition();
        
        public abstract void setPosition(int p0);
    }
}