package co.yap.multicurrency.walletcountry;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u0018H\u0016J\b\u0010\u001c\u001a\u00020\u0018H\u0016R\u001a\u0010\u0007\u001a\u00020\bX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u000eX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u0014X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u001d"}, d2 = {"Lco/yap/multicurrency/walletcountry/WalletDetailsViewModel;", "Lco/yap/multicurrency/base/MCLandingBaseViewModel;", "Lco/yap/multicurrency/walletcountry/IWalletDetails$State;", "Lco/yap/multicurrency/walletcountry/IWalletDetails$ViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "adapter", "Lco/yap/multicurrency/walletcountry/adapter/WalletDetailsListAdapter;", "getAdapter", "()Lco/yap/multicurrency/walletcountry/adapter/WalletDetailsListAdapter;", "setAdapter", "(Lco/yap/multicurrency/walletcountry/adapter/WalletDetailsListAdapter;)V", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "setClickEvent", "(Lco/yap/yapcore/SingleClickEvent;)V", "state", "Lco/yap/multicurrency/walletcountry/WalletDetailsState;", "getState", "()Lco/yap/multicurrency/walletcountry/WalletDetailsState;", "handlePressOnView", "", "id", "", "onCreate", "onResume", "multicurrency_debug"})
public final class WalletDetailsViewModel extends co.yap.multicurrency.base.MCLandingBaseViewModel<co.yap.multicurrency.walletcountry.IWalletDetails.State> implements co.yap.multicurrency.walletcountry.IWalletDetails.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private co.yap.yapcore.SingleClickEvent clickEvent;
    @org.jetbrains.annotations.NotNull()
    private co.yap.multicurrency.walletcountry.adapter.WalletDetailsListAdapter adapter;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.multicurrency.walletcountry.WalletDetailsState state = null;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.yapcore.SingleClickEvent getClickEvent() {
        return null;
    }
    
    @java.lang.Override()
    public void setClickEvent(@org.jetbrains.annotations.NotNull()
    co.yap.yapcore.SingleClickEvent p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.multicurrency.walletcountry.adapter.WalletDetailsListAdapter getAdapter() {
        return null;
    }
    
    @java.lang.Override()
    public void setAdapter(@org.jetbrains.annotations.NotNull()
    co.yap.multicurrency.walletcountry.adapter.WalletDetailsListAdapter p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.multicurrency.walletcountry.WalletDetailsState getState() {
        return null;
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @java.lang.Override()
    public void onResume() {
    }
    
    @java.lang.Override()
    public void handlePressOnView(int id) {
    }
    
    public WalletDetailsViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
}