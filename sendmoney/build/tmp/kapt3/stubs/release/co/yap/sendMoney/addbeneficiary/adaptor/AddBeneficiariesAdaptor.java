package co.yap.sendMoney.addbeneficiary.adaptor;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u001b\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0014J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\nH\u0016J\u0010\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u0012H\u0014R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lco/yap/sendMoney/addbeneficiary/adaptor/AddBeneficiariesAdaptor;", "Lco/yap/yapcore/BaseBindingRecyclerAdapter;", "Lco/yap/networking/customers/responsedtos/beneficiary/BankParams;", "Lco/yap/sendmoney/addbeneficiary/adaptor/BankParamItemViewHolder;", "list", "", "textWatcher", "Landroid/text/TextWatcher;", "(Ljava/util/List;Landroid/text/TextWatcher;)V", "getLayoutIdForViewType", "", "viewType", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "binding", "Landroidx/databinding/ViewDataBinding;", "sendmoney_release"})
public final class AddBeneficiariesAdaptor extends co.yap.yapcore.BaseBindingRecyclerAdapter<co.yap.networking.customers.responsedtos.beneficiary.BankParams, co.yap.sendmoney.addbeneficiary.adaptor.BankParamItemViewHolder> {
    private final java.util.List<co.yap.networking.customers.responsedtos.beneficiary.BankParams> list = null;
    private final android.text.TextWatcher textWatcher = null;
    
    @java.lang.Override()
    protected int getLayoutIdForViewType(int viewType) {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    protected co.yap.sendmoney.addbeneficiary.adaptor.BankParamItemViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    androidx.databinding.ViewDataBinding binding) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    co.yap.sendmoney.addbeneficiary.adaptor.BankParamItemViewHolder holder, int position) {
    }
    
    public AddBeneficiariesAdaptor(@org.jetbrains.annotations.NotNull()
    java.util.List<co.yap.networking.customers.responsedtos.beneficiary.BankParams> list, @org.jetbrains.annotations.NotNull()
    android.text.TextWatcher textWatcher) {
        super(null);
    }
}