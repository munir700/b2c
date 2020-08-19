package co.yap.multicurrency.base;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003R \u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\u0007\"\u0004\b\b\u0010\tR \u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0012X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\u00020\u0012X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0014\"\u0004\b\u0019\u0010\u0016R\u001a\u0010\u001a\u001a\u00020\u0012X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0014\"\u0004\b\u001c\u0010\u0016\u00a8\u0006\u001d"}, d2 = {"Lco/yap/multicurrency/base/MCLandingState;", "Lco/yap/yapcore/BaseState;", "Lco/yap/multicurrency/base/IMCLanding$State;", "()V", "isPrimaryWallet", "Landroidx/lifecycle/MutableLiveData;", "", "()Landroidx/lifecycle/MutableLiveData;", "setPrimaryWallet", "(Landroidx/lifecycle/MutableLiveData;)V", "title", "Landroidx/databinding/ObservableField;", "", "getTitle", "()Landroidx/databinding/ObservableField;", "setTitle", "(Landroidx/databinding/ObservableField;)V", "toolbarAddWalletIcon", "Landroidx/databinding/ObservableBoolean;", "getToolbarAddWalletIcon", "()Landroidx/databinding/ObservableBoolean;", "setToolbarAddWalletIcon", "(Landroidx/databinding/ObservableBoolean;)V", "toolbarBackIcon", "getToolbarBackIcon", "setToolbarBackIcon", "toolbarRateIcon", "getToolbarRateIcon", "setToolbarRateIcon", "multicurrency_debug"})
public final class MCLandingState extends co.yap.yapcore.BaseState implements co.yap.multicurrency.base.IMCLanding.State {
    @org.jetbrains.annotations.NotNull()
    private androidx.databinding.ObservableField<java.lang.String> title;
    @org.jetbrains.annotations.NotNull()
    private androidx.databinding.ObservableBoolean toolbarBackIcon;
    @org.jetbrains.annotations.NotNull()
    private androidx.databinding.ObservableBoolean toolbarRateIcon;
    @org.jetbrains.annotations.NotNull()
    private androidx.databinding.ObservableBoolean toolbarAddWalletIcon;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Boolean> isPrimaryWallet;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.databinding.ObservableField<java.lang.String> getTitle() {
        return null;
    }
    
    @java.lang.Override()
    public void setTitle(@org.jetbrains.annotations.NotNull()
    androidx.databinding.ObservableField<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.databinding.ObservableBoolean getToolbarBackIcon() {
        return null;
    }
    
    @java.lang.Override()
    public void setToolbarBackIcon(@org.jetbrains.annotations.NotNull()
    androidx.databinding.ObservableBoolean p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.databinding.ObservableBoolean getToolbarRateIcon() {
        return null;
    }
    
    @java.lang.Override()
    public void setToolbarRateIcon(@org.jetbrains.annotations.NotNull()
    androidx.databinding.ObservableBoolean p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.databinding.ObservableBoolean getToolbarAddWalletIcon() {
        return null;
    }
    
    @java.lang.Override()
    public void setToolbarAddWalletIcon(@org.jetbrains.annotations.NotNull()
    androidx.databinding.ObservableBoolean p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.lang.Boolean> isPrimaryWallet() {
        return null;
    }
    
    @java.lang.Override()
    public void setPrimaryWallet(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Boolean> p0) {
    }
    
    public MCLandingState() {
        super();
    }
}