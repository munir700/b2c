package co.yap.sendMoney.home.adapters;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B!\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0002\u0010\bJ\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0011"}, d2 = {"Lco/yap/sendMoney/home/adapters/BeneficiaryItemViewModel;", "", "beneficiary", "Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;", "position", "", "onItemClickListener", "Lco/yap/yapcore/interfaces/OnItemClickListener;", "(Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;ILco/yap/yapcore/interfaces/OnItemClickListener;)V", "getBeneficiary", "()Lco/yap/networking/customers/responsedtos/sendmoney/Beneficiary;", "getPosition", "()I", "onViewClicked", "", "view", "Landroid/view/View;", "sendmoney_debug"})
public final class BeneficiaryItemViewModel {
    @org.jetbrains.annotations.Nullable()
    private final co.yap.networking.customers.responsedtos.sendmoney.Beneficiary beneficiary = null;
    private final int position = 0;
    private final co.yap.yapcore.interfaces.OnItemClickListener onItemClickListener = null;
    
    public final void onViewClicked(@org.jetbrains.annotations.NotNull()
    android.view.View view) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final co.yap.networking.customers.responsedtos.sendmoney.Beneficiary getBeneficiary() {
        return null;
    }
    
    public final int getPosition() {
        return 0;
    }
    
    public BeneficiaryItemViewModel(@org.jetbrains.annotations.Nullable()
    co.yap.networking.customers.responsedtos.sendmoney.Beneficiary beneficiary, int position, @org.jetbrains.annotations.Nullable()
    co.yap.yapcore.interfaces.OnItemClickListener onItemClickListener) {
        super();
    }
}