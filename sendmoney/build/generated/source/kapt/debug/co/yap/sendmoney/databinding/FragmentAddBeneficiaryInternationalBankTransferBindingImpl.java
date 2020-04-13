package co.yap.sendmoney.databinding;
import co.yap.sendmoney.R;
import co.yap.sendmoney.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentAddBeneficiaryInternationalBankTransferBindingImpl extends FragmentAddBeneficiaryInternationalBankTransferBinding implements co.yap.sendmoney.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    @NonNull
    private final android.widget.TextView mboundView1;
    @NonNull
    private final android.widget.TextView mboundView11;
    @NonNull
    private final android.widget.TextView mboundView13;
    @NonNull
    private final android.widget.TextView mboundView15;
    @NonNull
    private final android.widget.TextView mboundView17;
    @NonNull
    private final android.widget.TextView mboundView2;
    @NonNull
    private final android.widget.TextView mboundView4;
    @NonNull
    private final android.widget.TextView mboundView5;
    @NonNull
    private final android.widget.TextView mboundView6;
    @NonNull
    private final android.widget.LinearLayout mboundView7;
    @NonNull
    private final android.widget.TextView mboundView8;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback23;
    @Nullable
    private final android.view.View.OnClickListener mCallback24;
    // values
    // listeners
    // Inverse Binding Event Handlers
    private androidx.databinding.InverseBindingListener etFirstNameandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.state.firstName
            //         is viewModel.state.setFirstName((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etFirstName);
            // localize variables for thread safety
            // viewModel.state
            co.yap.sendmoney.addbeneficiary.states.AddBeneficiaryStates viewModelState = null;
            // viewModel.state.firstName
            java.lang.String viewModelStateFirstName = null;
            // viewModel
            co.yap.sendmoney.addbeneficiary.viewmodels.AddBeneficiaryViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.state != null
            boolean viewModelStateJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelState = viewModel.getState();

                viewModelStateJavaLangObjectNull = (viewModelState) != (null);
                if (viewModelStateJavaLangObjectNull) {




                    viewModelState.setFirstName(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener etLastNameandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.state.lastName
            //         is viewModel.state.setLastName((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etLastName);
            // localize variables for thread safety
            // viewModel.state
            co.yap.sendmoney.addbeneficiary.states.AddBeneficiaryStates viewModelState = null;
            // viewModel
            co.yap.sendmoney.addbeneficiary.viewmodels.AddBeneficiaryViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.state.lastName
            java.lang.String viewModelStateLastName = null;
            // viewModel.state != null
            boolean viewModelStateJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelState = viewModel.getState();

                viewModelStateJavaLangObjectNull = (viewModelState) != (null);
                if (viewModelStateJavaLangObjectNull) {




                    viewModelState.setLastName(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener etMobileNumberandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.state.mobileNo
            //         is viewModel.state.setMobileNo((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etMobileNumber);
            // localize variables for thread safety
            // viewModel.state
            co.yap.sendmoney.addbeneficiary.states.AddBeneficiaryStates viewModelState = null;
            // viewModel.state.mobileNo
            java.lang.String viewModelStateMobileNo = null;
            // viewModel
            co.yap.sendmoney.addbeneficiary.viewmodels.AddBeneficiaryViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.state != null
            boolean viewModelStateJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelState = viewModel.getState();

                viewModelStateJavaLangObjectNull = (viewModelState) != (null);
                if (viewModelStateJavaLangObjectNull) {




                    viewModelState.setMobileNo(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener etnickNameandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.state.nickName
            //         is viewModel.state.setNickName((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etnickName);
            // localize variables for thread safety
            // viewModel.state
            co.yap.sendmoney.addbeneficiary.states.AddBeneficiaryStates viewModelState = null;
            // viewModel.state.nickName
            java.lang.String viewModelStateNickName = null;
            // viewModel
            co.yap.sendmoney.addbeneficiary.viewmodels.AddBeneficiaryViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.state != null
            boolean viewModelStateJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelState = viewModel.getState();

                viewModelStateJavaLangObjectNull = (viewModelState) != (null);
                if (viewModelStateJavaLangObjectNull) {




                    viewModelState.setNickName(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };

    public FragmentAddBeneficiaryInternationalBankTransferBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 20, sIncludes, sViewsWithIds));
    }
    private FragmentAddBeneficiaryInternationalBankTransferBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (co.yap.widgets.CoreButton) bindings[19]
            , (android.widget.EditText) bindings[14]
            , (android.widget.EditText) bindings[16]
            , (co.yap.widgets.PrefixSuffixEditText) bindings[18]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[12]
            , (co.yap.widgets.CoreCircularImageView) bindings[3]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[10]
            );
        this.confirmButton.setTag(null);
        this.etFirstName.setTag(null);
        this.etLastName.setTag(null);
        this.etMobileNumber.setTag(null);
        this.etnickName.setTag(null);
        this.flagImg.setTag(null);
        this.labelCardType.setTag(null);
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.TextView) bindings[1];
        this.mboundView1.setTag(null);
        this.mboundView11 = (android.widget.TextView) bindings[11];
        this.mboundView11.setTag(null);
        this.mboundView13 = (android.widget.TextView) bindings[13];
        this.mboundView13.setTag(null);
        this.mboundView15 = (android.widget.TextView) bindings[15];
        this.mboundView15.setTag(null);
        this.mboundView17 = (android.widget.TextView) bindings[17];
        this.mboundView17.setTag(null);
        this.mboundView2 = (android.widget.TextView) bindings[2];
        this.mboundView2.setTag(null);
        this.mboundView4 = (android.widget.TextView) bindings[4];
        this.mboundView4.setTag(null);
        this.mboundView5 = (android.widget.TextView) bindings[5];
        this.mboundView5.setTag(null);
        this.mboundView6 = (android.widget.TextView) bindings[6];
        this.mboundView6.setTag(null);
        this.mboundView7 = (android.widget.LinearLayout) bindings[7];
        this.mboundView7.setTag(null);
        this.mboundView8 = (android.widget.TextView) bindings[8];
        this.mboundView8.setTag(null);
        this.tvChangeCurrency.setTag(null);
        setRootTag(root);
        // listeners
        mCallback23 = new co.yap.sendmoney.generated.callback.OnClickListener(this, 1);
        mCallback24 = new co.yap.sendmoney.generated.callback.OnClickListener(this, 2);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x4000L;
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
            setViewModel((co.yap.sendmoney.addbeneficiary.viewmodels.AddBeneficiaryViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable co.yap.sendmoney.addbeneficiary.viewmodels.AddBeneficiaryViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeViewModelState((co.yap.sendmoney.addbeneficiary.states.AddBeneficiaryStates) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelState(co.yap.sendmoney.addbeneficiary.states.AddBeneficiaryStates ViewModelState, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        else if (fieldId == BR.flagDrawableResId) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        else if (fieldId == BR.country) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        else if (fieldId == BR.transferType) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        else if (fieldId == BR.currency) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
            }
            return true;
        }
        else if (fieldId == BR.nickName) {
            synchronized(this) {
                    mDirtyFlags |= 0x40L;
            }
            return true;
        }
        else if (fieldId == BR.firstName) {
            synchronized(this) {
                    mDirtyFlags |= 0x80L;
            }
            return true;
        }
        else if (fieldId == BR.lastName) {
            synchronized(this) {
                    mDirtyFlags |= 0x100L;
            }
            return true;
        }
        else if (fieldId == BR.selectedBeneficiaryType) {
            synchronized(this) {
                    mDirtyFlags |= 0x200L;
            }
            return true;
        }
        else if (fieldId == BR.mobileNo) {
            synchronized(this) {
                    mDirtyFlags |= 0x400L;
            }
            return true;
        }
        else if (fieldId == BR.countryCode) {
            synchronized(this) {
                    mDirtyFlags |= 0x800L;
            }
            return true;
        }
        else if (fieldId == BR.country2DigitIsoCode) {
            synchronized(this) {
                    mDirtyFlags |= 0x1000L;
            }
            return true;
        }
        else if (fieldId == BR.valid) {
            synchronized(this) {
                    mDirtyFlags |= 0x2000L;
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
        java.lang.String viewModelStateCountry2DigitIsoCode = null;
        java.lang.String viewModelStateSelectedBeneficiaryType = null;
        boolean viewModelStateLastNameEmpty = false;
        boolean viewModelStateValid = false;
        boolean viewModelStateMobileNoEmptyBooleanTrueBooleanFalse = false;
        co.yap.sendmoney.addbeneficiary.states.AddBeneficiaryStates viewModelState = null;
        java.lang.String viewModelStateMobileNo = null;
        boolean viewModelStateFirstNameEmpty = false;
        java.lang.String viewModelStateTransferType = null;
        boolean viewModelStateFirstNameEmptyBooleanTrueBooleanFalse = false;
        java.lang.String viewModelStateNickName = null;
        boolean viewModelStateNickNameEmpty = false;
        java.lang.String viewModelStateCountryCode = null;
        java.lang.String viewModelStateSelectedBeneficiaryTypeJavaLangStringCASHPAYOUTStringsScreenPersonalDetailDisplayTextPhoneNumberStringsScreenAddBeneficiaryDetailDisplayTextTransferPhone = null;
        java.lang.String viewModelStateCountryCodeJavaLangObjectNullViewModelStateCountryCodeJavaLangString = null;
        boolean viewModelStateLastNameEmptyBooleanTrueBooleanFalse = false;
        java.lang.String viewModelStateFirstName = null;
        android.graphics.drawable.Drawable viewModelStateTransferTypeEqualsJavaLangStringCashPickupMboundView6AndroidDrawableIcCashMboundView6AndroidDrawableIcBank = null;
        int viewModelStateTransferTypeEqualsJavaLangStringCashPickupViewGONEViewVISIBLE = 0;
        boolean viewModelStateCountry2DigitIsoCodeJavaLangObjectNull = false;
        java.lang.String viewModelStateLastName = null;
        boolean viewModelStateCountryCodeJavaLangObjectNull = false;
        java.lang.String viewModelStateCountry2DigitIsoCodeJavaLangObjectNullViewModelStateCountry2DigitIsoCodeJavaLangString = null;
        java.lang.String viewModelStateCountry = null;
        java.lang.String viewModelStateCurrency = null;
        java.lang.String viewModelStateTransferTypeEqualsJavaLangStringCashPickupStringsScreenAddBeneficiaryDetailButtonConfirmStringsCommonButtonNext = null;
        int viewModelStateFlagDrawableResId = 0;
        boolean viewModelStateTransferTypeEqualsJavaLangStringCashPickup = false;
        boolean viewModelStateSelectedBeneficiaryTypeJavaLangStringCASHPAYOUT = false;
        boolean viewModelStateNickNameEmptyBooleanTrueBooleanFalse = false;
        boolean viewModelStateMobileNoEmpty = false;
        co.yap.sendmoney.addbeneficiary.viewmodels.AddBeneficiaryViewModel viewModel = mViewModel;

        if ((dirtyFlags & 0x7fffL) != 0) {



                if (viewModel != null) {
                    // read viewModel.state
                    viewModelState = viewModel.getState();
                }
                updateRegistration(0, viewModelState);

            if ((dirtyFlags & 0x5803L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.country2DigitIsoCode
                        viewModelStateCountry2DigitIsoCode = viewModelState.getCountry2DigitIsoCode();
                        // read viewModel.state.countryCode
                        viewModelStateCountryCode = viewModelState.getCountryCode();
                    }


                    // read viewModel.state.country2DigitIsoCode != null
                    viewModelStateCountry2DigitIsoCodeJavaLangObjectNull = (viewModelStateCountry2DigitIsoCode) != (null);
                    // read viewModel.state.countryCode != null
                    viewModelStateCountryCodeJavaLangObjectNull = (viewModelStateCountryCode) != (null);
                if((dirtyFlags & 0x5803L) != 0) {
                    if(viewModelStateCountry2DigitIsoCodeJavaLangObjectNull) {
                            dirtyFlags |= 0x40000000L;
                    }
                    else {
                            dirtyFlags |= 0x20000000L;
                    }
                }
                if((dirtyFlags & 0x5803L) != 0) {
                    if(viewModelStateCountryCodeJavaLangObjectNull) {
                            dirtyFlags |= 0x400000L;
                    }
                    else {
                            dirtyFlags |= 0x200000L;
                    }
                }
            }
            if ((dirtyFlags & 0x4203L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.selectedBeneficiaryType
                        viewModelStateSelectedBeneficiaryType = viewModelState.getSelectedBeneficiaryType();
                    }


                    // read viewModel.state.selectedBeneficiaryType == "CASHPAYOUT"
                    viewModelStateSelectedBeneficiaryTypeJavaLangStringCASHPAYOUT = (viewModelStateSelectedBeneficiaryType) == ("CASHPAYOUT");
                if((dirtyFlags & 0x4203L) != 0) {
                    if(viewModelStateSelectedBeneficiaryTypeJavaLangStringCASHPAYOUT) {
                            dirtyFlags |= 0x100000L;
                    }
                    else {
                            dirtyFlags |= 0x80000L;
                    }
                }


                    // read viewModel.state.selectedBeneficiaryType == "CASHPAYOUT" ? Strings.screen_personal_detail_display_text_phone_number : Strings.screen_add_beneficiary_detail_display_text_transfer_phone
                    viewModelStateSelectedBeneficiaryTypeJavaLangStringCASHPAYOUTStringsScreenPersonalDetailDisplayTextPhoneNumberStringsScreenAddBeneficiaryDetailDisplayTextTransferPhone = ((viewModelStateSelectedBeneficiaryTypeJavaLangStringCASHPAYOUT) ? (co.yap.translation.Strings.screen_personal_detail_display_text_phone_number) : (co.yap.translation.Strings.screen_add_beneficiary_detail_display_text_transfer_phone));
            }
            if ((dirtyFlags & 0x6003L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.valid
                        viewModelStateValid = viewModelState.getValid();
                    }
            }
            if ((dirtyFlags & 0x4403L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.mobileNo
                        viewModelStateMobileNo = viewModelState.getMobileNo();
                    }


                    if (viewModelStateMobileNo != null) {
                        // read viewModel.state.mobileNo.empty
                        viewModelStateMobileNoEmpty = viewModelStateMobileNo.isEmpty();
                    }
                if((dirtyFlags & 0x4403L) != 0) {
                    if(viewModelStateMobileNoEmpty) {
                            dirtyFlags |= 0x10000L;
                    }
                    else {
                            dirtyFlags |= 0x8000L;
                    }
                }


                    // read viewModel.state.mobileNo.empty ? true : false
                    viewModelStateMobileNoEmptyBooleanTrueBooleanFalse = ((viewModelStateMobileNoEmpty) ? (true) : (false));
            }
            if ((dirtyFlags & 0x4013L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.transferType
                        viewModelStateTransferType = viewModelState.getTransferType();
                    }


                    if (viewModelStateTransferType != null) {
                        // read viewModel.state.transferType.equals("Cash Pickup")
                        viewModelStateTransferTypeEqualsJavaLangStringCashPickup = viewModelStateTransferType.equals("Cash Pickup");
                    }
                if((dirtyFlags & 0x4013L) != 0) {
                    if(viewModelStateTransferTypeEqualsJavaLangStringCashPickup) {
                            dirtyFlags |= 0x4000000L;
                            dirtyFlags |= 0x10000000L;
                            dirtyFlags |= 0x100000000L;
                    }
                    else {
                            dirtyFlags |= 0x2000000L;
                            dirtyFlags |= 0x8000000L;
                            dirtyFlags |= 0x80000000L;
                    }
                }


                    // read viewModel.state.transferType.equals("Cash Pickup") ? @android:drawable/ic_cash : @android:drawable/ic_bank
                    viewModelStateTransferTypeEqualsJavaLangStringCashPickupMboundView6AndroidDrawableIcCashMboundView6AndroidDrawableIcBank = ((viewModelStateTransferTypeEqualsJavaLangStringCashPickup) ? (getDrawableFromResource(mboundView6, R.drawable.ic_cash)) : (getDrawableFromResource(mboundView6, R.drawable.ic_bank)));
                    // read viewModel.state.transferType.equals("Cash Pickup") ? View.GONE : View.VISIBLE
                    viewModelStateTransferTypeEqualsJavaLangStringCashPickupViewGONEViewVISIBLE = ((viewModelStateTransferTypeEqualsJavaLangStringCashPickup) ? (android.view.View.GONE) : (android.view.View.VISIBLE));
                    // read viewModel.state.transferType.equals("Cash Pickup") ? Strings.screen_add_beneficiary_detail_button_confirm : Strings.common_button_next
                    viewModelStateTransferTypeEqualsJavaLangStringCashPickupStringsScreenAddBeneficiaryDetailButtonConfirmStringsCommonButtonNext = ((viewModelStateTransferTypeEqualsJavaLangStringCashPickup) ? (co.yap.translation.Strings.screen_add_beneficiary_detail_button_confirm) : (co.yap.translation.Strings.common_button_next));
            }
            if ((dirtyFlags & 0x4043L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.nickName
                        viewModelStateNickName = viewModelState.getNickName();
                    }


                    if (viewModelStateNickName != null) {
                        // read viewModel.state.nickName.empty
                        viewModelStateNickNameEmpty = viewModelStateNickName.isEmpty();
                    }
                if((dirtyFlags & 0x4043L) != 0) {
                    if(viewModelStateNickNameEmpty) {
                            dirtyFlags |= 0x400000000L;
                    }
                    else {
                            dirtyFlags |= 0x200000000L;
                    }
                }


                    // read viewModel.state.nickName.empty ? true : false
                    viewModelStateNickNameEmptyBooleanTrueBooleanFalse = ((viewModelStateNickNameEmpty) ? (true) : (false));
            }
            if ((dirtyFlags & 0x4083L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.firstName
                        viewModelStateFirstName = viewModelState.getFirstName();
                    }


                    if (viewModelStateFirstName != null) {
                        // read viewModel.state.firstName.empty
                        viewModelStateFirstNameEmpty = viewModelStateFirstName.isEmpty();
                    }
                if((dirtyFlags & 0x4083L) != 0) {
                    if(viewModelStateFirstNameEmpty) {
                            dirtyFlags |= 0x40000L;
                    }
                    else {
                            dirtyFlags |= 0x20000L;
                    }
                }


                    // read viewModel.state.firstName.empty ? true : false
                    viewModelStateFirstNameEmptyBooleanTrueBooleanFalse = ((viewModelStateFirstNameEmpty) ? (true) : (false));
            }
            if ((dirtyFlags & 0x4103L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.lastName
                        viewModelStateLastName = viewModelState.getLastName();
                    }


                    if (viewModelStateLastName != null) {
                        // read viewModel.state.lastName.empty
                        viewModelStateLastNameEmpty = viewModelStateLastName.isEmpty();
                    }
                if((dirtyFlags & 0x4103L) != 0) {
                    if(viewModelStateLastNameEmpty) {
                            dirtyFlags |= 0x1000000L;
                    }
                    else {
                            dirtyFlags |= 0x800000L;
                    }
                }


                    // read viewModel.state.lastName.empty ? true : false
                    viewModelStateLastNameEmptyBooleanTrueBooleanFalse = ((viewModelStateLastNameEmpty) ? (true) : (false));
            }
            if ((dirtyFlags & 0x400bL) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.country
                        viewModelStateCountry = viewModelState.getCountry();
                    }
            }
            if ((dirtyFlags & 0x4023L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.currency
                        viewModelStateCurrency = viewModelState.getCurrency();
                    }
            }
            if ((dirtyFlags & 0x4007L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.flagDrawableResId
                        viewModelStateFlagDrawableResId = viewModelState.getFlagDrawableResId();
                    }
            }
        }
        // batch finished

        if ((dirtyFlags & 0x5803L) != 0) {

                // read viewModel.state.countryCode != null ? viewModel.state.countryCode : " "
                viewModelStateCountryCodeJavaLangObjectNullViewModelStateCountryCodeJavaLangString = ((viewModelStateCountryCodeJavaLangObjectNull) ? (viewModelStateCountryCode) : (" "));
                // read viewModel.state.country2DigitIsoCode != null ? viewModel.state.country2DigitIsoCode : " "
                viewModelStateCountry2DigitIsoCodeJavaLangObjectNullViewModelStateCountry2DigitIsoCodeJavaLangString = ((viewModelStateCountry2DigitIsoCodeJavaLangObjectNull) ? (viewModelStateCountry2DigitIsoCode) : (" "));
        }
        // batch finished
        if ((dirtyFlags & 0x4000L) != 0) {
            // api target 1

            this.confirmButton.setOnClickListener(mCallback24);
            co.yap.yapcore.binders.UIBinder.setHint(this.etFirstName, co.yap.translation.Strings.screen_add_beneficiary_detail_input_text_first_name_hint);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etFirstName, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etFirstNameandroidTextAttrChanged);
            co.yap.yapcore.binders.UIBinder.setHint(this.etLastName, co.yap.translation.Strings.screen_add_beneficiary_detail_input_text_last_name_hint);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etLastName, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etLastNameandroidTextAttrChanged);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etMobileNumber, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etMobileNumberandroidTextAttrChanged);
            co.yap.yapcore.binders.UIBinder.setHint(this.etnickName, co.yap.translation.Strings.screen_add_beneficiary_detail_input_text_nick_name_hint);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etnickName, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etnickNameandroidTextAttrChanged);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView1, co.yap.translation.Strings.screen_add_beneficiary_detail_display_text_title);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView11, co.yap.translation.Strings.screen_add_beneficiary_detail_display_text_transfer_nick_name);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView13, co.yap.translation.Strings.screen_add_beneficiary_detail_display_text_transfer_first_name);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView15, co.yap.translation.Strings.screen_add_beneficiary_detail_display_text_transfer_last_name);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView2, co.yap.translation.Strings.screen_add_beneficiary_detail_display_text_country);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView5, co.yap.translation.Strings.screen_add_beneficiary_detail_display_text_transfer_type);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView8, co.yap.translation.Strings.screen_add_beneficiary_detail_display_text_currency);
            this.tvChangeCurrency.setOnClickListener(mCallback23);
            co.yap.yapcore.binders.UIBinder.setText(this.tvChangeCurrency, co.yap.translation.Strings.screen_add_beneficiary_detail_display_text_change_currency);
        }
        if ((dirtyFlags & 0x6003L) != 0) {
            // api target 1

            co.yap.yapcore.binders.UIBinder.setEnable(this.confirmButton, viewModelStateValid);
        }
        if ((dirtyFlags & 0x4013L) != 0) {
            // api target 1

            co.yap.yapcore.binders.UIBinder.setText(this.confirmButton, viewModelStateTransferTypeEqualsJavaLangStringCashPickupStringsScreenAddBeneficiaryDetailButtonConfirmStringsCommonButtonNext);
            androidx.databinding.adapters.TextViewBindingAdapter.setDrawableStart(this.mboundView6, viewModelStateTransferTypeEqualsJavaLangStringCashPickupMboundView6AndroidDrawableIcCashMboundView6AndroidDrawableIcBank);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView6, viewModelStateTransferType);
            this.mboundView7.setVisibility(viewModelStateTransferTypeEqualsJavaLangStringCashPickupViewGONEViewVISIBLE);
        }
        if ((dirtyFlags & 0x4083L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etFirstName, viewModelStateFirstName);
            co.yap.yapcore.binders.UIBinder.setSelectedTextColor(this.mboundView13, viewModelStateFirstNameEmptyBooleanTrueBooleanFalse);
        }
        if ((dirtyFlags & 0x4103L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etLastName, viewModelStateLastName);
            co.yap.yapcore.binders.UIBinder.setSelectedTextColor(this.mboundView15, viewModelStateLastNameEmptyBooleanTrueBooleanFalse);
        }
        if ((dirtyFlags & 0x4403L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etMobileNumber, viewModelStateMobileNo);
            co.yap.yapcore.binders.UIBinder.setSelectedTextColor(this.mboundView17, viewModelStateMobileNoEmptyBooleanTrueBooleanFalse);
        }
        if ((dirtyFlags & 0x5803L) != 0) {
            // api target 1

            co.yap.yapcore.helpers.ImageBinding.setPhonePrefix(this.etMobileNumber, viewModelStateCountryCodeJavaLangObjectNullViewModelStateCountryCodeJavaLangString, viewModelStateCountry2DigitIsoCodeJavaLangObjectNullViewModelStateCountry2DigitIsoCodeJavaLangString);
        }
        if ((dirtyFlags & 0x4043L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etnickName, viewModelStateNickName);
            co.yap.yapcore.binders.UIBinder.setSelectedTextColor(this.mboundView11, viewModelStateNickNameEmptyBooleanTrueBooleanFalse);
        }
        if ((dirtyFlags & 0x4007L) != 0) {
            // api target 1

            co.yap.yapcore.binders.UIBinder.setImageResId(this.flagImg, viewModelStateFlagDrawableResId);
        }
        if ((dirtyFlags & 0x4023L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.labelCardType, viewModelStateCurrency);
        }
        if ((dirtyFlags & 0x4203L) != 0) {
            // api target 1

            co.yap.yapcore.binders.UIBinder.setText(this.mboundView17, viewModelStateSelectedBeneficiaryTypeJavaLangStringCASHPAYOUTStringsScreenPersonalDetailDisplayTextPhoneNumberStringsScreenAddBeneficiaryDetailDisplayTextTransferPhone);
        }
        if ((dirtyFlags & 0x400bL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView4, viewModelStateCountry);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 1: {
                // localize variables for thread safety
                // viewModel
                co.yap.sendmoney.addbeneficiary.viewmodels.AddBeneficiaryViewModel viewModel = mViewModel;
                // viewModel != null
                boolean viewModelJavaLangObjectNull = false;
                // v.id
                int callbackArg0Id = 0;



                viewModelJavaLangObjectNull = (viewModel) != (null);
                if (viewModelJavaLangObjectNull) {


                    if ((callbackArg_0) != (null)) {


                        callbackArg0Id = callbackArg_0.getId();

                        viewModel.handlePressOnAddNow(callbackArg0Id);
                    }
                }
                break;
            }
            case 2: {
                // localize variables for thread safety
                // viewModel
                co.yap.sendmoney.addbeneficiary.viewmodels.AddBeneficiaryViewModel viewModel = mViewModel;
                // viewModel != null
                boolean viewModelJavaLangObjectNull = false;
                // v.id
                int callbackArg0Id = 0;



                viewModelJavaLangObjectNull = (viewModel) != (null);
                if (viewModelJavaLangObjectNull) {


                    if ((callbackArg_0) != (null)) {


                        callbackArg0Id = callbackArg_0.getId();

                        viewModel.handlePressOnAddNow(callbackArg0Id);
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
        flag 1 (0x2L): viewModel
        flag 2 (0x3L): viewModel.state.flagDrawableResId
        flag 3 (0x4L): viewModel.state.country
        flag 4 (0x5L): viewModel.state.transferType
        flag 5 (0x6L): viewModel.state.currency
        flag 6 (0x7L): viewModel.state.nickName
        flag 7 (0x8L): viewModel.state.firstName
        flag 8 (0x9L): viewModel.state.lastName
        flag 9 (0xaL): viewModel.state.selectedBeneficiaryType
        flag 10 (0xbL): viewModel.state.mobileNo
        flag 11 (0xcL): viewModel.state.countryCode
        flag 12 (0xdL): viewModel.state.country2DigitIsoCode
        flag 13 (0xeL): viewModel.state.valid
        flag 14 (0xfL): null
        flag 15 (0x10L): viewModel.state.mobileNo.empty ? true : false
        flag 16 (0x11L): viewModel.state.mobileNo.empty ? true : false
        flag 17 (0x12L): viewModel.state.firstName.empty ? true : false
        flag 18 (0x13L): viewModel.state.firstName.empty ? true : false
        flag 19 (0x14L): viewModel.state.selectedBeneficiaryType == "CASHPAYOUT" ? Strings.screen_personal_detail_display_text_phone_number : Strings.screen_add_beneficiary_detail_display_text_transfer_phone
        flag 20 (0x15L): viewModel.state.selectedBeneficiaryType == "CASHPAYOUT" ? Strings.screen_personal_detail_display_text_phone_number : Strings.screen_add_beneficiary_detail_display_text_transfer_phone
        flag 21 (0x16L): viewModel.state.countryCode != null ? viewModel.state.countryCode : " "
        flag 22 (0x17L): viewModel.state.countryCode != null ? viewModel.state.countryCode : " "
        flag 23 (0x18L): viewModel.state.lastName.empty ? true : false
        flag 24 (0x19L): viewModel.state.lastName.empty ? true : false
        flag 25 (0x1aL): viewModel.state.transferType.equals("Cash Pickup") ? @android:drawable/ic_cash : @android:drawable/ic_bank
        flag 26 (0x1bL): viewModel.state.transferType.equals("Cash Pickup") ? @android:drawable/ic_cash : @android:drawable/ic_bank
        flag 27 (0x1cL): viewModel.state.transferType.equals("Cash Pickup") ? View.GONE : View.VISIBLE
        flag 28 (0x1dL): viewModel.state.transferType.equals("Cash Pickup") ? View.GONE : View.VISIBLE
        flag 29 (0x1eL): viewModel.state.country2DigitIsoCode != null ? viewModel.state.country2DigitIsoCode : " "
        flag 30 (0x1fL): viewModel.state.country2DigitIsoCode != null ? viewModel.state.country2DigitIsoCode : " "
        flag 31 (0x20L): viewModel.state.transferType.equals("Cash Pickup") ? Strings.screen_add_beneficiary_detail_button_confirm : Strings.common_button_next
        flag 32 (0x21L): viewModel.state.transferType.equals("Cash Pickup") ? Strings.screen_add_beneficiary_detail_button_confirm : Strings.common_button_next
        flag 33 (0x22L): viewModel.state.nickName.empty ? true : false
        flag 34 (0x23L): viewModel.state.nickName.empty ? true : false
    flag mapping end*/
    //end
}