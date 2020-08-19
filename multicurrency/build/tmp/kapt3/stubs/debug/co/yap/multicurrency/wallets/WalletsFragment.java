package co.yap.multicurrency.wallets;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0005\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\b\u0010\u0015\u001a\u00020\tH\u0016J\b\u0010\u0016\u001a\u00020\tH\u0016J\u0018\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u0012\u0010\u001b\u001a\u00020\u00122\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u0012H\u0016J\b\u0010\u001f\u001a\u00020\u0012H\u0016J\b\u0010 \u001a\u00020\u0012H\u0016J\u001a\u0010!\u001a\u00020\u00122\u0006\u0010\"\u001a\u00020#2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\b\u0010$\u001a\u00020\u0012H\u0016J\u0010\u0010%\u001a\u00020\u00122\u0006\u0010&\u001a\u00020\'H\u0002J\b\u0010(\u001a\u00020\u0012H\u0002J\b\u0010)\u001a\u00020\u0012H\u0016J\u0006\u0010*\u001a\u00020\u0012J\u0010\u0010+\u001a\u00020\u00122\u0006\u0010,\u001a\u00020\u001dH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006-"}, d2 = {"Lco/yap/multicurrency/wallets/WalletsFragment;", "Lco/yap/multicurrency/base/MCLandingBaseFragment;", "Lco/yap/multicurrency/wallets/IWallets$ViewModel;", "Lco/yap/multicurrency/wallets/IWallets$View;", "()V", "activeWalletsItemClickListener", "Lco/yap/yapcore/interfaces/OnItemClickListener;", "addWalletObserver", "Landroidx/lifecycle/Observer;", "", "clickObserver", "deleteWalletObserver", "inActiveWalletsItemClickListener", "viewModel", "Lco/yap/multicurrency/wallets/WalletsViewModel;", "getViewModel", "()Lco/yap/multicurrency/wallets/WalletsViewModel;", "confirmDeleteWallet", "", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "getBindingVariable", "getLayoutId", "getSwipeListener", "Lcom/nikhilpanju/recyclerviewenhanced/RecyclerTouchListener;", "walletsAdapter", "Lco/yap/multicurrency/wallets/adapter/WalletsAdapter;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onPause", "onResume", "onViewCreated", "view", "Landroid/view/View;", "removeObservers", "setListItems", "wallet", "Lco/yap/multicurrency/wallets/MultiCurrencyWallet;", "setListeners", "setObservers", "startAddWalletFragment", "startCurrencyPickerFragment", "bundle", "multicurrency_debug"})
public final class WalletsFragment extends co.yap.multicurrency.base.MCLandingBaseFragment<co.yap.multicurrency.wallets.IWallets.ViewModel> implements co.yap.multicurrency.wallets.IWallets.View {
    private final co.yap.yapcore.interfaces.OnItemClickListener activeWalletsItemClickListener = null;
    private final co.yap.yapcore.interfaces.OnItemClickListener inActiveWalletsItemClickListener = null;
    private final androidx.lifecycle.Observer<java.lang.Integer> deleteWalletObserver = null;
    private final androidx.lifecycle.Observer<java.lang.Integer> clickObserver = null;
    private final androidx.lifecycle.Observer<java.lang.Integer> addWalletObserver = null;
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
    public co.yap.multicurrency.wallets.WalletsViewModel getViewModel() {
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
    
    private final void setListeners() {
    }
    
    @java.lang.Override()
    public void setObservers() {
    }
    
    @java.lang.Override()
    public void removeObservers() {
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    @java.lang.Override()
    public void onResume() {
    }
    
    @java.lang.Override()
    public void onPause() {
    }
    
    private final com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener getSwipeListener(androidx.recyclerview.widget.RecyclerView recyclerView, co.yap.multicurrency.wallets.adapter.WalletsAdapter walletsAdapter) {
        return null;
    }
    
    private final void confirmDeleteWallet(androidx.recyclerview.widget.RecyclerView recyclerView) {
    }
    
    public final void startAddWalletFragment() {
    }
    
    private final void startCurrencyPickerFragment(android.os.Bundle bundle) {
    }
    
    private final void setListItems(co.yap.multicurrency.wallets.MultiCurrencyWallet wallet) {
    }
    
    public WalletsFragment() {
        super();
    }
}