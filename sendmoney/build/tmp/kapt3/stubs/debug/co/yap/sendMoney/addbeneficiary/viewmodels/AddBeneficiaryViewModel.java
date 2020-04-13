package co.yap.sendmoney.addbeneficiary.viewmodels;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u00032\b\u0012\u0004\u0012\u00020\u00050\u0004B\r\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010%\u001a\u00020&H\u0016J\u0012\u0010\'\u001a\u00020&2\b\u0010(\u001a\u0004\u0018\u00010\u0011H\u0016J\u0010\u0010)\u001a\u00020&2\u0006\u0010*\u001a\u00020+H\u0016J\u0010\u0010,\u001a\u00020&2\u0006\u0010-\u001a\u00020.H\u0016J\u0010\u0010/\u001a\u00020&2\u0006\u0010-\u001a\u00020.H\u0016J\u0010\u00100\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\b\u00101\u001a\u00020&H\u0016J\b\u00102\u001a\u00020&H\u0016J\b\u00103\u001a\u00020&H\u0002J\u0018\u00104\u001a\u00020&2\u0006\u00105\u001a\u00020\u00112\u0006\u00106\u001a\u00020+H\u0016R \u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0017X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\rR\u0014\u0010\u001e\u001a\u00020\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0014\u0010!\u001a\u00020\"X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$\u00a8\u00067"}, d2 = {"Lco/yap/sendmoney/addbeneficiary/viewmodels/AddBeneficiaryViewModel;", "Lco/yap/sendmoney/viewmodels/SendMoneyBaseViewModel;", "Lco/yap/sendmoney/addbeneficiary/interfaces/IAddBeneficiary$State;", "Lco/yap/sendmoney/addbeneficiary/interfaces/IAddBeneficiary$ViewModel;", "Lco/yap/networking/interfaces/IRepositoryHolder;", "Lco/yap/networking/customers/CustomersRepository;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "addBeneficiarySuccess", "Landroidx/lifecycle/MutableLiveData;", "", "getAddBeneficiarySuccess", "()Landroidx/lifecycle/MutableLiveData;", "setAddBeneficiarySuccess", "(Landroidx/lifecycle/MutableLiveData;)V", "beneficiary", "Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;", "getBeneficiary", "()Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;", "setBeneficiary", "(Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;)V", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "setClickEvent", "(Lco/yap/yapcore/SingleClickEvent;)V", "otpCreateObserver", "getOtpCreateObserver", "repository", "getRepository", "()Lco/yap/networking/customers/CustomersRepository;", "state", "Lco/yap/sendmoney/addbeneficiary/states/AddBeneficiaryStates;", "getState", "()Lco/yap/sendmoney/addbeneficiary/states/AddBeneficiaryStates;", "addCashPickupBeneficiary", "", "addDomesticBeneficiary", "objBeneficiary", "createOtp", "action", "", "handlePressOnAddDomestic", "id", "", "handlePressOnAddNow", "isLoggedinUserIBAN", "onCreate", "onResume", "setBeneficiaryDetail", "validateBeneficiaryDetails", "beneficiaryy", "otpType", "sendmoney_debug"})
public final class AddBeneficiaryViewModel extends co.yap.sendmoney.viewmodels.SendMoneyBaseViewModel<co.yap.sendmoney.addbeneficiary.interfaces.IAddBeneficiary.State> implements co.yap.sendmoney.addbeneficiary.interfaces.IAddBeneficiary.ViewModel, co.yap.networking.interfaces.IRepositoryHolder<co.yap.networking.customers.CustomersRepository> {
    @org.jetbrains.annotations.NotNull()
    private final co.yap.networking.customers.CustomersRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.sendmoney.addbeneficiary.states.AddBeneficiaryStates state = null;
    @org.jetbrains.annotations.NotNull()
    private co.yap.yapcore.SingleClickEvent clickEvent;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Boolean> addBeneficiarySuccess;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.Boolean> otpCreateObserver = null;
    @org.jetbrains.annotations.Nullable()
    private co.yap.networking.customers.responsedtos.sendmoney.Beneficiary beneficiary;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.networking.customers.CustomersRepository getRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.sendmoney.addbeneficiary.states.AddBeneficiaryStates getState() {
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
    public androidx.lifecycle.MutableLiveData<java.lang.Boolean> getAddBeneficiarySuccess() {
        return null;
    }
    
    @java.lang.Override()
    public void setAddBeneficiarySuccess(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Boolean> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.lang.Boolean> getOtpCreateObserver() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public co.yap.networking.customers.responsedtos.sendmoney.Beneficiary getBeneficiary() {
        return null;
    }
    
    @java.lang.Override()
    public void setBeneficiary(@org.jetbrains.annotations.Nullable()
    co.yap.networking.customers.responsedtos.sendmoney.Beneficiary p0) {
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @java.lang.Override()
    public void handlePressOnAddDomestic(int id) {
    }
    
    @java.lang.Override()
    public void handlePressOnAddNow(int id) {
    }
    
    private final boolean isLoggedinUserIBAN(co.yap.networking.customers.responsedtos.sendmoney.Beneficiary beneficiary) {
        return false;
    }
    
    @java.lang.Override()
    public void createOtp(@org.jetbrains.annotations.NotNull()
    java.lang.String action) {
    }
    
    @java.lang.Override()
    public void validateBeneficiaryDetails(@org.jetbrains.annotations.NotNull()
    co.yap.networking.customers.responsedtos.sendmoney.Beneficiary beneficiaryy, @org.jetbrains.annotations.NotNull()
    java.lang.String otpType) {
    }
    
    private final void setBeneficiaryDetail() {
    }
    
    @java.lang.Override()
    public void addCashPickupBeneficiary() {
    }
    
    @java.lang.Override()
    public void addDomesticBeneficiary(@org.jetbrains.annotations.Nullable()
    co.yap.networking.customers.responsedtos.sendmoney.Beneficiary objBeneficiary) {
    }
    
    @java.lang.Override()
    public void onResume() {
    }
    
    public AddBeneficiaryViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
}