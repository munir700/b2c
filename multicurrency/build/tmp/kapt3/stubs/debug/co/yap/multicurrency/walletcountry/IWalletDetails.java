package co.yap.multicurrency.walletcountry;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u00a8\u0006\u0005"}, d2 = {"Lco/yap/multicurrency/walletcountry/IWalletDetails;", "", "State", "View", "ViewModel", "multicurrency_debug"})
public abstract interface IWalletDetails {
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\b\u0010\u0003\u001a\u00020\u0004H&\u00a8\u0006\u0005"}, d2 = {"Lco/yap/multicurrency/walletcountry/IWalletDetails$View;", "Lco/yap/yapcore/IBase$View;", "Lco/yap/multicurrency/walletcountry/IWalletDetails$ViewModel;", "setObservers", "", "multicurrency_debug"})
    public static abstract interface View extends co.yap.yapcore.IBase.View<co.yap.multicurrency.walletcountry.IWalletDetails.ViewModel> {
        
        public abstract void setObservers();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H&R\u0018\u0010\u0003\u001a\u00020\u0004X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0018\u0010\t\u001a\u00020\nX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e\u00a8\u0006\u0013"}, d2 = {"Lco/yap/multicurrency/walletcountry/IWalletDetails$ViewModel;", "Lco/yap/yapcore/IBase$ViewModel;", "Lco/yap/multicurrency/walletcountry/IWalletDetails$State;", "adapter", "Lco/yap/multicurrency/walletcountry/adapter/WalletDetailsListAdapter;", "getAdapter", "()Lco/yap/multicurrency/walletcountry/adapter/WalletDetailsListAdapter;", "setAdapter", "(Lco/yap/multicurrency/walletcountry/adapter/WalletDetailsListAdapter;)V", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "setClickEvent", "(Lco/yap/yapcore/SingleClickEvent;)V", "handlePressOnView", "", "id", "", "multicurrency_debug"})
    public static abstract interface ViewModel extends co.yap.yapcore.IBase.ViewModel<co.yap.multicurrency.walletcountry.IWalletDetails.State> {
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.yapcore.SingleClickEvent getClickEvent();
        
        public abstract void setClickEvent(@org.jetbrains.annotations.NotNull()
        co.yap.yapcore.SingleClickEvent p0);
        
        public abstract void handlePressOnView(int id);
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.multicurrency.walletcountry.adapter.WalletDetailsListAdapter getAdapter();
        
        public abstract void setAdapter(@org.jetbrains.annotations.NotNull()
        co.yap.multicurrency.walletcountry.adapter.WalletDetailsListAdapter p0);
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001R \u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR \u0010\t\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\t\u0010\u0006\"\u0004\b\u000b\u0010\b\u00a8\u0006\f"}, d2 = {"Lco/yap/multicurrency/walletcountry/IWalletDetails$State;", "Lco/yap/yapcore/IBase$State;", "bankDetails", "Landroidx/lifecycle/MutableLiveData;", "Lco/yap/multicurrency/walletcountry/DefaultWalletBankDetails;", "getBankDetails", "()Landroidx/lifecycle/MutableLiveData;", "setBankDetails", "(Landroidx/lifecycle/MutableLiveData;)V", "isPrimaryWallet", "", "setPrimaryWallet", "multicurrency_debug"})
    public static abstract interface State extends co.yap.yapcore.IBase.State {
        
        @org.jetbrains.annotations.Nullable()
        public abstract androidx.lifecycle.MutableLiveData<co.yap.multicurrency.walletcountry.DefaultWalletBankDetails> getBankDetails();
        
        public abstract void setBankDetails(@org.jetbrains.annotations.Nullable()
        androidx.lifecycle.MutableLiveData<co.yap.multicurrency.walletcountry.DefaultWalletBankDetails> p0);
        
        @org.jetbrains.annotations.Nullable()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.Boolean> isPrimaryWallet();
        
        public abstract void setPrimaryWallet(@org.jetbrains.annotations.Nullable()
        androidx.lifecycle.MutableLiveData<java.lang.Boolean> p0);
    }
}