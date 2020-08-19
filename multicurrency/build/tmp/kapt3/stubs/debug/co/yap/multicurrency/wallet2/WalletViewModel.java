package co.yap.multicurrency.wallet2;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\fX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0013"}, d2 = {"Lco/yap/multicurrency/wallet2/WalletViewModel;", "Lco/yap/multicurrency/base/MCLandingBaseViewModel;", "Lco/yap/multicurrency/wallet2/IWallet$State;", "Lco/yap/multicurrency/wallet2/IWallet$ViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "state", "Lco/yap/multicurrency/wallet2/WalletState;", "getState", "()Lco/yap/multicurrency/wallet2/WalletState;", "walletAdapter", "Landroidx/databinding/ObservableField;", "Lco/yap/multicurrency/wallet2/WalletAdapter;", "getWalletAdapter", "()Landroidx/databinding/ObservableField;", "getDummyData", "", "Lco/yap/multicurrency/wallets/MultiCurrencyWallet;", "multicurrency_debug"})
public final class WalletViewModel extends co.yap.multicurrency.base.MCLandingBaseViewModel<co.yap.multicurrency.wallet2.IWallet.State> implements co.yap.multicurrency.wallet2.IWallet.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final co.yap.multicurrency.wallet2.WalletState state = null;
    @org.jetbrains.annotations.Nullable()
    private final androidx.databinding.ObservableField<co.yap.multicurrency.wallet2.WalletAdapter> walletAdapter = null;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.multicurrency.wallet2.WalletState getState() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public androidx.databinding.ObservableField<co.yap.multicurrency.wallet2.WalletAdapter> getWalletAdapter() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<co.yap.multicurrency.wallets.MultiCurrencyWallet> getDummyData() {
        return null;
    }
    
    public WalletViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
}