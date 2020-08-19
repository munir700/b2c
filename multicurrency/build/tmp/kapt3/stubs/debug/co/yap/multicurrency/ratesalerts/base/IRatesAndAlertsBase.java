package co.yap.multicurrency.ratesalerts.base;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u00a8\u0006\u0005"}, d2 = {"Lco/yap/multicurrency/ratesalerts/base/IRatesAndAlertsBase;", "", "State", "View", "ViewModel", "multicurrency_debug"})
public abstract interface IRatesAndAlertsBase {
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001\u00a8\u0006\u0003"}, d2 = {"Lco/yap/multicurrency/ratesalerts/base/IRatesAndAlertsBase$View;", "Lco/yap/yapcore/IBase$View;", "Lco/yap/multicurrency/ratesalerts/base/IRatesAndAlertsBase$ViewModel;", "multicurrency_debug"})
    public static abstract interface View extends co.yap.yapcore.IBase.View<co.yap.multicurrency.ratesalerts.base.IRatesAndAlertsBase.ViewModel> {
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH&R\u0012\u0010\u0003\u001a\u00020\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u000b"}, d2 = {"Lco/yap/multicurrency/ratesalerts/base/IRatesAndAlertsBase$ViewModel;", "Lco/yap/yapcore/IBase$ViewModel;", "Lco/yap/multicurrency/ratesalerts/base/IRatesAndAlertsBase$State;", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "handlePressOnView", "", "id", "", "multicurrency_debug"})
    public static abstract interface ViewModel extends co.yap.yapcore.IBase.ViewModel<co.yap.multicurrency.ratesalerts.base.IRatesAndAlertsBase.State> {
        
        @org.jetbrains.annotations.NotNull()
        public abstract co.yap.yapcore.SingleClickEvent getClickEvent();
        
        public abstract void handlePressOnView(int id);
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001R\u0018\u0010\u0002\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\t\u0010\u0005\"\u0004\b\n\u0010\u0007R\u001e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0012"}, d2 = {"Lco/yap/multicurrency/ratesalerts/base/IRatesAndAlertsBase$State;", "Lco/yap/yapcore/IBase$State;", "leftIcon", "Landroidx/databinding/ObservableBoolean;", "getLeftIcon", "()Landroidx/databinding/ObservableBoolean;", "setLeftIcon", "(Landroidx/databinding/ObservableBoolean;)V", "rightIcon", "getRightIcon", "setRightIcon", "tbTitle", "Landroidx/databinding/ObservableField;", "", "getTbTitle", "()Landroidx/databinding/ObservableField;", "setTbTitle", "(Landroidx/databinding/ObservableField;)V", "multicurrency_debug"})
    public static abstract interface State extends co.yap.yapcore.IBase.State {
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableField<java.lang.String> getTbTitle();
        
        public abstract void setTbTitle(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableField<java.lang.String> p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableBoolean getLeftIcon();
        
        public abstract void setLeftIcon(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableBoolean p0);
        
        @org.jetbrains.annotations.NotNull()
        public abstract androidx.databinding.ObservableBoolean getRightIcon();
        
        public abstract void setRightIcon(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ObservableBoolean p0);
    }
}