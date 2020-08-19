package co.yap.multicurrency.ratesalerts.alerts;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010&\u001a\u00020\'2\u0006\u0010(\u001a\u00020\u0011H\u0016J\u000e\u0010)\u001a\b\u0012\u0004\u0012\u00020\u001c0*H\u0002J\u0010\u0010+\u001a\u00020\'2\u0006\u0010(\u001a\u00020\u0011H\u0016J\b\u0010,\u001a\u00020\'H\u0016J\b\u0010-\u001a\u00020\'H\u0002J\u0018\u0010.\u001a\u00020\'2\u0006\u0010/\u001a\u0002002\u0006\u0010\u001b\u001a\u00020\u001cH\u0002R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR \u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0011X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\u00020\u001cX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u000e\u0010!\u001a\u00020\"X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010#\u001a\u00020\u0002X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010%\u00a8\u00061"}, d2 = {"Lco/yap/multicurrency/ratesalerts/alerts/MCPriceAlertsVM;", "Lco/yap/multicurrency/ratesalerts/base/RatesAndAlertsBaseVM;", "Lco/yap/multicurrency/ratesalerts/alerts/IMCPriceAlerts$State;", "Lco/yap/multicurrency/ratesalerts/alerts/IMCPriceAlerts$ViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "alertsAdaptor", "Lco/yap/multicurrency/ratesalerts/alerts/adaptor/MCPriceAlertsAdapter;", "getAlertsAdaptor", "()Lco/yap/multicurrency/ratesalerts/alerts/adaptor/MCPriceAlertsAdapter;", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "deleteAlert", "Landroidx/lifecycle/MutableLiveData;", "", "getDeleteAlert", "()Landroidx/lifecycle/MutableLiveData;", "setDeleteAlert", "(Landroidx/lifecycle/MutableLiveData;)V", "deletePosition", "getDeletePosition", "()I", "setDeletePosition", "(I)V", "priceAlert", "Lco/yap/networking/customers/responsedtos/mcratesandalerts/MCPriceAlert;", "getPriceAlert", "()Lco/yap/networking/customers/responsedtos/mcratesandalerts/MCPriceAlert;", "setPriceAlert", "(Lco/yap/networking/customers/responsedtos/mcratesandalerts/MCPriceAlert;)V", "rvItemClickListener", "Lco/yap/yapcore/interfaces/OnItemClickListener;", "state", "getState", "()Lco/yap/multicurrency/ratesalerts/alerts/IMCPriceAlerts$State;", "deleteWalletRequest", "", "id", "getDummyRatesList", "", "handlePressOnView", "onCreate", "setupRecyclerView", "updatePriceAlert", "isActivate", "", "multicurrency_debug"})
public final class MCPriceAlertsVM extends co.yap.multicurrency.ratesalerts.base.RatesAndAlertsBaseVM<co.yap.multicurrency.ratesalerts.alerts.IMCPriceAlerts.State> implements co.yap.multicurrency.ratesalerts.alerts.IMCPriceAlerts.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final co.yap.multicurrency.ratesalerts.alerts.IMCPriceAlerts.State state = null;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.yapcore.SingleClickEvent clickEvent = null;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.multicurrency.ratesalerts.alerts.adaptor.MCPriceAlertsAdapter alertsAdaptor = null;
    private int deletePosition;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Integer> deleteAlert;
    @org.jetbrains.annotations.NotNull()
    private co.yap.networking.customers.responsedtos.mcratesandalerts.MCPriceAlert priceAlert;
    private final co.yap.yapcore.interfaces.OnItemClickListener rvItemClickListener = null;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.multicurrency.ratesalerts.alerts.IMCPriceAlerts.State getState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.yapcore.SingleClickEvent getClickEvent() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.multicurrency.ratesalerts.alerts.adaptor.MCPriceAlertsAdapter getAlertsAdaptor() {
        return null;
    }
    
    @java.lang.Override()
    public int getDeletePosition() {
        return 0;
    }
    
    @java.lang.Override()
    public void setDeletePosition(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.lang.Integer> getDeleteAlert() {
        return null;
    }
    
    @java.lang.Override()
    public void setDeleteAlert(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Integer> p0) {
    }
    
    @java.lang.Override()
    public void handlePressOnView(int id) {
    }
    
    @java.lang.Override()
    public void deleteWalletRequest(int id) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.networking.customers.responsedtos.mcratesandalerts.MCPriceAlert getPriceAlert() {
        return null;
    }
    
    @java.lang.Override()
    public void setPriceAlert(@org.jetbrains.annotations.NotNull()
    co.yap.networking.customers.responsedtos.mcratesandalerts.MCPriceAlert p0) {
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    private final void setupRecyclerView() {
    }
    
    private final java.util.List<co.yap.networking.customers.responsedtos.mcratesandalerts.MCPriceAlert> getDummyRatesList() {
        return null;
    }
    
    private final void updatePriceAlert(boolean isActivate, co.yap.networking.customers.responsedtos.mcratesandalerts.MCPriceAlert priceAlert) {
    }
    
    public MCPriceAlertsVM(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
}