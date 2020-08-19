package co.yap.multicurrency.wallets;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u00a8\u0006\u0005"}, d2 = {"Lco/yap/multicurrency/wallets/IWallets;", "", "State", "View", "ViewModel", "multicurrency_debug"})
public abstract interface IWallets {
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0004H&\u00a8\u0006\u0006"}, d2 = {"Lco/yap/multicurrency/wallets/IWallets$View;", "Lco/yap/yapcore/IBase$View;", "Lco/yap/multicurrency/wallets/IWallets$ViewModel;", "removeObservers", "", "setObservers", "multicurrency_debug"})
    public static abstract interface View extends co.yap.yapcore.IBase.View<co.yap.multicurrency.wallets.IWallets.ViewModel> {
        
        public abstract void setObservers();
        
        public abstract void removeObservers();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0003\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0010\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\u0017H&J\b\u0010\u001f\u001a\u00020/H&J\u0010\u00101\u001a\u00020/2\u0006\u00100\u001a\u00020\u0017H&R\u001e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001e\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0012\u0010\u0011\u001a\u00020\u0012X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u001e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001e\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u001d\u0010\u0019\"\u0004\b\u001e\u0010\u001bR\u001e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b \u0010\u0007\"\u0004\b!\u0010\tR\u001e\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b#\u0010\u0007\"\u0004\b$\u0010\tR\u0012\u0010%\u001a\u00020&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\'\u0010(R\u001e\u0010)\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b*\u0010\u000e\"\u0004\b+\u0010\u0010R\u0012\u0010,\u001a\u00020&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b-\u0010(\u00a8\u00062"}, d2 = {"Lco/yap/multicurrency/wallets/IWallets$ViewModel;", "Lco/yap/yapcore/IBase$ViewModel;", "Lco/yap/multicurrency/wallets/IWallets$State;", "activeWallet", "Landroidx/databinding/ObservableField;", "", "getActiveWallet", "()Landroidx/databinding/ObservableField;", "setActiveWallet", "(Landroidx/databinding/ObservableField;)V", "activeWalletsList", "", "Lco/yap/multicurrency/wallets/MultiCurrencyWallet;", "getActiveWalletsList", "()Ljava/util/List;", "setActiveWalletsList", "(Ljava/util/List;)V", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "deletePosition", "Landroidx/lifecycle/MutableLiveData;", "", "getDeletePosition", "()Landroidx/lifecycle/MutableLiveData;", "setDeletePosition", "(Landroidx/lifecycle/MutableLiveData;)V", "deleteWallet", "getDeleteWallet", "setDeleteWallet", "editWallet", "getEditWallet", "setEditWallet", "inActiveWallet", "getInActiveWallet", "setInActiveWallet", "inActiveWalletsAdapter", "Lco/yap/multicurrency/wallets/adapter/WalletsAdapter;", "getInActiveWalletsAdapter", "()Lco/yap/multicurrency/wallets/adapter/WalletsAdapter;", "inActiveWalletsList", "getInActiveWalletsList", "setInActiveWalletsList", "walletsAdapter", "getWalletsAdapter", "deleteWalletRequest", "", "id", "handlePressOnView", "multicurrency_debug"})
    public static abstract interface ViewModel extends co.yap.yapcore.IBase.ViewModel<co.yap.multicurrency.wallets.IWallets.State> {
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.yapcore.SingleClickEvent getClickEvent();
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.multicurrency.wallets.adapter.WalletsAdapter getWalletsAdapter();
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.multicurrency.wallets.adapter.WalletsAdapter getInActiveWalletsAdapter();
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.util.List<co.yap.multicurrency.wallets.MultiCurrencyWallet> getActiveWalletsList();
        
        public abstract void setActiveWalletsList(@org.jetbrains.annotations.NotNull()
        java.util.List<co.yap.multicurrency.wallets.MultiCurrencyWallet> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract java.util.List<co.yap.multicurrency.wallets.MultiCurrencyWallet> getInActiveWalletsList();
        
        public abstract void setInActiveWalletsList(@org.jetbrains.annotations.NotNull()
        java.util.List<co.yap.multicurrency.wallets.MultiCurrencyWallet> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.Integer> getDeletePosition();
        
        public abstract void setDeletePosition(@org.jetbrains.annotations.NotNull()
        androidx.lifecycle.MutableLiveData<java.lang.Integer> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.Integer> getDeleteWallet();
        
        public abstract void setDeleteWallet(@org.jetbrains.annotations.NotNull()
        androidx.lifecycle.MutableLiveData<java.lang.Integer> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.lang.Boolean> getActiveWallet();
        
        public abstract void setActiveWallet(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.lang.Boolean> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.lang.Boolean> getInActiveWallet();
        
        public abstract void setInActiveWallet(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.lang.Boolean> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.lang.Boolean> getEditWallet();
        
        public abstract void setEditWallet(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.lang.Boolean> p0);
        
        public abstract void handlePressOnView(int id);
        
        public abstract void deleteWalletRequest(int id);
        
        public abstract void editWallet();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001R\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, d2 = {"Lco/yap/multicurrency/wallets/IWallets$State;", "Lco/yap/yapcore/IBase$State;", "editWalletText", "Landroidx/databinding/ObservableField;", "", "getEditWalletText", "()Landroidx/databinding/ObservableField;", "setEditWalletText", "(Landroidx/databinding/ObservableField;)V", "multicurrency_debug"})
    public static abstract interface State extends co.yap.yapcore.IBase.State {
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.lang.String> getEditWalletText();
        
        public abstract void setEditWalletText(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.lang.String> p0);
    }
}