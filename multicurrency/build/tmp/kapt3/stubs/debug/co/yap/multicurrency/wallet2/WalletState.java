package co.yap.multicurrency.wallet2;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003R&\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b\u00a8\u0006\f"}, d2 = {"Lco/yap/multicurrency/wallet2/WalletState;", "Lco/yap/yapcore/BaseState;", "Lco/yap/multicurrency/wallet2/IWallet$State;", "()V", "mcWallets", "Landroidx/lifecycle/MutableLiveData;", "", "Lco/yap/multicurrency/wallets/MultiCurrencyWallet;", "getMcWallets", "()Landroidx/lifecycle/MutableLiveData;", "setMcWallets", "(Landroidx/lifecycle/MutableLiveData;)V", "multicurrency_debug"})
public final class WalletState extends co.yap.yapcore.BaseState implements co.yap.multicurrency.wallet2.IWallet.State {
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.util.List<co.yap.multicurrency.wallets.MultiCurrencyWallet>> mcWallets;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.util.List<co.yap.multicurrency.wallets.MultiCurrencyWallet>> getMcWallets() {
        return null;
    }
    
    @java.lang.Override()
    public void setMcWallets(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.util.List<co.yap.multicurrency.wallets.MultiCurrencyWallet>> p0) {
    }
    
    public WalletState() {
        super();
    }
}