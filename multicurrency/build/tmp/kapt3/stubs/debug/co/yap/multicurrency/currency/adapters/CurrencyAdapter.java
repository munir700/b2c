package co.yap.multicurrency.currency.adapters;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001\u0016B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001a\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH\u0014J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\rH\u0016J\u0010\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u0015H\u0014R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lco/yap/multicurrency/currency/adapters/CurrencyAdapter;", "Lco/yap/yapcore/BaseBindingSearchRecylerAdapter;", "Lco/yap/multicurrency/wallets/MultiCurrencyWallet;", "Lco/yap/multicurrency/currency/adapters/CurrencyAdapter$CurrencyItemViewHolder;", "list", "", "(Ljava/util/List;)V", "filterItem", "", "constraint", "", "item", "getLayoutIdForViewType", "", "viewType", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "binding", "Landroidx/databinding/ViewDataBinding;", "CurrencyItemViewHolder", "multicurrency_debug"})
public final class CurrencyAdapter extends co.yap.yapcore.BaseBindingSearchRecylerAdapter<co.yap.multicurrency.wallets.MultiCurrencyWallet, co.yap.multicurrency.currency.adapters.CurrencyAdapter.CurrencyItemViewHolder> {
    private final java.util.List<co.yap.multicurrency.wallets.MultiCurrencyWallet> list = null;
    
    @java.lang.Override()
    protected int getLayoutIdForViewType(int viewType) {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    protected co.yap.multicurrency.currency.adapters.CurrencyAdapter.CurrencyItemViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    androidx.databinding.ViewDataBinding binding) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    co.yap.multicurrency.currency.adapters.CurrencyAdapter.CurrencyItemViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public boolean filterItem(@org.jetbrains.annotations.Nullable()
    java.lang.CharSequence constraint, @org.jetbrains.annotations.NotNull()
    co.yap.multicurrency.wallets.MultiCurrencyWallet item) {
        return false;
    }
    
    public CurrencyAdapter(@org.jetbrains.annotations.NotNull()
    java.util.List<co.yap.multicurrency.wallets.MultiCurrencyWallet> list) {
        super(null);
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\"\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lco/yap/multicurrency/currency/adapters/CurrencyAdapter$CurrencyItemViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemCurrencyCountryBinding", "Lco/yap/multicurrency/databinding/ItemCurrencyCountryBinding;", "(Lco/yap/multicurrency/databinding/ItemCurrencyCountryBinding;)V", "onBind", "", "multiCurrencyWallet", "Lco/yap/multicurrency/wallets/MultiCurrencyWallet;", "position", "", "onItemClickListener", "Lco/yap/yapcore/interfaces/OnItemClickListener;", "multicurrency_debug"})
    public static final class CurrencyItemViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final co.yap.multicurrency.databinding.ItemCurrencyCountryBinding itemCurrencyCountryBinding = null;
        
        public final void onBind(@org.jetbrains.annotations.Nullable()
        co.yap.multicurrency.wallets.MultiCurrencyWallet multiCurrencyWallet, int position, @org.jetbrains.annotations.Nullable()
        co.yap.yapcore.interfaces.OnItemClickListener onItemClickListener) {
        }
        
        public CurrencyItemViewHolder(@org.jetbrains.annotations.NotNull()
        co.yap.multicurrency.databinding.ItemCurrencyCountryBinding itemCurrencyCountryBinding) {
            super(null);
        }
    }
}