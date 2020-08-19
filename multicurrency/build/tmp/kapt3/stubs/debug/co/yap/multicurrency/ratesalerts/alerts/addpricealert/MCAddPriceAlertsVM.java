package co.yap.multicurrency.ratesalerts.alerts.addpricealert;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00150\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u0015H\u0016J\b\u0010\u001c\u001a\u00020\u0015H\u0002J\b\u0010\u001d\u001a\u00020\u0015H\u0016R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u0002X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u001e"}, d2 = {"Lco/yap/multicurrency/ratesalerts/alerts/addpricealert/MCAddPriceAlertsVM;", "Lco/yap/yapcore/BaseViewModel;", "Lco/yap/multicurrency/ratesalerts/alerts/addpricealert/IMCAddPriceAlerts$State;", "Lco/yap/multicurrency/ratesalerts/alerts/addpricealert/IMCAddPriceAlerts$ViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "priceAlert", "Lco/yap/networking/customers/responsedtos/mcratesandalerts/MCPriceAlert;", "getPriceAlert", "()Lco/yap/networking/customers/responsedtos/mcratesandalerts/MCPriceAlert;", "setPriceAlert", "(Lco/yap/networking/customers/responsedtos/mcratesandalerts/MCPriceAlert;)V", "state", "getState", "()Lco/yap/multicurrency/ratesalerts/alerts/addpricealert/IMCAddPriceAlerts$State;", "addPriceAlert", "", "success", "Lkotlin/Function0;", "handlePressOnView", "id", "", "onCreate", "setPriceAlertData", "validate", "multicurrency_debug"})
public final class MCAddPriceAlertsVM extends co.yap.yapcore.BaseViewModel<co.yap.multicurrency.ratesalerts.alerts.addpricealert.IMCAddPriceAlerts.State> implements co.yap.multicurrency.ratesalerts.alerts.addpricealert.IMCAddPriceAlerts.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final co.yap.multicurrency.ratesalerts.alerts.addpricealert.IMCAddPriceAlerts.State state = null;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.yapcore.SingleClickEvent clickEvent = null;
    @org.jetbrains.annotations.Nullable()
    private co.yap.networking.customers.responsedtos.mcratesandalerts.MCPriceAlert priceAlert;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.multicurrency.ratesalerts.alerts.addpricealert.IMCAddPriceAlerts.State getState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.yapcore.SingleClickEvent getClickEvent() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public co.yap.networking.customers.responsedtos.mcratesandalerts.MCPriceAlert getPriceAlert() {
        return null;
    }
    
    @java.lang.Override()
    public void setPriceAlert(@org.jetbrains.annotations.Nullable()
    co.yap.networking.customers.responsedtos.mcratesandalerts.MCPriceAlert p0) {
    }
    
    @java.lang.Override()
    public void handlePressOnView(int id) {
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    private final void setPriceAlertData() {
    }
    
    @java.lang.Override()
    public void validate() {
    }
    
    @java.lang.Override()
    public void addPriceAlert(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> success) {
    }
    
    public MCAddPriceAlertsVM(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
}