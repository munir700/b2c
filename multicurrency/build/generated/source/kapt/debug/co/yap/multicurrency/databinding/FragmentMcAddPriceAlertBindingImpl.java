package co.yap.multicurrency.databinding;
import co.yap.multicurrency.R;
import co.yap.multicurrency.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentMcAddPriceAlertBindingImpl extends FragmentMcAddPriceAlertBinding implements co.yap.multicurrency.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.clRatesAndAlertsScreenContainer, 7);
        sViewsWithIds.put(R.id.tvSrcAmount, 8);
        sViewsWithIds.put(R.id.etAmountLayout, 9);
        sViewsWithIds.put(R.id.ly_YapRate, 10);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback17;
    @Nullable
    private final android.view.View.OnClickListener mCallback16;
    // values
    // listeners
    // Inverse Binding Event Handlers
    private androidx.databinding.InverseBindingListener etAmountandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.state.etAmount.get()
            //         is viewModel.state.etAmount.set((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etAmount);
            // localize variables for thread safety
            // viewModel.state.etAmount.get()
            java.lang.String viewModelStateEtAmountGet = null;
            // viewModel.state
            co.yap.multicurrency.ratesalerts.alerts.addpricealert.IMCAddPriceAlerts.State viewModelState = null;
            // viewModel.state.etAmount
            androidx.databinding.ObservableField<java.lang.String> viewModelStateEtAmount = null;
            // viewModel
            co.yap.multicurrency.ratesalerts.alerts.addpricealert.MCAddPriceAlertsVM viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.state.etAmount != null
            boolean viewModelStateEtAmountJavaLangObjectNull = false;
            // viewModel.state != null
            boolean viewModelStateJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelState = viewModel.getState();

                viewModelStateJavaLangObjectNull = (viewModelState) != (null);
                if (viewModelStateJavaLangObjectNull) {


                    viewModelStateEtAmount = viewModelState.getEtAmount();

                    viewModelStateEtAmountJavaLangObjectNull = (viewModelStateEtAmount) != (null);
                    if (viewModelStateEtAmountJavaLangObjectNull) {




                        viewModelStateEtAmount.set(((java.lang.String) (callbackArg_0)));
                    }
                }
            }
        }
    };

    public FragmentMcAddPriceAlertBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }
    private FragmentMcAddPriceAlertBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 6
            , (co.yap.widgets.CoreButton) bindings[6]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[7]
            , (co.yap.widgets.CurrencyPickerView) bindings[1]
            , (co.yap.widgets.CurrencyPickerView) bindings[2]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[3]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[9]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[4]
            , (android.widget.LinearLayout) bindings[10]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[8]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[5]
            );
        this.btnAddAlert.setTag(null);
        this.clSrcCurrContainer.setTag(null);
        this.clTargetCurrContainer.setTag(null);
        this.etAmount.setTag(null);
        this.lblYapRate.setTag(null);
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
        this.tvYapRate.setTag(null);
        setRootTag(root);
        // listeners
        mCallback17 = new co.yap.multicurrency.generated.callback.OnClickListener(this, 2);
        mCallback16 = new co.yap.multicurrency.generated.callback.OnClickListener(this, 1);
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
            setViewModel((co.yap.multicurrency.ratesalerts.alerts.addpricealert.MCAddPriceAlertsVM) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable co.yap.multicurrency.ratesalerts.alerts.addpricealert.MCAddPriceAlertsVM ViewModel) {
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
                return onChangeViewModelStateSrcCountry2DigitCode((androidx.databinding.ObservableField<java.lang.String>) object, fieldId);
            case 1 :
                return onChangeViewModelStateSrcCurrencyCode((androidx.databinding.ObservableField<java.lang.String>) object, fieldId);
            case 2 :
                return onChangeViewModelStateValid((androidx.databinding.ObservableBoolean) object, fieldId);
            case 3 :
                return onChangeViewModelStateSrcCurrencyName((androidx.databinding.ObservableField<java.lang.String>) object, fieldId);
            case 4 :
                return onChangeViewModelStateEtAmount((androidx.databinding.ObservableField<java.lang.String>) object, fieldId);
            case 5 :
                return onChangeViewModelStateYapRateString((androidx.databinding.ObservableField<java.lang.String>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelStateSrcCountry2DigitCode(androidx.databinding.ObservableField<java.lang.String> ViewModelStateSrcCountry2DigitCode, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateSrcCurrencyCode(androidx.databinding.ObservableField<java.lang.String> ViewModelStateSrcCurrencyCode, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateValid(androidx.databinding.ObservableBoolean ViewModelStateValid, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateSrcCurrencyName(androidx.databinding.ObservableField<java.lang.String> ViewModelStateSrcCurrencyName, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateEtAmount(androidx.databinding.ObservableField<java.lang.String> ViewModelStateEtAmount, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateYapRateString(androidx.databinding.ObservableField<java.lang.String> ViewModelStateYapRateString, int fieldId) {
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
        androidx.databinding.ObservableField<java.lang.String> viewModelStateSrcCountry2DigitCode = null;
        androidx.databinding.ObservableField<java.lang.String> viewModelStateSrcCurrencyCode = null;
        androidx.databinding.ObservableBoolean viewModelStateValid = null;
        co.yap.multicurrency.ratesalerts.alerts.addpricealert.IMCAddPriceAlerts.State viewModelState = null;
        java.lang.String viewModelStateSrcCurrencyCodeGet = null;
        boolean viewModelStateValidGet = false;
        java.lang.String viewModelStateYapRateStringGet = null;
        java.lang.String viewModelStateEtAmountGet = null;
        androidx.databinding.ObservableField<java.lang.String> viewModelStateSrcCurrencyName = null;
        java.lang.String viewModelStateSrcCurrencyNameGet = null;
        androidx.databinding.ObservableField<java.lang.String> viewModelStateEtAmount = null;
        co.yap.multicurrency.ratesalerts.alerts.addpricealert.MCAddPriceAlertsVM viewModel = mViewModel;
        java.lang.String viewModelStateSrcCountry2DigitCodeGet = null;
        androidx.databinding.ObservableField<java.lang.String> viewModelStateYapRateString = null;

        if ((dirtyFlags & 0xffL) != 0) {



                if (viewModel != null) {
                    // read viewModel.state
                    viewModelState = viewModel.getState();
                }

            if ((dirtyFlags & 0xc1L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.srcCountry2DigitCode
                        viewModelStateSrcCountry2DigitCode = viewModelState.getSrcCountry2DigitCode();
                    }
                    updateRegistration(0, viewModelStateSrcCountry2DigitCode);


                    if (viewModelStateSrcCountry2DigitCode != null) {
                        // read viewModel.state.srcCountry2DigitCode.get()
                        viewModelStateSrcCountry2DigitCodeGet = viewModelStateSrcCountry2DigitCode.get();
                    }
            }
            if ((dirtyFlags & 0xc2L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.srcCurrencyCode
                        viewModelStateSrcCurrencyCode = viewModelState.getSrcCurrencyCode();
                    }
                    updateRegistration(1, viewModelStateSrcCurrencyCode);


                    if (viewModelStateSrcCurrencyCode != null) {
                        // read viewModel.state.srcCurrencyCode.get()
                        viewModelStateSrcCurrencyCodeGet = viewModelStateSrcCurrencyCode.get();
                    }
            }
            if ((dirtyFlags & 0xc4L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.valid
                        viewModelStateValid = viewModelState.getValid();
                    }
                    updateRegistration(2, viewModelStateValid);


                    if (viewModelStateValid != null) {
                        // read viewModel.state.valid.get()
                        viewModelStateValidGet = viewModelStateValid.get();
                    }
            }
            if ((dirtyFlags & 0xc8L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.srcCurrencyName
                        viewModelStateSrcCurrencyName = viewModelState.getSrcCurrencyName();
                    }
                    updateRegistration(3, viewModelStateSrcCurrencyName);


                    if (viewModelStateSrcCurrencyName != null) {
                        // read viewModel.state.srcCurrencyName.get()
                        viewModelStateSrcCurrencyNameGet = viewModelStateSrcCurrencyName.get();
                    }
            }
            if ((dirtyFlags & 0xd0L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.etAmount
                        viewModelStateEtAmount = viewModelState.getEtAmount();
                    }
                    updateRegistration(4, viewModelStateEtAmount);


                    if (viewModelStateEtAmount != null) {
                        // read viewModel.state.etAmount.get()
                        viewModelStateEtAmountGet = viewModelStateEtAmount.get();
                    }
            }
            if ((dirtyFlags & 0xe0L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.yapRateString
                        viewModelStateYapRateString = viewModelState.getYapRateString();
                    }
                    updateRegistration(5, viewModelStateYapRateString);


                    if (viewModelStateYapRateString != null) {
                        // read viewModel.state.yapRateString.get()
                        viewModelStateYapRateStringGet = viewModelStateYapRateString.get();
                    }
            }
        }
        // batch finished
        if ((dirtyFlags & 0x80L) != 0) {
            // api target 1

            this.btnAddAlert.setOnClickListener(mCallback17);
            co.yap.yapcore.binders.UIBinder.setText(this.btnAddAlert, co.yap.translation.Strings.screen_add_price_alert_button_add_alert);
            this.clSrcCurrContainer.setOnClickListener(mCallback16);
            this.clTargetCurrContainer.setCountryCode("ae");
            this.clTargetCurrContainer.setCurrency(clTargetCurrContainer.getResources().getString(R.string.common_text_currency_name));
            this.clTargetCurrContainer.setCurrencyCode(clTargetCurrContainer.getResources().getString(R.string.common_text_currency_type));
            co.yap.yapcore.binders.UIBinder.setHint(this.etAmount, co.yap.translation.Strings.common_amount_hint);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etAmount, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etAmountandroidTextAttrChanged);
            co.yap.yapcore.binders.UIBinder.setText(this.lblYapRate, co.yap.translation.Strings.screen_add_price_alert_display_label_yap_rate);
        }
        if ((dirtyFlags & 0xc4L) != 0) {
            // api target 1

            co.yap.yapcore.binders.UIBinder.setEnable(this.btnAddAlert, viewModelStateValidGet);
        }
        if ((dirtyFlags & 0xc1L) != 0) {
            // api target 1

            this.clSrcCurrContainer.setCountryCode(viewModelStateSrcCountry2DigitCodeGet);
        }
        if ((dirtyFlags & 0xc8L) != 0) {
            // api target 1

            this.clSrcCurrContainer.setCurrency(viewModelStateSrcCurrencyNameGet);
        }
        if ((dirtyFlags & 0xc2L) != 0) {
            // api target 1

            this.clSrcCurrContainer.setCurrencyCode(viewModelStateSrcCurrencyCodeGet);
        }
        if ((dirtyFlags & 0xd0L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etAmount, viewModelStateEtAmountGet);
        }
        if ((dirtyFlags & 0xe0L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvYapRate, viewModelStateYapRateStringGet);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 2: {
                // localize variables for thread safety
                // viewModel
                co.yap.multicurrency.ratesalerts.alerts.addpricealert.MCAddPriceAlertsVM viewModel = mViewModel;
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
            case 1: {
                // localize variables for thread safety
                // viewModel
                co.yap.multicurrency.ratesalerts.alerts.addpricealert.MCAddPriceAlertsVM viewModel = mViewModel;
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
        flag 0 (0x1L): viewModel.state.srcCountry2DigitCode
        flag 1 (0x2L): viewModel.state.srcCurrencyCode
        flag 2 (0x3L): viewModel.state.valid
        flag 3 (0x4L): viewModel.state.srcCurrencyName
        flag 4 (0x5L): viewModel.state.etAmount
        flag 5 (0x6L): viewModel.state.yapRateString
        flag 6 (0x7L): viewModel
        flag 7 (0x8L): null
    flag mapping end*/
    //end
}