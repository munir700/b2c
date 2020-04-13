package co.yap.sendmoney.databinding;
import co.yap.sendmoney.R;
import co.yap.sendmoney.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentAddBankDetailBindingImpl extends FragmentAddBankDetailBinding implements co.yap.sendmoney.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.txtTitle, 15);
        sViewsWithIds.put(R.id.llMain, 16);
        sViewsWithIds.put(R.id.recycler_banks, 17);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    @NonNull
    private final android.widget.TextView mboundView2;
    @NonNull
    private final android.widget.TextView mboundView4;
    @NonNull
    private final android.widget.TextView mboundView6;
    @NonNull
    private final android.widget.TextView mboundView9;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback7;
    // values
    // listeners
    // Inverse Binding Event Handlers
    private androidx.databinding.InverseBindingListener etBankCityandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.state.bankCity
            //         is viewModel.state.setBankCity((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etBankCity);
            // localize variables for thread safety
            // viewModel.state.bankCity
            java.lang.String viewModelStateBankCity = null;
            // viewModel.state
            co.yap.sendmoney.addbeneficiary.states.BankDetailsState viewModelState = null;
            // viewModel
            co.yap.sendmoney.addbeneficiary.viewmodels.BankDetailsViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.state != null
            boolean viewModelStateJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelState = viewModel.getState();

                viewModelStateJavaLangObjectNull = (viewModelState) != (null);
                if (viewModelStateJavaLangObjectNull) {




                    viewModelState.setBankCity(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener etBankNameandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.state.bankName
            //         is viewModel.state.setBankName((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etBankName);
            // localize variables for thread safety
            // viewModel.state
            co.yap.sendmoney.addbeneficiary.states.BankDetailsState viewModelState = null;
            // viewModel.state.bankName
            java.lang.String viewModelStateBankName = null;
            // viewModel
            co.yap.sendmoney.addbeneficiary.viewmodels.BankDetailsViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.state != null
            boolean viewModelStateJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelState = viewModel.getState();

                viewModelStateJavaLangObjectNull = (viewModelState) != (null);
                if (viewModelStateJavaLangObjectNull) {




                    viewModelState.setBankName(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener etBranchandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.state.bankBranch
            //         is viewModel.state.setBankBranch((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etBranch);
            // localize variables for thread safety
            // viewModel.state
            co.yap.sendmoney.addbeneficiary.states.BankDetailsState viewModelState = null;
            // viewModel
            co.yap.sendmoney.addbeneficiary.viewmodels.BankDetailsViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.state.bankBranch
            java.lang.String viewModelStateBankBranch = null;
            // viewModel.state != null
            boolean viewModelStateJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelState = viewModel.getState();

                viewModelStateJavaLangObjectNull = (viewModelState) != (null);
                if (viewModelStateJavaLangObjectNull) {




                    viewModelState.setBankBranch(((java.lang.String) (callbackArg_0)));
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
            co.yap.sendmoney.addbeneficiary.states.BankDetailsState viewModelState = null;
            // viewModel.state.swiftCode
            java.lang.String viewModelStateSwiftCode = null;
            // viewModel
            co.yap.sendmoney.addbeneficiary.viewmodels.BankDetailsViewModel viewModel = mViewModel;
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

    public FragmentAddBankDetailBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 18, sIncludes, sViewsWithIds));
    }
    private FragmentAddBankDetailBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 4
            , (co.yap.widgets.CoreButton) bindings[14]
            , (android.widget.EditText) bindings[7]
            , (android.widget.EditText) bindings[3]
            , (android.widget.EditText) bindings[5]
            , (android.widget.EditText) bindings[10]
            , (android.widget.LinearLayout) bindings[1]
            , (android.widget.LinearLayout) bindings[11]
            , (android.widget.LinearLayout) bindings[16]
            , (android.widget.LinearLayout) bindings[8]
            , (androidx.recyclerview.widget.RecyclerView) bindings[12]
            , (androidx.recyclerview.widget.RecyclerView) bindings[17]
            , (android.widget.TextView) bindings[13]
            , (android.widget.TextView) bindings[15]
            );
        this.confirmButton.setTag(null);
        this.etBankCity.setTag(null);
        this.etBankName.setTag(null);
        this.etBranch.setTag(null);
        this.etSwiftCode.setTag(null);
        this.lYNonRmt.setTag(null);
        this.lYRmt.setTag(null);
        this.lySwiftCode.setTag(null);
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView2 = (android.widget.TextView) bindings[2];
        this.mboundView2.setTag(null);
        this.mboundView4 = (android.widget.TextView) bindings[4];
        this.mboundView4.setTag(null);
        this.mboundView6 = (android.widget.TextView) bindings[6];
        this.mboundView6.setTag(null);
        this.mboundView9 = (android.widget.TextView) bindings[9];
        this.mboundView9.setTag(null);
        this.recycler.setTag(null);
        this.txtLabelSmall.setTag(null);
        setRootTag(root);
        // listeners
        mCallback7 = new co.yap.sendmoney.generated.callback.OnClickListener(this, 1);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1000L;
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
            setViewModel((co.yap.sendmoney.addbeneficiary.viewmodels.BankDetailsViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable co.yap.sendmoney.addbeneficiary.viewmodels.BankDetailsViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x10L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeViewModelState((co.yap.sendmoney.addbeneficiary.states.BankDetailsState) object, fieldId);
            case 1 :
                return onChangeViewModelBankParams((androidx.databinding.ObservableField<java.util.List<co.yap.networking.customers.responsedtos.beneficiary.BankParams>>) object, fieldId);
            case 2 :
                return onChangeViewModelStateIsRmt((androidx.databinding.ObservableField<java.lang.Boolean>) object, fieldId);
            case 3 :
                return onChangeViewModelStateTxtCount((androidx.databinding.ObservableField<java.lang.String>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelState(co.yap.sendmoney.addbeneficiary.states.BankDetailsState ViewModelState, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        else if (fieldId == BR.bankName) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
            }
            return true;
        }
        else if (fieldId == BR.bankBranch) {
            synchronized(this) {
                    mDirtyFlags |= 0x40L;
            }
            return true;
        }
        else if (fieldId == BR.bankCity) {
            synchronized(this) {
                    mDirtyFlags |= 0x80L;
            }
            return true;
        }
        else if (fieldId == BR.hideSwiftSection) {
            synchronized(this) {
                    mDirtyFlags |= 0x100L;
            }
            return true;
        }
        else if (fieldId == BR.swiftCode) {
            synchronized(this) {
                    mDirtyFlags |= 0x200L;
            }
            return true;
        }
        else if (fieldId == BR.buttonText) {
            synchronized(this) {
                    mDirtyFlags |= 0x400L;
            }
            return true;
        }
        else if (fieldId == BR.valid) {
            synchronized(this) {
                    mDirtyFlags |= 0x800L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelBankParams(androidx.databinding.ObservableField<java.util.List<co.yap.networking.customers.responsedtos.beneficiary.BankParams>> ViewModelBankParams, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateIsRmt(androidx.databinding.ObservableField<java.lang.Boolean> ViewModelStateIsRmt, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateTxtCount(androidx.databinding.ObservableField<java.lang.String> ViewModelStateTxtCount, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
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
        java.lang.String viewModelStateSwiftCode = null;
        boolean viewModelStateSwiftCodeEmpty = false;
        boolean viewModelStateValid = false;
        boolean androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsRmtGet = false;
        boolean viewModelStateBankCityEmptyBooleanTrueBooleanFalse = false;
        co.yap.sendmoney.addbeneficiary.states.BankDetailsState viewModelState = null;
        boolean viewModelStateSwiftCodeEmptyBooleanTrueBooleanFalse = false;
        java.util.List<co.yap.networking.customers.responsedtos.beneficiary.BankParams> viewModelBankParamsGet = null;
        int viewModelStateIsRmtViewGONEViewVISIBLE = 0;
        java.lang.Boolean viewModelStateIsRmtGet = null;
        boolean viewModelStateBankBranchEmpty = false;
        androidx.databinding.ObservableField<java.util.List<co.yap.networking.customers.responsedtos.beneficiary.BankParams>> viewModelBankParams = null;
        int viewModelStateIsRmtViewVISIBLEViewGONE = 0;
        java.lang.String viewModelStateTxtCountGet = null;
        boolean viewModelStateBankCityEmpty = false;
        boolean viewModelStateBankNameEmptyBooleanTrueBooleanFalse = false;
        int viewModelStateHideSwiftSectionViewVISIBLEViewGONE = 0;
        int viewModelStateTxtCountEmptyViewGONEViewVISIBLE = 0;
        boolean viewModelStateHideSwiftSection = false;
        androidx.databinding.ObservableField<java.lang.Boolean> viewModelStateIsRmt = null;
        java.lang.String viewModelStateButtonText = null;
        boolean viewModelStateTxtCountEmpty = false;
        boolean viewModelStateBankBranchEmptyBooleanTrueBooleanFalse = false;
        java.lang.String viewModelStateBankName = null;
        java.lang.String viewModelStateBankBranch = null;
        java.lang.String viewModelStateBankCity = null;
        boolean viewModelStateBankNameEmpty = false;
        androidx.databinding.ObservableField<java.lang.String> viewModelStateTxtCount = null;
        co.yap.sendmoney.addbeneficiary.viewmodels.BankDetailsViewModel viewModel = mViewModel;

        if ((dirtyFlags & 0x1fffL) != 0) {


            if ((dirtyFlags & 0x1ffdL) != 0) {

                    if (viewModel != null) {
                        // read viewModel.state
                        viewModelState = viewModel.getState();
                    }
                    updateRegistration(0, viewModelState);

                if ((dirtyFlags & 0x1211L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.swiftCode
                            viewModelStateSwiftCode = viewModelState.getSwiftCode();
                        }


                        if (viewModelStateSwiftCode != null) {
                            // read viewModel.state.swiftCode.empty
                            viewModelStateSwiftCodeEmpty = viewModelStateSwiftCode.isEmpty();
                        }
                    if((dirtyFlags & 0x1211L) != 0) {
                        if(viewModelStateSwiftCodeEmpty) {
                                dirtyFlags |= 0x10000L;
                        }
                        else {
                                dirtyFlags |= 0x8000L;
                        }
                    }


                        // read viewModel.state.swiftCode.empty ? true : false
                        viewModelStateSwiftCodeEmptyBooleanTrueBooleanFalse = ((viewModelStateSwiftCodeEmpty) ? (true) : (false));
                }
                if ((dirtyFlags & 0x1811L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.valid
                            viewModelStateValid = viewModelState.getValid();
                        }
                }
                if ((dirtyFlags & 0x1111L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.hideSwiftSection
                            viewModelStateHideSwiftSection = viewModelState.getHideSwiftSection();
                        }
                    if((dirtyFlags & 0x1111L) != 0) {
                        if(viewModelStateHideSwiftSection) {
                                dirtyFlags |= 0x1000000L;
                        }
                        else {
                                dirtyFlags |= 0x800000L;
                        }
                    }


                        // read viewModel.state.hideSwiftSection ? View.VISIBLE : View.GONE
                        viewModelStateHideSwiftSectionViewVISIBLEViewGONE = ((viewModelStateHideSwiftSection) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
                }
                if ((dirtyFlags & 0x1015L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.isRmt
                            viewModelStateIsRmt = viewModelState.isRmt();
                        }
                        updateRegistration(2, viewModelStateIsRmt);


                        if (viewModelStateIsRmt != null) {
                            // read viewModel.state.isRmt.get()
                            viewModelStateIsRmtGet = viewModelStateIsRmt.get();
                        }


                        // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isRmt.get())
                        androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsRmtGet = androidx.databinding.ViewDataBinding.safeUnbox(viewModelStateIsRmtGet);
                    if((dirtyFlags & 0x1015L) != 0) {
                        if(androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsRmtGet) {
                                dirtyFlags |= 0x40000L;
                                dirtyFlags |= 0x100000L;
                        }
                        else {
                                dirtyFlags |= 0x20000L;
                                dirtyFlags |= 0x80000L;
                        }
                    }


                        // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isRmt.get()) ? View.GONE : View.VISIBLE
                        viewModelStateIsRmtViewGONEViewVISIBLE = ((androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsRmtGet) ? (android.view.View.GONE) : (android.view.View.VISIBLE));
                        // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isRmt.get()) ? View.VISIBLE : View.GONE
                        viewModelStateIsRmtViewVISIBLEViewGONE = ((androidxDatabindingViewDataBindingSafeUnboxViewModelStateIsRmtGet) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
                }
                if ((dirtyFlags & 0x1411L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.buttonText
                            viewModelStateButtonText = viewModelState.getButtonText();
                        }
                }
                if ((dirtyFlags & 0x1031L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.bankName
                            viewModelStateBankName = viewModelState.getBankName();
                        }


                        if (viewModelStateBankName != null) {
                            // read viewModel.state.bankName.empty
                            viewModelStateBankNameEmpty = viewModelStateBankName.isEmpty();
                        }
                    if((dirtyFlags & 0x1031L) != 0) {
                        if(viewModelStateBankNameEmpty) {
                                dirtyFlags |= 0x400000L;
                        }
                        else {
                                dirtyFlags |= 0x200000L;
                        }
                    }


                        // read viewModel.state.bankName.empty ? true : false
                        viewModelStateBankNameEmptyBooleanTrueBooleanFalse = ((viewModelStateBankNameEmpty) ? (true) : (false));
                }
                if ((dirtyFlags & 0x1051L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.bankBranch
                            viewModelStateBankBranch = viewModelState.getBankBranch();
                        }


                        if (viewModelStateBankBranch != null) {
                            // read viewModel.state.bankBranch.empty
                            viewModelStateBankBranchEmpty = viewModelStateBankBranch.isEmpty();
                        }
                    if((dirtyFlags & 0x1051L) != 0) {
                        if(viewModelStateBankBranchEmpty) {
                                dirtyFlags |= 0x10000000L;
                        }
                        else {
                                dirtyFlags |= 0x8000000L;
                        }
                    }


                        // read viewModel.state.bankBranch.empty ? true : false
                        viewModelStateBankBranchEmptyBooleanTrueBooleanFalse = ((viewModelStateBankBranchEmpty) ? (true) : (false));
                }
                if ((dirtyFlags & 0x1091L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.bankCity
                            viewModelStateBankCity = viewModelState.getBankCity();
                        }


                        if (viewModelStateBankCity != null) {
                            // read viewModel.state.bankCity.empty
                            viewModelStateBankCityEmpty = viewModelStateBankCity.isEmpty();
                        }
                    if((dirtyFlags & 0x1091L) != 0) {
                        if(viewModelStateBankCityEmpty) {
                                dirtyFlags |= 0x4000L;
                        }
                        else {
                                dirtyFlags |= 0x2000L;
                        }
                    }


                        // read viewModel.state.bankCity.empty ? true : false
                        viewModelStateBankCityEmptyBooleanTrueBooleanFalse = ((viewModelStateBankCityEmpty) ? (true) : (false));
                }
                if ((dirtyFlags & 0x1019L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.txtCount
                            viewModelStateTxtCount = viewModelState.getTxtCount();
                        }
                        updateRegistration(3, viewModelStateTxtCount);


                        if (viewModelStateTxtCount != null) {
                            // read viewModel.state.txtCount.get()
                            viewModelStateTxtCountGet = viewModelStateTxtCount.get();
                        }


                        if (viewModelStateTxtCountGet != null) {
                            // read viewModel.state.txtCount.get().empty
                            viewModelStateTxtCountEmpty = viewModelStateTxtCountGet.isEmpty();
                        }
                    if((dirtyFlags & 0x1019L) != 0) {
                        if(viewModelStateTxtCountEmpty) {
                                dirtyFlags |= 0x4000000L;
                        }
                        else {
                                dirtyFlags |= 0x2000000L;
                        }
                    }


                        // read viewModel.state.txtCount.get().empty ? View.GONE : View.VISIBLE
                        viewModelStateTxtCountEmptyViewGONEViewVISIBLE = ((viewModelStateTxtCountEmpty) ? (android.view.View.GONE) : (android.view.View.VISIBLE));
                }
            }
            if ((dirtyFlags & 0x1012L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.bankParams
                        viewModelBankParams = viewModel.getBankParams();
                    }
                    updateRegistration(1, viewModelBankParams);


                    if (viewModelBankParams != null) {
                        // read viewModel.bankParams.get()
                        viewModelBankParamsGet = viewModelBankParams.get();
                    }
            }
        }
        // batch finished
        if ((dirtyFlags & 0x1000L) != 0) {
            // api target 1

            this.confirmButton.setOnClickListener(mCallback7);
            co.yap.yapcore.binders.UIBinder.setHint(this.etBankCity, co.yap.translation.Strings.screen_bank_details_input_text_city_hint);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etBankCity, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etBankCityandroidTextAttrChanged);
            co.yap.yapcore.binders.UIBinder.setHint(this.etBankName, co.yap.translation.Strings.screen_bank_details_display_text_bank_name);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etBankName, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etBankNameandroidTextAttrChanged);
            co.yap.yapcore.binders.UIBinder.setHint(this.etBranch, co.yap.translation.Strings.screen_bank_details_input_text_branch_hint);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etBranch, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etBranchandroidTextAttrChanged);
            co.yap.yapcore.binders.UIBinder.setHint(this.etSwiftCode, co.yap.translation.Strings.screen_bank_details_display_text_swift_code);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etSwiftCode, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etSwiftCodeandroidTextAttrChanged);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView2, co.yap.translation.Strings.screen_bank_details_display_text_bank_name);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView4, co.yap.translation.Strings.screen_bank_details_display_text_branch_name);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView6, co.yap.translation.Strings.screen_bank_details_display_text_bank_city);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView9, co.yap.translation.Strings.screen_bank_details_display_text_swift_code);
        }
        if ((dirtyFlags & 0x1411L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.confirmButton, viewModelStateButtonText);
        }
        if ((dirtyFlags & 0x1811L) != 0) {
            // api target 1

            co.yap.yapcore.binders.UIBinder.setEnable(this.confirmButton, viewModelStateValid);
        }
        if ((dirtyFlags & 0x1091L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etBankCity, viewModelStateBankCity);
            co.yap.yapcore.binders.UIBinder.setSelectedTextColor(this.mboundView6, viewModelStateBankCityEmptyBooleanTrueBooleanFalse);
        }
        if ((dirtyFlags & 0x1031L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etBankName, viewModelStateBankName);
            co.yap.yapcore.binders.UIBinder.setSelectedTextColor(this.mboundView2, viewModelStateBankNameEmptyBooleanTrueBooleanFalse);
        }
        if ((dirtyFlags & 0x1051L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etBranch, viewModelStateBankBranch);
            co.yap.yapcore.binders.UIBinder.setSelectedTextColor(this.mboundView4, viewModelStateBankBranchEmptyBooleanTrueBooleanFalse);
        }
        if ((dirtyFlags & 0x1211L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etSwiftCode, viewModelStateSwiftCode);
            co.yap.yapcore.binders.UIBinder.setSelectedTextColor(this.mboundView9, viewModelStateSwiftCodeEmptyBooleanTrueBooleanFalse);
        }
        if ((dirtyFlags & 0x1015L) != 0) {
            // api target 1

            this.lYNonRmt.setVisibility(viewModelStateIsRmtViewGONEViewVISIBLE);
            this.lYRmt.setVisibility(viewModelStateIsRmtViewVISIBLEViewGONE);
        }
        if ((dirtyFlags & 0x1111L) != 0) {
            // api target 1

            this.lySwiftCode.setVisibility(viewModelStateHideSwiftSectionViewVISIBLEViewGONE);
        }
        if ((dirtyFlags & 0x1012L) != 0) {
            // api target 1

            co.yap.sendmoney.helper.SendMoneyBindingHelper.setAdaptorParam(this.recycler, viewModelBankParams);
        }
        if ((dirtyFlags & 0x1019L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtLabelSmall, viewModelStateTxtCountGet);
            this.txtLabelSmall.setVisibility(viewModelStateTxtCountEmptyViewGONEViewVISIBLE);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // viewModel
        co.yap.sendmoney.addbeneficiary.viewmodels.BankDetailsViewModel viewModel = mViewModel;
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
        flag 1 (0x2L): viewModel.bankParams
        flag 2 (0x3L): viewModel.state.isRmt
        flag 3 (0x4L): viewModel.state.txtCount
        flag 4 (0x5L): viewModel
        flag 5 (0x6L): viewModel.state.bankName
        flag 6 (0x7L): viewModel.state.bankBranch
        flag 7 (0x8L): viewModel.state.bankCity
        flag 8 (0x9L): viewModel.state.hideSwiftSection
        flag 9 (0xaL): viewModel.state.swiftCode
        flag 10 (0xbL): viewModel.state.buttonText
        flag 11 (0xcL): viewModel.state.valid
        flag 12 (0xdL): null
        flag 13 (0xeL): viewModel.state.bankCity.empty ? true : false
        flag 14 (0xfL): viewModel.state.bankCity.empty ? true : false
        flag 15 (0x10L): viewModel.state.swiftCode.empty ? true : false
        flag 16 (0x11L): viewModel.state.swiftCode.empty ? true : false
        flag 17 (0x12L): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isRmt.get()) ? View.GONE : View.VISIBLE
        flag 18 (0x13L): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isRmt.get()) ? View.GONE : View.VISIBLE
        flag 19 (0x14L): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isRmt.get()) ? View.VISIBLE : View.GONE
        flag 20 (0x15L): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.isRmt.get()) ? View.VISIBLE : View.GONE
        flag 21 (0x16L): viewModel.state.bankName.empty ? true : false
        flag 22 (0x17L): viewModel.state.bankName.empty ? true : false
        flag 23 (0x18L): viewModel.state.hideSwiftSection ? View.VISIBLE : View.GONE
        flag 24 (0x19L): viewModel.state.hideSwiftSection ? View.VISIBLE : View.GONE
        flag 25 (0x1aL): viewModel.state.txtCount.get().empty ? View.GONE : View.VISIBLE
        flag 26 (0x1bL): viewModel.state.txtCount.get().empty ? View.GONE : View.VISIBLE
        flag 27 (0x1cL): viewModel.state.bankBranch.empty ? true : false
        flag 28 (0x1dL): viewModel.state.bankBranch.empty ? true : false
    flag mapping end*/
    //end
}