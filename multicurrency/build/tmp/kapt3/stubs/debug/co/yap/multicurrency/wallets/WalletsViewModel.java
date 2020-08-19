package co.yap.multicurrency.wallets;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u00105\u001a\u0002062\u0006\u00107\u001a\u00020\u001bH\u0016J\b\u0010#\u001a\u000206H\u0016J\u0010\u00108\u001a\u0002062\u0006\u00107\u001a\u00020\u001bH\u0016J\b\u00109\u001a\u000206H\u0016J\u0006\u0010:\u001a\u000206R \u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR \u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u0016X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R \u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR \u0010 \u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u001d\"\u0004\b\"\u0010\u001fR \u0010#\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u000b\"\u0004\b%\u0010\rR \u0010&\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\'\u0010\u000b\"\u0004\b(\u0010\rR\u0014\u0010)\u001a\u00020*X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010,R \u0010-\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\u0012\"\u0004\b/\u0010\u0014R\u0014\u00100\u001a\u00020\u0002X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u00102R\u0014\u00103\u001a\u00020*X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b4\u0010,\u00a8\u0006;"}, d2 = {"Lco/yap/multicurrency/wallets/WalletsViewModel;", "Lco/yap/multicurrency/base/MCLandingBaseViewModel;", "Lco/yap/multicurrency/wallets/IWallets$State;", "Lco/yap/multicurrency/wallets/IWallets$ViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "activeWallet", "Landroidx/databinding/ObservableField;", "", "getActiveWallet", "()Landroidx/databinding/ObservableField;", "setActiveWallet", "(Landroidx/databinding/ObservableField;)V", "activeWalletsList", "", "Lco/yap/multicurrency/wallets/MultiCurrencyWallet;", "getActiveWalletsList", "()Ljava/util/List;", "setActiveWalletsList", "(Ljava/util/List;)V", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "deletePosition", "Landroidx/lifecycle/MutableLiveData;", "", "getDeletePosition", "()Landroidx/lifecycle/MutableLiveData;", "setDeletePosition", "(Landroidx/lifecycle/MutableLiveData;)V", "deleteWallet", "getDeleteWallet", "setDeleteWallet", "editWallet", "getEditWallet", "setEditWallet", "inActiveWallet", "getInActiveWallet", "setInActiveWallet", "inActiveWalletsAdapter", "Lco/yap/multicurrency/wallets/adapter/WalletsAdapter;", "getInActiveWalletsAdapter", "()Lco/yap/multicurrency/wallets/adapter/WalletsAdapter;", "inActiveWalletsList", "getInActiveWalletsList", "setInActiveWalletsList", "state", "getState", "()Lco/yap/multicurrency/wallets/IWallets$State;", "walletsAdapter", "getWalletsAdapter", "deleteWalletRequest", "", "id", "handlePressOnView", "onCreate", "setUpDummyData", "multicurrency_debug"})
public final class WalletsViewModel extends co.yap.multicurrency.base.MCLandingBaseViewModel<co.yap.multicurrency.wallets.IWallets.State> implements co.yap.multicurrency.wallets.IWallets.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final co.yap.multicurrency.wallets.IWallets.State state = null;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.yapcore.SingleClickEvent clickEvent = null;
    @org.jetbrains.annotations.NotNull()
    private androidx.databinding.ObservableField<java.lang.Boolean> activeWallet;
    @org.jetbrains.annotations.NotNull()
    private androidx.databinding.ObservableField<java.lang.Boolean> inActiveWallet;
    @org.jetbrains.annotations.NotNull()
    private androidx.databinding.ObservableField<java.lang.Boolean> editWallet;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.multicurrency.wallets.adapter.WalletsAdapter walletsAdapter = null;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.multicurrency.wallets.adapter.WalletsAdapter inActiveWalletsAdapter = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<co.yap.multicurrency.wallets.MultiCurrencyWallet> activeWalletsList;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<co.yap.multicurrency.wallets.MultiCurrencyWallet> inActiveWalletsList;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Integer> deleteWallet;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Integer> deletePosition;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.multicurrency.wallets.IWallets.State getState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.yapcore.SingleClickEvent getClickEvent() {
        return null;
    }
    
    @java.lang.Override()
    public void handlePressOnView(int id) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.databinding.ObservableField<java.lang.Boolean> getActiveWallet() {
        return null;
    }
    
    @java.lang.Override()
    public void setActiveWallet(@org.jetbrains.annotations.NotNull()
    androidx.databinding.ObservableField<java.lang.Boolean> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.databinding.ObservableField<java.lang.Boolean> getInActiveWallet() {
        return null;
    }
    
    @java.lang.Override()
    public void setInActiveWallet(@org.jetbrains.annotations.NotNull()
    androidx.databinding.ObservableField<java.lang.Boolean> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.databinding.ObservableField<java.lang.Boolean> getEditWallet() {
        return null;
    }
    
    @java.lang.Override()
    public void setEditWallet(@org.jetbrains.annotations.NotNull()
    androidx.databinding.ObservableField<java.lang.Boolean> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.multicurrency.wallets.adapter.WalletsAdapter getWalletsAdapter() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.multicurrency.wallets.adapter.WalletsAdapter getInActiveWalletsAdapter() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.util.List<co.yap.multicurrency.wallets.MultiCurrencyWallet> getActiveWalletsList() {
        return null;
    }
    
    @java.lang.Override()
    public void setActiveWalletsList(@org.jetbrains.annotations.NotNull()
    java.util.List<co.yap.multicurrency.wallets.MultiCurrencyWallet> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.util.List<co.yap.multicurrency.wallets.MultiCurrencyWallet> getInActiveWalletsList() {
        return null;
    }
    
    @java.lang.Override()
    public void setInActiveWalletsList(@org.jetbrains.annotations.NotNull()
    java.util.List<co.yap.multicurrency.wallets.MultiCurrencyWallet> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.lang.Integer> getDeleteWallet() {
        return null;
    }
    
    @java.lang.Override()
    public void setDeleteWallet(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Integer> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.lang.Integer> getDeletePosition() {
        return null;
    }
    
    @java.lang.Override()
    public void setDeletePosition(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Integer> p0) {
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    public final void setUpDummyData() {
    }
    
    @java.lang.Override()
    public void deleteWalletRequest(int id) {
    }
    
    @java.lang.Override()
    public void editWallet() {
    }
    
    public WalletsViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
}