package co.yap.sendmoney.databinding;
import co.yap.sendmoney.R;
import co.yap.sendmoney.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentInternationalFundsTransferBindingImpl extends FragmentInternationalFundsTransferBinding implements co.yap.sendmoney.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.clFTSnackbar, 17);
        sViewsWithIds.put(R.id.ly_internationalTransfer, 18);
        sViewsWithIds.put(R.id.rlPicture, 19);
        sViewsWithIds.put(R.id.rlAddNewProfilePic, 20);
        sViewsWithIds.put(R.id.etAmountLayout, 21);
        sViewsWithIds.put(R.id.etBeneficiaryAmountLayout, 22);
        sViewsWithIds.put(R.id.ly_YapRate, 23);
        sViewsWithIds.put(R.id.layoutReasonsSpinner, 24);
        sViewsWithIds.put(R.id.reasonsSpinner, 25);
    }
    // views
    @NonNull
    private final android.widget.TextView mboundView11;
    @NonNull
    private final android.widget.TextView mboundView14;
    @NonNull
    private final android.widget.TextView mboundView15;
    @NonNull
    private final android.widget.TextView mboundView3;
    @NonNull
    private final co.yap.widgets.EasyMoneyEditText mboundView7;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback9;
    @Nullable
    private final android.view.View.OnClickListener mCallback8;
    // values
    // listeners
    // Inverse Binding Event Handlers
    private androidx.databinding.InverseBindingListener etNoteandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.state.transactionNote.get()
            //         is viewModel.state.transactionNote.set((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etNote);
            // localize variables for thread safety
            // viewModel.state
            co.yap.sendmoney.fundtransfer.states.InternationalFundsTransferState viewModelState = null;
            // viewModel.state.transactionNote.get()
            java.lang.String viewModelStateTransactionNoteGet = null;
            // viewModel.state.transactionNote
            androidx.databinding.ObservableField<java.lang.String> viewModelStateTransactionNote = null;
            // viewModel.state.transactionNote != null
            boolean viewModelStateTransactionNoteJavaLangObjectNull = false;
            // viewModel
            co.yap.sendmoney.fundtransfer.viewmodels.InternationalFundsTransferViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.state != null
            boolean viewModelStateJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelState = viewModel.getState();

                viewModelStateJavaLangObjectNull = (viewModelState) != (null);
                if (viewModelStateJavaLangObjectNull) {


                    viewModelStateTransactionNote = viewModelState.getTransactionNote();

                    viewModelStateTransactionNoteJavaLangObjectNull = (viewModelStateTransactionNote) != (null);
                    if (viewModelStateTransactionNoteJavaLangObjectNull) {




                        viewModelStateTransactionNote.set(((java.lang.String) (callbackArg_0)));
                    }
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener etSenderAmountandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.state.etInputAmount
            //         is viewModel.state.setEtInputAmount((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etSenderAmount);
            // localize variables for thread safety
            // viewModel.state
            co.yap.sendmoney.fundtransfer.states.InternationalFundsTransferState viewModelState = null;
            // viewModel.state.etInputAmount
            java.lang.String viewModelStateEtInputAmount = null;
            // viewModel
            co.yap.sendmoney.fundtransfer.viewmodels.InternationalFundsTransferViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.state != null
            boolean viewModelStateJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelState = viewModel.getState();

                viewModelStateJavaLangObjectNull = (viewModelState) != (null);
                if (viewModelStateJavaLangObjectNull) {




                    viewModelState.setEtInputAmount(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };

    public FragmentInternationalFundsTransferBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 26, sIncludes, sViewsWithIds));
    }
    private FragmentInternationalFundsTransferBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 6
            , (co.yap.widgets.CoreButton) bindings[16]
            , (androidx.coordinatorlayout.widget.CoordinatorLayout) bindings[17]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[21]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[22]
            , (android.widget.TextView) bindings[6]
            , (com.google.android.material.textfield.TextInputEditText) bindings[13]
            , (android.widget.EditText) bindings[5]
            , (android.widget.TextView) bindings[4]
            , (android.widget.ImageView) bindings[2]
            , (co.yap.widgets.CoreCircularImageView) bindings[1]
            , (android.widget.RelativeLayout) bindings[24]
            , (android.widget.LinearLayout) bindings[18]
            , (android.widget.LinearLayout) bindings[23]
            , (android.widget.Spinner) bindings[25]
            , (android.widget.RelativeLayout) bindings[20]
            , (android.widget.RelativeLayout) bindings[19]
            , (android.widget.ScrollView) bindings[0]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[9]
            , (android.view.View) bindings[12]
            );
        this.btnNext.setTag(null);
        this.etBeneficiaryCurrency.setTag(null);
        this.etNote.setTag(null);
        this.etSenderAmount.setTag(null);
        this.etSenderCurrency.setTag(null);
        this.ivAddProfilePic.setTag(null);
        this.ivProfilePic.setTag(null);
        this.mboundView11 = (android.widget.TextView) bindings[11];
        this.mboundView11.setTag(null);
        this.mboundView14 = (android.widget.TextView) bindings[14];
        this.mboundView14.setTag(null);
        this.mboundView15 = (android.widget.TextView) bindings[15];
        this.mboundView15.setTag(null);
        this.mboundView3 = (android.widget.TextView) bindings[3];
        this.mboundView3.setTag(null);
        this.mboundView7 = (co.yap.widgets.EasyMoneyEditText) bindings[7];
        this.mboundView7.setTag(null);
        this.svContainer.setTag(null);
        this.tvAvailableBalance.setTag(null);
        this.tvDefaultCurrencyRate.setTag(null);
        this.tvDynamicCurrencyRete.setTag(null);
        this.viewSpinnerClickReason.setTag(null);
        setRootTag(root);
        // listeners
        mCallback9 = new co.yap.sendmoney.generated.callback.OnClickListener(this, 2);
        mCallback8 = new co.yap.sendmoney.generated.callback.OnClickListener(this, 1);
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
            setViewModel((co.yap.sendmoney.fundtransfer.viewmodels.InternationalFundsTransferViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable co.yap.sendmoney.fundtransfer.viewmodels.InternationalFundsTransferViewModel ViewModel) {
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
                return onChangeViewModelState((co.yap.sendmoney.fundtransfer.states.InternationalFundsTransferState) object, fieldId);
            case 1 :
                return onChangeViewModelStateTransactionNote((androidx.databinding.ObservableField<java.lang.String>) object, fieldId);
            case 2 :
                return onChangeViewModelParentViewModelTransferData((androidx.lifecycle.MutableLiveData<co.yap.sendmoney.fundtransfer.models.TransferFundData>) object, fieldId);
            case 3 :
                return onChangeViewModelParentViewModelBeneficiary((androidx.lifecycle.MutableLiveData<co.yap.networking.customers.responsedtos.sendmoney.Beneficiary>) object, fieldId);
            case 4 :
                return onChangeViewModelStateSourceCurrency((androidx.databinding.ObservableField<java.lang.String>) object, fieldId);
            case 5 :
                return onChangeViewModelStateDestinationCurrency((androidx.databinding.ObservableField<java.lang.String>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelState(co.yap.sendmoney.fundtransfer.states.InternationalFundsTransferState ViewModelState, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        else if (fieldId == BR.etInputAmount) {
            synchronized(this) {
                    mDirtyFlags |= 0x80L;
            }
            return true;
        }
        else if (fieldId == BR.etOutputAmount) {
            synchronized(this) {
                    mDirtyFlags |= 0x100L;
            }
            return true;
        }
        else if (fieldId == BR.toFxRate) {
            synchronized(this) {
                    mDirtyFlags |= 0x200L;
            }
            return true;
        }
        else if (fieldId == BR.fromFxRate) {
            synchronized(this) {
                    mDirtyFlags |= 0x400L;
            }
            return true;
        }
        else if (fieldId == BR.availableBalanceString) {
            synchronized(this) {
                    mDirtyFlags |= 0x800L;
            }
            return true;
        }
        else if (fieldId == BR.transferFeeSpannable) {
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
    private boolean onChangeViewModelStateTransactionNote(androidx.databinding.ObservableField<java.lang.String> ViewModelStateTransactionNote, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelParentViewModelTransferData(androidx.lifecycle.MutableLiveData<co.yap.sendmoney.fundtransfer.models.TransferFundData> ViewModelParentViewModelTransferData, int fieldId) {
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
    private boolean onChangeViewModelStateSourceCurrency(androidx.databinding.ObservableField<java.lang.String> ViewModelStateSourceCurrency, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateDestinationCurrency(androidx.databinding.ObservableField<java.lang.String> ViewModelStateDestinationCurrency, int fieldId) {
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
        java.lang.String viewModelStateEtOutputAmount = null;
        boolean viewModelStateValid = false;
        java.lang.String viewModelParentViewModelBeneficiaryBeneficiaryPictureUrl = null;
        co.yap.sendmoney.fundtransfer.states.InternationalFundsTransferState viewModelState = null;
        java.lang.String viewModelStateSourceCurrencyGet = null;
        androidx.databinding.ObservableField<java.lang.String> viewModelStateTransactionNote = null;
        androidx.lifecycle.MutableLiveData<co.yap.sendmoney.fundtransfer.models.TransferFundData> viewModelParentViewModelTransferData = null;
        java.lang.String viewModelStateFromFxRate = null;
        co.yap.sendmoney.fundtransfer.interfaces.IBeneficiaryFundTransfer.ViewModel viewModelParentViewModel = null;
        java.lang.String viewModelStateTransactionNoteGet = null;
        java.lang.String viewModelParentViewModelBeneficiaryFullName = null;
        androidx.lifecycle.MutableLiveData<co.yap.networking.customers.responsedtos.sendmoney.Beneficiary> viewModelParentViewModelBeneficiary = null;
        java.lang.String viewModelParentViewModelBeneficiaryCountry = null;
        java.lang.Integer viewModelParentViewModelTransferDataPosition = null;
        androidx.databinding.ObservableField<java.lang.String> viewModelStateSourceCurrency = null;
        int androidxDatabindingViewDataBindingSafeUnboxViewModelParentViewModelTransferDataPosition = 0;
        java.lang.String viewModelStateEtInputAmount = null;
        java.lang.CharSequence viewModelStateAvailableBalanceString = null;
        java.lang.String viewModelStateDestinationCurrencyGet = null;
        co.yap.sendmoney.fundtransfer.models.TransferFundData viewModelParentViewModelTransferDataGetValue = null;
        co.yap.networking.customers.responsedtos.sendmoney.Beneficiary viewModelParentViewModelBeneficiaryGetValue = null;
        androidx.databinding.ObservableField<java.lang.String> viewModelStateDestinationCurrency = null;
        java.lang.String viewModelStateToFxRate = null;
        java.lang.CharSequence viewModelStateTransferFeeSpannable = null;
        co.yap.sendmoney.fundtransfer.viewmodels.InternationalFundsTransferViewModel viewModel = mViewModel;

        if ((dirtyFlags & 0x7fffL) != 0) {


            if ((dirtyFlags & 0x7ff3L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.state
                        viewModelState = viewModel.getState();
                    }
                    updateRegistration(0, viewModelState);

                if ((dirtyFlags & 0x4141L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.etOutputAmount
                            viewModelStateEtOutputAmount = viewModelState.getEtOutputAmount();
                        }
                }
                if ((dirtyFlags & 0x6041L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.valid
                            viewModelStateValid = viewModelState.getValid();
                        }
                }
                if ((dirtyFlags & 0x4043L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.transactionNote
                            viewModelStateTransactionNote = viewModelState.getTransactionNote();
                        }
                        updateRegistration(1, viewModelStateTransactionNote);


                        if (viewModelStateTransactionNote != null) {
                            // read viewModel.state.transactionNote.get()
                            viewModelStateTransactionNoteGet = viewModelStateTransactionNote.get();
                        }
                }
                if ((dirtyFlags & 0x4441L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.fromFxRate
                            viewModelStateFromFxRate = viewModelState.getFromFxRate();
                        }
                }
                if ((dirtyFlags & 0x4051L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.sourceCurrency
                            viewModelStateSourceCurrency = viewModelState.getSourceCurrency();
                        }
                        updateRegistration(4, viewModelStateSourceCurrency);


                        if (viewModelStateSourceCurrency != null) {
                            // read viewModel.state.sourceCurrency.get()
                            viewModelStateSourceCurrencyGet = viewModelStateSourceCurrency.get();
                        }
                }
                if ((dirtyFlags & 0x40c1L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.etInputAmount
                            viewModelStateEtInputAmount = viewModelState.getEtInputAmount();
                        }
                }
                if ((dirtyFlags & 0x4841L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.availableBalanceString
                            viewModelStateAvailableBalanceString = viewModelState.getAvailableBalanceString();
                        }
                }
                if ((dirtyFlags & 0x4061L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.destinationCurrency
                            viewModelStateDestinationCurrency = viewModelState.getDestinationCurrency();
                        }
                        updateRegistration(5, viewModelStateDestinationCurrency);


                        if (viewModelStateDestinationCurrency != null) {
                            // read viewModel.state.destinationCurrency.get()
                            viewModelStateDestinationCurrencyGet = viewModelStateDestinationCurrency.get();
                        }
                }
                if ((dirtyFlags & 0x4241L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.toFxRate
                            viewModelStateToFxRate = viewModelState.getToFxRate();
                        }
                }
                if ((dirtyFlags & 0x5041L) != 0) {

                        if (viewModelState != null) {
                            // read viewModel.state.transferFeeSpannable
                            viewModelStateTransferFeeSpannable = viewModelState.getTransferFeeSpannable();
                        }
                }
            }
            if ((dirtyFlags & 0x404cL) != 0) {

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
                    updateLiveDataRegistration(2, viewModelParentViewModelTransferData);
                    updateLiveDataRegistration(3, viewModelParentViewModelBeneficiary);


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
                if ((dirtyFlags & 0x4048L) != 0) {

                        if (viewModelParentViewModelBeneficiaryGetValue != null) {
                            // read viewModel.parentViewModel.beneficiary.getValue().country
                            viewModelParentViewModelBeneficiaryCountry = viewModelParentViewModelBeneficiaryGetValue.getCountry();
                        }
                }
            }
        }
        // batch finished
        if ((dirtyFlags & 0x4000L) != 0) {
            // api target 1

            this.btnNext.setOnClickListener(mCallback9);
            co.yap.yapcore.binders.UIBinder.setText(this.btnNext, co.yap.translation.Strings.screen_yap_to_yap_transfer_display_text_button);
            co.yap.yapcore.binders.UIBinder.setHint(this.etNote, co.yap.translation.Strings.screen_international_funds_transfer_input_text_note_hint);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etNote, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etNoteandroidTextAttrChanged);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etSenderAmount, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etSenderAmountandroidTextAttrChanged);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView11, co.yap.translation.Strings.screen_international_funds_transfer_display_text_reson);
            co.yap.yapcore.binders.UIBinder.setText(this.mboundView15, co.yap.translation.Strings.screen_international_funds_transfer_display_text_note);
            this.viewSpinnerClickReason.setOnClickListener(mCallback8);
        }
        if ((dirtyFlags & 0x6041L) != 0) {
            // api target 1

            co.yap.yapcore.binders.UIBinder.setEnable(this.btnNext, viewModelStateValid);
        }
        if ((dirtyFlags & 0x4061L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etBeneficiaryCurrency, viewModelStateDestinationCurrencyGet);
        }
        if ((dirtyFlags & 0x4043L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etNote, viewModelStateTransactionNoteGet);
        }
        if ((dirtyFlags & 0x40c1L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etSenderAmount, viewModelStateEtInputAmount);
        }
        if ((dirtyFlags & 0x4051L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etSenderCurrency, viewModelStateSourceCurrencyGet);
        }
        if ((dirtyFlags & 0x4048L) != 0) {
            // api target 1

            co.yap.yapcore.helpers.ImageBinding.setIsoCountryDrawable(this.ivAddProfilePic, viewModelParentViewModelBeneficiaryCountry);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView3, viewModelParentViewModelBeneficiaryFullName);
        }
        if ((dirtyFlags & 0x404cL) != 0) {
            // api target 1

            co.yap.yapcore.helpers.ImageBinding.loadAvatar(this.ivProfilePic, viewModelParentViewModelBeneficiaryBeneficiaryPictureUrl, viewModelParentViewModelBeneficiaryFullName, androidxDatabindingViewDataBindingSafeUnboxViewModelParentViewModelTransferDataPosition, "Beneficiary");
        }
        if ((dirtyFlags & 0x5041L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView14, viewModelStateTransferFeeSpannable);
        }
        if ((dirtyFlags & 0x4141L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView7, viewModelStateEtOutputAmount);
        }
        if ((dirtyFlags & 0x4841L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvAvailableBalance, viewModelStateAvailableBalanceString);
        }
        if ((dirtyFlags & 0x4241L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvDefaultCurrencyRate, viewModelStateToFxRate);
        }
        if ((dirtyFlags & 0x4441L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvDynamicCurrencyRete, viewModelStateFromFxRate);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 2: {
                // localize variables for thread safety
                // viewModel
                co.yap.sendmoney.fundtransfer.viewmodels.InternationalFundsTransferViewModel viewModel = mViewModel;
                // viewModel != null
                boolean viewModelJavaLangObjectNull = false;
                // v.id
                int callbackArg0Id = 0;



                viewModelJavaLangObjectNull = (viewModel) != (null);
                if (viewModelJavaLangObjectNull) {


                    if ((callbackArg_0) != (null)) {


                        callbackArg0Id = callbackArg_0.getId();

                        viewModel.handlePressOnButton(callbackArg0Id);
                    }
                }
                break;
            }
            case 1: {
                // localize variables for thread safety
                // viewModel
                co.yap.sendmoney.fundtransfer.viewmodels.InternationalFundsTransferViewModel viewModel = mViewModel;
                // viewModel != null
                boolean viewModelJavaLangObjectNull = false;
                // v.id
                int callbackArg0Id = 0;



                viewModelJavaLangObjectNull = (viewModel) != (null);
                if (viewModelJavaLangObjectNull) {


                    if ((callbackArg_0) != (null)) {


                        callbackArg0Id = callbackArg_0.getId();

                        viewModel.handlePressOnButton(callbackArg0Id);
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
        flag 1 (0x2L): viewModel.state.transactionNote
        flag 2 (0x3L): viewModel.parentViewModel.transferData
        flag 3 (0x4L): viewModel.parentViewModel.beneficiary
        flag 4 (0x5L): viewModel.state.sourceCurrency
        flag 5 (0x6L): viewModel.state.destinationCurrency
        flag 6 (0x7L): viewModel
        flag 7 (0x8L): viewModel.state.etInputAmount
        flag 8 (0x9L): viewModel.state.etOutputAmount
        flag 9 (0xaL): viewModel.state.toFxRate
        flag 10 (0xbL): viewModel.state.fromFxRate
        flag 11 (0xcL): viewModel.state.availableBalanceString
        flag 12 (0xdL): viewModel.state.transferFeeSpannable
        flag 13 (0xeL): viewModel.state.valid
        flag 14 (0xfL): null
    flag mapping end*/
    //end
}