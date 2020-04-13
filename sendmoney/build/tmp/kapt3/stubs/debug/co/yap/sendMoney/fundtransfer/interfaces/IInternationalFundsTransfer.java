package co.yap.sendmoney.fundtransfer.interfaces;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u00a8\u0006\u0005"}, d2 = {"Lco/yap/sendmoney/fundtransfer/interfaces/IInternationalFundsTransfer;", "", "State", "View", "ViewModel", "sendmoney_debug"})
public abstract interface IInternationalFundsTransfer {
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0013\n\u0002\u0010\u0006\n\u0002\b\u0017\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010;\u001a\u00020<H&R\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u001e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0018\u0010\u000f\u001a\u00020\nX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u0004\u0018\u00010\nX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0015\u0010\u0011\"\u0004\b\u0016\u0010\u0013R\u001a\u0010\u0017\u001a\u0004\u0018\u00010\nX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0018\u0010\u0011\"\u0004\b\u0019\u0010\u0013R\u001a\u0010\u001a\u001a\u0004\u0018\u00010\nX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u001b\u0010\u0011\"\u0004\b\u001c\u0010\u0013R\u001a\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001a\u0010#\u001a\u0004\u0018\u00010\u001eX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b$\u0010 \"\u0004\b%\u0010\"R\u001e\u0010&\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\'\u0010\f\"\u0004\b(\u0010\u000eR\u001a\u0010)\u001a\u0004\u0018\u00010\nX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b*\u0010\u0011\"\u0004\b+\u0010\u0013R\u001e\u0010,\u001a\b\u0012\u0004\u0012\u00020\u001e0\tX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b-\u0010\f\"\u0004\b.\u0010\u000eR\u001e\u0010/\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b0\u0010\f\"\u0004\b1\u0010\u000eR\u001a\u00102\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b3\u0010\u0005\"\u0004\b4\u0010\u0007R\u0018\u00105\u001a\u000206X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b7\u00108\"\u0004\b9\u0010:\u00a8\u0006="}, d2 = {"Lco/yap/sendmoney/fundtransfer/interfaces/IInternationalFundsTransfer$State;", "Lco/yap/yapcore/IBase$State;", "availableBalanceString", "", "getAvailableBalanceString", "()Ljava/lang/CharSequence;", "setAvailableBalanceString", "(Ljava/lang/CharSequence;)V", "destinationCurrency", "Landroidx/databinding/ObservableField;", "", "getDestinationCurrency", "()Landroidx/databinding/ObservableField;", "setDestinationCurrency", "(Landroidx/databinding/ObservableField;)V", "errorDescription", "getErrorDescription", "()Ljava/lang/String;", "setErrorDescription", "(Ljava/lang/String;)V", "etInputAmount", "getEtInputAmount", "setEtInputAmount", "etOutputAmount", "getEtOutputAmount", "setEtOutputAmount", "fromFxRate", "getFromFxRate", "setFromFxRate", "maxLimit", "", "getMaxLimit", "()Ljava/lang/Double;", "setMaxLimit", "(Ljava/lang/Double;)V", "minLimit", "getMinLimit", "setMinLimit", "sourceCurrency", "getSourceCurrency", "setSourceCurrency", "toFxRate", "getToFxRate", "setToFxRate", "totalTransferAmount", "getTotalTransferAmount", "setTotalTransferAmount", "transactionNote", "getTransactionNote", "setTransactionNote", "transferFeeSpannable", "getTransferFeeSpannable", "setTransferFeeSpannable", "valid", "", "getValid", "()Z", "setValid", "(Z)V", "clearError", "", "sendmoney_debug"})
    public static abstract interface State extends co.yap.yapcore.IBase.State {
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.lang.String> getTransactionNote();
        
        public abstract void setTransactionNote(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.lang.String> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.lang.String> getSourceCurrency();
        
        public abstract void setSourceCurrency(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.lang.String> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.lang.String> getDestinationCurrency();
        
        public abstract void setDestinationCurrency(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.lang.String> p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.CharSequence getTransferFeeSpannable();
        
        public abstract void setTransferFeeSpannable(@org.jetbrains.annotations.Nullable()
        java.lang.CharSequence p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getEtInputAmount();
        
        public abstract void setEtInputAmount(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getEtOutputAmount();
        
        public abstract void setEtOutputAmount(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getFromFxRate();
        
        public abstract void setFromFxRate(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getToFxRate();
        
        public abstract void setToFxRate(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        public abstract boolean getValid();
        
        public abstract void setValid(boolean p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.Double getMaxLimit();
        
        public abstract void setMaxLimit(@org.jetbrains.annotations.Nullable()
        java.lang.Double p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.Double getMinLimit();
        
        public abstract void setMinLimit(@org.jetbrains.annotations.Nullable()
        java.lang.Double p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.lang.Double> getTotalTransferAmount();
        
        public abstract void setTotalTransferAmount(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.lang.Double> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getErrorDescription();
        
        public abstract void setErrorDescription(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.CharSequence getAvailableBalanceString();
        
        public abstract void setAvailableBalanceString(@org.jetbrains.annotations.Nullable()
        java.lang.CharSequence p0);
        
        public abstract void clearError();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\b\u0010.\u001a\u00020/H&J\u0012\u00100\u001a\u00020/2\b\u00101\u001a\u0004\u0018\u000102H&J\u0012\u00103\u001a\u00020/2\b\u00101\u001a\u0004\u0018\u000102H&J\u0012\u00104\u001a\u00020/2\b\u00101\u001a\u0004\u0018\u000102H&J\u0012\u00105\u001a\u00020/2\b\u00101\u001a\u0004\u0018\u000102H&J\b\u00106\u001a\u00020/H&J\u0010\u00107\u001a\u00020/2\u0006\u00108\u001a\u00020 H&R\u0018\u0010\u0003\u001a\u00020\u0004X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u0011X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0017\u0010\u0014\"\u0004\b\u0019\u0010\u0016R(\u0010\u001a\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u001c0\u001bj\b\u0012\u0004\u0012\u00020\u001c`\u001d0\u0011X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001e\u0010\u0014R\u0018\u0010\u001f\u001a\u00020 X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R(\u0010%\u001a\u0012\u0012\u0004\u0012\u00020\u001c0\u001bj\b\u0012\u0004\u0012\u00020\u001c`\u001dX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b&\u0010\'\"\u0004\b(\u0010)R\u001e\u0010*\u001a\b\u0012\u0004\u0012\u00020+0\u0011X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b,\u0010\u0014\"\u0004\b-\u0010\u0016\u00a8\u00069"}, d2 = {"Lco/yap/sendmoney/fundtransfer/interfaces/IInternationalFundsTransfer$ViewModel;", "Lco/yap/yapcore/IBase$ViewModel;", "Lco/yap/sendmoney/fundtransfer/interfaces/IInternationalFundsTransfer$State;", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "setClickEvent", "(Lco/yap/yapcore/SingleClickEvent;)V", "feeTiers", "", "Lco/yap/networking/transactions/responsedtos/transaction/RemittanceFeeResponse$RemittanceFee$TierRateDTO;", "getFeeTiers", "()Ljava/util/List;", "setFeeTiers", "(Ljava/util/List;)V", "fxRateResponse", "Landroidx/lifecycle/MutableLiveData;", "Lco/yap/networking/transactions/responsedtos/transaction/FxRateResponse$Data;", "getFxRateResponse", "()Landroidx/lifecycle/MutableLiveData;", "setFxRateResponse", "(Landroidx/lifecycle/MutableLiveData;)V", "isAPIFailed", "", "setAPIFailed", "populateSpinnerData", "Ljava/util/ArrayList;", "Lco/yap/networking/transactions/responsedtos/InternationalFundsTransferReasonList$ReasonList;", "Lkotlin/collections/ArrayList;", "getPopulateSpinnerData", "reasonPosition", "", "getReasonPosition", "()I", "setReasonPosition", "(I)V", "transactionData", "getTransactionData", "()Ljava/util/ArrayList;", "setTransactionData", "(Ljava/util/ArrayList;)V", "transactionFeeResponse", "Lco/yap/networking/transactions/responsedtos/transaction/RemittanceFeeResponse$RemittanceFee;", "getTransactionFeeResponse", "setTransactionFeeResponse", "getCountryLimits", "", "getMoneyTransferLimits", "productCode", "", "getReasonList", "getTransactionFeeInternational", "getTransactionInternationalfxList", "getTransactionThresholds", "handlePressOnButton", "id", "sendmoney_debug"})
    public static abstract interface ViewModel extends co.yap.yapcore.IBase.ViewModel<co.yap.sendmoney.fundtransfer.interfaces.IInternationalFundsTransfer.State> {
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.yapcore.SingleClickEvent getClickEvent();
        
        public abstract void setClickEvent(@org.jetbrains.annotations.NotNull()
        co.yap.yapcore.SingleClickEvent p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.Boolean> isAPIFailed();
        
        public abstract void setAPIFailed(@org.jetbrains.annotations.NotNull()
        androidx.lifecycle.MutableLiveData<java.lang.Boolean> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.util.ArrayList<co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList.ReasonList> getTransactionData();
        
        public abstract void setTransactionData(@org.jetbrains.annotations.NotNull()
        java.util.ArrayList<co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList.ReasonList> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.util.ArrayList<co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList.ReasonList>> getPopulateSpinnerData();
        
        public abstract int getReasonPosition();
        
        public abstract void setReasonPosition(int p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse.RemittanceFee> getTransactionFeeResponse();
        
        public abstract void setTransactionFeeResponse(@org.jetbrains.annotations.NotNull()
        androidx.lifecycle.MutableLiveData<co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse.RemittanceFee> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<co.yap.networking.transactions.responsedtos.transaction.FxRateResponse.Data> getFxRateResponse();
        
        public abstract void setFxRateResponse(@org.jetbrains.annotations.NotNull()
        androidx.lifecycle.MutableLiveData<co.yap.networking.transactions.responsedtos.transaction.FxRateResponse.Data> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.util.List<co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse.RemittanceFee.TierRateDTO> getFeeTiers();
        
        public abstract void setFeeTiers(@org.jetbrains.annotations.NotNull()
        java.util.List<co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse.RemittanceFee.TierRateDTO> p0);
        
        public abstract void handlePressOnButton(int id);
        
        public abstract void getTransactionFeeInternational(@org.jetbrains.annotations.Nullable()
        java.lang.String productCode);
        
        public abstract void getReasonList(@org.jetbrains.annotations.Nullable()
        java.lang.String productCode);
        
        public abstract void getTransactionInternationalfxList(@org.jetbrains.annotations.Nullable()
        java.lang.String productCode);
        
        public abstract void getMoneyTransferLimits(@org.jetbrains.annotations.Nullable()
        java.lang.String productCode);
        
        public abstract void getCountryLimits();
        
        public abstract void getTransactionThresholds();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001\u00a8\u0006\u0003"}, d2 = {"Lco/yap/sendmoney/fundtransfer/interfaces/IInternationalFundsTransfer$View;", "Lco/yap/yapcore/IBase$View;", "Lco/yap/sendmoney/fundtransfer/interfaces/IInternationalFundsTransfer$ViewModel;", "sendmoney_debug"})
    public static abstract interface View extends co.yap.yapcore.IBase.View<co.yap.sendmoney.fundtransfer.interfaces.IInternationalFundsTransfer.ViewModel> {
    }
}