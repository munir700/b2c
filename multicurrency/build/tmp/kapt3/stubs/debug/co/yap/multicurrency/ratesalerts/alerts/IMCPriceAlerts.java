package co.yap.multicurrency.ratesalerts.alerts;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u00a8\u0006\u0005"}, d2 = {"Lco/yap/multicurrency/ratesalerts/alerts/IMCPriceAlerts;", "", "State", "View", "ViewModel", "multicurrency_debug"})
public abstract interface IMCPriceAlerts {
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\u0006H&\u00a8\u0006\b"}, d2 = {"Lco/yap/multicurrency/ratesalerts/alerts/IMCPriceAlerts$View;", "Lco/yap/yapcore/IBase$View;", "Lco/yap/multicurrency/ratesalerts/alerts/IMCPriceAlerts$ViewModel;", "getBinding", "Lco/yap/multicurrency/databinding/FragmentMcAlertsBinding;", "removeObservers", "", "setObservers", "multicurrency_debug"})
    public static abstract interface View extends co.yap.yapcore.IBase.View<co.yap.multicurrency.ratesalerts.alerts.IMCPriceAlerts.ViewModel> {
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.multicurrency.databinding.FragmentMcAlertsBinding getBinding();
        
        public abstract void setObservers();
        
        public abstract void removeObservers();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\rH&J\u0010\u0010 \u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\rH&R\u0012\u0010\u0003\u001a\u00020\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u001e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0018\u0010\u0012\u001a\u00020\rX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0018\u0010\u0017\u001a\u00020\u0018X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001c\u00a8\u0006!"}, d2 = {"Lco/yap/multicurrency/ratesalerts/alerts/IMCPriceAlerts$ViewModel;", "Lco/yap/yapcore/IBase$ViewModel;", "Lco/yap/multicurrency/ratesalerts/alerts/IMCPriceAlerts$State;", "alertsAdaptor", "Lco/yap/multicurrency/ratesalerts/alerts/adaptor/MCPriceAlertsAdapter;", "getAlertsAdaptor", "()Lco/yap/multicurrency/ratesalerts/alerts/adaptor/MCPriceAlertsAdapter;", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "deleteAlert", "Landroidx/lifecycle/MutableLiveData;", "", "getDeleteAlert", "()Landroidx/lifecycle/MutableLiveData;", "setDeleteAlert", "(Landroidx/lifecycle/MutableLiveData;)V", "deletePosition", "getDeletePosition", "()I", "setDeletePosition", "(I)V", "priceAlert", "Lco/yap/networking/customers/responsedtos/mcratesandalerts/MCPriceAlert;", "getPriceAlert", "()Lco/yap/networking/customers/responsedtos/mcratesandalerts/MCPriceAlert;", "setPriceAlert", "(Lco/yap/networking/customers/responsedtos/mcratesandalerts/MCPriceAlert;)V", "deleteWalletRequest", "", "id", "handlePressOnView", "multicurrency_debug"})
    public static abstract interface ViewModel extends co.yap.yapcore.IBase.ViewModel<co.yap.multicurrency.ratesalerts.alerts.IMCPriceAlerts.State> {
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.yapcore.SingleClickEvent getClickEvent();
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.networking.customers.responsedtos.mcratesandalerts.MCPriceAlert getPriceAlert();
        
        public abstract void setPriceAlert(@org.jetbrains.annotations.NotNull()
        co.yap.networking.customers.responsedtos.mcratesandalerts.MCPriceAlert p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.multicurrency.ratesalerts.alerts.adaptor.MCPriceAlertsAdapter getAlertsAdaptor();
        
        public abstract int getDeletePosition();
        
        public abstract void setDeletePosition(int p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.lifecycle.MutableLiveData<java.lang.Integer> getDeleteAlert();
        
        public abstract void setDeleteAlert(@org.jetbrains.annotations.NotNull()
        androidx.lifecycle.MutableLiveData<java.lang.Integer> p0);
        
        public abstract void handlePressOnView(int id);
        
        public abstract void deleteWalletRequest(int id);
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001\u00a8\u0006\u0002"}, d2 = {"Lco/yap/multicurrency/ratesalerts/alerts/IMCPriceAlerts$State;", "Lco/yap/yapcore/IBase$State;", "multicurrency_debug"})
    public static abstract interface State extends co.yap.yapcore.IBase.State {
    }
}