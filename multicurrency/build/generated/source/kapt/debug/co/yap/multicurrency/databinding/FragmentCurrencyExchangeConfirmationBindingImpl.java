package co.yap.multicurrency.databinding;
import co.yap.multicurrency.R;
import co.yap.multicurrency.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentCurrencyExchangeConfirmationBindingImpl extends FragmentCurrencyExchangeConfirmationBinding implements co.yap.multicurrency.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.ivSelectedFlag, 7);
        sViewsWithIds.put(R.id.ivTargetFlag, 8);
        sViewsWithIds.put(R.id.clRatesConversion, 9);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    @NonNull
    private final androidx.appcompat.widget.AppCompatTextView mboundView5;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback20;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentCurrencyExchangeConfirmationBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds));
    }
    private FragmentCurrencyExchangeConfirmationBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 4
            , (co.yap.widgets.CoreButton) bindings[6]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[9]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[7]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[8]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[1]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[3]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[2]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[4]
            );
        this.btnConfirmExchange.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView5 = (androidx.appcompat.widget.AppCompatTextView) bindings[5];
        this.mboundView5.setTag(null);
        this.tvSelectedCurrencyAmount.setTag(null);
        this.tvTargetCurrencyAmount.setTag(null);
        this.tvTo.setTag(null);
        this.tvYapRateTitle.setTag(null);
        setRootTag(root);
        // listeners
        mCallback20 = new co.yap.multicurrency.generated.callback.OnClickListener(this, 1);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x20L;
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
            setViewModel((co.yap.multicurrency.currencyexchange.confirmation.CurrencyExchangeConfirmationViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable co.yap.multicurrency.currencyexchange.confirmation.CurrencyExchangeConfirmationViewModel ViewModel) {
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
                return onChangeViewModelStateYapRateValue((androidx.lifecycle.MutableLiveData<java.lang.String>) object, fieldId);
            case 1 :
                return onChangeViewModelStateSelectedCurrencyAmount((androidx.lifecycle.MutableLiveData<java.lang.String>) object, fieldId);
            case 2 :
                return onChangeViewModelStateTargetCurrencyAmount((androidx.lifecycle.MutableLiveData<java.lang.String>) object, fieldId);
            case 3 :
                return onChangeViewModelState((co.yap.multicurrency.currencyexchange.confirmation.CurrencyExchangeConfirmationState) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelStateYapRateValue(androidx.lifecycle.MutableLiveData<java.lang.String> ViewModelStateYapRateValue, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateSelectedCurrencyAmount(androidx.lifecycle.MutableLiveData<java.lang.String> ViewModelStateSelectedCurrencyAmount, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateTargetCurrencyAmount(androidx.lifecycle.MutableLiveData<java.lang.String> ViewModelStateTargetCurrencyAmount, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelState(co.yap.multicurrency.currencyexchange.confirmation.CurrencyExchangeConfirmationState ViewModelState, int fieldId) {
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
        androidx.lifecycle.MutableLiveData<java.lang.String> viewModelStateYapRateValue = null;
        java.lang.String viewModelStateTargetCurrencyAmountGetValue = null;
        java.lang.String viewModelStateYapRateValueGetValue = null;
        androidx.lifecycle.MutableLiveData<java.lang.String> viewModelStateSelectedCurrencyAmount = null;
        androidx.lifecycle.MutableLiveData<java.lang.String> viewModelStateTargetCurrencyAmount = null;
        java.lang.String viewModelStateSelectedCurrencyAmountGetValue = null;
        co.yap.multicurrency.currencyexchange.confirmation.CurrencyExchangeConfirmationState viewModelState = null;
        co.yap.multicurrency.currencyexchange.confirmation.CurrencyExchangeConfirmationViewModel viewModel = mViewModel;

        if ((dirtyFlags & 0x3fL) != 0) {



                if (viewModel != null) {
                    // read viewModel.state
                    viewModelState = viewModel.getState();
                }
                updateRegistration(3, viewModelState);

            if ((dirtyFlags & 0x39L) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.yapRateValue
                        viewModelStateYapRateValue = viewModelState.getYapRateValue();
                    }
                    updateLiveDataRegistration(0, viewModelStateYapRateValue);


                    if (viewModelStateYapRateValue != null) {
                        // read viewModel.state.yapRateValue.getValue()
                        viewModelStateYapRateValueGetValue = viewModelStateYapRateValue.getValue();
                    }
            }
            if ((dirtyFlags & 0x3aL) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.selectedCurrencyAmount
                        viewModelStateSelectedCurrencyAmount = viewModelState.getSelectedCurrencyAmount();
                    }
                    updateLiveDataRegistration(1, viewModelStateSelectedCurrencyAmount);


                    if (viewModelStateSelectedCurrencyAmount != null) {
                        // read viewModel.state.selectedCurrencyAmount.getValue()
                        viewModelStateSelectedCurrencyAmountGetValue = viewModelStateSelectedCurrencyAmount.getValue();
                    }
            }
            if ((dirtyFlags & 0x3cL) != 0) {

                    if (viewModelState != null) {
                        // read viewModel.state.targetCurrencyAmount
                        viewModelStateTargetCurrencyAmount = viewModelState.getTargetCurrencyAmount();
                    }
                    updateLiveDataRegistration(2, viewModelStateTargetCurrencyAmount);


                    if (viewModelStateTargetCurrencyAmount != null) {
                        // read viewModel.state.targetCurrencyAmount.getValue()
                        viewModelStateTargetCurrencyAmountGetValue = viewModelStateTargetCurrencyAmount.getValue();
                    }
            }
        }
        // batch finished
        if ((dirtyFlags & 0x20L) != 0) {
            // api target 1

            this.btnConfirmExchange.setOnClickListener(mCallback20);
            co.yap.yapcore.binders.UIBinder.setText(this.btnConfirmExchange, co.yap.translation.Strings.screen_multi_currency_exchange_currency_confirmation_text_button);
            co.yap.yapcore.binders.UIBinder.setText(this.tvTo, co.yap.translation.Strings.screen_multi_currency_exchange_currency_confirmation_text_for);
            co.yap.yapcore.binders.UIBinder.setText(this.tvYapRateTitle, co.yap.translation.Strings.screen_multi_currency_exchange_currency_input_text_yap_rate);
        }
        if ((dirtyFlags & 0x39L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView5, viewModelStateYapRateValueGetValue);
        }
        if ((dirtyFlags & 0x3aL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvSelectedCurrencyAmount, viewModelStateSelectedCurrencyAmountGetValue);
        }
        if ((dirtyFlags & 0x3cL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvTargetCurrencyAmount, viewModelStateTargetCurrencyAmountGetValue);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // viewModel
        co.yap.multicurrency.currencyexchange.confirmation.CurrencyExchangeConfirmationViewModel viewModel = mViewModel;
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
        flag 0 (0x1L): viewModel.state.yapRateValue
        flag 1 (0x2L): viewModel.state.selectedCurrencyAmount
        flag 2 (0x3L): viewModel.state.targetCurrencyAmount
        flag 3 (0x4L): viewModel.state
        flag 4 (0x5L): viewModel
        flag 5 (0x6L): null
    flag mapping end*/
    //end
}