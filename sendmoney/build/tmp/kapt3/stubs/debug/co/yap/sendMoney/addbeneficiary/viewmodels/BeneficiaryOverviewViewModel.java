package co.yap.sendMoney.addbeneficiary.viewmodels;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u00032\b\u0012\u0004\u0012\u00020\u00050\u0004B\r\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0010\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u001cH\u0016J\u0010\u0010+\u001a\u00020)2\u0006\u0010*\u001a\u00020\u001cH\u0016J\b\u0010,\u001a\u00020)H\u0016J\b\u0010-\u001a\u00020)H\u0016R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0015X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R \u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u0014\u0010!\u001a\u00020\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0014\u0010$\u001a\u00020%X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\'\u00a8\u0006."}, d2 = {"Lco/yap/sendMoney/addbeneficiary/viewmodels/BeneficiaryOverviewViewModel;", "Lco/yap/sendMoney/viewmodels/SendMoneyBaseViewModel;", "Lco/yap/sendMoney/addbeneficiary/interfaces/IBeneficiaryOverview$State;", "Lco/yap/sendMoney/addbeneficiary/interfaces/IBeneficiaryOverview$ViewModel;", "Lco/yap/networking/interfaces/IRepositoryHolder;", "Lco/yap/networking/customers/CustomersRepository;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "backButtonPressEvent", "Lco/yap/yapcore/SingleLiveEvent;", "", "getBackButtonPressEvent", "()Lco/yap/yapcore/SingleLiveEvent;", "beneficiary", "Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;", "getBeneficiary", "()Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;", "setBeneficiary", "(Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;)V", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "setClickEvent", "(Lco/yap/yapcore/SingleClickEvent;)V", "onDeleteSuccess", "Landroidx/lifecycle/MutableLiveData;", "", "getOnDeleteSuccess", "()Landroidx/lifecycle/MutableLiveData;", "setOnDeleteSuccess", "(Landroidx/lifecycle/MutableLiveData;)V", "repository", "getRepository", "()Lco/yap/networking/customers/CustomersRepository;", "state", "Lco/yap/sendMoney/addbeneficiary/states/BeneficiaryOverviewState;", "getState", "()Lco/yap/sendMoney/addbeneficiary/states/BeneficiaryOverviewState;", "handlePressOnAddNow", "", "id", "handlePressOnConfirm", "onResume", "requestUpdateBeneficiary", "sendmoney_debug"})
public final class BeneficiaryOverviewViewModel extends co.yap.sendMoney.viewmodels.SendMoneyBaseViewModel<co.yap.sendMoney.addbeneficiary.interfaces.IBeneficiaryOverview.State> implements co.yap.sendMoney.addbeneficiary.interfaces.IBeneficiaryOverview.ViewModel, co.yap.networking.interfaces.IRepositoryHolder<co.yap.networking.customers.CustomersRepository> {
    @org.jetbrains.annotations.NotNull()
    private final co.yap.networking.customers.CustomersRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.sendMoney.addbeneficiary.states.BeneficiaryOverviewState state = null;
    @org.jetbrains.annotations.NotNull()
    private co.yap.yapcore.SingleClickEvent clickEvent;
    @org.jetbrains.annotations.NotNull()
    private co.yap.networking.customers.responsedtos.sendmoney.Beneficiary beneficiary;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Integer> onDeleteSuccess;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.yapcore.SingleLiveEvent<java.lang.Boolean> backButtonPressEvent = null;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.networking.customers.CustomersRepository getRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.sendMoney.addbeneficiary.states.BeneficiaryOverviewState getState() {
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
    public co.yap.networking.customers.responsedtos.sendmoney.Beneficiary getBeneficiary() {
        return null;
    }
    
    @java.lang.Override()
    public void setBeneficiary(@org.jetbrains.annotations.NotNull()
    co.yap.networking.customers.responsedtos.sendmoney.Beneficiary p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.lang.Integer> getOnDeleteSuccess() {
        return null;
    }
    
    public void setOnDeleteSuccess(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Integer> p0) {
    }
    
    @java.lang.Override()
    public void handlePressOnAddNow(int id) {
    }
    
    @java.lang.Override()
    public void handlePressOnConfirm(int id) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.yapcore.SingleLiveEvent<java.lang.Boolean> getBackButtonPressEvent() {
        return null;
    }
    
    @java.lang.Override()
    public void onResume() {
    }
    
    @java.lang.Override()
    public void requestUpdateBeneficiary() {
    }
    
    public BeneficiaryOverviewViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
}