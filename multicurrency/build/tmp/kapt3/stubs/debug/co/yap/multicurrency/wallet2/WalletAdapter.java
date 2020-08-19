package co.yap.multicurrency.wallet2;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00012\b\u0012\u0004\u0012\u00020\u00040\u00052\b\u0012\u0004\u0012\u00020\u00040\u0006:\u00013B\u001d\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\u0002\u0010\u000bJ\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000fH\u0016J\r\u0010\u0012\u001a\u00020\u0013H\u0016\u00a2\u0006\u0002\u0010\u0014J(\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0011\u001a\u00020\u000fH\u0016J\u0010\u0010\u001b\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u000fH\u0016J\u0018\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020\u000fH\u0016J(\u0010 \u001a\u00020\u001d2\u0006\u0010!\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\"\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020\u000fH\u0016J\u001a\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010!\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J(\u0010&\u001a\u00020\u000f2\u0006\u0010!\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\"\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020\u000fH\u0016J \u0010\'\u001a\u00020(2\u0006\u0010)\u001a\u00020\u000f2\u0006\u0010*\u001a\u00020\u000f2\u0006\u0010+\u001a\u00020\u001dH\u0016J\u0010\u0010,\u001a\u00020(2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010-\u001a\u00020(2\u0006\u0010)\u001a\u00020\u000f2\u0006\u0010*\u001a\u00020\u000fH\u0016J \u0010.\u001a\u00020(2\u0006\u0010!\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010/\u001a\u00020\u000fH\u0016J \u00100\u001a\u0002012\u0006\u0010!\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010+\u001a\u00020\u000fH\u0016J\u0018\u00102\u001a\u00020(2\u0006\u0010!\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000fH\u0016\u00a8\u00064"}, d2 = {"Lco/yap/multicurrency/wallet2/WalletAdapter;", "Lco/yap/yapcore/BaseRVAdapter;", "Lco/yap/multicurrency/wallets/MultiCurrencyWallet;", "Lco/yap/multicurrency/wallet2/WalletItemViewModel;", "Lco/yap/multicurrency/wallet2/WalletAdapter$ViewHolder;", "Lco/yap/widgets/advrecyclerview/draggable/DraggableItemAdapter;", "Lco/yap/widgets/advrecyclerview/swipeable/SwipeableItemAdapter;", "mValue", "", "navigation", "Landroidx/navigation/NavController;", "(Ljava/util/List;Landroidx/navigation/NavController;)V", "getItemId", "", "position", "", "getLayoutId", "viewType", "getVariableId", "error/NonExistentClass", "()Lerror/NonExistentClass;", "getViewHolder", "view", "Landroid/view/View;", "viewModel", "mDataBinding", "Landroidx/databinding/ViewDataBinding;", "getViewModel", "onCheckCanDrop", "", "draggingPosition", "dropPosition", "onCheckCanStartDrag", "holder", "x", "y", "onGetItemDraggableRange", "", "onGetSwipeReactionType", "onItemDragFinished", "", "fromPosition", "toPosition", "result", "onItemDragStarted", "onMoveItem", "onSetSwipeBackground", "type", "onSwipeItem", "Lco/yap/widgets/advrecyclerview/swipeable/action/SwipeResultActionDefault;", "onSwipeItemStarted", "ViewHolder", "multicurrency_debug"})
public final class WalletAdapter extends co.yap.yapcore.BaseRVAdapter<co.yap.multicurrency.wallets.MultiCurrencyWallet, co.yap.multicurrency.wallet2.WalletItemViewModel, co.yap.multicurrency.wallet2.WalletAdapter.ViewHolder> implements co.yap.widgets.advrecyclerview.draggable.DraggableItemAdapter<co.yap.multicurrency.wallet2.WalletAdapter.ViewHolder>, co.yap.widgets.advrecyclerview.swipeable.SwipeableItemAdapter<co.yap.multicurrency.wallet2.WalletAdapter.ViewHolder> {
    
    @java.lang.Override()
    public long getItemId(int position) {
        return 0L;
    }
    
    @java.lang.Override()
    public int getLayoutId(int viewType) {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.multicurrency.wallet2.WalletAdapter.ViewHolder getViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.NotNull()
    co.yap.multicurrency.wallet2.WalletItemViewModel viewModel, @org.jetbrains.annotations.NotNull()
    androidx.databinding.ViewDataBinding mDataBinding, int viewType) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.multicurrency.wallet2.WalletItemViewModel getViewModel(int viewType) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public error.NonExistentClass getVariableId() {
        return null;
    }
    
