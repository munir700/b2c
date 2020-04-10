package co.yap.sendMoney.home.interfaces;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u00a8\u0006\u0005"}, d2 = {"Lco/yap/sendMoney/home/interfaces/ISendMoneyHome;", "", "State", "View", "ViewModel", "sendmoney_debug"})
public abstract interface ISendMoneyHome {
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\t\bf\u0018\u00002\u00020\u0001R\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u000b\u0010\u0006\"\u0004\b\f\u0010\bR\u001e\u0010\r\u001a\b\u0012\u0004\u0012\u00020\n0\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\n0\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u000f\u0010\u0006\"\u0004\b\u0010\u0010\bR\u001e\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\n0\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0011\u0010\u0006\"\u0004\b\u0012\u0010\b\u00a8\u0006\u0013"}, d2 = {"Lco/yap/sendMoney/home/interfaces/ISendMoneyHome$State;", "Lco/yap/yapcore/IBase$State;", "flagDrawableResId", "Landroidx/databinding/ObservableField;", "", "getFlagDrawableResId", "()Landroidx/databinding/ObservableField;", "setFlagDrawableResId", "(Landroidx/databinding/ObservableField;)V", "hasBeneficiary", "", "getHasBeneficiary", "setHasBeneficiary", "isNoBeneficiary", "setNoBeneficiary", "isNoRecentBeneficiary", "setNoRecentBeneficiary", "isSearching", "setSearching", "sendmoney_debug"})
    public static abstract interface State extends co.yap.yapcore.IBase.State {
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.lang.Boolean> isNoBeneficiary();
        
        public abstract void setNoBeneficiary(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.lang.Boolean> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.lang.Boolean> getHasBeneficiary();
        
        public abstract void setHasBeneficiary(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.lang.Boolean> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.lang.Boolean> isNoRecentBeneficiary();
        
        public abstract void setNoRecentBeneficiary(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.lang.Boolean> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.lang.Boolean> isSearching();
        
        public abstract void setSearching(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.lang.Boolean> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.lang.Integer> getFlagDrawableResId();
        
        public abstract void setFlagDrawableResId(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.lang.Integer> p0);
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u000e\u0010#\u001a\b\u0012\u0004\u0012\u00020\u001a0$H&J\u0010\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020\u0017H&J\b\u0010(\u001a\u00020&H&J\u0010\u0010)\u001a\u00020&2\u0006\u0010*\u001a\u00020\u0017H&J\b\u0010+\u001a\u00020&H&R\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u001e\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\tX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0018\u0010\u000e\u001a\u00020\u000fX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0018\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\tX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\rR\u0018\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\tX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0018\u0010\rR\u001e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\tX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u001b\u0010\r\"\u0004\b\u001c\u0010\u001dR\u001e\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\tX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001f\u0010\rR\u0018\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\tX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\"\u0010\r\u00a8\u0006,"}, d2 = {"Lco/yap/sendMoney/home/interfaces/ISendMoneyHome$ViewModel;", "Lco/yap/yapcore/IBase$ViewModel;", "Lco/yap/sendMoney/home/interfaces/ISendMoneyHome$State;", "adapter", "Landroidx/databinding/ObservableField;", "Lco/yap/sendMoney/home/adapters/RecentTransferAdaptor;", "getAdapter", "()Landroidx/databinding/ObservableField;", "allBeneficiariesLiveData", "Landroidx/lifecycle/MutableLiveData;", "", "Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;", "getAllBeneficiariesLiveData", "()Landroidx/lifecycle/MutableLiveData;", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "setClickEvent", "(Lco/yap/yapcore/SingleClickEvent;)V", "isSearching", "", "onDeleteSuccess", "", "getOnDeleteSuccess", "pagingState", "Lco/yap/yapcore/helpers/PagingState;", "getPagingState", "setPagingState", "(Landroidx/lifecycle/MutableLiveData;)V", "recentTransferData", "getRecentTransferData", "searchQuery", "", "getSearchQuery", "getState", "Landroidx/lifecycle/LiveData;", "handlePressOnView", "", "id", "requestAllBeneficiaries", "requestDeleteBeneficiary", "beneficiaryId", "requestRecentBeneficiaries", "sendmoney_debug"})
    public static abstract interface ViewModel extends co.yap.yapcore.IBase.ViewModel<co.yap.sendMoney.home.interfaces.ISendMoneyHome.State> {
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.yapcore.SingleClickEvent getClickEvent();
        
        public abstract void setClickEvent(@org.jetbrains.annotations.NotNull()
        co.yap.yapcore.SingleClickEvent p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<co.yap.yapcore.helpers.PagingState> getPagingState();
        
        public abstract void setPagingState(@org.jetbrains.annotations.NotNull()
        androidx.lifecycle.MutableLiveData<co.yap.yapcore.helpers.PagingState> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.util.List<co.yap.networking.customers.responsedtos.sendmoney.Beneficiary>> getAllBeneficiariesLiveData();
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.Integer> getOnDeleteSuccess();
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.util.List<co.yap.networking.customers.responsedtos.sendmoney.Beneficiary>> getRecentTransferData();
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<co.yap.sendMoney.home.adapters.RecentTransferAdaptor> getAdapter();
        
        public abstract void handlePressOnView(int id);
        
        public abstract void requestDeleteBeneficiary(int beneficiaryId);
        
        public abstract void requestRecentBeneficiaries();
        
        public abstract void requestAllBeneficiaries();
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.LiveData<co.yap.yapcore.helpers.PagingState> getState();
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.String> getSearchQuery();
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.Boolean> isSearching();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001\u00a8\u0006\u0003"}, d2 = {"Lco/yap/sendMoney/home/interfaces/ISendMoneyHome$View;", "Lco/yap/yapcore/IBase$View;", "Lco/yap/sendMoney/home/interfaces/ISendMoneyHome$ViewModel;", "sendmoney_debug"})
    public static abstract interface View extends co.yap.yapcore.IBase.View<co.yap.sendMoney.home.interfaces.ISendMoneyHome.ViewModel> {
    }
}