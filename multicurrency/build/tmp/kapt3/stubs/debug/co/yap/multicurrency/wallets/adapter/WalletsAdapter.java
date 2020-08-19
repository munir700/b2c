package co.yap.multicurrency.wallets.adapter;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B!\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\u0002\u0010\tJ\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0014J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u000fH\u0016J\u0010\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u0017H\u0014R \u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lco/yap/multicurrency/wallets/adapter/WalletsAdapter;", "Lco/yap/yapcore/BaseBindingRecyclerAdapter;", "Lco/yap/multicurrency/wallets/MultiCurrencyWallet;", "Lco/yap/multicurrency/wallets/adapter/WalletsViewHolder;", "list", "", "activeWallet", "Landroidx/databinding/ObservableField;", "", "(Ljava/util/List;Landroidx/databinding/ObservableField;)V", "getActiveWallet", "()Landroidx/databinding/ObservableField;", "setActiveWallet", "(Landroidx/databinding/ObservableField;)V", "getLayoutIdForViewType", "", "viewType", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "binding", "Landroidx/databinding/ViewDataBinding;", "multicurrency_debug"})
public final class WalletsAdapter extends co.yap.yapcore.BaseBindingRecyclerAdapter<co.yap.multicurrency.wallets.MultiCurrencyWallet, co.yap.multicurrency.wallets.adapter.WalletsViewHolder> {
    private final java.util.List<co.yap.multicurrency.wallets.MultiCurrencyWallet> list = null;
    @org.jetbrains.annotations.NotNull()
    private androidx.databinding.ObservableField<java.lang.Boolean> activeWallet;
    
    @java.lang.Override()
    protected int getLayoutIdForViewType(int viewType) {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    protected co.yap.multicurrency.wallets.adapter.WalletsViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    androidx.databinding.ViewDataBinding binding) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    co.yap.multicurrency.wallets.adapter.WalletsViewHolder holder, int position) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.databinding.ObservableField<java.lang.Boolean> getActiveWallet() {
        return null;
    }
    
    public final void setActiveWallet(@org.jetbrains.annotations.NotNull()
    androidx.databinding.ObservableField<java.lang.Boolean> p0) {
    }
    
    public WalletsAdapter(@org.jetbrains.annotations.NotNull()
    java.util.List<co.yap.multicurrency.wallets.MultiCurrencyWallet> list, @org.jetbrains.annotations.NotNull()
    androidx.databinding.ObservableField<java.lang.Boolean> activeWallet) {
        super(null);
    }
}