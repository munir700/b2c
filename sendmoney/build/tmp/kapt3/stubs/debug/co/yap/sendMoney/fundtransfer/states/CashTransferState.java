package co.yap.sendMoney.fundtransfer.states;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\r\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0006\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\b\u0010E\u001a\u00020FH\u0016R&\u0010\b\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR*\u0010\u000e\u001a\u0004\u0018\u00010\r2\b\u0010\u0006\u001a\u0004\u0018\u00010\r8W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R*\u0010\u0014\u001a\u0004\u0018\u00010\u00132\b\u0010\u0006\u001a\u0004\u0018\u00010\u00138W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0019\u001a\u00020\u001a\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR&\u0010\u001d\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\n\"\u0004\b\u001f\u0010\fR*\u0010 \u001a\u0004\u0018\u00010\u00132\b\u0010\u0006\u001a\u0004\u0018\u00010\u00138W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0016\"\u0004\b\"\u0010\u0018R&\u0010$\u001a\u00020#2\u0006\u0010\u0006\u001a\u00020#8W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b\'\u0010(R&\u0010)\u001a\u00020#2\u0006\u0010\u0006\u001a\u00020#8W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b*\u0010&\"\u0004\b+\u0010(R*\u0010,\u001a\u0004\u0018\u00010\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b-\u0010\n\"\u0004\b.\u0010\fR\"\u0010/\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020201008WX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u00104R*\u00105\u001a\u0004\u0018\u00010\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u00078W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b6\u0010\n\"\u0004\b7\u0010\fRF\u0010:\u001a\u0012\u0012\u0004\u0012\u00020208j\b\u0012\u0004\u0012\u000202`92\u0016\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020208j\b\u0012\u0004\u0012\u000202`98W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b;\u0010<\"\u0004\b=\u0010>R&\u0010@\u001a\u00020?2\u0006\u0010\u0006\u001a\u00020?8W@VX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bA\u0010B\"\u0004\bC\u0010D\u00a8\u0006G"}, d2 = {"Lco/yap/sendMoney/fundtransfer/states/CashTransferState;", "Lco/yap/yapcore/BaseState;", "Lco/yap/sendMoney/fundtransfer/interfaces/ICashTransfer$State;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "value", "", "amount", "getAmount", "()Ljava/lang/String;", "setAmount", "(Ljava/lang/String;)V", "Landroid/graphics/drawable/Drawable;", "amountBackground", "getAmountBackground", "()Landroid/graphics/drawable/Drawable;", "setAmountBackground", "(Landroid/graphics/drawable/Drawable;)V", "", "availableBalanceString", "getAvailableBalanceString", "()Ljava/lang/CharSequence;", "setAvailableBalanceString", "(Ljava/lang/CharSequence;)V", "context", "Landroid/content/Context;", "getContext", "()Landroid/content/Context;", "errorDescription", "getErrorDescription", "setErrorDescription", "feeAmountSpannableString", "getFeeAmountSpannableString", "setFeeAmountSpannableString", "", "maxLimit", "getMaxLimit", "()D", "setMaxLimit", "(D)V", "minLimit", "getMinLimit", "setMinLimit", "noteValue", "getNoteValue", "setNoteValue", "populateSpinnerData", "Landroidx/lifecycle/MutableLiveData;", "", "Lco/yap/networking/transactions/responsedtos/InternationalFundsTransferReasonList$ReasonList;", "getPopulateSpinnerData", "()Landroidx/lifecycle/MutableLiveData;", "produceCode", "getProduceCode", "setProduceCode", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "transactionData", "getTransactionData", "()Ljava/util/ArrayList;", "setTransactionData", "(Ljava/util/ArrayList;)V", "", "valid", "getValid", "()Z", "setValid", "(Z)V", "clearError", "", "sendmoney_debug"})
public final class CashTransferState extends co.yap.yapcore.BaseState implements co.yap.sendMoney.fundtransfer.interfaces.ICashTransfer.State {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.Nullable()
    private android.graphics.drawable.Drawable amountBackground;
    @org.jetbrains.annotations.Nullable()
    private java.lang.CharSequence availableBalanceString;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String amount;
    private boolean valid;
    private double minLimit;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String errorDescription;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String noteValue;
    private double maxLimit;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String produceCode;
    @org.jetbrains.annotations.Nullable()
    private java.lang.CharSequence feeAmountSpannableString;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList.ReasonList> transactionData;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.util.List<co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList.ReasonList>> populateSpinnerData = null;
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.Context getContext() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public android.graphics.drawable.Drawable getAmountBackground() {
        return null;
    }
    
    @java.lang.Override()
    public void setAmountBackground(@org.jetbrains.annotations.Nullable()
    android.graphics.drawable.Drawable value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.CharSequence getAvailableBalanceString() {
        return null;
    }
    
    @java.lang.Override()
    public void setAvailableBalanceString(@org.jetbrains.annotations.Nullable()
    java.lang.CharSequence value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getAmount() {
        return null;
    }
    
    @java.lang.Override()
    public void setAmount(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public boolean getValid() {
        return false;
    }
    
    @java.lang.Override()
    public void setValid(boolean value) {
    }
    
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public double getMinLimit() {
        return 0.0;
    }
    
    @java.lang.Override()
    public void setMinLimit(double value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getErrorDescription() {
        return null;
    }
    
    @java.lang.Override()
    public void setErrorDescription(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getNoteValue() {
        return null;
    }
    
    @java.lang.Override()
    public void setNoteValue(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public double getMaxLimit() {
        return 0.0;
    }
    
    @java.lang.Override()
    public void setMaxLimit(double value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.String getProduceCode() {
        return null;
    }
    
    @java.lang.Override()
    public void setProduceCode(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.lang.CharSequence getFeeAmountSpannableString() {
        return null;
    }
    
    @java.lang.Override()
    public void setFeeAmountSpannableString(@org.jetbrains.annotations.Nullable()
    java.lang.CharSequence value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public java.util.ArrayList<co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList.ReasonList> getTransactionData() {
        return null;
    }
    
    @java.lang.Override()
    public void setTransactionData(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList.ReasonList> value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    @androidx.databinding.Bindable()
    public androidx.lifecycle.MutableLiveData<java.util.List<co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList.ReasonList>> getPopulateSpinnerData() {
        return null;
    }
    
    @java.lang.Override()
    public void clearError() {
    }
    
    public CashTransferState(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super();
    }
}