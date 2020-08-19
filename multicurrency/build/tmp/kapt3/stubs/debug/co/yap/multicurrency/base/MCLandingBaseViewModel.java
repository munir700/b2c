package co.yap.multicurrency.base;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f\u00a8\u0006\u0017"}, d2 = {"Lco/yap/multicurrency/base/MCLandingBaseViewModel;", "S", "Lco/yap/yapcore/IBase$State;", "Lco/yap/yapcore/BaseViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "parentViewModel", "Lco/yap/multicurrency/base/IMCLanding$ViewModel;", "getParentViewModel", "()Lco/yap/multicurrency/base/IMCLanding$ViewModel;", "setParentViewModel", "(Lco/yap/multicurrency/base/IMCLanding$ViewModel;)V", "setAddWalletIconVisibility", "", "visibility", "", "setToolBarTitle", "title", "", "setToolbarBackIconVisibility", "setToolbarRateIconVisibility", "setToolbarVisibility", "multicurrency_debug"})
public abstract class MCLandingBaseViewModel<S extends co.yap.yapcore.IBase.State> extends co.yap.yapcore.BaseViewModel<S> {
    @org.jetbrains.annotations.Nullable()
    private co.yap.multicurrency.base.IMCLanding.ViewModel parentViewModel;
    
    @org.jetbrains.annotations.Nullable()
    public final co.yap.multicurrency.base.IMCLanding.ViewModel getParentViewModel() {
        return null;
    }
    
    public final void setParentViewModel(@org.jetbrains.annotations.Nullable()
    co.yap.multicurrency.base.IMCLanding.ViewModel p0) {
    }
    
    public final void setToolBarTitle(@org.jetbrains.annotations.NotNull()
    java.lang.String title) {
    }
    
    public final void setToolbarVisibility(boolean visibility) {
    }
    
    public final void setToolbarBackIconVisibility(boolean visibility) {
    }
    
    public final void setAddWalletIconVisibility(boolean visibility) {
    }
    
    public final void setToolbarRateIconVisibility(boolean visibility) {
    }
    
    public MCLandingBaseViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
}