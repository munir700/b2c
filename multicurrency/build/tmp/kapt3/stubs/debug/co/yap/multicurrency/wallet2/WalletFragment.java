package co.yap.multicurrency.wallet2;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u00032\u00020\u0004B\u0005\u00a2\u0006\u0002\u0010\u0005J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0015H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0002J\u0012\u0010\u0019\u001a\u00020\u00182\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u0018H\u0016J \u0010\u001d\u001a\u00020\u00182\u0006\u0010\u001e\u001a\u00020\u00152\u0006\u0010\u001f\u001a\u00020\u00152\u0006\u0010 \u001a\u00020!H\u0016J\u0018\u0010\"\u001a\u00020\u00182\u0006\u0010#\u001a\u00020\u00152\u0006\u0010$\u001a\u00020\u0015H\u0016J\u0018\u0010%\u001a\u00020\u00182\u0006\u0010\u001e\u001a\u00020\u00152\u0006\u0010\u001f\u001a\u00020\u0015H\u0016J\u0010\u0010&\u001a\u00020\u00182\u0006\u0010\'\u001a\u00020\u0015H\u0016J\b\u0010(\u001a\u00020\u0018H\u0016J\u001a\u0010)\u001a\u00020\u00182\u0006\u0010*\u001a\u00020+2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u00020\u00118VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006,"}, d2 = {"Lco/yap/multicurrency/wallet2/WalletFragment;", "Lco/yap/multicurrency/base/MCLandingBaseFragment;", "Lco/yap/multicurrency/wallet2/IWallet$ViewModel;", "Lco/yap/multicurrency/wallet2/IWallet$View;", "Lco/yap/widgets/advrecyclerview/draggable/RecyclerViewDragDropManager$OnItemDragEventListener;", "()V", "mAdpter", "Lco/yap/multicurrency/wallet2/WalletAdapter;", "mRecyclerViewDragDropManager", "Lco/yap/widgets/advrecyclerview/draggable/RecyclerViewDragDropManager;", "mRecyclerViewSwipeManager", "Lco/yap/widgets/advrecyclerview/swipeable/RecyclerViewSwipeManager;", "mRecyclerViewTouchActionGuardManager", "Lco/yap/widgets/advrecyclerview/touchguard/RecyclerViewTouchActionGuardManager;", "mWrappedAdapter", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "viewModel", "Lco/yap/multicurrency/wallet2/WalletViewModel;", "getViewModel", "()Lco/yap/multicurrency/wallet2/WalletViewModel;", "getBindingVariable", "", "getLayoutId", "initDragDropAdapter", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onItemDragFinished", "fromPosition", "toPosition", "result", "", "onItemDragMoveDistanceUpdated", "offsetX", "offsetY", "onItemDragPositionChanged", "onItemDragStarted", "position", "onPause", "onViewCreated", "view", "Landroid/view/View;", "multicurrency_debug"})
public final class WalletFragment extends co.yap.multicurrency.base.MCLandingBaseFragment<co.yap.multicurrency.wallet2.IWallet.ViewModel> implements co.yap.multicurrency.wallet2.IWallet.View, co.yap.widgets.advrecyclerview.draggable.RecyclerViewDragDropManager.OnItemDragEventListener {
    private androidx.recyclerview.widget.RecyclerView.Adapter<?> mWrappedAdapter;
    private co.yap.widgets.advrecyclerview.draggable.RecyclerViewDragDropManager mRecyclerViewDragDropManager;
    private co.yap.widgets.advrecyclerview.swipeable.RecyclerViewSwipeManager mRecyclerViewSwipeManager;
    private co.yap.widgets.advrecyclerview.touchguard.RecyclerViewTouchActionGuardManager mRecyclerViewTouchActionGuardManager;
    private co.yap.multicurrency.wallet2.WalletAdapter mAdpter;
    private java.util.HashMap _$_findViewCache;
    
    @java.lang.Override()
    public int getBindingVariable() {
        return 0;
    }
    
    @java.lang.Override()
    public int getLayoutId() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.multicurrency.wallet2.WalletViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    public void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initDragDropAdapter() {
    }
    
    @java.lang.Override()
    public void onItemDragStarted(int position) {
    }
    
    @java.lang.Override()
    public void onItemDragPositionChanged(int fromPosition, int toPosition) {
    }
    
    @java.lang.Override()
    public void onItemDragFinished(int fromPosition, int toPosition, boolean result) {
    }
    
    @java.lang.Override()
    public void onItemDragMoveDistanceUpdated(int offsetX, int offsetY) {
    }
    
    @java.lang.Override()
    public void onPause() {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
    
    public WalletFragment() {
        super();
    }
}