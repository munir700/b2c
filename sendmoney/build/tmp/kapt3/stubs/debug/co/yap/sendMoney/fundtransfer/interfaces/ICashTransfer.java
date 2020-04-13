package co.yap.sendmoney.fundtransfer.interfaces;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u00a8\u0006\u0005"}, d2 = {"Lco/yap/sendmoney/fundtransfer/interfaces/ICashTransfer;", "", "State", "View", "ViewModel", "sendmoney_debug"})
public abstract interface ICashTransfer {
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\b\u0010\u0003\u001a\u00020\u0004H&\u00a8\u0006\u0005"}, d2 = {"Lco/yap/sendmoney/fundtransfer/interfaces/ICashTransfer$View;", "Lco/yap/yapcore/IBase$View;", "Lco/yap/sendmoney/fundtransfer/interfaces/ICashTransfer$ViewModel;", "setObservers", "", "sendmoney_debug"})
    public static abstract interface View extends co.yap.yapcore.IBase.View<co.yap.sendmoney.fundtransfer.interfaces.ICashTransfer.ViewModel> {
        
        public abstract void setObservers();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u000e\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0017\u00101\u001a\u0002022\b\u00103\u001a\u0004\u0018\u00010#H&\u00a2\u0006\u0002\u00104J\b\u00105\u001a\u000202H&J\u0012\u00106\u001a\u0002022\b\u00107\u001a\u0004\u0018\u00010\u0011H&J\u0010\u00108\u001a\u0002022\u0006\u00107\u001a\u00020\u0011H&J\b\u00109\u001a\u000202H&J\u0012\u0010:\u001a\u0002022\b\u00107\u001a\u0004\u0018\u00010\u0011H&J\u0010\u0010;\u001a\u0002022\u0006\u0010<\u001a\u00020#H&J\b\u0010=\u001a\u000202H&J \u0010>\u001a\u0002022\u0016\u0010?\u001a\u0012\u0012\u0004\u0012\u00020\u001e0\u001dj\b\u0012\u0004\u0012\u00020\u001e`\u001fH&R\u0012\u0010\u0003\u001a\u00020\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u001e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u0018\u0010\u0010\u001a\u00020\u0011X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001e\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0016\u0010\u0019\"\u0004\b\u001a\u0010\u001bR.\u0010\u001c\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u001e0\u001dj\b\u0012\u0004\u0012\u00020\u001e`\u001f0\u0017X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b \u0010\u0019\"\u0004\b!\u0010\u001bR\u0018\u0010\"\u001a\u00020#X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b$\u0010%\"\u0004\b&\u0010\'R\u0018\u0010(\u001a\u00020\u0011X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b)\u0010\u0013\"\u0004\b*\u0010\u0015R.\u0010+\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020,0\u001dj\b\u0012\u0004\u0012\u00020,`\u001f0\u0017X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b-\u0010\u0019\"\u0004\b.\u0010\u001bR\u0018\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00110\u0017X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b0\u0010\u0019\u00a8\u0006@"}, d2 = {"Lco/yap/sendmoney/fundtransfer/interfaces/ICashTransfer$ViewModel;", "Lco/yap/yapcore/IBase$ViewModel;", "Lco/yap/sendmoney/fundtransfer/interfaces/ICashTransfer$State;", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "errorEvent", "getErrorEvent", "feeTiers", "", "Lco/yap/networking/transactions/responsedtos/transaction/RemittanceFeeResponse$RemittanceFee$TierRateDTO;", "getFeeTiers", "()Ljava/util/List;", "setFeeTiers", "(Ljava/util/List;)V", "feeType", "", "getFeeType", "()Ljava/lang/String;", "setFeeType", "(Ljava/lang/String;)V", "isAPIFailed", "Landroidx/lifecycle/MutableLiveData;", "", "()Landroidx/lifecycle/MutableLiveData;", "setAPIFailed", "(Landroidx/lifecycle/MutableLiveData;)V", "purposeOfPaymentList", "Ljava/util/ArrayList;", "Lco/yap/networking/transactions/responsedtos/purposepayment/PurposeOfPayment;", "Lkotlin/collections/ArrayList;", "getPurposeOfPaymentList", "setPurposeOfPaymentList", "reasonPosition", "", "getReasonPosition", "()I", "setReasonPosition", "(I)V", "receiverUUID", "getReceiverUUID", "setReceiverUUID", "transactionData", "Lco/yap/networking/transactions/responsedtos/InternationalFundsTransferReasonList$ReasonList;", "getTransactionData", "setTransactionData", "updatedFee", "getUpdatedFee", "cashPayoutTransferRequest", "", "beneficiaryId", "(Ljava/lang/Integer;)V", "getCountryLimit", "getMoneyTransferLimits", "productCode", "getPurposeOfPayment", "getTransactionThresholds", "getTransferFees", "handlePressOnView", "id", "proceedToTransferAmount", "processPurposeList", "list", "sendmoney_debug"})
    public static abstract interface ViewModel extends co.yap.yapcore.IBase.ViewModel<co.yap.sendmoney.fundtransfer.interfaces.ICashTransfer.State> {
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.String> getUpdatedFee();
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.Boolean> isAPIFailed();
        
