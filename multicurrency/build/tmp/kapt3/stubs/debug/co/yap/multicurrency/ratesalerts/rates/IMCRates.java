package co.yap.multicurrency.ratesalerts.rates;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u00a8\u0006\u0005"}, d2 = {"Lco/yap/multicurrency/ratesalerts/rates/IMCRates;", "", "State", "View", "ViewModel", "multicurrency_debug"})
public abstract interface IMCRates {
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\u0006H&\u00a8\u0006\b"}, d2 = {"Lco/yap/multicurrency/ratesalerts/rates/IMCRates$View;", "Lco/yap/yapcore/IBase$View;", "Lco/yap/multicurrency/ratesalerts/rates/IMCRates$ViewModel;", "getBinding", "Lco/yap/multicurrency/databinding/FragmentMcRatesBinding;", "removeObservers", "", "setObservers", "multicurrency_debug"})
    public static abstract interface View extends co.yap.yapcore.IBase.View<co.yap.multicurrency.ratesalerts.rates.IMCRates.ViewModel> {
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.multicurrency.databinding.FragmentMcRatesBinding getBinding();
        
        public abstract void setObservers();
        
        public abstract void removeObservers();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\tH&J\u0010\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\tH&R\u0012\u0010\u0003\u001a\u00020\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u001e\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0018\u0010\u000e\u001a\u00020\tX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0012\u0010\u0013\u001a\u00020\u0014X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u001b"}, d2 = {"Lco/yap/multicurrency/ratesalerts/rates/IMCRates$ViewModel;", "Lco/yap/yapcore/IBase$ViewModel;", "Lco/yap/multicurrency/ratesalerts/rates/IMCRates$State;", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "deleteAlert", "Landroidx/lifecycle/MutableLiveData;", "", "getDeleteAlert", "()Landroidx/lifecycle/MutableLiveData;", "setDeleteAlert", "(Landroidx/lifecycle/MutableLiveData;)V", "deletePosition", "getDeletePosition", "()I", "setDeletePosition", "(I)V", "ratesAdaptor", "Lco/yap/multicurrency/ratesalerts/rates/adaptor/MCRatesAdapter;", "getRatesAdaptor", "()Lco/yap/multicurrency/ratesalerts/rates/adaptor/MCRatesAdapter;", "deleteWalletRequest", "", "id", "handlePressOnView", "multicurrency_debug"})
    public static abstract interface ViewModel extends co.yap.yapcore.IBase.ViewModel<co.yap.multicurrency.ratesalerts.rates.IMCRates.State> {
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.yapcore.SingleClickEvent getClickEvent();
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.multicurrency.ratesalerts.rates.adaptor.MCRatesAdapter getRatesAdaptor();
        
        public abstract int getDeletePosition();
        
        public abstract void setDeletePosition(int p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.Integer> getDeleteAlert();
        
        public abstract void setDeleteAlert(@org.jetbrains.annotations.NotNull()
        androidx.lifecycle.MutableLiveData<java.lang.Integer> p0);
        
        public abstract void handlePressOnView(int id);
        
        public abstract void deleteWalletRequest(int id);
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001\u00a8\u0006\u0002"}, d2 = {"Lco/yap/multicurrency/ratesalerts/rates/IMCRates$State;", "Lco/yap/yapcore/IBase$State;", "multicurrency_debug"})
    public static abstract interface State extends co.yap.yapcore.IBase.State {
    }
}