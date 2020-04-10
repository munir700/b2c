package co.yap.sendmoney.databinding;
import co.yap.sendmoney.R;
import co.yap.sendmoney.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentTransferSuccessBindingImpl extends FragmentTransferSuccessBinding implements co.yap.sendmoney.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.barrier, 13);
        sViewsWithIds.put(R.id.barrier2, 14);
        sViewsWithIds.put(R.id.flTransactionComplete, 15);
        sViewsWithIds.put(R.id.llData, 16);
        sViewsWithIds.put(R.id.tvIdCode, 17);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback34;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentTransferSuccessBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 18, sIncludes, sViewsWithIds));
    }
    private FragmentTransferSuccessBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 4
            , (androidx.appcompat.widget.AppCompatImageView) bindings[9]
            , (android.view.View) bindings[13]
            , (android.view.View) bindings[14]
            , (co.yap.widgets.CoreButton) bindings[12]
            , (android.widget.FrameLayout) bindings[15]
            , (android.widget.ImageView) bindings[4]
            , (co.yap.widgets.CoreCircularImageView) bindings[2]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[16]
            , (android.widget.RelativeLayout) bindings[3]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[5]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[10]
            , (android.widget.TextView) bindings[17]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[6]
            );
        this.appCompatImageView.setTag(null);
        this.confirmButton.setTag(null);
        this.ivAddProfilePic.setTag(null);
        this.ivUserImage.setTag(null);
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
        this.rlAddNewProfilePic.setTag(null);
        this.tvAmount.setTag(null);
        this.tvAvailableBalance.setTag(null);
        this.tvBeneficiaryName.setTag(null);
        this.tvCutOffTime.setTag(null);
        this.tvReferenceNumber.setTag(null);
        this.tvSuccess.setTag(null);
        this.tvTransferCaption.setTag(null);
        setRootTag(root);
        // listeners
        mCallback34 = new co.yap.sendmoney.generated.callback.OnClickListener(this, 1);
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
            setViewModel((co.yap.sendMoney.fundtransfer.viewmodels.TransferSuccessViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable co.yap.sendMoney.fundtransfer.viewmodels.TransferSuccessViewModel ViewModel) {
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
                return onChangeViewModelState((co.yap.sendMoney.fundtransfer.states.TransferSuccessState) object, fieldId);
            case 1 :
                return onChangeViewModelParentViewModelTransferData((androidx.lifecycle.MutableLiveData<co.yap.sendMoney.fundtransfer.models.TransferFundData>) object, fieldId);
            case 2 :
                return onChangeViewModelStateCutOffMessage((androidx.databinding.ObservableField<java.lang.String>) object, fieldId);
            case 3 :
                return onChangeViewModelParentViewModelBeneficiary((androidx.lifecycle.MutableLiveData<co.yap.networking.customers.responsedtos.sendmoney.Beneficiary>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelState(co.yap.sendMoney.fundtransfer.states.TransferSuccessState ViewModelState, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        else if (fieldId == BR.successHeader) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
            }
            return true;
        }
        else if (fieldId == BR.flagLayoutVisibility) {
            synchronized(this) {
                    mDirtyFlags |= 0x40L;
            }
            return true;
        }
        else if (fieldId == BR.transferAmountHeading) {
            synchronized(this) {
                    mDirtyFlags |= 0x80L;
            }
            return true;
        }
        else if (fieldId == BR.availableBalanceString) {
            synchronized(this) {
                    mDirtyFlags |= 0x100L;
            }
            return true;
        }
        else if (fieldId == BR.buttonTitle) {
            synchronized(this) {
                    mDirtyFlags |= 0x200L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelParentViewModelTransferData(androidx.lifecycle.MutableLiveData<co.yap.sendMoney.fundtransfer.models.TransferFundData> ViewModelParentViewModelTransferData, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateCutOffMessage(androidx.databinding.ObservableField<java.lang.String> ViewModelStateCutOffMessage, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelParentViewModelBeneficiary(androidx.lifecycle.MutableLiveData<co.yap.networking.customers.responsedtos.sendmoney.Beneficiary> ViewModelParentViewModelBeneficiary, int fieldId) {
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
        int viewModelParentViewModelTransferDataCutOffTimeMsgIsEmptyViewGONEViewVISIBLE = 0;
        int viewModelParentViewModelTransferDataPositionJavaLangObjectNullInt0ViewModelParentViewModelTransferDataPosition = 0;
        java.lang.Boolean viewModelStateFlagLayoutVisibility = null;
        java.lang.String viewModelParentViewModelBeneficiaryBeneficiaryPictureUrl = null;
        co.yap.sendMoney.fundtransfer.states.TransferSuccessState viewModelState = null;
        androidx.lifecycle.MutableLiveData<co.yap.sendMoney.fundtransfer.models.TransferFundData> viewModelParentViewModelTransferData = null;
        co.yap.sendMoney.fundtransfer.interfaces.IBeneficiaryFundTransfer.ViewModel viewModelParentViewModel = null;
        boolean viewModelParentViewModelTransferDataCutOffTimeMsgJavaLangObjectNull = false;
        androidx.databinding.ObservableField<java.lang.String> viewModelStateCutOffMessage = null;
        java.lang.String viewModelParentViewModelBeneficiaryFullName = null;
        java.lang.String viewModelParentViewModelTransferDataTransferAmount = null;
        androidx.lifecycle.MutableLiveData<co.yap.networking.customers.responsedtos.sendmoney.Beneficiary> viewModelParentViewModelBeneficiary = null;
        java.lang.String viewModelParentViewModelTransferDataSourceCurrencyJavaLangStringViewModelParentViewModelTransferDataTransferAmount = null;
        java.lang.String viewModelParentViewModelTransferDataSourceCurrencyJavaLangString = null;
        java.lang.String viewModelParentViewModelBeneficiaryCountry = null;
        java.lang.Integer viewModelParentViewModelTransferDataPosition = null;
        java.lang.String viewModelStateTransferAmountHeading = null;
        int viewModelParentViewModelTransferDataCutOffTimeMsgJavaLangObjectNullBooleanTrueViewModelParentViewModelTransferDataCutOffTimeMsgIsEmptyViewGONEViewVISIBLE = 0;
        java.lang.String viewModelParentViewModelTransferDataReferenceNumber = null;
        java.lang.String viewModelParentViewModelTransferDataSourceCurrency = null;
        java.lang.CharSequence viewModelStateAvailableBalanceString = null;
        java.lang.String viewModelParentViewModelTransferDataCutOffTimeMsg = null;
        java.lang.String viewModelStateButtonTitle = null;
        java.lang.String viewModelParentViewModelBeneficiaryCountryToString = null;
        java.lang.String viewModelStateCutOffMessageGet = null;
        co.yap.sendMoney.fundtransfer.models.TransferFundData viewModelParentViewModelTransferDataGetValue = null;
        co.yap.networking.customers.responsedtos.sendmoney.Beneficiary viewModelParentViewModelBeneficiaryGetValue = null;
        boolean androidxDatabindingViewDataBindingSafeUnboxViewModelStateFlagLayoutVisibility = false;
        boolean viewModelParentViewModelTransferDataCutOffTimeMsgJavaLangObjectNullBooleanTrueViewModelParentViewModelTransferDataCutOffTimeMsgIsEmpty = false;
        int viewModelStateFlagLayoutVisibilityViewVISIBLEViewGONE = 0;
        boolean viewModelParentViewModelTransferDataCutOffTimeMsgIsEmpty = false;
        java.lang.String viewModelStateSuccessHeader = null;
        co.yap.sendMoney.fundtransfer.viewmodels.TransferSuccessViewModel viewModel = mViewModel;
        boolean viewModelParentViewModelTransferDataPositionJavaLangObjectNull = false;

        if ((dirtyFlags & 0x7ffL) != 0) {


            if ((dirtyFlags & 0x7f5L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.state
                        viewModelState = viewModel.getState();
                    }
                    updateRegistration(0, viewModelState);

                if ((dirtyFlags & 0x451L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.flagLayoutVisibility
                            viewModelStateFlagLayoutVisibility = viewModelState.getFlagLayoutVisibility();
                        }


                        // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.flagLayoutVisibility)
                        androidxDatabindingViewDataBindingSafeUnboxViewModelStateFlagLayoutVisibility = androidx.databinding.ViewDataBinding.safeUnbox(viewModelStateFlagLayoutVisibility);
                    if((dirtyFlags & 0x451L) != 0) {
                        if(androidxDatabindingViewDataBindingSafeUnboxViewModelStateFlagLayoutVisibility) {
                                dirtyFlags |= 0x100000L;
                        }
                        else {
                                dirtyFlags |= 0x80000L;
                        }
                    }


                        // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.flagLayoutVisibility) ? View.VISIBLE : View.GONE
                        viewModelStateFlagLayoutVisibilityViewVISIBLEViewGONE = ((androidxDatabindingViewDataBindingSafeUnboxViewModelStateFlagLayoutVisibility) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
                }
                if ((dirtyFlags & 0x415L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.cutOffMessage
                            viewModelStateCutOffMessage = viewModelState.getCutOffMessage();
                        }
                        updateRegistration(2, viewModelStateCutOffMessage);


                        if (viewModelStateCutOffMessage != null) {
                            // read viewModel.state.cutOffMessage.get()
                            viewModelStateCutOffMessageGet = viewModelStateCutOffMessage.get();
                        }
                }
                if ((dirtyFlags & 0x491L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.transferAmountHeading
                            viewModelStateTransferAmountHeading = viewModelState.getTransferAmountHeading();
                        }
                }
                if ((dirtyFlags & 0x511L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.availableBalanceString
                            viewModelStateAvailableBalanceString = viewModelState.getAvailableBalanceString();
                        }
                }
                if ((dirtyFlags & 0x611L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.buttonTitle
                            viewModelStateButtonTitle = viewModelState.getButtonTitle();
                        }
                }
                if ((dirtyFlags & 0x431L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.successHeader
                            viewModelStateSuccessHeader = viewModelState.getSuccessHeader();
                        }
                }
            }
            if ((dirtyFlags & 0x41aL) != 0) {

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
                    updateLiveDataRegistration(3, viewModelParentViewModelBeneficiary);


                    if (viewModelParentViewModelTransferData != null) {
                        // read viewModel.parentViewModel.transferData.getValue()
                        viewModelParentViewModelTransferDataGetValue = viewModelParentViewModelTransferData.getValue();
                    }
                    if (viewModelParentViewModelBeneficiary != null) {
                        // read viewModel.parentViewModel.beneficiary.getValue()
                        viewModelParentViewModelBeneficiaryGetValue = viewModelParentViewModelBeneficiary.getValue();
                    }

                if ((dirtyFlags & 0x412L) != 0) {

                        if (viewModelParentViewModelTransferDataGetValue != null) {
                            // read viewModel.parentViewModel.transferData.getValue().transferAmount
                            viewModelParentViewModelTransferDataTransferAmount = viewModelParentViewModelTransferDataGetValue.getTransferAmount();
                            // read viewModel.parentViewModel.transferData.getValue().referenceNumber
                            viewModelParentViewModelTransferDataReferenceNumber = viewModelParentViewModelTransferDataGetValue.getReferenceNumber();
                            // read viewModel.parentViewModel.transferData.getValue().sourceCurrency
                            viewModelParentViewModelTransferDataSourceCurrency = viewModelParentViewModelTransferDataGetValue.getSourceCurrency();
                            // read viewModel.parentViewModel.transferData.getValue().cutOffTimeMsg
                            viewModelParentViewModelTransferDataCutOffTimeMsg = viewModelParentViewModelTransferDataGetValue.getCutOffTimeMsg();
                        }


                        // read (viewModel.parentViewModel.transferData.getValue().sourceCurrency) + (" ")
                        viewModelParentViewModelTransferDataSourceCurrencyJavaLangString = (viewModelParentViewModelTransferDataSourceCurrency) + (" ");
                        // read viewModel.parentViewModel.transferData.getValue().cutOffTimeMsg == null
                        viewModelParentViewModelTransferDataCutOffTimeMsgJavaLangObjectNull = (viewModelParentViewModelTransferDataCutOffTimeMsg) == (null);
                    if((dirtyFlags & 0x412L) != 0) {
                        if(viewModelParentViewModelTransferDataCutOffTimeMsgJavaLangObjectNull) {
                                dirtyFlags |= 0x40000L;
                        }
                        else {
                                dirtyFlags |= 0x20000L;
                        }
                    }
                        if (viewModelParentViewModelTransferDataCutOffTimeMsg != null) {
                            // read viewModel.parentViewModel.transferData.getValue().cutOffTimeMsg.isEmpty()
                            viewModelParentViewModelTransferDataCutOffTimeMsgIsEmpty = viewModelParentViewModelTransferDataCutOffTimeMsg.isEmpty();
                        }
                    if((dirtyFlags & 0x412L) != 0) {
                        if(viewModelParentViewModelTransferDataCutOffTimeMsgIsEmpty) {
                                dirtyFlags |= 0x1000L;
                        }
                        else {
                                dirtyFlags |= 0x800L;
                        }
                    }


                        // read ((viewModel.parentViewModel.transferData.getValue().sourceCurrency) + (" ")) + (viewModel.parentViewModel.transferData.getValue().transferAmount)
                        viewModelParentViewModelTransferDataSourceCurrencyJavaLangStringViewModelParentViewModelTransferDataTransferAmount = (viewModelParentViewModelTransferDataSourceCurrencyJavaLangString) + (viewModelParentViewModelTransferDataTransferAmount);
                        // read viewModel.parentViewModel.transferData.getValue().cutOffTimeMsg.isEmpty() ? View.GONE : View.VISIBLE
                        viewModelParentViewModelTransferDataCutOffTimeMsgIsEmptyViewGONEViewVISIBLE = ((viewModelParentViewModelTransferDataCutOffTimeMsgIsEmpty) ? (android.view.View.GONE) : (android.view.View.VISIBLE));
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
                if((dirtyFlags & 0x41aL) != 0) {
                    if(viewModelParentViewModelTransferDataPositionJavaLangObjectNull) {
                            dirtyFlags |= 0x4000L;
                    }
                    else {
                            dirtyFlags |= 0x2000L;
                    }
                }
                if ((dirtyFlags & 0x418L) != 0) {

                        if (viewModelParentViewModelBeneficiaryGetValue != null) {
                            // read viewModel.parentViewModel.beneficiary.getValue().country
                            viewModelParentViewModelBeneficiaryCountry = viewModelParentViewModelBeneficiaryGetValue.getCountry();
                        }


                        if (viewModelParentViewModelBeneficiaryCountry != null) {
                            // read viewModel.parentViewModel.beneficiary.getValue().country.toString()
                            viewModelParentViewModelBeneficiaryCountryToString = viewModelParentViewModelBeneficiaryCountry.toString();
                        }
                }
            }
        }
        // batch finished

        if ((dirtyFlags & 0x41aL) != 0) {

                // read viewModel.parentViewModel.transferData.getValue().position == null ? 0 : viewModel.parentViewModel.transferData.getValue().position
                viewModelParentViewModelTransferDataPositionJavaLangObjectNullInt0ViewModelParentViewModelTransferDataPosition = ((viewModelParentViewModelTransferDataPositionJavaLangObjectNull) ? (0) : (viewModelParentViewModelTransferDataPosition));
        }
        if ((dirtyFlags & 0x412L) != 0) {

                // read viewModel.parentViewModel.transferData.getValue().cutOffTimeMsg == null ? true : viewModel.parentViewModel.transferData.getValue().cutOffTimeMsg.isEmpty()
                viewModelParentViewModelTransferDataCutOffTimeMsgJavaLangObjectNullBooleanTrueViewModelParentViewModelTransferDataCutOffTimeMsgIsEmpty = ((viewModelParentViewModelTransferDataCutOffTimeMsgJavaLangObjectNull) ? (true) : (viewModelParentViewModelTransferDataCutOffTimeMsgIsEmpty));
            if((dirtyFlags & 0x412L) != 0) {
                if(viewModelParentViewModelTransferDataCutOffTimeMsgJavaLangObjectNullBooleanTrueViewModelParentViewModelTransferDataCutOffTimeMsgIsEmpty) {
                        dirtyFlags |= 0x10000L;
                }
                else {
                        dirtyFlags |= 0x8000L;
                }
            }


                // read viewModel.parentViewModel.transferData.getValue().cutOffTimeMsg == null ? true : viewModel.parentViewModel.transferData.getValue().cutOffTimeMsg.isEmpty() ? View.GONE : View.VISIBLE
                viewModelParentViewModelTransferDataCutOffTimeMsgJavaLangObjectNullBooleanTrueViewModelParentViewModelTransferDataCutOffTimeMsgIsEmptyViewGONEViewVISIBLE = ((viewModelParentViewModelTransferDataCutOffTimeMsgJavaLangObjectNullBooleanTrueViewModelParentViewModelTransferDataCutOffTimeMsgIsEmpty) ? (android.view.View.GONE) : (android.view.View.VISIBLE));
        }
        // batch finished
        if ((dirtyFlags & 0x412L) != 0) {
            // api target 1

            this.appCompatImageView.setVisibility(viewModelParentViewModelTransferDataCutOffTimeMsgJavaLangObjectNullBooleanTrueViewModelParentViewModelTransferDataCutOffTimeMsgIsEmptyViewGONEViewVISIBLE);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvAmount, viewModelParentViewModelTransferDataSourceCurrencyJavaLangStringViewModelParentViewModelTransferDataTransferAmount);
            this.tvCutOffTime.setVisibility(viewModelParentViewModelTransferDataCutOffTimeMsgIsEmptyViewGONEViewVISIBLE);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvReferenceNumber, viewModelParentViewModelTransferDataReferenceNumber);
        }
        if ((dirtyFlags & 0x400L) != 0) {
            // api target 1

            this.confirmButton.setOnClickListener(mCallback34);
        }
        if ((dirtyFlags & 0x611L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.confirmButton, viewModelStateButtonTitle);
        }
        if ((dirtyFlags & 0x418L) != 0) {
            // api target 1

            co.yap.yapcore.helpers.ImageBinding.setIsoCountryDrawable(this.ivAddProfilePic, viewModelParentViewModelBeneficiaryCountryToString);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvBeneficiaryName, viewModelParentViewModelBeneficiaryFullName);
        }
        if ((dirtyFlags & 0x41aL) != 0) {
            // api target 1

            co.yap.yapcore.helpers.ImageBinding.loadAvatar(this.ivUserImage, viewModelParentViewModelBeneficiaryBeneficiaryPictureUrl, viewModelParentViewModelBeneficiaryFullName, viewModelParentViewModelTransferDataPositionJavaLangObjectNullInt0ViewModelParentViewModelTransferDataPosition, "Beneficiary");
        }
        if ((dirtyFlags & 0x451L) != 0) {
            // api target 1

            this.rlAddNewProfilePic.setVisibility(viewModelStateFlagLayoutVisibilityViewVISIBLEViewGONE);
        }
        if ((dirtyFlags & 0x511L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvAvailableBalance, viewModelStateAvailableBalanceString);
        }
        if ((dirtyFlags & 0x415L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvCutOffTime, viewModelStateCutOffMessageGet);
        }
        if ((dirtyFlags & 0x431L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvSuccess, viewModelStateSuccessHeader);
        }
        if ((dirtyFlags & 0x491L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvTransferCaption, viewModelStateTransferAmountHeading);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // viewModel
        co.yap.sendMoney.fundtransfer.viewmodels.TransferSuccessViewModel viewModel = mViewModel;
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
        flag 2 (0x3L): viewModel.state.cutOffMessage
        flag 3 (0x4L): viewModel.parentViewModel.beneficiary
        flag 4 (0x5L): viewModel
        flag 5 (0x6L): viewModel.state.successHeader
        flag 6 (0x7L): viewModel.state.flagLayoutVisibility
        flag 7 (0x8L): viewModel.state.transferAmountHeading
        flag 8 (0x9L): viewModel.state.availableBalanceString
        flag 9 (0xaL): viewModel.state.buttonTitle
        flag 10 (0xbL): null
        flag 11 (0xcL): viewModel.parentViewModel.transferData.getValue().cutOffTimeMsg.isEmpty() ? View.GONE : View.VISIBLE
        flag 12 (0xdL): viewModel.parentViewModel.transferData.getValue().cutOffTimeMsg.isEmpty() ? View.GONE : View.VISIBLE
        flag 13 (0xeL): viewModel.parentViewModel.transferData.getValue().position == null ? 0 : viewModel.parentViewModel.transferData.getValue().position
        flag 14 (0xfL): viewModel.parentViewModel.transferData.getValue().position == null ? 0 : viewModel.parentViewModel.transferData.getValue().position
        flag 15 (0x10L): viewModel.parentViewModel.transferData.getValue().cutOffTimeMsg == null ? true : viewModel.parentViewModel.transferData.getValue().cutOffTimeMsg.isEmpty() ? View.GONE : View.VISIBLE
        flag 16 (0x11L): viewModel.parentViewModel.transferData.getValue().cutOffTimeMsg == null ? true : viewModel.parentViewModel.transferData.getValue().cutOffTimeMsg.isEmpty() ? View.GONE : View.VISIBLE
        flag 17 (0x12L): viewModel.parentViewModel.transferData.getValue().cutOffTimeMsg == null ? true : viewModel.parentViewModel.transferData.getValue().cutOffTimeMsg.isEmpty()
        flag 18 (0x13L): viewModel.parentViewModel.transferData.getValue().cutOffTimeMsg == null ? true : viewModel.parentViewModel.transferData.getValue().cutOffTimeMsg.isEmpty()
        flag 19 (0x14L): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.flagLayoutVisibility) ? View.VISIBLE : View.GONE
        flag 20 (0x15L): androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.flagLayoutVisibility) ? View.VISIBLE : View.GONE
    flag mapping end*/
    //end
}