        public abstract void setAPIFailed(@org.jetbrains.annotations.NotNull()
        androidx.lifecycle.MutableLiveData<java.lang.Boolean> p0);
        
        public abstract int getReasonPosition();
        
        public abstract void setReasonPosition(int p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.yapcore.SingleClickEvent getClickEvent();
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.yapcore.SingleClickEvent getErrorEvent();
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.util.ArrayList<co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList.ReasonList>> getTransactionData();
        
        public abstract void setTransactionData(@org.jetbrains.annotations.NotNull()
        androidx.lifecycle.MutableLiveData<java.util.ArrayList<co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList.ReasonList>> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getReceiverUUID();
        
        public abstract void setReceiverUUID(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.util.ArrayList<co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment>> getPurposeOfPaymentList();
        
        public abstract void setPurposeOfPaymentList(@org.jetbrains.annotations.NotNull()
        androidx.lifecycle.MutableLiveData<java.util.ArrayList<co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment>> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getFeeType();
        
        public abstract void setFeeType(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.util.List<co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse.RemittanceFee.TierRateDTO> getFeeTiers();
        
        public abstract void setFeeTiers(@org.jetbrains.annotations.NotNull()
        java.util.List<co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse.RemittanceFee.TierRateDTO> p0);
        
        public abstract void handlePressOnView(int id);
        
        public abstract void getTransferFees(@org.jetbrains.annotations.Nullable()
        java.lang.String productCode);
        
        public abstract void cashPayoutTransferRequest(@org.jetbrains.annotations.Nullable()
        java.lang.Integer beneficiaryId);
        
        public abstract void getMoneyTransferLimits(@org.jetbrains.annotations.Nullable()
        java.lang.String productCode);
        
        public abstract void getPurposeOfPayment(@org.jetbrains.annotations.NotNull()
        java.lang.String productCode);
        
        public abstract void getCountryLimit();
        
        public abstract void getTransactionThresholds();
        
        public abstract void proceedToTransferAmount();
        
        public abstract void processPurposeList(@org.jetbrains.annotations.NotNull()
        java.util.ArrayList<co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment> list);
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\r\n\u0002\b\u000b\n\u0002\u0010\u0006\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010<\u001a\u00020=H&R\u0018\u0010\u0002\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u0004\u0018\u00010\tX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0018\u0010\u0014\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0015\u0010\u0005\"\u0004\b\u0016\u0010\u0007R\u001a\u0010\u0017\u001a\u0004\u0018\u00010\u000fX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0018\u0010\u0011\"\u0004\b\u0019\u0010\u0013R\u0018\u0010\u001a\u001a\u00020\u001bX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u0018\u0010 \u001a\u00020\u001bX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b!\u0010\u001d\"\u0004\b\"\u0010\u001fR\u001a\u0010#\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b$\u0010\u0005\"\u0004\b%\u0010\u0007R\u001e\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020)0(0\'X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b*\u0010+R\u001a\u0010,\u001a\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b-\u0010\u0005\"\u0004\b.\u0010\u0007R(\u0010/\u001a\u0012\u0012\u0004\u0012\u00020)00j\b\u0012\u0004\u0012\u00020)`1X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b2\u00103\"\u0004\b4\u00105R\u0018\u00106\u001a\u000207X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b8\u00109\"\u0004\b:\u0010;\u00a8\u0006>"}, d2 = {"Lco/yap/sendmoney/fundtransfer/interfaces/ICashTransfer$State;", "Lco/yap/yapcore/IBase$State;", "amount", "", "getAmount", "()Ljava/lang/String;", "setAmount", "(Ljava/lang/String;)V", "amountBackground", "Landroid/graphics/drawable/Drawable;", "getAmountBackground", "()Landroid/graphics/drawable/Drawable;", "setAmountBackground", "(Landroid/graphics/drawable/Drawable;)V", "availableBalanceString", "", "getAvailableBalanceString", "()Ljava/lang/CharSequence;", "setAvailableBalanceString", "(Ljava/lang/CharSequence;)V", "errorDescription", "getErrorDescription", "setErrorDescription", "feeAmountSpannableString", "getFeeAmountSpannableString", "setFeeAmountSpannableString", "maxLimit", "", "getMaxLimit", "()D", "setMaxLimit", "(D)V", "minLimit", "getMinLimit", "setMinLimit", "noteValue", "getNoteValue", "setNoteValue", "populateSpinnerData", "Landroidx/lifecycle/MutableLiveData;", "", "Lco/yap/networking/transactions/responsedtos/InternationalFundsTransferReasonList$ReasonList;", "getPopulateSpinnerData", "()Landroidx/lifecycle/MutableLiveData;", "produceCode", "getProduceCode", "setProduceCode", "transactionData", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "getTransactionData", "()Ljava/util/ArrayList;", "setTransactionData", "(Ljava/util/ArrayList;)V", "valid", "", "getValid", "()Z", "setValid", "(Z)V", "clearError", "", "sendmoney_debug"})
    public static abstract interface State extends co.yap.yapcore.IBase.State {
        
        @org.jetbrains.annotations.Nullable()
        public abstract android.graphics.drawable.Drawable getAmountBackground();
        
        public abstract void setAmountBackground(@org.jetbrains.annotations.Nullable()
        android.graphics.drawable.Drawable p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.CharSequence getFeeAmountSpannableString();
        
        public abstract void setFeeAmountSpannableString(@org.jetbrains.annotations.Nullable()
        java.lang.CharSequence p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.CharSequence getAvailableBalanceString();
        
        public abstract void setAvailableBalanceString(@org.jetbrains.annotations.Nullable()
        java.lang.CharSequence p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getAmount();
        
        public abstract void setAmount(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        public abstract boolean getValid();
        
        public abstract void setValid(boolean p0);
        
        public abstract double getMinLimit();
        
        public abstract void setMinLimit(double p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.lang.String getErrorDescription();
        
        public abstract void setErrorDescription(@org.jetbrains.annotations.NotNull()
        java.lang.String p0);
        
        public abstract double getMaxLimit();
        
        public abstract void setMaxLimit(double p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getNoteValue();
        
        public abstract void setNoteValue(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
        
        public abstract void clearError();
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.util.ArrayList<co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList.ReasonList> getTransactionData();
        
        public abstract void setTransactionData(@org.jetbrains.annotations.NotNull()
        java.util.ArrayList<co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList.ReasonList> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.util.List<co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList.ReasonList>> getPopulateSpinnerData();
        
        @org.jetbrains.annotations.Nullable()
        public abstract java.lang.String getProduceCode();
        
        public abstract void setProduceCode(@org.jetbrains.annotations.Nullable()
        java.lang.String p0);
    }
}