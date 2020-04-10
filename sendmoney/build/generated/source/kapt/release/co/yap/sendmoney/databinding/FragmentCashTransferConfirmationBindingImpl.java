package co.yap.sendmoney.databinding;
import co.yap.sendmoney.R;
import co.yap.sendmoney.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentCashTransferConfirmationBindingImpl extends FragmentCashTransferConfirmationBinding implements co.yap.sendmoney.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.barrier, 8);
        sViewsWithIds.put(R.id.barrier2, 9);
        sViewsWithIds.put(R.id.tvDisclaimer, 10);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback32;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentCashTransferConfirmationBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }
    private FragmentCashTransferConfirmationBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 6
            , (androidx.appcompat.widget.AppCompatImageView) bindings[5]
            , (android.view.View) bindings[8]
            , (android.view.View) bindings[9]
            , (co.yap.widgets.CoreButton) bindings[7]
            , (co.yap.widgets.CoreCircularImageView) bindings[1]
            , (android.widget.TextView) bindings[2]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[6]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[10]
            , (android.widget.TextView) bindings[3]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[4]
            );
        this.appCompatImageView.setTag(null);
        this.confirmButton.setTag(null);
        this.ivUserImage.setTag(null);
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
        this.tvBeneficiaryName.setTag(null);
        this.tvCutOffTime.setTag(null);
        this.tvTransferCaption.setTag(null);
        this.tvTransferFee.setTag(null);
        setRootTag(root);
        // listeners
        mCallback32 = new co.yap.sendmoney.generated.callback.OnClickListener(this, 1);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x80L;
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
            setViewModel((co.yap.sendMoney.fundtransfer.viewmodels.CashTransferConfirmationViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable co.yap.sendMoney.fundtransfer.viewmodels.CashTransferConfirmationViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x40L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeViewModelStateCutOffTimeMsg((androidx.databinding.ObservableField<java.lang.String>) object, fieldId);
            case 1 :
                return onChangeViewModelStateTransferFeeDescription((androidx.databinding.ObservableField<java.lang.CharSequence>) object, fieldId);
            case 2 :
                return onChangeViewModelState((co.yap.sendMoney.fundtransfer.states.CashTransferConfirmationState) object, fieldId);
            case 3 :
                return onChangeViewModelParentViewModelTransferData((androidx.lifecycle.MutableLiveData<co.yap.sendMoney.fundtransfer.models.TransferFundData>) object, fieldId);
            case 4 :
                return onChangeViewModelStateDescription((androidx.databinding.ObservableField<java.lang.CharSequence>) object, fieldId);
            case 5 :
                return onChangeViewModelParentViewModelBeneficiary((androidx.lifecycle.MutableLiveData<co.yap.networking.customers.responsedtos.sendmoney.Beneficiary>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelStateCutOffTimeMsg(androidx.databinding.ObservableField<java.lang.String> ViewModelStateCutOffTimeMsg, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateTransferFeeDescription(androidx.databinding.ObservableField<java.lang.CharSequence> ViewModelStateTransferFeeDescription, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelState(co.yap.sendMoney.fundtransfer.states.CashTransferConfirmationState ViewModelState, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelParentViewModelTransferData(androidx.lifecycle.MutableLiveData<co.yap.sendMoney.fundtransfer.models.TransferFundData> ViewModelParentViewModelTransferData, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateDescription(androidx.databinding.ObservableField<java.lang.CharSequence> ViewModelStateDescription, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelParentViewModelBeneficiary(androidx.lifecycle.MutableLiveData<co.yap.networking.customers.responsedtos.sendmoney.Beneficiary> ViewModelParentViewModelBeneficiary, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
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
        java.lang.Integer viewModelParentViewModelTransferDataPosition = null;
        int viewModelStateCutOffTimeMsgJavaLangObjectNullViewVISIBLEViewGONE = 0;
        androidx.databinding.ObservableField<java.lang.String> viewModelStateCutOffTimeMsg = null;
        androidx.databinding.ObservableField<java.lang.CharSequence> viewModelStateTransferFeeDescription = null;
        int viewModelParentViewModelTransferDataPositionJavaLangObjectNullInt0ViewModelParentViewModelTransferDataPosition = 0;
        boolean viewModelStateCutOffTimeMsgJavaLangObjectNull = false;
        java.lang.String viewModelParentViewModelBeneficiaryBeneficiaryPictureUrlJavaLangObjectNullJavaLangStringViewModelParentViewModelBeneficiaryBeneficiaryPictureUrl = null;
        java.lang.String viewModelParentViewModelBeneficiaryBeneficiaryPictureUrl = null;
        co.yap.sendMoney.fundtransfer.states.CashTransferConfirmationState viewModelState = null;
        java.lang.String viewModelParentViewModelBeneficiaryFullNameJavaLangObjectNullJavaLangStringViewModelParentViewModelBeneficiaryFullName = null;
        androidx.lifecycle.MutableLiveData<co.yap.sendMoney.fundtransfer.models.TransferFundData> viewModelParentViewModelTransferData = null;
        co.yap.sendMoney.fundtransfer.interfaces.IBeneficiaryFundTransfer.ViewModel viewModelParentViewModel = null;
        boolean viewModelParentViewModelBeneficiaryBeneficiaryPictureUrlJavaLangObjectNull = false;
        java.lang.CharSequence viewModelStateTransferFeeDescriptionGet = null;
        co.yap.sendMoney.fundtransfer.models.TransferFundData viewModelParentViewModelTransferDataGetValue = null;
        co.yap.networking.customers.responsedtos.sendmoney.Beneficiary viewModelParentViewModelBeneficiaryGetValue = null;
        java.lang.String viewModelParentViewModelBeneficiaryFullName = null;
        androidx.databinding.ObservableField<java.lang.CharSequence> viewModelStateDescription = null;
        androidx.lifecycle.MutableLiveData<co.yap.networking.customers.responsedtos.sendmoney.Beneficiary> viewModelParentViewModelBeneficiary = null;
        java.lang.String viewModelStateCutOffTimeMsgGet = null;
        boolean viewModelParentViewModelBeneficiaryFullNameJavaLangObjectNull = false;
        java.lang.String ViewModelParentViewModelBeneficiaryFullName1 = null;
        java.lang.CharSequence viewModelStateDescriptionGet = null;
        co.yap.sendMoney.fundtransfer.viewmodels.CashTransferConfirmationViewModel viewModel = mViewModel;
        boolean viewModelParentViewModelTransferDataPositionJavaLangObjectNull = false;

        if ((dirtyFlags & 0xffL) != 0) {


            if ((dirtyFlags & 0xd7L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.state
                        viewModelState = viewModel.getState();
                    }
                    updateRegistration(2, viewModelState);

                if ((dirtyFlags & 0xc5L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.cutOffTimeMsg
                            viewModelStateCutOffTimeMsg = viewModelState.getCutOffTimeMsg();
                        }
                        updateRegistration(0, viewModelStateCutOffTimeMsg);


                        if (viewModelStateCutOffTimeMsg != null) {
                            // read viewModel.state.cutOffTimeMsg.get()
                            viewModelStateCutOffTimeMsgGet = viewModelStateCutOffTimeMsg.get();
                        }


                        // read viewModel.state.cutOffTimeMsg.get() != null
                        viewModelStateCutOffTimeMsgJavaLangObjectNull = (viewModelStateCutOffTimeMsgGet) != (null);
                    if((dirtyFlags & 0xc5L) != 0) {
                        if(viewModelStateCutOffTimeMsgJavaLangObjectNull) {
                                dirtyFlags |= 0x200L;
                        }
                        else {
                                dirtyFlags |= 0x100L;
                        }
                    }


                        // read viewModel.state.cutOffTimeMsg.get() != null ? View.VISIBLE : View.GONE
                        viewModelStateCutOffTimeMsgJavaLangObjectNullViewVISIBLEViewGONE = ((viewModelStateCutOffTimeMsgJavaLangObjectNull) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
                }
                if ((dirtyFlags & 0xc6L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.transferFeeDescription
                            viewModelStateTransferFeeDescription = viewModelState.getTransferFeeDescription();
                        }
                        updateRegistration(1, viewModelStateTransferFeeDescription);


                        if (viewModelStateTransferFeeDescription != null) {
                            // read viewModel.state.transferFeeDescription.get()
                            viewModelStateTransferFeeDescriptionGet = viewModelStateTransferFeeDescription.get();
                        }
                }
                if ((dirtyFlags & 0xd4L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.description
                            viewModelStateDescription = viewModelState.getDescription();
                        }
                        updateRegistration(4, viewModelStateDescription);


                        if (viewModelStateDescription != null) {
                            // read viewModel.state.description.get()
                            viewModelStateDescriptionGet = viewModelStateDescription.get();
                        }
                }
            }
            if ((dirtyFlags & 0xe8L) != 0) {

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
                    updateLiveDataRegistration(3, viewModelParentViewModelTransferData);
                    updateLiveDataRegistration(5, viewModelParentViewModelBeneficiary);


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


                    // read viewModel.parentViewModel.transferData.getValue().position == null
                    viewModelParentViewModelTransferDataPositionJavaLangObjectNull = (viewModelParentViewModelTransferDataPosition) == (null);
                    // read viewModel.parentViewModel.beneficiary.getValue().beneficiaryPictureUrl == null
                    viewModelParentViewModelBeneficiaryBeneficiaryPictureUrlJavaLangObjectNull = (viewModelParentViewModelBeneficiaryBeneficiaryPictureUrl) == (null);
                    // read viewModel.parentViewModel.beneficiary.getValue().fullName() == null
                    viewModelParentViewModelBeneficiaryFullNameJavaLangObjectNull = (viewModelParentViewModelBeneficiaryFullName) == (null);
                if((dirtyFlags & 0xe8L) != 0) {
                    if(viewModelParentViewModelTransferDataPositionJavaLangObjectNull) {
                            dirtyFlags |= 0x800L;
                    }
                    else {
                            dirtyFlags |= 0x400L;
                    }
                }
                if((dirtyFlags & 0xe8L) != 0) {
                    if(viewModelParentViewModelBeneficiaryBeneficiaryPictureUrlJavaLangObjectNull) {
                            dirtyFlags |= 0x2000L;
                    }
                    else {
                            dirtyFlags |= 0x1000L;
                    }
                }
                if((dirtyFlags & 0xe8L) != 0) {
                    if(viewModelParentViewModelBeneficiaryFullNameJavaLangObjectNull) {
                            dirtyFlags |= 0x8000L;
                    }
                    else {
                            dirtyFlags |= 0x4000L;
                    }
                }
                if ((dirtyFlags & 0xe0L) != 0) {

                        if (viewModelParentViewModelBeneficiaryGetValue != null) {
                            // read viewModel.parentViewModel.beneficiary.getValue().fullName
                            ViewModelParentViewModelBeneficiaryFullName1 = viewModelParentViewModelBeneficiaryGetValue.fullName();
                        }
                }
            }
        }
        // batch finished

        if ((dirtyFlags & 0xe8L) != 0) {

                // read viewModel.parentViewModel.transferData.getValue().position == null ? 0 : viewModel.parentViewModel.transferData.getValue().position
                viewModelParentViewModelTransferDataPositionJavaLangObjectNullInt0ViewModelParentViewModelTransferDataPosition = ((viewModelParentViewModelTransferDataPositionJavaLangObjectNull) ? (0) : (viewModelParentViewModelTransferDataPosition));
                // read viewModel.parentViewModel.beneficiary.getValue().beneficiaryPictureUrl == null ? " " : viewModel.parentViewModel.beneficiary.getValue().beneficiaryPictureUrl
                viewModelParentViewModelBeneficiaryBeneficiaryPictureUrlJavaLangObjectNullJavaLangStringViewModelParentViewModelBeneficiaryBeneficiaryPictureUrl = ((viewModelParentViewModelBeneficiaryBeneficiaryPictureUrlJavaLangObjectNull) ? (" ") : (viewModelParentViewModelBeneficiaryBeneficiaryPictureUrl));
                // read viewModel.parentViewModel.beneficiary.getValue().fullName() == null ? " " : viewModel.parentViewModel.beneficiary.getValue().fullName()
                viewModelParentViewModelBeneficiaryFullNameJavaLangObjectNullJavaLangStringViewModelParentViewModelBeneficiaryFullName = ((viewModelParentViewModelBeneficiaryFullNameJavaLangObjectNull) ? (" ") : (viewModelParentViewModelBeneficiaryFullName));
        }
        // batch finished
        if ((dirtyFlags & 0xc5L) != 0) {
            // api target 1

            this.appCompatImageView.setVisibility(viewModelStateCutOffTimeMsgJavaLangObjectNullViewVISIBLEViewGONE);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvCutOffTime, viewModelStateCutOffTimeMsgGet);
            this.tvCutOffTime.setVisibility(viewModelStateCutOffTimeMsgJavaLangObjectNullViewVISIBLEViewGONE);
        }
        if ((dirtyFlags & 0x80L) != 0) {
            // api target 1

            this.confirmButton.setOnClickListener(mCallback32);
            co.yap.yapcore.binders.UIBinder.setEnable(this.confirmButton, true);
        }
        if ((dirtyFlags & 0xe8L) != 0) {
            // api target 1

            co.yap.yapcore.helpers.ImageBinding.loadAvatar(this.ivUserImage, viewModelParentViewModelBeneficiaryBeneficiaryPictureUrlJavaLangObjectNullJavaLangStringViewModelParentViewModelBeneficiaryBeneficiaryPictureUrl, viewModelParentViewModelBeneficiaryFullNameJavaLangObjectNullJavaLangStringViewModelParentViewModelBeneficiaryFullName, viewModelParentViewModelTransferDataPositionJavaLangObjectNullInt0ViewModelParentViewModelTransferDataPosition, "Beneficiary");
        }
        if ((dirtyFlags & 0xe0L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvBeneficiaryName, ViewModelParentViewModelBeneficiaryFullName1);
        }
        if ((dirtyFlags & 0xd4L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvTransferCaption, viewModelStateDescriptionGet);
        }
        if ((dirtyFlags & 0xc6L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvTransferFee, viewModelStateTransferFeeDescriptionGet);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // viewModel
        co.yap.sendMoney.fundtransfer.viewmodels.CashTransferConfirmationViewModel viewModel = mViewModel;
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
        flag 0 (0x1L): viewModel.state.cutOffTimeMsg
        flag 1 (0x2L): viewModel.state.transferFeeDescription
        flag 2 (0x3L): viewModel.state
        flag 3 (0x4L): viewModel.parentViewModel.transferData
        flag 4 (0x5L): viewModel.state.description
        flag 5 (0x6L): viewModel.parentViewModel.beneficiary
        flag 6 (0x7L): viewModel
        flag 7 (0x8L): null
        flag 8 (0x9L): viewModel.state.cutOffTimeMsg.get() != null ? View.VISIBLE : View.GONE
        flag 9 (0xaL): viewModel.state.cutOffTimeMsg.get() != null ? View.VISIBLE : View.GONE
        flag 10 (0xbL): viewModel.parentViewModel.transferData.getValue().position == null ? 0 : viewModel.parentViewModel.transferData.getValue().position
        flag 11 (0xcL): viewModel.parentViewModel.transferData.getValue().position == null ? 0 : viewModel.parentViewModel.transferData.getValue().position
        flag 12 (0xdL): viewModel.parentViewModel.beneficiary.getValue().beneficiaryPictureUrl == null ? " " : viewModel.parentViewModel.beneficiary.getValue().beneficiaryPictureUrl
        flag 13 (0xeL): viewModel.parentViewModel.beneficiary.getValue().beneficiaryPictureUrl == null ? " " : viewModel.parentViewModel.beneficiary.getValue().beneficiaryPictureUrl
        flag 14 (0xfL): viewModel.parentViewModel.beneficiary.getValue().fullName() == null ? " " : viewModel.parentViewModel.beneficiary.getValue().fullName()
        flag 15 (0x10L): viewModel.parentViewModel.beneficiary.getValue().fullName() == null ? " " : viewModel.parentViewModel.beneficiary.getValue().fullName()
    flag mapping end*/
    //end
}