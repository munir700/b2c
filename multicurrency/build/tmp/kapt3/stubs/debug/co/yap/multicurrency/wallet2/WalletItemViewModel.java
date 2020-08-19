package co.yap.multicurrency.wallet2;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0005\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0005\u001a\u00020\u0002H\u0016J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\u001c\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0016J \u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0007H\u0016J\u0018\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\u00022\u0006\u0010\u0016\u001a\u00020\u0007H\u0016R\u000e\u0010\u0004\u001a\u00020\u0002X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lco/yap/multicurrency/wallet2/WalletItemViewModel;", "Lco/yap/yapcore/BaseListItemViewModel;", "Lco/yap/multicurrency/wallets/MultiCurrencyWallet;", "()V", "mItem", "getItem", "layoutRes", "", "onFirsTimeUiCreate", "", "bundle", "Landroid/os/Bundle;", "navigation", "Landroidx/navigation/NavController;", "onItemClick", "view", "Landroid/view/View;", "data", "", "pos", "setItem", "item", "position", "multicurrency_debug"})
public final class WalletItemViewModel extends co.yap.yapcore.BaseListItemViewModel<co.yap.multicurrency.wallets.MultiCurrencyWallet> {
    private co.yap.multicurrency.wallets.MultiCurrencyWallet mItem;
    
    @java.lang.Override()
    public void setItem(@org.jetbrains.annotations.NotNull()
    co.yap.multicurrency.wallets.MultiCurrencyWallet item, int position) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.multicurrency.wallets.MultiCurrencyWallet getItem() {
        return null;
    }
    
    @java.lang.Override()
    public void onFirsTimeUiCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle bundle, @org.jetbrains.annotations.Nullable()
    androidx.navigation.NavController navigation) {
    }
    
    @java.lang.Override()
    public int layoutRes() {
        return 0;
    }
    
    @java.lang.Override()
    public void onItemClick(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.NotNull()
    java.lang.Object data, int pos) {
    }
    
    public WalletItemViewModel() {
        super();
    }
}