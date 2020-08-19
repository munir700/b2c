package co.yap.multicurrency.wallets.adapter;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\u0002\u0010\u000bJ\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017R \u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0018"}, d2 = {"Lco/yap/multicurrency/wallets/adapter/WalletItemViewModel;", "", "multiCurrencyWallet", "Lco/yap/multicurrency/wallets/MultiCurrencyWallet;", "position", "", "onItemClickListener", "Lco/yap/yapcore/interfaces/OnItemClickListener;", "activeWallet", "Landroidx/databinding/ObservableField;", "", "(Lco/yap/multicurrency/wallets/MultiCurrencyWallet;ILco/yap/yapcore/interfaces/OnItemClickListener;Landroidx/databinding/ObservableField;)V", "getActiveWallet", "()Landroidx/databinding/ObservableField;", "setActiveWallet", "(Landroidx/databinding/ObservableField;)V", "getMultiCurrencyWallet", "()Lco/yap/multicurrency/wallets/MultiCurrencyWallet;", "getPosition", "()I", "onViewClicked", "", "view", "Landroid/view/View;", "multicurrency_debug"})
public final class WalletItemViewModel {
    @org.jetbrains.annotations.NotNull()
    private final co.yap.multicurrency.wallets.MultiCurrencyWallet multiCurrencyWallet = null;
    private final int position = 0;
    private final co.yap.yapcore.interfaces.OnItemClickListener onItemClickListener = null;
    @org.jetbrains.annotations.NotNull()
    private androidx.databinding.ObservableField<java.lang.Boolean> activeWallet;
    
    public final void onViewClicked(@org.jetbrains.annotations.NotNull()
    android.view.View view) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final co.yap.multicurrency.wallets.MultiCurrencyWallet getMultiCurrencyWallet() {
        return null;
    }
    
    public final int getPosition() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.databinding.ObservableField<java.lang.Boolean> getActiveWallet() {
        return null;
    }
    
    public final void setActiveWallet(@org.jetbrains.annotations.NotNull()
    androidx.databinding.ObservableField<java.lang.Boolean> p0) {
    }
    
    public WalletItemViewModel(@org.jetbrains.annotations.NotNull()
    co.yap.multicurrency.wallets.MultiCurrencyWallet multiCurrencyWallet, int position, @org.jetbrains.annotations.Nullable()
    co.yap.yapcore.interfaces.OnItemClickListener onItemClickListener, @org.jetbrains.annotations.NotNull()
    androidx.databinding.ObservableField<java.lang.Boolean> activeWallet) {
        super();
    }
}