package co.yap.sendMoney.home.viewmodels;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u00032\b\u0012\u0004\u0012\u00020\u00050\u0004B\r\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u000e\u00100\u001a\b\u0012\u0004\u0012\u00020\"02H\u0016J\u0010\u00103\u001a\u0002042\u0006\u00105\u001a\u00020\u001dH\u0016J\b\u00106\u001a\u000204H\u0016J\b\u00107\u001a\u000204H\u0016J\b\u00108\u001a\u000204H\u0016J\u0010\u00109\u001a\u0002042\u0006\u0010:\u001a\u00020\u001dH\u0016J\b\u0010;\u001a\u000204H\u0016R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR \u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\u000fX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0015X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u000fX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0013R \u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u000fX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0013\"\u0004\b\u001f\u0010 R \u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u000fX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u0013\"\u0004\b$\u0010 R&\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\u000fX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u0013\"\u0004\b\'\u0010 R\u0014\u0010(\u001a\u00020\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u001a\u0010+\u001a\b\u0012\u0004\u0012\u00020,0\u000fX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010\u0013R\u0014\u0010.\u001a\u00020/X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u00101\u00a8\u0006<"}, d2 = {"Lco/yap/sendMoney/home/viewmodels/SendMoneyHomeScreenViewModel;", "Lco/yap/sendMoney/viewmodels/SendMoneyBaseViewModel;", "Lco/yap/sendMoney/home/interfaces/ISendMoneyHome$State;", "Lco/yap/sendMoney/home/interfaces/ISendMoneyHome$ViewModel;", "Lco/yap/networking/interfaces/IRepositoryHolder;", "Lco/yap/networking/customers/CustomersRepository;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "adapter", "Landroidx/databinding/ObservableField;", "Lco/yap/sendMoney/home/adapters/RecentTransferAdaptor;", "getAdapter", "()Landroidx/databinding/ObservableField;", "allBeneficiariesLiveData", "Landroidx/lifecycle/MutableLiveData;", "", "Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;", "getAllBeneficiariesLiveData", "()Landroidx/lifecycle/MutableLiveData;", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "setClickEvent", "(Lco/yap/yapcore/SingleClickEvent;)V", "isSearching", "", "onDeleteSuccess", "", "getOnDeleteSuccess", "setOnDeleteSuccess", "(Landroidx/lifecycle/MutableLiveData;)V", "pagingState", "Lco/yap/yapcore/helpers/PagingState;", "getPagingState", "setPagingState", "recentTransferData", "getRecentTransferData", "setRecentTransferData", "repository", "getRepository", "()Lco/yap/networking/customers/CustomersRepository;", "searchQuery", "", "getSearchQuery", "state", "Lco/yap/sendMoney/home/states/SendMoneyHomeState;", "getState", "()Lco/yap/sendMoney/home/states/SendMoneyHomeState;", "Landroidx/lifecycle/LiveData;", "handlePressOnView", "", "id", "onCreate", "onResume", "requestAllBeneficiaries", "requestDeleteBeneficiary", "beneficiaryId", "requestRecentBeneficiaries", "sendmoney_release"})
public final class SendMoneyHomeScreenViewModel extends co.yap.sendMoney.viewmodels.SendMoneyBaseViewModel<co.yap.sendMoney.home.interfaces.ISendMoneyHome.State> implements co.yap.sendMoney.home.interfaces.ISendMoneyHome.ViewModel, co.yap.networking.interfaces.IRepositoryHolder<co.yap.networking.customers.CustomersRepository> {
    @org.jetbrains.annotations.NotNull()
    private final co.yap.networking.customers.CustomersRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.sendMoney.home.states.SendMoneyHomeState state = null;
    @org.jetbrains.annotations.NotNull()
    private co.yap.yapcore.SingleClickEvent clickEvent;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<co.yap.yapcore.helpers.PagingState> pagingState;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.util.List<co.yap.networking.customers.responsedtos.sendmoney.Beneficiary>> allBeneficiariesLiveData = null;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Integer> onDeleteSuccess;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.util.List<co.yap.networking.customers.responsedtos.sendmoney.Beneficiary>> recentTransferData;
    @org.jetbrains.annotations.NotNull()
    private final androidx.databinding.ObservableField<co.yap.sendMoney.home.adapters.RecentTransferAdaptor> adapter = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.String> searchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.Boolean> isSearching = null;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.networking.customers.CustomersRepository getRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.sendMoney.home.states.SendMoneyHomeState getState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.yapcore.SingleClickEvent getClickEvent() {
        return null;
    }
    
    @java.lang.Override()
    public void setClickEvent(@org.jetbrains.annotations.NotNull()
    co.yap.yapcore.SingleClickEvent p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<co.yap.yapcore.helpers.PagingState> getPagingState() {
        return null;
    }
    
    @java.lang.Override()
    public void setPagingState(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<co.yap.yapcore.helpers.PagingState> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.util.List<co.yap.networking.customers.responsedtos.sendmoney.Beneficiary>> getAllBeneficiariesLiveData() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.lang.Integer> getOnDeleteSuccess() {
        return null;
    }
    
    public void setOnDeleteSuccess(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Integer> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.util.List<co.yap.networking.customers.responsedtos.sendmoney.Beneficiary>> getRecentTransferData() {
        return null;
    }
    
    public void setRecentTransferData(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.util.List<co.yap.networking.customers.responsedtos.sendmoney.Beneficiary>> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.databinding.ObservableField<co.yap.sendMoney.home.adapters.RecentTransferAdaptor> getAdapter() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.lang.String> getSearchQuery() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.lang.Boolean> isSearching() {
        return null;
    }
    
    @java.lang.Override()
    public void handlePressOnView(int id) {
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @java.lang.Override()
    public void onResume() {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.LiveData<co.yap.yapcore.helpers.PagingState> getState() {
        return null;
    }
    
    @java.lang.Override()
    public void requestAllBeneficiaries() {
    }
    
    @java.lang.Override()
    public void requestRecentBeneficiaries() {
    }
    
    @java.lang.Override()
    public void requestDeleteBeneficiary(int beneficiaryId) {
    }
    
    public SendMoneyHomeScreenViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
}