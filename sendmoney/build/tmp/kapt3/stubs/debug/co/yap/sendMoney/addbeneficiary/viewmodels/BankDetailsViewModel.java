package co.yap.sendMoney.addbeneficiary.viewmodels;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u00032\b\u0012\u0004\u0012\u00020\u00050\u0004B\r\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0010\u00103\u001a\u0002042\u0006\u00105\u001a\u000206H\u0002J\u0010\u00107\u001a\u0002042\u0006\u00108\u001a\u000209H\u0016J\b\u0010:\u001a\u000204H\u0016J\b\u0010;\u001a\u000204H\u0016J\b\u0010<\u001a\u000204H\u0016J\u0010\u0010=\u001a\u0002042\u0006\u0010>\u001a\u00020?H\u0016R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR&\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00110\u0010X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R&\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190\u00110\u0018X\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001a\u0010\u001e\u001a\u00020\u001fX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001a\u0010$\u001a\u00020%X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\'\"\u0004\b(\u0010)R\u0014\u0010*\u001a\u00020\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0014\u0010-\u001a\u00020.X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u00100R\u000e\u00101\u001a\u000202X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006@"}, d2 = {"Lco/yap/sendMoney/addbeneficiary/viewmodels/BankDetailsViewModel;", "Lco/yap/sendMoney/viewmodels/SendMoneyBaseViewModel;", "Lco/yap/sendMoney/addbeneficiary/interfaces/IBankDetails$State;", "Lco/yap/sendMoney/addbeneficiary/interfaces/IBankDetails$ViewModel;", "Lco/yap/networking/interfaces/IRepositoryHolder;", "Lco/yap/networking/customers/CustomersRepository;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "adaptorBanks", "Lco/yap/sendMoney/addbeneficiary/adaptor/RAKBankAdaptor;", "getAdaptorBanks", "()Lco/yap/sendMoney/addbeneficiary/adaptor/RAKBankAdaptor;", "setAdaptorBanks", "(Lco/yap/sendMoney/addbeneficiary/adaptor/RAKBankAdaptor;)V", "bankList", "Landroidx/lifecycle/MutableLiveData;", "", "Lco/yap/networking/customers/responsedtos/sendmoney/RAKBank$Bank;", "getBankList", "()Landroidx/lifecycle/MutableLiveData;", "setBankList", "(Landroidx/lifecycle/MutableLiveData;)V", "bankParams", "Landroidx/databinding/ObservableField;", "Lco/yap/networking/customers/responsedtos/beneficiary/BankParams;", "getBankParams", "()Landroidx/databinding/ObservableField;", "setBankParams", "(Landroidx/databinding/ObservableField;)V", "clickEvent", "Lco/yap/yapcore/SingleClickEvent;", "getClickEvent", "()Lco/yap/yapcore/SingleClickEvent;", "setClickEvent", "(Lco/yap/yapcore/SingleClickEvent;)V", "paramsAdaptor", "Lco/yap/sendMoney/addbeneficiary/adaptor/AddBeneficiariesAdaptor;", "getParamsAdaptor", "()Lco/yap/sendMoney/addbeneficiary/adaptor/AddBeneficiariesAdaptor;", "setParamsAdaptor", "(Lco/yap/sendMoney/addbeneficiary/adaptor/AddBeneficiariesAdaptor;)V", "repository", "getRepository", "()Lco/yap/networking/customers/CustomersRepository;", "state", "Lco/yap/sendMoney/addbeneficiary/states/BankDetailsState;", "getState", "()Lco/yap/sendMoney/addbeneficiary/states/BankDetailsState;", "watcher", "Landroid/text/TextWatcher;", "getOtherBankParams", "", "countryCode", "", "handlePressOnView", "id", "", "onCreate", "onResume", "retry", "searchRMTBanks", "otherBankQuery", "Lco/yap/networking/customers/requestdtos/OtherBankQuery;", "sendmoney_debug"})
public final class BankDetailsViewModel extends co.yap.sendMoney.viewmodels.SendMoneyBaseViewModel<co.yap.sendMoney.addbeneficiary.interfaces.IBankDetails.State> implements co.yap.sendMoney.addbeneficiary.interfaces.IBankDetails.ViewModel, co.yap.networking.interfaces.IRepositoryHolder<co.yap.networking.customers.CustomersRepository> {
    @org.jetbrains.annotations.NotNull()
    private androidx.databinding.ObservableField<java.util.List<co.yap.networking.customers.responsedtos.beneficiary.BankParams>> bankParams;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.util.List<co.yap.networking.customers.responsedtos.sendmoney.RAKBank.Bank>> bankList;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.networking.customers.CustomersRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final co.yap.sendMoney.addbeneficiary.states.BankDetailsState state = null;
    @org.jetbrains.annotations.NotNull()
    private co.yap.yapcore.SingleClickEvent clickEvent;
    private final android.text.TextWatcher watcher = null;
    @org.jetbrains.annotations.NotNull()
    private co.yap.sendMoney.addbeneficiary.adaptor.RAKBankAdaptor adaptorBanks;
    @org.jetbrains.annotations.NotNull()
    private co.yap.sendMoney.addbeneficiary.adaptor.AddBeneficiariesAdaptor paramsAdaptor;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.databinding.ObservableField<java.util.List<co.yap.networking.customers.responsedtos.beneficiary.BankParams>> getBankParams() {
        return null;
    }
    
    @java.lang.Override()
    public void setBankParams(@org.jetbrains.annotations.NotNull()
    androidx.databinding.ObservableField<java.util.List<co.yap.networking.customers.responsedtos.beneficiary.BankParams>> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.lifecycle.MutableLiveData<java.util.List<co.yap.networking.customers.responsedtos.sendmoney.RAKBank.Bank>> getBankList() {
        return null;
    }
    
    @java.lang.Override()
    public void setBankList(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.util.List<co.yap.networking.customers.responsedtos.sendmoney.RAKBank.Bank>> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.networking.customers.CustomersRepository getRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public co.yap.sendMoney.addbeneficiary.states.BankDetailsState getState() {
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
    public final co.yap.sendMoney.addbeneficiary.adaptor.RAKBankAdaptor getAdaptorBanks() {
        return null;
    }
    
    public final void setAdaptorBanks(@org.jetbrains.annotations.NotNull()
    co.yap.sendMoney.addbeneficiary.adaptor.RAKBankAdaptor p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final co.yap.sendMoney.addbeneficiary.adaptor.AddBeneficiariesAdaptor getParamsAdaptor() {
        return null;
    }
    
    public final void setParamsAdaptor(@org.jetbrains.annotations.NotNull()
    co.yap.sendMoney.addbeneficiary.adaptor.AddBeneficiariesAdaptor p0) {
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @java.lang.Override()
    public void handlePressOnView(int id) {
    }
    
    @java.lang.Override()
    public void onResume() {
    }
    
    @java.lang.Override()
    public void searchRMTBanks(@org.jetbrains.annotations.NotNull()
    co.yap.networking.customers.requestdtos.OtherBankQuery otherBankQuery) {
    }
    
    private final void getOtherBankParams(java.lang.String countryCode) {
    }
    
    @java.lang.Override()
    public void retry() {
    }
    
    public BankDetailsViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
}