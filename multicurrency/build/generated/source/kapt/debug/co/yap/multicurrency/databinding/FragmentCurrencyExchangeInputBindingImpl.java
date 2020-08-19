package co.yap.multicurrency.databinding;
import co.yap.multicurrency.R;
import co.yap.multicurrency.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentCurrencyExchangeInputBindingImpl extends FragmentCurrencyExchangeInputBinding implements co.yap.multicurrency.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.ivFlag, 10);
        sViewsWithIds.put(R.id.etAmountLayout, 11);
        sViewsWithIds.put(R.id.ivAEDFlag, 12);
        sViewsWithIds.put(R.id.etConvertedAmountLayout, 13);
        sViewsWithIds.put(R.id.clRatesConversion, 14);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    @NonNull
    private final androidx.appcompat.widget.AppCompatTextView mboundView2;
    @NonNull
    private final androidx.appcompat.widget.AppCompatTextView mboundView8;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback11;
    // values
    // listeners
    // Inverse Binding Event Handlers
    private androidx.databinding.InverseBindingListener etAmountandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.state.amount.getValue()
            //         is viewModel.state.amount.setValue((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etAmount);
            // localize variables for thread safety
            // viewModel.state
            co.yap.multicurrency.currencyexchange.currencyexchangeinput.CurrencyExchangeInputState viewModelState = null;
            // viewModel
            co.yap.multicurrency.currencyexchange.currencyexchangeinput.CurrencyExchangeInputViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.state.amount
            androidx.lifecycle.MutableLiveData<java.lang.String> viewModelStateAmount = null;
            // viewModel.state.amount != null
            boolean viewModelStateAmountJavaLangObjectNull = false;
            // viewModel.state.amount.getValue()
            java.lang.String viewModelStateAmountGetValue = null;
            // viewModel.state != null
            boolean viewModelStateJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelState = viewModel.getState();

                viewModelStateJavaLangObjectNull = (viewModelState) != (null);
                if (viewModelStateJavaLangObjectNull) {


                    viewModelStateAmount = viewModelState.getAmount();

                    viewModelStateAmountJavaLangObjectNull = (viewModelStateAmount) != (null);
                    if (viewModelStateAmountJavaLangObjectNull) {




                        viewModelStateAmount.setValue(((java.lang.String) (callbackArg_0)));
                    }
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener etConvertedAmountandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.state.targetAmount.getValue()
            //         is viewModel.state.targetAmount.setValue((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(etConvertedAmount);
            // localize variables for thread safety
            // viewModel.state
            co.yap.multicurrency.currencyexchange.currencyexchangeinput.CurrencyExchangeInputState viewModelState = null;
            // viewModel.state.targetAmount.getValue()
            java.lang.String viewModelStateTargetAmountGetValue = null;
            // viewModel.state.targetAmount
            androidx.lifecycle.MutableLiveData<java.lang.String> viewModelStateTargetAmount = null;
            // viewModel.state.targetAmount != null
            boolean viewModelStateTargetAmountJavaLangObjectNull = false;
            // viewModel
            co.yap.multicurrency.currencyexchange.currencyexchangeinput.CurrencyExchangeInputViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;
            // viewModel.state != null
            boolean viewModelStateJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelState = viewModel.getState();

                viewModelStateJavaLangObjectNull = (viewModelState) != (null);
                if (viewModelStateJavaLangObjectNull) {


                    viewModelStateTargetAmount = viewModelState.getTargetAmount();

                    viewModelStateTargetAmountJavaLangObjectNull = (viewModelStateTargetAmount) != (null);
                    if (viewModelStateTargetAmountJavaLangObjectNull) {




                        viewModelStateTargetAmount.setValue(((java.lang.String) (callbackArg_0)));
                    }
                }
            }
        }
    };

    public FragmentCurrencyExchangeInputBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds));
    }
    private FragmentCurrencyExchangeInputBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 9
            , (co.yap.widgets.CoreButton) bindings[9]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[14]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[3]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[11]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[6]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[13]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[12]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[10]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[1]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[4]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[5]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[7]
            );
        this.btnExchange.setTag(null);
        this.etAmount.setTag(null);
        this.etConvertedAmount.setTag(null);
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView2 = (androidx.appcompat.widget.AppCompatTextView) bindings[2];
        this.mboundView2.setTag(null);
        this.mboundView8 = (androidx.appcompat.widget.AppCompatTextView) bindings[8];
        this.mboundView8.setTag(null);
        this.tvSelectedCurrency.setTag(null);
        this.tvTargetCurrency.setTag(null);
        this.tvTargetCurrencyBalance.setTag(null);
        this.tvYapRateTitle.setTag(null);
        setRootTag(root);
        // listeners
        mCallback11 = new co.yap.multicurrency.generated.callback.OnClickListener(this, 1);
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
            setViewModel((co.yap.multicurrency.currencyexchange.currencyexchangeinput.CurrencyExchangeInputViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable co.yap.multicurrency.currencyexchange.currencyexchangeinput.CurrencyExchangeInputViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x200L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeViewModelStateValid((androidx.lifecycle.MutableLiveData<java.lang.Boolean>) object, fieldId);
            case 1 :
                return onChangeViewModelState((co.yap.multicurrency.currencyexchange.currencyexchangeinput.CurrencyExchangeInputState) object, fieldId);
            case 2 :
                return onChangeViewModelStateTargetCurrencyAvailableBalance((androidx.lifecycle.MutableLiveData<java.lang.String>) object, fieldId);
            case 3 :
                return onChangeViewModelStateTargetAmount((androidx.lifecycle.MutableLiveData<java.lang.String>) object, fieldId);
            case 4 :
                return onChangeViewModelStateTargetCurrency((androidx.lifecycle.MutableLiveData<java.lang.String>) object, fieldId);
            case 5 :
                return onChangeViewModelStateAmount((androidx.lifecycle.MutableLiveData<java.lang.String>) object, fieldId);
            case 6 :
                return onChangeViewModelStateSelectedCurrencyAvailableBalance((androidx.lifecycle.MutableLiveData<java.lang.String>) object, fieldId);
            case 7 :
                return onChangeViewModelStateYapRateValue((androidx.lifecycle.MutableLiveData<java.lang.String>) object, fieldId);
            case 8 :
                return onChangeViewModelStateSelectedCurrency((androidx.lifecycle.MutableLiveData<java.lang.String>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelStateValid(androidx.lifecycle.MutableLiveData<java.lang.Boolean> ViewModelStateValid, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelState(co.yap.multicurrency.currencyexchange.currencyexchangeinput.CurrencyExchangeInputState ViewModelState, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateTargetCurrencyAvailableBalance(androidx.lifecycle.MutableLiveData<java.lang.String> ViewModelStateTargetCurrencyAvailableBalance, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateTargetAmount(androidx.lifecycle.MutableLiveData<java.lang.String> ViewModelStateTargetAmount, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateTargetCurrency(androidx.lifecycle.MutableLiveData<java.lang.String> ViewModelStateTargetCurrency, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateAmount(androidx.lifecycle.MutableLiveData<java.lang.String> ViewModelStateAmount, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateSelectedCurrencyAvailableBalance(androidx.lifecycle.MutableLiveData<java.lang.String> ViewModelStateSelectedCurrencyAvailableBalance, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x40L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateYapRateValue(androidx.lifecycle.MutableLiveData<java.lang.String> ViewModelStateYapRateValue, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x80L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateSelectedCurrency(androidx.lifecycle.MutableLiveData<java.lang.String> ViewModelStateSelectedCurrency, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x100L;
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
        java.lang.String viewModelStateTargetCurrencyGetValue = null;
        java.lang.String viewModelStateTargetCurrencyAvailableBalanceGetValue = null;
        java.lang.Boolean viewModelStateValidGetValue = null;
        java.lang.String viewModelStateYapRateValueGetValue = null;
        androidx.lifecycle.MutableLiveData<java.lang.Boolean> viewModelStateValid = null;
        co.yap.multicurrency.currencyexchange.currencyexchangeinput.CurrencyExchangeInputState viewModelState = null;
        java.lang.String viewModelStateTargetAmountGetValue = null;
        androidx.lifecycle.MutableLiveData<java.lang.String> viewModelStateTargetCurrencyAvailableBalance = null;
        androidx.lifecycle.MutableLiveData<java.lang.String> viewModelStateTargetAmount = null;
        androidx.lifecycle.MutableLiveData<java.lang.String> viewModelStateTargetCurrency = null;
        androidx.lifecycle.MutableLiveData<java.lang.String> viewModelStateAmount = null;
        java.lang.String viewModelStateAmountGetValue = null;
        androidx.lifecycle.MutableLiveData<java.lang.String> viewModelStateSelectedCurrencyAvailableBalance = null;
        androidx.lifecycle.MutableLiveData<java.lang.String> viewModelStateYapRateValue = null;
        androidx.lifecycle.MutableLiveData<java.lang.String> viewModelStateSelectedCurrency = null;
        java.lang.String viewModelStateSelectedCurrencyGetValue = null;
        java.lang.String viewModelStateSelectedCurrencyAvailableBalanceGetValue = null;
        co.yap.multicurrency.currencyexchange.currencyexchangeinput.CurrencyExchangeInputViewModel viewModel = mViewModel;
        boolean androidxDatabindingViewDataBindingSafeUnboxViewModelStateValidGetValue = false;

        if ((dirtyFlags & 0x7ffL) != 0) {



                if (viewModel != null) {
                    // read viewModel.state
                    viewModelState = viewModel.getState();
                }
                updateRegistration(1, viewModelState);

            if ((dirtyFlags & 0x603L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.valid
                        viewModelStateValid = viewModelState.getValid();
                    }
                    updateLiveDataRegistration(0, viewModelStateValid);


                    if (viewModelStateValid != null) {
                        // read viewModel.state.valid.getValue()
                        viewModelStateValidGetValue = viewModelStateValid.getValue();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(viewModel.state.valid.getValue())
                    androidxDatabindingViewDataBindingSafeUnboxViewModelStateValidGetValue = androidx.databinding.ViewDataBinding.safeUnbox(viewModelStateValidGetValue);
            }
            if ((dirtyFlags & 0x606L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.targetCurrencyAvailableBalance
                        viewModelStateTargetCurrencyAvailableBalance = viewModelState.getTargetCurrencyAvailableBalance();
                    }
                    updateLiveDataRegistration(2, viewModelStateTargetCurrencyAvailableBalance);


                    if (viewModelStateTargetCurrencyAvailableBalance != null) {
                        // read viewModel.state.targetCurrencyAvailableBalance.getValue()
                        viewModelStateTargetCurrencyAvailableBalanceGetValue = viewModelStateTargetCurrencyAvailableBalance.getValue();
                    }
            }
            if ((dirtyFlags & 0x60aL) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.targetAmount
                        viewModelStateTargetAmount = viewModelState.getTargetAmount();
                    }
                    updateLiveDataRegistration(3, viewModelStateTargetAmount);


                    if (viewModelStateTargetAmount != null) {
                        // read viewModel.state.targetAmount.getValue()
                        viewModelStateTargetAmountGetValue = viewModelStateTargetAmount.getValue();
                    }
            }
            if ((dirtyFlags & 0x612L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.targetCurrency
                        viewModelStateTargetCurrency = viewModelState.getTargetCurrency();
                    }
                    updateLiveDataRegistration(4, viewModelStateTargetCurrency);


                    if (viewModelStateTargetCurrency != null) {
                        // read viewModel.state.targetCurrency.getValue()
                        viewModelStateTargetCurrencyGetValue = viewModelStateTargetCurrency.getValue();
                    }
            }
            if ((dirtyFlags & 0x622L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.amount
                        viewModelStateAmount = viewModelState.getAmount();
                    }
                    updateLiveDataRegistration(5, viewModelStateAmount);


                    if (viewModelStateAmount != null) {
                        // read viewModel.state.amount.getValue()
                        viewModelStateAmountGetValue = viewModelStateAmount.getValue();
                    }
            }
            if ((dirtyFlags & 0x642L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.selectedCurrencyAvailableBalance
                        viewModelStateSelectedCurrencyAvailableBalance = viewModelState.getSelectedCurrencyAvailableBalance();
                    }
                    updateLiveDataRegistration(6, viewModelStateSelectedCurrencyAvailableBalance);


                    if (viewModelStateSelectedCurrencyAvailableBalance != null) {
                        // read viewModel.state.selectedCurrencyAvailableBalance.getValue()
                        viewModelStateSelectedCurrencyAvailableBalanceGetValue = viewModelStateSelectedCurrencyAvailableBalance.getValue();
                    }
            }
            if ((dirtyFlags & 0x682L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.yapRateValue
                        viewModelStateYapRateValue = viewModelState.getYapRateValue();
                    }
                    updateLiveDataRegistration(7, viewModelStateYapRateValue);


                    if (viewModelStateYapRateValue != null) {
                        // read viewModel.state.yapRateValue.getValue()
                        viewModelStateYapRateValueGetValue = viewModelStateYapRateValue.getValue();
                    }
            }
            if ((dirtyFlags & 0x702L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.selectedCurrency
                        viewModelStateSelectedCurrency = viewModelState.getSelectedCurrency();
                    }
                    updateLiveDataRegistration(8, viewModelStateSelectedCurrency);


                    if (viewModelStateSelectedCurrency != null) {
                        // read viewModel.state.selectedCurrency.getValue()
                        viewModelStateSelectedCurrencyGetValue = viewModelStateSelectedCurrency.getValue();
                    }
            }
        }
        // batch finished
        if ((dirtyFlags & 0x400L) != 0) {
            // api target 1

            this.btnExchange.setOnClickListener(mCallback11);
            co.yap.yapcore.binders.UIBinder.setText(this.btnExchange, co.yap.translation.Strings.screen_multi_currency_exchange_currency_input_text_button);
            co.yap.yapcore.binders.UIBinder.setHint(this.etAmount, co.yap.translation.Strings.common_amount_hint);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etAmount, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etAmountandroidTextAttrChanged);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.etConvertedAmount, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, etConvertedAmountandroidTextAttrChanged);
            co.yap.yapcore.binders.UIBinder.setText(this.tvYapRateTitle, co.yap.translation.Strings.screen_multi_currency_exchange_currency_input_text_yap_rate);
        }
        if ((dirtyFlags & 0x603L) != 0) {
            // api target 1

            co.yap.yapcore.binders.UIBinder.setEnable(this.btnExchange, androidxDatabindingViewDataBindingSafeUnboxViewModelStateValidGetValue);
        }
        if ((dirtyFlags & 0x622L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etAmount, viewModelStateAmountGetValue);
        }
        if ((dirtyFlags & 0x60aL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.etConvertedAmount, viewModelStateTargetAmountGetValue);
        }
        if ((dirtyFlags & 0x642L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView2, viewModelStateSelectedCurrencyAvailableBalanceGetValue);
        }
        if ((dirtyFlags & 0x682L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView8, viewModelStateYapRateValueGetValue);
        }
        if ((dirtyFlags & 0x702L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvSelectedCurrency, viewModelStateSelectedCurrencyGetValue);
        }
        if ((dirtyFlags & 0x612L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvTargetCurrency, viewModelStateTargetCurrencyGetValue);
        }
        if ((dirtyFlags & 0x606L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvTargetCurrencyBalance, viewModelStateTargetCurrencyAvailableBalanceGetValue);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // viewModel
        co.yap.multicurrency.currencyexchange.currencyexchangeinput.CurrencyExchangeInputViewModel viewModel = mViewModel;
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
        flag 0 (0x1L): viewModel.state.valid
        flag 1 (0x2L): viewModel.state
        flag 2 (0x3L): viewModel.state.targetCurrencyAvailableBalance
        flag 3 (0x4L): viewModel.state.targetAmount
        flag 4 (0x5L): viewModel.state.targetCurrency
        flag 5 (0x6L): viewModel.state.amount
        flag 6 (0x7L): viewModel.state.selectedCurrencyAvailableBalance
        flag 7 (0x8L): viewModel.state.yapRateValue
        flag 8 (0x9L): viewModel.state.selectedCurrency
        flag 9 (0xaL): viewModel
        flag 10 (0xbL): null
    flag mapping end*/
    //end
}