package co.yap.sendmoney.databinding;
import co.yap.sendmoney.R;
import co.yap.sendmoney.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityEditBeneficiaryBindingImpl extends ActivityEditBeneficiaryBinding implements co.yap.sendmoney.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.appCompatTextView, 40);
        sViewsWithIds.put(R.id.tvTransferType, 41);
        sViewsWithIds.put(R.id.tvBankPhoneNumber, 42);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    private final androidx.appcompat.widget.AppCompatTextView mboundView11;
    @NonNull
    private final androidx.appcompat.widget.AppCompatImageView mboundView12;
    @NonNull
    private final androidx.appcompat.widget.AppCompatImageView mboundView13;
    @NonNull
    private final android.widget.TextView mboundView14;
    @NonNull
    private final android.widget.LinearLayout mboundView15;
    @NonNull
    private final androidx.appcompat.widget.AppCompatTextView mboundView18;
    @NonNull
    private final androidx.appcompat.widget.AppCompatTextView mboundView20;
    @NonNull
    private final androidx.appcompat.widget.AppCompatTextView mboundView22;
    @NonNull
    private final androidx.appcompat.widget.AppCompatTextView mboundView24;
    @NonNull
    private final androidx.appcompat.widget.AppCompatTextView mboundView26;
    @NonNull
    private final co.yap.widgets.CoreCircularImageView mboundView29;
    @NonNull
    private final android.widget.TextView mboundView3;
    @NonNull
    private final androidx.appcompat.widget.AppCompatTextView mboundView35;
    @NonNull
    private final android.widget.TextView mboundView37;
    @NonNull
    private final androidx.appcompat.widget.LinearLayoutCompat mboundView4;
    @NonNull
    private final android.widget.TextView mboundView6;
    @NonNull
    private final android.widget.TextView mboundView8;
    @NonNull
    private final android.widget.LinearLayout mboundView9;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback13;
    @Nullable
    private final android.view.View.OnClickListener mCallback11;
    @Nullable
    private final android.view.View.OnClickListener mCallback12;
    // values
    // listeners
    // Inverse Binding Event Handlers
    private androidx.databinding.InverseBindingListener etBankREquiredFieldCodeandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of editBeneficiaryViewModel.state.countryBankRequirementFieldCode
            //         is editBeneficiaryViewModel.state.setCountryBankRequirementFieldCode((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etBankREquiredFieldCode);
            // localize variables for thread safety
            // editBeneficiaryViewModel.state.countryBankRequirementFieldCode
            java.lang.String editBeneficiaryViewModelStateCountryBankRequirementFieldCode = null;
            // editBeneficiaryViewModel.state
            co.yap.sendMoney.editbeneficiary.states.EditBeneficiaryStates editBeneficiaryViewModelState = null;
            // editBeneficiaryViewModel.state != null
            boolean editBeneficiaryViewModelStateJavaLangObjectNull = false;
            // editBeneficiaryViewModel != null
            boolean editBeneficiaryViewModelJavaLangObjectNull = false;
            // editBeneficiaryViewModel
            co.yap.sendMoney.editbeneficiary.viewmodel.EditBeneficiaryViewModel editBeneficiaryViewModel = mEditBeneficiaryViewModel;



            editBeneficiaryViewModelJavaLangObjectNull = (editBeneficiaryViewModel) != (null);
            if (editBeneficiaryViewModelJavaLangObjectNull) {


                editBeneficiaryViewModelState = editBeneficiaryViewModel.getState();

                editBeneficiaryViewModelStateJavaLangObjectNull = (editBeneficiaryViewModelState) != (null);
                if (editBeneficiaryViewModelStateJavaLangObjectNull) {




                    editBeneficiaryViewModelState.setCountryBankRequirementFieldCode(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener etFirstNameandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of editBeneficiaryViewModel.state.firstName
            //         is editBeneficiaryViewModel.state.setFirstName((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etFirstName);
            // localize variables for thread safety
            // editBeneficiaryViewModel.state
            co.yap.sendMoney.editbeneficiary.states.EditBeneficiaryStates editBeneficiaryViewModelState = null;
            // editBeneficiaryViewModel.state != null
            boolean editBeneficiaryViewModelStateJavaLangObjectNull = false;
            // editBeneficiaryViewModel.state.firstName
            java.lang.String editBeneficiaryViewModelStateFirstName = null;
            // editBeneficiaryViewModel != null
            boolean editBeneficiaryViewModelJavaLangObjectNull = false;
            // editBeneficiaryViewModel
            co.yap.sendMoney.editbeneficiary.viewmodel.EditBeneficiaryViewModel editBeneficiaryViewModel = mEditBeneficiaryViewModel;



            editBeneficiaryViewModelJavaLangObjectNull = (editBeneficiaryViewModel) != (null);
            if (editBeneficiaryViewModelJavaLangObjectNull) {


                editBeneficiaryViewModelState = editBeneficiaryViewModel.getState();

                editBeneficiaryViewModelStateJavaLangObjectNull = (editBeneficiaryViewModelState) != (null);
                if (editBeneficiaryViewModelStateJavaLangObjectNull) {




                    editBeneficiaryViewModelState.setFirstName(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener etLastNameandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of editBeneficiaryViewModel.state.lastName
            //         is editBeneficiaryViewModel.state.setLastName((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etLastName);
            // localize variables for thread safety
            // editBeneficiaryViewModel.state
            co.yap.sendMoney.editbeneficiary.states.EditBeneficiaryStates editBeneficiaryViewModelState = null;
            // editBeneficiaryViewModel.state != null
            boolean editBeneficiaryViewModelStateJavaLangObjectNull = false;
            // editBeneficiaryViewModel.state.lastName
            java.lang.String editBeneficiaryViewModelStateLastName = null;
            // editBeneficiaryViewModel != null
            boolean editBeneficiaryViewModelJavaLangObjectNull = false;
            // editBeneficiaryViewModel
            co.yap.sendMoney.editbeneficiary.viewmodel.EditBeneficiaryViewModel editBeneficiaryViewModel = mEditBeneficiaryViewModel;



            editBeneficiaryViewModelJavaLangObjectNull = (editBeneficiaryViewModel) != (null);
            if (editBeneficiaryViewModelJavaLangObjectNull) {


                editBeneficiaryViewModelState = editBeneficiaryViewModel.getState();

                editBeneficiaryViewModelStateJavaLangObjectNull = (editBeneficiaryViewModelState) != (null);
                if (editBeneficiaryViewModelStateJavaLangObjectNull) {




                    editBeneficiaryViewModelState.setLastName(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener etPhoneNumberandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of editBeneficiaryViewModel.state.phoneNumber
            //         is editBeneficiaryViewModel.state.setPhoneNumber((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etPhoneNumber);
            // localize variables for thread safety
            // editBeneficiaryViewModel.state
            co.yap.sendMoney.editbeneficiary.states.EditBeneficiaryStates editBeneficiaryViewModelState = null;
            // editBeneficiaryViewModel.state != null
            boolean editBeneficiaryViewModelStateJavaLangObjectNull = false;
            // editBeneficiaryViewModel.state.phoneNumber
            java.lang.String editBeneficiaryViewModelStatePhoneNumber = null;
            // editBeneficiaryViewModel != null
            boolean editBeneficiaryViewModelJavaLangObjectNull = false;
            // editBeneficiaryViewModel
            co.yap.sendMoney.editbeneficiary.viewmodel.EditBeneficiaryViewModel editBeneficiaryViewModel = mEditBeneficiaryViewModel;



            editBeneficiaryViewModelJavaLangObjectNull = (editBeneficiaryViewModel) != (null);
            if (editBeneficiaryViewModelJavaLangObjectNull) {


                editBeneficiaryViewModelState = editBeneficiaryViewModel.getState();

                editBeneficiaryViewModelStateJavaLangObjectNull = (editBeneficiaryViewModelState) != (null);
                if (editBeneficiaryViewModelStateJavaLangObjectNull) {




                    editBeneficiaryViewModelState.setPhoneNumber(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener etSwiftCodeandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of editBeneficiaryViewModel.state.swiftCode
            //         is editBeneficiaryViewModel.state.setSwiftCode((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etSwiftCode);
            // localize variables for thread safety
            // editBeneficiaryViewModel.state
            co.yap.sendMoney.editbeneficiary.states.EditBeneficiaryStates editBeneficiaryViewModelState = null;
            // editBeneficiaryViewModel.state != null
            boolean editBeneficiaryViewModelStateJavaLangObjectNull = false;
            // editBeneficiaryViewModel.state.swiftCode
            java.lang.String editBeneficiaryViewModelStateSwiftCode = null;
            // editBeneficiaryViewModel != null
            boolean editBeneficiaryViewModelJavaLangObjectNull = false;
            // editBeneficiaryViewModel
            co.yap.sendMoney.editbeneficiary.viewmodel.EditBeneficiaryViewModel editBeneficiaryViewModel = mEditBeneficiaryViewModel;



            editBeneficiaryViewModelJavaLangObjectNull = (editBeneficiaryViewModel) != (null);
            if (editBeneficiaryViewModelJavaLangObjectNull) {


                editBeneficiaryViewModelState = editBeneficiaryViewModel.getState();

                editBeneficiaryViewModelStateJavaLangObjectNull = (editBeneficiaryViewModelState) != (null);
                if (editBeneficiaryViewModelStateJavaLangObjectNull) {




                    editBeneficiaryViewModelState.setSwiftCode(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener etnickNameandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of editBeneficiaryViewModel.state.nickName
            //         is editBeneficiaryViewModel.state.setNickName((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etnickName);
            // localize variables for thread safety
            // editBeneficiaryViewModel.state
            co.yap.sendMoney.editbeneficiary.states.EditBeneficiaryStates editBeneficiaryViewModelState = null;
            // editBeneficiaryViewModel.state != null
            boolean editBeneficiaryViewModelStateJavaLangObjectNull = false;
            // editBeneficiaryViewModel != null
            boolean editBeneficiaryViewModelJavaLangObjectNull = false;
            // editBeneficiaryViewModel
            co.yap.sendMoney.editbeneficiary.viewmodel.EditBeneficiaryViewModel editBeneficiaryViewModel = mEditBeneficiaryViewModel;
            // editBeneficiaryViewModel.state.nickName
            java.lang.String editBeneficiaryViewModelStateNickName = null;



            editBeneficiaryViewModelJavaLangObjectNull = (editBeneficiaryViewModel) != (null);
            if (editBeneficiaryViewModelJavaLangObjectNull) {


                editBeneficiaryViewModelState = editBeneficiaryViewModel.getState();

                editBeneficiaryViewModelStateJavaLangObjectNull = (editBeneficiaryViewModelState) != (null);
                if (editBeneficiaryViewModelStateJavaLangObjectNull) {




                    editBeneficiaryViewModelState.setNickName(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };

    public ActivityEditBeneficiaryBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 43, sIncludes, sViewsWithIds));
    }
    private ActivityEditBeneficiaryBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (androidx.appcompat.widget.AppCompatTextView) bindings[40]
            , (co.yap.widgets.CoreButton) bindings[39]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[33]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[34]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[27]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[38]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[21]
            , (com.google.android.material.textfield.TextInputEditText) bindings[23]
            , (co.yap.widgets.PrefixSuffixEditText) bindings[25]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[36]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[19]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[7]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[5]
            , (co.yap.widgets.CoreCircularImageView) bindings[10]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[16]
            , (android.widget.LinearLayout) bindings[28]
            , (android.widget.ImageView) bindings[1]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[32]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[30]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[42]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[17]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[31]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[2]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[41]
            );
        this.confirmButton.setTag(null);
        this.etAccountIban.setTag(null);
        this.etAccountNumber.setTag(null);
        this.etAccountNumberRMT.setTag(null);
        this.etBankREquiredFieldCode.setTag(null);
        this.etFirstName.setTag(null);
        this.etLastName.setTag(null);
        this.etPhoneNumber.setTag(null);
        this.etSwiftCode.setTag(null);
        this.etnickName.setTag(null);
        this.imgBankTransfer.setTag(null);
        this.imgFlag.setTag(null);
        this.imgProfile.setTag(null);
        this.labelCardType.setTag(null);
        this.llBankDetail.setTag(null);
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView11 = (androidx.appcompat.widget.AppCompatTextView) bindings[11];
        this.mboundView11.setTag(null);
        this.mboundView12 = (androidx.appcompat.widget.AppCompatImageView) bindings[12];
        this.mboundView12.setTag(null);
        this.mboundView13 = (androidx.appcompat.widget.AppCompatImageView) bindings[13];
        this.mboundView13.setTag(null);
        this.mboundView14 = (android.widget.TextView) bindings[14];
        this.mboundView14.setTag(null);
        this.mboundView15 = (android.widget.LinearLayout) bindings[15];
        this.mboundView15.setTag(null);
        this.mboundView18 = (androidx.appcompat.widget.AppCompatTextView) bindings[18];
        this.mboundView18.setTag(null);
        this.mboundView20 = (androidx.appcompat.widget.AppCompatTextView) bindings[20];
        this.mboundView20.setTag(null);
        this.mboundView22 = (androidx.appcompat.widget.AppCompatTextView) bindings[22];
        this.mboundView22.setTag(null);
        this.mboundView24 = (androidx.appcompat.widget.AppCompatTextView) bindings[24];
        this.mboundView24.setTag(null);
        this.mboundView26 = (androidx.appcompat.widget.AppCompatTextView) bindings[26];
        this.mboundView26.setTag(null);
        this.mboundView29 = (co.yap.widgets.CoreCircularImageView) bindings[29];
        this.mboundView29.setTag(null);
        this.mboundView3 = (android.widget.TextView) bindings[3];
        this.mboundView3.setTag(null);
        this.mboundView35 = (androidx.appcompat.widget.AppCompatTextView) bindings[35];
        this.mboundView35.setTag(null);
        this.mboundView37 = (android.widget.TextView) bindings[37];
        this.mboundView37.setTag(null);
        this.mboundView4 = (androidx.appcompat.widget.LinearLayoutCompat) bindings[4];
        this.mboundView4.setTag(null);
        this.mboundView6 = (android.widget.TextView) bindings[6];
        this.mboundView6.setTag(null);
        this.mboundView8 = (android.widget.TextView) bindings[8];
        this.mboundView8.setTag(null);
        this.mboundView9 = (android.widget.LinearLayout) bindings[9];
        this.mboundView9.setTag(null);
        this.tbBtnBack.setTag(null);
        this.tvBankAddress.setTag(null);
        this.tvBankName.setTag(null);
        this.tvChangeCurrency.setTag(null);
        this.tvIdCode.setTag(null);
        this.tvTitle.setTag(null);
        setRootTag(root);
        // listeners
        mCallback13 = new co.yap.sendmoney.generated.callback.OnClickListener(this, 3);
        mCallback11 = new co.yap.sendmoney.generated.callback.OnClickListener(this, 1);
        mCallback12 = new co.yap.sendmoney.generated.callback.OnClickListener(this, 2);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x8000L;
                mDirtyFlags_1 = 0x0L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0 || mDirtyFlags_1 != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.editBeneficiaryViewModel == variableId) {
            setEditBeneficiaryViewModel((co.yap.sendMoney.editbeneficiary.viewmodel.EditBeneficiaryViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setEditBeneficiaryViewModel(@Nullable co.yap.sendMoney.editbeneficiary.viewmodel.EditBeneficiaryViewModel EditBeneficiaryViewModel) {
        this.mEditBeneficiaryViewModel = EditBeneficiaryViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.editBeneficiaryViewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeEditBeneficiaryViewModelState((co.yap.sendMoney.editbeneficiary.states.EditBeneficiaryStates) object, fieldId);
        }
        return false;
    }
    private boolean onChangeEditBeneficiaryViewModelState(co.yap.sendMoney.editbeneficiary.states.EditBeneficiaryStates EditBeneficiaryViewModelState, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        else if (fieldId == BR.needOverView) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        else if (fieldId == BR.beneficiary) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        else if (fieldId == BR.country) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        else if (fieldId == BR.transferType) {
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
        else if (fieldId == BR.phoneNumber) {
            synchronized(this) {
                    mDirtyFlags |= 0x200L;
            }
            return true;
        }
        else if (fieldId == BR.countryCode) {
            synchronized(this) {
                    mDirtyFlags |= 0x400L;
            }
            return true;
        }
        else if (fieldId == BR.needIban) {
            synchronized(this) {
                    mDirtyFlags |= 0x800L;
            }
            return true;
        }
        else if (fieldId == BR.accountNumber) {
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
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        long dirtyFlags_1 = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
            dirtyFlags_1 = mDirtyFlags_1;
            mDirtyFlags_1 = 0;
        }
        java.lang.String editBeneficiaryViewModelStateBeneficiaryCountry = null;
        java.lang.String editBeneficiaryViewModelStateNeedIbanBooleanFalseEtAccountIbanAndroidStringScreenEditBeneficiaryDisplayTextAccountNumberEtAccountIbanAndroidStringScreenBeneficiaryOverviewDisplayTextAccountNumberIban = null;
        java.lang.String editBeneficiaryViewModelStateCountryCodeJavaLangObjectNullPhoneNumberUtilsKtGetCountryCodeForRegionEditBeneficiaryViewModelStateCountryCodeJavaLangString = null;
        boolean editBeneficiaryViewModelStateNeedIbanBooleanFalse = false;
        java.lang.String editBeneficiaryViewModelStateNeedIbanBooleanFalseMboundView26AndroidStringScreenEditBeneficiaryDisplayTextAccountNumberMboundView26AndroidStringScreenBeneficiaryOverviewDisplayTextAccountNumberIban = null;
        int editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringRMTBooleanTrueEditBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringSWIFTViewVISIBLEViewGONE = 0;
        java.lang.Boolean editBeneficiaryViewModelStateNeedOverView = null;
        boolean editBeneficiaryViewModelStateLastNameEmptyBooleanTrueBooleanFalse = false;
        boolean editBeneficiaryViewModelStateBeneficiaryBankNameJavaLangObjectNull = false;
        java.lang.String editBeneficiaryViewModelStateTransferTypeEqualsJavaLangStringCASHPAYOUTStringsScreenEditBeneficiaryDisplayTextPhoneNumberStringsScreenAddBeneficiaryDetailDisplayTextTransferPhone = null;
        int editBeneficiaryViewModelStateBeneficiaryIdentifierCode1Length = 0;
        int editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringUAEFTSBooleanTrueEditBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringDOMESTICViewGONEViewVISIBLE = 0;
        java.lang.String editBeneficiaryViewModelStateBeneficiaryBeneficiaryType = null;
        boolean editBeneficiaryViewModelStateFirstNameEmptyBooleanTrueBooleanFalse = false;
        java.lang.String editBeneficiaryViewModelStateBeneficiaryBankNameJavaLangObjectNullEditBeneficiaryViewModelStateBeneficiaryBankNameJavaLangString = null;
        java.lang.String editBeneficiaryViewModelStateNickName = null;
        java.lang.String editBeneficiaryViewModelStateNeedIbanBooleanFalseStringsScreenEditBeneficiaryDisplayTextAccountNumberStringsScreenBeneficiaryOverviewDisplayTextAccountNumberIban = null;
        boolean editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringDOMESTIC = false;
        android.graphics.drawable.Drawable editBeneficiaryViewModelStateTransferTypeEqualsJavaLangStringCASHPAYOUTMboundView12AndroidDrawableIcCashMboundView12AndroidDrawableIcBank = null;
        java.lang.Boolean editBeneficiaryViewModelStateNeedIban = null;
        java.lang.String editBeneficiaryViewModelStateCountryCodeJavaLangObjectNullEditBeneficiaryViewModelStateCountryCodeJavaLangString = null;
        boolean editBeneficiaryViewModelStateLastNameEmpty = false;
        java.lang.String editBeneficiaryViewModelStateBeneficiaryBeneficiaryPictureUrl = null;
        boolean editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringRMTBooleanTrueEditBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringSWIFT = false;
        boolean editBeneficiaryViewModelStateAccountNumberEmptyBooleanTrueBooleanFalse = false;
        boolean editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringRMT = false;
        java.lang.String editBeneficiaryViewModelStateNeedOverViewTvTitleAndroidStringScreenAddBeneficiaryDisplayTextTitleTvTitleAndroidStringScreenEditBeneficiaryDisplayTextTitle = null;
        java.lang.String editBeneficiaryViewModelStateNeedOverViewStringsScreenAddBeneficiaryDisplayTextTitleStringsScreenEditBeneficiaryDisplayTextTitle = null;
        java.lang.String editBeneficiaryViewModelStateTransferTypeEqualsJavaLangStringCASHPAYOUTMboundView8AndroidStringScreenAddBeneficiaryDisplayTextTransferTypeCashPickupMboundView8AndroidStringScreenAddBeneficiaryDisplayTextTransferTypeBank = null;
        boolean editBeneficiaryViewModelStateAccountNumberEmpty = false;
        int editBeneficiaryViewModelStateBeneficiaryIdentifierCode1LengthInt0ViewVISIBLEViewGONE = 0;
        java.lang.String editBeneficiaryViewModelStateBeneficiaryFullName = null;
        java.lang.String editBeneficiaryViewModelStateBeneficiaryCurrency = null;
        boolean editBeneficiaryViewModelStateFirstNameEmpty = false;
        boolean editBeneficiaryViewModelStateSwiftCodeEmpty = false;
        co.yap.sendMoney.editbeneficiary.states.EditBeneficiaryStates editBeneficiaryViewModelState = null;
        java.lang.String editBeneficiaryViewModelStateAccountNumber = null;
        boolean editBeneficiaryViewModelStateSwiftCodeEmptyBooleanTrueBooleanFalse = false;
        boolean editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringSWIFT = false;
        java.lang.String editBeneficiaryViewModelStateBeneficiaryBankName = null;
        boolean editBeneficiaryViewModelStateTransferTypeEqualsJavaLangStringCASHPAYOUT = false;
        boolean editBeneficiaryViewModelStatePhoneNumberEmptyBooleanTrueBooleanFalse = false;
        java.lang.String editBeneficiaryViewModelStateTransferType = null;
        java.lang.String editBeneficiaryViewModelStateBeneficiaryIdentifierCode1 = null;
        java.lang.String javaLangStringIDCodeEditBeneficiaryViewModelStateBeneficiaryIdentifierCode1 = null;
        java.lang.String editBeneficiaryViewModelStateFirstName = null;
        android.graphics.drawable.Drawable editBeneficiaryViewModelStateTransferTypeEqualsJavaLangStringCASHPAYOUTImgBankTransferAndroidDrawableIcCashImgBankTransferAndroidDrawableIcBank = null;
        int editBeneficiaryViewModelStateNeedOverViewViewVISIBLEViewGONE = 0;
        java.lang.String editBeneficiaryViewModelStateCountryBankRequirementFieldCode = null;
        int editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringUAEFTSBooleanTrueEditBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringDOMESTICViewVISIBLEViewGONE = 0;
        java.lang.String editBeneficiaryViewModelStatePhoneNumber = null;
        int editBeneficiaryViewModelStateNeedOverViewViewGONEViewVISIBLE = 0;
        boolean editBeneficiaryViewModelStateCountryBankRequirementFieldCodeEmpty = false;
        boolean editBeneficiaryViewModelStatePhoneNumberEmpty = false;
        boolean androidxDatabindingViewDataBindingSafeUnboxEditBeneficiaryViewModelStateNeedOverView = false;
        boolean editBeneficiaryViewModelStateNickNameEmpty = false;
        boolean editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringUAEFTSBooleanTrueEditBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringDOMESTIC = false;
        boolean editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringUAEFTS = false;
        java.lang.String editBeneficiaryViewModelStateSwiftCode = null;
        co.yap.sendMoney.editbeneficiary.viewmodel.EditBeneficiaryViewModel editBeneficiaryViewModel = mEditBeneficiaryViewModel;
        java.lang.String phoneNumberUtilsKtGetCountryCodeForRegionEditBeneficiaryViewModelStateCountryCode = null;
        boolean androidxDatabindingViewDataBindingSafeUnboxEditBeneficiaryViewModelStateNeedIban = false;
        boolean editBeneficiaryViewModelStateNickNameEmptyBooleanTrueBooleanFalse = false;
        java.lang.String editBeneficiaryViewModelStateLastName = null;
        java.lang.String editBeneficiaryViewModelStateCountryCode = null;
        java.lang.String editBeneficiaryViewModelStateBeneficiaryBranchName = null;
        java.lang.String editBeneficiaryViewModelStateTransferTypeEqualsJavaLangStringCASHPAYOUTStringsScreenAddBeneficiaryDisplayTextTransferTypeCashPickupStringsScreenAddBeneficiaryDisplayTextTransferTypeBank = null;
        java.lang.String editBeneficiaryViewModelStateCountry = null;
        boolean editBeneficiaryViewModelStateCountryCodeJavaLangObjectNull = false;
        boolean editBeneficiaryViewModelStateCountryBankRequirementFieldCodeEmptyBooleanTrueBooleanFalse = false;
        co.yap.networking.customers.responsedtos.sendmoney.Beneficiary editBeneficiaryViewModelStateBeneficiary = null;
        int editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringSWIFTViewVISIBLEViewGONE = 0;
        boolean editBeneficiaryViewModelStateBeneficiaryIdentifierCode1LengthInt0 = false;

        if ((dirtyFlags & 0xffffL) != 0) {



                if (editBeneficiaryViewModel != null) {
                    // read editBeneficiaryViewModel.state
                    editBeneficiaryViewModelState = editBeneficiaryViewModel.getState();
                }
                updateRegistration(0, editBeneficiaryViewModelState);

            if ((dirtyFlags & 0x8007L) != 0) {

                    if (editBeneficiaryViewModelState != null) {
                        // read editBeneficiaryViewModel.state.needOverView
                        editBeneficiaryViewModelStateNeedOverView = editBeneficiaryViewModelState.getNeedOverView();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(editBeneficiaryViewModel.state.needOverView)
                    androidxDatabindingViewDataBindingSafeUnboxEditBeneficiaryViewModelStateNeedOverView = androidx.databinding.ViewDataBinding.safeUnbox(editBeneficiaryViewModelStateNeedOverView);
                if((dirtyFlags & 0x8007L) != 0) {
                    if(androidxDatabindingViewDataBindingSafeUnboxEditBeneficiaryViewModelStateNeedOverView) {
                            dirtyFlags |= 0x200000000000L;
                            dirtyFlags |= 0x800000000000L;
                            dirtyFlags |= 0x800000000000000L;
                            dirtyFlags |= 0x8000000000000000L;
                    }
                    else {
                            dirtyFlags |= 0x100000000000L;
                            dirtyFlags |= 0x400000000000L;
                            dirtyFlags |= 0x400000000000000L;
                            dirtyFlags |= 0x4000000000000000L;
                    }
                }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(editBeneficiaryViewModel.state.needOverView) ? @android:string/screen_add_beneficiary_display_text_title : @android:string/screen_edit_beneficiary_display_text_title
                    editBeneficiaryViewModelStateNeedOverViewTvTitleAndroidStringScreenAddBeneficiaryDisplayTextTitleTvTitleAndroidStringScreenEditBeneficiaryDisplayTextTitle = ((androidxDatabindingViewDataBindingSafeUnboxEditBeneficiaryViewModelStateNeedOverView) ? (tvTitle.getResources().getString(R.string.screen_add_beneficiary_display_text_title)) : (tvTitle.getResources().getString(R.string.screen_edit_beneficiary_display_text_title)));
                    // read androidx.databinding.ViewDataBinding.safeUnbox(editBeneficiaryViewModel.state.needOverView) ? Strings.screen_add_beneficiary_display_text_title : Strings.screen_edit_beneficiary_display_text_title
                    editBeneficiaryViewModelStateNeedOverViewStringsScreenAddBeneficiaryDisplayTextTitleStringsScreenEditBeneficiaryDisplayTextTitle = ((androidxDatabindingViewDataBindingSafeUnboxEditBeneficiaryViewModelStateNeedOverView) ? (co.yap.translation.Strings.screen_add_beneficiary_display_text_title) : (co.yap.translation.Strings.screen_edit_beneficiary_display_text_title));
                    // read androidx.databinding.ViewDataBinding.safeUnbox(editBeneficiaryViewModel.state.needOverView) ? View.VISIBLE : View.GONE
                    editBeneficiaryViewModelStateNeedOverViewViewVISIBLEViewGONE = ((androidxDatabindingViewDataBindingSafeUnboxEditBeneficiaryViewModelStateNeedOverView) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
                    // read androidx.databinding.ViewDataBinding.safeUnbox(editBeneficiaryViewModel.state.needOverView) ? View.GONE : View.VISIBLE
                    editBeneficiaryViewModelStateNeedOverViewViewGONEViewVISIBLE = ((androidxDatabindingViewDataBindingSafeUnboxEditBeneficiaryViewModelStateNeedOverView) ? (android.view.View.GONE) : (android.view.View.VISIBLE));
            }
            if ((dirtyFlags & 0x8043L) != 0) {

                    if (editBeneficiaryViewModelState != null) {
                        // read editBeneficiaryViewModel.state.nickName
                        editBeneficiaryViewModelStateNickName = editBeneficiaryViewModelState.getNickName();
                    }


                    if (editBeneficiaryViewModelStateNickName != null) {
                        // read editBeneficiaryViewModel.state.nickName.empty
                        editBeneficiaryViewModelStateNickNameEmpty = editBeneficiaryViewModelStateNickName.isEmpty();
                    }
                if((dirtyFlags & 0x8043L) != 0) {
                    if(editBeneficiaryViewModelStateNickNameEmpty) {
                            dirtyFlags_1 |= 0x8L;
                    }
                    else {
                            dirtyFlags_1 |= 0x4L;
                    }
                }


                    // read editBeneficiaryViewModel.state.nickName.empty ? true : false
                    editBeneficiaryViewModelStateNickNameEmptyBooleanTrueBooleanFalse = ((editBeneficiaryViewModelStateNickNameEmpty) ? (true) : (false));
            }
            if ((dirtyFlags & 0x8803L) != 0) {

                    if (editBeneficiaryViewModelState != null) {
                        // read editBeneficiaryViewModel.state.needIban
                        editBeneficiaryViewModelStateNeedIban = editBeneficiaryViewModelState.getNeedIban();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(editBeneficiaryViewModel.state.needIban)
                    androidxDatabindingViewDataBindingSafeUnboxEditBeneficiaryViewModelStateNeedIban = androidx.databinding.ViewDataBinding.safeUnbox(editBeneficiaryViewModelStateNeedIban);


                    // read androidx.databinding.ViewDataBinding.safeUnbox(editBeneficiaryViewModel.state.needIban) == false
                    editBeneficiaryViewModelStateNeedIbanBooleanFalse = (androidxDatabindingViewDataBindingSafeUnboxEditBeneficiaryViewModelStateNeedIban) == (false);
                if((dirtyFlags & 0x8803L) != 0) {
                    if(editBeneficiaryViewModelStateNeedIbanBooleanFalse) {
                            dirtyFlags |= 0x20000L;
                            dirtyFlags |= 0x200000L;
                            dirtyFlags |= 0x800000000L;
                    }
                    else {
                            dirtyFlags |= 0x10000L;
                            dirtyFlags |= 0x100000L;
                            dirtyFlags |= 0x400000000L;
                    }
                }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(editBeneficiaryViewModel.state.needIban) == false ? @android:string/screen_edit_beneficiary_display_text_account_number : @android:string/screen_beneficiary_overview_display_text_account_number_iban
                    editBeneficiaryViewModelStateNeedIbanBooleanFalseEtAccountIbanAndroidStringScreenEditBeneficiaryDisplayTextAccountNumberEtAccountIbanAndroidStringScreenBeneficiaryOverviewDisplayTextAccountNumberIban = ((editBeneficiaryViewModelStateNeedIbanBooleanFalse) ? (etAccountIban.getResources().getString(R.string.screen_edit_beneficiary_display_text_account_number)) : (etAccountIban.getResources().getString(R.string.screen_beneficiary_overview_display_text_account_number_iban)));
                    // read androidx.databinding.ViewDataBinding.safeUnbox(editBeneficiaryViewModel.state.needIban) == false ? @android:string/screen_edit_beneficiary_display_text_account_number : @android:string/screen_beneficiary_overview_display_text_account_number_iban
                    editBeneficiaryViewModelStateNeedIbanBooleanFalseMboundView26AndroidStringScreenEditBeneficiaryDisplayTextAccountNumberMboundView26AndroidStringScreenBeneficiaryOverviewDisplayTextAccountNumberIban = ((editBeneficiaryViewModelStateNeedIbanBooleanFalse) ? (mboundView26.getResources().getString(R.string.screen_edit_beneficiary_display_text_account_number)) : (mboundView26.getResources().getString(R.string.screen_beneficiary_overview_display_text_account_number_iban)));
                    // read androidx.databinding.ViewDataBinding.safeUnbox(editBeneficiaryViewModel.state.needIban) == false ? Strings.screen_edit_beneficiary_display_text_account_number : Strings.screen_beneficiary_overview_display_text_account_number_iban
                    editBeneficiaryViewModelStateNeedIbanBooleanFalseStringsScreenEditBeneficiaryDisplayTextAccountNumberStringsScreenBeneficiaryOverviewDisplayTextAccountNumberIban = ((editBeneficiaryViewModelStateNeedIbanBooleanFalse) ? (co.yap.translation.Strings.screen_edit_beneficiary_display_text_account_number) : (co.yap.translation.Strings.screen_beneficiary_overview_display_text_account_number_iban));
            }
            if ((dirtyFlags & 0x9003L) != 0) {

                    if (editBeneficiaryViewModelState != null) {
                        // read editBeneficiaryViewModel.state.accountNumber
                        editBeneficiaryViewModelStateAccountNumber = editBeneficiaryViewModelState.getAccountNumber();
                    }


                    if (editBeneficiaryViewModelStateAccountNumber != null) {
                        // read editBeneficiaryViewModel.state.accountNumber.empty
                        editBeneficiaryViewModelStateAccountNumberEmpty = editBeneficiaryViewModelStateAccountNumber.isEmpty();
                    }
                if((dirtyFlags & 0x9003L) != 0) {
                    if(editBeneficiaryViewModelStateAccountNumberEmpty) {
                            dirtyFlags |= 0x80000000000L;
                    }
                    else {
                            dirtyFlags |= 0x40000000000L;
                    }
                }


                    // read editBeneficiaryViewModel.state.accountNumber.empty ? true : false
                    editBeneficiaryViewModelStateAccountNumberEmptyBooleanTrueBooleanFalse = ((editBeneficiaryViewModelStateAccountNumberEmpty) ? (true) : (false));
            }
            if ((dirtyFlags & 0x8023L) != 0) {

                    if (editBeneficiaryViewModelState != null) {
                        // read editBeneficiaryViewModel.state.transferType
                        editBeneficiaryViewModelStateTransferType = editBeneficiaryViewModelState.getTransferType();
                    }


                    if (editBeneficiaryViewModelStateTransferType != null) {
                        // read editBeneficiaryViewModel.state.transferType.equals("CASHPAYOUT")
                        editBeneficiaryViewModelStateTransferTypeEqualsJavaLangStringCASHPAYOUT = editBeneficiaryViewModelStateTransferType.equals("CASHPAYOUT");
                    }
                if((dirtyFlags & 0x8023L) != 0) {
                    if(editBeneficiaryViewModelStateTransferTypeEqualsJavaLangStringCASHPAYOUT) {
                            dirtyFlags |= 0x8000000L;
                            dirtyFlags |= 0x2000000000L;
                            dirtyFlags |= 0x2000000000000L;
                            dirtyFlags |= 0x200000000000000L;
                            dirtyFlags_1 |= 0x20L;
                    }
                    else {
                            dirtyFlags |= 0x4000000L;
                            dirtyFlags |= 0x1000000000L;
                            dirtyFlags |= 0x1000000000000L;
                            dirtyFlags |= 0x100000000000000L;
                            dirtyFlags_1 |= 0x10L;
                    }
                }


                    // read editBeneficiaryViewModel.state.transferType.equals("CASHPAYOUT") ? Strings.screen_edit_beneficiary_display_text_phone_number : Strings.screen_add_beneficiary_detail_display_text_transfer_phone
                    editBeneficiaryViewModelStateTransferTypeEqualsJavaLangStringCASHPAYOUTStringsScreenEditBeneficiaryDisplayTextPhoneNumberStringsScreenAddBeneficiaryDetailDisplayTextTransferPhone = ((editBeneficiaryViewModelStateTransferTypeEqualsJavaLangStringCASHPAYOUT) ? (co.yap.translation.Strings.screen_edit_beneficiary_display_text_phone_number) : (co.yap.translation.Strings.screen_add_beneficiary_detail_display_text_transfer_phone));
                    // read editBeneficiaryViewModel.state.transferType.equals("CASHPAYOUT") ? @android:drawable/ic_cash : @android:drawable/ic_bank
                    editBeneficiaryViewModelStateTransferTypeEqualsJavaLangStringCASHPAYOUTMboundView12AndroidDrawableIcCashMboundView12AndroidDrawableIcBank = ((editBeneficiaryViewModelStateTransferTypeEqualsJavaLangStringCASHPAYOUT) ? (getDrawableFromResource(mboundView12, R.drawable.ic_cash)) : (getDrawableFromResource(mboundView12, R.drawable.ic_bank)));
                    // read editBeneficiaryViewModel.state.transferType.equals("CASHPAYOUT") ? @android:string/screen_add_beneficiary_display_text_transfer_type_cash_pickup : @android:string/screen_add_beneficiary_display_text_transfer_type_bank
                    editBeneficiaryViewModelStateTransferTypeEqualsJavaLangStringCASHPAYOUTMboundView8AndroidStringScreenAddBeneficiaryDisplayTextTransferTypeCashPickupMboundView8AndroidStringScreenAddBeneficiaryDisplayTextTransferTypeBank = ((editBeneficiaryViewModelStateTransferTypeEqualsJavaLangStringCASHPAYOUT) ? (mboundView8.getResources().getString(R.string.screen_add_beneficiary_display_text_transfer_type_cash_pickup)) : (mboundView8.getResources().getString(R.string.screen_add_beneficiary_display_text_transfer_type_bank)));
                    // read editBeneficiaryViewModel.state.transferType.equals("CASHPAYOUT") ? @android:drawable/ic_cash : @android:drawable/ic_bank
                    editBeneficiaryViewModelStateTransferTypeEqualsJavaLangStringCASHPAYOUTImgBankTransferAndroidDrawableIcCashImgBankTransferAndroidDrawableIcBank = ((editBeneficiaryViewModelStateTransferTypeEqualsJavaLangStringCASHPAYOUT) ? (getDrawableFromResource(imgBankTransfer, R.drawable.ic_cash)) : (getDrawableFromResource(imgBankTransfer, R.drawable.ic_bank)));
                    // read editBeneficiaryViewModel.state.transferType.equals("CASHPAYOUT") ? Strings.screen_add_beneficiary_display_text_transfer_type_cash_pickup : Strings.screen_add_beneficiary_display_text_transfer_type_bank
                    editBeneficiaryViewModelStateTransferTypeEqualsJavaLangStringCASHPAYOUTStringsScreenAddBeneficiaryDisplayTextTransferTypeCashPickupStringsScreenAddBeneficiaryDisplayTextTransferTypeBank = ((editBeneficiaryViewModelStateTransferTypeEqualsJavaLangStringCASHPAYOUT) ? (co.yap.translation.Strings.screen_add_beneficiary_display_text_transfer_type_cash_pickup) : (co.yap.translation.Strings.screen_add_beneficiary_display_text_transfer_type_bank));
            }
            if ((dirtyFlags & 0x8083L) != 0) {

                    if (editBeneficiaryViewModelState != null) {
                        // read editBeneficiaryViewModel.state.firstName
                        editBeneficiaryViewModelStateFirstName = editBeneficiaryViewModelState.getFirstName();
                    }


                    if (editBeneficiaryViewModelStateFirstName != null) {
                        // read editBeneficiaryViewModel.state.firstName.empty
                        editBeneficiaryViewModelStateFirstNameEmpty = editBeneficiaryViewModelStateFirstName.isEmpty();
                    }
                if((dirtyFlags & 0x8083L) != 0) {
                    if(editBeneficiaryViewModelStateFirstNameEmpty) {
                            dirtyFlags |= 0x80000000L;
                    }
                    else {
                            dirtyFlags |= 0x40000000L;
                    }
                }


                    // read editBeneficiaryViewModel.state.firstName.empty ? true : false
                    editBeneficiaryViewModelStateFirstNameEmptyBooleanTrueBooleanFalse = ((editBeneficiaryViewModelStateFirstNameEmpty) ? (true) : (false));
            }
            if ((dirtyFlags & 0xc003L) != 0) {

                    if (editBeneficiaryViewModelState != null) {
                        // read editBeneficiaryViewModel.state.countryBankRequirementFieldCode
                        editBeneficiaryViewModelStateCountryBankRequirementFieldCode = editBeneficiaryViewModelState.getCountryBankRequirementFieldCode();
                    }


                    if (editBeneficiaryViewModelStateCountryBankRequirementFieldCode != null) {
                        // read editBeneficiaryViewModel.state.countryBankRequirementFieldCode.empty
                        editBeneficiaryViewModelStateCountryBankRequirementFieldCodeEmpty = editBeneficiaryViewModelStateCountryBankRequirementFieldCode.isEmpty();
                    }
                if((dirtyFlags & 0xc003L) != 0) {
                    if(editBeneficiaryViewModelStateCountryBankRequirementFieldCodeEmpty) {
                            dirtyFlags_1 |= 0x80L;
                    }
                    else {
                            dirtyFlags_1 |= 0x40L;
                    }
                }


                    // read editBeneficiaryViewModel.state.countryBankRequirementFieldCode.empty ? true : false
                    editBeneficiaryViewModelStateCountryBankRequirementFieldCodeEmptyBooleanTrueBooleanFalse = ((editBeneficiaryViewModelStateCountryBankRequirementFieldCodeEmpty) ? (true) : (false));
            }
            if ((dirtyFlags & 0x8203L) != 0) {

                    if (editBeneficiaryViewModelState != null) {
                        // read editBeneficiaryViewModel.state.phoneNumber
                        editBeneficiaryViewModelStatePhoneNumber = editBeneficiaryViewModelState.getPhoneNumber();
                    }


                    if (editBeneficiaryViewModelStatePhoneNumber != null) {
                        // read editBeneficiaryViewModel.state.phoneNumber.empty
                        editBeneficiaryViewModelStatePhoneNumberEmpty = editBeneficiaryViewModelStatePhoneNumber.isEmpty();
                    }
                if((dirtyFlags & 0x8203L) != 0) {
                    if(editBeneficiaryViewModelStatePhoneNumberEmpty) {
                            dirtyFlags |= 0x80000000000000L;
                    }
                    else {
                            dirtyFlags |= 0x40000000000000L;
                    }
                }


                    // read editBeneficiaryViewModel.state.phoneNumber.empty ? true : false
                    editBeneficiaryViewModelStatePhoneNumberEmptyBooleanTrueBooleanFalse = ((editBeneficiaryViewModelStatePhoneNumberEmpty) ? (true) : (false));
            }
            if ((dirtyFlags & 0xa003L) != 0) {

                    if (editBeneficiaryViewModelState != null) {
                        // read editBeneficiaryViewModel.state.swiftCode
                        editBeneficiaryViewModelStateSwiftCode = editBeneficiaryViewModelState.getSwiftCode();
                    }


                    if (editBeneficiaryViewModelStateSwiftCode != null) {
                        // read editBeneficiaryViewModel.state.swiftCode.empty
                        editBeneficiaryViewModelStateSwiftCodeEmpty = editBeneficiaryViewModelStateSwiftCode.isEmpty();
                    }
                if((dirtyFlags & 0xa003L) != 0) {
                    if(editBeneficiaryViewModelStateSwiftCodeEmpty) {
                            dirtyFlags |= 0x20000000000000L;
                    }
                    else {
                            dirtyFlags |= 0x10000000000000L;
                    }
                }


                    // read editBeneficiaryViewModel.state.swiftCode.empty ? true : false
                    editBeneficiaryViewModelStateSwiftCodeEmptyBooleanTrueBooleanFalse = ((editBeneficiaryViewModelStateSwiftCodeEmpty) ? (true) : (false));
            }
            if ((dirtyFlags & 0x8103L) != 0) {

                    if (editBeneficiaryViewModelState != null) {
                        // read editBeneficiaryViewModel.state.lastName
                        editBeneficiaryViewModelStateLastName = editBeneficiaryViewModelState.getLastName();
                    }


                    if (editBeneficiaryViewModelStateLastName != null) {
                        // read editBeneficiaryViewModel.state.lastName.empty
                        editBeneficiaryViewModelStateLastNameEmpty = editBeneficiaryViewModelStateLastName.isEmpty();
                    }
                if((dirtyFlags & 0x8103L) != 0) {
                    if(editBeneficiaryViewModelStateLastNameEmpty) {
                            dirtyFlags |= 0x2000000L;
                    }
                    else {
                            dirtyFlags |= 0x1000000L;
                    }
                }


                    // read editBeneficiaryViewModel.state.lastName.empty ? true : false
                    editBeneficiaryViewModelStateLastNameEmptyBooleanTrueBooleanFalse = ((editBeneficiaryViewModelStateLastNameEmpty) ? (true) : (false));
            }
            if ((dirtyFlags & 0x8403L) != 0) {

                    if (editBeneficiaryViewModelState != null) {
                        // read editBeneficiaryViewModel.state.countryCode
                        editBeneficiaryViewModelStateCountryCode = editBeneficiaryViewModelState.getCountryCode();
                    }


                    // read editBeneficiaryViewModel.state.countryCode != null
                    editBeneficiaryViewModelStateCountryCodeJavaLangObjectNull = (editBeneficiaryViewModelStateCountryCode) != (null);
                if((dirtyFlags & 0x8403L) != 0) {
                    if(editBeneficiaryViewModelStateCountryCodeJavaLangObjectNull) {
                            dirtyFlags |= 0x80000L;
                            dirtyFlags |= 0x8000000000L;
                    }
                    else {
                            dirtyFlags |= 0x40000L;
                            dirtyFlags |= 0x4000000000L;
                    }
                }
            }
            if ((dirtyFlags & 0x8013L) != 0) {

                    if (editBeneficiaryViewModelState != null) {
                        // read editBeneficiaryViewModel.state.country
                        editBeneficiaryViewModelStateCountry = editBeneficiaryViewModelState.getCountry();
                    }
            }
            if ((dirtyFlags & 0x800bL) != 0) {

                    if (editBeneficiaryViewModelState != null) {
                        // read editBeneficiaryViewModel.state.beneficiary
                        editBeneficiaryViewModelStateBeneficiary = editBeneficiaryViewModelState.getBeneficiary();
                    }


                    if (editBeneficiaryViewModelStateBeneficiary != null) {
                        // read editBeneficiaryViewModel.state.beneficiary.country
                        editBeneficiaryViewModelStateBeneficiaryCountry = editBeneficiaryViewModelStateBeneficiary.getCountry();
                        // read editBeneficiaryViewModel.state.beneficiary.beneficiaryType
                        editBeneficiaryViewModelStateBeneficiaryBeneficiaryType = editBeneficiaryViewModelStateBeneficiary.getBeneficiaryType();
                        // read editBeneficiaryViewModel.state.beneficiary.beneficiaryPictureUrl
                        editBeneficiaryViewModelStateBeneficiaryBeneficiaryPictureUrl = editBeneficiaryViewModelStateBeneficiary.getBeneficiaryPictureUrl();
                        // read editBeneficiaryViewModel.state.beneficiary.fullName()
                        editBeneficiaryViewModelStateBeneficiaryFullName = editBeneficiaryViewModelStateBeneficiary.fullName();
                        // read editBeneficiaryViewModel.state.beneficiary.currency
                        editBeneficiaryViewModelStateBeneficiaryCurrency = editBeneficiaryViewModelStateBeneficiary.getCurrency();
                        // read editBeneficiaryViewModel.state.beneficiary.bankName
                        editBeneficiaryViewModelStateBeneficiaryBankName = editBeneficiaryViewModelStateBeneficiary.getBankName();
                        // read editBeneficiaryViewModel.state.beneficiary.identifierCode1
                        editBeneficiaryViewModelStateBeneficiaryIdentifierCode1 = editBeneficiaryViewModelStateBeneficiary.getIdentifierCode1();
                        // read editBeneficiaryViewModel.state.beneficiary.branchName
                        editBeneficiaryViewModelStateBeneficiaryBranchName = editBeneficiaryViewModelStateBeneficiary.getBranchName();
                    }


                    if (editBeneficiaryViewModelStateBeneficiaryBeneficiaryType != null) {
                        // read editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("RMT")
                        editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringRMT = editBeneficiaryViewModelStateBeneficiaryBeneficiaryType.equals("RMT");
                        // read editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("SWIFT")
                        editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringSWIFT = editBeneficiaryViewModelStateBeneficiaryBeneficiaryType.equals("SWIFT");
                        // read editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("UAEFTS")
                        editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringUAEFTS = editBeneficiaryViewModelStateBeneficiaryBeneficiaryType.equals("UAEFTS");
                    }
                if((dirtyFlags & 0x800bL) != 0) {
                    if(editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringRMT) {
                            dirtyFlags |= 0x20000000000L;
                    }
                    else {
                            dirtyFlags |= 0x10000000000L;
                    }
                }
                if((dirtyFlags & 0x800bL) != 0) {
                    if(editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringSWIFT) {
                            dirtyFlags_1 |= 0x200L;
                    }
                    else {
                            dirtyFlags_1 |= 0x100L;
                    }
                }
                if((dirtyFlags & 0x800bL) != 0) {
                    if(editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringUAEFTS) {
                            dirtyFlags_1 |= 0x2L;
                    }
                    else {
                            dirtyFlags_1 |= 0x1L;
                    }
                }
                    // read editBeneficiaryViewModel.state.beneficiary.bankName != null
                    editBeneficiaryViewModelStateBeneficiaryBankNameJavaLangObjectNull = (editBeneficiaryViewModelStateBeneficiaryBankName) != (null);
                    // read ("ID Code: ") + (editBeneficiaryViewModel.state.beneficiary.identifierCode1)
                    javaLangStringIDCodeEditBeneficiaryViewModelStateBeneficiaryIdentifierCode1 = ("ID Code: ") + (editBeneficiaryViewModelStateBeneficiaryIdentifierCode1);
                if((dirtyFlags & 0x800bL) != 0) {
                    if(editBeneficiaryViewModelStateBeneficiaryBankNameJavaLangObjectNull) {
                            dirtyFlags |= 0x200000000L;
                    }
                    else {
                            dirtyFlags |= 0x100000000L;
                    }
                }
                    if (editBeneficiaryViewModelStateBeneficiaryIdentifierCode1 != null) {
                        // read editBeneficiaryViewModel.state.beneficiary.identifierCode1.length()
                        editBeneficiaryViewModelStateBeneficiaryIdentifierCode1Length = editBeneficiaryViewModelStateBeneficiaryIdentifierCode1.length();
                    }


                    // read editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("SWIFT") ? View.VISIBLE : View.GONE
                    editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringSWIFTViewVISIBLEViewGONE = ((editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringSWIFT) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
                    // read editBeneficiaryViewModel.state.beneficiary.identifierCode1.length() > 0
                    editBeneficiaryViewModelStateBeneficiaryIdentifierCode1LengthInt0 = (editBeneficiaryViewModelStateBeneficiaryIdentifierCode1Length) > (0);
                if((dirtyFlags & 0x800bL) != 0) {
                    if(editBeneficiaryViewModelStateBeneficiaryIdentifierCode1LengthInt0) {
                            dirtyFlags |= 0x8000000000000L;
                    }
                    else {
                            dirtyFlags |= 0x4000000000000L;
                    }
                }


                    // read editBeneficiaryViewModel.state.beneficiary.identifierCode1.length() > 0 ? View.VISIBLE : View.GONE
                    editBeneficiaryViewModelStateBeneficiaryIdentifierCode1LengthInt0ViewVISIBLEViewGONE = ((editBeneficiaryViewModelStateBeneficiaryIdentifierCode1LengthInt0) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
            }
        }
        // batch finished

        if ((dirtyFlags & 0x800bL) != 0) {

                // read editBeneficiaryViewModel.state.beneficiary.bankName != null ? editBeneficiaryViewModel.state.beneficiary.bankName : " "
                editBeneficiaryViewModelStateBeneficiaryBankNameJavaLangObjectNullEditBeneficiaryViewModelStateBeneficiaryBankNameJavaLangString = ((editBeneficiaryViewModelStateBeneficiaryBankNameJavaLangObjectNull) ? (editBeneficiaryViewModelStateBeneficiaryBankName) : (" "));
                // read editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("RMT") ? true : editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("SWIFT")
                editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringRMTBooleanTrueEditBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringSWIFT = ((editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringRMT) ? (true) : (editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringSWIFT));
            if((dirtyFlags & 0x800bL) != 0) {
                if(editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringRMTBooleanTrueEditBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringSWIFT) {
                        dirtyFlags |= 0x800000L;
                }
                else {
                        dirtyFlags |= 0x400000L;
                }
            }


                // read editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("RMT") ? true : editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("SWIFT") ? View.VISIBLE : View.GONE
                editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringRMTBooleanTrueEditBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringSWIFTViewVISIBLEViewGONE = ((editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringRMTBooleanTrueEditBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringSWIFT) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
        }
        if ((dirtyFlags_1 & 0x1L) != 0) {

                if (editBeneficiaryViewModelStateBeneficiaryBeneficiaryType != null) {
                    // read editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("DOMESTIC")
                    editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringDOMESTIC = editBeneficiaryViewModelStateBeneficiaryBeneficiaryType.equals("DOMESTIC");
                }
        }
        if ((dirtyFlags & 0x8403L) != 0) {

                // read editBeneficiaryViewModel.state.countryCode != null ? editBeneficiaryViewModel.state.countryCode : " "
                editBeneficiaryViewModelStateCountryCodeJavaLangObjectNullEditBeneficiaryViewModelStateCountryCodeJavaLangString = ((editBeneficiaryViewModelStateCountryCodeJavaLangObjectNull) ? (editBeneficiaryViewModelStateCountryCode) : (" "));
        }
        if ((dirtyFlags & 0x80000L) != 0) {

                // read PhoneNumberUtilsKt.getCountryCodeForRegion(editBeneficiaryViewModel.state.countryCode)
                phoneNumberUtilsKtGetCountryCodeForRegionEditBeneficiaryViewModelStateCountryCode = co.yap.yapcore.helpers.PhoneNumberUtilsKt.getCountryCodeForRegion(editBeneficiaryViewModelStateCountryCode);
        }

        if ((dirtyFlags & 0x8403L) != 0) {

                // read editBeneficiaryViewModel.state.countryCode != null ? PhoneNumberUtilsKt.getCountryCodeForRegion(editBeneficiaryViewModel.state.countryCode) : " "
                editBeneficiaryViewModelStateCountryCodeJavaLangObjectNullPhoneNumberUtilsKtGetCountryCodeForRegionEditBeneficiaryViewModelStateCountryCodeJavaLangString = ((editBeneficiaryViewModelStateCountryCodeJavaLangObjectNull) ? (phoneNumberUtilsKtGetCountryCodeForRegionEditBeneficiaryViewModelStateCountryCode) : (" "));
        }
        if ((dirtyFlags & 0x800bL) != 0) {

                // read editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("UAEFTS") ? true : editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("DOMESTIC")
                editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringUAEFTSBooleanTrueEditBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringDOMESTIC = ((editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringUAEFTS) ? (true) : (editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringDOMESTIC));
            if((dirtyFlags & 0x800bL) != 0) {
                if(editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringUAEFTSBooleanTrueEditBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringDOMESTIC) {
                        dirtyFlags |= 0x20000000L;
                        dirtyFlags |= 0x2000000000000000L;
                }
                else {
                        dirtyFlags |= 0x10000000L;
                        dirtyFlags |= 0x1000000000000000L;
                }
            }


                // read editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("UAEFTS") ? true : editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("DOMESTIC") ? View.GONE : View.VISIBLE
                editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringUAEFTSBooleanTrueEditBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringDOMESTICViewGONEViewVISIBLE = ((editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringUAEFTSBooleanTrueEditBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringDOMESTIC) ? (android.view.View.GONE) : (android.view.View.VISIBLE));
                // read editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("UAEFTS") ? true : editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("DOMESTIC") ? View.VISIBLE : View.GONE
                editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringUAEFTSBooleanTrueEditBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringDOMESTICViewVISIBLEViewGONE = ((editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringUAEFTSBooleanTrueEditBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringDOMESTIC) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
        }
        // batch finished
        if ((dirtyFlags & 0x8000L) != 0) {
            // api target 1

            this.confirmButton.setOnClickListener(mCallback13);
            co.yap.yapcore.binders.UIBinder.setText(this.confirmButton, co.yap.translation.Strings.screen_beneficiary_overview_button_confirm);
            co.yap.yapcore.binders.UIBinder.setEditTextEditable(this.etAccountNumber, false);
            co.yap.yapcore.binders.UIBinder.maskIbanNo(this.etAccountNumber, "#### #### #### #### #### #### ####");
            co.yap.yapcore.binders.UIBinder.setEditTextEditable(this.etAccountNumberRMT, false);
            co.yap.yapcore.binders.UIBinder.maskIbanNo(this.etAccountNumberRMT, "#### #### #### #### #### #### ####");
            co.yap.yapcore.binders.UIBinder.setEditTextEditable(this.etBankREquiredFieldCode, false);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etBankREquiredFieldCode, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etBankREquiredFieldCodeandroidTextAttrChanged);
            co.yap.yapcore.binders.UIBinder.setEditTextEditable(this.etFirstName, false);
            co.yap.yapcore.binders.UIBinder.setHint(this.etFirstName, co.yap.translation.Strings.screen_add_beneficiary_detail_input_text_first_name_hint);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etFirstName, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etFirstNameandroidTextAttrChanged);
            co.yap.yapcore.binders.UIBinder.setEditTextEditable(this.etLastName, false);
            co.yap.yapcore.binders.UIBinder.setHint(this.etLastName, co.yap.translation.Strings.screen_add_beneficiary_detail_input_text_last_name_hint);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etLastName, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etLastNameandroidTextAttrChanged);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etPhoneNumber, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etPhoneNumberandroidTextAttrChanged);
            co.yap.yapcore.binders.UIBinder.setEditTextEditable(this.etSwiftCode, false);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etSwiftCode, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etSwiftCodeandroidTextAttrChanged);
            co.yap.yapcore.binders.UIBinder.setHint(this.etnickName, co.yap.translation.Strings.screen_add_beneficiary_detail_input_text_nick_name_hint);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etnickName, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etnickNameandroidTextAttrChanged);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView14, co.yap.translation.Strings.screen_add_beneficiary_detail_display_text_currency);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView18, co.yap.translation.Strings.screen_add_beneficiary_detail_display_text_transfer_nick_name);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView20, co.yap.translation.Strings.screen_add_beneficiary_detail_display_text_transfer_first_name);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView22, co.yap.translation.Strings.screen_add_beneficiary_detail_display_text_transfer_last_name);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView3, co.yap.translation.Strings.screen_beneficiary_overview_display_text_title);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView35, co.yap.translation.Strings.screen_beneficiary_overview_display_text_swift_code);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView37, co.yap.translation.Strings.screen_beneficiary_overview_display_text_field_code);
            this.tbBtnBack.setOnClickListener(mCallback11);
            this.tvChangeCurrency.setOnClickListener(mCallback12);
        }
        if ((dirtyFlags & 0x8803L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etAccountIban, editBeneficiaryViewModelStateNeedIbanBooleanFalseEtAccountIbanAndroidStringScreenEditBeneficiaryDisplayTextAccountNumberEtAccountIbanAndroidStringScreenBeneficiaryOverviewDisplayTextAccountNumberIban);
            co.yap.yapcore.binders.UIBinder.setText(this.etAccountIban, editBeneficiaryViewModelStateNeedIbanBooleanFalseStringsScreenEditBeneficiaryDisplayTextAccountNumberStringsScreenBeneficiaryOverviewDisplayTextAccountNumberIban);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView26, editBeneficiaryViewModelStateNeedIbanBooleanFalseMboundView26AndroidStringScreenEditBeneficiaryDisplayTextAccountNumberMboundView26AndroidStringScreenBeneficiaryOverviewDisplayTextAccountNumberIban);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView26, editBeneficiaryViewModelStateNeedIbanBooleanFalseStringsScreenEditBeneficiaryDisplayTextAccountNumberStringsScreenBeneficiaryOverviewDisplayTextAccountNumberIban);
        }
        if ((dirtyFlags & 0x800bL) != 0) {
            // api target 1

            this.etAccountIban.setVisibility(editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringRMTBooleanTrueEditBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringSWIFTViewVISIBLEViewGONE);
            this.etAccountNumber.setVisibility(editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringRMTBooleanTrueEditBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringSWIFTViewVISIBLEViewGONE);
            this.etAccountNumberRMT.setVisibility(editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringUAEFTSBooleanTrueEditBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringDOMESTICViewVISIBLEViewGONE);
            this.etPhoneNumber.setVisibility(editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringUAEFTSBooleanTrueEditBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringDOMESTICViewGONEViewVISIBLE);
            this.etSwiftCode.setVisibility(editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringSWIFTViewVISIBLEViewGONE);
            co.yap.yapcore.helpers.ImageBinding.setIsoCountryDrawable(this.imgFlag, editBeneficiaryViewModelStateBeneficiaryCountry);
            co.yap.yapcore.helpers.ImageBinding.loadAvatar(this.imgProfile, editBeneficiaryViewModelStateBeneficiaryBeneficiaryPictureUrl, editBeneficiaryViewModelStateBeneficiaryFullName);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.labelCardType, editBeneficiaryViewModelStateBeneficiaryCurrency);
            this.llBankDetail.setVisibility(editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringRMTBooleanTrueEditBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringSWIFTViewVISIBLEViewGONE);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView11, editBeneficiaryViewModelStateBeneficiaryFullName);
            co.yap.yapcore.helpers.ImageBinding.setIsoCountryDrawable(this.mboundView13, editBeneficiaryViewModelStateBeneficiaryCountry);
            this.mboundView14.setVisibility(editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringRMTBooleanTrueEditBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringSWIFTViewVISIBLEViewGONE);
            this.mboundView15.setVisibility(editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringRMTBooleanTrueEditBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringSWIFTViewVISIBLEViewGONE);
            this.mboundView24.setVisibility(editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringUAEFTSBooleanTrueEditBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringDOMESTICViewGONEViewVISIBLE);
            this.mboundView26.setVisibility(editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringUAEFTSBooleanTrueEditBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringDOMESTICViewVISIBLEViewGONE);
            co.yap.yapcore.helpers.ImageBinding.loadAvatar(this.mboundView29, " ", editBeneficiaryViewModelStateBeneficiaryBankNameJavaLangObjectNullEditBeneficiaryViewModelStateBeneficiaryBankNameJavaLangString);
            this.mboundView35.setVisibility(editBeneficiaryViewModelStateBeneficiaryBeneficiaryTypeEqualsJavaLangStringSWIFTViewVISIBLEViewGONE);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvBankAddress, editBeneficiaryViewModelStateBeneficiaryBranchName);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvBankName, editBeneficiaryViewModelStateBeneficiaryBankName);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvIdCode, javaLangStringIDCodeEditBeneficiaryViewModelStateBeneficiaryIdentifierCode1);
            this.tvIdCode.setVisibility(editBeneficiaryViewModelStateBeneficiaryIdentifierCode1LengthInt0ViewVISIBLEViewGONE);
        }
        if ((dirtyFlags & 0x9003L) != 0) {
            // api target 1

            co.yap.yapcore.binders.UIBinder.setSelectedTextColor(this.etAccountIban, editBeneficiaryViewModelStateAccountNumberEmptyBooleanTrueBooleanFalse);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etAccountNumber, editBeneficiaryViewModelStateAccountNumber);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etAccountNumberRMT, editBeneficiaryViewModelStateAccountNumber);
            co.yap.yapcore.binders.UIBinder.setSelectedTextColor(this.mboundView26, editBeneficiaryViewModelStateAccountNumberEmptyBooleanTrueBooleanFalse);
        }
        if ((dirtyFlags & 0xc003L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etBankREquiredFieldCode, editBeneficiaryViewModelStateCountryBankRequirementFieldCode);
            co.yap.yapcore.binders.UIBinder.setSelectedTextColor(this.mboundView37, editBeneficiaryViewModelStateCountryBankRequirementFieldCodeEmptyBooleanTrueBooleanFalse);
        }
        if ((dirtyFlags & 0x8083L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etFirstName, editBeneficiaryViewModelStateFirstName);
            co.yap.yapcore.binders.UIBinder.setSelectedTextColor(this.mboundView20, editBeneficiaryViewModelStateFirstNameEmptyBooleanTrueBooleanFalse);
        }
        if ((dirtyFlags & 0x8103L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etLastName, editBeneficiaryViewModelStateLastName);
            co.yap.yapcore.binders.UIBinder.setSelectedTextColor(this.mboundView22, editBeneficiaryViewModelStateLastNameEmptyBooleanTrueBooleanFalse);
        }
        if ((dirtyFlags & 0x8203L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etPhoneNumber, editBeneficiaryViewModelStatePhoneNumber);
            co.yap.yapcore.binders.UIBinder.setSelectedTextColor(this.mboundView24, editBeneficiaryViewModelStatePhoneNumberEmptyBooleanTrueBooleanFalse);
        }
        if ((dirtyFlags & 0x8403L) != 0) {
            // api target 1

            co.yap.yapcore.helpers.ImageBinding.setPhonePrefix(this.etPhoneNumber, editBeneficiaryViewModelStateCountryCodeJavaLangObjectNullPhoneNumberUtilsKtGetCountryCodeForRegionEditBeneficiaryViewModelStateCountryCodeJavaLangString, editBeneficiaryViewModelStateCountryCodeJavaLangObjectNullEditBeneficiaryViewModelStateCountryCodeJavaLangString);
        }
        if ((dirtyFlags & 0xa003L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etSwiftCode, editBeneficiaryViewModelStateSwiftCode);
            co.yap.yapcore.binders.UIBinder.setSelectedTextColor(this.mboundView35, editBeneficiaryViewModelStateSwiftCodeEmptyBooleanTrueBooleanFalse);
        }
        if ((dirtyFlags & 0x8043L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etnickName, editBeneficiaryViewModelStateNickName);
            co.yap.yapcore.binders.UIBinder.setSelectedTextColor(this.mboundView18, editBeneficiaryViewModelStateNickNameEmptyBooleanTrueBooleanFalse);
        }
        if ((dirtyFlags & 0x8023L) != 0) {
            // api target 1

            androidx.databinding.adapters.ImageViewBindingAdapter.setImageDrawable(this.imgBankTransfer, editBeneficiaryViewModelStateTransferTypeEqualsJavaLangStringCASHPAYOUTImgBankTransferAndroidDrawableIcCashImgBankTransferAndroidDrawableIcBank);
            androidx.databinding.adapters.ImageViewBindingAdapter.setImageDrawable(this.mboundView12, editBeneficiaryViewModelStateTransferTypeEqualsJavaLangStringCASHPAYOUTMboundView12AndroidDrawableIcCashMboundView12AndroidDrawableIcBank);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView24, editBeneficiaryViewModelStateTransferTypeEqualsJavaLangStringCASHPAYOUTStringsScreenEditBeneficiaryDisplayTextPhoneNumberStringsScreenAddBeneficiaryDetailDisplayTextTransferPhone);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView8, editBeneficiaryViewModelStateTransferTypeEqualsJavaLangStringCASHPAYOUTMboundView8AndroidStringScreenAddBeneficiaryDisplayTextTransferTypeCashPickupMboundView8AndroidStringScreenAddBeneficiaryDisplayTextTransferTypeBank);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView8, editBeneficiaryViewModelStateTransferTypeEqualsJavaLangStringCASHPAYOUTStringsScreenAddBeneficiaryDisplayTextTransferTypeCashPickupStringsScreenAddBeneficiaryDisplayTextTransferTypeBank);
        }
        if ((dirtyFlags & 0x8007L) != 0) {
            // api target 1

            this.mboundView3.setVisibility(editBeneficiaryViewModelStateNeedOverViewViewVISIBLEViewGONE);
            this.mboundView4.setVisibility(editBeneficiaryViewModelStateNeedOverViewViewVISIBLEViewGONE);
            this.mboundView9.setVisibility(editBeneficiaryViewModelStateNeedOverViewViewGONEViewVISIBLE);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvTitle, editBeneficiaryViewModelStateNeedOverViewTvTitleAndroidStringScreenAddBeneficiaryDisplayTextTitleTvTitleAndroidStringScreenEditBeneficiaryDisplayTextTitle);
            co.yap.yapcore.binders.UIBinder.setText(this.tvTitle, editBeneficiaryViewModelStateNeedOverViewStringsScreenAddBeneficiaryDisplayTextTitleStringsScreenEditBeneficiaryDisplayTextTitle);
        }
        if ((dirtyFlags & 0x8013L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView6, editBeneficiaryViewModelStateCountry);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 3: {
                // localize variables for thread safety
                // editBeneficiaryViewModel != null
                boolean editBeneficiaryViewModelJavaLangObjectNull = false;
                // editBeneficiaryViewModel
                co.yap.sendMoney.editbeneficiary.viewmodel.EditBeneficiaryViewModel editBeneficiaryViewModel = mEditBeneficiaryViewModel;
                // v.id
                int callbackArg0Id = 0;



                editBeneficiaryViewModelJavaLangObjectNull = (editBeneficiaryViewModel) != (null);
                if (editBeneficiaryViewModelJavaLangObjectNull) {


                    if ((callbackArg_0) != (null)) {


                        callbackArg0Id = callbackArg_0.getId();

                        editBeneficiaryViewModel.handlePressOnConfirm(callbackArg0Id);
                    }
                }
                break;
            }
            case 1: {
                // localize variables for thread safety
                // editBeneficiaryViewModel != null
                boolean editBeneficiaryViewModelJavaLangObjectNull = false;
                // editBeneficiaryViewModel
                co.yap.sendMoney.editbeneficiary.viewmodel.EditBeneficiaryViewModel editBeneficiaryViewModel = mEditBeneficiaryViewModel;
                // v.id
                int callbackArg0Id = 0;



                editBeneficiaryViewModelJavaLangObjectNull = (editBeneficiaryViewModel) != (null);
                if (editBeneficiaryViewModelJavaLangObjectNull) {


                    if ((callbackArg_0) != (null)) {


                        callbackArg0Id = callbackArg_0.getId();

                        editBeneficiaryViewModel.handlePressOnConfirm(callbackArg0Id);
                    }
                }
                break;
            }
            case 2: {
                // localize variables for thread safety
                // editBeneficiaryViewModel != null
                boolean editBeneficiaryViewModelJavaLangObjectNull = false;
                // editBeneficiaryViewModel
                co.yap.sendMoney.editbeneficiary.viewmodel.EditBeneficiaryViewModel editBeneficiaryViewModel = mEditBeneficiaryViewModel;
                // v.id
                int callbackArg0Id = 0;



                editBeneficiaryViewModelJavaLangObjectNull = (editBeneficiaryViewModel) != (null);
                if (editBeneficiaryViewModelJavaLangObjectNull) {


                    if ((callbackArg_0) != (null)) {


                        callbackArg0Id = callbackArg_0.getId();

                        editBeneficiaryViewModel.handlePressOnConfirm(callbackArg0Id);
                    }
                }
                break;
            }
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    private  long mDirtyFlags_1 = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): editBeneficiaryViewModel.state
        flag 1 (0x2L): editBeneficiaryViewModel
        flag 2 (0x3L): editBeneficiaryViewModel.state.needOverView
        flag 3 (0x4L): editBeneficiaryViewModel.state.beneficiary
        flag 4 (0x5L): editBeneficiaryViewModel.state.country
        flag 5 (0x6L): editBeneficiaryViewModel.state.transferType
        flag 6 (0x7L): editBeneficiaryViewModel.state.nickName
        flag 7 (0x8L): editBeneficiaryViewModel.state.firstName
        flag 8 (0x9L): editBeneficiaryViewModel.state.lastName
        flag 9 (0xaL): editBeneficiaryViewModel.state.phoneNumber
        flag 10 (0xbL): editBeneficiaryViewModel.state.countryCode
        flag 11 (0xcL): editBeneficiaryViewModel.state.needIban
        flag 12 (0xdL): editBeneficiaryViewModel.state.accountNumber
        flag 13 (0xeL): editBeneficiaryViewModel.state.swiftCode
        flag 14 (0xfL): editBeneficiaryViewModel.state.countryBankRequirementFieldCode
        flag 15 (0x10L): null
        flag 16 (0x11L): androidx.databinding.ViewDataBinding.safeUnbox(editBeneficiaryViewModel.state.needIban) == false ? @android:string/screen_edit_beneficiary_display_text_account_number : @android:string/screen_beneficiary_overview_display_text_account_number_iban
        flag 17 (0x12L): androidx.databinding.ViewDataBinding.safeUnbox(editBeneficiaryViewModel.state.needIban) == false ? @android:string/screen_edit_beneficiary_display_text_account_number : @android:string/screen_beneficiary_overview_display_text_account_number_iban
        flag 18 (0x13L): editBeneficiaryViewModel.state.countryCode != null ? PhoneNumberUtilsKt.getCountryCodeForRegion(editBeneficiaryViewModel.state.countryCode) : " "
        flag 19 (0x14L): editBeneficiaryViewModel.state.countryCode != null ? PhoneNumberUtilsKt.getCountryCodeForRegion(editBeneficiaryViewModel.state.countryCode) : " "
        flag 20 (0x15L): androidx.databinding.ViewDataBinding.safeUnbox(editBeneficiaryViewModel.state.needIban) == false ? @android:string/screen_edit_beneficiary_display_text_account_number : @android:string/screen_beneficiary_overview_display_text_account_number_iban
        flag 21 (0x16L): androidx.databinding.ViewDataBinding.safeUnbox(editBeneficiaryViewModel.state.needIban) == false ? @android:string/screen_edit_beneficiary_display_text_account_number : @android:string/screen_beneficiary_overview_display_text_account_number_iban
        flag 22 (0x17L): editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("RMT") ? true : editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("SWIFT") ? View.VISIBLE : View.GONE
        flag 23 (0x18L): editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("RMT") ? true : editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("SWIFT") ? View.VISIBLE : View.GONE
        flag 24 (0x19L): editBeneficiaryViewModel.state.lastName.empty ? true : false
        flag 25 (0x1aL): editBeneficiaryViewModel.state.lastName.empty ? true : false
        flag 26 (0x1bL): editBeneficiaryViewModel.state.transferType.equals("CASHPAYOUT") ? Strings.screen_edit_beneficiary_display_text_phone_number : Strings.screen_add_beneficiary_detail_display_text_transfer_phone
        flag 27 (0x1cL): editBeneficiaryViewModel.state.transferType.equals("CASHPAYOUT") ? Strings.screen_edit_beneficiary_display_text_phone_number : Strings.screen_add_beneficiary_detail_display_text_transfer_phone
        flag 28 (0x1dL): editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("UAEFTS") ? true : editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("DOMESTIC") ? View.GONE : View.VISIBLE
        flag 29 (0x1eL): editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("UAEFTS") ? true : editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("DOMESTIC") ? View.GONE : View.VISIBLE
        flag 30 (0x1fL): editBeneficiaryViewModel.state.firstName.empty ? true : false
        flag 31 (0x20L): editBeneficiaryViewModel.state.firstName.empty ? true : false
        flag 32 (0x21L): editBeneficiaryViewModel.state.beneficiary.bankName != null ? editBeneficiaryViewModel.state.beneficiary.bankName : " "
        flag 33 (0x22L): editBeneficiaryViewModel.state.beneficiary.bankName != null ? editBeneficiaryViewModel.state.beneficiary.bankName : " "
        flag 34 (0x23L): androidx.databinding.ViewDataBinding.safeUnbox(editBeneficiaryViewModel.state.needIban) == false ? Strings.screen_edit_beneficiary_display_text_account_number : Strings.screen_beneficiary_overview_display_text_account_number_iban
        flag 35 (0x24L): androidx.databinding.ViewDataBinding.safeUnbox(editBeneficiaryViewModel.state.needIban) == false ? Strings.screen_edit_beneficiary_display_text_account_number : Strings.screen_beneficiary_overview_display_text_account_number_iban
        flag 36 (0x25L): editBeneficiaryViewModel.state.transferType.equals("CASHPAYOUT") ? @android:drawable/ic_cash : @android:drawable/ic_bank
        flag 37 (0x26L): editBeneficiaryViewModel.state.transferType.equals("CASHPAYOUT") ? @android:drawable/ic_cash : @android:drawable/ic_bank
        flag 38 (0x27L): editBeneficiaryViewModel.state.countryCode != null ? editBeneficiaryViewModel.state.countryCode : " "
        flag 39 (0x28L): editBeneficiaryViewModel.state.countryCode != null ? editBeneficiaryViewModel.state.countryCode : " "
        flag 40 (0x29L): editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("RMT") ? true : editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("SWIFT")
        flag 41 (0x2aL): editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("RMT") ? true : editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("SWIFT")
        flag 42 (0x2bL): editBeneficiaryViewModel.state.accountNumber.empty ? true : false
        flag 43 (0x2cL): editBeneficiaryViewModel.state.accountNumber.empty ? true : false
        flag 44 (0x2dL): androidx.databinding.ViewDataBinding.safeUnbox(editBeneficiaryViewModel.state.needOverView) ? @android:string/screen_add_beneficiary_display_text_title : @android:string/screen_edit_beneficiary_display_text_title
        flag 45 (0x2eL): androidx.databinding.ViewDataBinding.safeUnbox(editBeneficiaryViewModel.state.needOverView) ? @android:string/screen_add_beneficiary_display_text_title : @android:string/screen_edit_beneficiary_display_text_title
        flag 46 (0x2fL): androidx.databinding.ViewDataBinding.safeUnbox(editBeneficiaryViewModel.state.needOverView) ? Strings.screen_add_beneficiary_display_text_title : Strings.screen_edit_beneficiary_display_text_title
        flag 47 (0x30L): androidx.databinding.ViewDataBinding.safeUnbox(editBeneficiaryViewModel.state.needOverView) ? Strings.screen_add_beneficiary_display_text_title : Strings.screen_edit_beneficiary_display_text_title
        flag 48 (0x31L): editBeneficiaryViewModel.state.transferType.equals("CASHPAYOUT") ? @android:string/screen_add_beneficiary_display_text_transfer_type_cash_pickup : @android:string/screen_add_beneficiary_display_text_transfer_type_bank
        flag 49 (0x32L): editBeneficiaryViewModel.state.transferType.equals("CASHPAYOUT") ? @android:string/screen_add_beneficiary_display_text_transfer_type_cash_pickup : @android:string/screen_add_beneficiary_display_text_transfer_type_bank
        flag 50 (0x33L): editBeneficiaryViewModel.state.beneficiary.identifierCode1.length() > 0 ? View.VISIBLE : View.GONE
        flag 51 (0x34L): editBeneficiaryViewModel.state.beneficiary.identifierCode1.length() > 0 ? View.VISIBLE : View.GONE
        flag 52 (0x35L): editBeneficiaryViewModel.state.swiftCode.empty ? true : false
        flag 53 (0x36L): editBeneficiaryViewModel.state.swiftCode.empty ? true : false
        flag 54 (0x37L): editBeneficiaryViewModel.state.phoneNumber.empty ? true : false
        flag 55 (0x38L): editBeneficiaryViewModel.state.phoneNumber.empty ? true : false
        flag 56 (0x39L): editBeneficiaryViewModel.state.transferType.equals("CASHPAYOUT") ? @android:drawable/ic_cash : @android:drawable/ic_bank
        flag 57 (0x3aL): editBeneficiaryViewModel.state.transferType.equals("CASHPAYOUT") ? @android:drawable/ic_cash : @android:drawable/ic_bank
        flag 58 (0x3bL): androidx.databinding.ViewDataBinding.safeUnbox(editBeneficiaryViewModel.state.needOverView) ? View.VISIBLE : View.GONE
        flag 59 (0x3cL): androidx.databinding.ViewDataBinding.safeUnbox(editBeneficiaryViewModel.state.needOverView) ? View.VISIBLE : View.GONE
        flag 60 (0x3dL): editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("UAEFTS") ? true : editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("DOMESTIC") ? View.VISIBLE : View.GONE
        flag 61 (0x3eL): editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("UAEFTS") ? true : editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("DOMESTIC") ? View.VISIBLE : View.GONE
        flag 62 (0x3fL): androidx.databinding.ViewDataBinding.safeUnbox(editBeneficiaryViewModel.state.needOverView) ? View.GONE : View.VISIBLE
        flag 63 (0x40L): androidx.databinding.ViewDataBinding.safeUnbox(editBeneficiaryViewModel.state.needOverView) ? View.GONE : View.VISIBLE
        flag 64 (0x41L): editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("UAEFTS") ? true : editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("DOMESTIC")
        flag 65 (0x42L): editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("UAEFTS") ? true : editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("DOMESTIC")
        flag 66 (0x43L): editBeneficiaryViewModel.state.nickName.empty ? true : false
        flag 67 (0x44L): editBeneficiaryViewModel.state.nickName.empty ? true : false
        flag 68 (0x45L): editBeneficiaryViewModel.state.transferType.equals("CASHPAYOUT") ? Strings.screen_add_beneficiary_display_text_transfer_type_cash_pickup : Strings.screen_add_beneficiary_display_text_transfer_type_bank
        flag 69 (0x46L): editBeneficiaryViewModel.state.transferType.equals("CASHPAYOUT") ? Strings.screen_add_beneficiary_display_text_transfer_type_cash_pickup : Strings.screen_add_beneficiary_display_text_transfer_type_bank
        flag 70 (0x47L): editBeneficiaryViewModel.state.countryBankRequirementFieldCode.empty ? true : false
        flag 71 (0x48L): editBeneficiaryViewModel.state.countryBankRequirementFieldCode.empty ? true : false
        flag 72 (0x49L): editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("SWIFT") ? View.VISIBLE : View.GONE
        flag 73 (0x4aL): editBeneficiaryViewModel.state.beneficiary.beneficiaryType.equals("SWIFT") ? View.VISIBLE : View.GONE
    flag mapping end*/
    //end
}