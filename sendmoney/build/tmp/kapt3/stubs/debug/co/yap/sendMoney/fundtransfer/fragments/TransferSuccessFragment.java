package co.yap.sendMoney.fundtransfer.fragments;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u00032\u00020\u0004B\u0005\u00a2\u0006\u0002\u0010\u0005J\u001a\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0002J\b\u0010\u0014\u001a\u00020\u0011H\u0016J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0010\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0010\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\b\u0010\u001b\u001a\u00020\u000bH\u0016J\u0018\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u001d\u001a\u00020\u00112\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\b\u0010 \u001a\u00020\u0016H\u0016J\b\u0010!\u001a\u00020\u0016H\u0016J\u001a\u0010\"\u001a\u00020\u00162\u0006\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010&H\u0016J\b\u0010\'\u001a\u00020\u0016H\u0002J\b\u0010(\u001a\u00020\u0016H\u0002J\b\u0010)\u001a\u00020\u0016H\u0002J\b\u0010*\u001a\u00020\u0016H\u0002J\b\u0010+\u001a\u00020\u0016H\u0002J\b\u0010,\u001a\u00020\u0016H\u0002J\b\u0010-\u001a\u00020\u0016H\u0016J\b\u0010.\u001a\u00020\u0016H\u0002R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\t\u00a8\u0006/"}, d2 = {"Lco/yap/sendMoney/fundtransfer/fragments/TransferSuccessFragment;", "Lco/yap/sendMoney/fundtransfer/fragments/BeneficiaryFundTransferBaseFragment;", "Lco/yap/sendMoney/fundtransfer/interfaces/ITransferSuccess$ViewModel;", "Lco/yap/sendMoney/fundtransfer/interfaces/ITransferSuccess$View;", "Lco/yap/sendMoney/InviteBottomSheet$OnItemClickListener;", "()V", "viewModel", "Lco/yap/sendMoney/fundtransfer/viewmodels/TransferSuccessViewModel;", "getViewModel", "()Lco/yap/sendMoney/fundtransfer/viewmodels/TransferSuccessViewModel;", "canHandleIntent", "", "intent", "Landroid/content/Intent;", "activity", "Landroid/app/Activity;", "getBindingVariable", "", "getBindings", "Lco/yap/sendmoney/databinding/FragmentTransferSuccessBinding;", "getLayoutId", "inviteViaEmail", "", "referenceNumber", "", "inviteViaSms", "inviteViaWhatsapp", "onBackPressed", "onClick", "viewId", "data", "", "onPause", "onResume", "onViewCreated", "view", "Landroid/view/View;", "savedInstanceState", "Landroid/os/Bundle;", "setData", "setDataForCashPayout", "setDataForDomestic", "setDataForRmt", "setDataForSwift", "setDataForUAEFTS", "setObservers", "setResultData", "sendmoney_debug"})
public final class TransferSuccessFragment extends co.yap.sendMoney.fundtransfer.fragments.BeneficiaryFundTransferBaseFragment<co.yap.sendMoney.fundtransfer.interfaces.ITransferSuccess.ViewModel> implements co.yap.sendMoney.fundtransfer.interfaces.ITransferSuccess.View, co.yap.sendMoney.InviteBottomSheet.OnItemClickListener {
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
    public co.yap.sendMoney.fundtransfer.viewmodels.TransferSuccessViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    public void setObservers() {
    }
    
    private final void setResultData() {
    }
    
    @java.lang.Override()
    public void onResume() {
    }
    
    @java.lang.Override()
    public void onPause() {
    }
    
    @java.lang.Override()
    public boolean onBackPressed() {
        return false;
    }
    
    private final void setData() {
    }
    
    private final void setDataForCashPayout() {
    }
    
    private final void setDataForDomestic() {
    }
    
    private final void setDataForUAEFTS() {
    }
    
    private final void setDataForSwift() {
    }
    
    private final void setDataForRmt() {
    }
    
    @java.lang.Override()
    public void onClick(int viewId, @org.jetbrains.annotations.NotNull()
    java.lang.Object data) {
    }
    
    private final void inviteViaWhatsapp(java.lang.String referenceNumber) {
    }
    
    private final void inviteViaEmail(java.lang.String referenceNumber) {
    }
    
    private final void inviteViaSms(java.lang.String referenceNumber) {
    }
    
    private final boolean canHandleIntent(android.content.Intent intent, android.app.Activity activity) {
        return false;
    }
    
    private final co.yap.sendmoney.databinding.FragmentTransferSuccessBinding getBindings() {
        return null;
    }
    
    public TransferSuccessFragment() {
        super();
    }
}