package co.yap.sendmoney.databinding;
import co.yap.sendmoney.R;
import co.yap.sendmoney.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentCashTransferBindingImpl extends FragmentCashTransferBinding implements co.yap.sendmoney.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tvCurrency, 12);
        sViewsWithIds.put(R.id.reasonsSpinnerCashTransfer, 13);
        sViewsWithIds.put(R.id.breakLine, 14);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback17;
    @Nullable
    private final android.view.View.OnClickListener mCallback18;
    // values
    // listeners
    // Inverse Binding Event Handlers
    private androidx.databinding.InverseBindingListener etAmountandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.state.amount
            //         is viewModel.state.setAmount((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etAmount);
            // localize variables for thread safety
            // viewModel.state
            co.yap.sendmoney.fundtransfer.states.CashTransferState viewModelState = null;
            // viewModel
            co.yap.sendmoney.fundtransfer.viewmodels.CashTransferViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.state.amount
            java.lang.String viewModelStateAmount = null;
            // viewModel.state != null
            boolean viewModelStateJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelState = viewModel.getState();

                viewModelStateJavaLangObjectNull = (viewModelState) != (null);
                if (viewModelStateJavaLangObjectNull) {




                    viewModelState.setAmount(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener etNoteandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.state.noteValue
            //         is viewModel.state.setNoteValue((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etNote);
            // localize variables for thread safety
            // viewModel.state
            co.yap.sendmoney.fundtransfer.states.CashTransferState viewModelState = null;
            // viewModel.state.noteValue
            java.lang.String viewModelStateNoteValue = null;
            // viewModel
            co.yap.sendmoney.fundtransfer.viewmodels.CashTransferViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.state != null
            boolean viewModelStateJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelState = viewModel.getState();

                viewModelStateJavaLangObjectNull = (viewModelState) != (null);
                if (viewModelStateJavaLangObjectNull) {




                    viewModelState.setNoteValue(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };

    public FragmentCashTransferBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds));
    }
    private FragmentCashTransferBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 3
            , (android.view.View) bindings[14]
            , (co.yap.widgets.CoreButton) bindings[11]
            , (android.widget.EditText) bindings[5]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[4]
            , (com.google.android.material.textfield.TextInputEditText) bindings[8]
            , (co.yap.widgets.CoreCircularImageView) bindings[1]
            , (android.widget.RelativeLayout) bindings[9]
            , (android.widget.Spinner) bindings[13]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[2]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[3]
            , (android.view.View) bindings[10]
            );
        this.btnConfirm.setTag(null);
        this.etAmount.setTag(null);
        this.etAmountLayout.setTag(null);
        this.etNote.setTag(null);
        this.ivUserImage.setTag(null);
        this.layoutReasonsSpinner.setTag(null);
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
        this.tvAvailableBalance.setTag(null);
        this.tvFee.setTag(null);
        this.tvFullName.setTag(null);
        this.tvIban.setTag(null);
        this.viewTriggerSpinnerClickReasonCash.setTag(null);
        setRootTag(root);
        // listeners
        mCallback17 = new co.yap.sendmoney.generated.callback.OnClickListener(this, 1);
        mCallback18 = new co.yap.sendmoney.generated.callback.OnClickListener(this, 2);
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
            setViewModel((co.yap.sendmoney.fundtransfer.viewmodels.CashTransferViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable co.yap.sendmoney.fundtransfer.viewmodels.CashTransferViewModel ViewModel) {
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
                return onChangeViewModelState((co.yap.sendmoney.fundtransfer.states.CashTransferState) object, fieldId);
            case 1 :
                return onChangeViewModelParentViewModelTransferData((androidx.lifecycle.MutableLiveData<co.yap.sendmoney.fundtransfer.models.TransferFundData>) object, fieldId);
            case 2 :
                return onChangeViewModelParentViewModelBeneficiary((androidx.lifecycle.MutableLiveData<co.yap.networking.customers.responsedtos.sendmoney.Beneficiary>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelState(co.yap.sendmoney.fundtransfer.states.CashTransferState ViewModelState, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        else if (fieldId == BR.amountBackground) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        else if (fieldId == BR.amount) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
            }
            return true;
        }
        else if (fieldId == BR.feeAmountSpannableString) {
            synchronized(this) {
                    mDirtyFlags |= 0x40L;
            }
            return true;
        }
        else if (fieldId == BR.availableBalanceString) {
            synchronized(this) {
                    mDirtyFlags |= 0x80L;
            }
            return true;
        }
        else if (fieldId == BR.noteValue) {
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
    private boolean onChangeViewModelParentViewModelTransferData(androidx.lifecycle.MutableLiveData<co.yap.sendmoney.fundtransfer.models.TransferFundData> ViewModelParentViewModelTransferData, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelParentViewModelBeneficiary(androidx.lifecycle.MutableLiveData<co.yap.networking.customers.responsedtos.sendmoney.Beneficiary> ViewModelParentViewModelBeneficiary, int fieldId) {
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
        boolean viewModelParentViewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringDOMESTIC = false;
        boolean viewModelStateValid = false;
        java.lang.String viewModelParentViewModelBeneficiaryBeneficiaryPictureUrl = null;
        co.yap.sendmoney.fundtransfer.states.CashTransferState viewModelState = null;
        androidx.lifecycle.MutableLiveData<co.yap.sendmoney.fundtransfer.models.TransferFundData> viewModelParentViewModelTransferData = null;
        java.lang.String viewModelStateAmount = null;
        co.yap.sendmoney.fundtransfer.interfaces.IBeneficiaryFundTransfer.ViewModel viewModelParentViewModel = null;
        boolean viewModelParentViewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringCASHPAYOUT = false;
        androidx.lifecycle.MutableLiveData<co.yap.networking.customers.responsedtos.sendmoney.Beneficiary> viewModelParentViewModelBeneficiary = null;
        java.lang.String viewModelParentViewModelBeneficiaryFullName = null;
        java.lang.Integer viewModelParentViewModelTransferDataPosition = null;
        java.lang.String viewModelParentViewModelBeneficiaryAccountNo = null;
        int viewModelParentViewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringCASHPAYOUTViewGONEViewVISIBLE = 0;
        boolean viewModelParentViewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringDOMESTICBooleanTrueViewModelParentViewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringUAEFTS = false;
        int androidxDatabindingViewDataBindingSafeUnboxViewModelParentViewModelTransferDataPosition = 0;
        java.lang.String viewModelStateNoteValue = null;
        int viewModelParentViewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringDOMESTICBooleanTrueViewModelParentViewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringUAEFTSViewVISIBLEViewGONE = 0;
        java.lang.CharSequence viewModelStateAvailableBalanceString = null;
        android.graphics.drawable.Drawable viewModelStateAmountBackground = null;
        co.yap.sendmoney.fundtransfer.models.TransferFundData viewModelParentViewModelTransferDataGetValue = null;
        boolean viewModelParentViewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringUAEFTS = false;
        co.yap.networking.customers.responsedtos.sendmoney.Beneficiary viewModelParentViewModelBeneficiaryGetValue = null;
        java.lang.String viewModelParentViewModelBeneficiaryBeneficiaryType = null;
        java.lang.CharSequence viewModelStateFeeAmountSpannableString = null;
        co.yap.sendmoney.fundtransfer.viewmodels.CashTransferViewModel viewModel = mViewModel;

        if ((dirtyFlags & 0x7ffL) != 0) {


            if ((dirtyFlags & 0x7f9L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.state
                        viewModelState = viewModel.getState();
                    }
                    updateRegistration(0, viewModelState);

                if ((dirtyFlags & 0x609L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.valid
                            viewModelStateValid = viewModelState.getValid();
                        }
                }
                if ((dirtyFlags & 0x429L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.amount
                            viewModelStateAmount = viewModelState.getAmount();
                        }
                }
                if ((dirtyFlags & 0x509L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.noteValue
                            viewModelStateNoteValue = viewModelState.getNoteValue();
                        }
                }
                if ((dirtyFlags & 0x489L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.availableBalanceString
                            viewModelStateAvailableBalanceString = viewModelState.getAvailableBalanceString();
                        }
                }
                if ((dirtyFlags & 0x419L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.amountBackground
                            viewModelStateAmountBackground = viewModelState.getAmountBackground();
                        }
                }
                if ((dirtyFlags & 0x449L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.feeAmountSpannableString
                            viewModelStateFeeAmountSpannableString = viewModelState.getFeeAmountSpannableString();
                        }
                }
            }
            if ((dirtyFlags & 0x40eL) != 0) {

                    if (viewModel != null) {
                        // read viewModel.parentViewModel
                        viewModelParentViewModel = viewModel.getParentViewModel();
                    }


                    if (viewModelParentViewModel != null) {
                        // read viewModel.parentViewModel.transferData
                        viewModelParentViewModelTransferData = viewModelParentViewModel.getTransferData();
                        // read viewModel.parentViewModel.beneficiary
                        viewModelParentViewModelBeneficiary = viewModelParentViewModel.getBeneficiary();
                    }
                    updateLiveDataRegistration(1, viewModelParentViewModelTransferData);
                    updateLiveDataRegistration(2, viewModelParentViewModelBeneficiary);


                    if (viewModelParentViewModelTransferData != null) {
                        // read viewModel.parentViewModel.transferData.getValue()
                        viewModelParentViewModelTransferDataGetValue = viewModelParentViewModelTransferData.getValue();
                    }
                    if (viewModelParentViewModelBeneficiary != null) {
                        // read viewModel.parentViewModel.beneficiary.getValue()
                        viewModelParentViewModelBeneficiaryGetValue = viewModelParentViewModelBeneficiary.getValue();
                    }


                    if (viewModelParentViewModelTransferDataGetValue != null) {
                        // read viewModel.parentViewModel.transferData.getValue().position
                        viewModelParentViewModelTransferDataPosition = viewModelParentViewModelTransferDataGetValue.getPosition();
                    }
                    if (viewModelParentViewModelBeneficiaryGetValue != null) {
                        // read viewModel.parentViewModel.beneficiary.getValue().beneficiaryPictureUrl
                        viewModelParentViewModelBeneficiaryBeneficiaryPictureUrl = viewModelParentViewModelBeneficiaryGetValue.getBeneficiaryPictureUrl();
                        // read viewModel.parentViewModel.beneficiary.getValue().fullName
                        viewModelParentViewModelBeneficiaryFullName = viewModelParentViewModelBeneficiaryGetValue.fullName();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.parentViewModel.transferData.getValue().position)
                    androidxDatabindingViewDataBindingSafeUnboxViewModelParentViewModelTransferDataPosition = androidx.databinding.ViewDataBinding.safeUnbox(viewModelParentViewModelTransferDataPosition);
                if ((dirtyFlags & 0x40cL) != 0) {

                        if (viewModelParentViewModelBeneficiaryGetValue != null) {
                            // read viewModel.parentViewModel.beneficiary.getValue().accountNo
                            viewModelParentViewModelBeneficiaryAccountNo = viewModelParentViewModelBeneficiaryGetValue.getAccountNo();
                            // read viewModel.parentViewModel.beneficiary.getValue().beneficiaryType
                            viewModelParentViewModelBeneficiaryBeneficiaryType = viewModelParentViewModelBeneficiaryGetValue.getBeneficiaryType();
                        }


                        if (viewModelParentViewModelBeneficiaryBeneficiaryType != null) {
                            // read viewModel.parentViewModel.beneficiary.getValue().beneficiaryType.equals("DOMESTIC")
                            viewModelParentViewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringDOMESTIC = viewModelParentViewModelBeneficiaryBeneficiaryType.equals("DOMESTIC");
                            // read viewModel.parentViewModel.beneficiary.getValue().beneficiaryType.equals("CASHPAYOUT")
                            viewModelParentViewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringCASHPAYOUT = viewModelParentViewModelBeneficiaryBeneficiaryType.equals("CASHPAYOUT");
                        }
                    if((dirtyFlags & 0x40cL) != 0) {
                        if(viewModelParentViewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringDOMESTIC) {
                                dirtyFlags |= 0x4000L;
                        }
                        else {
                                dirtyFlags |= 0x2000L;
                        }
                    }
                    if((dirtyFlags & 0x40cL) != 0) {
                        if(viewModelParentViewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringCASHPAYOUT) {
                                dirtyFlags |= 0x1000L;
                        }
                        else {
                                dirtyFlags |= 0x800L;
                        }
                    }


                        // read viewModel.parentViewModel.beneficiary.getValue().beneficiaryType.equals("CASHPAYOUT") ? View.GONE : View.VISIBLE
                        viewModelParentViewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringCASHPAYOUTViewGONEViewVISIBLE = ((viewModelParentViewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringCASHPAYOUT) ? (android.view.View.GONE) : (android.view.View.VISIBLE));
                }
            }
        }
        // batch finished

        if ((dirtyFlags & 0x2000L) != 0) {

                if (viewModelParentViewModelBeneficiaryBeneficiaryType != null) {
                    // read viewModel.parentViewModel.beneficiary.getValue().beneficiaryType.equals("UAEFTS")
                    viewModelParentViewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringUAEFTS = viewModelParentViewModelBeneficiaryBeneficiaryType.equals("UAEFTS");
                }
        }

        if ((dirtyFlags & 0x40cL) != 0) {

                // read viewModel.parentViewModel.beneficiary.getValue().beneficiaryType.equals("DOMESTIC") ? true : viewModel.parentViewModel.beneficiary.getValue().beneficiaryType.equals("UAEFTS")
                viewModelParentViewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringDOMESTICBooleanTrueViewModelParentViewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringUAEFTS = ((viewModelParentViewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringDOMESTIC) ? (true) : (viewModelParentViewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringUAEFTS));
            if((dirtyFlags & 0x40cL) != 0) {
                if(viewModelParentViewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringDOMESTICBooleanTrueViewModelParentViewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringUAEFTS) {
                        dirtyFlags |= 0x10000L;
                }
                else {
                        dirtyFlags |= 0x8000L;
                }
            }


                // read viewModel.parentViewModel.beneficiary.getValue().beneficiaryType.equals("DOMESTIC") ? true : viewModel.parentViewModel.beneficiary.getValue().beneficiaryType.equals("UAEFTS") ? View.VISIBLE : View.GONE
                viewModelParentViewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringDOMESTICBooleanTrueViewModelParentViewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringUAEFTSViewVISIBLEViewGONE = ((viewModelParentViewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringDOMESTICBooleanTrueViewModelParentViewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringUAEFTS) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
        }
        // batch finished
        if ((dirtyFlags & 0x400L) != 0) {
            // api target 1

            this.btnConfirm.setOnClickListener(mCallback18);
            co.yap.yapcore.binders.UIBinder.setText(this.btnConfirm, co.yap.translation.Strings.screen_yap_to_yap_transfer_display_text_button);
            co.yap.yapcore.binders.UIBinder.setHint(this.etAmount, co.yap.translation.Strings.common_amount_hint);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etAmount, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etAmountandroidTextAttrChanged);
            co.yap.yapcore.binders.UIBinder.setHint(this.etNote, co.yap.translation.Strings.screen_y2y_funds_transfer_display_text_note_placeholder);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etNote, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etNoteandroidTextAttrChanged);
            co.yap.yapcore.binders.UIBinder.setEditTextEditable(this.tvIban, false);
            co.yap.yapcore.binders.UIBinder.maskIbanNo(this.tvIban, "#### #### #### #### #### #### ####");
            this.viewTriggerSpinnerClickReasonCash.setOnClickListener(mCallback17);
        }
        if ((dirtyFlags & 0x609L) != 0) {
            // api target 1

            co.yap.yapcore.binders.UIBinder.setEnable(this.btnConfirm, viewModelStateValid);
        }
        if ((dirtyFlags & 0x429L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etAmount, viewModelStateAmount);
        }
        if ((dirtyFlags & 0x419L) != 0) {
            // api target 1

            androidx.databinding.adapters.ViewBindingAdapter.setBackground(this.etAmountLayout, viewModelStateAmountBackground);
        }
        if ((dirtyFlags & 0x509L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etNote, viewModelStateNoteValue);
        }
        if ((dirtyFlags & 0x40eL) != 0) {
            // api target 1

            co.yap.yapcore.helpers.ImageBinding.loadAvatar(this.ivUserImage, viewModelParentViewModelBeneficiaryBeneficiaryPictureUrl, viewModelParentViewModelBeneficiaryFullName, androidxDatabindingViewDataBindingSafeUnboxViewModelParentViewModelTransferDataPosition, "Beneficiary");
        }
        if ((dirtyFlags & 0x40cL) != 0) {
            // api target 1

            this.layoutReasonsSpinner.setVisibility(viewModelParentViewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringCASHPAYOUTViewGONEViewVISIBLE);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvFullName, viewModelParentViewModelBeneficiaryFullName);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvIban, viewModelParentViewModelBeneficiaryAccountNo);
            this.tvIban.setVisibility(viewModelParentViewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringDOMESTICBooleanTrueViewModelParentViewModelBeneficiaryBeneficiaryTypeEqualsJavaLangStringUAEFTSViewVISIBLEViewGONE);
        }
        if ((dirtyFlags & 0x489L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvAvailableBalance, viewModelStateAvailableBalanceString);
        }
        if ((dirtyFlags & 0x449L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvFee, viewModelStateFeeAmountSpannableString);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 1: {
                // localize variables for thread safety
                // viewModel
                co.yap.sendmoney.fundtransfer.viewmodels.CashTransferViewModel viewModel = mViewModel;
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
                break;
            }
            case 2: {
                // localize variables for thread safety
                // viewModel
                co.yap.sendmoney.fundtransfer.viewmodels.CashTransferViewModel viewModel = mViewModel;
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
                break;
            }
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel.state
        flag 1 (0x2L): viewModel.parentViewModel.transferData
        flag 2 (0x3L): viewModel.parentViewModel.beneficiary
        flag 3 (0x4L): viewModel
        flag 4 (0x5L): viewModel.state.amountBackground
        flag 5 (0x6L): viewModel.state.amount
        flag 6 (0x7L): viewModel.state.feeAmountSpannableString
        flag 7 (0x8L): viewModel.state.availableBalanceString
        flag 8 (0x9L): viewModel.state.noteValue
        flag 9 (0xaL): viewModel.state.valid
        flag 10 (0xbL): null
        flag 11 (0xcL): viewModel.parentViewModel.beneficiary.getValue().beneficiaryType.equals("CASHPAYOUT") ? View.GONE : View.VISIBLE
        flag 12 (0xdL): viewModel.parentViewModel.beneficiary.getValue().beneficiaryType.equals("CASHPAYOUT") ? View.GONE : View.VISIBLE
        flag 13 (0xeL): viewModel.parentViewModel.beneficiary.getValue().beneficiaryType.equals("DOMESTIC") ? true : viewModel.parentViewModel.beneficiary.getValue().beneficiaryType.equals("UAEFTS")
        flag 14 (0xfL): viewModel.parentViewModel.beneficiary.getValue().beneficiaryType.equals("DOMESTIC") ? true : viewModel.parentViewModel.beneficiary.getValue().beneficiaryType.equals("UAEFTS")
        flag 15 (0x10L): viewModel.parentViewModel.beneficiary.getValue().beneficiaryType.equals("DOMESTIC") ? true : viewModel.parentViewModel.beneficiary.getValue().beneficiaryType.equals("UAEFTS") ? View.VISIBLE : View.GONE
        flag 16 (0x11L): viewModel.parentViewModel.beneficiary.getValue().beneficiaryType.equals("DOMESTIC") ? true : viewModel.parentViewModel.beneficiary.getValue().beneficiaryType.equals("UAEFTS") ? View.VISIBLE : View.GONE
    flag mapping end*/
    //end
}