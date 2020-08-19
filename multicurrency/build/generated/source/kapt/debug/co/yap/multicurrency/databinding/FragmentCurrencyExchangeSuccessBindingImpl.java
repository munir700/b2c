package co.yap.multicurrency.databinding;
import co.yap.multicurrency.R;
import co.yap.multicurrency.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentCurrencyExchangeSuccessBindingImpl extends FragmentCurrencyExchangeSuccessBinding implements co.yap.multicurrency.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback9;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentCurrencyExchangeSuccessBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds));
    }
    private FragmentCurrencyExchangeSuccessBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2
            , (co.yap.widgets.CoreButton) bindings[3]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[2]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[1]
            );
        this.btnDone.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvFromAndToCurrencyAmount.setTag(null);
        this.tvSuccessTitle.setTag(null);
        setRootTag(root);
        // listeners
        mCallback9 = new co.yap.multicurrency.generated.callback.OnClickListener(this, 1);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x8L;
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
            setViewModel((co.yap.multicurrency.currencyexchange.exchangesuccess.CurrencyExchangeSuccessViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable co.yap.multicurrency.currencyexchange.exchangesuccess.CurrencyExchangeSuccessViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeViewModelState((co.yap.multicurrency.currencyexchange.exchangesuccess.CurrencyExchangeSuccessState) object, fieldId);
            case 1 :
                return onChangeViewModelStateSelectedAndConvertedAmount((androidx.lifecycle.MutableLiveData<java.lang.String>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelState(co.yap.multicurrency.currencyexchange.exchangesuccess.CurrencyExchangeSuccessState ViewModelState, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelStateSelectedAndConvertedAmount(androidx.lifecycle.MutableLiveData<java.lang.String> ViewModelStateSelectedAndConvertedAmount, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
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
        co.yap.multicurrency.currencyexchange.exchangesuccess.CurrencyExchangeSuccessState viewModelState = null;
        java.lang.String viewModelStateSelectedAndConvertedAmountGetValue = null;
        androidx.lifecycle.MutableLiveData<java.lang.String> viewModelStateSelectedAndConvertedAmount = null;
        co.yap.multicurrency.currencyexchange.exchangesuccess.CurrencyExchangeSuccessViewModel viewModel = mViewModel;

        if ((dirtyFlags & 0xfL) != 0) {



                if (viewModel != null) {
                    // read viewModel.state
                    viewModelState = viewModel.getState();
                }
                updateRegistration(0, viewModelState);


                if (viewModelState != null) {
                    // read viewModel.state.selectedAndConvertedAmount
                    viewModelStateSelectedAndConvertedAmount = viewModelState.getSelectedAndConvertedAmount();
                }
                updateLiveDataRegistration(1, viewModelStateSelectedAndConvertedAmount);


                if (viewModelStateSelectedAndConvertedAmount != null) {
                    // read viewModel.state.selectedAndConvertedAmount.getValue()
                    viewModelStateSelectedAndConvertedAmountGetValue = viewModelStateSelectedAndConvertedAmount.getValue();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x8L) != 0) {
            // api target 1

            this.btnDone.setOnClickListener(mCallback9);
            co.yap.yapcore.binders.UIBinder.setText(this.btnDone, co.yap.translation.Strings.screen_multi_currency_exchange_currency_success_text_button);
            co.yap.yapcore.binders.UIBinder.setText(this.tvSuccessTitle, co.yap.translation.Strings.screen_multi_currency_exchange_currency_success_text_title);
        }
        if ((dirtyFlags & 0xfL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvFromAndToCurrencyAmount, viewModelStateSelectedAndConvertedAmountGetValue);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // viewModel
        co.yap.multicurrency.currencyexchange.exchangesuccess.CurrencyExchangeSuccessViewModel viewModel = mViewModel;
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
        flag 1 (0x2L): viewModel.state.selectedAndConvertedAmount
        flag 2 (0x3L): viewModel
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}