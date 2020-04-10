package co.yap.sendmoney.databinding;
import co.yap.sendmoney.R;
import co.yap.sendmoney.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentBeneficiaryOverviewBindingImpl extends FragmentBeneficiaryOverviewBinding implements co.yap.sendmoney.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.viewPhoneNumber, 26);
        sViewsWithIds.put(R.id.ccpContainer, 27);
        sViewsWithIds.put(R.id.ccpSelector, 28);
        sViewsWithIds.put(R.id.tvBankName, 29);
        sViewsWithIds.put(R.id.tvIdCode, 30);
        sViewsWithIds.put(R.id.tvBankAddress, 31);
        sViewsWithIds.put(R.id.tvBankPhoneNumber, 32);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    @NonNull
    private final android.widget.TextView mboundView1;
    @NonNull
    private final android.widget.TextView mboundView10;
    @NonNull
    private final android.widget.TextView mboundView12;
    @NonNull
    private final android.widget.TextView mboundView14;
    @NonNull
    private final android.widget.TextView mboundView16;
    @NonNull
    private final android.widget.TextView mboundView19;
    @NonNull
    private final android.widget.TextView mboundView2;
    @NonNull
    private final android.widget.TextView mboundView21;
    @NonNull
    private final android.widget.TextView mboundView23;
    @NonNull
    private final android.widget.TextView mboundView4;
    @NonNull
    private final android.widget.TextView mboundView5;
    @NonNull
    private final android.widget.TextView mboundView6;
    @NonNull
    private final android.widget.TextView mboundView7;
    @NonNull
    private final android.widget.TextView mboundView9;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback21;
    @Nullable
    private final android.view.View.OnClickListener mCallback22;
    // values
    // listeners
    // Inverse Binding Event Handlers
    private androidx.databinding.InverseBindingListener etAccountIbanNumberandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.state.accountIban
            //         is viewModel.state.setAccountIban((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etAccountIbanNumber);
            // localize variables for thread safety
            // viewModel.state
            co.yap.sendMoney.addbeneficiary.states.BeneficiaryOverviewState viewModelState = null;
            // viewModel.state.accountIban
            java.lang.String viewModelStateAccountIban = null;
            // viewModel
            co.yap.sendMoney.addbeneficiary.viewmodels.BeneficiaryOverviewViewModel viewModel = mViewModel;
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
    private androidx.databinding.InverseBindingListener etBankREquiredFieldCodeandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.state.countryBankRequirementFieldCode
            //         is viewModel.state.setCountryBankRequirementFieldCode((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etBankREquiredFieldCode);
            // localize variables for thread safety
            // viewModel.state
            co.yap.sendMoney.addbeneficiary.states.BeneficiaryOverviewState viewModelState = null;
            // viewModel.state.countryBankRequirementFieldCode
            java.lang.String viewModelStateCountryBankRequirementFieldCode = null;
            // viewModel
            co.yap.sendMoney.addbeneficiary.viewmodels.BeneficiaryOverviewViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.state != null
            boolean viewModelStateJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelState = viewModel.getState();

                viewModelStateJavaLangObjectNull = (viewModelState) != (null);
                if (viewModelStateJavaLangObjectNull) {




                    viewModelState.setCountryBankRequirementFieldCode(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener etFirstNameandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.state.firstName
            //         is viewModel.state.setFirstName((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etFirstName);
            // localize variables for thread safety
            // viewModel.state
            co.yap.sendMoney.addbeneficiary.states.BeneficiaryOverviewState viewModelState = null;
            // viewModel.state.firstName
            java.lang.String viewModelStateFirstName = null;
            // viewModel
            co.yap.sendMoney.addbeneficiary.viewmodels.BeneficiaryOverviewViewModel viewModel = mViewModel;
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
            co.yap.sendMoney.addbeneficiary.states.BeneficiaryOverviewState viewModelState = null;
            // viewModel
            co.yap.sendMoney.addbeneficiary.viewmodels.BeneficiaryOverviewViewModel viewModel = mViewModel;
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
            // Inverse of viewModel.state.mobile
            //         is viewModel.state.setMobile((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etMobileNumber);
            // localize variables for thread safety
            // viewModel.state
            co.yap.sendMoney.addbeneficiary.states.BeneficiaryOverviewState viewModelState = null;
            // viewModel.state.mobile
            java.lang.String viewModelStateMobile = null;
            // viewModel
            co.yap.sendMoney.addbeneficiary.viewmodels.BeneficiaryOverviewViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.state != null
            boolean viewModelStateJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelState = viewModel.getState();

                viewModelStateJavaLangObjectNull = (viewModelState) != (null);
                if (viewModelStateJavaLangObjectNull) {




                    viewModelState.setMobile(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener etSwiftCodeandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.state.swiftCode
            //         is viewModel.state.setSwiftCode((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etSwiftCode);
            // localize variables for thread safety
            // viewModel.state
            co.yap.sendMoney.addbeneficiary.states.BeneficiaryOverviewState viewModelState = null;
            // viewModel.state.swiftCode
            java.lang.String viewModelStateSwiftCode = null;
            // viewModel
            co.yap.sendMoney.addbeneficiary.viewmodels.BeneficiaryOverviewViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.state != null
            boolean viewModelStateJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelState = viewModel.getState();

                viewModelStateJavaLangObjectNull = (viewModelState) != (null);
                if (viewModelStateJavaLangObjectNull) {




                    viewModelState.setSwiftCode(((java.lang.String) (callbackArg_0)));
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
            co.yap.sendMoney.addbeneficiary.states.BeneficiaryOverviewState viewModelState = null;
            // viewModel.state.nickName
            java.lang.String viewModelStateNickName = null;
            // viewModel
            co.yap.sendMoney.addbeneficiary.viewmodels.BeneficiaryOverviewViewModel viewModel = mViewModel;
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

    public FragmentBeneficiaryOverviewBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 33, sIncludes, sViewsWithIds));
    }
    private FragmentBeneficiaryOverviewBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.LinearLayout) bindings[27]
            , (co.yap.widgets.mobile.CountryCodePicker) bindings[28]
            , (co.yap.widgets.CoreButton) bindings[25]
            , (android.widget.EditText) bindings[20]
            , (android.widget.EditText) bindings[24]
            , (android.widget.EditText) bindings[13]
            , (android.widget.EditText) bindings[15]
            , (android.widget.EditText) bindings[17]
            , (android.widget.EditText) bindings[22]
            , (android.widget.EditText) bindings[11]
            , (co.yap.widgets.CoreCircularImageView) bindings[3]
            , (android.widget.TextView) bindings[8]
            , (android.widget.LinearLayout) bindings[18]
            , (android.widget.TextView) bindings[31]
            , (android.widget.TextView) bindings[29]
            , (android.widget.TextView) bindings[32]
            , (android.widget.TextView) bindings[30]
            , (android.widget.LinearLayout) bindings[26]
            );
        this.confirmButton.setTag(null);
        this.etAccountIbanNumber.setTag(null);
        this.etBankREquiredFieldCode.setTag(null);
        this.etFirstName.setTag(null);
        this.etLastName.setTag(null);
        this.etMobileNumber.setTag(null);
        this.etSwiftCode.setTag(null);
        this.etnickName.setTag(null);
        this.flagImg.setTag(null);
        this.labelCardType.setTag(null);
        this.llBankDetail.setTag(null);
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.TextView) bindings[1];
        this.mboundView1.setTag(null);
        this.mboundView10 = (android.widget.TextView) bindings[10];
        this.mboundView10.setTag(null);
        this.mboundView12 = (android.widget.TextView) bindings[12];
        this.mboundView12.setTag(null);
        this.mboundView14 = (android.widget.TextView) bindings[14];
        this.mboundView14.setTag(null);
        this.mboundView16 = (android.widget.TextView) bindings[16];
        this.mboundView16.setTag(null);
        this.mboundView19 = (android.widget.TextView) bindings[19];
        this.mboundView19.setTag(null);
        this.mboundView2 = (android.widget.TextView) bindings[2];
        this.mboundView2.setTag(null);
        this.mboundView21 = (android.widget.TextView) bindings[21];
        this.mboundView21.setTag(null);
        this.mboundView23 = (android.widget.TextView) bindings[23];
        this.mboundView23.setTag(null);
        this.mboundView4 = (android.widget.TextView) bindings[4];
        this.mboundView4.setTag(null);
        this.mboundView5 = (android.widget.TextView) bindings[5];
        this.mboundView5.setTag(null);
        this.mboundView6 = (android.widget.TextView) bindings[6];
        this.mboundView6.setTag(null);
        this.mboundView7 = (android.widget.TextView) bindings[7];
        this.mboundView7.setTag(null);
        this.mboundView9 = (android.widget.TextView) bindings[9];
        this.mboundView9.setTag(null);
        setRootTag(root);
        // listeners
        mCallback21 = new co.yap.sendmoney.generated.callback.OnClickListener(this, 1);
        mCallback22 = new co.yap.sendmoney.generated.callback.OnClickListener(this, 2);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x10000L;
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
            setViewModel((co.yap.sendMoney.addbeneficiary.viewmodels.BeneficiaryOverviewViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable co.yap.sendMoney.addbeneficiary.viewmodels.BeneficiaryOverviewViewModel ViewModel) {
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
                return onChangeViewModelState((co.yap.sendMoney.addbeneficiary.states.BeneficiaryOverviewState) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelState(co.yap.sendMoney.addbeneficiary.states.BeneficiaryOverviewState ViewModelState, int fieldId) {
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
        else if (fieldId == BR.mobile) {
            synchronized(this) {
                    mDirtyFlags |= 0x200L;
            }
            return true;
        }
        else if (fieldId == BR.drawbleRight) {
            synchronized(this) {
                    mDirtyFlags |= 0x400L;
            }
            return true;
        }
        else if (fieldId == BR.mobileNoLength) {
            synchronized(this) {
                    mDirtyFlags |= 0x800L;
            }
            return true;
        }
        else if (fieldId == BR.accountIban) {
            synchronized(this) {
                    mDirtyFlags |= 0x1000L;
            }
            return true;
        }
        else if (fieldId == BR.swiftCode) {
            synchronized(this) {
                    mDirtyFlags |= 0x2000L;
            }
            return true;
        }
        else if (fieldId == BR.countryBankRequirementFieldCode) {
            synchronized(this) {
                    mDirtyFlags |= 0x4000L;
            }
            return true;
        }
        else if (fieldId == BR.valid) {
            synchronized(this) {
                    mDirtyFlags |= 0x8000L;
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
        boolean viewModelStateLastNameEmpty = false;
        java.lang.String viewModelStateSwiftCode = null;
        boolean viewModelStateCountryBankRequirementFieldCodeEmpty = false;
        java.lang.String viewModelStateAccountIban = null;
        boolean viewModelStateSwiftCodeEmpty = false;
        boolean viewModelStateCountryBankRequirementFieldCodeEmptyBooleanTrueBooleanFalse = false;
        boolean viewModelStateValid = false;
        android.graphics.drawable.Drawable viewModelStateDrawbleRight = null;
        co.yap.sendMoney.addbeneficiary.states.BeneficiaryOverviewState viewModelState = null;
        boolean viewModelStateMobileEmptyBooleanTrueBooleanFalse = false;
        boolean viewModelStateFirstNameEmpty = false;
        java.lang.String viewModelStateTransferType = null;
        boolean viewModelStateFirstNameEmptyBooleanTrueBooleanFalse = false;
        boolean viewModelStateSwiftCodeEmptyBooleanTrueBooleanFalse = false;
        java.lang.String viewModelStateNickName = null;
        boolean viewModelStateNickNameEmpty = false;
        int viewModelStateMobileNoLength = 0;
        boolean viewModelStateAccountIbanEmpty = false;
        boolean viewModelStateAccountIbanEmptyBooleanTrueBooleanFalse = false;
        java.lang.String viewModelStateCountryBankRequirementFieldCode = null;
        boolean viewModelStateLastNameEmptyBooleanTrueBooleanFalse = false;
        java.lang.String viewModelStateFirstName = null;
        java.lang.String viewModelStateMobile = null;
        java.lang.String viewModelStateLastName = null;
        java.lang.String viewModelStateCountry = null;
        java.lang.String viewModelStateCurrency = null;
        int viewModelStateFlagDrawableResId = 0;
        boolean viewModelStateNickNameEmptyBooleanTrueBooleanFalse = false;
        co.yap.sendMoney.addbeneficiary.viewmodels.BeneficiaryOverviewViewModel viewModel = mViewModel;
        boolean viewModelStateMobileEmpty = false;

        if ((dirtyFlags & 0x1ffffL) != 0) {



                if (viewModel != null) {
                    // read viewModel.state
                    viewModelState = viewModel.getState();
                }
                updateRegistration(0, viewModelState);

            if ((dirtyFlags & 0x12003L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.swiftCode
                        viewModelStateSwiftCode = viewModelState.getSwiftCode();
                    }


                    if (viewModelStateSwiftCode != null) {
                        // read viewModel.state.swiftCode.empty
                        viewModelStateSwiftCodeEmpty = viewModelStateSwiftCode.isEmpty();
                    }
                if((dirtyFlags & 0x12003L) != 0) {
                    if(viewModelStateSwiftCodeEmpty) {
                            dirtyFlags |= 0x1000000L;
                    }
                    else {
                            dirtyFlags |= 0x800000L;
                    }
                }


                    // read viewModel.state.swiftCode.empty ? true : false
                    viewModelStateSwiftCodeEmptyBooleanTrueBooleanFalse = ((viewModelStateSwiftCodeEmpty) ? (true) : (false));
            }
            if ((dirtyFlags & 0x11003L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.accountIban
                        viewModelStateAccountIban = viewModelState.getAccountIban();
                    }


                    if (viewModelStateAccountIban != null) {
                        // read viewModel.state.accountIban.empty
                        viewModelStateAccountIbanEmpty = viewModelStateAccountIban.isEmpty();
                    }
                if((dirtyFlags & 0x11003L) != 0) {
                    if(viewModelStateAccountIbanEmpty) {
                            dirtyFlags |= 0x4000000L;
                    }
                    else {
                            dirtyFlags |= 0x2000000L;
                    }
                }


                    // read viewModel.state.accountIban.empty ? true : false
                    viewModelStateAccountIbanEmptyBooleanTrueBooleanFalse = ((viewModelStateAccountIbanEmpty) ? (true) : (false));
            }
            if ((dirtyFlags & 0x18003L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.valid
                        viewModelStateValid = viewModelState.getValid();
                    }
            }
            if ((dirtyFlags & 0x10403L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.drawbleRight
                        viewModelStateDrawbleRight = viewModelState.getDrawbleRight();
                    }
            }
            if ((dirtyFlags & 0x10013L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.transferType
                        viewModelStateTransferType = viewModelState.getTransferType();
                    }
            }
            if ((dirtyFlags & 0x10043L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.nickName
                        viewModelStateNickName = viewModelState.getNickName();
                    }


                    if (viewModelStateNickName != null) {
                        // read viewModel.state.nickName.empty
                        viewModelStateNickNameEmpty = viewModelStateNickName.isEmpty();
                    }
                if((dirtyFlags & 0x10043L) != 0) {
                    if(viewModelStateNickNameEmpty) {
                            dirtyFlags |= 0x40000000L;
                    }
                    else {
                            dirtyFlags |= 0x20000000L;
                    }
                }


                    // read viewModel.state.nickName.empty ? true : false
                    viewModelStateNickNameEmptyBooleanTrueBooleanFalse = ((viewModelStateNickNameEmpty) ? (true) : (false));
            }
            if ((dirtyFlags & 0x10803L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.mobileNoLength
                        viewModelStateMobileNoLength = viewModelState.getMobileNoLength();
                    }
            }
            if ((dirtyFlags & 0x14003L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.countryBankRequirementFieldCode
                        viewModelStateCountryBankRequirementFieldCode = viewModelState.getCountryBankRequirementFieldCode();
                    }


                    if (viewModelStateCountryBankRequirementFieldCode != null) {
                        // read viewModel.state.countryBankRequirementFieldCode.empty
                        viewModelStateCountryBankRequirementFieldCodeEmpty = viewModelStateCountryBankRequirementFieldCode.isEmpty();
                    }
                if((dirtyFlags & 0x14003L) != 0) {
                    if(viewModelStateCountryBankRequirementFieldCodeEmpty) {
                            dirtyFlags |= 0x40000L;
                    }
                    else {
                            dirtyFlags |= 0x20000L;
                    }
                }


                    // read viewModel.state.countryBankRequirementFieldCode.empty ? true : false
                    viewModelStateCountryBankRequirementFieldCodeEmptyBooleanTrueBooleanFalse = ((viewModelStateCountryBankRequirementFieldCodeEmpty) ? (true) : (false));
            }
            if ((dirtyFlags & 0x10083L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.firstName
                        viewModelStateFirstName = viewModelState.getFirstName();
                    }


                    if (viewModelStateFirstName != null) {
                        // read viewModel.state.firstName.empty
                        viewModelStateFirstNameEmpty = viewModelStateFirstName.isEmpty();
                    }
                if((dirtyFlags & 0x10083L) != 0) {
                    if(viewModelStateFirstNameEmpty) {
                            dirtyFlags |= 0x400000L;
                    }
                    else {
                            dirtyFlags |= 0x200000L;
                    }
                }


                    // read viewModel.state.firstName.empty ? true : false
                    viewModelStateFirstNameEmptyBooleanTrueBooleanFalse = ((viewModelStateFirstNameEmpty) ? (true) : (false));
            }
            if ((dirtyFlags & 0x10203L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.mobile
                        viewModelStateMobile = viewModelState.getMobile();
                    }


                    if (viewModelStateMobile != null) {
                        // read viewModel.state.mobile.empty
                        viewModelStateMobileEmpty = viewModelStateMobile.isEmpty();
                    }
                if((dirtyFlags & 0x10203L) != 0) {
                    if(viewModelStateMobileEmpty) {
                            dirtyFlags |= 0x100000L;
                    }
                    else {
                            dirtyFlags |= 0x80000L;
                    }
                }


                    // read viewModel.state.mobile.empty ? true : false
                    viewModelStateMobileEmptyBooleanTrueBooleanFalse = ((viewModelStateMobileEmpty) ? (true) : (false));
            }
            if ((dirtyFlags & 0x10103L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.lastName
                        viewModelStateLastName = viewModelState.getLastName();
                    }


                    if (viewModelStateLastName != null) {
                        // read viewModel.state.lastName.empty
                        viewModelStateLastNameEmpty = viewModelStateLastName.isEmpty();
                    }
                if((dirtyFlags & 0x10103L) != 0) {
                    if(viewModelStateLastNameEmpty) {
                            dirtyFlags |= 0x10000000L;
                    }
                    else {
                            dirtyFlags |= 0x8000000L;
                    }
                }


                    // read viewModel.state.lastName.empty ? true : false
                    viewModelStateLastNameEmptyBooleanTrueBooleanFalse = ((viewModelStateLastNameEmpty) ? (true) : (false));
            }
            if ((dirtyFlags & 0x1000bL) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.country
                        viewModelStateCountry = viewModelState.getCountry();
                    }
            }
            if ((dirtyFlags & 0x10023L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.currency
                        viewModelStateCurrency = viewModelState.getCurrency();
                    }
            }
            if ((dirtyFlags & 0x10007L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.flagDrawableResId
                        viewModelStateFlagDrawableResId = viewModelState.getFlagDrawableResId();
                    }
            }
        }
        // batch finished
        if ((dirtyFlags & 0x10000L) != 0) {
            // api target 1

            this.confirmButton.setOnClickListener(mCallback22);
            co.yap.yapcore.binders.UIBinder.setText(this.confirmButton, co.yap.translation.Strings.screen_beneficiary_overview_button_confirm);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etAccountIbanNumber, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etAccountIbanNumberandroidTextAttrChanged);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etBankREquiredFieldCode, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etBankREquiredFieldCodeandroidTextAttrChanged);
            co.yap.yapcore.binders.UIBinder.setHint(this.etFirstName, co.yap.translation.Strings.screen_add_beneficiary_detail_display_text_transfer_first_name);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etFirstName, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etFirstNameandroidTextAttrChanged);
            co.yap.yapcore.binders.UIBinder.setHint(this.etLastName, co.yap.translation.Strings.screen_add_beneficiary_detail_input_text_last_name_hint);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etLastName, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etLastNameandroidTextAttrChanged);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etMobileNumber, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etMobileNumberandroidTextAttrChanged);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etSwiftCode, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etSwiftCodeandroidTextAttrChanged);
            co.yap.yapcore.binders.UIBinder.setHint(this.etnickName, co.yap.translation.Strings.screen_add_beneficiary_detail_input_text_nick_name_hint);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etnickName, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etnickNameandroidTextAttrChanged);
            this.llBankDetail.setOnClickListener(mCallback21);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView1, co.yap.translation.Strings.screen_add_beneficiary_detail_display_text_title);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView10, co.yap.translation.Strings.screen_add_beneficiary_detail_display_text_transfer_nick_name);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView12, co.yap.translation.Strings.screen_add_beneficiary_detail_display_text_transfer_first_name);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView14, co.yap.translation.Strings.screen_add_beneficiary_detail_display_text_transfer_last_name);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView16, co.yap.translation.Strings.screen_add_beneficiary_detail_display_text_transfer_phone);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView19, co.yap.translation.Strings.screen_beneficiary_overview_display_text_account_number_iban);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView2, co.yap.translation.Strings.screen_add_beneficiary_detail_display_text_country);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView21, co.yap.translation.Strings.screen_beneficiary_overview_display_text_swift_code);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView23, co.yap.translation.Strings.screen_beneficiary_overview_display_text_field_code);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView5, co.yap.translation.Strings.screen_add_beneficiary_detail_display_text_transfer_type);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView7, co.yap.translation.Strings.screen_add_beneficiary_detail_display_text_currency);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView9, co.yap.translation.Strings.screen_add_beneficiary_detail_display_text_change_currency);
        }
        if ((dirtyFlags & 0x18003L) != 0) {
            // api target 1

            co.yap.yapcore.binders.UIBinder.setEnable(this.confirmButton, viewModelStateValid);
        }
        if ((dirtyFlags & 0x11003L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etAccountIbanNumber, viewModelStateAccountIban);
            co.yap.yapcore.binders.UIBinder.setSelectedTextColor(this.mboundView19, viewModelStateAccountIbanEmptyBooleanTrueBooleanFalse);
        }
        if ((dirtyFlags & 0x14003L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etBankREquiredFieldCode, viewModelStateCountryBankRequirementFieldCode);
            co.yap.yapcore.binders.UIBinder.setSelectedTextColor(this.mboundView23, viewModelStateCountryBankRequirementFieldCodeEmptyBooleanTrueBooleanFalse);
        }
        if ((dirtyFlags & 0x10083L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etFirstName, viewModelStateFirstName);
            co.yap.yapcore.binders.UIBinder.setSelectedTextColor(this.mboundView12, viewModelStateFirstNameEmptyBooleanTrueBooleanFalse);
        }
        if ((dirtyFlags & 0x10103L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etLastName, viewModelStateLastName);
            co.yap.yapcore.binders.UIBinder.setSelectedTextColor(this.mboundView14, viewModelStateLastNameEmptyBooleanTrueBooleanFalse);
        }
        if ((dirtyFlags & 0x10403L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setDrawableRight(this.etMobileNumber, viewModelStateDrawbleRight);
        }
        if ((dirtyFlags & 0x10803L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setMaxLength(this.etMobileNumber, viewModelStateMobileNoLength);
        }
        if ((dirtyFlags & 0x10203L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etMobileNumber, viewModelStateMobile);
            co.yap.yapcore.binders.UIBinder.setSelectedTextColor(this.mboundView16, viewModelStateMobileEmptyBooleanTrueBooleanFalse);
        }
        if ((dirtyFlags & 0x12003L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etSwiftCode, viewModelStateSwiftCode);
            co.yap.yapcore.binders.UIBinder.setSelectedTextColor(this.mboundView21, viewModelStateSwiftCodeEmptyBooleanTrueBooleanFalse);
        }
        if ((dirtyFlags & 0x10043L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etnickName, viewModelStateNickName);
            co.yap.yapcore.binders.UIBinder.setSelectedTextColor(this.mboundView10, viewModelStateNickNameEmptyBooleanTrueBooleanFalse);
        }
        if ((dirtyFlags & 0x10007L) != 0) {
            // api target 1

            co.yap.yapcore.binders.UIBinder.setImageResId(this.flagImg, viewModelStateFlagDrawableResId);
        }
        if ((dirtyFlags & 0x10023L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.labelCardType, viewModelStateCurrency);
        }
        if ((dirtyFlags & 0x1000bL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView4, viewModelStateCountry);
        }
        if ((dirtyFlags & 0x10013L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView6, viewModelStateTransferType);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 1: {
                // localize variables for thread safety
                // viewModel
                co.yap.sendMoney.addbeneficiary.viewmodels.BeneficiaryOverviewViewModel viewModel = mViewModel;
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
                co.yap.sendMoney.addbeneficiary.viewmodels.BeneficiaryOverviewViewModel viewModel = mViewModel;
                // viewModel != null
                boolean viewModelJavaLangObjectNull = false;
                // v.id
                int callbackArg0Id = 0;



                viewModelJavaLangObjectNull = (viewModel) != (null);
                if (viewModelJavaLangObjectNull) {


                    if ((callbackArg_0) != (null)) {


                        callbackArg0Id = callbackArg_0.getId();

                        viewModel.handlePressOnConfirm(callbackArg0Id);
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
        flag 9 (0xaL): viewModel.state.mobile
        flag 10 (0xbL): viewModel.state.drawbleRight
        flag 11 (0xcL): viewModel.state.mobileNoLength
        flag 12 (0xdL): viewModel.state.accountIban
        flag 13 (0xeL): viewModel.state.swiftCode
        flag 14 (0xfL): viewModel.state.countryBankRequirementFieldCode
        flag 15 (0x10L): viewModel.state.valid
        flag 16 (0x11L): null
        flag 17 (0x12L): viewModel.state.countryBankRequirementFieldCode.empty ? true : false
        flag 18 (0x13L): viewModel.state.countryBankRequirementFieldCode.empty ? true : false
        flag 19 (0x14L): viewModel.state.mobile.empty ? true : false
        flag 20 (0x15L): viewModel.state.mobile.empty ? true : false
        flag 21 (0x16L): viewModel.state.firstName.empty ? true : false
        flag 22 (0x17L): viewModel.state.firstName.empty ? true : false
        flag 23 (0x18L): viewModel.state.swiftCode.empty ? true : false
        flag 24 (0x19L): viewModel.state.swiftCode.empty ? true : false
        flag 25 (0x1aL): viewModel.state.accountIban.empty ? true : false
        flag 26 (0x1bL): viewModel.state.accountIban.empty ? true : false
        flag 27 (0x1cL): viewModel.state.lastName.empty ? true : false
        flag 28 (0x1dL): viewModel.state.lastName.empty ? true : false
        flag 29 (0x1eL): viewModel.state.nickName.empty ? true : false
        flag 30 (0x1fL): viewModel.state.nickName.empty ? true : false
    flag mapping end*/
    //end
}