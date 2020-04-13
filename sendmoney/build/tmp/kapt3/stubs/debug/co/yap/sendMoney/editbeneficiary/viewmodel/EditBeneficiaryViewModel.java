package co.yap.sendmoney.editbeneficiary.viewmodel;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u00032\b\u0012\u0004\u0012\u00020\u00050\u0004B\r\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010\"\u001a\u00020#H\u0016J\u0010\u0010$\u001a\u00020#2\u0006\u0010%\u001a\u00020&H\u0016J\b\u0010\'\u001a\u00020#H\u0016J\b\u0010(\u001a\u00020#H\u0016J\u0010\u0010)\u001a\u00020#2\u0006\u0010*\u001a\u00020+H\u0016R\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR \u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0012\"\u0004\b\u0013\u0010\u0014R \u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0012\"\u0004\b\u0017\u0010\u0014R \u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0012\"\u0004\b\u001a\u0010\u0014R\u0014\u0010\u001b\u001a\u00020\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001e\u001a\u00020\u001fX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!\u00a8\u0006,"}, d2 = {"Lco/yap/sendmoney/editbeneficiary/viewmodel/EditBeneficiaryViewModel;", "Lco/yap/sendmoney/viewmodels/SendMoneyBaseViewModel;", "Lco/yap/sendmoney/editbeneficiary/interfaces/IEditBeneficiary$State;", "Lco/yap/sendmoney/editbeneficiary/interfaces/IEditBeneficiary$ViewModel;", "Lco/yap/networking/interfaces/IRepositoryHolder;", "Lco/yap/networking/customers/CustomersRepository;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "setClickEvent", "(Lco/yap/yapcore/SingleClickEvent;)V", "isBeneficiaryValid", "Landroidx/lifecycle/MutableLiveData;", "", "()Landroidx/lifecycle/MutableLiveData;", "setBeneficiaryValid", "(Landroidx/lifecycle/MutableLiveData;)V", "onBeneficiaryCreatedSuccess", "getOnBeneficiaryCreatedSuccess", "setOnBeneficiaryCreatedSuccess", "onUpdateSuccess", "getOnUpdateSuccess", "setOnUpdateSuccess", "repository", "getRepository", "()Lco/yap/networking/customers/CustomersRepository;", "state", "Lco/yap/sendmoney/editbeneficiary/states/EditBeneficiaryStates;", "getState", "()Lco/yap/sendmoney/editbeneficiary/states/EditBeneficiaryStates;", "createBeneficiaryRequest", "", "handlePressOnConfirm", "id", "", "requestCountryInfo", "requestUpdateBeneficiary", "validateBeneficiaryDetails", "beneficiary", "Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;", "sendmoney_debug"})
public final class EditBeneficiaryViewModel extends co.yap.sendmoney.viewmodels.SendMoneyBaseViewModel<co.yap.sendmoney.editbeneficiary.interfaces.IEditBeneficiary.State> implements co.yap.sendmoney.editbeneficiary.interfaces.IEditBeneficiary.ViewModel, co.yap.networking.interfaces.IRepositoryHolder<co.yap.networking.customers.CustomersRepository> {
    @org.jetbrains.annotations.NotNull()
    private final co.yap.networking.customers.CustomersRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.sendmoney.editbeneficiary.states.EditBeneficiaryStates state = null;
    @org.jetbrains.annotations.Nullable()
    private co.yap.yapcore.SingleClickEvent clickEvent;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Boolean> onUpdateSuccess;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Boolean> isBeneficiaryValid;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Boolean> onBeneficiaryCreatedSuccess;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.networking.customers.CustomersRepository getRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.sendmoney.editbeneficiary.states.EditBeneficiaryStates getState() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public co.yap.yapcore.SingleClickEvent getClickEvent() {
        return null;
    }
    
    @java.lang.Override()
    public void setClickEvent(@org.jetbrains.annotations.Nullable()
    co.yap.yapcore.SingleClickEvent p0) {
    }
    
    @java.lang.Override()
    public void handlePressOnConfirm(int id) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.lang.Boolean> getOnUpdateSuccess() {
        return null;
    }
    
    @java.lang.Override()
    public void setOnUpdateSuccess(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Boolean> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.lang.Boolean> isBeneficiaryValid() {
        return null;
    }
    
    @java.lang.Override()
    public void setBeneficiaryValid(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Boolean> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.lang.Boolean> getOnBeneficiaryCreatedSuccess() {
        return null;
    }
    
    @java.lang.Override()
    public void setOnBeneficiaryCreatedSuccess(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Boolean> p0) {
    }
    
    @java.lang.Override()
    public void requestUpdateBeneficiary() {
    }
    
    @java.lang.Override()
    public void requestCountryInfo() {
    }
    
    @java.lang.Override()
    public void createBeneficiaryRequest() {
    }
    
    @java.lang.Override()
    public void validateBeneficiaryDetails(@org.jetbrains.annotations.NotNull()
    co.yap.networking.customers.responsedtos.sendmoney.Beneficiary beneficiary) {
    }
    
    public EditBeneficiaryViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
}