    @java.lang.Override()
    public boolean onCheckCanStartDrag(@org.jetbrains.annotations.NotNull()
    co.yap.multicurrency.wallet2.WalletAdapter.ViewHolder holder, int position, int x, int y) {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Void onGetItemDraggableRange(@org.jetbrains.annotations.NotNull()
    co.yap.multicurrency.wallet2.WalletAdapter.ViewHolder holder, int position) {
        return null;
    }
    
    @java.lang.Override()
    public void onMoveItem(int fromPosition, int toPosition) {
    }
    
    @java.lang.Override()
    public boolean onCheckCanDrop(int draggingPosition, int dropPosition) {
        return false;
    }
    
    @java.lang.Override()
    public void onItemDragStarted(int position) {
    }
    
    @java.lang.Override()
    public void onItemDragFinished(int fromPosition, int toPosition, boolean result) {
    }
    
    @java.lang.Override()
    public int onGetSwipeReactionType(@org.jetbrains.annotations.NotNull()
    co.yap.multicurrency.wallet2.WalletAdapter.ViewHolder holder, int position, int x, int y) {
        return 0;
    }
    
    @java.lang.Override()
    public void onSwipeItemStarted(@org.jetbrains.annotations.NotNull()
    co.yap.multicurrency.wallet2.WalletAdapter.ViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public void onSetSwipeBackground(@org.jetbrains.annotations.NotNull()
    co.yap.multicurrency.wallet2.WalletAdapter.ViewHolder holder, int position, int type) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.widgets.advrecyclerview.swipeable.action.SwipeResultActionDefault onSwipeItem(@org.jetbrains.annotations.NotNull()
    co.yap.multicurrency.wallet2.WalletAdapter.ViewHolder holder, int position, int result) {
        return null;
    }
    
    public WalletAdapter(@org.jetbrains.annotations.NotNull()
    java.util.List<co.yap.multicurrency.wallets.MultiCurrencyWallet> mValue, @org.jetbrains.annotations.Nullable()
    androidx.navigation.NavController navigation) {
        super(null, null);
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u001d\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0012"}, d2 = {"Lco/yap/multicurrency/wallet2/WalletAdapter$ViewHolder;", "Lco/yap/widgets/advrecyclerview/utils/AbstractDraggableSwipeableItemViewHolder;", "Lco/yap/multicurrency/wallets/MultiCurrencyWallet;", "Lco/yap/multicurrency/wallet2/WalletItemViewModel;", "view", "Landroid/view/View;", "viewModel", "mDataBinding", "Landroidx/databinding/ViewDataBinding;", "(Landroid/view/View;Lco/yap/multicurrency/wallet2/WalletItemViewModel;Landroidx/databinding/ViewDataBinding;)V", "binding", "Lco/yap/multicurrency/databinding/ItemWalletV2Binding;", "getBinding", "()Lco/yap/multicurrency/databinding/ItemWalletV2Binding;", "setBinding", "(Lco/yap/multicurrency/databinding/ItemWalletV2Binding;)V", "getSwipeableContainerView", "Landroidx/appcompat/widget/LinearLayoutCompat;", "multicurrency_debug"})
    public static final class ViewHolder extends co.yap.widgets.advrecyclerview.utils.AbstractDraggableSwipeableItemViewHolder<co.yap.multicurrency.wallets.MultiCurrencyWallet, co.yap.multicurrency.wallet2.WalletItemViewModel> {
        @org.jetbrains.annotations.NotNull()
        private co.yap.multicurrency.databinding.ItemWalletV2Binding binding;
        
        @org.jetbrains.annotations.NotNull()
        public final co.yap.multicurrency.databinding.ItemWalletV2Binding getBinding() {
            return null;
        }
        
        public final void setBinding(@org.jetbrains.annotations.NotNull()
        co.yap.multicurrency.databinding.ItemWalletV2Binding p0) {
        }
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public androidx.appcompat.widget.LinearLayoutCompat getSwipeableContainerView() {
            return null;
        }
        
        public ViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.View view, @org.jetbrains.annotations.NotNull()
        co.yap.multicurrency.wallet2.WalletItemViewModel viewModel, @org.jetbrains.annotations.NotNull()
        androidx.databinding.ViewDataBinding mDataBinding) {
            super(null, null, null);
        }
    }
}