package co.yap.multicurrency.walletcountry;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003R\"\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\"\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\b\"\u0004\b\r\u0010\n\u00a8\u0006\u000e"}, d2 = {"Lco/yap/multicurrency/walletcountry/WalletDetailsState;", "Lco/yap/yapcore/BaseState;", "Lco/yap/multicurrency/walletcountry/IWalletDetails$State;", "()V", "bankDetails", "Landroidx/lifecycle/MutableLiveData;", "Lco/yap/multicurrency/walletcountry/DefaultWalletBankDetails;", "getBankDetails", "()Landroidx/lifecycle/MutableLiveData;", "setBankDetails", "(Landroidx/lifecycle/MutableLiveData;)V", "isPrimaryWallet", "", "setPrimaryWallet", "multicurrency_debug"})
public final class WalletDetailsState extends co.yap.yapcore.BaseState implements co.yap.multicurrency.walletcountry.IWalletDetails.State {
    @org.jetbrains.annotations.Nullable()
    private androidx.lifecycle.MutableLiveData<co.yap.multicurrency.walletcountry.DefaultWalletBankDetails> bankDetails;
    @org.jetbrains.annotations.Nullable()
    private androidx.lifecycle.MutableLiveData<java.lang.Boolean> isPrimaryWallet;
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<co.yap.multicurrency.walletcountry.DefaultWalletBankDetails> getBankDetails() {
        return null;
    }
    
    @java.lang.Override()
    public void setBankDetails(@org.jetbrains.annotations.Nullable()
    androidx.lifecycle.MutableLiveData<co.yap.multicurrency.walletcountry.DefaultWalletBankDetails> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.lang.Boolean> isPrimaryWallet() {
        return null;
    }
    
    @java.lang.Override()
    public void setPrimaryWallet(@org.jetbrains.annotations.Nullable()
    androidx.lifecycle.MutableLiveData<java.lang.Boolean> p0) {
    }
    
    public WalletDetailsState() {
        super();
    }
}