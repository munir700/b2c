package co.yap.multicurrency.ratesalerts.base;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f\u00a8\u0006\u0016"}, d2 = {"Lco/yap/multicurrency/ratesalerts/base/RatesAndAlertsBaseVM;", "S", "Lco/yap/yapcore/IBase$State;", "Lco/yap/yapcore/BaseViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "parentViewModel", "Lco/yap/multicurrency/ratesalerts/base/IRatesAndAlertsBase$ViewModel;", "getParentViewModel", "()Lco/yap/multicurrency/ratesalerts/base/IRatesAndAlertsBase$ViewModel;", "setParentViewModel", "(Lco/yap/multicurrency/ratesalerts/base/IRatesAndAlertsBase$ViewModel;)V", "setTBLeftIconVisibility", "", "visibility", "", "setTBRightIconVisibility", "setToolBarTitle", "title", "", "setToolbarVisibility", "multicurrency_debug"})
public abstract class RatesAndAlertsBaseVM<S extends co.yap.yapcore.IBase.State> extends co.yap.yapcore.BaseViewModel<S> {
    @org.jetbrains.annotations.Nullable()
    private co.yap.multicurrency.ratesalerts.base.IRatesAndAlertsBase.ViewModel parentViewModel;
    
    @org.jetbrains.annotations.Nullable()
    public final co.yap.multicurrency.ratesalerts.base.IRatesAndAlertsBase.ViewModel getParentViewModel() {
        return null;
    }
    
    public final void setParentViewModel(@org.jetbrains.annotations.Nullable()
    co.yap.multicurrency.ratesalerts.base.IRatesAndAlertsBase.ViewModel p0) {
    }
    
    public final void setToolBarTitle(@org.jetbrains.annotations.NotNull()
    java.lang.String title) {
    }
    
    public final void setToolbarVisibility(boolean visibility) {
    }
    
    public final void setTBLeftIconVisibility(boolean visibility) {
    }
    
    public final void setTBRightIconVisibility(boolean visibility) {
    }
    
    public RatesAndAlertsBaseVM(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
}