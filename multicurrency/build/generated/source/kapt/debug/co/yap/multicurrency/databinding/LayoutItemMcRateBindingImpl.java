package co.yap.multicurrency.databinding;
import co.yap.multicurrency.R;
import co.yap.multicurrency.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutItemMcRateBindingImpl extends LayoutItemMcRateBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.swipe, 9);
        sViewsWithIds.put(R.id.btnDelete, 10);
        sViewsWithIds.put(R.id.foregroundContainer, 11);
        sViewsWithIds.put(R.id.content, 12);
        sViewsWithIds.put(R.id.content_second_currency, 13);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    @NonNull
    private final android.widget.TextView mboundView1;
    // variables
    // values
    // listeners
    private OnClickListenerImpl mViewModelOnViewClickedAndroidViewViewOnClickListener;
    // Inverse Binding Event Handlers

    public LayoutItemMcRateBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 14, sIncludes, sViewsWithIds));
    }
    private LayoutItemMcRateBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.RelativeLayout) bindings[10]
            , (android.widget.LinearLayout) bindings[12]
            , (android.widget.LinearLayout) bindings[13]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[11]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[5]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[2]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[6]
            , (android.widget.LinearLayout) bindings[9]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[7]
            );
        this.ivCurrencySwitch.setTag(null);
        this.ivFirstCurr.setTag(null);
        this.ivSecondCurr.setTag(null);
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.TextView) bindings[1];
        this.mboundView1.setTag(null);
        this.tvCurrencyName.setTag(null);
        this.tvFullName.setTag(null);
        this.tvName.setTag(null);
        this.tvSecondCurrency.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
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
            setViewModel((co.yap.multicurrency.ratesalerts.rates.adaptor.MCRateItemViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable co.yap.multicurrency.ratesalerts.rates.adaptor.MCRateItemViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
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
        java.lang.String viewModelMcRateSrcCurrencyCode = null;
        java.lang.String viewModelMcRateDesCurrency = null;
        java.lang.String stringExtensionsKtToFormattedAmountWithCurrencyViewModelMcRateConversionRateViewModelMcRateDesCurrencyCode = null;
        co.yap.networking.customers.responsedtos.mcratesandalerts.MCRate viewModelMcRate = null;
        android.view.View.OnClickListener viewModelOnViewClickedAndroidViewViewOnClickListener = null;
        java.lang.String stringExtensionsKtToFormattedAmountWithCurrencyJavaLangString1ViewModelMcRateSrcCurrencyCode = null;
        java.lang.String viewModelMcRateDesCountry2DigitCode = null;
        java.lang.String viewModelMcRateSrcCountry2DigitCode = null;
        java.lang.String viewModelMcRateConversionRate = null;
        java.lang.String viewModelMcRateSrcCurrency = null;
        co.yap.multicurrency.ratesalerts.rates.adaptor.MCRateItemViewModel viewModel = mViewModel;
        java.lang.String viewModelMcRateDesCurrencyCode = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (viewModel != null) {
                    // read viewModel.mcRate
                    viewModelMcRate = viewModel.getMcRate();
                    // read viewModel::onViewClicked
                    viewModelOnViewClickedAndroidViewViewOnClickListener = (((mViewModelOnViewClickedAndroidViewViewOnClickListener == null) ? (mViewModelOnViewClickedAndroidViewViewOnClickListener = new OnClickListenerImpl()) : mViewModelOnViewClickedAndroidViewViewOnClickListener).setValue(viewModel));
                }


                if (viewModelMcRate != null) {
                    // read viewModel.mcRate.srcCurrencyCode
                    viewModelMcRateSrcCurrencyCode = viewModelMcRate.getSrcCurrencyCode();
                    // read viewModel.mcRate.desCurrency
                    viewModelMcRateDesCurrency = viewModelMcRate.getDesCurrency();
                    // read viewModel.mcRate.desCountry2DigitCode
                    viewModelMcRateDesCountry2DigitCode = viewModelMcRate.getDesCountry2DigitCode();
                    // read viewModel.mcRate.srcCountry2DigitCode
                    viewModelMcRateSrcCountry2DigitCode = viewModelMcRate.getSrcCountry2DigitCode();
                    // read viewModel.mcRate.conversionRate
                    viewModelMcRateConversionRate = viewModelMcRate.getConversionRate();
                    // read viewModel.mcRate.srcCurrency
                    viewModelMcRateSrcCurrency = viewModelMcRate.getSrcCurrency();
                    // read viewModel.mcRate.desCurrencyCode
                    viewModelMcRateDesCurrencyCode = viewModelMcRate.getDesCurrencyCode();
                }


                // read StringExtensionsKt.toFormattedAmountWithCurrency("1", viewModel.mcRate.srcCurrencyCode)
                stringExtensionsKtToFormattedAmountWithCurrencyJavaLangString1ViewModelMcRateSrcCurrencyCode = co.yap.yapcore.helpers.extentions.StringExtensionsKt.toFormattedAmountWithCurrency("1", viewModelMcRateSrcCurrencyCode);
                // read StringExtensionsKt.toFormattedAmountWithCurrency(viewModel.mcRate.conversionRate, viewModel.mcRate.desCurrencyCode)
                stringExtensionsKtToFormattedAmountWithCurrencyViewModelMcRateConversionRateViewModelMcRateDesCurrencyCode = co.yap.yapcore.helpers.extentions.StringExtensionsKt.toFormattedAmountWithCurrency(viewModelMcRateConversionRate, viewModelMcRateDesCurrencyCode);
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            this.ivCurrencySwitch.setOnClickListener(viewModelOnViewClickedAndroidViewViewOnClickListener);
            co.yap.yapcore.helpers.ImageBinding.setIsoCountryDrawable(this.ivFirstCurr, viewModelMcRateSrcCountry2DigitCode);
            co.yap.yapcore.helpers.ImageBinding.setIsoCountryDrawable(this.ivSecondCurr, viewModelMcRateDesCountry2DigitCode);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvCurrencyName, viewModelMcRateDesCurrency);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvFullName, viewModelMcRateSrcCurrency);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvName, stringExtensionsKtToFormattedAmountWithCurrencyJavaLangString1ViewModelMcRateSrcCurrencyCode);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvSecondCurrency, stringExtensionsKtToFormattedAmountWithCurrencyViewModelMcRateConversionRateViewModelMcRateDesCurrencyCode);
        }
        if ((dirtyFlags & 0x2L) != 0) {
            // api target 1

            co.yap.yapcore.binders.UIBinder.setText(this.mboundView1, co.yap.translation.Strings.common_button_delete);
        }
    }
    // Listener Stub Implementations
    public static class OnClickListenerImpl implements android.view.View.OnClickListener{
        private co.yap.multicurrency.ratesalerts.rates.adaptor.MCRateItemViewModel value;
        public OnClickListenerImpl setValue(co.yap.multicurrency.ratesalerts.rates.adaptor.MCRateItemViewModel value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.onViewClicked(arg0); 
        }
    }
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}