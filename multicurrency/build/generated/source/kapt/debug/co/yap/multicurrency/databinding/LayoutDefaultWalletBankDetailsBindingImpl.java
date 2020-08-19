package co.yap.multicurrency.databinding;
import co.yap.multicurrency.R;
import co.yap.multicurrency.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutDefaultWalletBankDetailsBindingImpl extends LayoutDefaultWalletBankDetailsBinding implements co.yap.multicurrency.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.linearLayout5, 15);
        sViewsWithIds.put(R.id.linearLayout6, 16);
        sViewsWithIds.put(R.id.linearLayout3, 17);
        sViewsWithIds.put(R.id.linearLayout7, 18);
        sViewsWithIds.put(R.id.linearLayout8, 19);
        sViewsWithIds.put(R.id.linearLayout9, 20);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback1;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutDefaultWalletBankDetailsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 21, sIncludes, sViewsWithIds));
    }
    private LayoutDefaultWalletBankDetailsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2
            , (android.widget.LinearLayout) bindings[17]
            , (android.widget.LinearLayout) bindings[15]
            , (android.widget.LinearLayout) bindings[16]
            , (android.widget.LinearLayout) bindings[18]
            , (android.widget.LinearLayout) bindings[19]
            , (android.widget.LinearLayout) bindings[20]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[9]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[10]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[13]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[14]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[11]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[12]
            , (android.widget.TextView) bindings[1]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[7]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[8]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[3]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[4]
            , (android.widget.TextView) bindings[2]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[5]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[6]
            );
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvAccount.setTag(null);
        this.tvAccountValue.setTag(null);
        this.tvAddress.setTag(null);
        this.tvAddressValue.setTag(null);
        this.tvBank.setTag(null);
        this.tvBankValue.setTag(null);
        this.tvDetailsTitle.setTag(null);
        this.tvIban.setTag(null);
        this.tvIbanValue.setTag(null);
        this.tvName.setTag(null);
        this.tvNameValue.setTag(null);
        this.tvShare.setTag(null);
        this.tvSwift.setTag(null);
        this.tvSwiftValue.setTag(null);
        setRootTag(root);
        // listeners
        mCallback1 = new co.yap.multicurrency.generated.callback.OnClickListener(this, 1);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x8L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.viewModel == variableId) {
            setViewModel((co.yap.multicurrency.walletcountry.WalletDetailsViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable co.yap.multicurrency.walletcountry.WalletDetailsViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeViewModelState((co.yap.multicurrency.walletcountry.WalletDetailsState) object, fieldId);
            case 1 :
                return onChangeViewModelStateBankDetails((androidx.lifecycle.MutableLiveData<co.yap.multicurrency.walletcountry.DefaultWalletBankDetails>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelState(co.yap.multicurrency.walletcountry.WalletDetailsState ViewModelState, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateBankDetails(androidx.lifecycle.MutableLiveData<co.yap.multicurrency.walletcountry.DefaultWalletBankDetails> ViewModelStateBankDetails, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        java.lang.String viewModelStateBankDetailsName = null;
        java.lang.String viewModelStateBankDetailsSwiftCode = null;
        co.yap.multicurrency.walletcountry.WalletDetailsState viewModelState = null;
        java.lang.String viewModelStateBankDetailsIBan = null;
        java.lang.String viewModelStateBankDetailsBankAddress = null;
        java.lang.String viewModelStateBankDetailsBankName = null;
        androidx.lifecycle.MutableLiveData<co.yap.multicurrency.walletcountry.DefaultWalletBankDetails> viewModelStateBankDetails = null;
        co.yap.multicurrency.walletcountry.DefaultWalletBankDetails viewModelStateBankDetailsGetValue = null;
        co.yap.multicurrency.walletcountry.WalletDetailsViewModel viewModel = mViewModel;
        java.lang.String viewModelStateBankDetailsAccountNo = null;

        if ((dirtyFlags & 0xfL) != 0) {



                if (viewModel != null) {
                    // read viewModel.state
                    viewModelState = viewModel.getState();
                }
                updateRegistration(0, viewModelState);


                if (viewModelState != null) {
                    // read viewModel.state.bankDetails
                    viewModelStateBankDetails = viewModelState.getBankDetails();
                }
                updateLiveDataRegistration(1, viewModelStateBankDetails);


                if (viewModelStateBankDetails != null) {
                    // read viewModel.state.bankDetails.getValue()
                    viewModelStateBankDetailsGetValue = viewModelStateBankDetails.getValue();
                }


                if (viewModelStateBankDetailsGetValue != null) {
                    // read viewModel.state.bankDetails.getValue().name
                    viewModelStateBankDetailsName = viewModelStateBankDetailsGetValue.getName();
                    // read viewModel.state.bankDetails.getValue().swiftCode
                    viewModelStateBankDetailsSwiftCode = viewModelStateBankDetailsGetValue.getSwiftCode();
                    // read viewModel.state.bankDetails.getValue().iBan
                    viewModelStateBankDetailsIBan = viewModelStateBankDetailsGetValue.getIBan();
                    // read viewModel.state.bankDetails.getValue().bankAddress
                    viewModelStateBankDetailsBankAddress = viewModelStateBankDetailsGetValue.getBankAddress();
                    // read viewModel.state.bankDetails.getValue().bankName
                    viewModelStateBankDetailsBankName = viewModelStateBankDetailsGetValue.getBankName();
                    // read viewModel.state.bankDetails.getValue().accountNo
                    viewModelStateBankDetailsAccountNo = viewModelStateBankDetailsGetValue.getAccountNo();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x8L) != 0) {
            // api target 1

            co.yap.yapcore.binders.UIBinder.setText(this.tvAccount, co.yap.translation.Strings.screen_multi_currency_wallet_details_bank_details_text_account);
            co.yap.yapcore.binders.UIBinder.setText(this.tvAddress, co.yap.translation.Strings.screen_multi_currency_wallet_details_bank_details_text_address);
            co.yap.yapcore.binders.UIBinder.setText(this.tvBank, co.yap.translation.Strings.screen_multi_currency_wallet_details_bank_details_text_bank);
            co.yap.yapcore.binders.UIBinder.setText(this.tvDetailsTitle, co.yap.translation.Strings.screen_multi_currency_wallet_details_bank_details_text_title);
            co.yap.yapcore.binders.UIBinder.setText(this.tvIban, co.yap.translation.Strings.screen_multi_currency_wallet_details_bank_details_text_iban);
            co.yap.yapcore.binders.UIBinder.setText(this.tvName, co.yap.translation.Strings.screen_multi_currency_wallet_details_bank_details_text_name);
            this.tvShare.setOnClickListener(mCallback1);
            co.yap.yapcore.binders.UIBinder.setText(this.tvShare, co.yap.translation.Strings.screen_multi_currency_wallet_details_bank_details_text_share);
            co.yap.yapcore.binders.UIBinder.setText(this.tvSwift, co.yap.translation.Strings.screen_multi_currency_wallet_details_bank_details_text_swift);
        }
        if ((dirtyFlags & 0xfL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvAccountValue, viewModelStateBankDetailsAccountNo);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvAddressValue, viewModelStateBankDetailsBankAddress);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvBankValue, viewModelStateBankDetailsBankName);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvIbanValue, viewModelStateBankDetailsIBan);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvNameValue, viewModelStateBankDetailsName);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvSwiftValue, viewModelStateBankDetailsSwiftCode);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // viewModel
        co.yap.multicurrency.walletcountry.WalletDetailsViewModel viewModel = mViewModel;
        // viewModel != null
        boolean viewModelJavaLangObjectNull = false;
        // v.id
        int callbackArg0Id = 0;



        viewModelJavaLangObjectNull = (viewModel) != (null);
        if (viewModelJavaLangObjectNull) {


            if ((callbackArg_0) != (null)) {


                callbackArg0Id = callbackArg_0.getId();

                viewModel.handlePressOnView(callbackArg0Id);
            }
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel.state
        flag 1 (0x2L): viewModel.state.bankDetails
        flag 2 (0x3L): viewModel
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}