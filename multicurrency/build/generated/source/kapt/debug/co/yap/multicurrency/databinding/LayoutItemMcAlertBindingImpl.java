package co.yap.multicurrency.databinding;
import co.yap.multicurrency.R;
import co.yap.multicurrency.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutItemMcAlertBindingImpl extends LayoutItemMcAlertBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.swipe, 6);
        sViewsWithIds.put(R.id.btnDelete, 7);
        sViewsWithIds.put(R.id.foregroundContainer, 8);
        sViewsWithIds.put(R.id.rlFlags, 9);
        sViewsWithIds.put(R.id.content, 10);
        sViewsWithIds.put(R.id.swActiveInactiveAlert, 11);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    @NonNull
    private final androidx.appcompat.widget.AppCompatTextView mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutItemMcAlertBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds));
    }
    private LayoutItemMcAlertBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.RelativeLayout) bindings[7]
            , (android.widget.LinearLayout) bindings[10]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[8]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[2]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[3]
            , (android.widget.RelativeLayout) bindings[9]
            , (androidx.appcompat.widget.SwitchCompat) bindings[11]
            , (android.widget.LinearLayout) bindings[6]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[5]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[4]
            );
        this.ivFirstFlag.setTag(null);
        this.ivSecFlag.setTag(null);
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (androidx.appcompat.widget.AppCompatTextView) bindings[1];
        this.mboundView1.setTag(null);
        this.tvOriginalRate.setTag(null);
        this.tvWishRate.setTag(null);
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
            setViewModel((co.yap.multicurrency.ratesalerts.alerts.adaptor.MCPriceAlertsItemViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable co.yap.multicurrency.ratesalerts.alerts.adaptor.MCPriceAlertsItemViewModel ViewModel) {
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
        java.lang.String viewModelMcPriceAlertSrcCountry2DigitCode = null;
        java.lang.String javaLangString1ViewModelMcPriceAlertSrcCurrencyCode = null;
        java.lang.String viewModelMcPriceAlertOriginalRate = null;
        java.lang.String viewModelMcPriceAlertDesCountry2DigitCode = null;
        java.lang.String stringExtensionsKtToFormattedAmountWithCurrencyViewModelMcPriceAlertOriginalRateViewModelMcPriceAlertDesCurrencyCode = null;
        java.lang.String viewModelMcPriceAlertSrcCurrencyCode = null;
        co.yap.networking.customers.responsedtos.mcratesandalerts.MCPriceAlert viewModelMcPriceAlert = null;
        java.lang.String javaLangString1ViewModelMcPriceAlertSrcCurrencyCodeJavaLangStringStringExtensionsKtToFormattedAmountWithCurrencyViewModelMcPriceAlertWishRateViewModelMcPriceAlertDesCurrencyCode = null;
        java.lang.String viewModelMcPriceAlertWishRate = null;
        java.lang.String javaLangString1ViewModelMcPriceAlertSrcCurrencyCodeJavaLangString = null;
        java.lang.String stringExtensionsKtToFormattedAmountWithCurrencyViewModelMcPriceAlertWishRateViewModelMcPriceAlertDesCurrencyCode = null;
        java.lang.String viewModelMcPriceAlertDesCurrencyCode = null;
        java.lang.String javaLangString1ViewModelMcPriceAlertSrcCurrencyCodeJavaLangStringStringExtensionsKtToFormattedAmountWithCurrencyViewModelMcPriceAlertOriginalRateViewModelMcPriceAlertDesCurrencyCode = null;
        co.yap.multicurrency.ratesalerts.alerts.adaptor.MCPriceAlertsItemViewModel viewModel = mViewModel;

        if ((dirtyFlags & 0x3L) != 0) {



                if (viewModel != null) {
                    // read viewModel.mcPriceAlert
                    viewModelMcPriceAlert = viewModel.getMcPriceAlert();
                }


                if (viewModelMcPriceAlert != null) {
                    // read viewModel.mcPriceAlert.srcCountry2DigitCode
                    viewModelMcPriceAlertSrcCountry2DigitCode = viewModelMcPriceAlert.getSrcCountry2DigitCode();
                    // read viewModel.mcPriceAlert.originalRate
                    viewModelMcPriceAlertOriginalRate = viewModelMcPriceAlert.getOriginalRate();
                    // read viewModel.mcPriceAlert.desCountry2DigitCode
                    viewModelMcPriceAlertDesCountry2DigitCode = viewModelMcPriceAlert.getDesCountry2DigitCode();
                    // read viewModel.mcPriceAlert.srcCurrencyCode
                    viewModelMcPriceAlertSrcCurrencyCode = viewModelMcPriceAlert.getSrcCurrencyCode();
                    // read viewModel.mcPriceAlert.wishRate
                    viewModelMcPriceAlertWishRate = viewModelMcPriceAlert.getWishRate();
                    // read viewModel.mcPriceAlert.desCurrencyCode
                    viewModelMcPriceAlertDesCurrencyCode = viewModelMcPriceAlert.getDesCurrencyCode();
                }


                // read ("1 ") + (viewModel.mcPriceAlert.srcCurrencyCode)
                javaLangString1ViewModelMcPriceAlertSrcCurrencyCode = ("1 ") + (viewModelMcPriceAlertSrcCurrencyCode);
                // read StringExtensionsKt.toFormattedAmountWithCurrency(viewModel.mcPriceAlert.originalRate, viewModel.mcPriceAlert.desCurrencyCode)
                stringExtensionsKtToFormattedAmountWithCurrencyViewModelMcPriceAlertOriginalRateViewModelMcPriceAlertDesCurrencyCode = co.yap.yapcore.helpers.extentions.StringExtensionsKt.toFormattedAmountWithCurrency(viewModelMcPriceAlertOriginalRate, viewModelMcPriceAlertDesCurrencyCode);
                // read StringExtensionsKt.toFormattedAmountWithCurrency(viewModel.mcPriceAlert.wishRate, viewModel.mcPriceAlert.desCurrencyCode)
                stringExtensionsKtToFormattedAmountWithCurrencyViewModelMcPriceAlertWishRateViewModelMcPriceAlertDesCurrencyCode = co.yap.yapcore.helpers.extentions.StringExtensionsKt.toFormattedAmountWithCurrency(viewModelMcPriceAlertWishRate, viewModelMcPriceAlertDesCurrencyCode);


                // read (("1 ") + (viewModel.mcPriceAlert.srcCurrencyCode)) + (" = ")
                javaLangString1ViewModelMcPriceAlertSrcCurrencyCodeJavaLangString = (javaLangString1ViewModelMcPriceAlertSrcCurrencyCode) + (" = ");


                // read ((("1 ") + (viewModel.mcPriceAlert.srcCurrencyCode)) + (" = ")) + (StringExtensionsKt.toFormattedAmountWithCurrency(viewModel.mcPriceAlert.wishRate, viewModel.mcPriceAlert.desCurrencyCode))
                javaLangString1ViewModelMcPriceAlertSrcCurrencyCodeJavaLangStringStringExtensionsKtToFormattedAmountWithCurrencyViewModelMcPriceAlertWishRateViewModelMcPriceAlertDesCurrencyCode = (javaLangString1ViewModelMcPriceAlertSrcCurrencyCodeJavaLangString) + (stringExtensionsKtToFormattedAmountWithCurrencyViewModelMcPriceAlertWishRateViewModelMcPriceAlertDesCurrencyCode);
                // read ((("1 ") + (viewModel.mcPriceAlert.srcCurrencyCode)) + (" = ")) + (StringExtensionsKt.toFormattedAmountWithCurrency(viewModel.mcPriceAlert.originalRate, viewModel.mcPriceAlert.desCurrencyCode))
                javaLangString1ViewModelMcPriceAlertSrcCurrencyCodeJavaLangStringStringExtensionsKtToFormattedAmountWithCurrencyViewModelMcPriceAlertOriginalRateViewModelMcPriceAlertDesCurrencyCode = (javaLangString1ViewModelMcPriceAlertSrcCurrencyCodeJavaLangString) + (stringExtensionsKtToFormattedAmountWithCurrencyViewModelMcPriceAlertOriginalRateViewModelMcPriceAlertDesCurrencyCode);
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            co.yap.yapcore.helpers.ImageBinding.setIsoCountryDrawable(this.ivFirstFlag, viewModelMcPriceAlertDesCountry2DigitCode);
            co.yap.yapcore.helpers.ImageBinding.setIsoCountryDrawable(this.ivSecFlag, viewModelMcPriceAlertSrcCountry2DigitCode);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvOriginalRate, javaLangString1ViewModelMcPriceAlertSrcCurrencyCodeJavaLangStringStringExtensionsKtToFormattedAmountWithCurrencyViewModelMcPriceAlertOriginalRateViewModelMcPriceAlertDesCurrencyCode);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvWishRate, javaLangString1ViewModelMcPriceAlertSrcCurrencyCodeJavaLangStringStringExtensionsKtToFormattedAmountWithCurrencyViewModelMcPriceAlertWishRateViewModelMcPriceAlertDesCurrencyCode);
        }
        if ((dirtyFlags & 0x2L) != 0) {
            // api target 1

            co.yap.yapcore.binders.UIBinder.setText(this.mboundView1, co.yap.translation.Strings.common_button_delete);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}