package co.yap.sendMoney.home.activities;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u0000 12\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003:\u00011B\u0005\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0002J\b\u0010\u0019\u001a\u00020\u0007H\u0016J\b\u0010\u001a\u001a\u00020\u0007H\u0016J\b\u0010\u001b\u001a\u00020\u001cH\u0002J\b\u0010\u001d\u001a\u00020\u0012H\u0002J\b\u0010\u001e\u001a\u00020\u0012H\u0002J\"\u0010\u001f\u001a\u00020\u00122\u0006\u0010 \u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u00072\b\u0010\"\u001a\u0004\u0018\u00010#H\u0014J\u0012\u0010$\u001a\u00020\u00122\b\u0010%\u001a\u0004\u0018\u00010&H\u0014J\b\u0010\'\u001a\u00020\u0012H\u0014J\b\u0010(\u001a\u00020\u0012H\u0014J\b\u0010)\u001a\u00020\u0012H\u0014J\u0012\u0010*\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0002J\b\u0010+\u001a\u00020\u0012H\u0002J\u0010\u0010,\u001a\u00020\u00122\u0006\u0010-\u001a\u00020.H\u0002J\u001a\u0010/\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u00100\u001a\u00020\u0007H\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010\u00a8\u00062"}, d2 = {"Lco/yap/sendMoney/home/activities/SendMoneyLandingActivity;", "Lco/yap/yapcore/BaseBindingActivity;", "Lco/yap/sendMoney/home/interfaces/ISendMoneyHome$ViewModel;", "Lco/yap/sendMoney/home/interfaces/ISendMoneyHome$View;", "()V", "clickListener", "Landroidx/lifecycle/Observer;", "", "onTouchListener", "Lcom/nikhilpanju/recyclerviewenhanced/RecyclerTouchListener;", "positionToDelete", "recentItemClickListener", "Lco/yap/yapcore/interfaces/OnItemClickListener;", "viewModel", "Lco/yap/sendMoney/home/viewmodels/SendMoneyHomeScreenViewModel;", "getViewModel", "()Lco/yap/sendMoney/home/viewmodels/SendMoneyHomeScreenViewModel;", "confirmDeleteBeneficiary", "", "beneficiary", "Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;", "getAdaptor", "Lco/yap/sendMoney/home/adapters/AllBeneficiariesAdapter;", "getBinding", "Lco/yap/sendmoney/databinding/ActivitySendMoneyLandingBinding;", "getBindingVariable", "getLayoutId", "getSearchView", "Landroidx/appcompat/widget/SearchView;", "initComponents", "initSwipeListener", "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onPause", "onResume", "openEditBeneficiary", "setObservers", "setSearchView", "show", "", "startMoneyTransfer", "position", "Companion", "sendmoney_debug"})
public final class SendMoneyLandingActivity extends co.yap.yapcore.BaseBindingActivity<co.yap.sendMoney.home.interfaces.ISendMoneyHome.ViewModel> implements co.yap.sendMoney.home.interfaces.ISendMoneyHome.View {
    private int positionToDelete;
    private com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener onTouchListener;
    private final co.yap.yapcore.interfaces.OnItemClickListener recentItemClickListener = null;
    private final androidx.lifecycle.Observer<java.lang.Integer> clickListener = null;
    private static final java.lang.String searching = "searching";
    private static boolean performedDeleteOperation;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String data = "payLoad";
    public static final co.yap.sendMoney.home.activities.SendMoneyLandingActivity.Companion Companion = null;
    private java.util.HashMap _$_findViewCache;
    
    @java.lang.Override()
    public int getBindingVariable() {
        return 0;
    }
    
    @java.lang.Override()
    public int getLayoutId() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.sendMoney.home.viewmodels.SendMoneyHomeScreenViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initComponents() {
    }
    
    private final void setObservers() {
    }
    
    private final void setSearchView(boolean show) {
    }
    
    private final void initSwipeListener() {
    }
    
    private final void startMoneyTransfer(co.yap.networking.customers.responsedtos.sendmoney.Beneficiary beneficiary, int position) {
    }
    
    private final void openEditBeneficiary(co.yap.networking.customers.responsedtos.sendmoney.Beneficiary beneficiary) {
    }
    
    private final void confirmDeleteBeneficiary(co.yap.networking.customers.responsedtos.sendmoney.Beneficiary beneficiary) {
    }
    
    @java.lang.Override()
    protected void onPause() {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    private final co.yap.sendMoney.home.adapters.AllBeneficiariesAdapter getAdaptor() {
        return null;
    }
    
    private final androidx.appcompat.widget.SearchView getSearchView() {
        return null;
    }
    
    private final co.yap.sendmoney.databinding.ActivitySendMoneyLandingBinding getBinding() {
        return null;
    }
    
    @java.lang.Override()
    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable()
    android.content.Intent data) {
    }
    
    public SendMoneyLandingActivity() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00062\b\u0010\r\u001a\u0004\u0018\u00010\u000eJ\u000e\u0010\u000f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lco/yap/sendMoney/home/activities/SendMoneyLandingActivity$Companion;", "", "()V", "data", "", "performedDeleteOperation", "", "searching", "getIntent", "Landroid/content/Intent;", "context", "Landroid/content/Context;", "isSearching", "payLoad", "Landroid/os/Parcelable;", "newIntent", "sendmoney_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final android.content.Intent newIntent(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.content.Intent getIntent(@org.jetbrains.annotations.NotNull()
        android.content.Context context, boolean isSearching, @org.jetbrains.annotations.Nullable()
        android.os.Parcelable payLoad) {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}