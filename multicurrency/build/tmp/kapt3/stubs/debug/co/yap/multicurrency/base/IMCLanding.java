package co.yap.multicurrency.base;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u00a8\u0006\u0005"}, d2 = {"Lco/yap/multicurrency/base/IMCLanding;", "", "State", "View", "ViewModel", "multicurrency_debug"})
public abstract interface IMCLanding {
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001\u00a8\u0006\u0003"}, d2 = {"Lco/yap/multicurrency/base/IMCLanding$View;", "Lco/yap/yapcore/IBase$View;", "Lco/yap/multicurrency/base/IMCLanding$ViewModel;", "multicurrency_debug"})
    public static abstract interface View extends co.yap.yapcore.IBase.View<co.yap.multicurrency.base.IMCLanding.ViewModel> {
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH&J\u0010\u0010\r\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH&R\u0012\u0010\u0003\u001a\u00020\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\u0006\u00a8\u0006\u000e"}, d2 = {"Lco/yap/multicurrency/base/IMCLanding$ViewModel;", "Lco/yap/yapcore/IBase$ViewModel;", "Lco/yap/multicurrency/base/IMCLanding$State;", "addWalletClickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getAddWalletClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "clickEvent", "getClickEvent", "handlePressOnAddWalletView", "", "id", "", "handlePressOnView", "multicurrency_debug"})
    public static abstract interface ViewModel extends co.yap.yapcore.IBase.ViewModel<co.yap.multicurrency.base.IMCLanding.State> {
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.yapcore.SingleClickEvent getClickEvent();
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.yapcore.SingleClickEvent getAddWalletClickEvent();
        
        public abstract void handlePressOnView(int id);
        
        public abstract void handlePressOnAddWalletView(int id);
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\bf\u0018\u00002\u00020\u0001R\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0002\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u001e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0018\u0010\u000f\u001a\u00020\u0010X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0018\u0010\u0015\u001a\u00020\u0010X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0016\u0010\u0012\"\u0004\b\u0017\u0010\u0014R\u0018\u0010\u0018\u001a\u00020\u0010X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0019\u0010\u0012\"\u0004\b\u001a\u0010\u0014\u00a8\u0006\u001b"}, d2 = {"Lco/yap/multicurrency/base/IMCLanding$State;", "Lco/yap/yapcore/IBase$State;", "isPrimaryWallet", "Landroidx/lifecycle/MutableLiveData;", "", "()Landroidx/lifecycle/MutableLiveData;", "setPrimaryWallet", "(Landroidx/lifecycle/MutableLiveData;)V", "title", "Landroidx/databinding/ObservableField;", "", "getTitle", "()Landroidx/databinding/ObservableField;", "setTitle", "(Landroidx/databinding/ObservableField;)V", "toolbarAddWalletIcon", "Landroidx/databinding/ObservableBoolean;", "getToolbarAddWalletIcon", "()Landroidx/databinding/ObservableBoolean;", "setToolbarAddWalletIcon", "(Landroidx/databinding/ObservableBoolean;)V", "toolbarBackIcon", "getToolbarBackIcon", "setToolbarBackIcon", "toolbarRateIcon", "getToolbarRateIcon", "setToolbarRateIcon", "multicurrency_debug"})
    public static abstract interface State extends co.yap.yapcore.IBase.State {
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.lang.String> getTitle();
        
        public abstract void setTitle(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.lang.String> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableBoolean getToolbarBackIcon();
        
        public abstract void setToolbarBackIcon(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableBoolean p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableBoolean getToolbarRateIcon();
        
        public abstract void setToolbarRateIcon(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableBoolean p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableBoolean getToolbarAddWalletIcon();
        
        public abstract void setToolbarAddWalletIcon(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableBoolean p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.Boolean> isPrimaryWallet();
        
        public abstract void setPrimaryWallet(@org.jetbrains.annotations.NotNull()
        androidx.lifecycle.MutableLiveData<java.lang.Boolean> p0);
    }
}