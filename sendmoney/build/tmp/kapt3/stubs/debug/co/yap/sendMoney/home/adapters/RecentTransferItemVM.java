package co.yap.sendMoney.home.adapters;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\r\u001a\u00020\u0002H\u0016J\b\u0010\u000e\u001a\u00020\bH\u0016J\u001c\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0016J \u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\bH\u0016J\u0018\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0004\u001a\u00020\u0002X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f\u00a8\u0006\u001b"}, d2 = {"Lco/yap/sendMoney/home/adapters/RecentTransferItemVM;", "Lco/yap/yapcore/BaseListItemViewModel;", "Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;", "()V", "mItem", "navigation", "Landroidx/navigation/NavController;", "position", "", "getPosition", "()I", "setPosition", "(I)V", "getItem", "layoutRes", "onFirsTimeUiCreate", "", "bundle", "Landroid/os/Bundle;", "onItemClick", "view", "Landroid/view/View;", "data", "", "pos", "setItem", "item", "sendmoney_debug"})
public final class RecentTransferItemVM extends co.yap.yapcore.BaseListItemViewModel<co.yap.networking.customers.responsedtos.sendmoney.Beneficiary> {
    private co.yap.networking.customers.responsedtos.sendmoney.Beneficiary mItem;
    private androidx.navigation.NavController navigation;
    private int position;
    
    public final int getPosition() {
        return 0;
    }
    
    public final void setPosition(int p0) {
    }
    
    @java.lang.Override()
    public void setItem(@org.jetbrains.annotations.NotNull()
    co.yap.networking.customers.responsedtos.sendmoney.Beneficiary item, int position) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.networking.customers.responsedtos.sendmoney.Beneficiary getItem() {
        return null;
    }
    
    @java.lang.Override()
    public void onFirsTimeUiCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle bundle, @org.jetbrains.annotations.Nullable()
    androidx.navigation.NavController navigation) {
    }
    
    @java.lang.Override()
    public int layoutRes() {
        return 0;
    }
    
    @java.lang.Override()
    public void onItemClick(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.NotNull()
    java.lang.Object data, int pos) {
    }
    
    public RecentTransferItemVM() {
        super();
    }
}