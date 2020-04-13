package co.yap.sendmoney.helper;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bH\u0007\u00a8\u0006\u000b"}, d2 = {"Lco/yap/sendmoney/helper/SendMoneyBindingHelper;", "", "()V", "setAdaptorParam", "", "recycleview", "Landroidx/recyclerview/widget/RecyclerView;", "list", "Landroidx/databinding/ObservableField;", "", "Lco/yap/networking/customers/responsedtos/beneficiary/BankParams;", "sendmoney_debug"})
public final class SendMoneyBindingHelper {
    public static final co.yap.sendmoney.helper.SendMoneyBindingHelper INSTANCE = null;
    
    @androidx.databinding.BindingAdapter(value = {"adaptorListBankParams"})
    public static final void setAdaptorParam(@org.jetbrains.annotations.NotNull()
    androidx.recyclerview.widget.RecyclerView recycleview, @org.jetbrains.annotations.NotNull()
    androidx.databinding.ObservableField<java.util.List<co.yap.networking.customers.responsedtos.beneficiary.BankParams>> list) {
    }
    
    private SendMoneyBindingHelper() {
        super();
    }
}