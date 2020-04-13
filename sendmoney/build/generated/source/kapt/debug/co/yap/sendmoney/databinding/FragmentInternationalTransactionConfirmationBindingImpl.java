package co.yap.sendmoney.databinding;
import co.yap.sendmoney.R;
import co.yap.sendmoney.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentInternationalTransactionConfirmationBindingImpl extends FragmentInternationalTransactionConfirmationBinding implements co.yap.sendmoney.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.barrier, 11);
        sViewsWithIds.put(R.id.barrier2, 12);
        sViewsWithIds.put(R.id.rlProfilePic, 13);
        sViewsWithIds.put(R.id.tvDisclaimer, 14);
        sViewsWithIds.put(R.id.llData, 15);
        sViewsWithIds.put(R.id.tvIdCode, 16);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback19;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentInternationalTransactionConfirmationBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds));
    }
    private FragmentInternationalTransactionConfirmationBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 3
            , (androidx.appcompat.widget.AppCompatImageView) bindings[8]
            , (android.view.View) bindings[11]
            , (android.view.View) bindings[12]
            , (co.yap.widgets.CoreButton) bindings[10]
            , (android.widget.ImageView) bindings[3]
            , (co.yap.widgets.CoreCircularImageView) bindings[2]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[15]
            , (android.widget.RelativeLayout) bindings[13]
            , (android.widget.TextView) bindings[4]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[9]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[14]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[16]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[5]
            );
        this.appCompatImageView.setTag(null);
        this.confirmButton.setTag(null);
        this.ivAddProfilePic.setTag(null);
        this.ivUserImage.setTag(null);
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
        this.tvBeneficiaryName.setTag(null);
        this.tvCutOffTime.setTag(null);
        this.tvFeeDescription.setTag(null);
        this.tvReceivingAmount.setTag(null);
        this.tvSuccess.setTag(null);
        this.tvTransferCaption.setTag(null);
        setRootTag(root);
        // listeners
        mCallback19 = new co.yap.sendmoney.generated.callback.OnClickListener(this, 1);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x200L;
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
            setViewModel((co.yap.sendmoney.fundtransfer.viewmodels.InternationalTransactionConfirmationViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable co.yap.sendmoney.fundtransfer.viewmodels.InternationalTransactionConfirmationViewModel ViewModel) {
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
                return onChangeViewModelState((co.yap.sendmoney.fundtransfer.states.InternationalTransactionConfirmationState) object, fieldId);
            case 1 :
                return onChangeViewModelParentViewModelTransferData((androidx.lifecycle.MutableLiveData<co.yap.sendmoney.fundtransfer.models.TransferFundData>) object, fieldId);
            case 2 :
                return onChangeViewModelParentViewModelBeneficiary((androidx.lifecycle.MutableLiveData<co.yap.networking.customers.responsedtos.sendmoney.Beneficiary>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelState(co.yap.sendmoney.fundtransfer.states.InternationalTransactionConfirmationState ViewModelState, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        else if (fieldId == BR.confirmHeading) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        else if (fieldId == BR.transferDescription) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
            }
            return true;
        }
        else if (fieldId == BR.receivingAmountDescription) {
            synchronized(this) {
                    mDirtyFlags |= 0x40L;
            }
            return true;
        }
        else if (fieldId == BR.transferFeeDescription) {
            synchronized(this) {
                    mDirtyFlags |= 0x80L;
            }
            return true;
        }
        else if (fieldId == BR.cutOffTimeMsg) {
            synchronized(this) {
                    mDirtyFlags |= 0x100L;
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
        java.lang.String viewModelParentViewModelBeneficiaryCountry = null;
        java.lang.Integer viewModelParentViewModelTransferDataPosition = null;
        int viewModelStateCutOffTimeMsgJavaLangObjectNullViewVISIBLEViewGONE = 0;
        java.lang.String viewModelStateCutOffTimeMsg = null;
        java.lang.CharSequence viewModelStateTransferFeeDescription = null;
        int androidxDatabindingViewDataBindingSafeUnboxViewModelParentViewModelTransferDataPosition = 0;
        boolean viewModelStateCutOffTimeMsgJavaLangObjectNull = false;
        java.lang.String viewModelParentViewModelBeneficiaryBeneficiaryPictureUrl = null;
        java.lang.String viewModelStateConfirmHeading = null;
        co.yap.sendmoney.fundtransfer.states.InternationalTransactionConfirmationState viewModelState = null;
        java.lang.CharSequence viewModelStateTransferDescription = null;
        androidx.lifecycle.MutableLiveData<co.yap.sendmoney.fundtransfer.models.TransferFundData> viewModelParentViewModelTransferData = null;
        co.yap.sendmoney.fundtransfer.interfaces.IBeneficiaryFundTransfer.ViewModel viewModelParentViewModel = null;
        co.yap.sendmoney.fundtransfer.models.TransferFundData viewModelParentViewModelTransferDataGetValue = null;
        co.yap.networking.customers.responsedtos.sendmoney.Beneficiary viewModelParentViewModelBeneficiaryGetValue = null;
        java.lang.String viewModelParentViewModelBeneficiaryFullName = null;
        java.lang.CharSequence viewModelStateReceivingAmountDescription = null;
        androidx.lifecycle.MutableLiveData<co.yap.networking.customers.responsedtos.sendmoney.Beneficiary> viewModelParentViewModelBeneficiary = null;
        co.yap.sendmoney.fundtransfer.viewmodels.InternationalTransactionConfirmationViewModel viewModel = mViewModel;

        if ((dirtyFlags & 0x3ffL) != 0) {


            if ((dirtyFlags & 0x3f9L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.state
                        viewModelState = viewModel.getState();
                    }
                    updateRegistration(0, viewModelState);

                if ((dirtyFlags & 0x309L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.cutOffTimeMsg
                            viewModelStateCutOffTimeMsg = viewModelState.getCutOffTimeMsg();
                        }


                        // read viewModel.state.cutOffTimeMsg != null
                        viewModelStateCutOffTimeMsgJavaLangObjectNull = (viewModelStateCutOffTimeMsg) != (null);
                    if((dirtyFlags & 0x309L) != 0) {
                        if(viewModelStateCutOffTimeMsgJavaLangObjectNull) {
                                dirtyFlags |= 0x800L;
                        }
                        else {
                                dirtyFlags |= 0x400L;
                        }
                    }


                        // read viewModel.state.cutOffTimeMsg != null ? View.VISIBLE : View.GONE
                        viewModelStateCutOffTimeMsgJavaLangObjectNullViewVISIBLEViewGONE = ((viewModelStateCutOffTimeMsgJavaLangObjectNull) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
                }
                if ((dirtyFlags & 0x289L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.transferFeeDescription
                            viewModelStateTransferFeeDescription = viewModelState.getTransferFeeDescription();
                        }
                }
                if ((dirtyFlags & 0x219L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.confirmHeading
                            viewModelStateConfirmHeading = viewModelState.getConfirmHeading();
                        }
                }
                if ((dirtyFlags & 0x229L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.transferDescription
                            viewModelStateTransferDescription = viewModelState.getTransferDescription();
                        }
                }
                if ((dirtyFlags & 0x249L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.receivingAmountDescription
                            viewModelStateReceivingAmountDescription = viewModelState.getReceivingAmountDescription();
                        }
                }
            }
            if ((dirtyFlags & 0x20eL) != 0) {

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
                        // read viewModel.parentViewModel.beneficiary.getValue().fullName()
                        viewModelParentViewModelBeneficiaryFullName = viewModelParentViewModelBeneficiaryGetValue.fullName();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.parentViewModel.transferData.getValue().position)
                    androidxDatabindingViewDataBindingSafeUnboxViewModelParentViewModelTransferDataPosition = androidx.databinding.ViewDataBinding.safeUnbox(viewModelParentViewModelTransferDataPosition);
                if ((dirtyFlags & 0x20cL) != 0) {

                        if (viewModelParentViewModelBeneficiaryGetValue != null) {
                            // read viewModel.parentViewModel.beneficiary.getValue().country
                            viewModelParentViewModelBeneficiaryCountry = viewModelParentViewModelBeneficiaryGetValue.getCountry();
                        }
                }
            }
        }
        // batch finished
        if ((dirtyFlags & 0x309L) != 0) {
            // api target 1

            this.appCompatImageView.setVisibility(viewModelStateCutOffTimeMsgJavaLangObjectNullViewVISIBLEViewGONE);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvCutOffTime, viewModelStateCutOffTimeMsg);
            this.tvCutOffTime.setVisibility(viewModelStateCutOffTimeMsgJavaLangObjectNullViewVISIBLEViewGONE);
        }
        if ((dirtyFlags & 0x200L) != 0) {
            // api target 1

            this.confirmButton.setOnClickListener(mCallback19);
            co.yap.yapcore.binders.UIBinder.setEnable(this.confirmButton, true);
        }
        if ((dirtyFlags & 0x20cL) != 0) {
            // api target 1

            co.yap.yapcore.helpers.ImageBinding.setIsoCountryDrawable(this.ivAddProfilePic, viewModelParentViewModelBeneficiaryCountry);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvBeneficiaryName, viewModelParentViewModelBeneficiaryFullName);
        }
        if ((dirtyFlags & 0x20eL) != 0) {
            // api target 1

            co.yap.yapcore.helpers.ImageBinding.loadAvatar(this.ivUserImage, viewModelParentViewModelBeneficiaryBeneficiaryPictureUrl, viewModelParentViewModelBeneficiaryFullName, androidxDatabindingViewDataBindingSafeUnboxViewModelParentViewModelTransferDataPosition, "Beneficiary");
        }
        if ((dirtyFlags & 0x289L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvFeeDescription, viewModelStateTransferFeeDescription);
        }
        if ((dirtyFlags & 0x249L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvReceivingAmount, viewModelStateReceivingAmountDescription);
        }
        if ((dirtyFlags & 0x219L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvSuccess, viewModelStateConfirmHeading);
        }
        if ((dirtyFlags & 0x229L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvTransferCaption, viewModelStateTransferDescription);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // viewModel
        co.yap.sendmoney.fundtransfer.viewmodels.InternationalTransactionConfirmationViewModel viewModel = mViewModel;
        // viewModel != null
        boolean viewModelJavaLangObjectNull = false;
        // v.id
        int callbackArg0Id = 0;



        viewModelJavaLangObjectNull = (viewModel) != (null);
        if (viewModelJavaLangObjectNull) {


            if ((callbackArg_0) != (null)) {


                callbackArg0Id = callbackArg_0.getId();

                viewModel.handlePressOnButtonClick(callbackArg0Id);
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
        flag 4 (0x5L): viewModel.state.confirmHeading
        flag 5 (0x6L): viewModel.state.transferDescription
        flag 6 (0x7L): viewModel.state.receivingAmountDescription
        flag 7 (0x8L): viewModel.state.transferFeeDescription
        flag 8 (0x9L): viewModel.state.cutOffTimeMsg
        flag 9 (0xaL): null
        flag 10 (0xbL): viewModel.state.cutOffTimeMsg != null ? View.VISIBLE : View.GONE
        flag 11 (0xcL): viewModel.state.cutOffTimeMsg != null ? View.VISIBLE : View.GONE
    flag mapping end*/
    //end
}