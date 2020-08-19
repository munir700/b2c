package co.yap.multicurrency.ratesalerts.rates.adaptor;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001\u0011B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0014J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\bH\u0016J\u0010\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0014R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lco/yap/multicurrency/ratesalerts/rates/adaptor/MCRatesAdapter;", "Lco/yap/yapcore/BaseBindingRecyclerAdapter;", "Lco/yap/networking/customers/responsedtos/mcratesandalerts/MCRate;", "Lco/yap/multicurrency/ratesalerts/rates/adaptor/MCRatesAdapter$MCRatesItemViewHolder;", "list", "", "(Ljava/util/List;)V", "getLayoutIdForViewType", "", "viewType", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "binding", "Landroidx/databinding/ViewDataBinding;", "MCRatesItemViewHolder", "multicurrency_debug"})
public final class MCRatesAdapter extends co.yap.yapcore.BaseBindingRecyclerAdapter<co.yap.networking.customers.responsedtos.mcratesandalerts.MCRate, co.yap.multicurrency.ratesalerts.rates.adaptor.MCRatesAdapter.MCRatesItemViewHolder> {
    private final java.util.List<co.yap.networking.customers.responsedtos.mcratesandalerts.MCRate> list = null;
    
    @java.lang.Override()
    protected int getLayoutIdForViewType(int viewType) {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    protected co.yap.multicurrency.ratesalerts.rates.adaptor.MCRatesAdapter.MCRatesItemViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    androidx.databinding.ViewDataBinding binding) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    co.yap.multicurrency.ratesalerts.rates.adaptor.MCRatesAdapter.MCRatesItemViewHolder holder, int position) {
    }
    
    public MCRatesAdapter(@org.jetbrains.annotations.NotNull()
    java.util.List<co.yap.networking.customers.responsedtos.mcratesandalerts.MCRate> list) {
        super(null);
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lco/yap/multicurrency/ratesalerts/rates/adaptor/MCRatesAdapter$MCRatesItemViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemMcRateBinding", "Lco/yap/multicurrency/databinding/LayoutItemMcRateBinding;", "(Lco/yap/multicurrency/databinding/LayoutItemMcRateBinding;)V", "onBind", "", "macRate", "Lco/yap/networking/customers/responsedtos/mcratesandalerts/MCRate;", "position", "", "onItemClickListener", "Lco/yap/yapcore/interfaces/OnItemClickListener;", "multicurrency_debug"})
    public static final class MCRatesItemViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final co.yap.multicurrency.databinding.LayoutItemMcRateBinding itemMcRateBinding = null;
        
        public final void onBind(@org.jetbrains.annotations.NotNull()
        co.yap.networking.customers.responsedtos.mcratesandalerts.MCRate macRate, int position, @org.jetbrains.annotations.Nullable()
        co.yap.yapcore.interfaces.OnItemClickListener onItemClickListener) {
        }
        
        public MCRatesItemViewHolder(@org.jetbrains.annotations.NotNull()
        co.yap.multicurrency.databinding.LayoutItemMcRateBinding itemMcRateBinding) {
            super(null);
        }
    }
}