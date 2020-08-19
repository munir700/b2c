package co.yap.multicurrency.wallet2;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u00a8\u0006\u0005"}, d2 = {"Lco/yap/multicurrency/wallet2/IWallet;", "", "State", "View", "ViewModel", "multicurrency_debug"})
public abstract interface IWallet {
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001\u00a8\u0006\u0003"}, d2 = {"Lco/yap/multicurrency/wallet2/IWallet$View;", "Lco/yap/yapcore/IBase$View;", "Lco/yap/multicurrency/wallet2/IWallet$ViewModel;", "multicurrency_debug"})
    public static abstract interface View extends co.yap.yapcore.IBase.View<co.yap.multicurrency.wallet2.IWallet.ViewModel> {
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001R\u001a\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\b"}, d2 = {"Lco/yap/multicurrency/wallet2/IWallet$ViewModel;", "Lco/yap/yapcore/IBase$ViewModel;", "Lco/yap/multicurrency/wallet2/IWallet$State;", "walletAdapter", "Landroidx/databinding/ObservableField;", "Lco/yap/multicurrency/wallet2/WalletAdapter;", "getWalletAdapter", "()Landroidx/databinding/ObservableField;", "multicurrency_debug"})
    public static abstract interface ViewModel extends co.yap.yapcore.IBase.ViewModel<co.yap.multicurrency.wallet2.IWallet.State> {
        
        @org.jetbrains.annotations.Nullable()
        public abstract androidx.databinding.ObservableField<co.yap.multicurrency.wallet2.WalletAdapter> getWalletAdapter();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001R$\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t\u00a8\u0006\n"}, d2 = {"Lco/yap/multicurrency/wallet2/IWallet$State;", "Lco/yap/yapcore/IBase$State;", "mcWallets", "Landroidx/lifecycle/MutableLiveData;", "", "Lco/yap/multicurrency/wallets/MultiCurrencyWallet;", "getMcWallets", "()Landroidx/lifecycle/MutableLiveData;", "setMcWallets", "(Landroidx/lifecycle/MutableLiveData;)V", "multicurrency_debug"})
    public static abstract interface State extends co.yap.yapcore.IBase.State {
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.util.List<co.yap.multicurrency.wallets.MultiCurrencyWallet>> getMcWallets();
        
        public abstract void setMcWallets(@org.jetbrains.annotations.NotNull()
        androidx.lifecycle.MutableLiveData<java.util.List<co.yap.multicurrency.wallets.MultiCurrencyWallet>> p0);
    }
}