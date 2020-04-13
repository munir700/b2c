package co.yap.sendmoney.databinding;
import co.yap.sendmoney.R;
import co.yap.sendmoney.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentBeneficiaryAccountDetailBindingImpl extends FragmentBeneficiaryAccountDetailBinding implements co.yap.sendmoney.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.llMain, 12);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    @NonNull
    private final android.widget.TextView mboundView1;
    @NonNull
    private final android.widget.TextView mboundView9;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback27;
    @Nullable
    private final android.view.View.OnClickListener mCallback26;
    // values
    // listeners
    // Inverse Binding Event Handlers
    private androidx.databinding.InverseBindingListener etIbanandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.state.accountIban
            //         is viewModel.state.setAccountIban((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etIban);
            // localize variables for thread safety
            // viewModel.state
            co.yap.sendmoney.addbeneficiary.states.BeneficiaryAccountDetailsState viewModelState = null;
            // viewModel.state.accountIban
            java.lang.String viewModelStateAccountIban = null;
            // viewModel
            co.yap.sendmoney.addbeneficiary.viewmodels.BeneficiaryAccountDetailsViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.state != null
            boolean viewModelStateJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelState = viewModel.getState();

                viewModelStateJavaLangObjectNull = (viewModelState) != (null);
                if (viewModelStateJavaLangObjectNull) {




                    viewModelState.setAccountIban(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };

    public FragmentBeneficiaryAccountDetailBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds));
    }
    private FragmentBeneficiaryAccountDetailBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 3
            , (co.yap.widgets.CoreButton) bindings[11]
            , (co.yap.widgets.DrawableClickEditText) bindings[10]
            , (co.yap.widgets.CoreCircularImageView) bindings[3]
            , (android.widget.LinearLayout) bindings[2]
            , (android.widget.LinearLayout) bindings[12]
            , (android.widget.LinearLayout) bindings[8]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[5]
            );
        this.confirmButton.setTag(null);
        this.etIban.setTag(null);
        this.imgProfile.setTag(null);
        this.llBankDetail.setTag(null);
        this.lyIban.setTag(null);
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.TextView) bindings[1];
        this.mboundView1.setTag(null);
        this.mboundView9 = (android.widget.TextView) bindings[9];
        this.mboundView9.setTag(null);
        this.tvBankAddress.setTag(null);
        this.tvBankName.setTag(null);
        this.tvBankPhoneNumber.setTag(null);
        this.tvIdCode.setTag(null);
        setRootTag(root);
        // listeners
        mCallback27 = new co.yap.sendmoney.generated.callback.OnClickListener(this, 2);
        mCallback26 = new co.yap.sendmoney.generated.callback.OnClickListener(this, 1);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x400L;
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
            setViewModel((co.yap.sendmoney.addbeneficiary.viewmodels.BeneficiaryAccountDetailsViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable co.yap.sendmoney.addbeneficiary.viewmodels.BeneficiaryAccountDetailsViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x8L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeViewModelState((co.yap.sendmoney.addbeneficiary.states.BeneficiaryAccountDetailsState) object, fieldId);
            case 1 :
                return onChangeViewModelStateShowlyIban((androidx.databinding.ObservableField<java.lang.Boolean>) object, fieldId);
            case 2 :
                return onChangeViewModelParentViewModelSelectedCountry((androidx.lifecycle.MutableLiveData<co.yap.countryutils.country.Country>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelState(co.yap.sendmoney.addbeneficiary.states.BeneficiaryAccountDetailsState ViewModelState, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        else if (fieldId == BR.bankName) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        else if (fieldId == BR.idCode) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
            }
            return true;
        }
        else if (fieldId == BR.bankAddress) {
            synchronized(this) {
                    mDirtyFlags |= 0x40L;
            }
            return true;
        }
        else if (fieldId == BR.bankPhoneNumber) {
            synchronized(this) {
                    mDirtyFlags |= 0x80L;
            }
            return true;
        }
        else if (fieldId == BR.accountIban) {
            synchronized(this) {
                    mDirtyFlags |= 0x100L;
            }
            return true;
        }
        else if (fieldId == BR.valid) {
            synchronized(this) {
                    mDirtyFlags |= 0x200L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateShowlyIban(androidx.databinding.ObservableField<java.lang.Boolean> ViewModelStateShowlyIban, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelParentViewModelSelectedCountry(androidx.lifecycle.MutableLiveData<co.yap.countryutils.country.Country> ViewModelParentViewModelSelectedCountry, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
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
        int viewModelStateBankPhoneNumberEmptyViewGONEViewVISIBLE = 0;
        java.lang.String viewModelStateBankPhoneNumber = null;
        java.lang.String viewModelStateAccountIban = null;
        boolean viewModelStateValid = false;
        co.yap.sendmoney.addbeneficiary.states.BeneficiaryAccountDetailsState viewModelState = null;
        co.yap.sendmoney.interfaces.ISendMoney.ViewModel viewModelParentViewModel = null;
        java.lang.Boolean viewModelStateShowlyIbanGet = null;
        int viewModelStateBankAddressEmptyViewGONEViewVISIBLE = 0;
        boolean androidxDatabindingViewDataBindingSafeUnboxViewModelParentViewModelSelectedCountryIbanMandatory = false;
        androidx.databinding.ObservableField<java.lang.Boolean> viewModelStateShowlyIban = null;
        boolean androidxDatabindingViewDataBindingSafeUnboxViewModelStateShowlyIbanGet = false;
        boolean viewModelStateAccountIbanEmpty = false;
        boolean viewModelStateAccountIbanEmptyBooleanTrueBooleanFalse = false;
        int viewModelStateIdCodeEmptyViewGONEViewVISIBLE = 0;
        int viewModelStateBankNameEmptyViewGONEViewVISIBLE = 0;
        boolean viewModelStateBankPhoneNumberEmpty = false;
        java.lang.Boolean viewModelParentViewModelSelectedCountryIbanMandatory = null;
        int viewModelStateShowlyIbanViewVISIBLEViewGONE = 0;
        co.yap.countryutils.country.Country viewModelParentViewModelSelectedCountryGetValue = null;
        boolean viewModelStateBankAddressEmpty = false;
        androidx.lifecycle.MutableLiveData<co.yap.countryutils.country.Country> viewModelParentViewModelSelectedCountry = null;
        java.lang.String viewModelStateBankName = null;
        java.lang.String viewModelStateIdCode = null;
        boolean viewModelStateBankNameEmpty = false;
        java.lang.String viewModelStateBankAddress = null;
        java.lang.String viewModelParentViewModelSelectedCountryIbanMandatoryStringsScreenBeneficiaryAccountDetailsDisplayTextIbanStringsScreenBeneficiaryAccountDetailsDisplayTextAccountNumber = null;
        co.yap.sendmoney.addbeneficiary.viewmodels.BeneficiaryAccountDetailsViewModel viewModel = mViewModel;
        boolean viewModelStateIdCodeEmpty = false;

        if ((dirtyFlags & 0x7ffL) != 0) {


            if ((dirtyFlags & 0x7fbL) != 0) {

                    if (viewModel != null) {
                        // read viewModel.state
                        viewModelState = viewModel.getState();
                    }
                    updateRegistration(0, viewModelState);

                if ((dirtyFlags & 0x489L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.bankPhoneNumber
                            viewModelStateBankPhoneNumber = viewModelState.getBankPhoneNumber();
                        }


                        if (viewModelStateBankPhoneNumber != null) {
                            // read viewModel.state.bankPhoneNumber.empty
                            viewModelStateBankPhoneNumberEmpty = viewModelStateBankPhoneNumber.isEmpty();
                        }
                    if((dirtyFlags & 0x489L) != 0) {
                        if(viewModelStateBankPhoneNumberEmpty) {
                                dirtyFlags |= 0x1000L;
                        }
                        else {
                                dirtyFlags |= 0x800L;
                        }
                    }


                        // read viewModel.state.bankPhoneNumber.empty ? View.GONE : View.VISIBLE
                        viewModelStateBankPhoneNumberEmptyViewGONEViewVISIBLE = ((viewModelStateBankPhoneNumberEmpty) ? (android.view.View.GONE) : (android.view.View.VISIBLE));
                }
                if ((dirtyFlags & 0x509L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.accountIban
                            viewModelStateAccountIban = viewModelState.getAccountIban();
                        }


                        if (viewModelStateAccountIban != null) {
                            // read viewModel.state.accountIban.empty
                            viewModelStateAccountIbanEmpty = viewModelStateAccountIban.isEmpty();
                        }
                    if((dirtyFlags & 0x509L) != 0) {
                        if(viewModelStateAccountIbanEmpty) {
                                dirtyFlags |= 0x10000L;
                        }
                        else {
                                dirtyFlags |= 0x8000L;
                        }
                    }


                        // read viewModel.state.accountIban.empty ? true : false
                        viewModelStateAccountIbanEmptyBooleanTrueBooleanFalse = ((viewModelStateAccountIbanEmpty) ? (true) : (false));
                }
                if ((dirtyFlags & 0x609L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.valid
                            viewModelStateValid = viewModelState.getValid();
                        }
                }
                if ((dirtyFlags & 0x40bL) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.showlyIban
                            viewModelStateShowlyIban = viewModelState.getShowlyIban();
                        }
                        updateRegistration(1, viewModelStateShowlyIban);


                        if (viewModelStateShowlyIban != null) {
                            // read viewModel.state.showlyIban.get()
                            viewModelStateShowlyIbanGet = viewModelStateShowlyIban.get();
                        }


                        // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.showlyIban.get())
                        androidxDatabindingViewDataBindingSafeUnboxViewModelStateShowlyIbanGet = androidx.databinding.ViewDataBinding.safeUnbox(viewModelStateShowlyIbanGet);
                    if((dirtyFlags & 0x40bL) != 0) {
                        if(androidxDatabindingViewDataBindingSafeUnboxViewModelStateShowlyIbanGet) {
                                dirtyFlags |= 0x400000L;
                        }
                        else {
                                dirtyFlags |= 0x200000L;
                        }
                    }


                        // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.showlyIban.get()) ? View.VISIBLE : View.GONE
                        viewModelStateShowlyIbanViewVISIBLEViewGONE = ((androidxDatabindingViewDataBindingSafeUnboxViewModelStateShowlyIbanGet) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
                }
                if ((dirtyFlags & 0x419L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.bankName
                            viewModelStateBankName = viewModelState.getBankName();
                        }


                        if (viewModelStateBankName != null) {
                            // read viewModel.state.bankName.empty
                            viewModelStateBankNameEmpty = viewModelStateBankName.isEmpty();
                        }
                    if((dirtyFlags & 0x419L) != 0) {
                        if(viewModelStateBankNameEmpty) {
                                dirtyFlags |= 0x100000L;
                        }
                        else {
                                dirtyFlags |= 0x80000L;
                        }
                    }


                        // read viewModel.state.bankName.empty ? View.GONE : View.VISIBLE
                        viewModelStateBankNameEmptyViewGONEViewVISIBLE = ((viewModelStateBankNameEmpty) ? (android.view.View.GONE) : (android.view.View.VISIBLE));
                }
                if ((dirtyFlags & 0x429L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.idCode
                            viewModelStateIdCode = viewModelState.getIdCode();
                        }


                        if (viewModelStateIdCode != null) {
                            // read viewModel.state.idCode.empty
                            viewModelStateIdCodeEmpty = viewModelStateIdCode.isEmpty();
                        }
                    if((dirtyFlags & 0x429L) != 0) {
                        if(viewModelStateIdCodeEmpty) {
                                dirtyFlags |= 0x40000L;
                        }
                        else {
                                dirtyFlags |= 0x20000L;
                        }
                    }


                        // read viewModel.state.idCode.empty ? View.GONE : View.VISIBLE
                        viewModelStateIdCodeEmptyViewGONEViewVISIBLE = ((viewModelStateIdCodeEmpty) ? (android.view.View.GONE) : (android.view.View.VISIBLE));
                }
                if ((dirtyFlags & 0x449L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.bankAddress
                            viewModelStateBankAddress = viewModelState.getBankAddress();
                        }


                        if (viewModelStateBankAddress != null) {
                            // read viewModel.state.bankAddress.empty
                            viewModelStateBankAddressEmpty = viewModelStateBankAddress.isEmpty();
                        }
                    if((dirtyFlags & 0x449L) != 0) {
                        if(viewModelStateBankAddressEmpty) {
                                dirtyFlags |= 0x4000L;
                        }
                        else {
                                dirtyFlags |= 0x2000L;
                        }
                    }


                        // read viewModel.state.bankAddress.empty ? View.GONE : View.VISIBLE
                        viewModelStateBankAddressEmptyViewGONEViewVISIBLE = ((viewModelStateBankAddressEmpty) ? (android.view.View.GONE) : (android.view.View.VISIBLE));
                }
            }
            if ((dirtyFlags & 0x40cL) != 0) {

                    if (viewModel != null) {
                        // read viewModel.parentViewModel
                        viewModelParentViewModel = viewModel.getParentViewModel();
                    }


                    if (viewModelParentViewModel != null) {
                        // read viewModel.parentViewModel.selectedCountry
                        viewModelParentViewModelSelectedCountry = viewModelParentViewModel.getSelectedCountry();
                    }
                    updateLiveDataRegistration(2, viewModelParentViewModelSelectedCountry);


                    if (viewModelParentViewModelSelectedCountry != null) {
                        // read viewModel.parentViewModel.selectedCountry.getValue()
                        viewModelParentViewModelSelectedCountryGetValue = viewModelParentViewModelSelectedCountry.getValue();
                    }


                    if (viewModelParentViewModelSelectedCountryGetValue != null) {
                        // read viewModel.parentViewModel.selectedCountry.getValue().ibanMandatory
                        viewModelParentViewModelSelectedCountryIbanMandatory = viewModelParentViewModelSelectedCountryGetValue.getIbanMandatory();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.parentViewModel.selectedCountry.getValue().ibanMandatory)
                    androidxDatabindingViewDataBindingSafeUnboxViewModelParentViewModelSelectedCountryIbanMandatory = androidx.databinding.ViewDataBinding.safeUnbox(viewModelParentViewModelSelectedCountryIbanMandatory);
                if((dirtyFlags & 0x40cL) != 0) {
                    if(androidxDatabindingViewDataBindingSafeUnboxViewModelParentViewModelSelectedCountryIbanMandatory) {
                            dirtyFlags |= 0x1000000L;
                    }
                    else {
                            dirtyFlags |= 0x800000L;
                    }
                }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.parentViewModel.selectedCountry.getValue().ibanMandatory) ? Strings.screen_beneficiary_account_details_display_text_iban : Strings.screen_beneficiary_account_details_display_text_account_number
                    viewModelParentViewModelSelectedCountryIbanMandatoryStringsScreenBeneficiaryAccountDetailsDisplayTextIbanStringsScreenBeneficiaryAccountDetailsDisplayTextAccountNumber = ((androidxDatabindingViewDataBindingSafeUnboxViewModelParentViewModelSelectedCountryIbanMandatory) ? (co.yap.translation.Strings.screen_beneficiary_account_details_display_text_iban) : (co.yap.translation.Strings.screen_beneficiary_account_details_display_text_account_number));
            }
        }
        // batch finished
        if ((dirtyFlags & 0x400L) != 0) {
            // api target 1

            this.confirmButton.setOnClickListener(mCallback27);
            co.yap.yapcore.binders.UIBinder.setText(this.confirmButton, co.yap.translation.Strings.common_button_next);
            co.yap.yapcore.binders.UIBinder.maskIbanNo(this.etIban, "#### #### #### #### #### #### ####");
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etIban, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etIbanandroidTextAttrChanged);
            this.llBankDetail.setOnClickListener(mCallback26);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView1, co.yap.translation.Strings.screen_beneficiary_account_details_display_text_title);
        }
        if ((dirtyFlags & 0x609L) != 0) {
            // api target 1

            co.yap.yapcore.binders.UIBinder.setEnable(this.confirmButton, viewModelStateValid);
        }
        if ((dirtyFlags & 0x509L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etIban, viewModelStateAccountIban);
            co.yap.yapcore.binders.UIBinder.setSelectedTextColor(this.mboundView9, viewModelStateAccountIbanEmptyBooleanTrueBooleanFalse);
        }
        if ((dirtyFlags & 0x419L) != 0) {
            // api target 1

            co.yap.yapcore.helpers.ImageBinding.loadAvatar(this.imgProfile, "", viewModelStateBankName);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvBankName, viewModelStateBankName);
            this.tvBankName.setVisibility(viewModelStateBankNameEmptyViewGONEViewVISIBLE);
        }
        if ((dirtyFlags & 0x40bL) != 0) {
            // api target 1

            this.lyIban.setVisibility(viewModelStateShowlyIbanViewVISIBLEViewGONE);
        }
        if ((dirtyFlags & 0x40cL) != 0) {
            // api target 1

            co.yap.yapcore.binders.UIBinder.setText(this.mboundView9, viewModelParentViewModelSelectedCountryIbanMandatoryStringsScreenBeneficiaryAccountDetailsDisplayTextIbanStringsScreenBeneficiaryAccountDetailsDisplayTextAccountNumber);
        }
        if ((dirtyFlags & 0x449L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvBankAddress, viewModelStateBankAddress);
            this.tvBankAddress.setVisibility(viewModelStateBankAddressEmptyViewGONEViewVISIBLE);
        }
        if ((dirtyFlags & 0x489L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvBankPhoneNumber, viewModelStateBankPhoneNumber);
            this.tvBankPhoneNumber.setVisibility(viewModelStateBankPhoneNumberEmptyViewGONEViewVISIBLE);
        }
        if ((dirtyFlags & 0x429L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvIdCode, viewModelStateIdCode);
            this.tvIdCode.setVisibility(viewModelStateIdCodeEmptyViewGONEViewVISIBLE);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 2: {
                // localize variables for thread safety
                // viewModel
                co.yap.sendmoney.addbeneficiary.viewmodels.BeneficiaryAccountDetailsViewModel viewModel = mViewModel;
                // viewModel != null
                boolean viewModelJavaLangObjectNull = false;
                // v.id
                int callbackArg0Id = 0;



                viewModelJavaLangObjectNull = (viewModel) != (null);
                if (viewModelJavaLangObjectNull) {


                    if ((callbackArg_0) != (null)) {


                        callbackArg0Id = callbackArg_0.getId();

                        viewModel.handlePressOnAddBank(callbackArg0Id);
                    }
                }
                break;
            }
            case 1: {
                // localize variables for thread safety
                // viewModel
                co.yap.sendmoney.addbeneficiary.viewmodels.BeneficiaryAccountDetailsViewModel viewModel = mViewModel;
                // viewModel != null
                boolean viewModelJavaLangObjectNull = false;
                // v.id
                int callbackArg0Id = 0;



                viewModelJavaLangObjectNull = (viewModel) != (null);
                if (viewModelJavaLangObjectNull) {


                    if ((callbackArg_0) != (null)) {


                        callbackArg0Id = callbackArg_0.getId();

                        viewModel.handlePressOnAddBank(callbackArg0Id);
                    }
                }
                break;
            }
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel.state
        flag 1 (0x2L): viewModel.state.showlyIban
        flag 2 (0x3L): viewModel.parentViewModel.selectedCountry
        flag 3 (0x4L): viewModel
        flag 4 (0x5L): viewModel.state.bankName
        flag 5 (0x6L): viewModel.state.idCode
        flag 6 (0x7L): viewModel.state.bankAddress
        flag 7 (0x8L): viewModel.state.bankPhoneNumber
        flag 8 (0x9L): viewModel.state.accountIban
        flag 9 (0xaL): viewModel.state.valid
        flag 10 (0xbL): null
        flag 11 (0xcL): viewModel.state.bankPhoneNumber.empty ? View.GONE : View.VISIBLE
        flag 12 (0xdL): viewModel.state.bankPhoneNumber.empty ? View.GONE : View.VISIBLE
        flag 13 (0xeL): viewModel.state.bankAddress.empty ? View.GONE : View.VISIBLE
        flag 14 (0xfL): viewModel.state.bankAddress.empty ? View.GONE : View.VISIBLE
        flag 15 (0x10L): viewModel.state.accountIban.empty ? true : false
        flag 16 (0x11L): viewModel.state.accountIban.empty ? true : false
        flag 17 (0x12L): viewModel.state.idCode.empty ? View.GONE : View.VISIBLE
        flag 18 (0x13L): viewModel.state.idCode.empty ? View.GONE : View.VISIBLE
        flag 19 (0x14L): viewModel.state.bankName.empty ? View.GONE : View.VISIBLE
        flag 20 (0x15L): viewModel.state.bankName.empty ? View.GONE : View.VISIBLE
        flag 21 (0x16L): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.showlyIban.get()) ? View.VISIBLE : View.GONE
        flag 22 (0x17L): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.showlyIban.get()) ? View.VISIBLE : View.GONE
        flag 23 (0x18L): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.parentViewModel.selectedCountry.getValue().ibanMandatory) ? Strings.screen_beneficiary_account_details_display_text_iban : Strings.screen_beneficiary_account_details_display_text_account_number
        flag 24 (0x19L): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.parentViewModel.selectedCountry.getValue().ibanMandatory) ? Strings.screen_beneficiary_account_details_display_text_iban : Strings.screen_beneficiary_account_details_display_text_account_number
    flag mapping end*/
    //end
}